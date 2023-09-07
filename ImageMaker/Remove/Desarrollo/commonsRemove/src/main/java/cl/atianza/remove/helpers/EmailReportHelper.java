package cl.atianza.remove.helpers;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.commons.ConfigurationDao;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.external.clients.email.RemoveEmailClient;
import cl.atianza.remove.external.clients.email.RemoveMail;

/**
 * Email Sender?
 * @author pilin
 *
 */
public class EmailReportHelper {
	private Logger log = LogManager.getLogger(EmailReportHelper.class);
			
	public static EmailReportHelper init() {
		return new EmailReportHelper();
	}
	
	public void sendReportEmailMonthly(String language, String emailTo, String userName, 
			Map<String, String> tokens, List<String> attachments) throws RemoveApplicationException {
		sendEmailMonthly("report_scanner_dashboard.html", language, emailTo, userName, tokens, attachments);
	}
	
	public void sendEmailMonthly(String template, String language, String emailTo, String userName, 
			Map<String, String> tokens, List<String> attachments) throws RemoveApplicationException {
		log.info("Sending Email to destinatary:" + emailTo);
		RemoveMail mail = RemoveMail.init(template, language, "title.report.scanner.dashboard.monthly");
		
		mail.setTo(emailTo);
		
		if (tokens != null) {
			mail.setTokens(tokens);	
		}
		mail.setLanguage(language);
		mail.addToken("{{USER_NAME}}", userName);
		mail.setFiles(attachments);
		
		log.info("Email Builded: " + mail);
		RemoveEmailClient.init(ConfigurationDao.init().getEmailParams()).send(mail);
	}
	
	public void sendReportEmail(String language, String emailTo, String userName, 
			Map<String, String> tokens, List<String> attachments) throws RemoveApplicationException {
		sendEmail("report_scanner_dashboard.html", language, emailTo, userName, tokens, attachments);
	}
	
	public void sendEmail(String template, String language, String emailTo, String userName, 
			Map<String, String> tokens, List<String> attachments) throws RemoveApplicationException {
		log.info("Sending Email to destinatary:" + emailTo);
		RemoveMail mail = RemoveMail.init(template, language, "title.report.scanner.dashboard");
		
		mail.setTo(emailTo);
		
		if (tokens != null) {
			mail.setTokens(tokens);	
		}
		mail.setLanguage(language);
		mail.addToken("{{USER_NAME}}", userName);
		mail.setFiles(attachments);
		
		log.info("Email Builded: " + mail);
		RemoveEmailClient.init(ConfigurationDao.init().getEmailParams()).send(mail);
	}
}