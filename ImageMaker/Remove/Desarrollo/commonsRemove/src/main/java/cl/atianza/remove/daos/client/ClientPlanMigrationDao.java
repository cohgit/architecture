package cl.atianza.remove.daos.client;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.ClientPlanMigration;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Client-Plan Relation DAOs methods.
 * @author pilin
 *
 */
@Deprecated
public class ClientPlanMigrationDao extends RemoveDao {
	public static final String TABLE_NAME = "clients_plans_migrations";
	
	private static final String FIELD_ID_CLIENT = "id_client";
	private static final String FIELD_ID_CLIENT_PLAN_FROM = "id_client_plan_from";
	private static final String FIELD_ID_CLIENT_PLAN_TO = "id_client_plan_to";
	private static final String FIELD_SCANNERS_TO_MIGRATE = "scanners_to_migrate";
	private static final String FIELD_PAYMENT_PLATFORM = "payment_platform";
	private static final String FIELD_PAYMENT_METHOD_KEY = "payment_method_key";
	private static final String FIELD_CREATION_DATE = "creation_date";
	private static final String FIELD_PROGRAMMED_DATE = "programmed_date";
	private static final String FIELD_EXECUTION_DATE = "execution_date";
	private static final String FIELD_PROGRAMMED = "programmed";
	private static final String FIELD_EXECUTED = "executed";
	
	public ClientPlanMigrationDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ClientPlanMigrationDao.class), TABLE_NAME);
	}

	public static ClientPlanMigrationDao init() throws RemoveApplicationException {
		return new ClientPlanMigrationDao();
	}

	/**
	 * Obtain a Client-Plan Data from client id.
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ClientPlanMigration> listProgrammedNotExecuted() throws RemoveApplicationException {
		List<ClientPlanMigration> list = null;
		String QUERY = buildSelectManyWheres(TABLE, FIELD_PROGRAMMED, FIELD_EXECUTED);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY)
					.addParameter(FIELD_PROGRAMMED, true)
					.addParameter(FIELD_EXECUTED, false)
					.executeAndFetch(ClientPlanMigration.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return list;
    }

	/**
	 * Insert client plan relation into database
	 * @param migration
	 * @return
	 * @throws RemoveApplicationException
	 */
    public ClientPlanMigration save(ClientPlanMigration migration) throws RemoveApplicationException {
    	String QUERY = buildInsertQuery(TABLE, FIELD_ID_CLIENT, FIELD_ID_CLIENT_PLAN_FROM, FIELD_ID_CLIENT_PLAN_TO, 
    			FIELD_SCANNERS_TO_MIGRATE, FIELD_CREATION_DATE, FIELD_PROGRAMMED_DATE, FIELD_EXECUTION_DATE, 
    			FIELD_PROGRAMMED, FIELD_EXECUTED, FIELD_PAYMENT_PLATFORM, FIELD_PAYMENT_METHOD_KEY);
    	
    	Long idRecord = INIT_RECORD_VALUE;
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_CLIENT, migration.getId_client())
    				.addParameter(FIELD_ID_CLIENT_PLAN_FROM, migration.getId_client_plan_from())
    				.addParameter(FIELD_ID_CLIENT_PLAN_TO, migration.getId_client_plan_to())
    				.addParameter(FIELD_SCANNERS_TO_MIGRATE, migration.getScanners_to_migrate())
    				.addParameter(FIELD_PAYMENT_PLATFORM, migration.getPayment_platform())
    				.addParameter(FIELD_PAYMENT_METHOD_KEY, migration.getPayment_method_key())
    				.addParameter(FIELD_CREATION_DATE, migration.getCreation_date())
    				.addParameter(FIELD_PROGRAMMED_DATE, migration.getProgrammed_date())
    				.addParameter(FIELD_EXECUTION_DATE, migration.getExecution_date())
    				.addParameter(FIELD_PROGRAMMED, migration.isProgrammed())
    				.addParameter(FIELD_EXECUTED, migration.isExecuted()).executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	validateIdRecord(idRecord);
    	migration.setId(idRecord);
    	
        return migration;
    }
    /**
     * Update information of Client-Plan in database, canceling suscription to the plan.
     * @param clientPlanMigration
     * @return
     * @throws RemoveApplicationException
     */
    public ClientPlanMigration updateExecutedTrue(ClientPlanMigration clientPlanMigration) throws RemoveApplicationException {
    	getLog().info("Executing migration: " + clientPlanMigration);
    	clientPlanMigration.setExecuted(true);
    	clientPlanMigration.setExecution_date(LocalDateTime.now());
    	
    	String QUERY = buildUpdateQuery(TABLE, FIELD_ID, clientPlanMigration.getId(), FIELD_EXECUTION_DATE, FIELD_EXECUTED);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_EXECUTION_DATE, clientPlanMigration.getExecution_date())
    				.addParameter(FIELD_EXECUTED, clientPlanMigration.isExecuted()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
        return clientPlanMigration;
    }

	/**
	 * @param id_client
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientPlanMigration getProgramedByClient(Long id_client) throws RemoveApplicationException {
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_CLIENT, FIELD_PROGRAMMED, FIELD_EXECUTED);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY)
					.addParameter(FIELD_PROGRAMMED, true)
					.addParameter(FIELD_EXECUTED, false)
					.addParameter(FIELD_ID_CLIENT, id_client)
					.executeAndFetchFirst(ClientPlanMigration.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
}
