package cl.atianza.remove.api.client.deindexation;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.commons.DeindexationDao;
import cl.atianza.remove.daos.commons.DeindexationHistoricDao;
import cl.atianza.remove.enums.EDeindexationStatus;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.views.DeindexationStatusChange;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.AccessToClientDataValidator;
import cl.atianza.remove.validators.ClientAccessValidator;
import cl.atianza.remove.validators.DeindexationValidator;
import spark.Request;
import spark.Response;

/**
 * REST deindexation service for deindexation operations.
 * @author pilin
 *
 */
@Deprecated
public class ClientDeindexationStatusController extends RestController {
	public ClientDeindexationStatusController() {
		super(ERestPath.CLIENT_DEINDEXATION_STATUS, LogManager.getLogger(ClientDeindexationStatusController.class));
	}

	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
			
			DeindexationStatusChange deindexationChange = (DeindexationStatusChange) RemoveRequestUtil.getBodyObject(request, DeindexationStatusChange.class);
			DeindexationValidator.init(client).validateClientDeindexation(deindexationChange.getIdDeindexation());
			
			if (deindexationChange.getStatus().equals(EDeindexationStatus.PROCESSING.getTag())) {
				DeindexationDao.init().calculateResults(deindexationChange.getIdDeindexation());
				DeindexationDao.init().updateStatus(deindexationChange.getIdDeindexation(), deindexationChange.getStatus());
				
				DeindexationHistoricDao.init().save(deindexationChange.getIdDeindexation(), deindexationChange.getStatus(), 
						client.getId(), EProfiles.CLIENT.getCode(), "message.deindexation.in.process");
				return RemoveResponseUtil.buildDefaultOk();	
			}

			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
