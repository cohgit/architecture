package cl.atianza.remove.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.models.commons.DeindexationInputValue;

public class ConditionEvaluatorHelper {
	private static Logger log = LogManager.getLogger(ConditionEvaluatorHelper.class);
	
	private static final String CONNECTOR_AND = " AND ";
	private static final String CONNECTOR_OR = " OR ";
	private static final String CONDITION_DF = "!=";
	private static final String CONDITION_GE = ">=";
	private static final String CONDITION_G = ">";
	private static final String CONDITION_LE = "<=";
	private static final String CONDITION_L = "<";
	private static final String CONDITION_E = "=";
	private static final String FORMAT_DATE = "DD-MM-YYYY";
	private static final String TRUE = "TRUE";
	private static final String FALSE = "FALSE";
	
	private static final String TEXT_TYPE = "TEXT";
	private static final String TEXT_AREA_TYPE = "TEXT_AREA";
	private static final String NUMBER_TYPE = "NUMBER";
	private static final String DATE_TYPE = "DATE";
	private static final String RADIO_TYPE = "RADIO";
	private static final String SELECT_TYPE = "SELECT";
	private static final String CHECKBOX_TYPE = "CHECKBOX";
	private static final String SWITCH_TYPE = "SWITCH";
	
	private Map<String, DeindexationInputValue> values;
	
	public static ConditionEvaluatorHelper init(List<DeindexationInputValue> listValues) {
		ConditionEvaluatorHelper helper = new ConditionEvaluatorHelper();
		helper.setValues(buildValues(listValues));
		return helper;
	}
	
	public Map<String, DeindexationInputValue> getValues() {
		return values;
	}

	public void setValues(Map<String, DeindexationInputValue> values) {
		this.values = values;
	}
	
	private static Map<String, DeindexationInputValue> buildValues(List<DeindexationInputValue> listValues) {
		Map<String, DeindexationInputValue> values = new HashMap<String, DeindexationInputValue>();
		
		if (listValues != null && !listValues.isEmpty()) {
			for (DeindexationInputValue val : listValues) {
				values.put(val.getInput().getName(), val);
			}
		}
		
		return values;
	}

	public boolean evaluate(String condition) {
		return recurrentEvaluation(condition);
	}

	private boolean recurrentEvaluation(String condition) {
		log.info("Evaluating condition: " + condition);
		if (condition != null) {
			condition = condition.trim();
			if (condition.equalsIgnoreCase(TRUE)) {
				return true;
			}
			if (condition.equalsIgnoreCase(FALSE)) {
				return false;
			}
			
			if (condition.contains("(")) {
				int indexOpen = condition.lastIndexOf('(');
				int indexClose = condition.substring(indexOpen).indexOf(')') + 1;
				String subcondition = condition.substring(indexOpen, indexClose);
				condition = condition.replace(subcondition, String.valueOf(recurrentEvaluation(subcondition.replace("(","").replace(")",""))));
				return recurrentEvaluation(condition);
			} else if (condition.contains(CONNECTOR_AND)) {
				String[] split = condition.split(CONNECTOR_AND);

				for (String innerCondition : split) {
					if (!recurrentEvaluation(innerCondition)) {
						return false;
					}
				};
			} else if (condition.contains(CONNECTOR_OR)) {
				String[] split = condition.split(CONNECTOR_AND);

				for (String innerCondition : split) {
					if (recurrentEvaluation(innerCondition)) {
						return true;
					}
				};
			} else {
				if (condition.contains(CONDITION_DF)) {                     // Evaluate Different
			        return compareDifferent(condition, values);
			      } else if (condition.contains(CONDITION_GE)) {              // Evaluate Greater or Equals
			        return compareGreaterOrEquals(condition, values);
			      } else if (condition.contains(CONDITION_G)) {               // Evaluate Greater
			        return compareGreater(condition, values);
			      } else if (condition.contains(CONDITION_LE)) {              // Evaluate Less or Equals
			        return compareLessOrEqual(condition, values);
			      } else if (condition.contains(CONDITION_L)) {               // Evaluate Less
			        return compareLess(condition, values);
			      } else if (condition.contains(CONDITION_E)) {               // Evaluate Equals
			        return compareEquals(condition, values);
			      }
			}
			
			return checkSimpleValue(condition, values);
		}
		
		return true;
	}

