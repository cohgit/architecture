package cl.atianza.remove.api.admin.scanners.impulses;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerImpulseContentDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ClientNotificationHelper;
import cl.atianza.remove.helpers.ImpulseControllerHelper;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.models.commons.ScannerImpulseContent;
import cl.atianza.remove.models.commons.ScannerImpulseObservation;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.AccessToClientDataValidator;
import cl.atianza.remove.validators.UserAccessValidator;
import spark.Request;
import spark.Response;

/**
 * Service to approve impulse publications.
 * @author pilin
 */
public class AdminImpulseApproverController extends RestController {
	public AdminImpulseApproverController() {
		super(ERestPath.ADMIN_IMPULSE_APPROVE, LogManager.getLogger(AdminImpulseApproverController.class));
	}
	
	/**
	 * This service count as Waiting to be Approved
	 */
	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserAccessValidator.init(token).validateUserAccess();
			
			ScannerImpulseObservation observation = (ScannerImpulseObservation) RemoveRequestUtil.getBodyObject(request, ScannerImpulseObservation.class);
			Client client = AccessToClientDataValidator.init(token).validateAccessOperation(observation, true);
			
			String resp = ImpulseControllerHelper.init(getLog()).processAproverFlow(client, token, observation);
			
			ScannerImpulseContent content = ScannerImpulseContentDao.init().getById(observation.getId_scanner_impulse_content());
			ScannerImpulse impulse = ScannerImpulseDao.init().get(content.getId_scanner_impulse());
			Scanner scanner = ScannerDao.init().getBasicById(impulse.getId_scanner());
			
			if (observation.esToBeApproved()) {
				ClientNotificationHelper.init(ClientDao.init().getById(scanner.getId_owner())).createTransformImpulseWaitingApproveNotification(scanner.getUuid(), impulse);
				UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_TO_BE_APPROVED);
				ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_TO_BE_APPROVED);
			}
				
			if (observation.esApproved()) {
				ClientNotificationHelper.init(ClientDao.init().getById(scanner.getId_owner())).createTransformImpulseApprovedNotification(scanner.getUuid(), impulse);
				UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_APPROVED);
				ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_APPROVED);
			}
				
			if (observation.esRejected()) {
				ClientNotificationHelper.init(ClientDao.init().getById(scanner.getId_owner())).createTransformImpulseRejectedNotification(scanner.getUuid(), impulse);
				UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_REJECTED);
				ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_REJECTED);
			}
			
			return resp;
		} catch (Throwable t) {
			return catchException(t);
		}  
	}
}
