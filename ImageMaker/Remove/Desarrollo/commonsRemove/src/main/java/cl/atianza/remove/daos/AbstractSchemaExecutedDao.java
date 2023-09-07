package cl.atianza.remove.daos;

import org.apache.logging.log4j.Logger;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.SchemaExecuted;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * This abstract class have parameters and functions used for record and check schema changes in database.
 * @author pilin
 *
 */
public abstract class AbstractSchemaExecutedDao extends RemoveDao {
	public AbstractSchemaExecutedDao(Logger _log, String table_name) throws RemoveApplicationException {
		super(_log, table_name);
	}

	protected static final String FIELD_NAME = "name";
	protected static final String FIELD_SCHEMA_EXECUTED = "schema_executed";
	protected static final String FIELD_SUCCESS = "success";
	protected static final String FIELD_DATE = "date";
	protected static final String FIELD_MESSAGE = "message";

	/**
	 * Record when a new schema was executed against database.
	 * @param executed
	 * @return
	 * @throws RemoveApplicationException
	 */
	public SchemaExecuted save(SchemaExecuted executed) throws RemoveApplicationException {
		executed.setDate(RemoveDateUtils.nowLocalDateTime());
    	String QUERY = buildInsertQuery(getTable(), FIELD_NAME, FIELD_SUCCESS, FIELD_DATE, FIELD_MESSAGE, FIELD_SCHEMA_EXECUTED);
    	
    	Long idRecord = INIT_RECORD_VALUE;
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_NAME, executed.getName())
    				.addParameter(FIELD_SUCCESS, executed.isSuccess())
    				.addParameter(FIELD_DATE, executed.getDate())
    				.addParameter(FIELD_SCHEMA_EXECUTED, executed.getSchema_executed())
    				.addParameter(FIELD_MESSAGE, executed.getMessage()).executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	executed.setId(idRecord);
        return executed;
    }
	
	/**
	 * Obtain info from an database schema executed
	 * @param scriptName
	 * @param schemaCode
	 * @return
	 * @throws RemoveApplicationException
	 */
	public SchemaExecuted getSuccessByNameAndSchema(String scriptName, String schemaCode) throws RemoveApplicationException {
		String QUERY = buildSelectManyWheres(getTable(), FIELD_NAME, FIELD_SCHEMA_EXECUTED, FIELD_SUCCESS);
		
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

	protected abstract String getTable();
}
