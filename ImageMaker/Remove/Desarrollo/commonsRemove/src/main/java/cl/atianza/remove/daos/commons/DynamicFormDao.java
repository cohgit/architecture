package cl.atianza.remove.daos.commons;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.DynamicForm;
import cl.atianza.remove.models.commons.DynamicFormConditionResult;
import cl.atianza.remove.models.commons.DynamicFormSection;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveJsonUtil;

/**
 * DeindexationForm DAOs methods.
 * @author pilin
 *
 */
public class DynamicFormDao extends RemoveDao {
	private static final String TABLE_NAME = "dynamic_forms";
	
	private static final String FIELD_VERSION = "version";
	private static final String FIELD_TYPE = "type";
	private static final String FIELD_CREATION_DATE = "creation_date";
	private static final String FIELD_ACTIVATION_DATE = "activation_date";
	private static final String FIELD_DEACTIVATION_DATE = "deactivation_date";
	private static final String FIELD_TEMPLATE = "template";
	private static final String FIELD_ACTIVE = "active";
	
	public DynamicFormDao() throws RemoveApplicationException {
		super(LogManager.getLogger(DynamicFormDao.class), TABLE_NAME);
	}
	
	public static DynamicFormDao init() throws RemoveApplicationException {
		return new DynamicFormDao();
	}	
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicForm getBasicById(long id) throws RemoveApplicationException {
		return (DynamicForm) getByField(TABLE, FIELD_ID, id, DynamicForm.class);
	}

	/**
	 * Save DeindexationForm Object
	 * @param form
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicForm save(DynamicForm form) throws RemoveApplicationException {
		form.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		form.sort();
		
		String QUERY = buildInsertQuery(TABLE, FIELD_VERSION, FIELD_CREATION_DATE, FIELD_ACTIVATION_DATE,
				FIELD_TEMPLATE, FIELD_TYPE, FIELD_DEACTIVATION_DATE, FIELD_ACTIVE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_VERSION, form.getVersion())
    				.addParameter(FIELD_TYPE, form.getType())
    				.addParameter(FIELD_CREATION_DATE, form.getCreation_date())
    				.addParameter(FIELD_ACTIVATION_DATE, form.getActivation_date())
    				.addParameter(FIELD_DEACTIVATION_DATE, form.getDeactivation_date())
    				.addParameter(FIELD_TEMPLATE, form.getTemplate())
    				.addParameter(FIELD_ACTIVE, form.isActive())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	form.setId(idRecord);
    	
    	innerSaves(form);
    	
    	return form;
	}
	
	private void innerSaves(DynamicForm form) throws RemoveApplicationException {
		if (form.getSections() != null) {
			for(DynamicFormSection section : form.getSections()) {
				section.setId_form(form.getId());
				DynamicFormSectionDao.init().save(section);
			}
		}
		if (form.getConditionResults() != null) {
			for(DynamicFormConditionResult condition : form.getConditionResults()) {
				condition.setId_form(form.getId());
				DynamicFormConditionResultDao.init().save(condition);
			}
		}
	}

	public DynamicForm active(DynamicForm form) throws RemoveApplicationException {
		form.setActivation_date(RemoveDateUtils.nowLocalDateTime());
		form.setActive(true);
		
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, form.getId(), FIELD_ACTIVATION_DATE, FIELD_ACTIVE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_ACTIVATION_DATE, form.getActivation_date())
    				.addParameter(FIELD_ACTIVE, form.isActive())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	updateTemplate(form);
    	return form;
	}
	
	public DynamicForm deactive(DynamicForm form) throws RemoveApplicationException {
		form.setDeactivation_date(RemoveDateUtils.nowLocalDateTime());
		form.setActive(false);
		
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, form.getId(), FIELD_DEACTIVATION_DATE, FIELD_ACTIVE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_DEACTIVATION_DATE, form.getDeactivation_date())
    				.addParameter(FIELD_ACTIVE, form.isActive())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return form;
	}
	public void deactiveAll() throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ACTIVE, true, FIELD_DEACTIVATION_DATE, FIELD_ACTIVE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_DEACTIVATION_DATE, RemoveDateUtils.nowLocalDateTime())
    				.addParameter(FIELD_ACTIVE, false)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public DynamicForm getActiveForClient() throws RemoveApplicationException {
		DynamicForm form = getActive();
		
//		if (form != null) {
//			form.setSections(DeindexationFormSectionDao.init().listByForm(form.getId()));
//		}
		
		return form;
	}
	
	public DynamicForm getTemplateActiveForClientUtility() throws RemoveApplicationException {
		DynamicForm form = getActive();
		
		return form != null ? RemoveJsonUtil.jsonToData(form.getTemplate(), DynamicForm.class) : null;
	}
	
	public DynamicForm getByIdForClientUtility(Long idForm) throws RemoveApplicationException {
		DynamicForm form = getBasicById(idForm);
		
		return form != null ? RemoveJsonUtil.jsonToData(form.getTemplate(), DynamicForm.class) : null;
	}
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicForm getActive() throws RemoveApplicationException {
		return (DynamicForm) getByField(TABLE, FIELD_ACTIVE, true, DynamicForm.class);
	}
	
	public void updateTemplate(DynamicForm form) throws RemoveApplicationException {
		form.setConditionResults(null);
		form.setTemplate(RemoveJsonUtil.dataToJson(form));
		
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, form.getId(), FIELD_TEMPLATE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_TEMPLATE, form.getTemplate())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
}
