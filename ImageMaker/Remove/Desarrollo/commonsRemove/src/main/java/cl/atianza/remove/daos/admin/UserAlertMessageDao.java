package cl.atianza.remove.daos.admin;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EUserAlertMessageTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.UserAlertMessage;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveJsonUtil;

/**
 * Plans DAOs methods
 * @author pilin
 */
public class UserAlertMessageDao extends RemoveDao {
	private static final String TABLE_NAME = "users_alerts_messages";
	
	private static final String FIELD_ID_OWNER = "id_owner";
	private static final String FIELD_TITLE = "title";
	private static final String FIELD_DESCRIPTION = "description";
	private static final String FIELD_TYPE = "type";
	private static final String FIELD_PARAMS = "params";
	private static final String FIELD_CREATION_DATE = "creation_date";
	
	private static final String FIELD_READED = "readed";
	private static final String FIELD_HIDDEN = "hidden";
	private static final String FIELD_URGENT = "urgent";
	private static final String FIELD_MUST_SEND_EMAIL = "must_send_email";
	private static final String FIELD_EMAIL_SENT = "email_sent";
	
	/**
	 * 
	 * @throws RemoveApplicationException
	 */
	public UserAlertMessageDao() throws RemoveApplicationException {
		super(LogManager.getLogger(UserAlertMessageDao.class), TABLE_NAME);
	}
	
	/**
	 * 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public static UserAlertMessageDao init() throws RemoveApplicationException {
		return new UserAlertMessageDao();
	}
	
	/**
	 * 
	 * @param total 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<UserAlertMessage> listForOwner(Long idOwner, Integer total) throws RemoveApplicationException {
		List<UserAlertMessage> list = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_OWNER, idOwner) + " ORDER BY " + FIELD_CREATION_DATE + " DESC" + 
				(total != null && total.intValue() > 0 ? " LIMIT " + total.intValue() : "");
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).executeAndFetch(UserAlertMessage.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (list != null) {
			list.forEach(message -> {
				message.setTypeObj(EUserAlertMessageTypes.obtain(message.getType()));
				try {
					message.setParameters(RemoveJsonUtil.jsonToData(message.getParams()));
				} catch (RemoveApplicationException e) { }
			});
		}
		
		return list;
	}

	/**
	 * 
	 * @param alert
	 * @return
	 * @throws RemoveApplicationException
	 */
	public UserAlertMessage save(UserAlertMessage alert) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_DESCRIPTION, FIELD_ID_OWNER, FIELD_CREATION_DATE, FIELD_EMAIL_SENT,
				FIELD_MUST_SEND_EMAIL, FIELD_PARAMS, FIELD_READED, FIELD_TITLE, FIELD_TYPE, FIELD_URGENT, FIELD_HIDDEN);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_DESCRIPTION, alert.getDescription())
    				.addParameter(FIELD_ID_OWNER, alert.getId_owner())
    				.addParameter(FIELD_CREATION_DATE, alert.getCreation_date())
    				.addParameter(FIELD_EMAIL_SENT, alert.isEmail_sent())
    				.addParameter(FIELD_MUST_SEND_EMAIL, alert.isMust_send_email())
    				.addParameter(FIELD_PARAMS, alert.getParams())
    				.addParameter(FIELD_READED, alert.isReaded())
    				.addParameter(FIELD_HIDDEN, alert.isHidden())
    				.addParameter(FIELD_TITLE, alert.getTitle())
    				.addParameter(FIELD_TYPE, alert.getType())
    				.addParameter(FIELD_URGENT, alert.isUrgent())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	alert.setId(idRecord);
    	
    	return alert;
	}
	
	/**
	 * 
	 * @param message
	 * @return
	 * @throws RemoveApplicationException
	 */
	public UserAlertMessage markEmailSent(UserAlertMessage message) throws RemoveApplicationException {
		message.setEmail_sent(true);
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, message.getId(), FIELD_EMAIL_SENT);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY, true).addParameter(FIELD_EMAIL_SENT, true).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return message;
	}

	/**
	 * 
	 * @param message
	 * @return
	 * @throws RemoveApplicationException
	 */
	public UserAlertMessage switchMessageReaded(UserAlertMessage message, boolean readed) throws RemoveApplicationException {
		message = switchField(message, FIELD_READED, readed);
		message.setReaded(readed);
    	return message;
	}
	/**
	 * 
	 * @param message
	 * @return
	 * @throws RemoveApplicationException
	 */
	public UserAlertMessage switchMessageHidden(UserAlertMessage message, boolean hidden) throws RemoveApplicationException {
		message = switchField(message, FIELD_HIDDEN, hidden);
		message.setHidden(hidden);
    	return message;
	}
	
	/**
	 * 
	 * @param message
	 * @param field
	 * @param value
	 * @return
	 * @throws RemoveApplicationException
	 */
	private UserAlertMessage switchField(UserAlertMessage message, String field, boolean value) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, message.getId(), field);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY, true).addParameter(field, value).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return message;
	}

	/**
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public UserAlertMessage getById(Long id) throws RemoveApplicationException {
		return (UserAlertMessage) getByField(TABLE, FIELD_ID, id, UserAlertMessage.class);
	}
}
