package cl.atianza.remove.daos.commons;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerConfiguration;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Scanner configuration DAOs methods
 * @author pilin
 *
 */
public class ScannerConfigurationDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_configuration";
	
	private static final String FIELD_ID_SCANNER = "id_scanner";
	private static final String FIELD_DEVICE = "device";
	private static final String FIELD_LANGUAGE = "language";
	private static final String FIELD_PAGES = "pages";
	private static final String FIELD_SEARCH_TYPE = "search_type";
	private static final String FIELD_CITIES = "cities";
	private static final String FIELD_IMAGES_COLOR = "images_color";
	private static final String FIELD_IMAGES_SIZE = "images_size";
	private static final String FIELD_IMAGES_TYPE = "images_type";
	private static final String FIELD_IMAGES_USE_RIGHTS = "images_use_rights";
	private static final String FIELD_NEWS_TYPE = "news_type";
	
	private static final String TABLE_COUNTRY_RELATION_NAME = "scanners_configuration_countries";
	private static final String FIELD_REL_COUNTRY_ID_SCANNER_CONFIG = "id_scanner_config";
	private static final String FIELD_REL_COUNTRY_ID_COUNTRY = "id_country";
	
	public ScannerConfigurationDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerConfigurationDao.class), TABLE_NAME);
	}
	
	public static ScannerConfigurationDao init() throws RemoveApplicationException {
		return new ScannerConfigurationDao();
	}	
	
	/**
	 * 
	 * @param idScanner
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerConfiguration get(long idScanner) throws RemoveApplicationException {
		ScannerConfiguration config = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER, idScanner);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			config = conn.createQuery(QUERY).executeAndFetchFirst(ScannerConfiguration.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (config != null) {
			loadCountryList(config);
		}
		
		return config;
	}
	/**
	 * 
	 * @param config
	 * @throws RemoveApplicationException
	 */
	private void loadCountryList(ScannerConfiguration config) throws RemoveApplicationException {
		List<Long> idCountries = null;
		String QUERY = "SELECT " + FIELD_REL_COUNTRY_ID_COUNTRY + " FROM " + schemaTable(TABLE_COUNTRY_RELATION_NAME) + " WHERE " + FIELD_REL_COUNTRY_ID_SCANNER_CONFIG + " = " + config.getId() + "";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			idCountries = conn.createQuery(QUERY).executeAndFetch(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (idCountries != null && !idCountries.isEmpty()) {
			config.setCountries(CountryDao.init().list(idCountries));
		}
	}

	/**
	 * 
	 * @param scanner
	 * @throws RemoveApplicationException
	 */
	public void save(Scanner scanner) throws RemoveApplicationException {
		if (scanner != null && scanner.getConfiguration() != null) {
			scanner.getConfiguration().setId_scanner(scanner.getId());
			scanner.setConfiguration(save(scanner.getConfiguration()));
		}
	}
	/**
	 * 
	 * @param scanner
	 * @throws RemoveApplicationException
	 */
	public void update(Scanner scanner) throws RemoveApplicationException {
		if (scanner != null && scanner.getConfiguration() != null) {
			scanner.setConfiguration(update(scanner.getConfiguration()));
		}
	}
	private ScannerConfiguration update(ScannerConfiguration configuration) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, configuration.getId(), FIELD_PAGES, FIELD_SEARCH_TYPE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_PAGES, configuration.getPages())
    				.addParameter(FIELD_SEARCH_TYPE, configuration.getSearch_type())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return configuration;
	}

	/**
	 * 
	 * @param config
	 * @return
	 * @throws RemoveApplicationException
	 */
	private ScannerConfiguration save(ScannerConfiguration config) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER, FIELD_DEVICE, FIELD_LANGUAGE, FIELD_PAGES, 
				FIELD_SEARCH_TYPE, FIELD_CITIES, FIELD_IMAGES_COLOR, FIELD_IMAGES_SIZE, FIELD_IMAGES_TYPE,
				FIELD_IMAGES_USE_RIGHTS, FIELD_NEWS_TYPE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER, config.getId_scanner())
    				.addParameter(FIELD_DEVICE, config.getDevice())
    				.addParameter(FIELD_LANGUAGE, config.getLanguage())
    				.addParameter(FIELD_PAGES, config.getPages())
    				.addParameter(FIELD_SEARCH_TYPE, config.getSearch_type())
    				.addParameter(FIELD_CITIES, config.getCities())
    				.addParameter(FIELD_IMAGES_COLOR, config.getImages_color())
    				.addParameter(FIELD_IMAGES_SIZE, config.getImages_size())
    				.addParameter(FIELD_IMAGES_TYPE, config.getImages_type())
    				.addParameter(FIELD_IMAGES_USE_RIGHTS, config.getImages_use_rights())
    				.addParameter(FIELD_NEWS_TYPE, config.getNews_type())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	config.setId(idRecord);
    	saveCountryRelation(config);
    	
    	return config;
	}
	/**
	 * 
	 * @param config
	 * @throws RemoveApplicationException
	 */
	private void saveCountryRelation(ScannerConfiguration config) throws RemoveApplicationException {
		if (config != null && config.getCountries() != null) {
			config.getCountries().forEach(country -> {
				try {
					saveCountryRelation(config.getId(), country.getId());
				} catch (RemoveApplicationException e) {
					getLog().error("Error inserting data: ", e);
				}
			});
		}
	}
	/**
	 * 
	 * @param idConfig
	 * @param idCountry
	 * @throws RemoveApplicationException
	 */
	private void saveCountryRelation(Long idConfig, Long idCountry) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(schemaTable(TABLE_COUNTRY_RELATION_NAME), FIELD_REL_COUNTRY_ID_COUNTRY, FIELD_REL_COUNTRY_ID_SCANNER_CONFIG);
		
		try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_REL_COUNTRY_ID_COUNTRY, idCountry)
    				.addParameter(FIELD_REL_COUNTRY_ID_SCANNER_CONFIG, idConfig)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
}
