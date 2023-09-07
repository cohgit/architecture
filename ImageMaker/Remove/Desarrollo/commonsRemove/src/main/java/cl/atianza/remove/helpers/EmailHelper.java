package cl.atianza.remove.helpers;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.commons.ConfigurationDao;
import cl.atianza.remove.enums.EClientAlertMessageTypes;
import cl.atianza.remove.enums.ELanguages;
import cl.atianza.remove.enums.EUserAlertMessageTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.external.clients.email.RemoveEmailClient;
import cl.atianza.remove.external.clients.email.RemoveMail;
import cl.atianza.remove.models.admin.PlanClientSuggestion;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.views.ContactAttsMailView;
import cl.atianza.remove.models.views.ContactMailView;

/**
 * Email Sender?
 * @author pilin
 *
 */
public class EmailHelper {
	private Logger log = LogManager.getLogger(EmailHelper.class);
			
	public static EmailHelper init() {
		return new EmailHelper();
	}
	
	public void sendUserEmail(EUserAlertMessageTypes notification, User user) throws RemoveApplicationException {
		sendUserEmail(notification, user, null);
	}
	public void sendUserEmail(EUserAlertMessageTypes notification, User user, Map<String, String> tokens) throws RemoveApplicationException {
		log.info("Sending Email to user:" + user);
		RemoveMail mail = RemoveMail.initForUser(notification, user.getLanguage());
		
		mail.setTo(user.getEmail());
		
		if (tokens != null) {
			mail.setTokens(tokens);	
		}
		mail.setLanguage(user.getLanguage());
		mail.addToken("{{USER_NAME}}", user.getName() + " " + user.getLastname());
		
		log.info("Email Builded:" + mail);
		RemoveEmailClient.init(ConfigurationDao.init().getEmailParams()).send(mail);
	}
	
	public void sendClientEmail(EClientAlertMessageTypes notification, Client client) throws RemoveApplicationException {
		sendClientEmail(notification, client, null);
	}
	public void sendClientEmail(EClientAlertMessageTypes notification, Client client, Map<String, String> tokens) throws RemoveApplicationException {
		log.info("Sending Email To Client:" + client);
		RemoveMail mail = RemoveMail.initForClient(notification, client.getLanguage());
		
		mail.setTo(client.getEmail());
		//mail.setTo("israel.navarrete@atianza.com"+", "+"paola.godoy@atianza.com");
		//mail.setTo("israel.navarrete@atianza.com");
		if (tokens != null) {
			mail.setTokens(tokens);	
		}
		mail.setLanguage(client.getLanguage());
		mail.addToken("{{CLIENT_NAME}}", client.getName() + " " + client.getLastname());
		log.info("Email Builded:" + mail);
		RemoveEmailClient.init(ConfigurationDao.init().getEmailParams()).send(mail);
	}
	
	/**
	 * 
	 * @param notification
	 * @param userName
	 * @param email
	 * @param uuidRequest 
	 * @throws RemoveApplicationException
	 */
	public void sendRecoveryPasswordUser(EUserAlertMessageTypes notification, String userName, String email, String uuidRequest) throws RemoveApplicationException {
		log.info("Sending Email 'Recovery Password Client' for:" + userName + " - " + email);
		RemoveMail mail = RemoveMail.initForUser(notification, ELanguages.SPANISH.getCode());
		
		mail.setTo(email);
		mail.addToken("{{USER_NAME}}", userName);
		mail.addToken("{{VERIFICATION_EMAIL_URL}}", "http://localhost:4900/remove-app/admin/newKey?uuid=" + uuidRequest);
		
		log.info("Email Builded:" + mail);
		RemoveEmailClient.init(ConfigurationDao.init().getEmailParams()).send(mail);
	}
	
	public void sendEmailToContact(ContactMailView contactMail) throws RemoveApplicationException {
		log.info("Sending Email To Contact:");
		RemoveMail mail = RemoveMail.initForContact("contact_admin.html", ELanguages.SPANISH.getCode());
		
		mail.setTo(ConfigurationDao.init().getContactEmail());
		mail.setLanguage(ELanguages.SPANISH.getCode());
		mail.addToken("{{TITLE_FORM}}", contactMail.getTitle());
		mail.addToken("{{CONTACT_FORM}}", buildContactForm(contactMail));
		
		log.info("Email Builded:" + mail);
		RemoveEmailClient.init(ConfigurationDao.init().getEmailParams()).send(mail);
	}

	private String buildContactForm(ContactMailView contactMail) {
		String content = "";
		
		for (ContactAttsMailView att : contactMail.getAtts()) {
			content = content + "<div><b>" + att.getKey() + ":</b> " + att.getValue() + "</div><br>";
		};
		
		return content;
	}
	
	public void sendSuggestionPlanMail(PlanClientSuggestion suggestion) throws RemoveApplicationException {
		log.info("Sending Email To Contact:" + suggestion.getId_client() + " | " + suggestion.getPlan().getId() + " | " + suggestion.getUuid());
		String keySuggestion = suggestion.getId_client() != null ? suggestion.getUuid() : String.valueOf(suggestion.getPlan().getId());
		
		RemoveMail mail = RemoveMail.initForContact("suggestion_plan.html", ELanguages.SPANISH.getCode());
		
		mail.setTo(suggestion.getClient_email());
		mail.setLanguage(ELanguages.SPANISH.getCode());
		mail.addToken("{{CLIENT_NAME}}", suggestion.getClient_name());
		mail.addToken("{{PLAN_NAME}}", suggestion.getPlan().getName());
		mail.addToken("{{CHECKOUT_URL}}", ConfigurationDao.init().getFrontEndDomainBase() + ConfigurationDao.init().getEmailParamCheckoutUrlPage() + "/" + keySuggestion);
		
		log.info("Email Builded:" + mail);
		RemoveEmailClient.init(ConfigurationDao.init().getEmailParams()).send(mail);
	}
}