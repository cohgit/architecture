package cl.atianza.remove.daos.client;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EClientAlertMessageTypes;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.ClientAlertMessage;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveJsonUtil;

/**
 * Plans DAOs methods
 * @author pilin
 */
public class ClientAlertMessageDao extends RemoveDao {
	private static final String TABLE_NAME = "clients_alerts_messages";
	
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
	public ClientAlertMessageDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ClientAlertMessageDao.class), TABLE_NAME);
	}
	
	/**
	 * 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public static ClientAlertMessageDao init() throws RemoveApplicationException {
		return new ClientAlertMessageDao();
	}
	
	/**
	 * List many notification message for a client.
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ClientAlertMessage> listForOwner(Long idOwner, Integer total) throws RemoveApplicationException {
		List<ClientAlertMessage> list = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_OWNER, idOwner) + " ORDER BY " + FIELD_CREATION_DATE + " DESC" + 
				(total != null && total.intValue() > 0 ? " LIMIT " + total.intValue() : "") ;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).executeAndFetch(ClientAlertMessage.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (list != null) {
			list.forEach(message -> {
				message.setTypeObj(EClientAlertMessageTypes.obtain(message.getType()));
				try {
					message.setParameters(RemoveJsonUtil.jsonToData(message.getParams()));
				} catch (RemoveApplicationException e) { }
			});
		}
		
		return list;
	}

	/**
	 * Save a notification message for a client.
	 * @param alert
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientAlertMessage save(ClientAlertMessage alert) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_DESCRIPTION, FIELD_ID_OWNER, FIELD_CREATION_DATE, FIELD_EMAIL_SENT,
				FIELD_MUST_SEND_EMAIL, FIELD_PARAMS, FIELD_READED, FIELD_HIDDEN, FIELD_TITLE, FIELD_TYPE, FIELD_URGENT);
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
	 * Mark a notification email message as sent. 
	 * @param message
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientAlertMessage markEmailSent(ClientAlertMessage message) throws RemoveApplicationException {
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
	 * Mark a notification message as readed.
	 * @param message
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientAlertMessage switchMessageReaded(ClientAlertMessage message, boolean readed) throws RemoveApplicationException {
		message = switchField(message, FIELD_READED, readed);
		message.setReaded(readed);
    	return message;
	}
	/**
	 * Mark a notification message as hidden/not hidden.
	 * @param message
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientAlertMessage switchMessageHidden(ClientAlertMessage message, boolean hidden) throws RemoveApplicationException {
		message = switchField(message, FIELD_HIDDEN, hidden);
		message.setHidden(hidden);
    	return message;
	}
	
	/**
	 * Mark a notification message field as true or false.
	 * @param message
	 * @param field
	 * @param value
	 * @return
	 * @throws RemoveApplicationException
	 */
	private ClientAlertMessage switchField(ClientAlertMessage message, String field, boolean value) throws RemoveApplicationException {
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
	 * List a group of notifications from a client by type.
	 * @param id_client
	 * @param messageCode
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ClientAlertMessage> listNotificationByClientType(Long id_client, String messageCode) throws RemoveApplicationException {
		List<ClientAlertMessage> list = null;
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_OWNER, FIELD_TYPE);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY)
					.addParameter(FIELD_ID_OWNER, id_client)
					.addParameter(FIELD_TYPE, messageCode).executeAndFetch(ClientAlertMessage.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (list != null) {
			list.forEach(message -> {
				message.setTypeObj(EClientAlertMessageTypes.obtain(message.getType()));
				try {
					message.setParameters(RemoveJsonUtil.jsonToData(message.getParams()));
				} catch (RemoveApplicationException e) { }
			});
		}
		
		return list;
	}

	/**
	 * Get a notification message.
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientAlertMessage getById(Long id) throws RemoveApplicationException {
		return (ClientAlertMessage) getByField(TABLE, FIELD_ID, id, ClientAlertMessage.class);
	}
}
