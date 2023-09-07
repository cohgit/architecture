package cl.atianza.remove.api.admin.deindexation;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.commons.DeindexationDao;
import cl.atianza.remove.daos.commons.DeindexationUrlDao;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.Deindexation;
import cl.atianza.remove.models.commons.DeindexationUrl;
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
public class AdminDeindexationUrlController extends RestController {
	public AdminDeindexationUrlController() {
		super(ERestPath.ADMIN_DEINDEXATION_URL, LogManager.getLogger(AdminDeindexationUrlController.class));
	}

	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			DeindexationUrl url = (DeindexationUrl) RemoveRequestUtil.getBodyObject(request, DeindexationUrl.class);
			Deindexation deindex = DeindexationDao.init().getBasicById(url.getId_deindexation());
			AccessToClientDataValidator.init(token).validateAccessOperation(deindex.getId_owner());
			
			DeindexationUrlDao dao = DeindexationUrlDao.init();
			DeindexationUrl oldUrl = dao.getById(url.getId());
			
			getLog().info("Evaluating url: " + url);
			
			if (oldUrl.isAsk_google() != null && oldUrl.isAsk_google().booleanValue() && 
					oldUrl.isSent_to_google() != null && !oldUrl.isSent_to_google().booleanValue() && 
					url.isSent_to_google() != null && url.isSent_to_google().booleanValue()) {
				getLog().info("Updating sent google for: " + url);
				dao.updateSentGoogle(url.getId(), url.isSent_to_google(), url.getSent_to_google_date());
			}
			if (oldUrl.isAsk_media() != null && oldUrl.isAsk_media() && 
					oldUrl.isSent_to_media() != null && !oldUrl.isSent_to_media().booleanValue() && 
					url.isSent_to_media() != null && url.isSent_to_media().booleanValue()) {
				getLog().info("Updating sent media for: " + url);
				dao.updateSentMedia(url.getId(), url.isSent_to_media(), url.getSent_to_media_date());
			}
			
			if (oldUrl.isGoogle_approved() == null && url.isGoogle_approved() != null) {
				getLog().info("Updating google response for: " + url);
				dao.updateMarkGoogleResponse(url.getId(), url.isGoogle_approved());
			}
			if (oldUrl.isMedia_approved() == null && url.isMedia_approved() != null) {
				getLog().info("Updating media response for: " + url);
				dao.updateMarkMediaResponse(url.getId(), url.isMedia_approved());
			}
			
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
