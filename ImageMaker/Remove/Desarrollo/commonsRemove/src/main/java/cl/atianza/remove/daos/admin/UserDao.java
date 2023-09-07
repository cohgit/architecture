package cl.atianza.remove.daos.admin;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;
import org.sql2o.Query;

import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.UserNotificationHelper;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.admin.UserPassword;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * User's DAOs methods.
 * @author pilin
 *
 */
public class UserDao extends RemoveDao {
	public static final String TABLE_NAME = "users";
	
	public static final String FIELD_ID = "id";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_LASTNAME = "lastname";
	private static final String FIELD_UUID = "uuid";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_PHONE = "phone";
	private static final String FIELD_LANGUAGE = "language";
	private static final String FIELD_ACTIVE = "active";
	private static final String FIELD_LOCKED = "locked";
	private static final String FIELD_FAILED_ATTEMPTS = "failed_attempts";
	private static final String FIELD_PROFILE = "profile";
	private static final String FIELD_FIRST_TIME = "first_time";
	private static final String FIELD_SEND_EMAIL = "send_email";
	
	public UserDao() throws RemoveApplicationException {
		super(LogManager.getLogger(UserDao.class), TABLE_NAME);
	}

	public static UserDao init() throws RemoveApplicationException {
		return new UserDao();
	}

	/**
	 * Obtain an user information by id.
	 * 
	 * @param user
	 * @return
	 * @throws RemoveApplicationException 
	 */
	public User getById(User user) throws RemoveApplicationException {
		return user != null ? getById(user.getId()) : null;
	}
	/**
	 * Obtain an user information by id.
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public User getById(Long id) throws RemoveApplicationException {
		if (id == null) {
			return null;
		}
		
		User user = (User) getById(TABLE, id, User.class);
		
		if (user != null) {
			innerLoad(user);
		}
		
		return user;
    }
	
	/**
	 * Load inner attributes for a user.
	 * @param user
	 * @throws RemoveApplicationException
	 */
	private void innerLoad(User user) throws RemoveApplicationException {
		user.setClients(ClientDao.init().listByUser(user.getId()));
	}
	/**
	 * Load basic data from a user.
	 * @param uuidAccount
	 * @return
	 * @throws RemoveApplicationException
	 */
	public User getBasicByUuid(String uuidAccount) throws RemoveApplicationException {
		if (uuidAccount == null) {
			return null;
		}
		
		return (User) getByField(TABLE, FIELD_UUID, uuidAccount, User.class);
	}
	/**
	 * 
	 * @param uuidAccount
	 * @return
	 * @throws RemoveApplicationException
	 */
	public User getByUuid(String uuidAccount) throws RemoveApplicationException {
		if (uuidAccount == null) {
			return null;
		}
		
		User user = getBasicByUuid(uuidAccount);
		
		if (user != null) {
			innerLoad(user);
		}
		
		return user;
	}
	/**
	 * Load all info from a user.
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public User getBasicById(Long id) throws RemoveApplicationException {
		User account = getById(id);
		
		if (account != null) {
			account.setFailed_attempts(null);
		}
		
		return account;
    }
	/**
	 * Load basic data from a user.
	 * @param email
	 * @return
	 * @throws RemoveApplicationException
	 */
	public User getByLogin(String email) throws RemoveApplicationException {
		return (User) getByField(TABLE, FIELD_EMAIL, email, User.class);
	}
	/**
	 * Load basic data from a user.
	 * @param phone
	 * @return
	 * @throws RemoveApplicationException
	 */
	public User getByPhone(String phone) throws RemoveApplicationException {
		return (User) getByField(TABLE, FIELD_PHONE, phone, User.class);
	}
	/**
	 * List basic data from all users in the platform.
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<User> listAll() throws RemoveApplicationException {
		return (List<User>) listAll(TABLE, User.class);
	}

	/**
	 * Insert an user information into database
	 * 
	 * @param user
	 * @return
	 * @throws RemoveApplicationException 
	 */
    public User save(User user) throws RemoveApplicationException {
    	user.setSend_email(true);
    	String QUERY = buildInsertQuery(TABLE, FIELD_NAME, FIELD_LASTNAME, FIELD_PHONE, FIELD_UUID, 
    			FIELD_EMAIL, FIELD_LANGUAGE, FIELD_PROFILE, FIELD_SEND_EMAIL);
    	
    	Long idRecord = INIT_RECORD_VALUE;
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) fillQueryObject(con.createQuery(QUERY, true), user).executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	validateIdRecord(idRecord);
    	
