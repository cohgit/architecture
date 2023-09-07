package cl.atianza.remove.api.admin.login;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.admin.UserPasswordDao;
import cl.atianza.remove.daos.admin.UserSessionDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.UserPassword;
import cl.atianza.remove.models.admin.UserSession;
import cl.atianza.remove.models.views.RemoveLogin;
import cl.atianza.remove.models.views.UserAccess;
import cl.atianza.remove.properties.RemoveWsProperties;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.utils.builders.RemoveUserAccessBuilder;
import spark.Request;
import spark.Response;

/**
 * Login service for User accounts.
 * @author pilin
 *
 */
public class AdminLoginController extends RestController {
	public AdminLoginController() {
		super(ERestPath.ADMIN_LOGIN, LogManager.getLogger(AdminLoginController.class));
	}

	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveLogin login = (RemoveLogin) RemoveRequestUtil.getBodyObject(request, RemoveLogin.class);
			
			return validateUserLogin(login, request.userAgent(), request.ip());
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	/**
	 * Validate user login
	 * @param login Login object with user and password references
	 * @param userAgent User agent from connection
	 * @param ip Ip from connection
	 * @return Json String object with user session information
	 * @throws NumberFormatException If retries parameter fails to cast (Shouldn't happend unless there is an error in properties file)
	 * @throws RemoveApplicationException If validation fails here comes the reason
	 */
	private String validateUserLogin(RemoveLogin login, String userAgent, String ip) throws NumberFormatException, RemoveApplicationException {
		UserPassword userPassword = UserPasswordDao.init().validateLogin(login, Integer.valueOf(RemoveWsProperties.init().getProperties().getLoginRetries()));
		
		if (userPassword.getUser().isFirst_time()) {
			UserDao.init().updateFirstTime(userPassword.getId_owner());
		}
		
		UserAccess access = RemoveUserAccessBuilder.buildAccessForUser(userPassword.getUser(), userAgent);
		createSession(userPassword, userAgent, ip, access.getToken());
		
		getLog().info("Access Granted:"+ access.getName());
		UserAuditAccessDao.init().save(userPassword.getUser().getId(), userPassword.getUser().getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_LOGIN);
		return RemoveResponseUtil.buildOk(access);
	}
	
	/**
	 * Create a user session
	 * @param userPassword UserPassword object with id owner
	 * @param userAgent User agent from session
	 * @param ip IP from session
	 * @param token relative token
	 * @throws RemoveApplicationException App Error in save session process.
	 */
	private void createSession(UserPassword userPassword, String userAgent, String ip, String token) throws RemoveApplicationException {
		UserSession session = new UserSession();
		session.setAgent(userAgent);
		session.setId_owner(userPassword.getId_owner());
		session.setIp(ip);
		session.setToken(token);
		
		UserSessionDao.init().save(session);
	}
}
