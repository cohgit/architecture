package cl.atianza.remove.daos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.helpers.ConfigHelper;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Abstract class with attributes and methods necesaries for Dao's transactions
 * @author Jose Gutierrez
 *
 */
public abstract class AbstractDao {
	public static final String FIELD_ID = "id";
	
	protected static final Long INIT_RECORD_VALUE = Long.valueOf(0);
	protected static final String ORDER_TYPE_ASC = "ASC";
	protected static final String ORDER_TYPE_DESC = "DESC";
	
	protected static final String SUBQUERY_TEMPLATE = "SELECT |RETURN| FROM |TABLES| WHERE |CONDITIONS| ";
	
	private Logger log;
	protected String TABLE;
	
	public AbstractDao() {
		super();
	}
	
	public AbstractDao(Logger _log) {
		super();
		
		this.log = _log;
	}
	public AbstractDao(Logger _log, String TABLE_NAME) throws RemoveApplicationException {
		super();
		
		this.log = _log;
		this.TABLE = schemaTable(TABLE_NAME);
	}
	
	protected Logger getLog() {
		if(this.log == null) {
			this.log = LogManager.getLogger(AbstractDao.class);
			
			log.warn("***** Se olvidó instanciar la clase del LOGGER");
		}
		
		return this.log;
	}

	/**
	 * Concat fields' names with ',' between them
	 * @param fields
	 * @return
	 */
	protected static String concatFields(String... fields) {
		String query = "";
		
		if(fields != null) {
			query = StringUtils.join(fields, ",");
		}
		
		return query;
	}
	/**
	 * Concat values wuth ':' in front for sql2o statements
	 * @param values
	 * @return
	 */
	protected static String concatValues(String... values) {
		String query = "";
		
		if(values != null) {
			List<String> lstValues = new ArrayList<String>();
			
			for(String v : values) {
				lstValues.add(":" + v);
			}
			
			query = StringUtils.join(lstValues, ",");
		}
		
		return query;
	}
	/**
	 * Concat the INSERT part for sql2o statements
	 * @param fields
	 * @return
	 */
	protected static String concatForInsert(String... fields) {
		String query = "";
		
		if(fields != null) {
			query = " ("+concatFields(fields)+") VALUES (" + concatValues(fields) + ")";
		}
		
		return query;
	}
	/**
	 * Build a statements for SELECT all 
	 * @param tableName
	 * @return
	 */
	protected static String buildSelectAllQuery(String tableName) {
		return "SELECT * FROM " + tableName;
	}
	/**
	 * Build a SELECT statement using WHERE conditional with a key field and value.
	 * @param tableName
	 * @param keyField
	 * @param value
	 * @return
	 */
	protected static String buildSelectKeyQuery(String tableName, String keyField, String value) {
		return "SELECT * FROM " + tableName + " WHERE " + keyField + " = '" + value + "'";
	}
	protected static String buildSelectKeyQuery(String tableName, String keyField, int value) {
		return "SELECT * FROM " + tableName + " WHERE " + keyField + " = " + value;
	}
	protected static String buildSelectKeyQuery(String tableName, String keyField, boolean value) {
		return "SELECT * FROM " + tableName + " WHERE " + keyField + " = " + value;
	}
	protected static String buildSelectKeyQuery(String tableName, String keyField, long value) {
		return "SELECT * FROM " + tableName + " WHERE " + keyField + " = " + value;
	}
	protected static String buildSelectKeyQuery(String tableName, String keyField, double value) {
		return "SELECT * FROM " + tableName + " WHERE " + keyField + " = " + value;
	}
	
