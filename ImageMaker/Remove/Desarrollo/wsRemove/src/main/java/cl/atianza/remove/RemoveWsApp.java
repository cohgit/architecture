package cl.atianza.remove;

import static spark.Spark.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.api.admin.alerts.AdminAlertAndAlarmController;
import cl.atianza.remove.api.admin.clients.AdminClientAttemptController;
import cl.atianza.remove.api.admin.clients.AdminClientAuditController;
import cl.atianza.remove.api.admin.clients.AdminClientController;
import cl.atianza.remove.api.admin.clients.AdminClientPaymentController;
import cl.atianza.remove.api.admin.configs.AdminConfigController;
import cl.atianza.remove.api.admin.dashboards.AdminDashboardController;
import cl.atianza.remove.api.admin.deindexation.AdminDeindexationController;
import cl.atianza.remove.api.admin.deindexation.AdminDeindexationHistoricController;
import cl.atianza.remove.api.admin.deindexation.AdminDeindexationStatusController;
import cl.atianza.remove.api.admin.deindexation.AdminDeindexationUrlController;
import cl.atianza.remove.api.admin.forbiddenwords.AdminForbiddenWordController;
import cl.atianza.remove.api.admin.healthcheck.AdminHealthCheckController;
import cl.atianza.remove.api.admin.login.AdminLoginController;
import cl.atianza.remove.api.admin.plan.AdminPlanController;
import cl.atianza.remove.api.admin.plan.AdminPlanSuggestionController;
import cl.atianza.remove.api.admin.reports.AdminReportController;
import cl.atianza.remove.api.admin.scanners.AdminScannerController;
import cl.atianza.remove.api.admin.scanners.comment.AdminScannerCommentController;
import cl.atianza.remove.api.admin.scanners.impulses.AdminImpulseApproverController;
import cl.atianza.remove.api.admin.scanners.impulses.AdminImpulseController;
import cl.atianza.remove.api.admin.scanners.impulses.AdminImpulsePublishController;
import cl.atianza.remove.api.admin.scanners.impulses.AdminImpulseVariableController;
import cl.atianza.remove.api.admin.scanners.snippets.AdminScannerSnippetController;
import cl.atianza.remove.api.admin.trackingwords.AdminTrackingWordController;
import cl.atianza.remove.api.admin.users.AdminUserAuditController;
import cl.atianza.remove.api.admin.users.AdminUserController;
import cl.atianza.remove.api.client.alerts.ClientAlertAndAlarmController;
import cl.atianza.remove.api.client.configs.ClientConfigController;
import cl.atianza.remove.api.client.deindexation.ClientDeindexationController;
import cl.atianza.remove.api.client.deindexation.ClientDeindexationHistoricController;
import cl.atianza.remove.api.client.deindexation.ClientDeindexationStatusController;
import cl.atianza.remove.api.client.login.ClientLoginController;
import cl.atianza.remove.api.client.scanners.ClientScannerController;
import cl.atianza.remove.api.client.scanners.comment.ClientScannerCommentController;
import cl.atianza.remove.api.client.scanners.impulses.ClientImpulseApproverController;
import cl.atianza.remove.api.client.scanners.impulses.ClientImpulseController;
import cl.atianza.remove.api.client.scanners.snippets.ClientScannerSnippetController;
import cl.atianza.remove.api.commons.CommonController;
import cl.atianza.remove.api.commons.logout.LogoutController;
import cl.atianza.remove.api.reports.ClientReportController;
import cl.atianza.remove.api.xternal.commons.XternalCommonController;
import cl.atianza.remove.api.xternal.password.XternalClientPasswordController;
import cl.atianza.remove.api.xternal.password.XternalUserPasswordController;
import cl.atianza.remove.api.xternal.suscriptions.XternalSuscriptionController;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ConfigHelper;
import cl.atianza.remove.properties.RemoveWsProperties;
import cl.atianza.remove.properties.RemoveWsProps;
import cl.atianza.remove.utils.Filters;
import cl.atianza.remove.utils.RemoveRestPathBuilder;
import spark.Route;

/**
 * Main App Class.
 * @author Jose Gutierrez
 */
public class RemoveWsApp {
    private static Logger log = LogManager.getLogger(RemoveWsApp.class);
    
