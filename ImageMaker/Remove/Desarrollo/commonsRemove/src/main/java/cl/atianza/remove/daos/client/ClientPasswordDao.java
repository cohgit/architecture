package cl.atianza.remove.daos.client;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;
import org.sql2o.Query;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPassword;
import cl.atianza.remove.models.views.RemoveLogin;
import cl.atianza.remove.utils.CipherUtils;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Client and Password relation DAOs methods. (Will be replaced by Cognito in V2)
 * @author pilin
 *
 */
@Deprecated
public class ClientPasswordDao extends RemoveDao {
	private static final String TABLE_NAME = "clients_passwords";
	
	private static final String FIELD_ID_OWNER = "id_owner";
	private static final String FIELD_PASSWORD = "password";
	private static final String FIELD_EXPIRATION_DATE = "expiration_date";
	private static final String FIELD_ACTIVE = "active";
	
	public ClientPasswordDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ClientPasswordDao.class), TABLE_NAME);
	}

	public static ClientPasswordDao init() throws RemoveApplicationException {
		return new ClientPasswordDao();
	}
	
	/**
	 * Validate Client access to the system.
	 * @param login
	 * @param loginRetries
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientPassword validateLogin(RemoveLogin login, Integer loginRetries) throws RemoveApplicationException {
		// Valida Usuario
		Client client = ClientDao.init().getByLogin(login.getLogin());
		getLog().info("Client Found: " + client);
		
		if(client == null || !client.isActive()) {
			getLog().error("Client: " + client);
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PASSWORD);
		}
		
		// Valida Password
		ClientPassword validation = getByUserPasswordActive(client);
		
		if(validation == null) {
			getLog().error("Password: " + validation);
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PASSWORD);
		}
		
		if(!validation.getPassword().equals(login.encriptPassword())) {
			getLog().error("Password Invalid... "+client);
			client.setFailed_attempts(client.getFailed_attempts()+1);
			ClientDao.init().updateFailedTries(client, loginRetries);
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PASSWORD);
		}
//		
//		if (client.getFailed_attempts() > 0) {
//			client.setFailed_attempts(0);
//			ClientDao.init().updateFailedTries(client, loginRetries);
//		}
		
		validation.setClient(client);
		return validation;
	}
	/**
	 * Create a new password for a client.
	 * @param clientPassword
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientPassword save(ClientPassword clientPassword) throws RemoveApplicationException {
		disableOldPasswords(clientPassword);
		
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_OWNER, FIELD_PASSWORD, FIELD_EXPIRATION_DATE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		fillQueryObject(con.createQuery(QUERY), clientPassword).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return getByUserPasswordActive(new Client(clientPassword.getId_owner()));
	}
	/**
	 * Create a temporary password for a client.
	 * @param client
	 * @throws RemoveApplicationException
	 */
	public void saveDefault(Client client) throws RemoveApplicationException {
		ClientPassword accountPassword = new ClientPassword();
		
		accountPassword.setActive(true);
		accountPassword.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		accountPassword.setId_owner(client.getId());
		accountPassword.setPassword(CipherUtils.encrypt(client.getEmail()));
		accountPassword.setClient(client);
		
		save(accountPassword);
	}
	/**
	 * Returns an active password from a client.
	 * @param account
	 * @return
	 * @throws RemoveApplicationException
	 */
	private ClientPassword getByUserPasswordActive(Client account) throws RemoveApplicationException{
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_OWNER, FIELD_ACTIVE);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY)
					.addParameter(FIELD_ID_OWNER, account.getId())
					.addParameter(FIELD_ACTIVE, true)
					.executeAndFetchFirst(ClientPassword.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Disable all old passwords from a client.
	 * @param accountPassword
	 * @throws RemoveApplicationException
	 */
	private void disableOldPasswords(ClientPassword accountPassword) throws RemoveApplicationException{
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
	private Query fillQueryObject(Query query, ClientPassword usuarioClave) throws RemoveApplicationException {
		return query.addParameter(FIELD_ID_OWNER, usuarioClave.getId_owner())
		        .addParameter(FIELD_PASSWORD, CipherUtils.encrypt(usuarioClave.getPassword()))
		        .addParameter(FIELD_EXPIRATION_DATE, usuarioClave.getExpiration_date());
	}
}
