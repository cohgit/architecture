package cl.atianza.remove.daos.admin;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.UserAuditAccess;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * User Audit Access and Actions DAOs methods.
 * @author pilin
 *
 */
public class UserAuditAccessDao extends RemoveDao {
	public static final String TABLE_NAME = "users_audit_access";
	
	private static final String FIELD_ID_USER = "id_user";
	private static final String FIELD_PROFILE = "profile";
	private static final String FIELD_DESCRIPTION = "description";
	private static final String FIELD_PARAMETERS = "parameters";
	private static final String FIELD_ACTION_DATE = "action_date";
	
	public UserAuditAccessDao() throws RemoveApplicationException {
		super(LogManager.getLogger(UserAuditAccessDao.class), TABLE_NAME);
	}

	public static UserAuditAccessDao init() throws RemoveApplicationException {
		return new UserAuditAccessDao();
	}

	/**
	 * Save user audit access data into database.
	 * @param audit
	 * @return
	 */
    public UserAuditAccess save(UserAuditAccess audit) {
    	try {
    		audit.setAction_date(RemoveDateUtils.nowLocalDateTime());
        	
        	String QUERY = buildInsertQuery(TABLE, FIELD_ID_USER, FIELD_PROFILE, FIELD_DESCRIPTION, 
        			FIELD_PARAMETERS, FIELD_ACTION_DATE);
        	
        	Long idRecord = INIT_RECORD_VALUE;
        	
        	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
        		idRecord = (Long) con.createQuery(QUERY, true)
        				.addParameter(FIELD_ID_USER, audit.getId_user())
        				.addParameter(FIELD_PROFILE, audit.getProfile())
        				.addParameter(FIELD_DESCRIPTION, audit.getDescription())
        				.addParameter(FIELD_PARAMETERS, audit.getParameters())
        				.addParameter(FIELD_ACTION_DATE, audit.getAction_date()).executeUpdate().getKey();
        	    
        	    // Remember to call commit() when a transaction is opened,
        	    // default is to roll back.
        	    con.commit();
        	} catch (Exception ex) {
        		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        	}
        	
        	validateIdRecord(idRecord);
        	audit.setId(idRecord);
    	} catch (RemoveApplicationException ex) {
    		getLog().error("Error saving audit:", ex);
    	}
    	
        return audit;
    }
    
    /**
     * List all audit access from a user.
     * @param uuidUser
     * @return
     * @throws RemoveApplicationException
     */
    public List<UserAuditAccess> listByUuid(String uuidUser) throws RemoveApplicationException {
    	return listByIdUser(UserDao.init().getBasicByUuid(uuidUser).getId());
    }

    /**
     * List all audit access from a user.
     * @param idUser
     * @return
     * @throws RemoveApplicationException
     */
	@SuppressWarnings("unchecked")
	public List<UserAuditAccess> listByIdUser(Long idUser) throws RemoveApplicationException {
		return (List<UserAuditAccess>) listByField(TABLE, FIELD_ID_USER, idUser, UserAuditAccess.class, FIELD_ACTION_DATE);
	}

	/**
	 * Save user audit access data into database.
	 * @param idUser
	 * @param profile
	 * @param message
	 */
	public void save(Long idUser, String profile, EMessageBundleKeys message) {
		save(idUser, profile, message, null);
	}
	/**
	 * Save user audit access data into database.
	 * @param idUser
	 * @param profile
	 * @param message
	 * @param params
	 */
	public void save(Long idUser, String profile, EMessageBundleKeys message, Map<String, Object> params) {
		UserAuditAccess audit = new UserAuditAccess();
		audit.setId_user(idUser);
		audit.setProfile(profile);
		audit.setDescription(message.getTag());
		
		if (params != null) {
			audit.setParamsObj(params);
			audit.parseParams();	
		}
		
		save(audit);
	}
}