	/**
	 * Build a SELECT statement using LIKE conditional with a key field and value
	 * @param tableName
	 * @param keyField
	 * @return
	 */
	protected static String buildSelectStartsWithQuery(String tableName, String keyField) {
		return "SELECT * FROM " + tableName + " WHERE " + keyField + " LIKE :" + keyField;
	}
	/**
	 * Build a SELECT statement using IN conditional with a key field and value
	 * @param tableName
	 * @param keyField
	 * @return
	 */
	protected static String buildSelectINQuery(String tableName, String keyField, List<String> values) {
		return "SELECT * FROM " + tableName + " WHERE " + keyField + " IN (" + StringUtils.join(values, ",") + ") ";
	}
	protected static String buildSelectINKeyQuery(String tableName, String keyField, List<Long> values) {
		return "SELECT * FROM " + tableName + " WHERE " + keyField + " IN (" + StringUtils.join(values, ",") + ") ";
	}
	/**
	 * Build a query statement for sql2o for records before a date field.
	 * @param tableName
	 * @param keyField
	 * @param value
	 * @param includeEquals
	 * @return
	 */
	protected static String buildSelectBeforeDateQuery(String tableName, String keyField, LocalDate value, boolean includeEquals) {
		return "SELECT * FROM " + tableName + " WHERE " + keyField + 
				(includeEquals ? " <= '" : " < '") 
				+ RemoveDateUtils.formatPostgres(value) + "'";
	}
	/**
	 * Build a query statement for sql2o for records after a date field.
	 * @param tableName
	 * @param keyField
	 * @param value
	 * @param includeEquals
	 * @return
	 */
	protected static String buildSelectAfterDateQuery(String tableName, String keyField, LocalDate value, boolean includeEquals) {
		return "SELECT * FROM " + tableName + " WHERE " + keyField + 
				(includeEquals ? " >= '" : " > '") 
				+ RemoveDateUtils.formatPostgres(value) + "'";
	}
	/**
	 * Build a query statement for sql2o for records between two dates in a date field.
	 * @param tableName
	 * @param keyField
	 * @param initDate
	 * @param endDate
	 * @param includeInit
	 * @param includeEnd
	 * @return
	 */
	protected static String buildSelectBetweenDateQuery(String tableName, String keyField, LocalDate initDate, LocalDate endDate, boolean includeInit, boolean includeEnd) {
		return "SELECT * FROM " + tableName + " WHERE " + keyField + 
				(includeInit ? " >= '" : " > '") 
				+ RemoveDateUtils.formatPostgres(initDate) + "' AND " + keyField + 
				(includeEnd ? " <= '" : " < '")
				+ RemoveDateUtils.formatPostgres(endDate) + "' ";
	}
	/**
	 * Build a query statement for sql2o for a tag field (String in upper case).
	 * @param tableName
	 * @param keyField
	 * @param value
	 * @return
	 */
	protected static String buildSelectKeyQueryForTags(String tableName, String keyField, String value) {
		return "SELECT * FROM " + tableName + " WHERE UPPER(" + keyField + ") = '" + value.toUpperCase() + "'";
	}
	/**
	 *  Build a query statement for sql2o for a select with many wheres.
	 * @param tableName
	 * @param fields
	 * @return
	 */
	protected static String buildSelectManyWheres(String tableName, String...fields) {
		return "SELECT * FROM " + tableName + concatSubqueryWheres(fields);
	}
	/**
	 *  Build a query statement for sql2o for select and specific page (For table purpose)
	 * @param tableName
	 * @param nPagina
	 * @param cantidad
	 * @param sortBy
	 * @param sortType
	 * @param searchLike
	 * @param fieldsSearchLike
	 * @return
	 */
	protected String buildSelectPageQuery(String tableName, int nPagina, int cantidad, 
			String sortBy, String sortType, String searchLike, String... fieldsSearchLike) {
		int offset = (nPagina) * cantidad;
		sortType = sortType != null ? sortType : "ASC";
		sortBy = sortBy != null ? sortBy : FIELD_ID;
		
		if(searchLike == null || fieldsSearchLike == null || fieldsSearchLike.length == 0) {
			return "SELECT * FROM " + tableName + " ORDER BY " + sortBy + " " + sortType + 
					" OFFSET " + offset + " ROWS FETCH NEXT " + cantidad + " ROWS ONLY";
		} else {
			searchLike = "'%"+searchLike+"%'";
			
			return "SELECT * FROM " + tableName + 
					" WHERE " + fillSearchLike(searchLike, fieldsSearchLike) +
					" ORDER BY " + sortBy + " " + sortType + 
					" OFFSET " + offset + " ROWS FETCH NEXT " + cantidad + " ROWS ONLY";
		}
	}
	/**
	 *  Build a query statement for sql2o for select and specific page (For table purpose)
	 * @param tableName
	 * @param nPagina
	 * @param cantidad
	 * @param sortBy
	 * @param sortType
	 * @param searchLike
	 * @param fieldsSearchLike
	 * @return
	 */
	protected String buildSelectPageQuery(String query, String table, int nPagina, int cantidad, 
			String sortBy, String sortType, String groupBy) {
		int offset = (nPagina) * cantidad;
		sortType = sortType != null ? sortType : "ASC";
		sortBy = sortBy != null ? sortBy : FIELD_ID;
		
		if (groupBy != null && !groupBy.trim().isEmpty()) {
			return query.replace("|RETURN|", table+".*") + 
					" GROUP BY " + groupBy +
					" ORDER BY " + sortBy + " " + sortType + 
					" OFFSET " + offset + " ROWS FETCH NEXT " + cantidad + " ROWS ONLY";
		} else {
			return query.replace("|RETURN|", table+".*") + " ORDER BY " + sortBy + " " + sortType + 
					" OFFSET " + offset + " ROWS FETCH NEXT " + cantidad + " ROWS ONLY";
		}
	}
	/**
	 * Build a substring of a Query sql2o statement for search like (Table purpose)
	 * @param searchLike
	 * @param fieldsSearchLike
	 * @return
	 */
	private String fillSearchLike(String searchLike, String[] fieldsSearchLike) {
		String subQuery = "";
		
		for(String field : fieldsSearchLike) {
			subQuery += " UPPER(CAST(" + field + " AS VARCHAR)) LIKE UPPER(" + searchLike + ") OR ";
		}
		
		return subQuery.substring(0, subQuery.length()-4);
	}
	/**
	 * Concat many WHEREs conditonals in a single String.
	 * @param fields
	 * @return
	 */
	private static String concatSubqueryWheres(String[] fields) {
		String subquery = "";
		
		if(fields != null && fields.length>0) {
			subquery = " WHERE ";
			
			for(String field : fields) {
				subquery += field + " = :" + field + " AND ";
			}
		}
		
		return subquery.isEmpty() ? subquery : subquery.substring(0, subquery.length() - 5);
	}
	
