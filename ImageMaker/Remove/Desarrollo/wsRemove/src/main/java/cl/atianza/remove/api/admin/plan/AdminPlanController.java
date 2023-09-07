package cl.atianza.remove.api.admin.plan;

import java.util.List;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.PlanClientSuggestionDao;
import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.external.clients.payments.stripe.PaymentStripeClient;
import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.UserProfileAccessValidator;
import spark.Request;
import spark.Response;

/**
 * Plan manager REST service.
 * @author pilin
 *
 */
public class AdminPlanController extends RestController {
	public AdminPlanController() {
		super(ERestPath.ADMIN_PLANS, LogManager.getLogger(AdminPlanController.class));
	}

	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			Plan plan = PlanDao.init().get(RemoveRequestUtil.getParamLong(request, EWebParam.ID));
			
			if (plan != null) {
				plan.setClientSuggestions(PlanClientSuggestionDao.init().listForPlan(plan.getId()));				
			}
			
			return RemoveResponseUtil.buildOk(plan);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			Plan plan = (Plan) RemoveRequestUtil.getBodyObject(request, Plan.class);
			plan = PlanDao.init().save(plan);
			PaymentStripeClient.init().refreshProduct(plan);
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_PLAN_SAVED);
			
			return RemoveResponseUtil.buildOk(plan);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object list(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LIST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccessList(this);
			
			List<Plan> plans = PlanDao.init().listAll();
			ClientPlanDao clientDao = ClientPlanDao.init();
			
			if (plans != null && !plans.isEmpty()) {
				plans.forEach(plan -> {
					try {
						plan.setTotalActiveClients(clientDao.countClientsByPlans(plan.getId()));
					} catch (RemoveApplicationException e) {
						getLog().error("Error counting clients by plan: "+plan, e);
					}
				});
			}
			
			return RemoveResponseUtil.buildOk(plans);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			Plan plan = (Plan) RemoveRequestUtil.getBodyObject(request, Plan.class);
			plan = PlanDao.init().update(plan);
			PaymentStripeClient.init().refreshProduct(plan);
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_PLAN_UPDATED);
			return RemoveResponseUtil.buildOk(plan);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object delete(Request request, Response response) throws RemoveApplicationException {
		getLog().info("DELETE: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			Plan plan = PlanDao.init().switchActive(RemoveRequestUtil.getParamLong(request, EWebParam.ID));
			PaymentStripeClient.init().refreshProduct(plan);
			return RemoveResponseUtil.buildOk(plan);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