    	user.setId(idRecord);
    	UserPasswordDao.init().saveDefault(user);
    	
    	refreshClientList(user);
    	
        return user;
    }

	/**
     * Update an user information into database and refresh the list of clients related to this user.
     * 
     * @param user
     * @return
     * @throws RemoveApplicationException 
     */
    public User updateAsAdmin(User user) throws RemoveApplicationException {
    	update(user);
    	
    	refreshClientList(user);
    	
        return user;
    }
    /**
     * Update an user information into database
     * 
     * @param user
     * @return
     * @throws RemoveApplicationException
     */
    public User update(User user) throws RemoveApplicationException {
    	String QUERY = buildUpdateQuery(TABLE, FIELD_ID, user.getId(), FIELD_NAME, FIELD_LASTNAME, FIELD_PHONE, 
    			FIELD_EMAIL, FIELD_LANGUAGE, FIELD_PROFILE, FIELD_SEND_EMAIL);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_NAME, user.getName())
    			.addParameter(FIELD_LASTNAME, user.getLastname())
				.addParameter(FIELD_PHONE, user.getPhone())
				.addParameter(FIELD_LANGUAGE, user.getLanguage())
		        .addParameter(FIELD_EMAIL, user.getEmail())
		        .addParameter(FIELD_PROFILE, user.getProfile())
		        .addParameter(FIELD_SEND_EMAIL, user.isSend_email())
		        .executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
        return user;
    }
    /**
     * Update account info from a user.
     * @param user
     * @throws RemoveApplicationException
     */
    public void updateAccount(User user) throws RemoveApplicationException {
    	String QUERY = buildUpdateQuery(TABLE, FIELD_ID, user.getId(), FIELD_NAME, FIELD_LASTNAME, FIELD_PHONE, FIELD_EMAIL,
    			FIELD_LANGUAGE, FIELD_SEND_EMAIL);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_NAME, user.getName())
    			.addParameter(FIELD_LASTNAME, user.getLastname())
				.addParameter(FIELD_PHONE, user.getPhone())
				.addParameter(FIELD_LANGUAGE, user.getLanguage())
				.addParameter(FIELD_EMAIL, user.getEmail())
				.addParameter(FIELD_SEND_EMAIL, user.isSend_email())
		        .executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
    
    /**
     * Refresh list of clients of this user.
     * @param user
     * @throws RemoveApplicationException
     */
    private void refreshClientList(User user) throws RemoveApplicationException {
    	ClientDao clientDao = ClientDao.init();
    	
    	List<Client> newsAssigned = clientDao.checkNewToAssign(user);
    	if (newsAssigned != null && !newsAssigned.isEmpty()) UserNotificationHelper.init(user).createNewClientsAssignedNotification(newsAssigned);
    	
    	clientDao.removeUserClients(user.getId());
		
		if (user.getClients() != null && !user.getClients().isEmpty()) {
			user.getClients().forEach(client -> {
				try {
					clientDao.saveUserClient(user.getId(), client.getId());
				} catch (RemoveApplicationException e) {
					getLog().error("Error matching user/client:" + client, e);
				}
			});
		}
	}
    /**
     * Switch a user by 'active' attribute.
     * @param user
     * @return
     * @throws RemoveApplicationException
     */
	public User switchActive(User user) throws RemoveApplicationException {
    	String QUERY = buildUpdateQuery(TABLE, FIELD_ID, user.getId(), FIELD_ACTIVE);
    	user.setActive(!user.isActive());
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_ACTIVE, user.isActive()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
        return user;
	}
    /**
     * Fill query insert or update functions.
     * @param query
     * @param user
     * @return
     */
    private Query fillQueryObject(Query query, User user) {
    	return query.addParameter(FIELD_NAME, user.getName())
    		.addParameter(FIELD_LASTNAME, user.getLastname())
			.addParameter(FIELD_PHONE, user.getPhone())
			.addParameter(FIELD_UUID, user.getUuid())
			.addParameter(FIELD_LANGUAGE, user.getLanguage())
	        .addParameter(FIELD_EMAIL, user.getEmail())
	        .addParameter(FIELD_PROFILE, user.getProfile())
	        .addParameter(FIELD_SEND_EMAIL, user.isSend_email());
    }
	/**
	 * 
	 * @param account
	 * @param maxLoginRetries
	 * @throws RemoveApplicationException
	 */
	public void updateFailedTries(User account, Integer maxLoginRetries) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, account.getId(), FIELD_FAILED_ATTEMPTS);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_FAILED_ATTEMPTS, account.getFailed_attempts()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	if (account.getFailed_attempts() >= maxLoginRetries) {
    		block(account);
    	}
    	
    	if (account.getFailed_attempts() == 0) {
    		unblock(account);
    	}
	}
	/**
	 * 
	 * @param account
	 * @throws RemoveApplicationException
	 */
	public void block(User account) throws RemoveApplicationException {
		updateBlock(account, true);
	}
	/**
	 * 
	 * @param account
	 * @throws RemoveApplicationException
	 */
	public void unblock(User account) throws RemoveApplicationException {
		updateBlock(account, false);
	}
	/**
	 * 
	 * @param account
	 * @param blockValue
	 * @throws RemoveApplicationException
	 */
	private void updateBlock(User account, boolean blockValue) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, account.getId(), FIELD_LOCKED);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_LOCKED, blockValue).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	/**
	 * Update if is the first time a user is logged in the system.
	 * @param idUser
	 * @return
	 * @throws RemoveApplicationException
	 */
	public boolean updateFirstTime(Long idUser) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, idUser, FIELD_FIRST_TIME);
		
		try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_FIRST_TIME, false)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
		
		return true;
	}
	
	/**
	 * @param idClient
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<User> listByClientRelation(Long idClient) throws RemoveApplicationException {
		List<Long> lstIds = ClientDao.init().listIdsByClient(idClient);
		
		if (lstIds != null && !lstIds.isEmpty()) {
			String QUERY = buildSelectINKeyQuery(TABLE, FIELD_ID, lstIds);
			try (Connection conn = ConnectionDB.getSql2oRO().open()) {
				return conn.createQuery(QUERY).executeAndFetch(User.class);
	        } catch (Exception ex) {
	        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
	        }	
		}
		
		return null;
	}
	
	/**
	 * Create the first user of the system (Server installation process)
	 * @throws RemoveApplicationException
	 */
	public void createDefaultUser() throws RemoveApplicationException {
		User defaultAdminUser = new User();
		defaultAdminUser.setName("ADMIN");
		defaultAdminUser.setLastname("ADMIN");
		defaultAdminUser.setPhone("XXXX-XXXXX-XXXXX");
		defaultAdminUser.setEmail("removeapp@atianza.com");
		defaultAdminUser.setLanguage("es");
		defaultAdminUser.setProfile(EProfiles.ADMIN.getCode());
		
		defaultAdminUser = save(defaultAdminUser);
		
		UserPassword pass = new UserPassword();
		pass.setId_owner(defaultAdminUser.getId());
		pass.setPassword("Admin.123");
		pass.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		pass.setActive(true);
		
		UserPasswordDao.init().save(pass);
	}

	/**
	 * @param profile
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<User> listByProfile(EProfiles profile) throws RemoveApplicationException {
		return (List<User>) listByField(TABLE, FIELD_PROFILE, profile.getCode(), User.class);
	}
}
