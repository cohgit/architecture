package cl.atianza.remove.api.client.login;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPasswordDao;
import cl.atianza.remove.daos.client.ClientSessionDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.ClientPassword;
import cl.atianza.remove.models.client.ClientSession;
import cl.atianza.remove.models.views.ClientAccess;
import cl.atianza.remove.models.views.RemoveLogin;
import cl.atianza.remove.properties.RemoveWsProperties;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.utils.builders.RemoveUserAccessBuilder;
import spark.Request;
import spark.Response;

/**
 * Login service for clients accounts.
 * @author pilin
 *
 */
public class ClientLoginController extends RestController {
	public ClientLoginController() {
		super(ERestPath.CLIENT_LOGIN, LogManager.getLogger(ClientLoginController.class));
	}

	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveLogin login = (RemoveLogin) RemoveRequestUtil.getBodyObject(request, RemoveLogin.class);
			
			getLog().info("login: " + login);
			
			return validateClientLogin(login, request.userAgent(), request.ip());
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	/**
	 * Validate client login
	 * @param login Login object with user and password references
	 * @param userAgent Client agent from connection
	 * @param ip Ip from connection
	 * @return Json String object with user session information
	 * @throws NumberFormatException If retries parameter fails to cast (Shouldn't happend unless there is an error in properties file)
	 * @throws RemoveApplicationException If validation fails here comes the reason
	 */
	private String validateClientLogin(RemoveLogin login, String userAgent, String ip) throws NumberFormatException, RemoveApplicationException {
		ClientPassword clientPassword = ClientPasswordDao.init().validateLogin(login, Integer.valueOf(RemoveWsProperties.init().getProperties().getLoginRetries()));
		
		if (clientPassword.getClient().isFirst_time()) {
			ClientDao.init().updateFirstTime(clientPassword.getId_owner());
		}
		
		ClientAccess access = RemoveUserAccessBuilder.buildAccessForClient(clientPassword.getClient(), userAgent);
		getLog().info("Access:" + access);
		
		createSession(clientPassword, userAgent, ip, access.getToken());
		
		ClientAuditAccessDao.init().save(clientPassword.getId_owner(), clientPassword.getId_owner(), EProfiles.CLIENT.getCode(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_LOGIN);
		return RemoveResponseUtil.buildOk(access);
	}

	/**
	 * Create a user session
	 * @param clientPassword ClientPassword object with id owner
	 * @param userAgent User agent from session
	 * @param ip IP from session
	 * @param token relative token
	 * @throws RemoveApplicationException App Error in save session process.
	 */
	private void createSession(ClientPassword clientPassword, String userAgent, String ip, String token) throws RemoveApplicationException {
		ClientSession session = new ClientSession();
		session.setAgent(userAgent);
		session.setId_owner(clientPassword.getId_owner());
		session.setIp(ip);
		session.setToken(token);
		
		ClientSessionDao.init().save(session);
	}
}
