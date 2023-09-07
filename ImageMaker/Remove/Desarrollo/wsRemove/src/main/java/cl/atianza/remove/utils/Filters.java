package cl.atianza.remove.utils;

import spark.Filter;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.admin.UserSessionDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientSessionDao;
import cl.atianza.remove.daos.commons.ConfigurationDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.admin.UserSession;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientSession;


/**
 * Filtros que se ejecutan antes o despu�s de la llamada a los servicios.
 * 
 * @author Jose Gutierrez
 */
public class Filters {
	private static Logger log;
	
	private static Logger getLog() {
		if(log == null) {
			log = LogManager.getLogger(Filters.class);
		}
		
		return log;
	}
	
    // If a user manually manipulates paths and forgets to add
    // a trailing slash, redirect the user to the correct path
    public static Filter addTrailingSlashes = (Request request, Response response) -> {
        if (!request.pathInfo().endsWith("/")) {
            response.redirect(request.pathInfo() + "/");
        }
    };

    // Locale change can be initiated from any page
    // The locale is extracted from the request and saved to the user's session
    public static Filter handleLocaleChange = (Request request, Response response) -> {
        if (RemoveRequestUtil.getQueryLocale(request) != null) {
            request.session().attribute("locale", RemoveRequestUtil.getQueryLocale(request)); 
            response.redirect(request.pathInfo());
        }
    };

    // Enable GZIP for all responses
    public static Filter addGzipHeader = (Request request, Response response) -> {
        response.header("Content-Encoding", "gzip");
    };
    
    // Imprime traza por cada request
    public static Filter printInfoResponse = (Request request, Response response) -> {
    	getLog().info("RESPONSE:" + "\nBody: "+ response.body());
    };
    public static Filter saveAudit = (Request request, Response response) -> {
//    	AuditAccessData.init().recordAccess(request, response);
    };
    
