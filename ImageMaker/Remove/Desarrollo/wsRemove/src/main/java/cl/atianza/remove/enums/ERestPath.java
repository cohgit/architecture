package cl.atianza.remove.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.properties.RemoveWsProperties;


/**
 * Service system paths.
 *
 * @author Jose Gutierrez
 */
public enum ERestPath {
	// ADMIN & MANAGER PATHS
	ADMIN("/admin"),
	ADMIN_LOGIN(ADMIN.getPath() + "/login"),
	ADMIN_CONFIG(ADMIN.getPath() + "/config"),
	HEALTHCHECK("/healthcheck"),
	
	
	ADMIN_SCANNER_COMMENT(ADMIN.getPath() + "/scanner/comment", "SCANNER_COMMENT"),
	ADMIN_SCANNER_ALERTS(ADMIN.getPath() + "/scanner/alerts", "SCANNER_ALERTS"),
	ADMIN_SCANNER(ADMIN.getPath() + "/scanner", "SCANNER"),
	ADMIN_SCANNER_SNIPPET(ADMIN.getPath() + "/scanner/snippet", "SCANNER_SNIPPET"),
	ADMIN_DASHBOARD(ADMIN.getPath() + "/dashboard", "DASHBOARD"),
	ADMIN_IMPULSE(ADMIN.getPath() + "/impulse", "IMPULSE"),
	ADMIN_IMPULSE_PUBLISH(ADMIN.getPath() + "/impulse/publish", "IMPULSE_PUBLISH"),
	ADMIN_IMPULSE_APPROVE(ADMIN.getPath() + "/impulse/approve", "IMPULSE_APPROVE"),
	ADMIN_IMPULSE_VARIABLE(ADMIN.getPath() + "/impulse/variable", "IMPULSE_VARIABLE"),
	ADMIN_DEINDEXATION(ADMIN.getPath() + "/deindexation", "DEINDEXATION"),
	ADMIN_DEINDEXATION_URL(ADMIN.getPath() + "/deindexation/url", "DEINDEXATION_URL"),
	ADMIN_DEINDEXATION_STATUS(ADMIN.getPath() + "/deindexation/status", "DEINDEXATION_STATUS"),
	ADMIN_DEINDEXATION_HISTORIC(ADMIN.getPath() + "/deindexation/historic", "DEINDEXATION_HISTORIC"),
	
	ADMIN_USERS(ADMIN.getPath() + "/users", "USERS"),
	ADMIN_USERS_AUDIT(ADMIN.getPath() + "/users/audit", "USERS_AUDIT"),
	ADMIN_CLIENTS(ADMIN.getPath() + "/clients", "CLIENTS"),
	ADMIN_CLIENTS_AUDIT(ADMIN.getPath() + "/clients/audit", "CLIENTS_AUDIT"),
	ADMIN_CLIENTS_PAYMENT(ADMIN.getPath() + "/clients/payment", "CLIENTS_PAYMENT"),
	ADMIN_CLIENTS_ATTEMPT(ADMIN.getPath() + "/clients/attempt", "CLIENTS_ATTEMPT"),
	ADMIN_PLANS(ADMIN.getPath() + "/plans", "PLANS"),
	ADMIN_PLANS_SUGGESTIONS(ADMIN.getPath() + "/plans/suggestions", "PLANS_SUGGESTIONS"),
	ADMIN_REPORTS(ADMIN.getPath() + "/reports", "REPORTS"),
	ADMIN_TRACKING_WORDS(ADMIN.getPath() + "/trackingwords", "TRACKING_WORDS"),
	ADMIN_FORBIDDEN_WORDS(ADMIN.getPath() + "/forbiddenwords", "FORBIDDEN_WORDS"),
	
	
	// CLIENT PATHS
	CLIENT("/client"),
	CLIENT_LOGIN(CLIENT.getPath() + "/login"),
	CLIENT_CONFIG(CLIENT.getPath() + "/config"),
	
	CLIENT_SCANNER_COMMENT(CLIENT.getPath() + "/scanner/comment", "SCANNER_COMMENT"),
	CLIENT_SCANNER(CLIENT.getPath() + "/scanner", "SCANNER"),
	CLIENT_SCANNER_SNIPPET(CLIENT.getPath() + "/scanner/snippet", "SCANNER_SNIPPET"),
	CLIENT_DEINDEXATION(CLIENT.getPath() + "/deindexation", "DEINDEXATION"),
	CLIENT_DEINDEXATION_STATUS(CLIENT.getPath() + "/deindexation/status", "DEINDEXATION_STATUS"),
	CLIENT_DEINDEXATION_HISTORIC(CLIENT.getPath() + "/deindexation/historic", "DEINDEXATION_HISTORIC"),
	
