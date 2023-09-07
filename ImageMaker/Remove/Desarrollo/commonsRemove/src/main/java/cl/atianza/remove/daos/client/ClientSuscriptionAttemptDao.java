package cl.atianza.remove.daos.client;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.ClientSuscriptionAttempt;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Client Paybills DAOs methods.
 * @author pilin
 *
 */
@Deprecated
public class ClientSuscriptionAttemptDao extends RemoveDao {
	public static final String TABLE_NAME = "clients_suscriptions_attempts";
	
	private static final String FIELD_ID_PLAN = "id_plan";
	private static final String FIELD_UUID = "uuid";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_LASTNAME = "lastname";
	private static final String FIELD_CREATION_DATE = "creation_date";
	private static final String FIELD_FINISHED = "finished";
	private static final String FIELD_SUCCESSFUL = "successful";
	
	public ClientSuscriptionAttemptDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ClientSuscriptionAttemptDao.class), TABLE_NAME);
	}

	public static ClientSuscriptionAttemptDao init() throws RemoveApplicationException {
		return new ClientSuscriptionAttemptDao();
	}
	
	/**
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<ClientSuscriptionAttempt> listAll() throws RemoveApplicationException {
		return (List<ClientSuscriptionAttempt>) listAll(TABLE, ClientSuscriptionAttempt.class, FIELD_CREATION_DATE, "DESC");
	}

	/**
	 * Insert a Client attempt data into database.
	 * @param attempt
	 * @return
	 * @throws RemoveApplicationException
	 */
    public ClientSuscriptionAttempt save(ClientSuscriptionAttempt attempt) throws RemoveApplicationException {
    	attempt.setUuid(UUID.randomUUID().toString());
    	attempt.setCreation_date(RemoveDateUtils.nowLocalDateTime());
    	attempt.setFinished(false);
    	attempt.setSuccessful(false);
    	
    	String QUERY = buildInsertQuery(TABLE, FIELD_EMAIL, FIELD_UUID, FIELD_ID_PLAN, FIELD_NAME, 
    			FIELD_LASTNAME, FIELD_CREATION_DATE, FIELD_FINISHED, FIELD_SUCCESSFUL);
    	
    	Long idRecord = INIT_RECORD_VALUE;
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_EMAIL, attempt.getEmail())
    				.addParameter(FIELD_UUID, attempt.getUuid())
    				.addParameter(FIELD_ID_PLAN, attempt.getId_plan())
    				.addParameter(FIELD_NAME, attempt.getName())
    				.addParameter(FIELD_LASTNAME, attempt.getLastname())
    				.addParameter(FIELD_CREATION_DATE, attempt.getCreation_date())
    				.addParameter(FIELD_FINISHED, attempt.isFinished())
    				.addParameter(FIELD_SUCCESSFUL, attempt.isSuccessful()).executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	validateIdRecord(idRecord);
    	attempt.setId(idRecord);
    	
        return attempt;
    }
    
	/**
	 * @param uuid
	 * @throws RemoveApplicationException
	 */
    public void updateSuccess(String uuid) throws RemoveApplicationException {
    	updateFinishAndSuccess(uuid, true);
    }
    
	/**
	 * @param uuid
	 * @throws RemoveApplicationException
	 */
    public void updateFail(String uuid) throws RemoveApplicationException {
    	updateFinishAndSuccess(uuid, false);
    }
    
	/**
	 * @param uuid
	 * @param success
	 * @return
	 * @throws RemoveApplicationException
	 */
    private void updateFinishAndSuccess(String uuid, boolean success) throws RemoveApplicationException {
    	String QUERY = buildUpdateQuery(TABLE, FIELD_UUID, uuid, FIELD_FINISHED, FIELD_SUCCESSFUL);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_UUID, uuid)
    				.addParameter(FIELD_FINISHED, true)
    				.addParameter(FIELD_SUCCESSFUL, success).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    }
}
