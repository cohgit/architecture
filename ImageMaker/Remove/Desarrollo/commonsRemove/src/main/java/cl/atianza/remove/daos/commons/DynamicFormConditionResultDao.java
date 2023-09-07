package cl.atianza.remove.daos.commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.DynamicFormConditionResult;
import cl.atianza.remove.models.commons.DynamicFormConditionResultLabel;
import cl.atianza.remove.utils.RemoveDao;

/**
 * DeindexationFormConditionResult DAOs methods.
 * @author pilin
 *
 */
public class DynamicFormConditionResultDao extends RemoveDao {
	private static final String TABLE_NAME = "dynamic_forms_conditions_results";
	
	private static final String FIELD_ID_FORM = "id_form";
	private static final String FIELD_TYPE = "type";
	private static final String FIELD_POSITION = "position";
	private static final String FIELD_CONDITION = "condition";
	private static final String FIELD_ACTIVE = "active";
	
	public DynamicFormConditionResultDao() throws RemoveApplicationException {
		super(LogManager.getLogger(DynamicFormConditionResultDao.class), TABLE_NAME);
	}
	
	public static DynamicFormConditionResultDao init() throws RemoveApplicationException {
		return new DynamicFormConditionResultDao();
	}	
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicFormConditionResult getBasicById(long id) throws RemoveApplicationException {
		return (DynamicFormConditionResult) getByField(TABLE, FIELD_ID, id, DynamicFormConditionResult.class);
	}
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<DynamicFormConditionResult> listBasicByDynamicFormId(long idForm) throws RemoveApplicationException {
		return (List<DynamicFormConditionResult>) listByField(TABLE, FIELD_ID_FORM, idForm, DynamicFormConditionResult.class, FIELD_POSITION);
	}

	/**
	 * Save DeindexationFormConditionResult Object
	 * @param condition
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicFormConditionResult save(DynamicFormConditionResult condition) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_FORM, FIELD_TYPE, FIELD_POSITION, FIELD_CONDITION, FIELD_ACTIVE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_FORM, condition.getId_form())
    				.addParameter(FIELD_TYPE, condition.getType())
    				.addParameter(FIELD_POSITION, condition.getPosition())
    				.addParameter(FIELD_CONDITION, condition.getCondition())
    				.addParameter(FIELD_ACTIVE, condition.isActive())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	condition.setId(idRecord);
    	
    	innerSave(condition);
    	
    	return condition;
	}
	
	private void innerSave(DynamicFormConditionResult condition) throws RemoveApplicationException {
		if (condition.getLabels() != null) {
			for(DynamicFormConditionResultLabel label : condition.getLabels()) {
				label.setId_dynamic_forms_conditions_results(condition.getId());
				DynamicFormConditionResultLabelDao.init().save(label);
			}
		}
	}

	public DynamicFormConditionResult update(DynamicFormConditionResult condition) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, condition.getId(), FIELD_POSITION, FIELD_CONDITION, FIELD_ACTIVE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_POSITION, condition.getPosition())
    				.addParameter(FIELD_CONDITION, condition.getCondition())
    				.addParameter(FIELD_ACTIVE, condition.isActive())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return condition;
	}

	public List<DynamicFormConditionResult> listByIds(List<Long> ids) throws RemoveApplicationException {
		List<DynamicFormConditionResult> list = new ArrayList<DynamicFormConditionResult>();
		
		if (!ids.isEmpty()) {
			String QUERY = buildSelectINKeyQuery(TABLE, FIELD_ID, ids) + " ORDER BY " + FIELD_POSITION;
			
			try (Connection conn = ConnectionDB.getSql2oRO().open()) {
				list = conn.createQuery(QUERY).executeAndFetch(DynamicFormConditionResult.class);
	        } catch (Exception ex) {
	        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
	        }
			
			if (list != null) {
				list.forEach(result -> {
					try {
						result.setLabels(DynamicFormConditionResultLabelDao.init().listByConditionId(result.getId()));
					} catch (RemoveApplicationException e) {
						getLog().info("Error loading condition label for: " + result, e);
					}
				});
			}
		}
		
		return list;
	}
}
