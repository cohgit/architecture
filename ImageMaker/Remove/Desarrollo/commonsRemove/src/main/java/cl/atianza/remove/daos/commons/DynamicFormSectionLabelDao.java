package cl.atianza.remove.daos.commons;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.DynamicFormSectionLabel;
import cl.atianza.remove.utils.RemoveDao;

/**
 * DeindexationFormSectionLabel DAOs methods.
 * @author pilin
 *
 */
public class DynamicFormSectionLabelDao extends RemoveDao {
	private static final String TABLE_NAME = "dynamic_forms_section_labels";
	
	private static final String FIELD_ID_DEINDEXATION_FORMS_SECTION = "id_dynamic_forms_section";
	private static final String FIELD_LABEL = "label";
	private static final String FIELD_LANGUAGE = "language";
	
	public DynamicFormSectionLabelDao() throws RemoveApplicationException {
		super(LogManager.getLogger(DynamicFormSectionLabelDao.class), TABLE_NAME);
	}
	
	public static DynamicFormSectionLabelDao init() throws RemoveApplicationException {
		return new DynamicFormSectionLabelDao();
	}	
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicFormSectionLabel listByBasicId(long id) throws RemoveApplicationException {
		return (DynamicFormSectionLabel) getByField(TABLE, FIELD_ID, id, DynamicFormSectionLabel.class);
	}

	/**
	 * Save DeindexationFormSectionLabel Object
	 * @param label
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicFormSectionLabel save(DynamicFormSectionLabel label) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_DEINDEXATION_FORMS_SECTION, FIELD_LABEL, FIELD_LANGUAGE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_DEINDEXATION_FORMS_SECTION, label.getId_dynamic_forms_section())
    				.addParameter(FIELD_LABEL, label.getLabel())
    				.addParameter(FIELD_LANGUAGE, label.getLanguage())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	label.setId(idRecord);
    	
    	return label;
	}
	
	public DynamicFormSectionLabel update(DynamicFormSectionLabel label) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, label.getId(), FIELD_LABEL, FIELD_LANGUAGE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_LABEL, label.getLabel())
    				.addParameter(FIELD_LANGUAGE, label.getLanguage())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return label;
	}
}