	CLIENT_IMPULSE(CLIENT.getPath() + "/impulse", "IMPULSE"),
	CLIENT_IMPULSE_PUBLISH(CLIENT.getPath() + "/impulse/publish", "IMPULSE_PUBLISH"),
	CLIENT_IMPULSE_APPROVE(CLIENT.getPath() + "/impulse/approve", "IMPULSE_APPROVE"),
	CLIENT_REPORTS(CLIENT.getPath() + "/reports", "REPORTS"),
	CLIENT_SCANNER_ALERTS(CLIENT.getPath() + "/scanner/alerts", "SCANNER_ALERTS"),
	
	// COMMON PATHS
	LOGOUT("/logout"),

	COMMON("/common"),
	COMMON_SEARCH_TYPES(COMMON.getPath() + "/searchtypes", Arrays.asList(RestMethod.GET)),
	COMMON_DEVICES(COMMON.getPath() + "/devices", Arrays.asList(RestMethod.GET)),
	COMMON_COUNTRIES(COMMON.getPath() + "/countries", Arrays.asList(RestMethod.GET)),
	COMMON_LANGUAGES(COMMON.getPath() + "/languages", Arrays.asList(RestMethod.GET)),
	COMMON_TRACKING_WORDS(COMMON.getPath() + "/trackingWords", Arrays.asList(RestMethod.GET)),
	COMMON_FORBIDDEN_WORDS(COMMON.getPath() + "/forbiddenWords", Arrays.asList(RestMethod.GET)),
	COMMON_FEELINGS(COMMON.getPath() + "/feelings", Arrays.asList(RestMethod.GET)),
	COMMON_NEWS_TYPES(COMMON.getPath() + "/news/types", Arrays.asList(RestMethod.GET)),
	COMMON_IMAGES_COLOURS(COMMON.getPath() + "/images/colours", Arrays.asList(RestMethod.GET)),
	COMMON_IMAGES_TYPES(COMMON.getPath() + "/images/types", Arrays.asList(RestMethod.GET)),
	COMMON_IMAGES_SIZES(COMMON.getPath() + "/images/sizes", Arrays.asList(RestMethod.GET)),
	COMMON_IMAGES_USAGE_RIGHTS(COMMON.getPath() + "/images/usagerights", Arrays.asList(RestMethod.GET)),
	COMMON_CLIENTS(COMMON.getPath() + "/clients", Arrays.asList(RestMethod.GET)),
	COMMON_CLIENTS_EMAIL(COMMON.getPath() + "/clients/email", Arrays.asList(RestMethod.GET)),
	COMMON_CLIENT_PLAN(COMMON.getPath() + "/clients/plans", Arrays.asList(RestMethod.GET)),
	COMMON_PLANS(COMMON.getPath() + "/plans", Arrays.asList(RestMethod.GET)),
	COMMON_PROFILES(COMMON.getPath() + "/profiles", Arrays.asList(RestMethod.GET)),
	COMMON_IMPULSE_TYPES(COMMON.getPath() + "/impulse/types", Arrays.asList(RestMethod.GET)),
	COMMON_IMPULSE_STATUS(COMMON.getPath() + "/impulse/status", Arrays.asList(RestMethod.GET)),
	COMMON_IMPULSE_CONCEPTS(COMMON.getPath() + "/impulse/concepts", Arrays.asList(RestMethod.GET)),
	COMMON_NOTIFICATIONS(COMMON.getPath() + "/notifications", Arrays.asList(RestMethod.GET, RestMethod.PUT)),
	COMMON_GOOGLE_SUGGEST(COMMON.getPath() + "/google/suggest", Arrays.asList(RestMethod.GET)),
	COMMON_DEINDEXATION_STATUS(COMMON.getPath() + "/deindexation/status", Arrays.asList(RestMethod.GET)),
	COMMON_EMAIL(COMMON.getPath() + "/email", Arrays.asList(RestMethod.GET)),
	COMMON_PING(COMMON.getPath() + "/ping", Arrays.asList(RestMethod.GET)),
	
