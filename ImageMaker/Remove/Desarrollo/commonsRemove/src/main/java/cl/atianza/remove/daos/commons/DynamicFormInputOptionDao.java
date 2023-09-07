package cl.atianza.remove.daos.commons;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.DynamicFormInputOption;
import cl.atianza.remove.models.commons.DynamicFormInputOptionsLabel;
import cl.atianza.remove.utils.RemoveDao;

/**
 * DeindexationFormInputOption DAOs methods.
 * @author pilin
 *
 */
public class DynamicFormInputOptionDao extends RemoveDao {
	private static final String TABLE_NAME = "dynamic_forms_input_options";
	
	private static final String FIELD_ID_INPUT = "id_input";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_ACTIVE = "active";
	
	public DynamicFormInputOptionDao() throws RemoveApplicationException {
		super(LogManager.getLogger(DynamicFormInputOptionDao.class), TABLE_NAME);
	}
	
	public static DynamicFormInputOptionDao init() throws RemoveApplicationException {
		return new DynamicFormInputOptionDao();
	}	
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicFormInputOption listByBasicId(long id) throws RemoveApplicationException {
		return (DynamicFormInputOption) getByField(TABLE, FIELD_ID, id, DynamicFormInputOption.class);
	}

	/**
	 * Save DeindexationFormInputOption Object
	 * @param option
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DynamicFormInputOption save(DynamicFormInputOption option) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_INPUT, FIELD_NAME, FIELD_ACTIVE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_INPUT, option.getId_input())
    				.addParameter(FIELD_NAME, option.getName())
    				.addParameter(FIELD_ACTIVE, option.isActive())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	option.setId(idRecord);
    	innerSaves(option);
    	
    	return option;
	}
	
	private void innerSaves(DynamicFormInputOption option) throws RemoveApplicationException {
		for (DynamicFormInputOptionsLabel label: option.getLabels()) {
			label.setId_dynamic_forms_input_options(option.getId());
			DynamicFormInputOptionLabelDao.init().save(label);
		}
	}

	public DynamicFormInputOption update(DynamicFormInputOption option) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, option.getId(), FIELD_NAME, FIELD_ACTIVE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_NAME, option.getName())
    				.addParameter(FIELD_ACTIVE, option.isActive())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return option;
	}
}
