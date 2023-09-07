package cl.atianza.remove.daos.client;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPaybill;
import cl.atianza.remove.models.client.ClientPaymentKey;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Client Paybills DAOs methods.
 * @author pilin
 *
 */
@Deprecated
public class ClientPaybillDao extends RemoveDao {
	public static final String TABLE_NAME = "clients_paybills";
	
	private static final String FIELD_ID_CLIENT_KEY = "id_client_key";
	private static final String FIELD_ID_PLAN = "id_plan";
	private static final String FIELD_ID_APPROVED_BY = "id_approved_by";
	private static final String FIELD_AMOUNT = "amount";
	private static final String FIELD_PAYMENT_DATE = "payment_date";
	private static final String FIELD_TRACKING_CODE = "tracking_code";
	private static final String FIELD_RAW_RESPONSE = "raw_response";
	private static final String FIELD_CONFIRMED = "confirmed";
	
	public ClientPaybillDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ClientPaybillDao.class), TABLE_NAME);
	}

	public static ClientPaybillDao init() throws RemoveApplicationException {
		return new ClientPaybillDao();
	}

	public ClientPaybill getById(Long id) throws RemoveApplicationException {
		return (ClientPaybill) getById(TABLE, id, ClientPaybill.class);
	}
	
	/**
	 * Insert a Client paybill data into database.
	 * @param paybill
	 * @return
	 * @throws RemoveApplicationException
	 */
    public ClientPaybill save(ClientPaybill paybill) throws RemoveApplicationException {
    	paybill.setPayment_date(RemoveDateUtils.nowLocalDateTime());
    	
    	String QUERY = buildInsertQuery(TABLE, FIELD_ID_CLIENT_KEY, FIELD_ID_PLAN, FIELD_AMOUNT, 
    			FIELD_RAW_RESPONSE, FIELD_PAYMENT_DATE, FIELD_TRACKING_CODE, FIELD_CONFIRMED);
    	
    	Long idRecord = INIT_RECORD_VALUE;
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_CLIENT_KEY, paybill.getId_client_key())
    				.addParameter(FIELD_ID_PLAN, paybill.getId_plan())
    				.addParameter(FIELD_AMOUNT, paybill.getAmount())
    				.addParameter(FIELD_PAYMENT_DATE, paybill.getPayment_date())
    				.addParameter(FIELD_TRACKING_CODE, paybill.getTracking_code())
    				.addParameter(FIELD_RAW_RESPONSE, paybill.getRaw_response())
    				.addParameter(FIELD_CONFIRMED, paybill.isConfirmed()).executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	validateIdRecord(idRecord);
    	paybill.setId(idRecord);
    	
        return paybill;
    }

	/**
	 * @param trackingCode
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientPaybill findByTrackingCode(String trackingCode) throws RemoveApplicationException {
		return (ClientPaybill) getByField(TABLE, FIELD_TRACKING_CODE, trackingCode, ClientPaybill.class);
	}

	/**
	 * @param payment
	 * @throws RemoveApplicationException
	 */
	public void confirmPayment(ClientPaybill payment) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, payment.getId(), FIELD_CONFIRMED, FIELD_ID_APPROVED_BY);
		
		try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_CONFIRMED, true)
    				.addParameter(FIELD_ID_APPROVED_BY, payment.getId_approved_by())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	
	/**
	 * @param idKey
	 * @throws RemoveApplicationException
	 */
	public List<ClientPaybill> listConfirmedByClientKey(Long idKey) throws RemoveApplicationException {
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_CLIENT_KEY, FIELD_CONFIRMED);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY)
					.addParameter(FIELD_ID_CLIENT_KEY, idKey)
					.addParameter(FIELD_CONFIRMED, true)
					.executeAndFetch(ClientPaybill.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	
	/**
	 * @param client
	 * @throws RemoveApplicationException
	 */
	public void listConfirmedByClient(Client client) throws RemoveApplicationException {
		List<ClientPaymentKey> keys = ClientPaymentKeyDao.init().listByClient(client.getId());
		
		keys.forEach(key -> {
			List<ClientPaybill> pays;
			try {
				pays = ClientPaybillDao.init().listConfirmedByClientKey(key.getId());
				pays.forEach(pay -> {
					try {
						pay.setMethod(key.getPlatform());
						pay.setPlanName(PlanDao.init().get(pay.getId_plan()).getName());
						
						if (pay.getId_approved_by() != null) {
							User user = UserDao.init().getBasicById(pay.getId_approved_by());
							
							if (user != null) {
								pay.setApprovedBy(user.getName() + " " + user.getLastname());
							}
						} else {
							pay.setApprovedBy(pay.isConfirmed() ? "message.approved.automatic" : "");
						}
					
						client.getPaybills().add(pay);	
					} catch (RemoveApplicationException e) {
						getLog().error("Error loading payments:", e);
					}
				});
			} catch (RemoveApplicationException e) {
				getLog().error("Error loading payments:", e);
			}
		});
	}

	/**
	 * @param date
	 * @return
	 * @throws RemoveApplicationException
	 */
	public long totalByMonthYear(LocalDate date) throws RemoveApplicationException {
		Long total = null;
		
		String QUERY = "SELECT SUM(" + FIELD_AMOUNT + ") FROM " + TABLE 
				+ " WHERE EXTRACT(MONTH FROM " + FIELD_PAYMENT_DATE + ") = " + date.getMonthValue() 
				+ " AND EXTRACT(YEAR FROM " + FIELD_PAYMENT_DATE + ") = " + date.getYear() + " AND " + FIELD_CONFIRMED + " = true";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			total = conn.createQuery(QUERY).executeAndFetchFirst(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return total != null ? total : 0l;
	}

	/**
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Long totalByLifetime() throws RemoveApplicationException {
		Long total = null;
		
		String QUERY = "SELECT SUM(" + FIELD_AMOUNT + ") FROM " + TABLE 
				+ " WHERE " + FIELD_CONFIRMED + " = true";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			total = conn.createQuery(QUERY).executeAndFetchFirst(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return total != null ? total : 0l;
	}
}
