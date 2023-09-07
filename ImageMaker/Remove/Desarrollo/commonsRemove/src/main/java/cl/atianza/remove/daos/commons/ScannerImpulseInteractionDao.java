package cl.atianza.remove.daos.commons;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EImpulseInteractionCategory;
import cl.atianza.remove.enums.EImpulseInteractionConcept;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ESearchTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.ScannerImpulseInteraction;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Scanner Impulse Contents DAOs methods
 * @author pilin
 *
 */
public class ScannerImpulseInteractionDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_impulses_interactions";
	
	private static final String FIELD_ID_SCANNER_IMPULSE_VARIABLES = "id_scanner_impulse_variables";
	private static final String FIELD_CONCEPT = "concept";
	private static final String FIELD_CATEGORY = "category";
	private static final String FIELD_SECTION = "section";
	private static final String FIELD_ACTION_URL = "action_url";
	private static final String FIELD_EXACT_URL = "exact_url";
	private static final String FIELD_QUANTITY = "quantity";
	private static final String FIELD_INIT_DATE = "init_date";
	private static final String FIELD_END_DATE = "end_date";
	
	
	public ScannerImpulseInteractionDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerImpulseInteractionDao.class), TABLE_NAME);
	}
	
	public static ScannerImpulseInteractionDao init() throws RemoveApplicationException {
		return new ScannerImpulseInteractionDao();
	}	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseInteraction getById(long id) throws RemoveApplicationException {
		ScannerImpulseInteraction content = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID, id);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			content = conn.createQuery(QUERY).executeAndFetchFirst(ScannerImpulseInteraction.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return content;
	}

	@SuppressWarnings("unchecked")
	public List<ScannerImpulseInteraction> listByVariable(Long idScannerImpulseVariable) throws RemoveApplicationException {
		List<ScannerImpulseInteraction> list = (List<ScannerImpulseInteraction>) listByField(TABLE, FIELD_ID_SCANNER_IMPULSE_VARIABLES, idScannerImpulseVariable, ScannerImpulseInteraction.class, FIELD_ID);
		
		list.forEach(interaction -> {
			interaction.setCategoryType(EImpulseInteractionCategory.obtain(interaction.getCategory()));
			interaction.setConceptType(EImpulseInteractionConcept.obtain(interaction.getConcept()));
			interaction.setSectionType(ESearchTypes.obtain(interaction.getSection()));
		});
		
		return list;
	}
	
	/**
	 * 
	 * @param interaction
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseInteraction save(ScannerImpulseInteraction interaction) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER_IMPULSE_VARIABLES, FIELD_CONCEPT, FIELD_CATEGORY,
				FIELD_SECTION, FIELD_ACTION_URL, FIELD_EXACT_URL, FIELD_INIT_DATE, FIELD_QUANTITY, FIELD_END_DATE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER_IMPULSE_VARIABLES, interaction.getId_scanner_impulse_variables())
    				.addParameter(FIELD_CONCEPT, interaction.getConcept())
    				.addParameter(FIELD_CATEGORY, interaction.getCategory())
    				.addParameter(FIELD_SECTION, interaction.getSection())
    				.addParameter(FIELD_ACTION_URL, interaction.getAction_url())
    				.addParameter(FIELD_EXACT_URL, interaction.getExact_url())
    				.addParameter(FIELD_INIT_DATE, interaction.getInit_date())
    				.addParameter(FIELD_QUANTITY, interaction.getQuantity())
    				.addParameter(FIELD_END_DATE, interaction.getEnd_date())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	interaction.setId(idRecord);
    	
    	return interaction;
	}
	/**
	 * 
	 * @param interaction
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseInteraction update(ScannerImpulseInteraction interaction) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, interaction.getId(), FIELD_CONCEPT, FIELD_CATEGORY,
				FIELD_SECTION, FIELD_ACTION_URL, FIELD_EXACT_URL, FIELD_INIT_DATE, FIELD_QUANTITY, FIELD_END_DATE);
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_CONCEPT, interaction.getConcept())
    				.addParameter(FIELD_CATEGORY, interaction.getCategory())
    				.addParameter(FIELD_SECTION, interaction.getSection())
    				.addParameter(FIELD_ACTION_URL, interaction.getAction_url())
    				.addParameter(FIELD_EXACT_URL, interaction.getExact_url())
    				.addParameter(FIELD_INIT_DATE, interaction.getInit_date())
    				.addParameter(FIELD_QUANTITY, interaction.getQuantity())
    				.addParameter(FIELD_END_DATE, interaction.getEnd_date())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return interaction;
	}

	public void upsert(ScannerImpulseInteraction interaction) throws RemoveApplicationException {
		if (interaction.esNuevo()) {
			save(interaction);
		} else {
			if (interaction.isMarkToDelete()) {
				delete(interaction);
			} else {
				update(interaction);
			}
		}
	}

	private void delete(ScannerImpulseInteraction interaction) throws RemoveApplicationException {
		String QUERY = buildDeleteQuery(TABLE, FIELD_ID, interaction.getId());
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
}