	/**
	 * Build an INSERT query statement with a table name and many fields.
	 * @param tableName
	 * @param fields
	 * @return
	 */
	protected static String buildInsertQuery(String tableName, String... fields) {
		return "INSERT INTO " + tableName + concatForInsert(fields);
	}
	/**
	 * Build an UPDATE query statement with a table name and many fields.
	 * @param tableName
	 * @param fields
	 * @return
	 */
	protected static String buildUpdateQuery(String tableName, String keyField, String keyValue, String... fields) {
		return "UPDATE " + tableName + " SET " + concatForUpdate(fields) + " WHERE " + keyField + " = '" + keyValue + "'";
	}
	protected static String buildUpdateQueryNullValueCondition(String tableName, String keyField, String... fields) {
		return "UPDATE " + tableName + " SET " + concatForUpdate(fields) + " WHERE " + keyField + " = null";
	}
	protected String buildUpdateQuery(String tableName, String keyField, int keyValue, String... fields) {
		return "UPDATE " + tableName + " SET " + concatForUpdate(fields) + " WHERE " + keyField + " = " + keyValue;
	}
	protected String buildUpdateQuery(String tableName, String keyField, long keyValue, String... fields) {
		return "UPDATE " + tableName + " SET " + concatForUpdate(fields) + " WHERE " + keyField + " = " + keyValue;
	}
	protected String buildUpdateQuery(String tableName, String keyField, boolean keyValue, String... fields) {
		return "UPDATE " + tableName + " SET " + concatForUpdate(fields) + " WHERE " + keyField + " = " + keyValue;
	}
	protected String buildUpdateQuery(String tableName, String keyField, double keyValue, String... fields) {
		return "UPDATE " + tableName + " SET " + concatForUpdate(fields) + " WHERE " + keyField + " = " + keyValue;
	}
	/**
	 * Build an DELETE query statement with a table name and a key field
	 * @param tableName
	 * @param keyField
	 * @param keyValue
	 * @return
	 */
	protected String buildDeleteQuery(String tableName, String keyField, int keyValue) {
		return "DELETE FROM " + tableName + " WHERE " + keyField + " = " + keyValue;
	}
	protected String buildDeleteQuery(String tableName, String keyField, Integer keyValue) {
		return "DELETE FROM " + tableName + " WHERE " + keyField + " = " + keyValue;
	}
	protected String buildDeleteQuery(String tableName, String keyField, Long keyValue) {
		return "DELETE FROM " + tableName + " WHERE " + keyField + " = " + keyValue;
	}
	protected String buildDeleteQuery(String tableName, String keyField, String keyValue) {
		return "DELETE FROM " + tableName + " WHERE " + keyField + " = '" + keyValue + "'";
	}
	
