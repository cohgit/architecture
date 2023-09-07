package cl.atianza.remove.daos.admin;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.UserSession;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * User's sessions DAOs methods.
 * @author pilin
 *
 */
public class UserSessionDao extends RemoveDao {
	public static final String TABLE_NAME = "users_sessions";
	
	private static final String FIELD_ID_OWNER = "id_owner";
	private static final String FIELD_TOKEN = "token";
	private static final String FIELD_ENTRY_DATE = "entry_date";
	private static final String FIELD_REFRESH_DATE = "refresh_date";
	private static final String FIELD_CLOSE_DATE = "close_date";
	private static final String FIELD_IP = "ip";
	private static final String FIELD_AGENT = "agent";
	
	public UserSessionDao() throws RemoveApplicationException {
		super(LogManager.getLogger(UserSessionDao.class), TABLE_NAME);
	}

	public static UserSessionDao init() throws RemoveApplicationException {
		return new UserSessionDao();
	}

	/**
	 * Obtain the last user session by user id.
	 * @param user
	 * @return
	 * @throws RemoveApplicationException
	 */
	public UserSession getByUserId(UserSession user) throws RemoveApplicationException {
		return user != null ? getByUserId(user.getId()) : null;
	}
	/**
	 * Obtain the last user session by user id.
	 * @param idUser
	 * @return
	 * @throws RemoveApplicationException
	 */
	public UserSession getByUserId(Long idUser) throws RemoveApplicationException {
		if (idUser == null) {
			return null;
		}
		
		UserSession session = null;
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_OWNER, idUser) + " ORDER BY " + FIELD_ENTRY_DATE + " DESC ";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			session = conn.createQuery(QUERY).executeAndFetchFirst(UserSession.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return session;
    }
	/**
	 * Save an user session.
	 * @param session
	 * @return
	 * @throws RemoveApplicationException
	 */
    public UserSession save(UserSession session) throws RemoveApplicationException {
    	String QUERY = buildInsertQuery(TABLE, FIELD_ID_OWNER, FIELD_AGENT, 
    			FIELD_ENTRY_DATE, FIELD_IP, FIELD_REFRESH_DATE, FIELD_TOKEN);
    	
    	Long idRecord = INIT_RECORD_VALUE;
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_OWNER, session.getId_owner())
    				.addParameter(FIELD_AGENT, session.getAgent())
    				.addParameter(FIELD_ENTRY_DATE, RemoveDateUtils.nowLocalDateTime())
    				.addParameter(FIELD_REFRESH_DATE, RemoveDateUtils.nowLocalDateTime())
    				.addParameter(FIELD_TOKEN, session.getToken())
    				.addParameter(FIELD_IP, session.getIp()).executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	validateIdRecord(idRecord);
    	session.setId(idRecord);
    	
        return session;
    }
    /**
     * Refresh an user session, updating FIELD_REFRESH_DATE.
     * @param token
     * @throws RemoveApplicationException
     */
    public void refresh(String token) throws RemoveApplicationException {
    	String QUERY = buildUpdateQuery(TABLE, FIELD_TOKEN, token, FIELD_REFRESH_DATE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_REFRESH_DATE, RemoveDateUtils.nowLocalDateTime()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    }
    /**
     * Close an user session updating FIELD_CLOSE_DATE.
     * @param token
     * @throws RemoveApplicationException
     */
    public void close(String token) throws RemoveApplicationException {
    	String QUERY = buildUpdateQuery(TABLE, FIELD_TOKEN, token, FIELD_CLOSE_DATE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_CLOSE_DATE, RemoveDateUtils.nowLocalDateTime()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    }
}
