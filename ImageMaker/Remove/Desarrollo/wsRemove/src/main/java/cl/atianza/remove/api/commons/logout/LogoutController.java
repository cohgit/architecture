package cl.atianza.remove.api.commons.logout;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.admin.UserSessionDao;
import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientSessionDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import spark.Request;
import spark.Response;

/**
 * Logout service. Used as user system or client system.
 * @author pilin
 *
 */
public class LogoutController extends RestController {
	public LogoutController() {
		super(ERestPath.LOGOUT, LogManager.getLogger(LogoutController.class));
	}
	
	
	@Override
	public Object delete(Request request, Response response) throws RemoveApplicationException {
		getLog().info("DELETE: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			
			UserSessionDao.init().close(token.getToken());
			ClientSessionDao.init().close(token.getToken());
			
			if (token.getProfile().equals(EProfiles.CLIENT.getCode())) {
				Client client = ClientDao.init().getBasicByUuid(token.getUuidAccount());
				ClientAuditAccessDao.init().save(client.getId(), client.getId(), EProfiles.CLIENT.getCode(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_LOGOUT);	
			} else {
				User user = UserDao.init().getBasicByUuid(token.getUuidAccount());
				UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_LOGOUT);	
			}
			
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
