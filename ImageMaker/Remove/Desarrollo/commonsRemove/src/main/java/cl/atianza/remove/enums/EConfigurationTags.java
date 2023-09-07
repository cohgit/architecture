package cl.atianza.remove.enums;

/**
 * Enum with system configuration params keys.
 * @author pilin
 *
 */
public enum EConfigurationTags {
	TOKEN_EXPIRES("TOKEN_EXPIRES", "120"),
	TRANSFER_MIN_VALUE("TRANSFER_MIN_VALUE", "100"),
	ES_SCHEME("ELASTIC_SEARCH_SCHEME", "http"),
	ES_PATH("ELASTIC_SEARCH_PATH", "localhost"),
	ES_PORT("ELASTIC_SEARCH_PORT", "9200"),
	
	CONTACT_EMAIL("CONTACT_EMAIL", "quiliparapruebas@gmail.com"),
	FRONT_END_DOMAIN_BASE("FRONT_END_DOMAIN_BASE", "http://localhost:4900"),
	
	EMAIL_CLIENT_HOST("EMAIL_CLIENT_HOST", "email-smtp.us-east-1.amazonaws.com"),
	EMAIL_CLIENT_USERNAME("EMAIL_CLIENT_USERNAME", "quiliparapruebas"),
	EMAIL_CLIENT_PASSWORD("EMAIL_CLIENT_PASSWORD", "Atianza2020"),
	EMAIL_CLIENT_EMAIL_FROM("EMAIL_CLIENT_EMAIL_FROM", "quiliparapruebas@gmail.com"),
	EMAIL_PARAM_PREFIX("EMAIL_PARAM_"),
	
	EMAIL_CLIENT_LOGIN_URL_PAGE("CLIENT_LOGIN_URL_PAGE", "/#/client/login"),
	EMAIL_CLIENT_VERIFICATION_EMAIL_URL_PAGE("CLIENT_VERIFICATION_EMAIL_URL_PAGE", "/#/client/verifyEmail/"),
	EMAIL_CLIENT_RECOVERY_PASSWORD_URL_PAGE("CLIENT_RECOVERY_PASSWORD_URL_PAGE", "/#/client/newKey/"),
	EMAIL_CLIENT_CHECKOUT_URL_PAGE("EMAIL_CLIENT_CHECKOUT_URL_PAGE", "/#/client/checkout"),
	
	EMAIL_USER_LOGIN_URL_PAGE("USER_LOGIN_URL_PAGE", "/#/user/login"),
	EMAIL_USER_VERIFICATION_EMAIL_URL_PAGE("USER_VERIFICATION_EMAIL_URL_PAGE", "/#/user/verifyEmail/"),
	EMAIL_USER_RECOVERY_PASSWORD_URL_PAGE("USER_RECOVERY_PASSWORD_URL_PAGE", "/#/user/newKey/"),
	
	SCALE_SERP_API_URL("SCALE_SERP_API_URL", "https://api.scaleserp.com/search"),
	SCALE_SERP_API_KEY("SCALE_SERP_API_KEY", "18E80C1599DA453F9A95D2EB2179C9EF"),
	SCALE_SERP_TOTAL_CREDITS("SCALE_SERP_TOTAL_CREDITS", "1000"),
	SCALE_SERP_CONSUPTION_PREFIX("SCALE_SERP_CONSUPTION_"),
	SCALE_SERP_CONSUPTION("SCALE_SERP_CONSUPTION"),
	SCALE_SERP_REMAINING_PREFIX("SCALE_SERP_REMAINING_"),
	SCALE_SERP_EXPIRATION_DATE("SCALE_SERP_EXPIRATION_DATE")
	;
	
	private String tag;
	private String defaultValue;
	
	private EConfigurationTags(String tag) {
		this.tag = tag;
	}
	private EConfigurationTags(String tag, String defaultValue) {
		this.tag = tag;
		this.defaultValue = defaultValue;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public static EConfigurationTags find(String key) {
		for (EConfigurationTags tag : EConfigurationTags.values()) {
			if (tag.getTag().equals(key)) {
				return tag;
			}
		}
		return null;
	}
}
