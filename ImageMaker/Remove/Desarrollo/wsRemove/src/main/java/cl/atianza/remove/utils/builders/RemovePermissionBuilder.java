package cl.atianza.remove.utils.builders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.api.admin.alerts.AdminAlertAndAlarmController;
import cl.atianza.remove.api.admin.clients.AdminClientAttemptController;
import cl.atianza.remove.api.admin.clients.AdminClientAuditController;
import cl.atianza.remove.api.admin.clients.AdminClientController;
import cl.atianza.remove.api.admin.clients.AdminClientPaymentController;
import cl.atianza.remove.api.admin.dashboards.AdminDashboardController;
import cl.atianza.remove.api.admin.deindexation.AdminDeindexationController;
import cl.atianza.remove.api.admin.deindexation.AdminDeindexationHistoricController;
import cl.atianza.remove.api.admin.deindexation.AdminDeindexationStatusController;
import cl.atianza.remove.api.admin.deindexation.AdminDeindexationUrlController;
import cl.atianza.remove.api.admin.forbiddenwords.AdminForbiddenWordController;
import cl.atianza.remove.api.admin.healthcheck.AdminHealthCheckController;
import cl.atianza.remove.api.admin.plan.AdminPlanController;
import cl.atianza.remove.api.admin.plan.AdminPlanSuggestionController;
import cl.atianza.remove.api.admin.scanners.AdminScannerController;
import cl.atianza.remove.api.admin.scanners.comment.AdminScannerCommentController;
import cl.atianza.remove.api.admin.scanners.impulses.AdminImpulseApproverController;
import cl.atianza.remove.api.admin.scanners.impulses.AdminImpulseController;
import cl.atianza.remove.api.admin.scanners.impulses.AdminImpulsePublishController;
import cl.atianza.remove.api.admin.scanners.impulses.AdminImpulseVariableController;
import cl.atianza.remove.api.admin.scanners.snippets.AdminScannerSnippetController;
import cl.atianza.remove.api.admin.trackingwords.AdminTrackingWordController;
import cl.atianza.remove.api.admin.users.AdminUserAuditController;
import cl.atianza.remove.api.admin.users.AdminUserController;
import cl.atianza.remove.api.client.alerts.ClientAlertAndAlarmController;
import cl.atianza.remove.api.client.scanners.ClientScannerController;
import cl.atianza.remove.api.client.scanners.comment.ClientScannerCommentController;
import cl.atianza.remove.api.client.scanners.impulses.ClientImpulseApproverController;
import cl.atianza.remove.api.client.scanners.impulses.ClientImpulseController;
import cl.atianza.remove.api.client.scanners.snippets.ClientScannerSnippetController;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.views.ProfileAccessModel;

/**
 * 
 * @author pilin
 *
 */
public class RemovePermissionBuilder {
	private static Logger log = LogManager.getLogger(RemovePermissionBuilder.class);
	
	public static RemovePermissionBuilder init() {
		return new RemovePermissionBuilder();
	}
	
	/**
	 * 
	 * @param client
	 * @return
	 */
	public ProfileAccessModel buildByClient(Client client) {
		ProfileAccessModel accessModel = new ProfileAccessModel();
		accessModel.setProfile(EProfiles.CLIENT);
		
		accessModel.addAccess(new ClientScannerController(), true, true, true, true, true, false);
		accessModel.addAccess(new ClientScannerSnippetController(), false, false, true, false, false, false);
		accessModel.addAccess(new ClientImpulseController(), false, true, true, false, true, false);
		accessModel.addAccess(new ClientImpulseApproverController(), false, true, false, false, false, false);
		accessModel.addAccess(new ClientScannerCommentController(),true,false,false,false,true,false);
		accessModel.addAccess(new ClientAlertAndAlarmController(), true, true, true, true, true, true);
		
		client.getPlanActives().forEach(planActive -> {
			if (planActive.getDetail().isAccess_scanner()) {
				accessModel.addAccess("ONE_SHOT", true, true, true, true, true, false);	
			}
			if (planActive.getDetail().isAccess_monitor()) {
				accessModel.addAccess("RECURRENT", true, true, true, true, true, false);	
			}
			if (planActive.getDetail().isAccess_transforma()) {
				accessModel.addAccess("TRANSFORM", true, true, true, true, true, false);	
				accessModel.addAccess("IMPULSE", false, true, true, false, true, false);	
			}
			if (planActive.getDetail().isAccess_deindexation()) {
				accessModel.addAccess("DEINDEXATION", true, true, true, false, true, true);
			}
		});
		
		return accessModel;
	}
	