	private boolean checkSimpleValue(String condition, Map<String, DeindexationInputValue> values) {
		boolean sign = !condition.trim().startsWith("!");
	    String key = condition.trim().replace("!","");

	    if (values.get(key) != null && values.get(key).getValue_boolean() != null) {
	    	return sign ? values.get(key).getValue_boolean().booleanValue() : !values.get(key).getValue_boolean().booleanValue();
	    }
	    
	    return true;
	}

	private boolean compareDifferent(String condition, Map<String, DeindexationInputValue> values) {
		String[] splited = condition.split(CONDITION_DF);
	    String fieldName = splited[0].trim().toUpperCase();

	    if (values.get(fieldName) != null) {
	      String typeField = values.get(fieldName).getInput().getType();
	      String fieldSelected;
	      String compareValue = getComparableValue(splited[1]);

	      switch (typeField) {
	        case TEXT_TYPE: 
	        case TEXT_AREA_TYPE:
	        case SELECT_TYPE:
	        case RADIO_TYPE:
	          fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
	          return !fieldSelected.equalsIgnoreCase(compareValue);
	        case NUMBER_TYPE:
	          fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
	          return values.get(fieldName).getValue_numeric().floatValue() != Float.valueOf(compareValue).floatValue();
	        case CHECKBOX_TYPE:
	          fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
	          return !fieldSelected.contains(compareValue);
	        case DATE_TYPE:
	          return !values.get(fieldName).getValue_date().isEqual(LocalDate.parse(compareValue, DateTimeFormatter.ofPattern(FORMAT_DATE)));
	      }
	    }

	    return false;
	}

	private boolean compareEquals(String condition, Map<String, DeindexationInputValue> values) {
		String[] splited = condition.split(CONDITION_E);
	    String fieldName = splited[0].trim().toUpperCase();

	    if (values.get(fieldName) != null) {
	      String typeField = values.get(fieldName).getInput().getType();
	      String fieldSelected;
	      String compareValue = getComparableValue(splited[1]);

	      switch (typeField) {
	        case TEXT_TYPE: 
	        case TEXT_AREA_TYPE:
	        case SELECT_TYPE:
	        case RADIO_TYPE:
	          if (values.get(fieldName).getValue() != null) {
	        	  fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
		          return fieldSelected.equalsIgnoreCase(compareValue);	
	          }
	          break;
	        case NUMBER_TYPE:
	          if (values.get(fieldName).getValue() != null) {
	        	  fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
	  	          return values.get(fieldName).getValue_numeric().floatValue() == Float.valueOf(compareValue).floatValue();	
	          }
	          break;
	        case CHECKBOX_TYPE:
	          if (values.get(fieldName).getValue() != null) {
	        	  fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
	  	          return fieldSelected.contains(compareValue);	
	          }
	          break;
	        case DATE_TYPE:
	          return values.get(fieldName).getValue_date().isEqual(LocalDate.parse(compareValue, DateTimeFormatter.ofPattern(FORMAT_DATE)));
	      }
	    }

	    return false;
	}

	private boolean compareGreaterOrEquals(String condition, Map<String, DeindexationInputValue> values) {
		String[] splited = condition.split(CONDITION_GE);
	    String fieldName = splited[0].trim().toUpperCase();

	    if (values.get(fieldName) != null) {
	      String typeField = values.get(fieldName).getInput().getType();
	      String fieldSelected;
	      String compareValue = getComparableValue(splited[1]);

	      switch (typeField) {
	        case TEXT_TYPE: 
	        case TEXT_AREA_TYPE:
	        case SELECT_TYPE:
	        case RADIO_TYPE:
	          fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
	          return fieldSelected.compareTo(compareValue) >= 0;
	        case NUMBER_TYPE:
	          fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
	          return values.get(fieldName).getValue_numeric().floatValue() >= Float.valueOf(compareValue).floatValue();
	        case CHECKBOX_TYPE:
	          // NA
	          break;
	        case DATE_TYPE:
	          LocalDate compareDate = LocalDate.parse(compareValue, DateTimeFormatter.ofPattern(FORMAT_DATE));
	          return values.get(fieldName).getValue_date().isAfter(compareDate) || values.get(fieldName).getValue_date().isEqual(compareDate);
	      }
	    }

	    return false;
	}

