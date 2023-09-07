package cl.atianza.remove.daos.commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.ECountries;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.Country;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Country DAOs methods.
 * @author pilin
 *
 */
public class CountryDao extends RemoveDao {
	private static final String TABLE_NAME = "countries";
	
	private static final String FIELD_NAME = "name";
	private static final String FIELD_TAG = "tag";
	private static final String FIELD_DOMAIN = "domain";
	private static final String FIELD_ACTIVE = "active";
	private Boolean active = true;

	public CountryDao() throws RemoveApplicationException {
		super(LogManager.getLogger(CountryDao.class), TABLE_NAME);
	}
	
	public static CountryDao init() throws RemoveApplicationException {
		return new CountryDao();
	}	
	
	/**
	 * Get a Country from database by id
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Country get(long id) throws RemoveApplicationException {
		return (Country) getByField(TABLE, FIELD_ID, id, Country.class);
	}
	/**
	 * Get a Country from database by code
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Country get(String code) throws RemoveApplicationException {
		return (Country) getByField(TABLE, FIELD_TAG, code, Country.class);
	}
	
	/**
	 * List all active countries
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<Country> list() throws RemoveApplicationException {
		return (List<Country>) listByField(TABLE, FIELD_ACTIVE, active, Country.class);
	}

	/**
	 * Insert all countries in database with default values.
	 * @throws RemoveApplicationException
	 */
	public void createCountriesData() throws RemoveApplicationException {
		for (ECountries country : ECountries.values()) {
			save(country);
		}
	}

	/**
	 * Insert a country into database with default values.
	 * @param country
	 * @throws RemoveApplicationException
	 */
	private void save(ECountries country) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_NAME, FIELD_TAG, FIELD_DOMAIN, FIELD_ACTIVE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_NAME, country.getCountry())
    				.addParameter(FIELD_TAG, country.getCode())
    				.addParameter(FIELD_DOMAIN, country.getDomain())
    				.addParameter(FIELD_ACTIVE, country.isActive()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	/**
	 * List all countries with certain ids.
	 * @param idCountries
	 * @return
	 */
	public List<Country> list(List<Long> idCountries) {
		List<Country> list = new ArrayList<Country>();

		if (idCountries != null && !idCountries.isEmpty()) {
			idCountries.forEach(id -> {
				try {
					list.add(get(id));
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading: ", e);
				}
			});
		}
		
		return list;
	}
}
