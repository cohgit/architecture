package cl.atianza.remove.api.client.scanners.impulses;

import java.util.List;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerImpulseContentDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ImpulseControllerHelper;
import cl.atianza.remove.helpers.UserNotificationHelper;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.models.commons.ScannerImpulseContent;
import cl.atianza.remove.models.commons.ScannerImpulseObservation;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.ClientAccessValidator;
import spark.Request;
import spark.Response;

/**
 * Service to approve impulse publications.
 * @author pilin
 */
public class ClientImpulseApproverController extends RestController {
	public ClientImpulseApproverController() {
		super(ERestPath.CLIENT_IMPULSE_APPROVE, LogManager.getLogger(ClientImpulseApproverController.class));
	}
	
	/**
	 * This service count as Waiting to be Approved
	 */
	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			ClientAccessValidator validator = ClientAccessValidator.init(token);
			Client client = validator.validateClientAccess();
			validator.validateReadOnly();
			
			ScannerImpulseObservation observation = (ScannerImpulseObservation) RemoveRequestUtil.getBodyObject(request, ScannerImpulseObservation.class);
			
			Object resp = ImpulseControllerHelper.init(getLog()).processAproverFlow(client, token, observation);
			
			ScannerImpulseContent content = ScannerImpulseContentDao.init().getById(observation.getId_scanner_impulse_content());
			ScannerImpulse impulse = ScannerImpulseDao.init().get(content.getId_scanner_impulse());
			Scanner scanner = ScannerDao.init().getBasicById(impulse.getId_scanner());
			
			List<User> lstUsers = UserDao.init().listByClientRelation(scanner.getId_owner());
			
			if (lstUsers != null) {
				if (observation.esToBeApproved()) {
					lstUsers.forEach(user -> {
						try {
							UserNotificationHelper.init(user).createTransformImpulseWaitingApproveNotification(scanner.getUuid(), client, impulse);
						} catch (RemoveApplicationException e) {
							getLog().error("Error sending notification: ", e);
						}
					});
					ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_TO_BE_APPROVED);
				}

				if (observation.esApproved()) {
					lstUsers.forEach(user -> {
						try {
							UserNotificationHelper.init(user).createTransformImpulseApprovedNotification(scanner.getUuid(), client, impulse);
						} catch (RemoveApplicationException e) {
							getLog().error("Error sending notification: ", e);
						}
					});
					ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_APPROVED);
				}
					
				if (observation.esRejected()) {
					lstUsers.forEach(user -> {
						try {
							UserNotificationHelper.init(user).createTransformImpulseRejectedNotification(scanner.getUuid(), client, impulse);
						} catch (RemoveApplicationException e) {
							getLog().error("Error sending notification: ", e);
						}
					});
					ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_REJECTED);
				}
			}
			
			return resp;
		} catch (Throwable t) {
			return catchException(t);
		}  
	}
	
}
