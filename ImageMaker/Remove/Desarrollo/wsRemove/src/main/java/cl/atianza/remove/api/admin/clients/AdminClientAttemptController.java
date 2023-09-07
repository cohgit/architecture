package cl.atianza.remove.api.admin.clients;

import java.util.List;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.client.ClientSuscriptionAttemptDao;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.ClientSuscriptionAttempt;
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
@Deprecated
public class AdminClientAttemptController extends RestController {
	public AdminClientAttemptController() {
		super(ERestPath.ADMIN_CLIENTS_ATTEMPT, LogManager.getLogger(AdminClientAttemptController.class));
	}

	@Override
	public Object list(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LIST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccessList(this);

			List<ClientSuscriptionAttempt> list = ClientSuscriptionAttemptDao.init().listAll();
			
			if (list != null) {
				list.forEach(attempt -> {
					try {
						attempt.setPlan(PlanDao.init().get(attempt.getId_plan()));
					} catch (RemoveApplicationException e) {
						getLog().error("Error loading plan: " + attempt);
					}
				});
			}

			return RemoveResponseUtil.buildOk(list);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
