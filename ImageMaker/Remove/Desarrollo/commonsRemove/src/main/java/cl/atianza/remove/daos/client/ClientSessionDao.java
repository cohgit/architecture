package cl.atianza.remove.daos.client;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.ClientSession;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Client's sessions DAOs methods.
 * @author pilin
 *
 */
public class ClientSessionDao extends RemoveDao {
	public static final String TABLE_NAME = "clients_sessions";
	
	private static final String FIELD_ID_OWNER = "id_owner";
	private static final String FIELD_TOKEN = "token";
	private static final String FIELD_ENTRY_DATE = "entry_date";
	private static final String FIELD_REFRESH_DATE = "refresh_date";
	private static final String FIELD_CLOSE_DATE = "close_date";
	private static final String FIELD_IP = "ip";
	private static final String FIELD_AGENT = "agent";
	
	public ClientSessionDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ClientSessionDao.class), TABLE_NAME);
	}

	public static ClientSessionDao init() throws RemoveApplicationException {
		return new ClientSessionDao();
	}

	/**
	 * Obtain the last client session by client id.
	 * @param client
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientSession getByClientId(ClientSession client) throws RemoveApplicationException {
		return client != null ? getByClientId(client.getId()) : null;
	}
	/**
	 * Obtain the last client session by client id.
	 * @param idClient
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientSession getByClientId(Long idClient) throws RemoveApplicationException {
		if (idClient == null) {
			return null;
		}
		
		ClientSession session = null;
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_OWNER, idClient) + " ORDER BY " + FIELD_ENTRY_DATE + " DESC ";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			session = conn.createQuery(QUERY).executeAndFetchFirst(ClientSession.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return session;
    }
	/**
	 * Save a Client session
	 * @param session
	 * @return
	 * @throws RemoveApplicationException
	 */
    public ClientSession save(ClientSession session) throws RemoveApplicationException {
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
     * Refresh a client session, updating FIELD_REFRESH_DATE.
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
     * Close a client session updating FIELD_CLOSE_DATE.
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
