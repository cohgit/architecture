package cl.atianza.remove.daos.commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.dtos.commons.ConfigurationDto;
import cl.atianza.remove.enums.EConfigurationTags;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.ConfigurationEmail;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Configuration system params DAOs methods.
 * @author pilin
 *
 */
public class ConfigurationDao extends RemoveDao {
	private static final String TABLE_NAME = "configurations";
	
	private static final String FIELD_KEY = "key";
	private static final String FIELD_VALUE = "value";
	
	public ConfigurationDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ConfigurationDao.class), TABLE_NAME);
	}
	
	public static ConfigurationDao init() throws RemoveApplicationException {
		return new ConfigurationDao();
	}
	
	/**
	 * Get time session expiration parameter
	 * @return
	 */
	public Integer getTokenExpires() {
		Integer expires = 120; // Default expires (120 minutos)
		
		try {
			expires = Integer.valueOf(getConfigByKey(EConfigurationTags.TOKEN_EXPIRES.getTag()));
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.TOKEN_EXPIRES);
		}
		
		return expires;
	}
	
	public Object getTransferMinValue() {
		Integer expires = 1000; // Default expires (120 minutos)
		
		try {
			expires = Integer.valueOf(getConfigByKey(EConfigurationTags.TRANSFER_MIN_VALUE.getTag()));
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.TRANSFER_MIN_VALUE);
		}
		
		return expires;
	}
	
	public String getEmailParamClientLoginUrlPage() {
		try {
			return getConfigByKey(EConfigurationTags.EMAIL_CLIENT_LOGIN_URL_PAGE.getTag());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.EMAIL_CLIENT_LOGIN_URL_PAGE);
		}
		
		return "";
	}
	public String getEmailParamClientRecoveryPasswordUrlPage() {
		try {
			return getConfigByKey(EConfigurationTags.EMAIL_CLIENT_RECOVERY_PASSWORD_URL_PAGE.getTag());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.EMAIL_CLIENT_RECOVERY_PASSWORD_URL_PAGE);
		}
		
		return "";
	}
	public String getEmailParamClientVerificationEmailUrlPage() {
		try {
			return getConfigByKey(EConfigurationTags.EMAIL_CLIENT_VERIFICATION_EMAIL_URL_PAGE.getTag());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.EMAIL_CLIENT_VERIFICATION_EMAIL_URL_PAGE);
		}
		
		return "";
	}
	public String getEmailParamCheckoutUrlPage() {
		try {
			return getConfigByKey(EConfigurationTags.EMAIL_CLIENT_CHECKOUT_URL_PAGE.getTag());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.EMAIL_CLIENT_CHECKOUT_URL_PAGE);
		}
		
		return "";
	}
	
	public String getEmailParamUserLoginUrlPage() {
		try {
			return getConfigByKey(EConfigurationTags.EMAIL_USER_LOGIN_URL_PAGE.getTag());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.EMAIL_USER_LOGIN_URL_PAGE);
		}
		
		return "";
	}
	public String getEmailParamUserRecoveryPasswordUrlPage() {
		try {
			return getConfigByKey(EConfigurationTags.EMAIL_USER_RECOVERY_PASSWORD_URL_PAGE.getTag());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.EMAIL_USER_RECOVERY_PASSWORD_URL_PAGE);
		}
		
		return "";
	}
	public String getEmailParamUserVerificationEmailUrlPage() {
		try {
			return getConfigByKey(EConfigurationTags.EMAIL_USER_VERIFICATION_EMAIL_URL_PAGE.getTag());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.EMAIL_USER_VERIFICATION_EMAIL_URL_PAGE);
		}
		
		return "";
	}
	
	public String getScaleSerpTotalCredits() {
		try {
			return getConfigByKey(EConfigurationTags.SCALE_SERP_TOTAL_CREDITS.getTag());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.SCALE_SERP_TOTAL_CREDITS);
		}
		
		return "";
	}
	
	public String getScaleSerpApi() {
		try {
			return getConfigByKey(EConfigurationTags.SCALE_SERP_API_URL.getTag());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.SCALE_SERP_API_URL);
		}
		
		return "";
	}
	
	public String getScaleSerpKey() {
		try {
			return getConfigByKey(EConfigurationTags.SCALE_SERP_API_KEY.getTag());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.SCALE_SERP_API_KEY);
		}
		
		return "";
	}
	
	/**
	 * Get a system parameter from configuration table. 
	 * @param key
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected ConfigurationDto get(EConfigurationTags key) throws RemoveApplicationException {
		ConfigurationDto config = get(key.getTag());
		
		if (config == null) {
			config = new ConfigurationDto(key.getTag(), key.getDefaultValue());
			save(config);
		}
		
		return config;
    }
	/**
	 * Get a system parameter from configuration table. 
	 * @param key
	 * @return
	 */
	private String getConfigByKey(String key) {
		String environmentValue = System.getenv(key);
		
		if (environmentValue != null) {
			return environmentValue;
		}
		
		ConfigurationDto configuracionDto = null;
		
		try {
			configuracionDto = get(key);
			
			if (configuracionDto == null) {
				EConfigurationTags tag = EConfigurationTags.find(key);
				
				if (tag != null && tag.getDefaultValue() != null) {
					insertConfigParam(tag);
					return tag.getDefaultValue();
				}
			}
		} catch (RemoveApplicationException e) {
			getLog().error(EMessageBundleKeys.ERROR_DATABASE.getTag(), e);
		}
		
		return configuracionDto != null ? configuracionDto.getValue() : null;
	}
	/**
	 * Get a system parameter from configuration table. 
	 * @param Key
	 * @return
	 */
	protected String getConfigByKey(EConfigurationTags Key) {
		return getConfigByKey(Key.getTag());
	}
	/**
	 * Update a configuration parameter.
	 * @param key
	 * @param value
	 * @return
	 */
	public String updateConfig(String key, String value) {
		ConfigurationDto conf = new ConfigurationDto(key, value);
		
		try {
			conf = update(conf);
		} catch (RemoveApplicationException e) {
			getLog().error(EMessageBundleKeys.ERROR_DATABASE.getTag(), e);
		}
		
		return conf != null ? conf.getValue() : null;
	}
	/**
	 * Update a configuration parameter.
	 * @param key
	 * @param value
	 * @return
	 */
	public String updateConfig(EConfigurationTags key, String value) {
		return updateConfig(key.getTag(), value);
	}
	
	/**
	 * Get a system parameter from configuration table. 
	 * @param key
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected ConfigurationDto get(String key) throws RemoveApplicationException {
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_KEY, key);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetchFirst(ConfigurationDto.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
    }
	/**
	 * Update a configuration parameter.
	 * @param configuracion
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected ConfigurationDto update(ConfigurationDto configuracion) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_KEY, configuracion.getKey(), FIELD_VALUE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_VALUE, configuracion.getValue()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
        return get(configuracion.getKey());
	}
	/**
	 * Check if database schema is correct.
	 * @return
	 * @throws RemoveApplicationException
	 */
	public boolean isValidGUSchema() throws RemoveApplicationException {
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_KEY, EConfigurationTags.TOKEN_EXPIRES.getTag());
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetchFirst(ConfigurationDto.class) != null;
        } catch (Exception ex) {
        	if (ex.getMessage().contains(TABLE)) {
        		return false;
        	}

        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return true;
	}
	/**
	 * Create configuration system parameters with default values.
	 * @throws RemoveApplicationException
	 */
	public void createConfigParams() throws RemoveApplicationException {
		insertConfigParam(EConfigurationTags.TOKEN_EXPIRES);
		
		insertConfigParam(EConfigurationTags.EMAIL_CLIENT_USERNAME);
		insertConfigParam(EConfigurationTags.EMAIL_CLIENT_PASSWORD);
		insertConfigParam(EConfigurationTags.EMAIL_CLIENT_EMAIL_FROM);
		
		insertConfigParam(EConfigurationTags.EMAIL_PARAM_PREFIX.getTag()+"MAIL.SMTP.HOST", "smtp.gmail.com");
		insertConfigParam(EConfigurationTags.EMAIL_PARAM_PREFIX.getTag()+"MAIL.SMTP.SSL.TRUST", "smtp.gmail.com");
		insertConfigParam(EConfigurationTags.EMAIL_PARAM_PREFIX.getTag()+"MAIL.SMTP.PORT", "587");
		insertConfigParam(EConfigurationTags.EMAIL_PARAM_PREFIX.getTag()+"MAIL.SMTP.AUTH", "true");
		insertConfigParam(EConfigurationTags.EMAIL_PARAM_PREFIX.getTag()+"MAIL.SMTP.STARTTLS.ENABLE", "true");
		
		insertConfigParam(EConfigurationTags.EMAIL_CLIENT_LOGIN_URL_PAGE);
		insertConfigParam(EConfigurationTags.EMAIL_CLIENT_RECOVERY_PASSWORD_URL_PAGE);
		insertConfigParam(EConfigurationTags.EMAIL_CLIENT_VERIFICATION_EMAIL_URL_PAGE);
		insertConfigParam(EConfigurationTags.EMAIL_USER_LOGIN_URL_PAGE);
		insertConfigParam(EConfigurationTags.EMAIL_USER_RECOVERY_PASSWORD_URL_PAGE);
		insertConfigParam(EConfigurationTags.EMAIL_USER_VERIFICATION_EMAIL_URL_PAGE);
		
		insertConfigParam(EConfigurationTags.SCALE_SERP_TOTAL_CREDITS);
	}
	/**
	 * Insert a new Configuration parameter
	 * @param tag
	 * @throws RemoveApplicationException
	 */
	private void insertConfigParam(EConfigurationTags tag) throws RemoveApplicationException {
		insertConfigParam(tag.getTag(), tag.getDefaultValue());
	}
	/**
	 * Insert a new Configuration parameter
	 * @param key
	 * @param value
	 * @throws RemoveApplicationException
	 */
	private void insertConfigParam(String key, String value) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_KEY, FIELD_VALUE);
		
		try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
			con.createQuery(QUERY)
				.addParameter(FIELD_KEY, key)
				.addParameter(FIELD_VALUE, value).executeUpdate();
			con.commit();
		} catch (Exception ex) {
			catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
		}
	}
	/**
	 * Get a system parameter from configuration table that starts with a parameter name.
	 * @param param
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected List<ConfigurationDto> getConfigStartsWith(EConfigurationTags param) throws RemoveApplicationException {
		List<ConfigurationDto> list = new ArrayList<ConfigurationDto>();
		String QUERY = buildSelectStartsWithQuery(TABLE, FIELD_KEY);
    	
    	try (Connection con = ConnectionDB.getSql2oRO().beginTransaction()) {
    		list = con.createQuery(QUERY).addParameter(FIELD_KEY, param.getTag()+"%").executeAndFetch(ConfigurationDto.class);
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
        return list;
	}
	/**
	 * Insert a new Configuration parameter
	 * @param configuracion
	 * @throws RemoveApplicationException
	 */
	protected void save(ConfigurationDto configuracion) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_KEY, FIELD_VALUE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_KEY, configuracion.getKey())
    				.addParameter(FIELD_VALUE, configuracion.getValue()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	/**
	 * Remove a configuration system parameter.
	 * @param config
	 * @throws RemoveApplicationException
	 */
	protected void delete(ConfigurationDto config) throws RemoveApplicationException {
		String QUERY = buildDeleteQuery(TABLE, FIELD_KEY, config.getKey());
		
		try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public ConfigurationEmail getEmailParams() throws RemoveApplicationException {
		ConfigurationEmail configEmail = ConfigurationEmail.init();
		
		configEmail.setHost(getConfigByKey(EConfigurationTags.EMAIL_CLIENT_HOST));
		configEmail.setUserName(getConfigByKey(EConfigurationTags.EMAIL_CLIENT_USERNAME));
		configEmail.setPassword(getConfigByKey(EConfigurationTags.EMAIL_CLIENT_PASSWORD));
		configEmail.setEmailFrom(getConfigByKey(EConfigurationTags.EMAIL_CLIENT_EMAIL_FROM));
		
		List<ConfigurationDto> lstParams = getConfigStartsWith(EConfigurationTags.EMAIL_PARAM_PREFIX);
		
		lstParams.forEach(config -> {
			config.setKey(config.getKey().replaceAll(EConfigurationTags.EMAIL_PARAM_PREFIX.getTag(), "").trim().toLowerCase());
		});
		
		configEmail.setParams(lstParams);
		
		return configEmail;
	}
	
	public String getScaleSerpRemaining() {
		try {
			return getConfigByKey(EConfigurationTags.SCALE_SERP_REMAINING_PREFIX.getTag()+getMonthYear());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.SCALE_SERP_REMAINING_PREFIX);
		}
		
		return "";
	}
	
	public void upsertScaleSerpRemaining(Long remaining) {
		String key = EConfigurationTags.SCALE_SERP_REMAINING_PREFIX.getTag()+getMonthYear();
		
		try {
			ConfigurationDto config = get(key);
			
			if (config == null) {
				insertConfigParam(key, String.valueOf(remaining));
			} else {
				updateConfig(key, String.valueOf(remaining));
			}
		} catch (RemoveApplicationException e) {
			getLog().error("Error upserting SERP Consuption", e);
		}
	}
	
	public String getScaleSerConsumed() {
		try {
			return getConfigByKey(EConfigurationTags.SCALE_SERP_CONSUPTION.getTag());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.SCALE_SERP_REMAINING_PREFIX);
		}
		
		return "";
	}
	
	public void upsertScaleSerpConsumed(Long remaining) {
		String key = EConfigurationTags.SCALE_SERP_CONSUPTION.getTag();
		
		try {
			ConfigurationDto config = get(key);
			
			if (config == null) {
				insertConfigParam(key, String.valueOf(remaining));
			} else {
				updateConfig(key, String.valueOf(remaining));
			}
		} catch (RemoveApplicationException e) {
			getLog().error("Error upserting SERP Consuption", e);
		}
	}
	
	public void upsertScaleSerpExpirationDate(String date) {
		try {
			ConfigurationDto config = get(EConfigurationTags.SCALE_SERP_EXPIRATION_DATE.getTag());
			
			if (config == null) {
				insertConfigParam(EConfigurationTags.SCALE_SERP_EXPIRATION_DATE.getTag(), date);
			} else {
				if (config.getValue() == null || !config.getValue().equals(date)) {
					updateConfig(EConfigurationTags.SCALE_SERP_EXPIRATION_DATE.getTag(), date);	
				}
			}
		} catch (RemoveApplicationException e) {
			getLog().error("Error upserting SERP Consuption", e);
		}
	}
	public String getScaleSerpExpirationDate() {
		try {
			return getConfigByKey(EConfigurationTags.SCALE_SERP_EXPIRATION_DATE.getTag());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.SCALE_SERP_EXPIRATION_DATE);
		}
		
		return "";
	}
	
	
	
	public String getScaleSerpConsumption() {
		try {
			return getConfigByKey(EConfigurationTags.SCALE_SERP_CONSUPTION_PREFIX.getTag()+getMonthYear());
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.SCALE_SERP_CONSUPTION_PREFIX);
		}
		
		return "";
	}
	
	public void upsertScaleSerpConsumption(long totalCalls) {
		String key = EConfigurationTags.SCALE_SERP_CONSUPTION_PREFIX.getTag()+getMonthYear();
		
		try {
			ConfigurationDto config = get(key);
			
			if (config == null) {
				insertConfigParam(key, String.valueOf(totalCalls));
			} else {
				updateConfig(key, String.valueOf(Long.valueOf(config.getValue()).longValue() + totalCalls));
			}
		} catch (RemoveApplicationException e) {
			getLog().error("Error upserting SERP Consuption", e);
		}
	}
	
	public String getContactEmail() {
		try {
			return getConfigByKey(EConfigurationTags.CONTACT_EMAIL);
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.CONTACT_EMAIL);
		}
		
		return "";
	}
	
	public String getFrontEndDomainBase() {
		try {
			return getConfigByKey(EConfigurationTags.FRONT_END_DOMAIN_BASE);
		} catch (Exception ex) {
			getLog().error("No se pudo configurar la propiedad: "+EConfigurationTags.FRONT_END_DOMAIN_BASE);
		}
		
		return "";
	}

	private String getMonthYear() {
		return RemoveDateUtils.nowLocalDate().getMonthValue()+"_"+RemoveDateUtils.nowLocalDate().getYear();
	}
}
