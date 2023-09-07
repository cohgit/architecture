package cl.atianza.remove.api.admin.scanners.impulses;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ClientNotificationHelper;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.AccessToClientDataValidator;
import spark.Request;
import spark.Response;

/**
 * Service to mark as "published" impulse publications.
 * @author pilin
 *
 */
public class AdminImpulsePublishController extends RestController {
	public AdminImpulsePublishController() {
		super(ERestPath.ADMIN_IMPULSE_PUBLISH, LogManager.getLogger(AdminImpulsePublishController.class));
	}
	
	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			ScannerImpulse impulse = (ScannerImpulse) RemoveRequestUtil.getBodyObject(request, ScannerImpulse.class);
			AccessToClientDataValidator.init(token).validateAccessOperation(impulse, true);
			
			ScannerImpulseDao.init().updateEstimatedPublishDate(impulse.getEstimated_publish(), impulse.getId());
			
			return RemoveResponseUtil.buildOk(impulse);
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
			Client client = AccessToClientDataValidator.init(token).validateAccessOperation(impulse, true);
			User user = UserDao.init().getByUuid(token.getUuidAccount());
			
			impulse = ScannerImpulseDao.init().updatePublishData(token, impulse);
			
			Scanner scanner = ScannerDao.init().getBasicById(impulse.getId_scanner());
			ClientNotificationHelper.init(ClientDao.init().getById(scanner.getId_owner())).createTransformImpulsePublishedNotification(scanner.getUuid(), impulse);
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_PUBLISHED);
			ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_PUBLISHED);
			
			return RemoveResponseUtil.buildOk(impulse);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
