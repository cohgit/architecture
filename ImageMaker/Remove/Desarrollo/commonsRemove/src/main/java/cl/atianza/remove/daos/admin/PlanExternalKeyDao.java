package cl.atianza.remove.daos.admin;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EPaymentMethods;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.PlanExternalKey;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Client's plans keys from payemnt platforms DAO methods. (Will be changed in V2)
 * @author pilin
 *
 */
@Deprecated
public class PlanExternalKeyDao extends RemoveDao {
	private static final String TABLE_NAME = "plans_external_key";
	
	private static final String FIELD_ID_PLAN = "id_plan";
	private static final String FIELD_KEY = "key";
	private static final String FIELD_KEY_PAYMENT = "key_payment";
	private static final String FIELD_PLATFORM = "platform";
	private static final String FIELD_ACTIVE = "active";
	
	/**
	 * 
	 * @throws RemoveApplicationException
	 */
	public PlanExternalKeyDao() throws RemoveApplicationException {
		super(LogManager.getLogger(PlanExternalKeyDao.class), TABLE_NAME);
	}
	
	/**
	 * 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public static PlanExternalKeyDao init() throws RemoveApplicationException {
		return new PlanExternalKeyDao();
	}	

	/**
	 * 
	 * @param key
	 * @return
	 * @throws RemoveApplicationException
	 */
	public PlanExternalKey save(PlanExternalKey key) throws RemoveApplicationException {
		key.setActive(true);
		
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_PLAN, FIELD_KEY, FIELD_PLATFORM, FIELD_KEY_PAYMENT, FIELD_ACTIVE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_PLAN, key.getId_plan())
    				.addParameter(FIELD_KEY, key.getKey())
    				.addParameter(FIELD_KEY_PAYMENT, key.getKey_payment())
    				.addParameter(FIELD_PLATFORM, key.getPlatform())
    				.addParameter(FIELD_ACTIVE, key.isActive())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	key.setId(idRecord);
    	
    	return key;
	}
	
	/**
	 * @param idPlan
	 * @param platform
	 * @return
	 * @throws RemoveApplicationException
	 */
	public PlanExternalKey getByPlan(Long idPlan, EPaymentMethods platform) throws RemoveApplicationException {
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_PLAN, FIELD_PLATFORM, FIELD_ACTIVE);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY)
					.addParameter(FIELD_ID_PLAN, idPlan)
					.addParameter(FIELD_ACTIVE, true)
					.addParameter(FIELD_PLATFORM, platform.getCode())
					.executeAndFetchFirst(PlanExternalKey.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}

	/**
	 * @param key
	 * @throws RemoveApplicationException
	 */
	public void update(PlanExternalKey key) throws RemoveApplicationException {
		String QUERY = "UPDATE " + TABLE + " SET " + concatFieldsForUpdate(FIELD_KEY, FIELD_KEY_PAYMENT) + 
				" WHERE " + FIELD_ID_PLAN + " = " + key.getId_plan() + " AND " + FIELD_PLATFORM + " = '" + key.getPlatform() + "' ";
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_KEY, key.getKey())
    				.addParameter(FIELD_KEY_PAYMENT, key.getKey_payment())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
}
