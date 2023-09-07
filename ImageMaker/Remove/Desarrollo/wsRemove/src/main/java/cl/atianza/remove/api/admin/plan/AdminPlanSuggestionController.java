package cl.atianza.remove.api.admin.plan;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.PlanClientSuggestionDao;
import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.EmailHelper;
import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.admin.PlanClientSuggestion;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RestController;
import spark.Request;
import spark.Response;

/**
 * Service for plan suggestion to clients.
 * @author pilin
 */
public class AdminPlanSuggestionController extends RestController {
	public AdminPlanSuggestionController() {
		super(ERestPath.ADMIN_PLANS_SUGGESTIONS, LogManager.getLogger(AdminPlanSuggestionController.class));
	}

	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			Plan plan = (Plan) RemoveRequestUtil.getBodyObject(request, Plan.class);
			
			if (plan.getClientSuggestions().isEmpty()) {
				getLog().info("Plan doesn't have client suggestions: ");
				RemoveResponseUtil.buildDefaultError();
			}
			
			PlanClientSuggestionDao.init().upsert(plan);
			
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			String uuidSuscriptions = RemoveRequestUtil.getParamString(request, EWebParam.UUID);
			
			String[] uuids = uuidSuscriptions.split(",");
			
			for (String uuid: uuids) {
				PlanClientSuggestion suggestion = PlanClientSuggestionDao.init().getByUuid(uuid);
				
				if (suggestion != null) {
					suggestion.setPlan(PlanDao.init().get(suggestion.getId_plan()));
					EmailHelper.init().sendSuggestionPlanMail(suggestion);					
				} else {
					getLog().error("Suggestion not found: " + uuid);
				}
			}
			
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
