package cl.atianza.remove.api.client.configs;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.PlanClientSuggestionDao;
import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientEmailVerificationRequestDao;
import cl.atianza.remove.daos.client.ClientPasswordDao;
import cl.atianza.remove.daos.client.ClientPaybillDao;
import cl.atianza.remove.daos.client.ClientPaymentKeyDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.commons.CountryDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EPaymentMethods;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.external.clients.payments.ListExternalClientPayment;
import cl.atianza.remove.external.clients.payments.stripe.PaymentStripeClient;
import cl.atianza.remove.helpers.SuscriptionHelper;
import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.admin.PlanClientSuggestion;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPassword;
import cl.atianza.remove.models.client.ClientPaymentKey;
import cl.atianza.remove.models.views.PlanChangeSuscriptionInfo;
import cl.atianza.remove.models.views.RemoveLogin;
import cl.atianza.remove.models.views.UpdatePassword;
import cl.atianza.remove.properties.RemoveWsProperties;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.ClientAccessValidator;
import spark.Request;
import spark.Response;

/**
 * Client Service for self-account management.
 * @author pilin
 *
 */
public class ClientConfigController extends RestController {
	public ClientConfigController() {
		super(ERestPath.CLIENT_CONFIG, LogManager.getLogger(ClientConfigController.class));
	}

	/**
	 * Obtain personal information from client's session (token)
	 */
	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());

		try {
			String infoRequested = RemoveRequestUtil.getParamString(request, EWebParam.TYPE_REQUEST);
			
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			Client client = ClientAccessValidator.init(token).validateClientAccess();
			
			ClientPaybillDao.init().listConfirmedByClient(client);
			
			if (infoRequested.equals("configuration")) {
				client.setCountry(CountryDao.init().get(client.getId_country()));
				client.setPlanActives(ClientPlanDao.init().listByClientId(client.getId()));
				
				try {
					client.getPayments().add(new ListExternalClientPayment(EPaymentMethods.STRIPE, PaymentStripeClient.init().listPaymentsForCustomer(client)));	
				} catch (RemoveApplicationException ex) {
					getLog().error("Error loading payments from Stripe: ", ex);
					client.setMessage("error.loading.payments.stripe");
				}
			} else if (infoRequested.equals("paymentPortal")) {
				ClientPaymentKey key = ClientPaymentKeyDao.init().getByClientAndPaymentMethod(client.getId(), EPaymentMethods.STRIPE);
				if (key != null) {
					client.setPaymentPortal(PaymentStripeClient.init().getPaymentPortal(key.getKey(), RemoveRequestUtil.getParamString(request, EWebParam.URL)));	
				}
			} else {
				RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PARAMS);
			}
			
			return RemoveResponseUtil.buildOk(client);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	/**
	 * 
	 */
	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			String changeType = RemoveRequestUtil.getHeader(request, EWebParam.TRANSACTION);

			if (changeType == null) {
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
			}
			
			Client client = ClientAccessValidator.init(token).validateClientAccess();
			
			if (changeType.equals("update_info")) {
				getLog().info("Changing client info...");
				
				Client clientToUpdate = (Client) RemoveRequestUtil.getBodyObject(request, Client.class);
				clientToUpdate.setId(client.getId());
				
				String validation = validateUpdate(clientToUpdate);
				if (validation != null) {
					return validation;
				}
				
				ClientDao.init().updateAccountData(clientToUpdate);
			} else if (changeType.equals("change_plan")) {
				getLog().info("Changing client plan...");
				
				PlanChangeSuscriptionInfo planSuscriptionInfo = (PlanChangeSuscriptionInfo) RemoveRequestUtil.getBodyObject(request, PlanChangeSuscriptionInfo.class);
				
				if (planSuscriptionInfo.getPaymentMethod() == null) { // Transfer by Default
					SuscriptionHelper.init().changeSuscriptionByTransfer(client, planSuscriptionInfo.getNewPlan(), 
							planSuscriptionInfo.buildScannersToMigrateIds(), planSuscriptionInfo.isProgrammedToNextPayment());
				} else {
					if (planSuscriptionInfo.getPaymentMethod().getPlatform().equals(EPaymentMethods.STRIPE.getCode())) {
						SuscriptionHelper.init().changeSuscriptionByStripe(client, planSuscriptionInfo.getNewPlan(), 
								planSuscriptionInfo.buildScannersToMigrateIds(), planSuscriptionInfo.isProgrammedToNextPayment(),
								planSuscriptionInfo.getPaymentMethod(), planSuscriptionInfo.getClientPlanToChangeId());
					} else {
						return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_UNSUPPORTED_PAYMENT_TYPE);
					}
				}
			} else if (changeType.equals("request_quote")) {
				Plan planQuoteRequest = (Plan) RemoveRequestUtil.getBodyObject(request, Plan.class);
				
				planQuoteRequest = PlanDao.init().saveAsQuoteRequest(planQuoteRequest, client.getEmail());
				PlanClientSuggestion suggestion = new PlanClientSuggestion();
				suggestion.setId_client(client.getId()); 
				suggestion.setId_plan(planQuoteRequest.getId());
				suggestion.setClient_already_registred(true);
				suggestion.setClient_attend_suggestion(false);
				suggestion.setClient_email(client.getEmail());
				suggestion.setClient_name(client.getName() + " " + client.getLastname());
				suggestion.setEmail_sent(false);
				
				PlanClientSuggestionDao.init().save(suggestion);
			} else if (changeType.equals("request_verification_email")) {
				ClientEmailVerificationRequestDao.init().createRequest(client);
			} else {
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
			}
			
			ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_CONFIGURATION_UPDATED);
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	/**
	 * 
	 */
	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UpdatePassword config = (UpdatePassword) RemoveRequestUtil.getBodyObject(request, UpdatePassword.class);
			
			Client client = ClientDao.init().getByUuid(token.getUuidAccount());
			RemoveLogin login = new RemoveLogin(client.getEmail(), config.getOldPassword());
			ClientPasswordDao.init().validateLogin(login, Integer.valueOf(RemoveWsProperties.init().getProperties().getLoginRetries()));
			
			ClientPassword password = new ClientPassword();
			password.setId_owner(client.getId());
			password.setPassword(config.getNewPassword());
			
			ClientPasswordDao.init().save(password);
			
			ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_PASSWORD_UPDATED);
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	private String validateUpdate(Client client) throws RemoveApplicationException {
		ClientDao clientDao = ClientDao.init();
		
		Client old = clientDao.getByLogin(client.getEmail());
		log.info("Old by email:" + old);
		if (old != null && old.getId().longValue() != client.getId().longValue()) {
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_MAIL_ALREADY_REGISTERED);
		}
		
		old = clientDao.getByPhone(client.getPhone());
		log.info("Old by phone:" + old);
		if (old != null && old.getId().longValue() != client.getId().longValue()) {
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_PHONE_ALREADY_REGISTERED);
		}
		
		return null;
	}
}
