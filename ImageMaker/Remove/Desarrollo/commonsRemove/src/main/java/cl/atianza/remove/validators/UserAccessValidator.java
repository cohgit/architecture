package cl.atianza.remove.validators;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.admin.UserAlertMessageDao;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.admin.UserAlertMessage;
import cl.atianza.remove.utils.RemoveTokenAccess;

/**
 * Verifies if user is exists and is active
 * @author pilin
 *
 */
public class UserAccessValidator {
	private static Logger log = LogManager.getLogger(UserAccessValidator.class);
	private RemoveTokenAccess tokenUser;
	
	public UserAccessValidator(RemoveTokenAccess tokenUser) {
		super();
		this.tokenUser = tokenUser;
	}
	
	public static UserAccessValidator init(RemoveTokenAccess tokenUser) {
		return new UserAccessValidator(tokenUser);
	}

	/**
	 * Validate if the user is registered or active in the system.
	 * @return
	 * @throws RemoveApplicationException
	 */
	public User validateUserAccess() throws RemoveApplicationException {
		User user = UserDao.init().getByUuid(tokenUser.getUuidAccount());
		
		if (user == null) {
			log.error("User not found: ", tokenUser.getUuidAccount());
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOT_FOUND);
		}
		if (!user.isActive()) {
			log.error("User not active: ", tokenUser.getUuidAccount());
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOT_ACTIVE);
		}
		
		return user;
	}

	public void validateNotifications(List<UserAlertMessage> userAlerts) throws RemoveApplicationException {
		if (userAlerts != null && !userAlerts.isEmpty()) {
			User user = UserDao.init().getByUuid(tokenUser.getUuidAccount());
			
			for (UserAlertMessage message : userAlerts) {
				if (UserAlertMessageDao.init().getById(message.getId()).getId_owner().longValue() != user.getId().longValue()) {
					log.error("User Notification doesn't belong to this user:" + message);
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOTIFICATION_NOT_MATCH);
				}
			}
		}
	}
}
