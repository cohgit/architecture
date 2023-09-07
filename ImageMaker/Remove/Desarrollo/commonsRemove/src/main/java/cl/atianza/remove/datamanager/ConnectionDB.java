package cl.atianza.remove.datamanager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sql2o.Sql2o;
import org.sql2o.converters.Converter;
import org.sql2o.quirks.PostgresQuirks;

import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.helpers.ConfigHelper;
import cl.atianza.remove.utils.converters.LocalDateConverter;
import cl.atianza.remove.utils.converters.LocalDateTimeConverter;
import cl.atianza.remove.utils.converters.LocalTimeConverter;

/**
 * Class dedicated to database connections.
 * 
 * @author Jose Gutierrez
 */
public class ConnectionDB {
	private static Logger log = null;
	private static Sql2o sql2oWO = null;
	private static Sql2o sql2oRO = null;
	
	/**
	 * Build a Sql2o instance.
	 * @return
	 * @throws RemoveApplicationException 
	 */
	public static Sql2o getSql2oWO() throws RemoveApplicationException {
		if(sql2oWO == null) {
			initConnectionWithHikariWO();
		}

		return sql2oWO;
	}
	
	/**
	 * Build a Sql2o instance.
	 * @return
	 * @throws RemoveApplicationException 
	 */
	public static Sql2o getSql2oRO() throws RemoveApplicationException {
		if(sql2oRO == null) {
			initConnectionWithHikariRO();
		}

		return sql2oRO;
	}
	
	/**
	 * Initialize connection with Datasource configuration (JNDI)
	 * @param prop
	 * @throws RemoveApplicationException
	 */
	private static void initConnectionWithHikariWO() throws RemoveApplicationException {
		getLog().info("Connecting with Hikari WO...");
		boolean connected = false;
		int timesTried = 1;
		
		do {
			try {
				sql2oWO = new Sql2o(HikariDS.getDatasourceWO(), buildQuirks());
				connected = true;
				getLog().info("BD WO:........ "+ sql2oWO.getDataSource());
			} catch (Exception e) {
				getLog().error("Error conectando con base de datos (DS): [Intento: "+timesTried+"]: ", e);
				timesTried++;
				
				try {
					Thread.sleep(ConfigHelper.getDBRetryWaitTime());
				} catch (InterruptedException e1) {
					getLog().error("No puede esperar para reintentar:", e1);
				}
			}	
		} while (!connected && timesTried <= ConfigHelper.getDBRetryConnectionTimes());
		
		if(!connected) {
			RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_CONNECTION_DATABASE);
		}
	}
	/**
	 * Initialize connection with Datasource configuration (JNDI)
	 * @param prop
	 * @throws RemoveApplicationException
	 */
	private static void initConnectionWithHikariRO() throws RemoveApplicationException {
		getLog().info("Connecting with Hikari RO...");
		boolean connected = false;
		int timesTried = 1;
		
		do {
			try {
				sql2oRO = new Sql2o(HikariDS.getDatasourceRO(), buildQuirks());
				connected = true;
				getLog().info("BD RO:........ "+ sql2oRO.getDataSource());
			} catch (Exception e) {
				getLog().error("Error conectando con base de datos (DS): [Intento: "+timesTried+"]: ", e);
				timesTried++;
				
				try {
					Thread.sleep(ConfigHelper.getDBRetryWaitTime());
				} catch (InterruptedException e1) {
					getLog().error("No puede esperar para reintentar:", e1);
				}
			}	
		} while (!connected && timesTried <= ConfigHelper.getDBRetryConnectionTimes());
		
		if(!connected) {
			RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_CONNECTION_DATABASE);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private static PostgresQuirks buildQuirks() {
		@SuppressWarnings("rawtypes")
		final Map<Class, Converter> mappers = new HashMap<>();
	    mappers.put(LocalDate.class, new LocalDateConverter());
	    mappers.put(LocalDateTime.class, new LocalDateTimeConverter());
	    mappers.put(LocalTime.class, new LocalTimeConverter());
	    
		return new PostgresQuirks(mappers);
	}
	
	/**
	 * Get log for stacktraces.
	 * @return
	 */
	private static Logger getLog() {
		if(log == null) {
			log = LogManager.getLogger(ConnectionDB.class);
		}
		
		return log;
	}
}