	/**
	 * 
	 * @param profileCode
	 * @return
	 */
	public ProfileAccessModel buildByProfile(String profileCode) {
		ProfileAccessModel accessModel = new ProfileAccessModel();
		accessModel.setProfile(EProfiles.obtain(profileCode));
		buildByProfile(accessModel);
		 
		return accessModel;
	}

	/**
	 * 
	 * @param accessModel
	 */
	private void buildByProfile(ProfileAccessModel accessModel) {
		switch (accessModel.getProfile()) {
			case ADMIN:
				buildAsAdmin(accessModel);
				break;
			case MANAGER:
				buildAsManager(accessModel);
				break;
			case EDITOR:
				buildAsEditor(accessModel);
				break;
			case FORMULE:
				buildAsFormula(accessModel);
				break;
			case FACTURATION:
				buildAsFacturation(accessModel);
				break;
			default:
				log.error("Invalid Profile: ", accessModel.getProfile());
		}
	}

	/**
	 * 
	 * @param accessModel
	 */
	private void buildAsAdmin(ProfileAccessModel accessModel) {
		//Dashboard OK
		accessModel.addAccess(new AdminDashboardController(), true, false, false, false, true, false); 
		
		//Usuarios OK
		accessModel.addAccess(new AdminUserController(), true, true, true, true, true, false);
		accessModel.addAccess(new AdminUserAuditController(), false, false, false, false, true, false);
		
		//Clientes OK
		accessModel.addAccess(new AdminClientController(), true, true, true, true, true, false);
		accessModel.addAccess(new AdminClientPaymentController(), false, false, true, false, false, false);
		accessModel.addAccess(new AdminClientAuditController(), false, false, false, false, true, false);
		accessModel.addAccess(new AdminClientAttemptController(), false, false, false, false, true, false);
		
		//Planes OK
		accessModel.addAccess(new AdminPlanController(), true, true, true, true, true, false);
		accessModel.addAccess(new AdminPlanSuggestionController(), true, false, true, false, false, false);
		
		//Palabras de Rastreo OK
		accessModel.addAccess(new AdminTrackingWordController(), false, true, true, false, true, true);
		
		//Palabras prohibidas OK
		accessModel.addAccess(new AdminForbiddenWordController(), true, true, true, true, true, false);
		
		//Scanner OK
		accessModel.addAccess(new AdminScannerController(), true, true, true, true, true, false);		
		accessModel.addAccess(new AdminScannerSnippetController(), false, false, true, false, false, false);
		
		//Impulsos OK
		accessModel.addAccess(new AdminImpulseController(), false, true, true, false, true, false);
		accessModel.addAccess(new AdminImpulseApproverController(), false, true, true, false, false, false);
		accessModel.addAccess(new AdminImpulsePublishController(), false, true, true, false, false, false);
		accessModel.addAccess(new AdminImpulseVariableController(), true, true, true, false, false, false);
		
		//Desindexacion OK
		accessModel.addAccess(new AdminDeindexationController(), true, true, true, false, true, true);
		accessModel.addAccess(new AdminDeindexationHistoricController(), true, false, false, false, false, false);
		accessModel.addAccess(new AdminDeindexationStatusController(), false, true, false, false, false, false);
		accessModel.addAccess(new AdminDeindexationUrlController(), false, false, true, false, false, false);

		//Comentarios de Scanner OK
		accessModel.addAccess(new AdminScannerCommentController(), true, true, true, true, true, true);
		
		//Alertas de Scanner OK
		accessModel.addAccess(new AdminAlertAndAlarmController(), true, true, true, true, true, true);
	}

