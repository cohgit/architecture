package cl.atianza.remove.daos.client;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ClientNotificationHelper;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientEmailVerificationRequest;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * User and Password relation DAOs methods.
 * @author pilin
 *
 */
public class ClientEmailVerificationRequestDao extends RemoveDao {
	private static final String TABLE_NAME = "clients_email_verification_requests";
	
	private static final String FIELD_ID_CLIENT = "id_client";
	private static final String FIELD_UUID = "uuid";
	private static final String FIELD_CREATION_DATE = "creation_date";
	private static final String FIELD_EXPIRATION_DATE = "expiration_date";
	private static final String FIELD_CONFIRMED = "confirmed";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_ACTIVED = "actived";
	
	public ClientEmailVerificationRequestDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ClientEmailVerificationRequestDao.class), TABLE_NAME);
	}

	public static ClientEmailVerificationRequestDao init() throws RemoveApplicationException {
		return new ClientEmailVerificationRequestDao();
	}
	
	/**
	 * Create a new password for an user.
	 * @param verification
	 * @return
	 * @throws RemoveApplicationException
	 */
	private void save(ClientEmailVerificationRequest verification) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_CLIENT, FIELD_UUID, FIELD_CREATION_DATE, 
				FIELD_EXPIRATION_DATE, FIELD_CONFIRMED, FIELD_EMAIL, FIELD_ACTIVED);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    			.addParameter(FIELD_ID_CLIENT, verification.getId_client())
    			.addParameter(FIELD_UUID, verification.getUuid())
    			.addParameter(FIELD_CREATION_DATE, verification.getCreation_date())
    			.addParameter(FIELD_EXPIRATION_DATE, verification.getExpiration_date())
    			.addParameter(FIELD_ACTIVED, verification.isActived())
    			.addParameter(FIELD_EMAIL, verification.getEmail())
    			.addParameter(FIELD_CONFIRMED, verification.isConfirmed()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	/**
	 * Returns an active password from an user.
	 * @param uuid
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientEmailVerificationRequest get(String uuid) throws RemoveApplicationException{
		return (ClientEmailVerificationRequest) getByField(TABLE, FIELD_UUID, uuid, ClientEmailVerificationRequest.class);
	}
	
	/**
	 * Disable all old passwords from an user.
	 * @param verification
	 * @throws RemoveApplicationException
	 */
	public void confirmChange(ClientEmailVerificationRequest verification) throws RemoveApplicationException{
		String QUERY = buildUpdateQuery(TABLE, FIELD_UUID, verification.getUuid(), FIELD_CONFIRMED, FIELD_ACTIVED);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    			.addParameter(FIELD_CONFIRMED, true)
    			.addParameter(FIELD_ACTIVED, false).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch ( Exception ex ) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	/**
	 * @param _client
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientEmailVerificationRequest createRequest(Client _client) throws RemoveApplicationException {
		ClientEmailVerificationRequest verification = new ClientEmailVerificationRequest();
		
		verification.setId_client(_client.getId());
		verification.setConfirmed(false);
		verification.setActived(true);
		verification.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		verification.setExpiration_date(RemoveDateUtils.nowLocalDateTime().plusDays(1));
		verification.setEmail(_client.getEmail());
		
		ClientEmailVerificationRequestDao.init().save(verification);
		Client client = ClientDao.init().getById(_client.getId());
		
		ClientNotificationHelper.init(client).createVerifyEmailSolitude(verification.getUuid(), verification.getEmail());
		
		return verification;
	}
}
