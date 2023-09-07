package cl.atianza.remove.api.admin.deindexation;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.commons.DeindexationDao;
import cl.atianza.remove.daos.commons.DeindexationUrlDao;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Deindexation;
import cl.atianza.remove.models.views.ListDeindaxations;
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
public class AdminDeindexationController extends RestController {
	public AdminDeindexationController() {
		super(ERestPath.ADMIN_DEINDEXATION, LogManager.getLogger(AdminDeindexationController.class));
	}

	@Override
	public Object list(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LIST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			String uuidClient = RemoveRequestUtil.getParamString(request, EWebParam.UUID_CLIENT);
			Client client = AccessToClientDataValidator.init(token).validateAccessOperation(uuidClient);
			
			ListDeindaxations table = new ListDeindaxations();
			table.setList(DeindexationDao.init().listByOwner(client.getId()));
			
			Integer urlOcupieds = DeindexationUrlDao.init().countByPlan(client.getPlanActives().get(0).getId());
			Plan plan = PlanDao.init().get(client.getPlanActives().get(0).getId_plan());
			table.setUrlsAvailables(plan.getMax_url_to_deindexate().intValue() - urlOcupieds.intValue());
			
			return RemoveResponseUtil.buildOk(table);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());
		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			String uuidClient = RemoveRequestUtil.getParamString(request, EWebParam.UUID_CLIENT);
			String uuidDeindexation = RemoveRequestUtil.getOptionalParamString(request, EWebParam.UUID);
			
			getLog().info("Loading deindexation: " + uuidDeindexation);
			
			Client client = AccessToClientDataValidator.init(token).validateAccessOperation(uuidClient);
			
			Deindexation deindexation = uuidDeindexation != null && !uuidDeindexation.isEmpty() ? 
					DeindexationDao.init().getByUuid(uuidDeindexation, client.getLanguage()) : DeindexationDao.init().getTemplate(client.getLanguage());
					
			if (!deindexation.esNuevo()) {
				DeindexationValidator.init(client).validateClientDeindexation(deindexation);	
			}

			DeindexationDao.init().loadResults(deindexation);
			deindexation.fixLabelsAndDescriptions(user.getLanguage());
			deindexation.splitResultsInTypes(user.getLanguage());
			
			Integer urlOcupieds = DeindexationUrlDao.init().countByPlan(client.getPlanActives().get(0).getId());
			Plan plan = PlanDao.init().get(client.getPlanActives().get(0).getId_plan());
			deindexation.setMaxUrlsAvailables(plan.getMax_url_to_deindexate().intValue() - urlOcupieds.intValue());
			
			return RemoveResponseUtil.buildOk(deindexation);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
