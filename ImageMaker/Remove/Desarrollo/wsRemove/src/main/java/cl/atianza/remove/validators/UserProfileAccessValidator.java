package cl.atianza.remove.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.views.ProfileAccessModel;
import cl.atianza.remove.models.views.ServiceAccess;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.utils.builders.RemovePermissionBuilder;

public class UserProfileAccessValidator {
	private static Logger log = LogManager.getLogger(UserProfileAccessValidator.class);
	private RemoveTokenAccess tokenUser;
	
	public UserProfileAccessValidator(RemoveTokenAccess tokenUser) {
		super();
		this.tokenUser = tokenUser;
	}
	
	public static UserProfileAccessValidator init(RemoveTokenAccess tokenUser) {
		return new UserProfileAccessValidator(tokenUser);
	}
	
	public User validateServiceAccess(RestController controller, String requestMethod) throws RemoveApplicationException {
		return validateServiceAccess(controller.getPath().getKey(), requestMethod);
	}
	public User validateServiceAccessList(RestController controller) throws RemoveApplicationException {
		return validateServiceAccess(controller.getPath().getKey(), "LIST");
	}
	public User validateServiceAccessFile(RestController controller) throws RemoveApplicationException {
		return validateServiceAccess(controller.getPath().getKey(), "FILE");
	}
	public User validateServiceAccess(String serviceKey, String method) throws RemoveApplicationException {
//		log.info("Verifying user access (" + tokenUser.getUuidAccount() + ") to: " + serviceKey);
		
		User user = UserAccessValidator.init(tokenUser).validateUserAccess();
//		log.info("Verifying user access found (" + user.getId() + ") to: " + serviceKey);
		if (!user.getProfile().equals(tokenUser.getProfile())) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOT_MATCH_PROFILE);
		}
		
		EProfiles profile = EProfiles.obtain(user.getProfile());
//		log.info("Verifying user profile found (" + profile + ") to: " + serviceKey);
		if (profile == null) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_PROFILE_NOT_EXISTS);
		}
		
		ProfileAccessModel permissionsToProfile = RemovePermissionBuilder.init().buildByProfile(user.getProfile());
		ServiceAccess permission = permissionsToProfile.getLstAccess().get(serviceKey);
		
//		log.info("Verifying user profile permissions found (" + permission + ") to: " + serviceKey);
		
		if (permission == null) {
			log.error("Access Not Found: " + tokenUser.getUuidAccount() + " to: "+ serviceKey + " | " + method);
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOT_ALLOWED);
		}
		
		switch (method) {
			case "GET": 
				if (!permission.isRead()) {
					log.error("Invalid access: " + tokenUser.getUuidAccount() + " to: "+ serviceKey + " | " + method);
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOT_ALLOWED);
				}
				break;
			case "POST": 
				if (!permission.isSave()) {
					log.error("Invalid access: " + tokenUser.getUuidAccount() + " to: "+ serviceKey + " | " + method);
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOT_ALLOWED);
				}
				break;
			case "PUT": 
				if (!permission.isUpdate()) {
					log.error("Invalid access: " + tokenUser.getUuidAccount() + " to: "+ serviceKey + " | " + method);
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOT_ALLOWED);
				}
				break;
			case "DELETE":
				if (!permission.isDelete()) {
					log.error("Invalid access: " + tokenUser.getUuidAccount() + " to: "+ serviceKey + " | " + method);
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOT_ALLOWED);
				}
				break;
			case "LIST":
				if (!permission.isList()) {
					log.error("Invalid access: " + tokenUser.getUuidAccount() + " to: "+ serviceKey + " | " + method);
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOT_ALLOWED);
				}
				break;
			case "FILE":
				if (!permission.isFile()) {
					log.error("Invalid access: " + tokenUser.getUuidAccount() + " to: "+ serviceKey + " | " + method);
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOT_ALLOWED);
				}
				break;
			default:
		}
		
		return user;
	}
}
