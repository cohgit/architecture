package cl.atianza.remove.utils;

import spark.Filter;
import spark.Request;
import spark.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Filtros que se ejecutan antes o después de la llamada a los servicios.
 * 
 * @author Jose Gutierrez
 */
public class EngineFilters {
	private static Logger log;
	
	private static Logger getLog() {
		if(log == null) {
			log = LogManager.getLogger(EngineFilters.class);
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
    
	private static boolean isOptions(Request request) {
		return request.requestMethod().equals("OPTIONS");
	}

    // Imprime traza por cada request
    public static Filter printInfoRequest = (Request request, Response response) -> {
    	if (isOptions(request)) {
        	getLog().info("REQUEST:"
        			+ "\nUri: "+ request.uri()
        			+ "\nUri: "+ request.url() 
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