	/**
	 * Concat fields for UPDATE statement
	 * @param fields
	 * @return
	 */
	protected static String concatForUpdate(String[] fields) {
		String subquery = "";
		
		if(fields != null) {
			for(String field : fields) {
				subquery += field + " = :" + field + ", ";
			}
		}
		
		return subquery.isEmpty() ? subquery : subquery.substring(0, subquery.length()-2);
	}
	/**
	 * Execute a SELECT query that counts how many records are in a db table.
	 * @param tableName
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected Long countRecords(String tableName) throws RemoveApplicationException {
		return countRecords(tableName, null);
	}
	/**
	 * Execute a SELECT query that counts how many records are in a db table, using a LIKE condition
	 * @param tableName
	 * @param searchLike
	 * @param fieldsSearchLike
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected Long countRecords(String tableName, String searchLike, String... fieldsSearchLike) throws RemoveApplicationException {
		String QUERY = buildCountQuery(tableName, searchLike, fieldsSearchLike);
		
		Long total = null;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			total = conn.createQuery(QUERY).executeAndFetchFirst(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return total;
	}
	/**
	 * Execute a SELECT query that counts how many records are in a db table, replacing values in query template
	 * @param subquery
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected int countRecordsWithSubquery(String subquery) throws RemoveApplicationException {
		String QUERY = subquery.replace("|RETURN|", "COUNT(*)");
		Integer total = null;
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			total = conn.createQuery(QUERY).executeAndFetchFirst(Integer.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return total;
	}
	
	/**
	 * 
	 * @param tableName
	 * @param searchLike
	 * @param fieldsSearchLike
	 * @return
	 */
	private String buildCountQuery(String tableName, String searchLike, String... fieldsSearchLike) {
		if(searchLike == null || fieldsSearchLike == null || fieldsSearchLike.length == 0) {
			return "SELECT COUNT(*) FROM " + tableName;
		} else {
			searchLike = "'%"+searchLike+"%'";
			
			return "SELECT COUNT(*) FROM " + tableName + 
					" WHERE " + fillSearchLike(searchLike, fieldsSearchLike);
		}
	}
	
