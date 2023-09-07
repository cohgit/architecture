package cl.atianza.remove.daos.client;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EPaymentMethods;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.ClientPaymentKey;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Client Paybills DAOs methods.
 * @author pilin
 *
 */
@Deprecated
public class ClientPaymentKeyDao extends RemoveDao {
	public static final String TABLE_NAME = "clients_payment_keys";
	
	private static final String FIELD_ID_CLIENT = "id_client";
	private static final String FIELD_PLATFORM = "platform";
	private static final String FIELD_KEY = "key";
	private static final String FIELD_ACTIVE = "active";
	
	public ClientPaymentKeyDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ClientPaymentKeyDao.class), TABLE_NAME);
	}

	public static ClientPaymentKeyDao init() throws RemoveApplicationException {
		return new ClientPaymentKeyDao();
	}

	/**
	 * Insert a Client paybill data into database.
	 * @param key
	 * @return
	 * @throws RemoveApplicationException
	 */
    public ClientPaymentKey save(ClientPaymentKey key) throws RemoveApplicationException {
    	key.setActive(true);
    	
    	String QUERY = buildInsertQuery(TABLE, FIELD_ID_CLIENT, FIELD_PLATFORM, FIELD_KEY, 
    			FIELD_ACTIVE);
    	
    	Long idRecord = INIT_RECORD_VALUE;
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_CLIENT, key.getId_client())
    				.addParameter(FIELD_PLATFORM, key.getPlatform())
    				.addParameter(FIELD_KEY, key.getKey())
    				.addParameter(FIELD_ACTIVE, key.isActive()).executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	validateIdRecord(idRecord);
    	key.setId(idRecord);
    	
        return key;
    }
    
	/**
	 * @param idClient
	 * @param platform
	 * @return
	 * @throws RemoveApplicationException
	 */
    public ClientPaymentKey getByClientAndPaymentMethod(Long idClient, EPaymentMethods platform) throws RemoveApplicationException {
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_CLIENT, FIELD_PLATFORM, FIELD_ACTIVE);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY)
					.addParameter(FIELD_ID_CLIENT, idClient)
					.addParameter(FIELD_PLATFORM, platform.getCode())
					.addParameter(FIELD_ACTIVE, true)
					.executeAndFetchFirst(ClientPaymentKey.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
    }
    
	/**
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
    public ClientPaymentKey getById(Long id) throws RemoveApplicationException {
		return (ClientPaymentKey) getById(TABLE, id, ClientPaymentKey.class);
	}
    
    @Deprecated
	public void createTestData() throws RemoveApplicationException {
		ClientPaymentKey cp = new ClientPaymentKey();
		cp.setKey("XXXXXXXX");
		cp.setPlatform(EPaymentMethods.STRIPE.getCode());
		cp.setId_client(1l);
		save(cp);
		cp.setId_client(2l);
		save(cp);
	}

	/**
	 * @param idClient
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ClientPaymentKey> listByClient(Long idClient) throws RemoveApplicationException {
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_CLIENT);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY)
					.addParameter(FIELD_ID_CLIENT, idClient)
					.executeAndFetch(ClientPaymentKey.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
}
