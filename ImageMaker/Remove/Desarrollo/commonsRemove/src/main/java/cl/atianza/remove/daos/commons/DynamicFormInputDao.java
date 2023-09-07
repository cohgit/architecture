package cl.atianza.remove.daos.commons;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.DynamicFormInput;
import cl.atianza.remove.models.commons.DynamicFormInputDescription;
import cl.atianza.remove.models.commons.DynamicFormInputLabel;
import cl.atianza.remove.models.commons.DynamicFormInputOption;
import cl.atianza.remove.utils.RemoveDao;

/**
 * DeindexationFormInput DAOs methods.
 * @author pilin
 *
 */
public class DynamicFormInputDao extends RemoveDao {
	private static final String TABLE_NAME = "dynamic_forms_input";
	
	private static final String FIELD_ID_SECTION = "id_section";
	private static final String FIELD_POSITION = "position";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_TYPE = "type";
	private static final String FIELD_WIDTH = "width";
	private static final String FIELD_REQUIRED = "required";
	private static final String FIELD_REQUIRED_CONDITION = "required_condition";
	private static final String FIELD_VISIBLE = "visible";
	private static final String FIELD_VISIBLE_CONDITION = "visible_condition";
	private static final String FIELD_ENABLED = "enabled";
	private static final String FIELD_ENABLED_CONDITION = "enabled_condition";
	private static final String FIELD_VALUE = "value";
	private static final String FIELD_VALUE_CONDITION = "value_condition";
	private static final String FIELD_ACTIVE = "active";
	
	public DynamicFormInputDao() throws RemoveApplicationException {
		super(LogManager.getLogger(DynamicFormInputDao.class), TABLE_NAME);
	}
	
	public static DynamicFormInputDao init() throws RemoveApplicationException {
		return new DynamicFormInputDao();
	}	
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicFormInput getBasicById(long id) throws RemoveApplicationException {
		return (DynamicFormInput) getByField(TABLE, FIELD_ID, id, DynamicFormInput.class);
	}
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	private List<DynamicFormInput> listBasicBySectionId(long id) throws RemoveApplicationException {
		return (List<DynamicFormInput>) listByField(TABLE, FIELD_ID, id, DynamicFormInput.class, FIELD_POSITION);
	}

	/**
	 * Save DeindexationFormInput Object
	 * @param input
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicFormInput save(DynamicFormInput input) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SECTION, FIELD_POSITION, FIELD_NAME, FIELD_TYPE, FIELD_WIDTH, FIELD_REQUIRED,
				FIELD_REQUIRED_CONDITION, FIELD_VISIBLE, FIELD_VISIBLE_CONDITION, FIELD_ENABLED, FIELD_ENABLED_CONDITION,
				FIELD_VALUE, FIELD_VALUE_CONDITION, FIELD_ACTIVE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SECTION, input.getId_section())
    				.addParameter(FIELD_POSITION, input.getPosition())
    				.addParameter(FIELD_NAME, input.getName())
    				.addParameter(FIELD_TYPE, input.getType())
    				.addParameter(FIELD_WIDTH, input.getWidth())
    				.addParameter(FIELD_REQUIRED, input.isRequired())
    				.addParameter(FIELD_REQUIRED_CONDITION, input.getRequired_condition())
    				.addParameter(FIELD_VISIBLE, input.isVisible())
    				.addParameter(FIELD_VISIBLE_CONDITION, input.getVisible_condition())
    				.addParameter(FIELD_ENABLED, input.isEnabled())
    				.addParameter(FIELD_ENABLED_CONDITION, input.getEnabled_condition())
    				.addParameter(FIELD_VALUE, input.getValue())
    				.addParameter(FIELD_VALUE_CONDITION, input.getValue_condition())
    				.addParameter(FIELD_ACTIVE, input.isActive())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	input.setId(idRecord);
    	innerSaves(input);
    	
    	return input;
	}
	
	private void innerSaves(DynamicFormInput input) throws RemoveApplicationException {
		for (DynamicFormInputLabel label : input.getLabels()) {
			label.setId_dynamic_forms_input(input.getId());
			DynamicFormInputLabelDao.init().save(label);
		}
		
		for (DynamicFormInputDescription description : input.getDescriptions()) {
			description.setId_dynamic_forms_input(input.getId());
			DynamicFormInputDescriptionDao.init().save(description);
		}
		
		for (DynamicFormInputOption option : input.getOptions()) {
			option.setId_input(input.getId());
			DynamicFormInputOptionDao.init().save(option);
		}
	}

	public DynamicFormInput update(DynamicFormInput input) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, input.getId(), FIELD_POSITION, FIELD_NAME, FIELD_TYPE,
				FIELD_WIDTH, FIELD_REQUIRED, FIELD_REQUIRED_CONDITION, FIELD_VISIBLE, FIELD_VISIBLE_CONDITION, 
				FIELD_ENABLED, FIELD_ENABLED_CONDITION, FIELD_VALUE, FIELD_VALUE_CONDITION, FIELD_ACTIVE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
		    		.addParameter(FIELD_POSITION, input.getOptions())
					.addParameter(FIELD_NAME, input.getName())
					.addParameter(FIELD_TYPE, input.getType())
					.addParameter(FIELD_WIDTH, input.getWidth())
					.addParameter(FIELD_REQUIRED, input.isRequired())
					.addParameter(FIELD_REQUIRED_CONDITION, input.getRequired_condition())
					.addParameter(FIELD_VISIBLE, input.isVisible())
					.addParameter(FIELD_VISIBLE_CONDITION, input.getVisible_condition())
					.addParameter(FIELD_ENABLED, input.isEnabled())
					.addParameter(FIELD_ENABLED_CONDITION, input.getEnabled_condition())
					.addParameter(FIELD_VALUE, input.getValue())
					.addParameter(FIELD_VALUE_CONDITION, input.getValue_condition())
					.addParameter(FIELD_ACTIVE, input.isActive())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return input;
	}

	public List<DynamicFormInput> listBySection(Long idSection) throws RemoveApplicationException {
		List<DynamicFormInput> list = listBasicBySectionId(idSection);
		
		if (list != null) {
			list.forEach(input -> {
//				input.setLabels(labels);
//				input.setOptions(options);
			});
		}
		
		return list;
	}
}
