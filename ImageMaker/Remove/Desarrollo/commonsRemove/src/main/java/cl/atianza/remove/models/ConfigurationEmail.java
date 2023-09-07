package cl.atianza.remove.models;

import java.util.List;

import cl.atianza.remove.dtos.commons.ConfigurationDto;
import cl.atianza.remove.external.clients.email.RemoveMail;

/**
 * Class for server email configurations.
 * @author pilin
 *
 */
public class ConfigurationEmail {
	private String host;
	private String userName;
	private String password;
	private String emailFrom;
	private String emailTo;
	
	private List<ConfigurationDto> params;

	public ConfigurationEmail() {
		super();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public List<ConfigurationDto> getParams() {
		return params;
	}

	public void setParams(List<ConfigurationDto> params) {
		this.params = params;
	}
	
	@Override
	public String toString() {
		return "ConfigurationEmail [userName=" + userName + ", password=" + password + ", emailFrom=" + emailFrom
				+ ", params=" + params + "]";
	}

	public static ConfigurationEmail init() {
		return new ConfigurationEmail();
	}

	public RemoveMail buildTestEmail(String subject, String content) {
		RemoveMail mail = new RemoveMail();
		
		mail.setContent(content);
		mail.setSubject(subject);
		mail.setTo(this.getEmailTo());
		
		return mail;
	}

	
}
