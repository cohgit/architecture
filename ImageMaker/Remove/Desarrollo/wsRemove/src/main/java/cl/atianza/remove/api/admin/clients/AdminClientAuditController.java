package cl.atianza.remove.api.admin.clients;


import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.UserProfileAccessValidator;
import spark.Request;
import spark.Response;

/**
 * Client account REST services for management.
 * @author pilin
 *
 */
public class AdminClientAuditController extends RestController {
	public AdminClientAuditController() {
		super(ERestPath.ADMIN_CLIENTS_AUDIT, LogManager.getLogger(AdminClientAuditController.class));
	}

	@Override
	public Object list(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LIST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccessList(this);
			
			String uuidClient = RemoveRequestUtil.getParamString(request, EWebParam.UUID_CLIENT);
			return RemoveResponseUtil.buildOk(ClientAuditAccessDao.init().listByIdClient(uuidClient));
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
