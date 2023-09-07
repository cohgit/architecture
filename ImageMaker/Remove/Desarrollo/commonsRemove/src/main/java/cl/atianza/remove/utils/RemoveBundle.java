package cl.atianza.remove.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveBundle {
	private Logger log = LogManager.getLogger(RemoveBundle.class);
	private String language;
	
	public static RemoveBundle init(String language) {
		return new RemoveBundle(language);
	}
	
	public RemoveBundle(String language) {
		super();
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}


	public String getBundle(String key) {
		try {
			return ResourceBundle.getBundle("localization/messages", Locale.forLanguageTag(this.getLanguage())).getString(key);			
		} catch (Throwable e) {
			log.error("Error loadign bundle for: " + key, e);
		}
		
		return "{{"+key+"}}";
	}
	
	public String getLabelBundle(String key) {
		return getBundle("label."+key);
	}
	public String getLabelCountryBundle(String key) {
		return getLabelBundle("country."+key);
	}

	public Object getLabelLanguageBundle(String key) {
		return getLabelBundle("language."+key);
	}
}
