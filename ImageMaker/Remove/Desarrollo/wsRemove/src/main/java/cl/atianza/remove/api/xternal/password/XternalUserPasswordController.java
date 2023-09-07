package cl.atianza.remove.api.xternal.password;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.admin.UserPasswordChangeRequestDao;
import cl.atianza.remove.daos.admin.UserPasswordDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.helpers.UserNotificationHelper;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.admin.UserPassword;
import cl.atianza.remove.models.admin.UserPasswordChangeRequest;
import cl.atianza.remove.models.views.NewPassword;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RestController;
import spark.Request;
import spark.Response;

/**
 * Service used for change users password (forgot your password option)
 * @author pilin
 *
 */
public class XternalUserPasswordController extends RestController {
	public XternalUserPasswordController() {
		super(ERestPath.XTERNAL_USER_PASSWORD, LogManager.getLogger(XternalUserPasswordController.class));
	}
	
	/**
	 * Count as request updating password (Send email with update link)
	 */
	@Override
	public Object delete(Request request, Response response) throws RemoveApplicationException {
		getLog().info("DELETE: " + this.getClass());

		try {
			String email = RemoveRequestUtil.getParamString(request, EWebParam.EMAIL);
			getLog().info("Recovery password for: " + email);
			User user = UserDao.init().getByLogin(email);
			
			if (user == null || !user.isActive()) {
				RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOT_FOUND);
			}
			getLog().info("Recovery password for: " + user);
			UserPasswordChangeRequest requestChange = UserPasswordChangeRequestDao.init().createRequest(user);
			UserNotificationHelper.init(user).createRecoveryPasswordNotification(requestChange.getUuid());
			getLog().info("Saving recovery password for: " + user);
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_PASSWORD_CHANGE_REQUESTED);
			
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	/**
	 * Count as update password (Create new password and disabled old one)
	 */
	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			NewPassword newPassword = (NewPassword) RemoveRequestUtil.getBodyObject(request, NewPassword.class);
			UserPasswordChangeRequest change = UserPasswordChangeRequestDao.init().get(newPassword.getUuidRequest());
			
			if (isValid(change)) {
				long id = saveNewPassword(change, newPassword);
				User user = UserDao.init().getBasicById(id);
				
				UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_PASSWORD_UPDATED);
				return RemoveResponseUtil.buildDefaultOk();
			}
		} catch (Throwable t) {
			return catchException(t);
		} 
		
		return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_RESET_PASSWORD_INVALID);
	}
	
	

	/**
	 * 
	 */
	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());

		try {
			String uuidRequest = RemoveRequestUtil.getParamString(request, EWebParam.UUID);
			UserPasswordChangeRequest change = UserPasswordChangeRequestDao.init().get(uuidRequest);
			
			if (isValid(change)) {
				return RemoveResponseUtil.buildDefaultOk();
			}
		} catch (Throwable t) {
			return catchException(t);
		} 
		
		return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_RESET_PASSWORD_INVALID);
	}

	private boolean isValid(UserPasswordChangeRequest change) {
		return change != null && !change.isConfirmed() && !change.expired();
	}
	/**
	 * 
	 * @param change
	 * @param newPassword
	 * @throws RemoveApplicationException
	 */
	private Long saveNewPassword(UserPasswordChangeRequest change, NewPassword newPassword) throws RemoveApplicationException {
		UserPassword password = new UserPassword();
		
		password.setActive(true);
		password.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		password.setId_owner(change.getId_owner());
		password.setPassword(newPassword.getNewPassword());
		
		UserPasswordDao.init().save(password);
		UserPasswordChangeRequestDao.init().confirmChange(change);
		
		return password.getId_owner();
	}
}

