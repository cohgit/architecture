package cl.atianza.remove.daos.commons;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.ScannerImpulseVariable;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Scanner Impulse Contents DAOs methods
 * @author pilin
 *
 */
public class ScannerImpulseVariableDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_impulses_variables";
	
	private static final String FIELD_ID_SCANNER_IMPULSE = "id_scanner_impulse";
	private static final String FIELD_ID_COUNTRY = "id_country";
	private static final String FIELD_TRAFFIC_SITE = "traffic_site";
	private static final String FIELD_DA = "da";
	private static final String FIELD_DR = "dr";
	private static final String FIELD_PA = "pa";
	private static final String FIELD_TOTAL_LINKS = "total_links";
	private static final String FIELD_TOTAL_KEYWORDS = "total_keywords";
	private static final String FIELD_CATEGORY_CONTENT = "category_content";
	private static final String FIELD_ID_AUTHOR = "id_author";
	private static final String FIELD_AUTHOR_PROFILE = "author_profile";
	
	
	public ScannerImpulseVariableDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerImpulseVariableDao.class), TABLE_NAME);
	}
	
	public static ScannerImpulseVariableDao init() throws RemoveApplicationException {
		return new ScannerImpulseVariableDao();
	}	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseVariable getById(long id) throws RemoveApplicationException {
		ScannerImpulseVariable content = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID, id);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			content = conn.createQuery(QUERY).executeAndFetchFirst(ScannerImpulseVariable.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		loadInnerData(content);
		
		return content;
	}
	/**
	 * 
	 * @param idScannerImpulse
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseVariable getByImpulse(long idScannerImpulse) throws RemoveApplicationException {
		ScannerImpulseVariable content = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER_IMPULSE, idScannerImpulse);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			content = conn.createQuery(QUERY).executeAndFetchFirst(ScannerImpulseVariable.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (content != null) {
			loadInnerData(content);	
		} else {
			content = new ScannerImpulseVariable();
			content.setId_scanner_impulse(idScannerImpulse);
		}
		
		return content;
	}
	/**
	 * 
	 * @param variable
	 * @throws RemoveApplicationException
	 */
	private void loadInnerData(ScannerImpulseVariable variable) throws RemoveApplicationException {
		if (variable != null) {
			variable.setCountry(CountryDao.init().get(variable.getId_country()));
			variable.setInteractions(ScannerImpulseInteractionDao.init().listByVariable(variable.getId()));
		}
	}
	
	/**
	 * 
	 * @param variable
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseVariable save(ScannerImpulseVariable variable) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER_IMPULSE, FIELD_ID_COUNTRY, FIELD_TRAFFIC_SITE,
				FIELD_DA, FIELD_DR, FIELD_PA, FIELD_TOTAL_KEYWORDS, FIELD_TOTAL_LINKS, FIELD_CATEGORY_CONTENT,
				FIELD_ID_AUTHOR, FIELD_AUTHOR_PROFILE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER_IMPULSE, variable.getId_scanner_impulse())
    				.addParameter(FIELD_ID_COUNTRY, variable.getId_country())
    				.addParameter(FIELD_TRAFFIC_SITE, variable.getTraffic_site())
    				.addParameter(FIELD_DA, variable.getDa())
    				.addParameter(FIELD_DR, variable.getDr())
    				.addParameter(FIELD_PA, variable.getPa())
    				.addParameter(FIELD_TOTAL_KEYWORDS, variable.getTotal_keywords())
    				.addParameter(FIELD_TOTAL_LINKS, variable.getTotal_links())
    				.addParameter(FIELD_CATEGORY_CONTENT, variable.getCategory_content())
    				.addParameter(FIELD_ID_AUTHOR, variable.getId_author())
    				.addParameter(FIELD_AUTHOR_PROFILE, variable.getAuthor_profile())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	variable.setId(idRecord);
    	
    	if (variable.getInteractions() != null) {
    		variable.getInteractions().forEach(interaction -> {
    			interaction.setId_scanner_impulse_variables(variable.getId());
    			interaction.setCategory(interaction.getCategoryType().getCode());
    			interaction.setConcept(interaction.getConceptType().getCode());
    			interaction.setSection(interaction.getSectionType().getCode());
    			
    			try {
					ScannerImpulseInteractionDao.init().save(interaction);
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving interaction: " + interaction, e);
				}
    		});
    	}
    	
    	return variable;
	}
	/**
	 * 
	 * @param variable
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseVariable update(ScannerImpulseVariable variable) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, variable.getId(), FIELD_ID_COUNTRY, FIELD_TRAFFIC_SITE,
				FIELD_DA, FIELD_DR, FIELD_PA, FIELD_TOTAL_KEYWORDS, FIELD_TOTAL_LINKS, FIELD_CATEGORY_CONTENT);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_ID_COUNTRY, variable.getId_country())
    				.addParameter(FIELD_TRAFFIC_SITE, variable.getTraffic_site())
    				.addParameter(FIELD_DA, variable.getDa())
    				.addParameter(FIELD_DR, variable.getDr())
    				.addParameter(FIELD_PA, variable.getPa())
    				.addParameter(FIELD_TOTAL_KEYWORDS, variable.getTotal_keywords())
    				.addParameter(FIELD_TOTAL_LINKS, variable.getTotal_links())
    				.addParameter(FIELD_CATEGORY_CONTENT, variable.getCategory_content())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}

    	if (variable.getInteractions() != null) {
    		variable.getInteractions().forEach(interaction -> {
    			interaction.setId_scanner_impulse_variables(variable.getId());
    			interaction.setCategory(interaction.getCategoryType().getCode());
    			interaction.setConcept(interaction.getConceptType().getCode());
    			interaction.setSection(interaction.getSectionType().getCode());
    			
    			try {
					ScannerImpulseInteractionDao.init().upsert(interaction);
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving interaction: " + interaction, e);
				}
    		});
    	}
    	
    	return variable;
	}
}