    /**
     * Main Method
     * @param args (Deprecated)
     */
	public static void main(String[] args) throws RemoveApplicationException {
		log.info("Starting " + RemoveWsApp.class + "...");
		
		log.info("LOG DE PRUEBA PARA DESPLIEGUE EN QA ....");
    	int port = isDevelop() ? Integer.valueOf(ConfigHelper.getServicesPort(RemoveWsProperties.init().getProperties().getPort())) : 80;
		
		configureSecurity();
		configureServer(port);


		path(RemoveWsProperties.init().getProperties().getRestPath(), () -> {
		    before("/*", (request, response) -> {
		    });
		    
		    
		    
		    // Administrator API 
		    RemoveRestPathBuilder.build(new AdminLoginController());
		    RemoveRestPathBuilder.build(new AdminConfigController());
		    
		    RemoveRestPathBuilder.build(new AdminDashboardController());
		    
		    RemoveRestPathBuilder.build(new AdminScannerController());
		    RemoveRestPathBuilder.build(new AdminScannerSnippetController());
		    
		    RemoveRestPathBuilder.build(new AdminImpulseController());
		    RemoveRestPathBuilder.build(new AdminImpulseApproverController());
		    RemoveRestPathBuilder.build(new AdminImpulsePublishController());
		    RemoveRestPathBuilder.build(new AdminImpulseVariableController());
		    
		    RemoveRestPathBuilder.build(new AdminDeindexationController());
		    RemoveRestPathBuilder.build(new AdminDeindexationStatusController());
		    RemoveRestPathBuilder.build(new AdminDeindexationHistoricController());
		    RemoveRestPathBuilder.build(new AdminDeindexationUrlController());
		    
		    RemoveRestPathBuilder.build(new AdminUserController());
		    RemoveRestPathBuilder.build(new AdminUserAuditController());
		    RemoveRestPathBuilder.build(new AdminClientController());
		    RemoveRestPathBuilder.build(new AdminClientPaymentController());
		    RemoveRestPathBuilder.build(new AdminClientAuditController());
		    RemoveRestPathBuilder.build(new AdminClientAttemptController());
		    RemoveRestPathBuilder.build(new AdminPlanController());
		    RemoveRestPathBuilder.build(new AdminPlanSuggestionController());
		    RemoveRestPathBuilder.build(new AdminTrackingWordController());
		    RemoveRestPathBuilder.build(new AdminForbiddenWordController());
		    RemoveRestPathBuilder.build(new AdminReportController());
		    
		    RemoveRestPathBuilder.build(new AdminScannerCommentController());
		    RemoveRestPathBuilder.build(new AdminAlertAndAlarmController());
		    RemoveRestPathBuilder.build(new AdminHealthCheckController());

		    
		    // Client API
		    RemoveRestPathBuilder.build(new ClientLoginController());
		    RemoveRestPathBuilder.build(new ClientConfigController());
		    
		    RemoveRestPathBuilder.build(new ClientScannerController());
		    RemoveRestPathBuilder.build(new ClientScannerSnippetController());
		    RemoveRestPathBuilder.build(new ClientImpulseController());
		    RemoveRestPathBuilder.build(new ClientImpulseApproverController());
		    
		    RemoveRestPathBuilder.build(new ClientDeindexationController());
		    RemoveRestPathBuilder.build(new ClientDeindexationStatusController());
		    RemoveRestPathBuilder.build(new ClientDeindexationHistoricController());
		    RemoveRestPathBuilder.build(new ClientReportController());

		    RemoveRestPathBuilder.build(new ClientScannerCommentController());
		    RemoveRestPathBuilder.build(new ClientAlertAndAlarmController());
		    
		    // Externals API
		    RemoveRestPathBuilder.build(new XternalSuscriptionController());
		    RemoveRestPathBuilder.build(new XternalUserPasswordController());
		    RemoveRestPathBuilder.build(new XternalClientPasswordController());
		    
		    
		    RemoveRestPathBuilder.build(new LogoutController());
		    CommonController.buildPaths();
		    XternalCommonController.buildPaths();
		});
		
		
		log.info(RemoveWsApp.class + " Web Services: Version '2022/07/05' Started: Service at: {{ServicePath}}:" + port
				+ "/" + RemoveWsProperties.init().getProperties().getRestPath());
    }


	/**
	 * Configure Security parameters if needed (Deprecated: AWS handles security access)
	 */
	private static void configureSecurity() {
		try {
			RemoveWsProps props = RemoveWsProperties.init().getProperties();
			
			log.info("Connection secure by ssl: "+props.isUseSecure());
			if (props.isUseSecure()) {
				secure(props.getSecureKey(), props.getSecurePassword(), null, null);
				// BasicConfigurator.configure();
			}	
		} catch (Exception e) {
			log.error("Error configuring security..." , e);
		}
	}

