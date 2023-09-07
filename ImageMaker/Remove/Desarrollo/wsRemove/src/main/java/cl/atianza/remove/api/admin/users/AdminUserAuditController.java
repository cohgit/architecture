package cl.atianza.remove.api.admin.users;


import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
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
public class AdminUserAuditController extends RestController {
	public AdminUserAuditController() {
		super(ERestPath.ADMIN_USERS_AUDIT, LogManager.getLogger(AdminUserAuditController.class));
	}

	@Override
	public Object list(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LIST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccessList(this);
			
			String uuidUser = RemoveRequestUtil.getParamString(request, EWebParam.UUID);
			return RemoveResponseUtil.buildOk(UserAuditAccessDao.init().listByUuid(uuidUser));
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
