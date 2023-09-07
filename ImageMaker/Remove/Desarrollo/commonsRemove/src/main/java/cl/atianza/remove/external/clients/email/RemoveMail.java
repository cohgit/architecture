package cl.atianza.remove.external.clients.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.enums.EClientAlertMessageTypes;
import cl.atianza.remove.enums.ELanguages;
import cl.atianza.remove.enums.EUserAlertMessageTypes;
import cl.atianza.remove.models.AbstractRemoveNotification;

/**
 * Class that represents an email to send.
 * @author pilin
 *
 */
public class RemoveMail extends AbstractRemoveNotification {
	private Logger log = LogManager.getLogger(RemoveMail.class);
			
	private String language;
	private String to;
	private String subject;
	private String content;
	private String type = "text/html";
	private String flag;
	private List<String> files;
	
	public RemoveMail() {
		super();
	}
	public RemoveMail(EUserAlertMessageTypes notification) {
		super();
		this.setSubject(notification.getTag());
		this.flag = notification.getCode();
	}
	public RemoveMail(EClientAlertMessageTypes notification) {
		super();
		this.setSubject(notification.getTag());
	}
	
	public static RemoveMail initForClient(EClientAlertMessageTypes notification, String language) {
		RemoveMail mail = new RemoveMail(notification);
		
		if (language != null) {
			mail.setContent(language + "/" + notification.getEmailTemplate());
		} else {
			mail.setContent(notification.getEmailTemplate());
		}
		mail.setLanguage(language);
		
		return mail;
	}
	public static RemoveMail initForUser(EUserAlertMessageTypes notification, String language) {
		RemoveMail mail = new RemoveMail(notification);
		
		if (language != null) {
			mail.setContent(language + "/" + notification.getEmailTemplate());
		}
		mail.setLanguage(language);
		
		return mail;
	}
	public static RemoveMail initForContact(String template, String language) {
		String _lan = language != null ? language : ELanguages.SPANISH.getCode();
		RemoveMail mail = new RemoveMail();
		
		mail.setSubject("contact.me");
		mail.setContent(_lan + "/" + template);
		mail.setLanguage(_lan);
		
		return mail;
	}
	
	public static RemoveMail init(String template, String language, String subject) {
		String _lan = language != null ? language : ELanguages.SPANISH.getCode();
		RemoveMail mail = new RemoveMail();
		
		mail.setSubject(subject);
		mail.setContent(_lan + "/" + template);
		mail.setLanguage(_lan);
		
		return mail;
	}
	
	public String getLanguage() {
		if (language == null || (!language.equals(ELanguages.SPANISH.getCode()) && !language.equals(ELanguages.ENGLISH.getCode()))) {
			language = ELanguages.SPANISH.getCode();
		}
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = getTemplate(content);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String parseSubject() {
		try {
			String subject = ResourceBundle.getBundle("localization/messages", Locale.forLanguageTag(this.getLanguage())).getString(this.getSubject());
			
			if (subject != null) {
				return this.replaceTokens(subject);
			}
		} catch (Throwable e) {log.error("Bundle not found:", e.getMessage());}
		
		return this.getSubject();
	}
	public String parseContent() {
		return replaceTokensClearHtml(this.getContent());
	}
	
	/**
	 * 
	 * @param htmlTemplateName
	 * @return
	 */
	private String getTemplate(String htmlTemplateName) {
		log.info("Loading template for email: " + htmlTemplateName);
		StringBuilder contentBuilder = new StringBuilder();
		
		 // The class loader that loaded the class
       ClassLoader classLoader = getClass().getClassLoader();
       InputStream inputStream = classLoader.getResourceAsStream("templates/emails/" + htmlTemplateName);

       // the stream holding the file content
       if (inputStream == null) {
           throw new IllegalArgumentException("file not found! templates/emails/" + htmlTemplateName);
       } 

       try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
   			BufferedReader reader = new BufferedReader(streamReader)) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	contentBuilder.append(line);
	        }
	    } catch (IOException e) {
	    	log.error("Error getting change status campaign content email: ", e);
	    }
       
		return contentBuilder.toString();
	}
	
	@Override
	public String toString() {
		return "RemoveMail [to=" + to + ", subject=" + subject + ", type=" + type + ", files="
				+ files + "]";
	}
	
}