	/**
	 * Configure server params (Need refactor: change to get these parameters from environment in pod)
	 * @throws RemoveApplicationException 
	 */
    private static void configureServer(int port) throws RemoveApplicationException {
    	port(port);
        
        /*String headersForCloud = ConfigHelper.getHeadersForCloud();
        log.info("Headers For Cloud: " + headersForCloud);
        
        if (headersForCloud != null && headersForCloud.equalsIgnoreCase("true")) {
        	log.info("Setting headers for Cloud...");
        	configHeadersForCloud();
        } else {
        	log.info("Setting headers for Develop...");
        	setHeaderServices("*", "GET,PUT,POST,DELETE,OPTIONS", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,", null);	
        }*/
        
    	log.info("ConfigHelper.getHeadersForProd: " + ConfigHelper.getHeadersForProd());
    	log.info("ConfigHelper.getHeadersForQA: " + ConfigHelper.getHeadersForQA());
        if (ConfigHelper.getHeadersForProd() != null && ConfigHelper.getHeadersForProd().equalsIgnoreCase("true")) {
        	log.info("Setting headers for Production...");
        	//setHeaderServices("https://app.removegroup.com", "GET,PUT,POST,DELETE,OPTIONS", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,", "true");
        	setHeaderServices("https://removesolutions.com", "GET,PUT,POST,DELETE,OPTIONS", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,", "true");
        } else if (ConfigHelper.getHeadersForQA() != null && ConfigHelper.getHeadersForQA().equalsIgnoreCase("true")) {
        	log.info("Setting headers for QA...");
        	//setHeaderServices("http://backendqa.removesolutions.com", "GET,PUT,POST,DELETE,OPTIONS", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,", "true");
        	setHeaderServices("https://qa.removesolutions.com", "GET,PUT,POST,DELETE,OPTIONS", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,", "true");
        	//setHeaderServices("https://qa.removegroup.com", "GET,PUT,POST,DELETE,OPTIONS", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,", "true");
        	
        } else {
        	log.info("Setting headers for Develop...");
        	setHeaderServices("*", "GET,PUT,POST,DELETE,OPTIONS", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,", null);
        }
        
        setBeforeFilters();
        setAfterFilters();
    }
    
    /**
     * Delete this after refactor on envirnoment variables.
     * @return
     */
    @Deprecated
	private static boolean isDevelop() {
		return ConfigHelper.getHeadersForProd() == null && ConfigHelper.getHeadersForQA() == null;
	}

	/**
     * Set filters to execute before every REST request
     */
    private static void setBeforeFilters() {
    	// Set up before-filters (called before each get/post)
//        before("*",                  Filters.addTrailingSlashes);
//        before("*",                  Filters.handleLocaleChange);
    	before("*",                  Filters.printInfoRequest);
    	before("*",                  Filters.validateAccess);
    }
    
    /**
     * Set filters to execute after every REST request
     */
    private static void setAfterFilters() {
//    	after("*", Filters.printInfoResponse);
//    	after("*", Filters.saveAudit);
    }
    
    // Enables CORS on requests. This method is an initialization method and should be called once.
    private static void setHeaderServices(final String origin, final String methods, final String headers, String allowCredentials) {
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            
            if (allowCredentials != null) {
            	response.header("Access-Control-Allow-Credentials", allowCredentials);	
            }
            	
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }
    
    // Enables CORS on requests. This method is an initialization method and should be called once.
    @Deprecated
    private static void configHeadersForCloud() {
    	log.info("Headers for cloud... ");
    	String accessControlAllowOrigin = ConfigHelper.getAccessControlAllowOrigin();
    	String accessControlRequestMethod = ConfigHelper.getAccessControlRequestMethod();
    	String accessControlAllowHeaders = ConfigHelper.getAccessControlAllowHeaders();
    	String accessControlAllowCredentials = ConfigHelper.getAccessControlAllowCredentials();
    	
        log.info("Access-Control-Allow-Origin: " + accessControlAllowOrigin);
        log.info("Access-Control-Request-Method: " + accessControlRequestMethod);
        log.info("Access-Control-Allow-Headers: " + accessControlAllowHeaders);
        log.info("Access-Control-Allow-Credentials: " + accessControlAllowCredentials);
        
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethods = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethods != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethods);
            }

            return "OK";
        });

        before((request, response) -> {
        	log.info("Adding headers for cloud: " + request.uri());
        	log.info("Adding Access-Control-Allow-Origin: " + accessControlAllowOrigin);
            log.info("Adding Access-Control-Request-Method: " + accessControlRequestMethod);
            log.info("Adding Access-Control-Allow-Headers: " + accessControlAllowHeaders);
            log.info("Adding Access-Control-Allow-Credentials: " + accessControlAllowCredentials);
            
        	if (accessControlAllowOrigin != null) {
        		response.header("Access-Control-Allow-Origin", accessControlAllowOrigin);	
        	}
        	if (accessControlRequestMethod != null) {
        		response.header("Access-Control-Request-Method", accessControlRequestMethod);	
        	}
        	if (accessControlAllowHeaders != null) {
        		response.header("Access-Control-Allow-Headers", accessControlAllowHeaders);	
        	}
        	if (accessControlAllowCredentials != null) {
        		response.header("Access-Control-Allow-Credentials", accessControlAllowCredentials);
        	}
        	
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }
}
