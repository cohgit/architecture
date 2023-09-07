package cl.atianza.remove;

import static spark.Spark.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.commons.ConfigurationDao;
import cl.atianza.remove.daos.commons.DynamicFormDao;
import cl.atianza.remove.daos.commons.SchemaDao;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.RemoveTaskHelper;
import cl.atianza.remove.helpers.tests.TestDeindexationFormCreator2;
import cl.atianza.remove.models.commons.DynamicForm;
import cl.atianza.remove.properties.RemoveEngineProperties;
import cl.atianza.remove.utils.EngineFilters;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveRestPathBuilder;

/**
 * Main App Class.
 * @author Jose Gutierrez 
 */
public class RemoveEngineApp {
    private static Logger log = LogManager.getLogger(RemoveEngineApp.class);
    
    /**
     * Main Method
     * @param args (Deprecated)
     */
	public static void main(String[] args) {
		log.info("Starting " + RemoveEngineApp.class + "...");
		
    	// Instantiate your dependencies
    	//DAOs
    	try {
			configureServer();
			
			path(RemoveEngineProperties.init().getProperties().getRestPath(), () -> {
	    	    before("/*", (request, response) -> {});
	    	    get(ERestPath.HEALTHCHECK.getPath(), (req, res) -> {return RemoveResponseUtil.buildOk("OK");});
	    	    // Publish services if needed...
	    	});
		
			
	    	
	    	log.info(RemoveEngineApp.class + "  Engine: Version '2022/07/04' Started: Service at: {{ServicePath}}:" 
	    			+ RemoveEngineProperties.init().getProperties().getPort() + "/" + RemoveEngineProperties.init().getProperties().getRestPath());
		} catch (RemoveApplicationException e) {
			log.error("Error starting app: ", e);
		}
    }

	/**
	 * Configure server params
	 * @throws RemoveApplicationException 
	 */
    private static void configureServer() throws RemoveApplicationException {
        port(Integer.valueOf(RemoveEngineProperties.init().getProperties().getPort()));
        
        databaseVerification();
        deindexationTemplateVerification();
        
        RemoveTaskHelper.initialize();
        
        enableCORS("*", "GET,PUT,POST,DELETE,OPTIONS", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        setBeforeFilters();
        setAfterFilters();
    }
    
    @Deprecated
    private static void deindexationTemplateVerification() {
		try {
			log.info("Verifying Deindexation Template...");
			DynamicForm activeForm = DynamicFormDao.init().getTemplateActiveForClientUtility();
			
			if (activeForm == null) {
				log.info("Deindexation Template Not Found... Creating a New One...");
				new TestDeindexationFormCreator2().createVersionOneDotOne();
				log.info("Deindexation Template Created...");
			} else {
				log.info("Deindexation Template Found... No need to create a new one...");
			}
		} catch (RemoveApplicationException e) {
			log.error("Couldn't check deindexation templates.", e);
		}
	}

	/**
     * Verify database connection, if fails then try to restore database.
     * @throws RemoveApplicationException
     */
    private static void databaseVerification() throws RemoveApplicationException {
    	log.info("Verifying database access...");
		if (!ConfigurationDao.init().isValidGUSchema()) {
			log.warn("Remove Scheme not found... Creating...");
			SchemaDao.init().createSchema();
			log.warn("Remove Scheme Created...");
		}
		log.info("Verifying schema updates...");
//		SchemaDao.init().updateSchemas();
		log.info("Database access is correct...");
	}

	/**
     * Set filters to execute before every REST request
     */
    private static void setBeforeFilters() {
    	// Set up before-filters (called before each get/post)
//        before("*",                  Filters.addTrailingSlashes);
//        before("*",                  Filters.handleLocaleChange);
    	before("*",                  EngineFilters.printInfoRequest);
    }
    
    /**
     * Set filters to execute after every REST request
     */
    private static void setAfterFilters() {
//    	after("*", Filters.printInfoResponse);
//    	after("*", Filters.saveAudit);
    }
    
    // Enables CORS on requests. This method is an initialization method and should be called once.
    private static void enableCORS(final String origin, final String methods, final String headers) {
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
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }
}
