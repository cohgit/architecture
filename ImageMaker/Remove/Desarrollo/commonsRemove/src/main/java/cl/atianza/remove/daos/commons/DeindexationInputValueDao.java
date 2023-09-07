package cl.atianza.remove.daos.commons;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.Deindexation;
import cl.atianza.remove.models.commons.DeindexationInputValue;
import cl.atianza.remove.models.commons.DynamicForm;
import cl.atianza.remove.models.commons.DynamicFormInput;
import cl.atianza.remove.utils.RemoveDao;

/**
 * DeindexationInputValue DAOs methods.
 * @author pilin
 *
 */
@Deprecated
public class DeindexationInputValueDao extends RemoveDao {
	private static final String TABLE_NAME = "deindexations_input_values";
	
	private static final String FIELD_ID_DEINDEXATION = "id_deindexation";
	private static final String FIELD_ID_FORM_INPUT = "id_form_input";
	private static final String FIELD_ID_OPTION = "id_option";
	private static final String FIELD_VALUE = "value";
	private static final String FIELD_VALUE_NUMERIC = "value_numeric";
	private static final String FIELD_VALUE_DATE = "value_date";
	private static final String FIELD_VALUE_BOOLEAN = "value_boolean";
	
	public DeindexationInputValueDao() throws RemoveApplicationException {
		super(LogManager.getLogger(DeindexationInputValueDao.class), TABLE_NAME);
	}
	
	public static DeindexationInputValueDao init() throws RemoveApplicationException {
		return new DeindexationInputValueDao();
	}	
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DeindexationInputValue getById(long id) throws RemoveApplicationException {
		return (DeindexationInputValue) getByField(TABLE, FIELD_ID, id, DeindexationInputValue.class);
	}

	/**
	 * Save DeindexationInputValue Object
	 * @param inputValue
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DeindexationInputValue save(DeindexationInputValue inputValue) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_DEINDEXATION, FIELD_ID_FORM_INPUT, FIELD_ID_OPTION, FIELD_VALUE,
				FIELD_VALUE_NUMERIC, FIELD_VALUE_DATE, FIELD_VALUE_BOOLEAN);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_DEINDEXATION, inputValue.getId_deindexation())
    				.addParameter(FIELD_ID_FORM_INPUT, inputValue.getId_form_input())
    				.addParameter(FIELD_ID_OPTION, inputValue.getId_option())
    				.addParameter(FIELD_VALUE, inputValue.getValue())
    				.addParameter(FIELD_VALUE_NUMERIC, inputValue.getValue_numeric())
    				.addParameter(FIELD_VALUE_DATE, inputValue.getValue_date())
    				.addParameter(FIELD_VALUE_BOOLEAN, inputValue.getValue_boolean())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	inputValue.setId(idRecord);
    	
    	return inputValue;
	}
	
	public void save(Long idDeindexation, DynamicForm dynamicForm) {
		dynamicForm.getSections().forEach(section -> {
			section.getInputs().forEach(input -> { 
				try {
					DeindexationInputValue inputValue = new DeindexationInputValue();		

					inputValue.setId_deindexation(idDeindexation);
					inputValue.setId_form_input(input.getId());
					setValueFromType(inputValue, input);
				
					save(inputValue);
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving input value: " + input, e);
				}	
			});
		});
	}
	
	private void setValueFromType(DeindexationInputValue inputValue, DynamicFormInput input) {
		switch (input.getType()) {
			case "TEXT":
			case "TEXT_AREA":
				inputValue.setValue(input.getValue());
				
				if (input.getValue() != null) {
					if (input.getValue().trim().equalsIgnoreCase("true")) {
						inputValue.setValue_boolean(true);
					} else if (input.getValue().trim().equalsIgnoreCase("false")) {
						inputValue.setValue_boolean(false);
					}
				}
				
				break;
			case "NUMBER":
				inputValue.setValue(input.getValue());
				inputValue.setValue_numeric(input.getValue() != null ? Float.valueOf(input.getValue()) : null);
				break;
			case "DATE":
				inputValue.setValue(input.getValue());
				inputValue.setValue_date(input.getValue() != null ? parseDate(input.getValue()) : null);
				break;
			case "RADIO":
			case "SELECT":
				if (input.getValue() != null) {
					inputValue.setId_option(input.extractSelectedOption());
					inputValue.setValue(input.getValue());
				}
				
				break;
			case "CHECKBOX":
				inputValue.setValue(input.getValue());
				break;
			case "SWITCH":
				if (input.getValue() != null) {
					inputValue.setValue(input.getValue());
					inputValue.setValue_boolean(input.getValue().equalsIgnoreCase("true"));
				}
				
				break;
		}
	}
	
	private LocalDate parseDate(String date) {
		try {
			return LocalDate.parse(date.split("T")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} catch (Exception ex) {
			/* Parse Fallido */
		}
		return null;
	}

	public DeindexationInputValue update(DeindexationInputValue inputValue) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, inputValue.getId(), FIELD_ID_OPTION, FIELD_VALUE,
				FIELD_VALUE_NUMERIC, FIELD_VALUE_DATE, FIELD_VALUE_BOOLEAN);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
		    		.addParameter(FIELD_ID_OPTION, inputValue.getId_option())
					.addParameter(FIELD_VALUE, inputValue.getValue())
					.addParameter(FIELD_VALUE_NUMERIC, inputValue.getValue_numeric())
					.addParameter(FIELD_VALUE_DATE, inputValue.getValue_date())
					.addParameter(FIELD_VALUE_BOOLEAN, inputValue.getValue_boolean())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return inputValue;
	}

	public void loadSavedValues(Deindexation deindexation) throws RemoveApplicationException {
		List<DeindexationInputValue> values = listByDeindexation(deindexation.getId());
		if (values != null) {
			deindexation.getDynamicForm().getSections().forEach(section -> {
				section.getInputs().forEach(input -> {
					values.forEach(value -> {
						if (value.getId_form_input().longValue() == input.getId().longValue()) {
							input.setValue(value.getValue());
						}
					});
				});
			});
		}
	}

	@SuppressWarnings("unchecked")
	public List<DeindexationInputValue> listByDeindexation(Long idDeindexation) throws RemoveApplicationException {
		return (List<DeindexationInputValue>) listByField(TABLE, FIELD_ID_DEINDEXATION, idDeindexation, DeindexationInputValue.class, FIELD_ID);
	}

	public void delete(Long idDeindexation) throws RemoveApplicationException {
		String QUERY = buildDeleteQuery(TABLE, FIELD_ID_DEINDEXATION, idDeindexation);
		
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
