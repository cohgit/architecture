package cl.atianza.remove.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.admin.UserAlertMessageDao;
import cl.atianza.remove.daos.commons.ConfigurationDao;
import cl.atianza.remove.enums.EUserAlertMessageTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.admin.UserAlertMessage;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.models.views.ClientSuscription;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveJsonUtil;

/**
 * 
 * @author Quiliano Gutierrez
 *
 */
public class UserNotificationHelper {
	private Logger log = LogManager.getLogger(UserNotificationHelper.class);
	private User user;
	private ConfigurationDao configDao;
	
	public UserNotificationHelper(User user) throws RemoveApplicationException {
		super();
		this.user = user;
		this.configDao = ConfigurationDao.init();
	}

	public static UserNotificationHelper init(User user) throws RemoveApplicationException {
		return new UserNotificationHelper(user);
	}
	
	public void createVerifyEmailNotification(String reqUuid) throws RemoveApplicationException {
		try {
			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{VERIFICATION_EMAIL_URL}}", configDao.getFrontEndDomainBase() + configDao.getEmailParamUserVerificationEmailUrlPage() + reqUuid);
			createNotification(new UserAlertMessage(user.getId(), new HashMap<String, Object>()), EUserAlertMessageTypes.VERIFY_MAIL_SOLITUDE, tokens);
		} catch (RemoveApplicationException|Exception ex) {
			log.error("Error creating 'createVerifyEmailNotification': ", ex);
		}
	}
	public void createVerifiedEmailNotification() {
		try {
			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{USER_LOGIN_URL}}", configDao.getFrontEndDomainBase() + configDao.getEmailParamUserLoginUrlPage());
			createNotification(new UserAlertMessage(user.getId(), new HashMap<String, Object>()), EUserAlertMessageTypes.VERIFY_MAIL_SUCCESSFUL, tokens);
		} catch (RemoveApplicationException|Exception ex) {
			log.error("Error creating 'createVerifyEmailNotification': ", ex);
		}
	}
	public void createRecoveryPasswordNotification(String uuidChange) throws RemoveApplicationException {
		try {
			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{RECOVERY_PASSWORD_URL}}", configDao.getFrontEndDomainBase() + configDao.getEmailParamUserRecoveryPasswordUrlPage() + uuidChange);
			createNotification(new UserAlertMessage(user.getId(), new HashMap<String, Object>()), EUserAlertMessageTypes.RECOVERY_PASSWORD_SOLITUDE, tokens);
		} catch (RemoveApplicationException|Exception ex) {
			log.error("Error creating 'createRecoveryPasswordNotification': ", ex);
		}
	}
	public void createRecoveryPasswordSuccessNotification()  {
		try {
			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{USER_LOGIN_URL}}", configDao.getFrontEndDomainBase() + configDao.getEmailParamUserLoginUrlPage());
			createNotification(new UserAlertMessage(user.getId(), new HashMap<String, Object>()), EUserAlertMessageTypes.RECOVERY_PASSWORD_SUCCESSFULL, tokens);
		} catch (RemoveApplicationException|Exception ex) {
			log.error("Error creating 'createRecoveryPasswordNotification': ", ex);
		}
	}
	
	public void createNewClientsAssignedNotification(List<Client> clients) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("listNewClients", clients);
			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{USER_LOGIN_URL}}", configDao.getFrontEndDomainBase() + configDao.getEmailParamUserLoginUrlPage());
			
			createNotification(new UserAlertMessage(user.getId(), parameters), EUserAlertMessageTypes.NEW_CLIENTS_ASSIGNED, tokens);
		} catch (RemoveApplicationException|Exception ex) {
			log.error("Error creating 'createTransformImpulseWaitingApproveNotification': ", ex);
		}
	}
	public void createTransformImpulseAwaitingWordingNotification(String scannerUUID, Client client, ScannerImpulse impulse) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("uuidScanner", scannerUUID);
			parameters.put("estimated_publish", impulse.getEstimated_publish());
			parameters.put("keyword", impulse.getKeyword());
			parameters.put("reference_link", impulse.getReference_link());
			parameters.put("comments", impulse.getComments());
			parameters.put("client_name", client.getName() + " " + client.getLastname());
			parameters.put("clientUUID", client.getUuid());
			
			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{USER_LOGIN_URL}}", configDao.getFrontEndDomainBase() + "/#/admin/module/impulse/"+scannerUUID);// TODO: Mover path a configDao
			tokens.put("{{IMPULSE_TITTLE}}", impulse.getContent().getTitle());
			
			createNotification(new UserAlertMessage(user.getId(), parameters), EUserAlertMessageTypes.TRANSFORM_IMPULSE_AWAITING_WORDING, tokens);
		} catch (RemoveApplicationException|Exception ex) {
			log.error("Error creating 'createTransformImpulseWaitingApproveNotification': ", ex);
		}
	}
	public void createTransformImpulseWaitingApproveNotification(String scannerUUID, Client client, ScannerImpulse impulse) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("uuidScanner", scannerUUID);
			parameters.put("estimated_publish", impulse.getEstimated_publish());
			parameters.put("keyword", impulse.getKeyword());
			parameters.put("reference_link", impulse.getReference_link());
			parameters.put("client_name", client.getName() + " " + client.getLastname());
			parameters.put("clientUUID", client.getUuid());
			
			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{USER_LOGIN_URL}}", configDao.getFrontEndDomainBase()  + "/#/admin/module/impulse/"+scannerUUID);// TODO: Mover path a configDao
			tokens.put("{{IMPULSE_TITTLE}}", impulse.getContent().getTitle());
			
			createNotification(new UserAlertMessage(user.getId(), parameters), EUserAlertMessageTypes.TRANSFORM_IMPULSE_WAITING_APPROVE, tokens);
		} catch (RemoveApplicationException|Exception ex) {
			log.error("Error creating 'createTransformImpulseWaitingApproveNotification': ", ex);
		}
	}
	public void createTransformImpulseApprovedNotification(String scannerUUID, Client client, ScannerImpulse impulse) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("uuidScanner", scannerUUID);
			parameters.put("estimated_publish", impulse.getEstimated_publish());
			parameters.put("keyword", impulse.getKeyword());
			parameters.put("reference_link", impulse.getReference_link());
			parameters.put("client_name", client.getName() + " " + client.getLastname());
			parameters.put("clientUUID", client.getUuid());
			
			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{USER_LOGIN_URL}}", configDao.getFrontEndDomainBase() + "/#/admin/module/impulse/"+scannerUUID);// TODO: Mover path a configDao
			tokens.put("{{IMPULSE_TITTLE}}", impulse.getContent().getTitle());
			
			createNotification(new UserAlertMessage(user.getId(), parameters), EUserAlertMessageTypes.TRANSFORM_IMPULSE_APPROVED, tokens);
		} catch (RemoveApplicationException|Exception ex) {
			log.error("Error creating 'createTransformImpulseWaitingApproveNotification': ", ex);
		}
	}
	public void createTransformImpulseRejectedNotification(String scannerUUID, Client client, ScannerImpulse impulse) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("uuidScanner", scannerUUID);
			parameters.put("estimated_publish", impulse.getEstimated_publish());
			parameters.put("keyword", impulse.getKeyword());
			parameters.put("reference_link", impulse.getReference_link());
			parameters.put("client_name", client.getName() + " " + client.getLastname());
			parameters.put("clientUUID", client.getUuid());
			
			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{USER_LOGIN_URL}}", configDao.getFrontEndDomainBase() + "/#/admin/module/impulse/"+scannerUUID);// TODO: Mover path a configDao
			tokens.put("{{IMPULSE_TITTLE}}", impulse.getContent().getTitle());
			
			createNotification(new UserAlertMessage(user.getId(), parameters), EUserAlertMessageTypes.TRANSFORM_IMPULSE_REJECTED, tokens);
		} catch (RemoveApplicationException|Exception ex) {
			log.error("Error creating 'createTransformImpulseWaitingApproveNotification': ", ex);
		}
	}

	public void createPlanToExpireNotification(Client client) {
		try {
			if (client.getPlanActives() != null && !client.getPlanActives().isEmpty()) {
				Map<String, Object> parameters = new HashMap<String, Object>();
				
				parameters.put("expiration_date", client.getPlanActives().get(0).getExpiration_date());
				parameters.put("suscribe_date", client.getPlanActives().get(0).getSuscribe_date());
				parameters.put("planName", client.getPlanActives().get(0).getDetail().getName());
				parameters.put("client_name", client.getName() + " " + client.getLastname());
				parameters.put("clientUUID", client.getUuid());
				
				Map<String, String> tokens = new HashMap<String, String>();
				tokens.put("{{USER_LOGIN_URL}}", configDao.getFrontEndDomainBase() + configDao.getEmailParamUserLoginUrlPage());
				
				createNotification(new UserAlertMessage(user.getId(), parameters), EUserAlertMessageTypes.USER_EXPIRES_PLAN_CLIENT, tokens);	
			}
		} catch (RemoveApplicationException|Exception ex) {
			log.error("Error creating 'createTransformImpulseWaitingApproveNotification': ", ex);
		}
	}
	
	public void createNewSuscriptionNotification(ClientSuscription suscription) {
		try {
			if (suscription != null) {
				Map<String, Object> parameters = new HashMap<String, Object>();
				
				parameters.put("client_name", suscription.getClient().getName() + " " + suscription.getClient().getLastname());
				parameters.put("client_email", suscription.getClient().getEmail());
				parameters.put("payment_method", suscription.getPaymentMethod());
				parameters.put("plan", suscription.getPlan().getName());
				parameters.put("uuid", suscription.getUuidSuscription());
				
				Map<String, String> tokens = new HashMap<String, String>();
				tokens.put("{{USER_LOGIN_URL}}", configDao.getFrontEndDomainBase() + configDao.getEmailParamUserLoginUrlPage());
				tokens.put("{{CLIENT_NAME}}", suscription.getClient().getName() + " " + suscription.getClient().getLastname());
				tokens.put("{{CLIENT_EMAIL}}", suscription.getClient().getEmail());
				tokens.put("{{CLIENT_PAYMENT}}", suscription.getPaymentMethod());
				tokens.put("{{CLIENT_PLAN}}", suscription.getPlan().getName());
				
				createNotification(new UserAlertMessage(user.getId(), parameters), EUserAlertMessageTypes.NEW_CLIENT_SUSCRIPTION, tokens);	
			}
		} catch (RemoveApplicationException|Exception ex) {
			log.error("Error creating 'createTransformImpulseWaitingApproveNotification': ", ex);
		}
	}
	
	public void createAlertCostSerpApi() {
		
	}
	
	public void createAdminMonthlyReportNotification() {
		
	}
	public void createManagerMonthlyReportNotification() {
		
	}
	public void createEditorMonthlyReportNotification() {
		
	}
	public void createFormulaMonthlyReportNotification() {
		
	}
	
	private UserAlertMessage createNotification(UserAlertMessage message, EUserAlertMessageTypes type) throws RemoveApplicationException  {
		return createNotification(message, type, null);
	}
	private UserAlertMessage createNotification(UserAlertMessage message, EUserAlertMessageTypes type, Map<String, String> emailsTokens) throws RemoveApplicationException  {
		message.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		message.setDescription(type.getTag());
		message.setTitle(type.getTag());
		message.setEmail_sent(false);
		message.setMust_send_email(type.isMustSendEmail());
		message.setParams(RemoveJsonUtil.dataToJson(message.getParameters()));
		message.setReaded(false);
		message.setType(type.getCode());
		message.setTypeObj(type);
		message.setUrgent(type.isMustSendEmail());
		message = UserAlertMessageDao.init().save(message);
		
		EmailHelper.init().sendUserEmail(type, user, emailsTokens);
		
		return message;
	}
	
	public static void main(String[] args) {
		
	}
}
