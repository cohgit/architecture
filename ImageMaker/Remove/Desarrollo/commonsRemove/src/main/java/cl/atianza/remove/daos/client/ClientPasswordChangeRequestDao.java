package cl.atianza.remove.daos.client;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPasswordChangeRequest;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * User and Password relation DAOs methods.
 * @author pilin
 *
 */
public class ClientPasswordChangeRequestDao extends RemoveDao {
	private static final String TABLE_NAME = "clients_passwords_change_requests";
	
	private static final String FIELD_ID_CLIENT = "id_client";
	private static final String FIELD_UUID = "uuid";
	private static final String FIELD_CREATION_DATE = "creation_date";
	private static final String FIELD_EXPIRATION_DATE = "expiration_date";
	private static final String FIELD_CONFIRMED = "confirmed";
	
	public ClientPasswordChangeRequestDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ClientPasswordChangeRequestDao.class), TABLE_NAME);
	}

	public static ClientPasswordChangeRequestDao init() throws RemoveApplicationException {
		return new ClientPasswordChangeRequestDao();
	}
	
	/**
	 * Create a new password for an user.
	 * @param requestChange
	 * @return
	 * @throws RemoveApplicationException
	 */
	private void save(ClientPasswordChangeRequest requestChange) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_CLIENT, FIELD_UUID, FIELD_CREATION_DATE, FIELD_EXPIRATION_DATE, FIELD_CONFIRMED);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    			.addParameter(FIELD_ID_CLIENT, requestChange.getId_client())
    			.addParameter(FIELD_UUID, requestChange.getUuid())
    			.addParameter(FIELD_CREATION_DATE, requestChange.getCreation_date())
    			.addParameter(FIELD_EXPIRATION_DATE, requestChange.getExpiration_date())
    			.addParameter(FIELD_CONFIRMED, requestChange.isConfirmed()).executeUpdate();
    	    
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
	public ClientPasswordChangeRequest get(String uuid) throws RemoveApplicationException{
		return (ClientPasswordChangeRequest) getByField(TABLE, FIELD_UUID, uuid, ClientPasswordChangeRequest.class);
	}
	
	/**
	 * Disable all old passwords from an user.
	 * @param requestChange
	 * @throws RemoveApplicationException
	 */
	public void confirmChange(ClientPasswordChangeRequest requestChange) throws RemoveApplicationException{
		String QUERY = buildUpdateQuery(TABLE, FIELD_UUID, requestChange.getUuid(), FIELD_CONFIRMED);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_CONFIRMED, true).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch ( Exception ex ) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	/**
	 * @param client
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientPasswordChangeRequest createRequest(Client client) throws RemoveApplicationException {
		ClientPasswordChangeRequest requestChange = new ClientPasswordChangeRequest();
		
		requestChange.setConfirmed(false);
		requestChange.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		requestChange.setExpiration_date(RemoveDateUtils.nowLocalDateTime().plusDays(1));
		requestChange.setId_client(client.getId());
		
		ClientPasswordChangeRequestDao.init().save(requestChange);
		
		return requestChange;
	}
}
