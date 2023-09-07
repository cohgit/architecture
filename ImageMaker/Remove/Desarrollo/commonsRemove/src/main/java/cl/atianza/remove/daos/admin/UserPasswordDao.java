package cl.atianza.remove.daos.admin;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;
import org.sql2o.Query;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.admin.UserPassword;
import cl.atianza.remove.models.views.RemoveLogin;
import cl.atianza.remove.utils.CipherUtils;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * User and Password relation DAOs methods. (This will be replaced by Cognito)
 * @author pilin
 *
 */
@Deprecated
public class UserPasswordDao extends RemoveDao {
	private static final String TABLE_NAME = "users_passwords";
	
	private static final String FIELD_ID_OWNER = "id_owner";
	private static final String FIELD_PASSWORD = "password";
	private static final String FIELD_EXPIRATION_DATE = "expiration_date";
	private static final String FIELD_ACTIVE = "active";
	
	public UserPasswordDao() throws RemoveApplicationException {
		super(LogManager.getLogger(UserPasswordDao.class), TABLE_NAME);
	}

	public static UserPasswordDao init() throws RemoveApplicationException {
		return new UserPasswordDao();
	}
	
	/**
	 * Validate User access to the system.
	 * @param login
	 * @param maxLoginRetries
	 * @return
	 * @throws RemoveApplicationException
	 */
	public UserPassword validateLogin(RemoveLogin login, Integer maxLoginRetries) throws RemoveApplicationException {
		// Valida Usuario
		User user = UserDao.init().getByLogin(login.getLogin());
		
		if(user == null || !user.isActive() || user.isLocked()) {
			getLog().error("User: " + user);
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PASSWORD);
		}
		
		// Valida Password
		UserPassword validation = getByUserPasswordActive(user);
		
		if(validation == null) {
			getLog().error("Password: " + validation);
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PASSWORD);
		}
		
		if(!validation.getPassword().equals(login.encriptPassword())) {
			getLog().error("Password Invalid... "+user);
			user.setFailed_attempts(user.getFailed_attempts()+1);
			UserDao.init().updateFailedTries(user, maxLoginRetries);
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PASSWORD);
		}
		
		if (user.getFailed_attempts() > 0) {
			user.setFailed_attempts(0);
			UserDao.init().updateFailedTries(user, maxLoginRetries);
		}
		
		validation.setUser(user);
		return validation;
	}
	/**
	 * Create a new password for an user.
	 * @param accountPassword
	 * @return
	 * @throws RemoveApplicationException
	 */
	public UserPassword save(UserPassword accountPassword) throws RemoveApplicationException {
		disableOldPasswords(accountPassword);
		
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_OWNER, FIELD_PASSWORD, FIELD_EXPIRATION_DATE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		fillQueryObject(con.createQuery(QUERY), accountPassword).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return getByUserPasswordActive(new User(accountPassword.getId_owner()));
	}
	/**
	 * Create a temporary password for an user.
	 * @param user
	 * @throws RemoveApplicationException
	 */
	public void saveDefault(User user) throws RemoveApplicationException {
		UserPassword accountPassword = new UserPassword();
		
		accountPassword.setActive(true);
		accountPassword.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		accountPassword.setId_owner(user.getId());
		accountPassword.setPassword(user.getEmail());
		accountPassword.setUser(user);
		
		save(accountPassword);
	}
	/**
	 * Returns an active password from an user.
	 * @param account
	 * @return
	 * @throws RemoveApplicationException
	 */
	private UserPassword getByUserPasswordActive(User account) throws RemoveApplicationException{
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_OWNER, FIELD_ACTIVE);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY)
					.addParameter(FIELD_ID_OWNER, account.getId())
					.addParameter(FIELD_ACTIVE, true)
					.executeAndFetchFirst(UserPassword.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Disable all old passwords from an user.
	 * @param accountPassword
	 * @throws RemoveApplicationException
	 */
	private void disableOldPasswords(UserPassword accountPassword) throws RemoveApplicationException{
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID_OWNER, accountPassword.getId_owner(), FIELD_ACTIVE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_ACTIVE, false).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch ( Exception ex ) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	/**
	 * 
	 * @param query
	 * @param usuarioClave
	 * @return
	 * @throws RemoveApplicationException 
	 */
	private Query fillQueryObject(Query query, UserPassword usuarioClave) throws RemoveApplicationException {
		return query.addParameter(FIELD_ID_OWNER, usuarioClave.getId_owner())
		        .addParameter(FIELD_PASSWORD, CipherUtils.encrypt(usuarioClave.getPassword()))
		        .addParameter(FIELD_EXPIRATION_DATE, usuarioClave.getExpiration_date());
	}
}
