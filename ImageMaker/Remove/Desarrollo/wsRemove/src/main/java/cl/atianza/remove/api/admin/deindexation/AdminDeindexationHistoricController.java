package cl.atianza.remove.api.admin.deindexation;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.commons.DeindexationDao;
import cl.atianza.remove.daos.commons.DeindexationHistoricDao;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Deindexation;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.AccessToClientDataValidator;
import cl.atianza.remove.validators.DeindexationValidator;
import cl.atianza.remove.validators.UserProfileAccessValidator;
import spark.Request;
import spark.Response;

/**
 * REST deindexation service for deindexation operations.
 * @author pilin
 *
 */
@Deprecated
public class AdminDeindexationHistoricController extends RestController {
	public AdminDeindexationHistoricController() {
		super(ERestPath.ADMIN_DEINDEXATION_HISTORIC, LogManager.getLogger(AdminDeindexationHistoricController.class));
	}
	
	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			String uuid = RemoveRequestUtil.getParamString(request, EWebParam.UUID);
			Deindexation deindexation = DeindexationDao.init().getBasicByUuid(uuid);
			
			Client client = AccessToClientDataValidator.init(token).validateAccessOperation(deindexation.getId_owner());
			DeindexationValidator.init(client).validateClientDeindexation(deindexation);
			
			return RemoveResponseUtil.buildOk(DeindexationHistoricDao.init().listByDeindexation(deindexation.getId()));
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
