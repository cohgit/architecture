package cl.atianza.remove.helpers;

import cl.atianza.remove.properties.RemoveCommonsProperties;
import cl.atianza.remove.properties.RemoveDBConnProps;

public class ConfigHelper {
	private static final String REMOVE_SERVICES_PORT = "REMOVE_SERVICES_PORT";
	
	private static final String REMOVE_DB_HOST_WO = "REMOVE_DB_HOST_WO";
	private static final String REMOVE_DB_HOST_RO = "REMOVE_DB_HOST_RO";
    private static final String REMOVE_DB_PORT = "REMOVE_DB_PORT";
    private static final String REMOVE_DB_NAME = "REMOVE_DB_NAME";
    private static final String REMOVE_DB_USER = "REMOVE_DB_USER";
    private static final String REMOVE_DB_PASSWORD = "REMOVE_DB_PASSWORD";
    
    private static final String REMOVE_DB_PARAMS = "REMOVE_DB_PARAMS";
    private static final String REMOVE_DB_POOL_CACHE_PREP_STMTS = "REMOVE_DB_POOL_CACHE_PREP_STMTS";
    private static final String REMOVE_DB_POOL_PREP_STMT_CACHE_SIZE = "REMOVE_DB_POOL_PREP_STMT_CACHE_SIZE";
    private static final String REMOVE_DB_POOL_PREP_STMT_CACHE_SQL_LIMIT = "REMOVE_DB_POOL_PREP_STMT_CACHE_SQL_LIMIT";
    
    private static final String REMOVE_DB_RETRY_CONNECTION_TIMES = "REMOVE_DB_RETRY_CONNECTION_TIMES";
    private static final String REMOVE_DB_RETRY_WAIT_TIME = "REMOVE_DB_RETRY_WAIT_TIME";
    
    private static final String REMOVE_DB_SCHEMA_NAME = "REMOVE_DB_SCHEMA_NAME";
    
    private static final String REMOVE_DEFAULT_COUNTRY = "REMOVE_DEFAULT_COUNTRY";
    private static final String REMOVE_DEFAULT_LANGUAGE = "REMOVE_DEFAULT_LANGUAGE";
    
    private static final String REMOVE_HEADERS_FOR_CLOUD = "REMOVE_HEADERS_FOR_CLOUD";
    private static final String REMOVE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "REMOVE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN";
    private static final String REMOVE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS = "REMOVE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS";
    private static final String REMOVE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS = "REMOVE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS";
    private static final String REMOVE_HEADER_ACCESS_CONTROL_REQUEST_METHOD = "REMOVE_HEADER_ACCESS_CONTROL_REQUEST_METHOD";
    
    private static final String REMOVE_HEADERS_FOR_PROD = "REMOVE_HEADERS_FOR_PROD";
    private static final String REMOVE_HEADERS_FOR_QA = "REMOVE_HEADERS_FOR_QA";
	
    private static final RemoveDBConnProps prop = RemoveCommonsProperties.init().getProperties().getConnection();
    
    public static String getServicesPort(int defaultValue) {
    	return getEnvParam(REMOVE_SERVICES_PORT, defaultValue);
    }
    
    public static String getDBHostWO() {
    	return getEnvParam(REMOVE_DB_HOST_WO, prop.getHostWO());
    }
    public static String getDBHostRO() {
    	return getEnvParam(REMOVE_DB_HOST_RO, prop.getHostRO());
    }
    public static String getDBPort() {
    	return getEnvParam(REMOVE_DB_PORT, prop.getPort());
    }
    public static String getDBName() {
    	return getEnvParam(REMOVE_DB_NAME, prop.getDbname());
    }
    public static String getDBUser() {
    	return getEnvParam(REMOVE_DB_USER, prop.getUser());
    }
    public static String getDBPassword() {
    	return getEnvParam(REMOVE_DB_PASSWORD, prop.getPassword());
    }
    
    public static String getDBParams() {
    	return getEnvParam(REMOVE_DB_PARAMS);
    }
    public static String getDBPoolCachePrepSTMTS(String defaultParam) {
    	return getEnvParam(REMOVE_DB_POOL_CACHE_PREP_STMTS, defaultParam);
    }
    public static String getDBPrepSTMTCacheSize(String defaultParam) {
    	return getEnvParam(REMOVE_DB_POOL_PREP_STMT_CACHE_SIZE, defaultParam);
    }
    public static String getDBPrepSTMTCacheSQLLimit(String defaultParam) {
    	return getEnvParam(REMOVE_DB_POOL_PREP_STMT_CACHE_SQL_LIMIT, defaultParam);
    }
    
    public static int getDBRetryConnectionTimes() {
    	return Integer.valueOf(getEnvParam(REMOVE_DB_RETRY_CONNECTION_TIMES, prop.getTryTimes()));
    }
    public static int getDBRetryWaitTime() {
    	return Integer.valueOf(getEnvParam(REMOVE_DB_RETRY_WAIT_TIME, prop.getWaitTime()));
    }
    
    public static String getDBSchemaName() {
    	return getEnvParam(REMOVE_DB_SCHEMA_NAME, prop.getSchemaName());
    }
	
	private static String getEnvParam(String param, String defaultValue) {
		String value = getEnvParam(param); 
		return value != null ? value : defaultValue;
	}
	private static String getEnvParam(String param, int defaultValue) {
		String value = getEnvParam(param); 
		return value != null ? value : String.valueOf(defaultValue);
	}
	private static String getEnvParam(String param) {
		return System.getenv(param);
	}
	public static String getDefaultCountry(String defaultValue) {
		return getEnvParam(REMOVE_DEFAULT_COUNTRY, defaultValue);
	}
	public static String getDefaultLanguage(String defaultValue) {
		return getEnvParam(REMOVE_DEFAULT_LANGUAGE, defaultValue);
	}
	
	public static String getHeadersForCloud() {
		return getEnvParam(REMOVE_HEADERS_FOR_CLOUD);
	}
	
	public static String getHeadersForProd() {
		return getEnvParam(REMOVE_HEADERS_FOR_PROD);
	}
	public static String getHeadersForQA() {
		return getEnvParam(REMOVE_HEADERS_FOR_QA);
	}
	
	public static String getAccessControlAllowOrigin() {
		return getEnvParam(REMOVE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN);
	}
	public static String getAccessControlAllowHeaders() {
		return getEnvParam(REMOVE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS);
	}
	public static String getAccessControlAllowCredentials() {
		return getEnvParam(REMOVE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS);
	}
	public static String getAccessControlRequestMethod() {
		return getEnvParam(REMOVE_HEADER_ACCESS_CONTROL_REQUEST_METHOD);
	}
}
