package cl.atianza.remove.datamanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import cl.atianza.remove.helpers.ConfigHelper;

/**
 * Class dedicated for Datasource creation (Serverless JNDI)
 * @author pilin
 *
 */
public class HikariDS {
	private static Logger log = LogManager.getLogger(HikariDS.class);
	private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    
    private static HikariConfig configRO = new HikariConfig();
    private static HikariDataSource dsRO;
    
    private static final String DEFAULT_CACHE_PREP_STMTS = "true";
    private static final String DEFAULT_PREP_STMT_CACHE_SIZE = "250";
    private static final String DEFAULT_PREP_STMT_CACHE_SQL_LIMIT = "2048";
    
    static {
    	String hostWO = ConfigHelper.getDBHostWO();
    	String hostRO = ConfigHelper.getDBHostWO();
		String port = ConfigHelper.getDBPort();
		String dbname = ConfigHelper.getDBName();
		String username = ConfigHelper.getDBUser();
		String pswrd = ConfigHelper.getDBPassword();
		String dbParams = ConfigHelper.getDBParams();
		
		String pathWO = hostWO + ":" + port + "/" + dbname;
		pathWO = dbParams == null ? pathWO : pathWO + "?" + dbParams;
		
		String pathRO = hostRO + ":" + port + "/" + dbname;
		pathRO = dbParams == null ? pathRO : pathRO + "?" + dbParams;
		
//		log.info("hostWO: " + hostWO);
//		log.info("hostRO: " + hostRO);
//		log.info("port: " + port);
//		log.info("dbname: " + dbname);
//		log.info("username: " + username);
//		log.info("pswrd: " + pswrd);
//		log.info("dbParams: " + dbParams);
//		log.info("pathWO: " + pathWO);
//		log.info("pathRO: " + pathRO);
		
        config.setJdbcUrl(pathWO);
        config.setUsername(username);
        config.setPassword(pswrd);
        
        config.addDataSourceProperty("cachePrepStmts", ConfigHelper.getDBPoolCachePrepSTMTS(DEFAULT_CACHE_PREP_STMTS));
        config.addDataSourceProperty( "prepStmtCacheSize", ConfigHelper.getDBPrepSTMTCacheSize(DEFAULT_PREP_STMT_CACHE_SIZE));
        config.addDataSourceProperty( "prepStmtCacheSqlLimit", ConfigHelper.getDBPrepSTMTCacheSQLLimit(DEFAULT_PREP_STMT_CACHE_SQL_LIMIT));
        ds = new HikariDataSource( config );
        
        configRO.setJdbcUrl(pathRO);
        configRO.setUsername(username);
        configRO.setPassword(pswrd);
        
        configRO.addDataSourceProperty("cachePrepStmts", ConfigHelper.getDBPoolCachePrepSTMTS(DEFAULT_CACHE_PREP_STMTS));
        configRO.addDataSourceProperty( "prepStmtCacheSize", ConfigHelper.getDBPrepSTMTCacheSize(DEFAULT_PREP_STMT_CACHE_SIZE));
        configRO.addDataSourceProperty( "prepStmtCacheSqlLimit", ConfigHelper.getDBPrepSTMTCacheSQLLimit(DEFAULT_PREP_STMT_CACHE_SQL_LIMIT));
        dsRO = new HikariDataSource( configRO );
    }
 
    private HikariDS() {}
 
    public static HikariDataSource getDatasourceWO() {
        return ds;
    }
    public static HikariDataSource getDatasourceRO() {
        return dsRO;
    }
}