	/**
	 * 
	 * @param accessModel
	 */
	private void buildAsManager(ProfileAccessModel accessModel) {
		//Usuarios OK
		accessModel.addAccess(new AdminUserController(), true, false, false, false, true, false);
		accessModel.addAccess(new AdminUserAuditController(), false, false, false, false, true, false);
		
		//Clientes OK
		accessModel.addAccess(new AdminClientController(), true, true, true, true, true, false);
		accessModel.addAccess(new AdminClientPaymentController(), false, false, true, false, false, false);
		accessModel.addAccess(new AdminClientAuditController(), false, false, false, false, true, false);
		accessModel.addAccess(new AdminClientAttemptController(), false, false, false, false, true, false);
		
		//Planes OK
		accessModel.addAccess(new AdminPlanController(), true, false, false, false, true, false);
		accessModel.addAccess(new AdminPlanSuggestionController(), true, false, false, false, true, false);
		
		//Palabras de Rastreo OK
		accessModel.addAccess(new AdminTrackingWordController(), false, false, false, false, true, false);
		
		//Palabras prohibidas OK
		accessModel.addAccess(new AdminForbiddenWordController(), true, true, true, true, true, false);
		
		//Scanner OK
		accessModel.addAccess(new AdminScannerController(), true, true, true, true, true, false);		
		accessModel.addAccess(new AdminScannerSnippetController(), false, false, true, false, false, false);
		
		//Impulsos OK
		accessModel.addAccess(new AdminImpulseController(), false, true, true, false, true, false);
		accessModel.addAccess(new AdminImpulseApproverController(), false, true, true, false, false, false);
		accessModel.addAccess(new AdminImpulsePublishController(), false, true, true, false, false, false);
		accessModel.addAccess(new AdminImpulseVariableController(), true, true, true, false, false, false);
		
		//Desindexacion OK
		accessModel.addAccess(new AdminDeindexationController(), true, true, true, false, true, true);
		accessModel.addAccess(new AdminDeindexationHistoricController(), true, false, false, false, false, false);
		accessModel.addAccess(new AdminDeindexationStatusController(), false, true, false, false, false, false);
		accessModel.addAccess(new AdminDeindexationUrlController(), false, false, true, false, false, false);
		
		accessModel.addAccess(new AdminScannerCommentController(), true, true, true, true, true, true);
	}

	/**
	 * 
	 * @param accessModel
	 */
	private void buildAsEditor(ProfileAccessModel accessModel) {
		//Dashboard OK
		accessModel.addAccess(new AdminDashboardController(), true, false, false, false, true, false);
		
		//Clientes OK
		accessModel.addAccess(new AdminClientController(), true, true, true, false, true, false);
		accessModel.addAccess(new AdminClientPaymentController(), false, false, false, false, true, false);
		accessModel.addAccess(new AdminClientAuditController(), true, false, false, false, true, false);
		accessModel.addAccess(new AdminClientAttemptController(), true, false, false, false, true, false);
		
		//Palabras prohibidas OK
		accessModel.addAccess(new AdminForbiddenWordController(), false, false, false, false, true, false);
		
		//Scanner OK 
		accessModel.addAccess(new AdminScannerController(), true, true, true, true, true, false);		
		accessModel.addAccess(new AdminScannerSnippetController(), false, false, true, false, false, false);
		
		
		//Impulsos OK
		accessModel.addAccess(new AdminImpulseController(), false, true, true, false, true, false);
		accessModel.addAccess(new AdminImpulseApproverController(), false, true, true, false, false, false);
		accessModel.addAccess(new AdminImpulsePublishController(), false, true, true, false, false, false);
		accessModel.addAccess(new AdminImpulseVariableController(), true, true, true, false, false, false);
		
		//Desindexacion OK
		accessModel.addAccess(new AdminDeindexationController(), true, true, true, false, true, true);
		accessModel.addAccess(new AdminDeindexationHistoricController(), true, false, false, false, false, false);
		accessModel.addAccess(new AdminDeindexationStatusController(), false, true, false, false, false, false);
		accessModel.addAccess(new AdminDeindexationUrlController(), false, false, true, false, false, false);
		
		accessModel.addAccess(new AdminScannerCommentController(), false, false, false, false, false, false);
	}

	/**
	 * 
	 * @param accessModel
	 */
	private void buildAsFormula(ProfileAccessModel accessModel) {
		//Dashboard OK
		accessModel.addAccess(new AdminDashboardController(), true, false, false, false, true, false);
		
		//Clientes OK
		accessModel.addAccess(new AdminClientController(), true, false, false, false, true, false);
		
		//Scanner OK
		accessModel.addAccess(new AdminScannerController(), true, false, false, false, true, false);
		
		//Impulsos OK
		accessModel.addAccess(new AdminImpulseController(), false, true, true, false, true, false);
		accessModel.addAccess(new AdminImpulseApproverController(), false, true, true, false, false, false);
		accessModel.addAccess(new AdminImpulsePublishController(), false, true, true, false, false, false);
		accessModel.addAccess(new AdminImpulseVariableController(), true, true, true, false, false, false);
		
		accessModel.addAccess(new AdminScannerCommentController(), false, false, false, false, false, false);
	}

	/**
	 * 
	 * @param accessModel
	 */
	private void buildAsFacturation(ProfileAccessModel accessModel) {
		// TODO Build FACTURATION perfil access
		
	}
}
