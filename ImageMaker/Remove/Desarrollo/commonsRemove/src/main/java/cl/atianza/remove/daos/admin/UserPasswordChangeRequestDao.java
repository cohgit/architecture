package cl.atianza.remove.daos.admin;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.admin.UserPasswordChangeRequest;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * User and Password update request DAOs methods.
 * @author pilin
 *
 */
public class UserPasswordChangeRequestDao extends RemoveDao {
	private static final String TABLE_NAME = "users_passwords_change_requests";
	
	private static final String FIELD_ID_OWNER = "id_owner";
	private static final String FIELD_UUID = "uuid";
	private static final String FIELD_CREATION_DATE = "creation_date";
	private static final String FIELD_EXPIRATION_DATE = "expiration_date";
	private static final String FIELD_CONFIRMED = "confirmed";
	
	public UserPasswordChangeRequestDao() throws RemoveApplicationException {
		super(LogManager.getLogger(UserPasswordChangeRequestDao.class), TABLE_NAME);
	}

	public static UserPasswordChangeRequestDao init() throws RemoveApplicationException {
		return new UserPasswordChangeRequestDao();
	}
	
	/**
	 * Create a new password update for an user.
	 * @param requestChange
	 * @throws RemoveApplicationException
	 */
	public void save(UserPasswordChangeRequest requestChange) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_OWNER, FIELD_UUID, FIELD_CREATION_DATE, FIELD_EXPIRATION_DATE, FIELD_CONFIRMED);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    			.addParameter(FIELD_ID_OWNER, requestChange.getId_owner())
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
	 * Returns an active password update request from an user.
	 * @param uuid
	 * @return
	 * @throws RemoveApplicationException
	 */
	public UserPasswordChangeRequest get(String uuid) throws RemoveApplicationException{
		return (UserPasswordChangeRequest) getByField(TABLE, FIELD_UUID, uuid, UserPasswordChangeRequest.class);
	}
	
	/**
	 * Update a request of password change to confirmed.
	 * @param requestChange
	 * @throws RemoveApplicationException
	 */
	public void confirmChange(UserPasswordChangeRequest requestChange) throws RemoveApplicationException{
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
	 * Create a request for change of password for a user.
	 * @param user
	 * @return
	 * @throws RemoveApplicationException
	 */
	public UserPasswordChangeRequest createRequest(User user) throws RemoveApplicationException {
		UserPasswordChangeRequest requestChange = new UserPasswordChangeRequest();
		
		requestChange.setConfirmed(false);
		requestChange.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		requestChange.setExpiration_date(RemoveDateUtils.nowLocalDateTime().plusDays(1));
		requestChange.setId_owner(user.getId());
		
		UserPasswordChangeRequestDao.init().save(requestChange);
		
		return requestChange;
	}
}