    public static Filter validateAccess = (Request request, Response response) -> {
    	// No son servicios de Options
    	try {
    		if(!isOptions(request)) {
				if (!isExternalAccess(request) && !isIgnoreTokenAccess(request)) {
					RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
//    				getLog().info("Filter Token: "+ token);
    				
    				boolean refreshToken = !request.uri().equals(ERestPath.COMMON_NOTIFICATIONS.getFullPath());
    				
    				if (isAdminAccess(request)) {
//    					getLog().info("Is admin access...");
    					validateAdminAccess(token, refreshToken);
            		} else if (isClientAccess(request)) {
//            			getLog().info("Is client access...");
            			validateClientAccess(token, refreshToken);
            		} else if (isCommonAccess(request)) {
//            			getLog().info("Is common access...");
            			validateCommonAccess(token, refreshToken);
            		}
				}
    		}
    	} catch (RemoveApplicationException e) {
			if (e.getError() != null) {
				getLog().error(e.getError(), e);
				halt(e.getError().getCode(), 
	    				(String) RemoveResponseUtil.buildError(e.getError()));
			} else {
				getLog().error(EMessageBundleKeys.ERROR_AUTHENTICATION_REQUIRED, e);
				halt(EMessageBundleKeys.ERROR_AUTHENTICATION_REQUIRED.getCode(), 
	    				(String) RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_AUTHENTICATION_REQUIRED));
			}
    	}
    };


    private static boolean isExternalAccess(Request request) {
		return request.uri().startsWith(ERestPath.XTERNAL.getFullPath());
	}
    private static boolean isIgnoreTokenAccess(Request request) {
		return request.uri().equals(ERestPath.ADMIN_LOGIN.getFullPath()) || 
				request.uri().equals(ERestPath.CLIENT_LOGIN.getFullPath()) ||
				request.uri().equals(ERestPath.HEALTHCHECK.getFullPath()) ||
				request.uri().equals(ERestPath.COMMON_PLANS.getFullPath());
	}
    private static boolean isAdminAccess(Request request) {
    	return request.uri().startsWith(ERestPath.ADMIN.getFullPath());
	}
	private static boolean isClientAccess(Request request) {
		return request.uri().startsWith(ERestPath.CLIENT.getFullPath());
	}
	private static boolean isCommonAccess(Request request) {
		return request.uri().startsWith(ERestPath.COMMON.getFullPath()) || request.uri().equals(ERestPath.LOGOUT.getFullPath());
	}
	
	private static void validateAdminAccess(RemoveTokenAccess token, boolean refreshToken) throws RemoveApplicationException {
//		getLog().info("Validating Admin Access");
		
		User user = UserDao.init().getBasicByUuid(token.getUuidAccount());
		UserSession session = UserSessionDao.init().getByUserId(user.getId());
		
		if (session == null) {
			getLog().error("Session active not found:" + token.getToken());
			closeUserSession(token);
		} else {
//			getLog().info("Session found: " + session.getEntry_date());
		}
		
		if (!session.getToken().equals(token.getToken())) {
			getLog().error("Token different from last active:" + token.getToken());
			closeUserSession(token);
		} else {
//			getLog().info("Token Confirmed: " + session.getToken());
		}
		
		if (isTokenExpired(session.getRefresh_date())) {
			getLog().warn("Session expired: " + session.getRefresh_date());
			closeUserSession(token);
		} else {
//			getLog().info("Session not expired: " + session.getRefresh_date());
		}
		
		if (refreshToken) {
			UserSessionDao.init().refresh(token.getToken());	
		}
	}
	
	private static void closeUserSession(RemoveTokenAccess token) throws RemoveApplicationException {
		getLog().warn("Closing session of user token: " + token);
		UserSessionDao.init().close(token.getToken());
		RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_AUTHENTICATION_REQUIRED);
	}
	private static void closeClientSession(RemoveTokenAccess token) throws RemoveApplicationException {
		getLog().warn("Closing session of client token: " + token);
		ClientSessionDao.init().close(token.getToken());
		RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_AUTHENTICATION_REQUIRED);
	}
	
	private static void validateClientAccess(RemoveTokenAccess token, boolean refreshToken) throws RemoveApplicationException {
//		getLog().info("Validating Client Access");
		
		Client client = ClientDao.init().getBasicByUuid(token.getUuidAccount());
		ClientSession session = ClientSessionDao.init().getByClientId(client.getId());
		
		if (session == null) {
			getLog().error("Session active not found:" + token.getToken());
			closeClientSession(token);
		} else {
//			getLog().info("Session found: " + session.getEntry_date());
		}
		if (!session.getToken().equals(token.getToken())) {
			getLog().error("Token different from last active:" + token.getToken());
			closeClientSession(token);
		} else {
//			getLog().info("Token Confirmed: " + session.getToken());
		}
		
		if (isTokenExpired(session.getRefresh_date())) {
			closeClientSession(token);
		} else {
//			getLog().info("Session not expired: " + session.getRefresh_date());
		}
		
		if (refreshToken) {
			ClientSessionDao.init().refresh(token.getToken());	
		}
	}
	
	private static boolean isTokenExpired(LocalDateTime refreshTokenTime) throws RemoveApplicationException {
//		getLog().info("Verifying token time: " + refreshTokenTime + " - " + RemoveDateUtils.nowLocalDateTime());
		return refreshTokenTime
				.plusMinutes(ConfigurationDao.init().getTokenExpires())
				.isBefore(RemoveDateUtils.nowLocalDateTime());
	}
	private static void validateCommonAccess(RemoveTokenAccess token, boolean refreshToken) throws RemoveApplicationException {
		if (token.isAdmin()) {
			validateAdminAccess(token, refreshToken);
		} else if (token.isClient()) {
			validateClientAccess(token, refreshToken);
		} else {
			getLog().error("What are you doing here???? " + token.getKind());
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_AUTHENTICATION_REQUIRED);
		}
	}
	
	private static boolean isOptions(Request request) {
		return request.requestMethod().equals("OPTIONS");
	}


    // Imprime traza por cada request
    public static Filter printInfoRequest = (Request request, Response response) -> {
    	if (!isOptions(request) && !request.uri().equals("/remove/api/rest/common/notifications")) {
        	getLog().info("REQUEST:"
        			+ "\nUri: "+ request.uri()
        			+ "\nUrl: "+ request.url() 
        			+ "\nMethod: "+ request.requestMethod() 
        			+ "\nParams: "+ printQParams(request)
        			+ "\nagent: "+ request.userAgent());
    	}
    };
	private static String printQParams(Request request) {
		String concat = "";
		
		if(!request.queryParams().isEmpty()) {
			for(String k : request.queryParams()) {
				concat += k + ": " + request.queryParams(k) + ", ";
			}
		}
		
		return concat;
	}
}
