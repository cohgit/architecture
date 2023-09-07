package cl.atianza.remove.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Class represent a system notification.
 * @author pilin
 *
 */
public class AbstractRemoveNotification {
	private Map<String, String> tokens;

	public AbstractRemoveNotification() {
		super();
		this.tokens = new HashMap<String, String>();
	}

	public Map<String, String> getTokens() {
		if (this.tokens == null) {
			this.tokens = new HashMap<String, String>();
		}
		return tokens;
	}

	public void setTokens(Map<String, String> tokens) {
		this.tokens = tokens;
	}
	
	public void addToken(String key, String value) {
		this.getTokens().put(key, value);
	}
	
	protected String replaceTokensClearHtml(String _template) {
		if (this.getTokens() == null || this.getTokens().isEmpty() || _template == null) {
			return _template;
		}
		
		String template = _template;
		
		for (String token : this.getTokens().keySet()) {
			template = template.replace(token, this.clearTextForHtmlES(this.getTokens().get(token)));
		}
		
		return template;
	}
	
	protected String replaceTokens(String _template) {
		if (this.getTokens() == null || this.getTokens().isEmpty() || _template == null) {
			return _template;
		}
		
		String template = _template;
		
		for (String token : this.getTokens().keySet()) {
			template = template.replace(token, this.getTokens().get(token));
		}
		
		return template;
	}
	
	protected String clearTextForHtmlES(String value) {
		return value == null ? "" :
			value.replaceAll("á", "&aacute;")
					.replaceAll("é", "&eacute;")
					.replaceAll("í", "&iacute;")
					.replaceAll("ó", "&oacute;")
					.replaceAll("ú", "&uacute;")
					.replaceAll("Á", "&Aacute;")
					.replaceAll("É", "&Eacute;")
					.replaceAll("Í", "&Iacute;")
					.replaceAll("Ó", "&Oacute;")
					.replaceAll("Ú", "&Uacute;")
					.replaceAll("ñ", "&ntilde;")
					.replaceAll("Ñ", "&Ntilde;")
					;
	}
}
