package cl.atianza.remove.api.client.scanners.impulses;

import java.util.List;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ImpulseControllerHelper;
import cl.atianza.remove.helpers.UserNotificationHelper;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.ClientAccessValidator;
import spark.Request;
import spark.Response;

/**
 * REST service for impulse management.
 * @author pilin
 *
 */
public class ClientImpulseController extends RestController {
	public ClientImpulseController() {
		super(ERestPath.CLIENT_IMPULSE, LogManager.getLogger(ClientImpulseController.class));
	}
	
	@Override
	public Object list(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LIST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			Client client = ClientAccessValidator.init(token).validateClientAccess();
			String uuidScanner = RemoveRequestUtil.getParamString(request, EWebParam.UUID_SCANNER);
			
			return ImpulseControllerHelper.init(getLog()).processListImpulses(uuidScanner, client, client.getPlanActives().get(0).getId(), client.getId(), EProfiles.CLIENT.getCode());
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			ClientAccessValidator validator = ClientAccessValidator.init(token);
			Client client = validator.validateClientAccess();
			validator.validateReadOnly();
			
			ScannerImpulse impulse = (ScannerImpulse) RemoveRequestUtil.getBodyObject(request, ScannerImpulse.class);
			
			impulse.setIdClientPlan(client.getPlanActives().get(0).getId());
			impulse.setCreator_profile(EProfiles.CLIENT.getCode());
			impulse.setId_creator(client.getId());
			impulse.setType(impulse.getTypeObj().getTag());
			
			String resp = ImpulseControllerHelper.init(getLog()).processSaveImpulse(client, impulse, impulse.getIdClientPlan(), token); 
			
			if (impulse.esWordingRequest()) {
				Scanner scanner = ScannerDao.init().getBasicByUuid(impulse.getUuidScanner(), EScannerTypes.TRANSFORM.getCode());
				
				List<User> lstUsers = UserDao.init().listByClientRelation(scanner.getId_owner());
				
				if (lstUsers != null) {
					lstUsers.forEach(user -> {
						try {
							UserNotificationHelper.init(user).createTransformImpulseAwaitingWordingNotification(scanner.getUuid(), client, impulse);
						} catch (RemoveApplicationException e) {
							getLog().error("Error sending notification: ", e);
						}
					});
				}
			}
			
			if (resp == null) {
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
			}
			
			ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_CREATED);	
			
			return resp;
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			ScannerImpulse impulse = (ScannerImpulse) RemoveRequestUtil.getBodyObject(request, ScannerImpulse.class);
			ClientAccessValidator validator = ClientAccessValidator.init(token);
			Client client = validator.validateClientAccess();
			validator.validateReadOnly();
			
			return ImpulseControllerHelper.init(getLog()).processUpdateImpulse(client, impulse, impulse.getIdClientPlan(), client.getId(), EProfiles.CLIENT.getCode());
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
