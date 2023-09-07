package cl.atianza.remove.api.xternal.password;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPasswordChangeRequestDao;
import cl.atianza.remove.daos.client.ClientPasswordDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.helpers.ClientNotificationHelper;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPassword;
import cl.atianza.remove.models.client.ClientPasswordChangeRequest;
import cl.atianza.remove.models.views.NewPassword;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RestController;
import spark.Request;
import spark.Response;

/**
 * Service used for change clients password (forgot your password option)
 * @author pilin
 *
 */
public class XternalClientPasswordController extends RestController {
	public XternalClientPasswordController() {
		super(ERestPath.XTERNAL_CLIENT_PASSWORD, LogManager.getLogger(XternalClientPasswordController.class));
	}
	

	/**
	 * Count as solitude for updating password (Send email with update link)
	 */
	@Override
	public Object delete(Request request, Response response) throws RemoveApplicationException {
		getLog().info("DELETE: " + this.getClass());

		try {
			String email = RemoveRequestUtil.getParamString(request, EWebParam.EMAIL);
			
			Client client = ClientDao.init().getByLogin(email);
			
			if (client == null || !client.isActive()) {
				RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOT_FOUND);
			}
			
			ClientPasswordChangeRequest change = ClientPasswordChangeRequestDao.init().createRequest(client);
			
			ClientNotificationHelper.init(client).createRecoveryPasswordSolitude(change.getUuid());
			ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_PASSWORD_CHANGE_REQUESTED);
			
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
			ClientPasswordChangeRequest change = ClientPasswordChangeRequestDao.init().get(newPassword.getUuidRequest());
			
			if (isValid(change)) {
				Long clientId = saveNewPassword(change, newPassword);
				
				ClientAuditAccessDao.init().saveForClient(clientId, EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_PASSWORD_UPDATED);
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
			getLog().info("Checking password request: " + uuidRequest);
			ClientPasswordChangeRequest change = ClientPasswordChangeRequestDao.init().get(uuidRequest);
			getLog().info("Password Request: " + change);
			if (isValid(change)) {
				return RemoveResponseUtil.buildDefaultOk();
			}
		} catch (Throwable t) {
			return catchException(t);
		} 
		
		return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_RESET_PASSWORD_INVALID);
	}

	private boolean isValid(ClientPasswordChangeRequest change) {
		return change != null && !change.isConfirmed() && !change.expired();
	}
	/**
	 * 
	 * @param change
	 * @param newPassword
	 * @throws RemoveApplicationException
	 */
	private long saveNewPassword(ClientPasswordChangeRequest change, NewPassword newPassword) throws RemoveApplicationException {
		ClientPassword password = new ClientPassword();
		
		password.setActive(true);
		password.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		password.setId_owner(change.getId_client());
		password.setPassword(newPassword.getNewPassword());
		
		ClientPasswordDao.init().save(password);
		ClientPasswordChangeRequestDao.init().confirmChange(change);
		
		return password.getId_owner();
	}
}