	/**
	 * Validate idRecord, it's a match validation for correct upsertions in database.
	 * @param idRecord
	 * @throws RemoveApplicationException
	 */
	protected void validateIdRecord(long idRecord) throws RemoveApplicationException {
		if( idRecord == INIT_RECORD_VALUE.longValue() ) {
    		RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_UPDATING_DATABASE);
		}
	}
	
	/**
	 * Catch general exceptions for standarized on RemoveApplicationException
	 * @param t
	 * @param message
	 * @param params
	 * @throws RemoveApplicationException
	 */
	protected void catchSeveralException(Throwable t, EMessageBundleKeys message, String... params) throws RemoveApplicationException {
		catchException(getLog(), t, message, true, params);
	}
	/**
	 * Catch general exceptions for standarized on RemoveApplicationException
	 * @param t
	 * @param message
	 * @param params
	 * @throws RemoveApplicationException
	 */
	protected void catchRegularException(Throwable t, EMessageBundleKeys message, String... params) throws RemoveApplicationException {
		catchException(getLog(), t, message, false, params);
	}
	/**
	 * Catch general exceptions for standarized on RemoveApplicationException
	 * @param log
	 * @param t
	 * @param message
	 * @param isSeveral
	 * @param params
	 * @throws RemoveApplicationException
	 */
	public static void catchException(Logger log, Throwable t, EMessageBundleKeys message, boolean isSeveral, String... params) throws RemoveApplicationException {
		if(t instanceof RemoveApplicationException) {
			RemoveApplicationException ae = (RemoveApplicationException) t;
			
			if(ae.isSeveral()) {
				if (message != null) {
					ae.setSeveralError(message);
				}
				
				log.error(ae.getSeveralError(), ae);
				RemoveExceptionHandler.throwSeveralAppException(message);
			} else {
				if (message != null) {
					ae.setError(message);
				}
				
				if(params != null && params.length > 0) {
					log.error(ae.getError()+": (params: "+StringUtils.join(params, ", "));
				} else {
					log.error(ae.getError());
				}
				RemoveExceptionHandler.throwAppException(ae.getError());
			}
		} else if(t instanceof Exception) {
			log.error(EMessageBundleKeys.ERROR_INTERNAL_SERVER, t);
			
			if (isSeveral) {
				RemoveExceptionHandler.throwSeveralAppException(message);
			} else {
				RemoveExceptionHandler.throwAppException(message);
			}
		}
		
		RemoveExceptionHandler.throwSeveralAppException(message);
	}
	/**
	 * Catch general exceptions for standarized on RemoveApplicationException
	 * @param t
	 * @param params
	 * @throws RemoveApplicationException
	 */
	protected void catchException(Throwable t, String... params) throws RemoveApplicationException {
		catchException(getLog(), t, params);
	}
	/**
	 * Catch general exceptions for standarized on RemoveApplicationException
	 * @param log
	 * @param t
	 * @param params
	 * @throws RemoveApplicationException
	 */
	public static void catchException(Logger log, Throwable t, String... params) throws RemoveApplicationException {
		if(t instanceof RemoveApplicationException) {
			RemoveApplicationException ae = (RemoveApplicationException) t;
			
			if(ae.isSeveral()) {
				log.error(ae.getSeveralError(), ae);
				RemoveExceptionHandler.throwSeveralAppException(ae.getSeveralError());
			} else {
				if(params != null && params.length > 0) {
					log.error(ae.getError()+": (params: "+StringUtils.join(params, ", "));
				} else {
					log.error(ae.getError());
				}
				RemoveExceptionHandler.throwAppException(ae.getError());
			}
		} else if(t instanceof Exception) {
			log.error(EMessageBundleKeys.ERROR_INTERNAL_SERVER, t);
			RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_INTERNAL_SERVER);
		}
		
		RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_INTERNAL_SERVER);
	}
	/**
	 * 
	 * @param table
	 * @param searchLike
	 * @param fieldsSearchLike
	 * @return
	 */
	protected String buildSearchLikeWhereSubquery(String table, String searchLike, String... fieldsSearchLike) {
		if (searchLike == null) {
			searchLike = "'%%'";
		} else {
			searchLike = "'%" + searchLike + "%'";
		}

		String subQuery = "(";
		
		for(String field : fieldsSearchLike) {
			subQuery += " UPPER(CAST(" + table + "." + field + " AS VARCHAR)) LIKE UPPER(" + searchLike + ") OR ";
		}
		
		return subQuery.substring(0, subQuery.length()-4)+")";
	}
	/**
	 * 
	 * @param field
	 * @param value
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected String substringConditionFieldIntValue(String field, String value) throws RemoveApplicationException {
		if (value != null && !value.isEmpty()) {
			try {
				return " " + field + "=" + Integer.valueOf(value) + " ";
			} catch(Exception ex) {
				catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
			}
		}

		return "";
	}
	/**
	 * 
	 * @param field
	 * @param value
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected String substringConditionFieldStringValue(String field, String value) throws RemoveApplicationException {
		if (value != null && !value.isEmpty()) {
			try {
				return " " + field + "='" + value + "' ";
			} catch(Exception ex) {
				catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
			}
		}

		return "";
	}
	/**
	 * 
	 * @param field
	 * @param value
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected String substringConditionFieldBooleanValue(String field, boolean value) throws RemoveApplicationException {
		try {
			return " " + field + "=" + value + " ";
		} catch(Exception ex) {
			catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
		}

		return "";
	}
	/**
	 * 
	 * @param field
	 * @param values
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected String substringConditionFieldArrayValue(String field, List<Integer> values) throws RemoveApplicationException {
		if (values != null && !values.isEmpty()) {
			try {
				return " " + field + " IN (" + StringUtils.join(values, ",") + ") ";
			} catch(Exception ex) {
				catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
			}
		}

		return "";
	}
	/**
	 * 
	 * @param field
	 * @param value
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected String substringConditionFieldDateLesserEqualValue(String field, String value) throws RemoveApplicationException {
		return substringConditionFieldDateValue(field, RemoveDateUtils.addLocalDateDay(value, 1), "<=");
	}
	/**
	 * 
	 * @param field
	 * @param value
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected String substringConditionFieldDateGreaterEqualValue(String field, String value) throws RemoveApplicationException {
		return substringConditionFieldDateValue(field, value, ">=");
	}
	/**
	 * 
	 * @param field
	 * @param value
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected String substringConditionFieldDateEqualsValue(String field, String value) throws RemoveApplicationException {
		return substringConditionFieldDateValue(field, value, "=");
	}
	/**
	 * 
	 * @param field
	 * @param value
	 * @param comparator
	 * @return
	 * @throws RemoveApplicationException
	 */
	private String substringConditionFieldDateValue(String field, String value, String comparator) throws RemoveApplicationException {
		if (value != null && !value.isEmpty()) {
			try {
				if(RemoveDateUtils.parseLocalDate(value) != null) {
					return " " + field + comparator + "'" + RemoveDateUtils.transformToPostgresFormat(value) + "' ";
				} else {
					RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_DATABASE);
				}
			} catch(Exception ex) {
				catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
			}
		}
		
		return "";
	}
	/**
	 * 
	 * @param lstValues
	 * @param tempTable
	 * @param tempColumn
	 * @return
	 */
	protected String subQueryTempTable(List<Integer> lstValues, String tempTable, String tempColumn) {
		return "(values (" + String.join("),(", lstValues.stream()
				.map(s -> String.valueOf(s))
				.collect(Collectors.toList())) + ")) as " + tempTable + " (" + tempColumn + ")";
	}
	/**
	 * 
	 * @param field1
	 * @param field2
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected String substringConditionFieldField(String field1, String field2) throws RemoveApplicationException {
		return " " + field1 + "=" + field2 + " ";
	}
	
	/**
	 * 
	 * @param tableName
	 * @param searchBy
	 * @param key
	 * @return
	 */
	protected String buildCountQuerybyKey(String tableName, String searchBy, int key) {
		return "SELECT COUNT(*) FROM " + tableName +
			" WHERE " +  searchBy + " = " + key + "";
	}
	/**
	 * 
	 * @param tableName
	 * @param fields
	 * @return
	 */
	protected static String buildSelectManyWheresWithOr(String tableName, String...fields) {
		return "SELECT * FROM " + tableName + concatSubqueryWheresWithOr(fields);
	}
	/**
	 * 
	 * @param fields
	 * @return
	 */
	private static String concatSubqueryWheresWithOr(String[] fields) {
		String subquery = "";

		if(fields != null && fields.length>0) {
			subquery = " WHERE ";

			for(String field : fields) {
				subquery += field + " = :" + field + " OR ";
			}
		}

		return subquery.isEmpty() ? subquery : subquery.substring(0, subquery.length() - 4);
	}
	/**
	 * Execute a SELECT query by FIELD_ID key in WHERE statement
	 * @param table
	 * @param id
	 * @param valueType
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected Object getById(String table, Long id, Class<?> valueType) throws RemoveApplicationException {
		String QUERY = buildSelectKeyQuery(table, FIELD_ID, id);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetchFirst(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Execute a SELECT query by a field key in WHERE statement
	 * @param table
	 * @param field
	 * @param id
	 * @param valueType
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected Object getByField(String table, String field, Long id, Class<?> valueType) throws RemoveApplicationException {
		String QUERY = buildSelectKeyQuery(table, field, id);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetchFirst(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Execute a SELECT query by a field key in WHERE statement
	 * @param table
	 * @param field
	 * @param id
	 * @param valueType
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected Object getByField(String table, String field, Boolean value, Class<?> valueType) throws RemoveApplicationException {
		String QUERY = buildSelectKeyQuery(table, field, value);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetchFirst(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Execute a SELECT query by a field key in WHERE statement
	 * @param table
	 * @param field
	 * @param value
	 * @param valueType
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected Object getByField(String table, String field, String value, Class<?> valueType) throws RemoveApplicationException {
		String QUERY = buildSelectKeyQuery(table, field, value);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetchFirst(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Execute a SELECT query recovering all records from a table
	 * @param table
	 * @param valueType
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected List<?> listAll(String table, Class<?> valueType) throws RemoveApplicationException {
		String QUERY = buildSelectAllQuery(table);
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Execute a SELECT query recovering all records from a table
	 * @param table
	 * @param valueType
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected List<?> listAll(String table, Class<?> valueType, String orderByField, String orderByParameter) throws RemoveApplicationException {
		String QUERY = buildSelectAllQuery(table) + " ORDER BY " + orderByField + " " + orderByParameter;
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Execute a SELECT query recovering all records from a table
	 * @param table
	 * @param valueType
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected List<?> listAllOrderByField(String table, Class<?> valueType, String orderByField, String ascOrDesc) throws RemoveApplicationException {
		String QUERY = buildSelectAllQuery(table) + " ORDER BY " + orderByField + " " + ascOrDesc;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Execute a SELECT query recovering a list of records from a table
	 * @param table
	 * @param field
	 * @param id
	 * @param valueType
	 * @param orderByField
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected List<?> listByField(String table, String field, Long id, Class<?> valueType, String orderByField) throws RemoveApplicationException {
		String QUERY = buildSelectKeyQuery(table, field, id) + " ORDER BY " + orderByField;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Execute a SELECT query recovering a list of records from a table
	 * @param table
	 * @param field
	 * @param id
	 * @param valueType
	 * @param orderByField
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected List<?> listByField(String table, String field, Long id, Class<?> valueType, String orderByField, String order) throws RemoveApplicationException {
		String QUERY = buildSelectKeyQuery(table, field, id) + " ORDER BY " + orderByField + " " + order;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Execute a SELECT query recovering a list of records from a table with a date field in condition statement
	 * @param table
	 * @param field
	 * @param value
	 * @param includeEquals
	 * @param valueType
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected List<?> listByBeforeDate(String table, String field, LocalDate value, boolean includeEquals, Class<?> valueType) throws RemoveApplicationException {
		String QUERY = buildSelectBeforeDateQuery(table, field, value, includeEquals);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Execute a SELECT query recovering a list of records from a table with a date field in condition statement
	 * @param table
	 * @param field
	 * @param value
	 * @param includeEquals
	 * @param valueType
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected List<?> listByAfterDate(String table, String field, LocalDate value, boolean includeEquals, Class<?> valueType) throws RemoveApplicationException {
		String QUERY = buildSelectAfterDateQuery(table, field, value, includeEquals);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Execute a SELECT query recovering a list of records from a table with a date field in condition statement
	 * @param table
	 * @param field
	 * @param initDate
	 * @param endDate
	 * @param includeInit
	 * @param includeEnd
	 * @param valueType
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected List<?> listByBetweenDates(String table, String field, LocalDate initDate, LocalDate endDate, boolean includeInit, boolean includeEnd, Class<?> valueType) throws RemoveApplicationException {
		String QUERY = buildSelectBetweenDateQuery(table, field, initDate, endDate, includeInit, includeEnd);
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Execute a SELECT query recovering a list of records from a table with a date field in condition statement
	 * @param table
	 * @param field
	 * @param value
	 * @param valueType
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected List<?> listByField(String table, String field, boolean value, Class<?> valueType) throws RemoveApplicationException {
		String QUERY = buildSelectKeyQuery(table, field, value);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	protected List<?> listByField(String table, String field, String value, Class<?> valueType) throws RemoveApplicationException {
		String QUERY = buildSelectKeyQuery(table, field, value);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(valueType);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	/**
	 * Recover a schema table by system config
	 * @param table
	 * @return
	 */
	protected String schemaTable(String table) {
		return schemaTable(ConfigHelper.getDBSchemaName(), table);
	}
	/**
	 * Recover a schema table by schemaName parameter
	 * @param schemaName
	 * @param table
	 * @return
	 */
	protected static String schemaTable(String schemaName, String table) {
		return schemaName+"."+table;
	}
}
