package cl.atianza.remove.api.admin.configs;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.admin.UserPasswordDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.admin.UserPassword;
import cl.atianza.remove.models.views.RemoveLogin;
import cl.atianza.remove.models.views.UpdatePassword;
import cl.atianza.remove.properties.RemoveWsProperties;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.UserAccessValidator;
import spark.Request;
import spark.Response;

/**
 * User service for self-account management.
 * @author pilin
 *
 */
public class AdminConfigController extends RestController {
	public AdminConfigController() {
		super(ERestPath.ADMIN_CONFIG, LogManager.getLogger(AdminConfigController.class));
	}
	
	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserAccessValidator.init(token).validateUserAccess();
			
			return RemoveResponseUtil.buildOk(user);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserAccessValidator.init(token).validateUserAccess();
			
			User config = (User) RemoveRequestUtil.getBodyObject(request, User.class);
			config.setId(user.getId());
			
			validateUpdateUser(config);
			
			UserDao.init().updateAccount(config);
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_CONFIGURATION_UPDATED);
			
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UpdatePassword config = (UpdatePassword) RemoveRequestUtil.getBodyObject(request, UpdatePassword.class);
			
			User user = UserAccessValidator.init(token).validateUserAccess();
			RemoveLogin login = new RemoveLogin(user.getEmail(), config.getOldPassword());
			
			UserPasswordDao.init().validateLogin(login, Integer.valueOf(RemoveWsProperties.init().getProperties().getLoginRetries()));
			
			UserPassword password = new UserPassword();
			password.setId_owner(user.getId());
			password.setPassword(config.getNewPassword());
			
			UserPasswordDao.init().save(password);
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_PASSWORD_UPDATED);
			
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	private void validateUpdateUser(User user) throws RemoveApplicationException {
		UserDao userDao = UserDao.init();
		User old = userDao.getByLogin(user.getEmail());
		
		if (old != null && old.getId().longValue() != user.getId().longValue()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_DUPLICATED);
		}
		
		old = userDao.getByPhone(user.getPhone());
		if (old != null && old.getId().longValue() != user.getId().longValue()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_DUPLICATED);
		}
	}
}
