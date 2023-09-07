package cl.atianza.remove.api.client.deindexation;

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
public class ClientDeindexationHistoricController extends RestController {
	public ClientDeindexationHistoricController() {
		super(ERestPath.CLIENT_DEINDEXATION_HISTORIC, LogManager.getLogger(ClientDeindexationHistoricController.class));
	}

	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			String uuid = RemoveRequestUtil.getParamString(request, EWebParam.UUID);
			Deindexation deindexation = DeindexationDao.init().getBasicByUuid(uuid);
			
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
			DeindexationValidator.init(client).validateClientDeindexation(deindexation);
			
			return RemoveResponseUtil.buildOk(DeindexationHistoricDao.init().listByDeindexation(deindexation.getId()));
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
