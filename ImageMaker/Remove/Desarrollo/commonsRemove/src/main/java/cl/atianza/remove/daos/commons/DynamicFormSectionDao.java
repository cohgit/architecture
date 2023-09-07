package cl.atianza.remove.daos.commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.DynamicFormInput;
import cl.atianza.remove.models.commons.DynamicFormSection;
import cl.atianza.remove.models.commons.DynamicFormSectionLabel;
import cl.atianza.remove.utils.RemoveDao;

/**
 * DeindexationFormSection DAOs methods.
 * @author pilin
 *
 */
public class DynamicFormSectionDao extends RemoveDao {
	private static final String TABLE_NAME = "dynamic_forms_section";
	
	private static final String FIELD_ID_FORM = "id_form";
	private static final String FIELD_POSITION = "position";
	private static final String FIELD_VISIBLE = "visible";
	private static final String FIELD_VISIBLE_CONDITION = "visible_condition";
	private static final String FIELD_ACTIVE = "active";
	
	public DynamicFormSectionDao() throws RemoveApplicationException {
		super(LogManager.getLogger(DynamicFormSectionDao.class), TABLE_NAME);
	}
	
	public static DynamicFormSectionDao init() throws RemoveApplicationException {
		return new DynamicFormSectionDao();
	}	
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicFormSection getBasicById(long id) throws RemoveApplicationException {
		return (DynamicFormSection) getByField(TABLE, FIELD_ID, id, DynamicFormSection.class);
	}
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<DynamicFormSection> listBasicById(long idForm) throws RemoveApplicationException {
		return (List<DynamicFormSection>) listByField(TABLE, FIELD_ID_FORM, idForm, DynamicFormSection.class, FIELD_POSITION);
	}

	/**
	 * Save DeindexationFormSection Object
	 * @param section
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicFormSection save(DynamicFormSection section) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_FORM, FIELD_POSITION, FIELD_VISIBLE,
				FIELD_VISIBLE_CONDITION, FIELD_ACTIVE);
		Long idRecord = INIT_RECORD_VALUE;
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_FORM, section.getId_form())
    				.addParameter(FIELD_POSITION, section.getPosition())
    				.addParameter(FIELD_VISIBLE, section.isVisible())
    				.addParameter(FIELD_VISIBLE_CONDITION, section.getVisible_condition())
    				.addParameter(FIELD_ACTIVE, section.isActive())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	section.setId(idRecord);
    	
    	innerSaves(section);
    	
    	return section;
	}
	
	private void innerSaves(DynamicFormSection section) throws RemoveApplicationException {
		for (DynamicFormSectionLabel label : section.getLabels()) {
			label.setId_dynamic_forms_section(section.getId());
			DynamicFormSectionLabelDao.init().save(label);
		}
		for (DynamicFormInput input : section.getInputs()) {
			input.setId_section(section.getId());
			DynamicFormInputDao.init().save(input);
		}
	}

	public DynamicFormSection update(DynamicFormSection section) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, section.getId(), FIELD_POSITION, FIELD_VISIBLE,
				FIELD_VISIBLE_CONDITION, FIELD_ACTIVE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_POSITION, section.getPosition())
    				.addParameter(FIELD_VISIBLE, section.isVisible())
    				.addParameter(FIELD_VISIBLE_CONDITION, section.getVisible_condition())
    				.addParameter(FIELD_ACTIVE, section.isActive())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return section;
	}

	public List<DynamicFormSection> listByForm(Long idForm) {
		List<DynamicFormSection> list = new ArrayList<DynamicFormSection>();
		
		if (list != null) {
			list.forEach(section -> {
//				section.setInputs(DeindexationFormInputDao.init().listBySection(section.getId()));
//				section.setLabels(labels);
			});
		}
		
		return list;
	}
}