	// EXTERNALS
	XTERNAL("/xternal"),
	XTERNAL_SUSCRIPTION(XTERNAL.getPath() + "/suscriptions"),
	XTERNAL_USER_PASSWORD(XTERNAL.getPath() + "/user/password"),
	XTERNAL_CLIENT_PASSWORD(XTERNAL.getPath() + "/client/password"),
	XTERNAL_COMMON_PLAN(XTERNAL.getPath() + "/common/plan"),
	XTERNAL_COMMON_PLANS(XTERNAL.getPath() + "/common/plans"),
	XTERNAL_COMMON_CONTACTS(XTERNAL.getPath() + "/common/contacts"),
	XTERNAL_COMMON_PING(XTERNAL.getPath() + "/common/ping", Arrays.asList(RestMethod.GET)),
	XTERNAL_COMMON_CERTIFICATE(XTERNAL.getPath() + "/common/certificate", Arrays.asList(RestMethod.GET)),
	
	
	
	
//	
//	USER("/user"),
//	USER_PASSWORD(USER.getPath() + "/password", Arrays.asList(RestMethod.PUT)),
//
//	// Common Routes
//	
//	COMMON_LIST(COMMON.getPath() + "/list", Arrays.asList(RestMethod.GET)),
//	COMMON_COUNTRY(COMMON.getPath() + "/country", Arrays.asList(RestMethod.GET)),
	
	;

	private String path;
	private String key;
	private List<String> skipTokenValidation;
	private List<String> skipActionValidation;

	private ERestPath(String path) {
		this.path = path;
		this.skipTokenValidation = new ArrayList<String>();
		this.skipActionValidation = new ArrayList<String>();
	}
	private ERestPath(String path, String key) {
		this.path = path;
		this.key = key;
		this.skipTokenValidation = new ArrayList<String>();
		this.skipActionValidation = new ArrayList<String>();
	}
	private ERestPath(String path,  List<String> skipActionValidation) {
		this.path = path;
		this.skipTokenValidation = new ArrayList<String>();
		this.skipActionValidation = skipActionValidation;
	}
	private ERestPath(String path,  List<String> skipActionValidation, List<String> skipTokenValidation) {
		this.path = path;
		this.skipTokenValidation = skipTokenValidation;
		this.skipActionValidation = skipActionValidation;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPath() {
		return path;
	}

	public String getBasePath() {
		int indexSecondSlash = path.indexOf("/", 1);

		return indexSecondSlash == -1 ? path : path.substring(0, indexSecondSlash);
	}
	public String getNonBasePath() {
		int indexSecondSlash = path.indexOf("/", 1);

		return indexSecondSlash == -1 ? "" : path.substring(indexSecondSlash, path.length());
	}

	public boolean validateToken(String method) {
		return this.skipTokenValidation == null || !this.skipTokenValidation.contains(method);
	}
	public boolean validateActionAccess(String method) {
		return this.skipActionValidation == null || !this.skipActionValidation.contains(method);
	}

	public String getFullPath() {
		return "/" + RemoveWsProperties.init().getProperties().getRestPath() + path;
	}

	public static boolean shouldValidateToken(String uri, String method) throws RemoveApplicationException {
//		boolean endsWithList = false;
//
//		if (!uri.endsWith(ERestPath.COMMON_LIST.getPath()) && uri.endsWith("/list")) {
//			endsWithList = true;
//			uri = uri.substring(0, uri.length()-5);
//		}
//
//		ERestPath path = findPath(uri);
//
//		if (path == null) {
//			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.RECURSO_NO_ENCONTRADO);
//		}
//
//		if (endsWithList) {
//			return path.validateToken(RestMethod.LIST);
//		} else {
//			return path.validateToken(method);
//		}
		
		return true;
	}
	public static boolean shouldValidateAction(String uri, String method) throws RemoveApplicationException {
//		boolean endsWithList = false;
//
//		if (!uri.endsWith(ERestPath.COMMON_LIST.getPath()) && uri.endsWith("/list")) {
//			endsWithList = true;
//			uri = uri.substring(0, uri.length()-5);
//		}
//
//		ERestPath path = findPath(uri);
//
//		if (path == null) {
//			RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.RECURSO_NO_ENCONTRADO);
//		}
//
//		if (endsWithList) {
//			return path.validateActionAccess(RestMethod.LIST);
//		} else {
//			return path.validateActionAccess(method);
//		}
		return true;
	}
	public static ERestPath findPath(String uri) {
		for (ERestPath path : ERestPath.values()) {
			if (path.getFullPath().trim().equals(uri.trim())) {
				return path;
			}
		}

		return null;
	}
	public static List<String> listProvisionalAccess() {
//		return Arrays.asList(ERestPath.USER_PASSWORD.getFullPath()+"|"+RestMethod.PUT);
		return null;
	}
}

class RestMethod {
	public static final String POST = "POST";
	public static final String GET = "GET";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";
	public static final String LIST = "LIST";
}
