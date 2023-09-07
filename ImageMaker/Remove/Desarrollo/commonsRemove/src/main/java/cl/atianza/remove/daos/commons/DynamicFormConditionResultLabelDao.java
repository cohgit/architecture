package cl.atianza.remove.daos.commons;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.DynamicFormConditionResultLabel;
import cl.atianza.remove.utils.RemoveDao;

/**
 * DeindexationFormConditionResultLabel DAOs methods.
 * @author pilin
 *
 */
public class DynamicFormConditionResultLabelDao extends RemoveDao {
	private static final String TABLE_NAME = "dynamic_forms_conditions_results_labels";
	
	private static final String FIELD_ID_DYNAMIN_FORMS_CONDITIONS_RESULTS = "id_dynamic_forms_conditions_results";
	private static final String FIELD_LABEL = "label";
	private static final String FIELD_LANGUAGE = "language";
	
	public DynamicFormConditionResultLabelDao() throws RemoveApplicationException {
		super(LogManager.getLogger(DynamicFormConditionResultLabelDao.class), TABLE_NAME);
	}
	
	public static DynamicFormConditionResultLabelDao init() throws RemoveApplicationException {
		return new DynamicFormConditionResultLabelDao();
	}	
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicFormConditionResultLabel getByBasicId(long id) throws RemoveApplicationException {
		return (DynamicFormConditionResultLabel) getByField(TABLE, FIELD_ID, id, DynamicFormConditionResultLabel.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<DynamicFormConditionResultLabel> listByConditionId(long idCondition) throws RemoveApplicationException {
		return (List<DynamicFormConditionResultLabel>) listByField(TABLE, FIELD_ID_DYNAMIN_FORMS_CONDITIONS_RESULTS, idCondition, DynamicFormConditionResultLabel.class, FIELD_ID);
	}

	/**
	 * Save DeindexationFormConditionResultLabel Object
	 * @param label
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicFormConditionResultLabel save(DynamicFormConditionResultLabel label) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_DYNAMIN_FORMS_CONDITIONS_RESULTS, FIELD_LABEL, FIELD_LANGUAGE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_DYNAMIN_FORMS_CONDITIONS_RESULTS, label.getId_dynamic_forms_conditions_results())
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
	
	public DynamicFormConditionResultLabel update(DynamicFormConditionResultLabel label) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, label.getId(), FIELD_ID_DYNAMIN_FORMS_CONDITIONS_RESULTS, FIELD_LABEL, FIELD_LANGUAGE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_ID_DYNAMIN_FORMS_CONDITIONS_RESULTS, label.getId_dynamic_forms_conditions_results())
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
