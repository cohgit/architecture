package cl.atianza.remove.daos.client;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientAuditAccess;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Client Audit Access DAOs methods, recording all operations by/to a client.
 * @author pilin
 *
 */
public class ClientAuditAccessDao extends RemoveDao {
	public static final String TABLE_NAME = "clients_audit_access";
	
	private static final String FIELD_ID_CLIENT = "id_client";
	private static final String FIELD_ID_USER_ACTION = "id_user_action";
	private static final String FIELD_PROFILE_USER_ACTION = "profile_user_action";
	private static final String FIELD_DESCRIPTION = "description";
	private static final String FIELD_PARAMETERS = "parameters";
	private static final String FIELD_ACTION_DATE = "action_date";
	
	public ClientAuditAccessDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ClientAuditAccessDao.class), TABLE_NAME);
	}

	public static ClientAuditAccessDao init() throws RemoveApplicationException {
		return new ClientAuditAccessDao();
	}

	/**
	 * Insert a Client Audit Access data into database.
	 * @param audit
	 * @return
	 * @throws RemoveApplicationException
	 */
    public ClientAuditAccess save(ClientAuditAccess audit) throws RemoveApplicationException {
    	audit.setAction_date(RemoveDateUtils.nowLocalDateTime());
    	
    	String QUERY = buildInsertQuery(TABLE, FIELD_ID_CLIENT, FIELD_ID_USER_ACTION, 
    			FIELD_PROFILE_USER_ACTION, FIELD_DESCRIPTION, FIELD_PARAMETERS, FIELD_ACTION_DATE);
    	
    	Long idRecord = INIT_RECORD_VALUE;
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_CLIENT, audit.getId_client())
    				.addParameter(FIELD_ID_USER_ACTION, audit.getId_user_action())
    				.addParameter(FIELD_PROFILE_USER_ACTION, audit.getProfile_user_action())
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
        return audit;
    }
    /**
     * List all audit access related to a client.
     * @param uuidClient
     * @return
     * @throws RemoveApplicationException
     */
    public List<ClientAuditAccess> listByIdClient(String uuidClient) throws RemoveApplicationException {
    	return listByIdClient(ClientDao.init().getBasicByUuid(uuidClient).getId());
    }
	/**
	 * List all audit access related to a client.
	 * @param idClient
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<ClientAuditAccess> listByIdClient(Long idClient) throws RemoveApplicationException {
		List<ClientAuditAccess> list = (List<ClientAuditAccess>) listByField(TABLE, FIELD_ID_CLIENT, idClient, ClientAuditAccess.class, FIELD_ACTION_DATE, "DESC");
		
		ClientDao clientDao = ClientDao.init();
		UserDao userDao = UserDao.init();
		
		if (list != null) {
			list.forEach(audit -> {
				audit.setProfileUserActionType(EProfiles.obtain(audit.getProfile_user_action()));
				try {
					if (audit.getProfile_user_action().equals(EProfiles.CLIENT.getCode())) {
						Client client = clientDao.getBasicById(audit.getId_user_action());
						audit.setNameUserAction(client.getName() + " " + client.getLastname());
					} else {
						User user = userDao.getBasicById(audit.getId_user_action());
						audit.setNameUserAction(user.getName() + " " + user.getLastname());
					}
				} catch (RemoveApplicationException e) {
					getLog().error("Error finding user action for audit: " + audit, e);
				}
			});
		}
		
		return list;
	}
	/**
	 * Save an audit access related to a client.
	 * @param idClient
	 * @param idUserAction
	 * @param profile
	 * @param message
	 * @throws RemoveApplicationException
	 */
	public void save(Long idClient, Long idUserAction, String profile, EMessageBundleKeys message) throws RemoveApplicationException {
		save(idClient, idUserAction, profile, message, null);
	}
	/**
	 * Save an audit access related to a client.
	 * @param idClient
	 * @param idUserAction
	 * @param profile
	 * @param message
	 * @param params
	 * @throws RemoveApplicationException
	 */
	public void save(Long idClient, Long idUserAction, String profile, EMessageBundleKeys message, Map<String, Object> params) throws RemoveApplicationException {
		ClientAuditAccess audit = new ClientAuditAccess();
		audit.setId_client(idClient);
		audit.setId_user_action(idUserAction);
		audit.setProfile_user_action(profile);
		audit.setDescription(message.getTag());
		
		if (params != null) {
			audit.setParamsObj(params);
			audit.parseParams();	
		}
		
		save(audit);
	}
	/**
	 * Save an audit access related to a client.
	 * @param idClient
	 * @param message
	 * @throws RemoveApplicationException
	 */
	public void saveForClient(Long idClient, EMessageBundleKeys message) throws RemoveApplicationException {
		saveForClient(idClient, message, null);
	}
	/**
	 * Save an audit access related to a client.
	 * @param idClient
	 * @param message
	 * @param params
	 * @throws RemoveApplicationException
	 */
	public void saveForClient(Long idClient, EMessageBundleKeys message, Map<String, Object> params) throws RemoveApplicationException {
		ClientAuditAccess audit = new ClientAuditAccess();
		audit.setId_client(idClient);
		audit.setId_user_action(idClient);
		audit.setProfile_user_action(EProfiles.CLIENT.getCode());
		audit.setDescription(message.getTag());
		
		if (params != null) {
			audit.setParamsObj(params);
			audit.parseParams();			
		}

		save(audit);
	}
}
