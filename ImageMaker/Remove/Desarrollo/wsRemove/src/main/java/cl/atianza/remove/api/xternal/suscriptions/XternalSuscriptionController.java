package cl.atianza.remove.api.xternal.suscriptions;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.PlanClientSuggestionDao;
import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientEmailVerificationRequestDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.helpers.ClientNotificationHelper;
import cl.atianza.remove.helpers.SuscriptionHelper;
import cl.atianza.remove.models.admin.PlanClientSuggestion;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientEmailVerificationRequest;
import cl.atianza.remove.models.views.ClientSuscription;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RestController;
import spark.Request;
import spark.Response;

/**
 * Service for Client suscriptions. (This service shouldn't use token verification)
 * @author pilin
 *
 */
public class XternalSuscriptionController extends RestController {
	public XternalSuscriptionController() {
		super(ERestPath.XTERNAL_SUSCRIPTION, LogManager.getLogger(XternalSuscriptionController.class));
	}
	
	/**
	 * Register a new Client, his payment and configuration
	 */
	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			ClientSuscription suscription = (ClientSuscription) RemoveRequestUtil.getBodyObject(request, ClientSuscription.class);
			
			if (suscription.isAttempt()) {
				return SuscriptionHelper.init().saveSuscribeAttempt(suscription);
			} else {
				if (suscription.getUuidSuscription() != null) {
					validateSuggestion(suscription);
				}
				
				return SuscriptionHelper.init().suscribe(suscription);
			}
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	private void validateSuggestion(ClientSuscription suscription) throws RemoveApplicationException {
		PlanClientSuggestion suggestion = PlanClientSuggestionDao.init().getByUuid(suscription.getUuidSuscription());
		
		if (suggestion.isClient_already_registred()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_SUSCRIPTION_REGISTRED);
		}
		
		if (suggestion.isClient_attend_suggestion()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_SUSCRIPTION_ATTEMPTED);
		}
		
		if (!suggestion.getClient_email().equalsIgnoreCase(suscription.getClient().getEmail())) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_SUSCRIPTION_EMAIL);
		}
		
		if (suggestion.getId_plan().longValue() != suscription.getPlan().getId().longValue()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_SUSCRIPTION_PLAN);
		}
	}

	/**
	 * Check if email is already registered (True: registered, False: not registered)
	 */
	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());

		try {
			String uuidSuscription = RemoveRequestUtil.getOptionalParamString(request, EWebParam.UUID);
			String email = RemoveRequestUtil.getOptionalParamString(request, EWebParam.EMAIL);
			
			if (uuidSuscription != null) {
				PlanClientSuggestion suggestion = PlanClientSuggestionDao.init().getByUuid(uuidSuscription);
				suggestion.setPlan(PlanDao.init().get(suggestion.getId_plan()));
				return suggestion != null ? RemoveResponseUtil.buildOk(suggestion) : RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_SUSCRIPTION_NOT_FOUND);
			} else if (email != null){
				Client client = ClientDao.init().getByEmail(email);
				
				if (client != null && !client.esNuevo()) {
					return RemoveResponseUtil.buildOk(true);
				} else {
					return RemoveResponseUtil.buildOk(false);
				}
			}
			
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	/**
	 * Verificate Email Client and Activate it.
	 */
	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			VerifyEmail verificationBody = (VerifyEmail) RemoveRequestUtil.getBodyObject(request, VerifyEmail.class);
			
			if (verificationBody.getType().equalsIgnoreCase("client")) {
				return verifyForClient(verificationBody);
			} else if (verificationBody.getType().equalsIgnoreCase("user")) {
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_SERVICE_NOT_IMPLEMENTED);
			}
			
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	private String verifyForClient(VerifyEmail verificationBody) throws RemoveApplicationException {
		ClientEmailVerificationRequest verification = ClientEmailVerificationRequestDao.init().get(verificationBody.getUuid());
		
		if (verification == null) {
			getLog().error("Error email verification not found: " + verificationBody.getUuid());
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_EMAIL_VERIFICATION_INVALID);
		}
		
		if (verification.isConfirmed()) {
			getLog().error("Error email already verified: " + verification);
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_EMAIL_VERIFICATION_ALREADY);
		}
		
		if (verification.getExpiration_date().isBefore(RemoveDateUtils.nowLocalDateTime())) {
			getLog().error("Error email verification expired: " + verification);
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_EMAIL_VERIFICATION_EXPIRED);
		}
		
		ClientDao.init().updateEmailVerified(verification.getId_client(), verification.getEmail());
		ClientDao.init().updateReadOnly(verification.getId_client(), false, "");
		ClientEmailVerificationRequestDao.init().confirmChange(verification);
		
		ClientNotificationHelper.init(ClientDao.init().getBasicById(verification.getId_client())).createVerifiedEmailSuccess();
		
		return RemoveResponseUtil.buildDefaultOk();
	}
}

class VerifyEmail {
	private String uuid;
	private String type;
	public VerifyEmail() {
		super();
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	} 
}