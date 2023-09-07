package cl.atianza.remove.api.admin.deindexation;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.commons.DeindexationDao;
import cl.atianza.remove.daos.commons.DeindexationHistoricDao;
import cl.atianza.remove.enums.EDeindexationStatus;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.commons.Deindexation;
import cl.atianza.remove.models.views.DeindexationStatusChange;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.AccessToClientDataValidator;
import cl.atianza.remove.validators.UserProfileAccessValidator;
import spark.Request;
import spark.Response;

/**
 * REST deindexation service for deindexation operations.
 * @author pilin
 *
 */
@Deprecated
public class AdminDeindexationStatusController extends RestController {
	public AdminDeindexationStatusController() {
		super(ERestPath.ADMIN_DEINDEXATION_STATUS, LogManager.getLogger(AdminDeindexationStatusController.class));
	}

	/**
	 * 
	 */
	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			DeindexationStatusChange deindexationChange = (DeindexationStatusChange) RemoveRequestUtil.getBodyObject(request, DeindexationStatusChange.class);
			Deindexation deindexation = DeindexationDao.init().getBasicById(deindexationChange.getIdDeindexation());
			
			AccessToClientDataValidator.init(token).validateAccessOperation(deindexation.getId_owner());
			
			String message = null;
			
			//TODO: Fix this
//			if (deindexationChange.getStatus().equals(EDeindexationStatus.SENT_GOOGLE.getTag())) {
//				message = "message.deindexation.sent.google";
//			} else if (deindexationChange.getStatus().equals(EDeindexationStatus.RESPONDED_GOOGLE.getTag())) {
//				message = "message.deindexation.responded.google";
//			} else if (deindexationChange.getStatus().equals(EDeindexationStatus.SENT_MEDIA.getTag())) {
//				message = "message.deindexation.sent.media";
//			} else if (deindexationChange.getStatus().equals(EDeindexationStatus.RESPONDED_MEDIA.getTag())) {
//				message = "message.deindexation.responded.media";
//			}
			if (deindexationChange.getStatus().equals(EDeindexationStatus.APPROVED.getTag())) {
				message = "message.deindexation.approved";
			} else if (deindexationChange.getStatus().equals(EDeindexationStatus.REJECTED.getTag())) {
				message = "message.deindexation.rejected";
			} else if (deindexationChange.getStatus().equals(EDeindexationStatus.DELETED.getTag())) {
				message = "message.deindexation.deleted";
			}
			
			if (message != null) {
				DeindexationDao.init().updateStatus(deindexationChange.getIdDeindexation(), deindexationChange.getStatus());
				
//				if (deindexationChange.getStatus().equals(EDeindexationStatus.SENT_GOOGLE.getTag())) {
//					DeindexationDao.init().updateTrackingCode(deindexationChange.getIdDeindexation(), deindexationChange.getTrackingCode());
//				}
//				
				DeindexationHistoricDao.init().save(deindexationChange.getIdDeindexation(), deindexationChange.getStatus(), 
						user.getId(), user.getProfile(), message);
				return RemoveResponseUtil.buildDefaultOk();	
			}
			
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
