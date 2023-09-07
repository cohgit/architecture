package cl.atianza.remove.api.admin.users;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.admin.UserPasswordChangeRequestDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.helpers.UserNotificationHelper;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.admin.UserPasswordChangeRequest;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.UserProfileAccessValidator;
import spark.Request;
import spark.Response;

/**
 * REST services for system user management
 * @author pilin
 *
 */
public class AdminUserController extends RestController {
	public AdminUserController() {
		super(ERestPath.ADMIN_USERS, LogManager.getLogger(AdminUserController.class));
	}

	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			return RemoveResponseUtil.buildOk(UserDao.init().getByUuid(RemoveRequestUtil.getParamString(request, EWebParam.UUID)));
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			User user = (User) RemoveRequestUtil.getBodyObject(request, User.class);
			user.checkAtts();
			
			validateNewUser(user);
			
			user = UserDao.init().save(user);
			UserPasswordChangeRequest passReq = UserPasswordChangeRequestDao.init().createRequest(user);
			
			UserNotificationHelper.init(user).createVerifyEmailNotification(passReq.getUuid());
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_USER_SAVED);
			
			return RemoveResponseUtil.buildOk(user);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}


	@Override
	public Object list(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LIST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccessList(this);
			
			return RemoveResponseUtil.buildOk(UserDao.init().listAll());
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			User user = (User) RemoveRequestUtil.getBodyObject(request, User.class);
			user.checkAtts();
			
			validateUpdateUser(user);
			
			user = UserDao.init().updateAsAdmin(user);
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_USER_UPDATED);
			
			return RemoveResponseUtil.buildOk(user);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object delete(Request request, Response response) throws RemoveApplicationException {
		getLog().info("DELETE: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			User user = UserDao.init().getById(RemoveRequestUtil.getParamLong(request, EWebParam.ID));
			user = UserDao.init().switchActive(user);
			
			// No se si enviar aqui o en el post?
//			if (user.isActive()) {
//				EmailHelper.init().sendEmailNewUser(user.getName(), user.getEmail());
//			}
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_USER_UPDATED);
			
			return RemoveResponseUtil.buildOk(user);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	private void validateNewUser(User user) throws RemoveApplicationException {
		UserDao userDao = UserDao.init();
		User old = userDao.getByLogin(user.getEmail());
		
		if (old != null) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_DUPLICATED);
		}
		
		old = userDao.getByPhone(user.getPhone());
		if (old != null) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_DUPLICATED);
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
