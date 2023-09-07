package cl.atianza.remove.daos.commons;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.SchemaBuilder;
import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.SchemaExecuted;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Schema tools DAOs methods.
 * @author pilin
 *
 */
public class SchemaDao extends RemoveDao {
	protected static final String TABLE_NAME = "schema_tools_executeds";
	protected static final String FIELD_NAME = "name";
	protected static final String FIELD_SCHEMA_EXECUTED = "schema_executed";
	protected static final String FIELD_SUCCESS = "success";
	protected static final String FIELD_DATE = "date";
	protected static final String FIELD_MESSAGE = "message";
	
	public SchemaDao() throws RemoveApplicationException {
		super(LogManager.getLogger(SchemaDao.class), TABLE_NAME);
	}
	public static SchemaDao init() throws RemoveApplicationException {
		return new SchemaDao();
	}
	/**
	 * 
	 * @param scriptName
	 * @param schemaCode
	 * @return
	 * @throws RemoveApplicationException
	 */
	public SchemaExecuted getSuccessByNameAndSchema(String scriptName, String schemaCode) throws RemoveApplicationException {
		String QUERY = buildSelectManyWheres(TABLE, FIELD_NAME, FIELD_SCHEMA_EXECUTED, FIELD_SUCCESS);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY)
					.addParameter(FIELD_NAME, scriptName.trim())
					.addParameter(FIELD_SCHEMA_EXECUTED, schemaCode)
					.addParameter(FIELD_SUCCESS, true).executeAndFetchFirst(SchemaExecuted.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
    }
	/**
	 * 
	 * @throws RemoveApplicationException
	 */
	public void createSchema() throws RemoveApplicationException {
		String QUERY = SchemaBuilder.init().buildSchemaQueryDB();
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	ConfigurationDao.init().createConfigParams();
		CountryDao.init().createCountriesData();
		UserDao.init().createDefaultUser();
		
		// PlanDao.init().createDefaultPlans();	
	}
	/**
	 * 
	 */
//	public void updateSchemas() {
//		getLog().info("Running schema tool...");
////		try {
//			getLog().info("Schema tool executed successfully...");
////		} catch (RemoveApplicationException e) {
////			getLog().error("Couldn't execute schema tool correctly, system may run with problems... ", e);
////		}
//	}
	/**
	 * 
	 * @param pendingList
	 * @param executeDao
	 * @throws RemoveApplicationException
	 */
//	private void updateSchema(List<SchemaExecuted> pendingList, AbstractSchemaExecutedDao executeDao) throws RemoveApplicationException {
//		if (pendingList != null && !pendingList.isEmpty()) {
//			pendingList.forEach(pendingScript -> {
//				boolean stop = false;
//				try {
//					if (!stop) {
//						runSchemaUpdate(pendingScript, executeDao);
//					}
//				} catch (RemoveApplicationException e) {
//					getLog().error("Couldn't execute script: "+pendingScript, e);
//					stop = true;
//					
//					pendingScript.setMessage(e.getMessage());
//					pendingScript.setSuccess(false);
//					
//					try {
//						executeDao.save(pendingScript);
//					} catch (RemoveApplicationException e1) {
//						getLog().error("Couldn't save script execution: "+pendingScript, e);
//						stop = true;
//					}
//				}
//			});
//		}
//	}
	/**
	 * 
	 * @param pendingScript
	 * @param executeDao
	 * @throws RemoveApplicationException
	 */
//	private void runSchemaUpdate(SchemaExecuted pendingScript, AbstractSchemaExecutedDao executeDao) throws RemoveApplicationException {
//    	try (Connection con = ConnectionDB.getSql2o().beginTransaction()) {
//    		con.createQuery(pendingScript.getQuery()).executeUpdate();
//    	    
//    	    // Remember to call commit() when a transaction is opened,
//    	    // default is to roll back.
//    	    con.commit();
//    	} catch(Exception ex) {
//    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
//    	}
//    	
//    	pendingScript.setSuccess(true);
//    	executeDao.save(pendingScript);
//	}
}