	private boolean compareGreater(String condition, Map<String, DeindexationInputValue> values) {
		String[] splited = condition.split(CONDITION_G);
	    String fieldName = splited[0].trim().toUpperCase();

	    if (values.get(fieldName) != null) {
	      String typeField = values.get(fieldName).getInput().getType();
	      String fieldSelected;
	      String compareValue = getComparableValue(splited[1]);

	      switch (typeField) {
	        case TEXT_TYPE: 
	        case TEXT_AREA_TYPE:
	        case SELECT_TYPE:
	        case RADIO_TYPE:
	          fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
	          return fieldSelected.compareTo(compareValue) > 0;
	        case NUMBER_TYPE:
	          fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
	          return values.get(fieldName).getValue_numeric().floatValue() > Float.valueOf(compareValue).floatValue();
	        case CHECKBOX_TYPE:
	          // NA
	          break;
	        case DATE_TYPE:
	          LocalDate compareDate = LocalDate.parse(compareValue, DateTimeFormatter.ofPattern(FORMAT_DATE));
	          return values.get(fieldName).getValue_date().isAfter(compareDate);
	      }
	    }

	    return false;
	}

	private boolean compareLessOrEqual(String condition, Map<String, DeindexationInputValue> values) {
		String[] splited = condition.split(CONDITION_LE);
	    String fieldName = splited[0].trim().toUpperCase();

	    if (values.get(fieldName) != null) {
	      String typeField = values.get(fieldName).getInput().getType();
	      String fieldSelected;
	      String compareValue = getComparableValue(splited[1]);

	      switch (typeField) {
	        case TEXT_TYPE: 
	        case TEXT_AREA_TYPE:
	        case SELECT_TYPE:
	        case RADIO_TYPE:
	          fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
	          return fieldSelected.compareTo(compareValue) <= 0;
	        case NUMBER_TYPE:
	          fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
	          return values.get(fieldName).getValue_numeric().floatValue() <= Float.valueOf(compareValue).floatValue();
	        case CHECKBOX_TYPE:
	          // NA
	          break;
	        case DATE_TYPE:
	          LocalDate compareDate = LocalDate.parse(compareValue, DateTimeFormatter.ofPattern(FORMAT_DATE));
	          return values.get(fieldName).getValue_date().isBefore(compareDate) || values.get(fieldName).getValue_date().isEqual(compareDate);
	      }
	    }

	    return false;
	}

	private boolean compareLess(String condition, Map<String, DeindexationInputValue> values) {
		String[] splited = condition.split(CONDITION_L);
	    String fieldName = splited[0].trim().toUpperCase();

	    if (values.get(fieldName) != null) {
	      String typeField = values.get(fieldName).getInput().getType();
	      String fieldSelected;
	      String compareValue = getComparableValue(splited[1]);

	      switch (typeField) {
	        case TEXT_TYPE: 
	        case TEXT_AREA_TYPE:
	        case SELECT_TYPE:
	        case RADIO_TYPE:
	          fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
	          return fieldSelected.compareTo(compareValue) < 0;
	        case NUMBER_TYPE:
	          fieldSelected = values.get(fieldName).getValue().trim().toUpperCase();
	          return values.get(fieldName).getValue_numeric().floatValue() < Float.valueOf(compareValue).floatValue();
	        case CHECKBOX_TYPE:
	          // NA
	          break;
	        case DATE_TYPE:
	          LocalDate compareDate = LocalDate.parse(compareValue, DateTimeFormatter.ofPattern(FORMAT_DATE));
	          return values.get(fieldName).getValue_date().isBefore(compareDate);
	      }
	    }

	    return false;
	}

	private String getComparableValue(String value) {
		if (value.contains("'")) {
	      return value.replace("'","").trim().toUpperCase();
	    }

	    return value.trim().toUpperCase();
	}
}
