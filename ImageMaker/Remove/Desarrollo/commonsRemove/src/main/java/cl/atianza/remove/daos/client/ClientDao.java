package cl.atianza.remove.daos.client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.daos.commons.CountryDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerResultDao;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Client DAOs methods.
 * @author pilin
 *
 */
public class ClientDao extends RemoveDao {
	private static final String TABLE_NAME = "clients";
	
	private static final String FIELD_UUID = "uuid";
	private static final String FIELD_ID_COUNTRY = "id_country";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_LASTNAME = "lastname";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_LANGUAGE = "language";
	private static final String FIELD_PHONE = "phone";
	private static final String FIELD_PROJECT_NAME = "project_name";
	private static final String FIELD_CORPORATIVE = "corporative";
	private static final String FIELD_BUSINESS_NAME = "business_name";
	private static final String FIELD_DNI = "dni";
	private static final String FIELD_FISCAL_ADDRESS = "fiscal_address";
	private static final String FIELD_POSTAL = "postal";
	private static final String FIELD_REFERENCE_LINK_LOGO = "reference_link_logo";
	private static final String FIELD_PERIOCITY = "periocity";
	private static final String FIELD_VIEW_PAYMENTS = "view_payments";
	
	
	private static final String FIELD_FAILED_ATTEMPTS = "failed_attempts";
	private static final String FIELD_ACTIVE = "active";
	private static final String FIELD_FIRST_TIME = "first_time";
	private static final String FIELD_EMAIL_VERIFIED = "email_verified";
	
	private static final String FIELD_READ_ONLY = "readOnly";
	private static final String FIELD_MESSAGE = "message";
	private static final String FIELD_SEND_EMAIL = "send_email";
	
	private static final String REL_TABLE_USER = "users_clients";
	private static final String REL_FIELD_ID_USER = "id_user";
	private static final String REL_FIELD_ID_CLIENT = "id_client";
	
	public ClientDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ClientDao.class), TABLE_NAME);
	}
	
	public static ClientDao init() throws RemoveApplicationException {
		return new ClientDao();
	}	
	
	/**
	 * Obtain a client's data from id.
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client getById(long id) throws RemoveApplicationException {
		Client client = getBasicById(id);
		
		loadInnerData(client);
		
		return client;
	}
	/**
	 * Obtain a client's data from id.
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client getBasicById(long id) throws RemoveApplicationException {
		Client client = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID, id);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			client = conn.createQuery(QUERY).executeAndFetchFirst(Client.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return client;
	}
	/**
	 * Obtain a client's data from uuid.
	 * @param uuid
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client getByUuid(String uuid) throws RemoveApplicationException {
		Client client = getBasicByUuid(uuid);
		
		loadInnerData(client);
		
		return client;
	}
	/**
	 * Obtain basic data of a client's data from uuid.
	 * @param uuid
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client getBasicByUuid(String uuid) throws RemoveApplicationException {
		Client client = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_UUID, uuid);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			client = conn.createQuery(QUERY).executeAndFetchFirst(Client.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return client;
	}
	/**
	 * Obtain a client's data from email with inner data.
	 * @param email
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client getByLogin(String email) throws RemoveApplicationException {
		Client client = getByEmail(email);
		
		loadInnerData(client);
		
		return client;
	}
	/**
	 * Obtain a client's basic data from email.
	 * @param email
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client getByEmail(String email) throws RemoveApplicationException {
		String QUERY = buildSelectManyWheres(TABLE, FIELD_EMAIL);
		
		Client client = null;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			client = conn.createQuery(QUERY)
					.addParameter(FIELD_EMAIL, email)
					.executeAndFetchFirst(Client.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return client;
	}
	/**
	 * Obtain a client's basic data.
	 * @param phone
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client getByPhone(String phone) throws RemoveApplicationException {
		String QUERY = buildSelectManyWheres(TABLE, FIELD_PHONE);
		
		Client client = null;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			client = conn.createQuery(QUERY)
					.addParameter(FIELD_PHONE, phone)
					.executeAndFetchFirst(Client.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return client;
	}
	
	/**
	 * Fill inner attributes of a client object.
	 * @param client
	 * @throws RemoveApplicationException
	 */
	private void loadInnerData(Client client) throws RemoveApplicationException {
		if (client != null) {
			client.setCountry(CountryDao.init().get(client.getId_country()));
			client.setPlanActives(ClientPlanDao.init().listByClientId(client.getId()));
		}
	}

	/**
	 * Insert client information into database.
	 * @param client
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client save(Client client) throws RemoveApplicationException {
		client.setFailed_attempts(0);
		client.setEmail_verified(false);
		client.setReadOnly(true);
		client.setMessage(EMessageBundleKeys.WARNING_CLIENT_MUST_VERIFY_EMAIL.getTag());
		client.setSend_email(true);
		client.setReference_link_logo(null);
		client.setPeriocity(1);
		client.setView_payments(false);
		
		String QUERY = buildInsertQuery(TABLE, FIELD_UUID, FIELD_ID_COUNTRY, FIELD_NAME, FIELD_LASTNAME,
				FIELD_PHONE, FIELD_EMAIL, FIELD_PROJECT_NAME, FIELD_LANGUAGE, FIELD_CORPORATIVE, FIELD_FAILED_ATTEMPTS, FIELD_ACTIVE,
				FIELD_BUSINESS_NAME, FIELD_DNI, FIELD_FISCAL_ADDRESS, FIELD_POSTAL, FIELD_EMAIL_VERIFIED, FIELD_READ_ONLY, FIELD_MESSAGE, FIELD_SEND_EMAIL, FIELD_REFERENCE_LINK_LOGO, FIELD_PERIOCITY
				, FIELD_VIEW_PAYMENTS);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_UUID, client.getUuid())
    				.addParameter(FIELD_ID_COUNTRY, client.getId_country())
    				.addParameter(FIELD_NAME, client.getName())
    				.addParameter(FIELD_LASTNAME, client.getLastname())
    				.addParameter(FIELD_PHONE, client.getPhone())
    				.addParameter(FIELD_EMAIL, client.getEmail())
    				.addParameter(FIELD_LANGUAGE, client.getLanguage())
    				.addParameter(FIELD_FAILED_ATTEMPTS, client.getFailed_attempts())
    				.addParameter(FIELD_ACTIVE, client.isActive())
    				.addParameter(FIELD_PROJECT_NAME, client.getProject_name())
    				.addParameter(FIELD_CORPORATIVE, client.isCorporative())
    				.addParameter(FIELD_BUSINESS_NAME, client.getBusiness_name())
    				.addParameter(FIELD_DNI, client.getDni())
    				.addParameter(FIELD_FISCAL_ADDRESS, client.getFiscal_address())
    				.addParameter(FIELD_POSTAL, client.getPostal())
    				.addParameter(FIELD_EMAIL_VERIFIED, client.isEmail_verified())
    				.addParameter(FIELD_READ_ONLY, client.isReadOnly())
    				.addParameter(FIELD_MESSAGE, client.getMessage())
    				.addParameter(FIELD_SEND_EMAIL, client.isSend_email())
    				.addParameter(FIELD_REFERENCE_LINK_LOGO, client.getReference_link_logo())
    				.addParameter(FIELD_PERIOCITY, client.getPeriocity())
    				.addParameter(FIELD_VIEW_PAYMENTS, client.isView_payments())
    				.executeUpdate().getKey();
    	    
    		//.addParameter(FIELD_REFERENCE_LINK_LOGO, client.getReference_link_logo())
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	client.setId(idRecord);
    	
    	return client;
	}

	/**
	 * Update first time clients session in database.
	 * @param idClient
	 * @return
	 * @throws RemoveApplicationException
	 */
	public boolean updateFirstTime(Long idClient) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, idClient, FIELD_FIRST_TIME);
		
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
	 * Mark client's email as verified.
	 * @param idClient
	 * @param email
	 * @return
	 * @throws RemoveApplicationException
	 */
	public boolean updateEmailVerified(Long idClient, String email) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, idClient, FIELD_EMAIL_VERIFIED, FIELD_EMAIL);
		
		try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_EMAIL_VERIFIED, true)
    				.addParameter(FIELD_EMAIL, email)
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
	 * Update times client as retried login process.
	 * @param client
	 * @param loginRetries
	 * @throws RemoveApplicationException
	 */
	public void updateFailedTries(Client client, Integer loginRetries) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, client.getId(), FIELD_FAILED_ATTEMPTS);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_FAILED_ATTEMPTS, client.getFailed_attempts()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	/**
	 * Switch clients active/deactive
	 * @param idClient
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client switchActive(Long idClient) throws RemoveApplicationException {
		Client client = getById(idClient);
		client.setActive(!client.isActive());
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, idClient, FIELD_ACTIVE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_ACTIVE, client.isActive()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return client;
	}
	
	/**
	 * Change client's attribute readonly.
	 * @param idClient
	 * @param readOnly
	 * @param message
	 * @throws RemoveApplicationException
	 */
	public void updateReadOnly(Long idClient, boolean readOnly, String message) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, idClient, FIELD_READ_ONLY, FIELD_MESSAGE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    			.addParameter(FIELD_READ_ONLY, readOnly)
    			.addParameter(FIELD_MESSAGE, message).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	
	/**
	 * List all clients registered in the system.
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<Client> listAll() throws RemoveApplicationException {
		List<Client> list = (List<Client>) listAll(TABLE, Client.class);

		if (list != null) {
			list.forEach(client -> {
				try {
					client.setPlanActives(ClientPlanDao.init().listByClientId(client.getId()));
					client.setLastSuscription(ClientPlanDao.init().getLastSuscriptionByClient(client.getId()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading plan and suscriptions: ", e);
				}
			});
		}

		return list;
	}
	
	/**
	 * List all active clients in the system and periocity equal 15.
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Client> listActivesFifteen() throws RemoveApplicationException {
		String QUERY = "SELECT * FROM "+TABLE+ " WHERE "+FIELD_ACTIVE+" = true"+ " AND "+ FIELD_SEND_EMAIL+" = true "+" AND "+FIELD_PERIOCITY+" = 15";;

		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(Client.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }

		return null;
	}
	
	/**
	 * List all active clients in the system and periocity equals 01.
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Client> listActivesOne() throws RemoveApplicationException {
		String QUERY = "SELECT * FROM "+TABLE+ " WHERE "+FIELD_ACTIVE+" = true"+ " AND "+ FIELD_SEND_EMAIL+" = true "+" AND "+FIELD_PERIOCITY+" = 1";

		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(Client.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }

		return null;
	}
	
	/**
	 * List all active clients in the system.
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Client> listActives() throws RemoveApplicationException {
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ACTIVE, true);

		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(Client.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }

		return null;
	}
	/**
	 * List all clients associated to a user in the system.
	 * @param idUser
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Client> listByUser(Long idUser) throws RemoveApplicationException {
		List<Long> clientIds = listIdsByUser(idUser);
		List<Client> list = new ArrayList<Client>();
		
		if (clientIds != null && !clientIds.isEmpty()) {
			String QUERY = buildSelectINKeyQuery(TABLE, FIELD_ID, clientIds);

			try (Connection conn = ConnectionDB.getSql2oRO().open()) {
				list = conn.createQuery(QUERY).executeAndFetch(Client.class);
	        } catch (Exception ex) {
	        	getLog().error("Error loading clients: ", ex);
	        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
	        }
		}
		
		if (list != null) {
			list.forEach(client -> {
				try {
					client.setPlanActives(ClientPlanDao.init().listByClientId(client.getId()));
					client.setLastSuscription(ClientPlanDao.init().getLastSuscriptionByClient(client.getId()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading plans and suscriptions: ", e);
				}
			});
		}

		return list;
	}
	/**
	 * List all ids clients associated to a user in the system.
	 * @param idUser
	 * @return
	 * @throws RemoveApplicationException
	 */
	private List<Long> listIdsByUser(Long idUser) throws RemoveApplicationException {
		String QUERY = "SELECT " + REL_FIELD_ID_CLIENT + " FROM " + schemaTable(REL_TABLE_USER) + " WHERE " + REL_FIELD_ID_USER + " = " + idUser;

		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}
	public List<Long> listIdsByClient(Long idClient) throws RemoveApplicationException {
		String QUERY = "SELECT " + REL_FIELD_ID_USER + " FROM " + schemaTable(REL_TABLE_USER) + " WHERE " + REL_FIELD_ID_CLIENT + " = " + idClient;

		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetch(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return null;
	}

	/**
	 * Delete all clients relations with an user.
	 * @param idUser
	 * @throws RemoveApplicationException
	 */
	public void removeUserClients(Long idUser) throws RemoveApplicationException {
		String QUERY = buildDeleteQuery(schemaTable(REL_TABLE_USER), REL_FIELD_ID_USER, idUser);
		
		try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	/**
	 * Insert all clients relations with an user.
	 * @param idUser
	 * @param idClient
	 * @throws RemoveApplicationException
	 */
	public void saveUserClient(Long idUser, Long idClient) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(schemaTable(REL_TABLE_USER), REL_FIELD_ID_USER, REL_FIELD_ID_CLIENT);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(REL_FIELD_ID_USER, idUser)
    				.addParameter(REL_FIELD_ID_CLIENT, idClient)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	/**
	 * Update account config fields from a client. 
	 * @param client
	 * @throws RemoveApplicationException
	 */
	public void updateAccountData(Client client) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, client.getId(), FIELD_NAME, FIELD_LASTNAME, FIELD_PHONE, 
				FIELD_LANGUAGE, FIELD_ID_COUNTRY, FIELD_SEND_EMAIL, FIELD_EMAIL,FIELD_REFERENCE_LINK_LOGO, FIELD_PERIOCITY, FIELD_VIEW_PAYMENTS);
		
		try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_NAME, client.getName())
    				.addParameter(FIELD_LASTNAME, client.getLastname())
    				.addParameter(FIELD_PHONE, client.getPhone())
    				.addParameter(FIELD_LANGUAGE, client.getLanguage())
    				.addParameter(FIELD_ID_COUNTRY, client.getCountry().getId())
    				.addParameter(FIELD_EMAIL, client.getEmail())
    				.addParameter(FIELD_SEND_EMAIL, client.isSend_email())
    				.addParameter(FIELD_REFERENCE_LINK_LOGO, client.getReference_link_logo())
    				.addParameter(FIELD_PERIOCITY, client.getPeriocity())
    				.addParameter(FIELD_VIEW_PAYMENTS, client.isView_payments())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	/**
	 * Return id owner from a scanner result.
	 * @param id_scanner_result
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Long findIdClientByScannerResultOwner(Long id_scanner_result) throws RemoveApplicationException {
		ScannerResult result = ScannerResultDao.init().getById(id_scanner_result);
		Scanner scanner = ScannerDao.init().getBasicById(result.getId_scanner());
		return scanner.getId_owner();
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Client> checkNewToAssign(User user) throws RemoveApplicationException {
		List<Client> newsAssigned = new ArrayList<Client>();
		
		if (user.getClients() != null) {
			List<Long> oldAssigned = listIdsByUser(user.getId());
			
			for (Client newClient : user.getClients()) {
				boolean found = false;
				for (Long oldClient : oldAssigned) {
					if (oldClient.longValue() == newClient.getId().longValue()) {
						found = true;
						break;	
					}
				};
				if (found) newsAssigned.add(newClient);
			};	
		}
		
		return newsAssigned;
	}

	/**
	 * List all clients whos suscriptions will end in the next days.
	 * @param nextDays
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Client> listNextDue(int nextDays) throws RemoveApplicationException {
		List<Client> result = new ArrayList<Client>();
		List<ClientPlan> listClientPlans = ClientPlanDao.init().expiresBetween(LocalDate.now(), LocalDate.now().plusDays(nextDays));
		
		if (listClientPlans != null) {
			listClientPlans.forEach(clientPlan -> {
				try {
					result.add(getById(clientPlan.getId_client()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading listNextDue: "+ nextDays, e);
				}
			});
		}
		
		return result;
	}

	/**
	 * Update clients info.
	 * @param client
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client update(Client client) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, client.getId(), FIELD_NAME, FIELD_LASTNAME, FIELD_PHONE, 
    			FIELD_EMAIL, FIELD_LANGUAGE, FIELD_REFERENCE_LINK_LOGO, FIELD_PERIOCITY, FIELD_VIEW_PAYMENTS);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_NAME, client.getName())
    			.addParameter(FIELD_LASTNAME, client.getLastname())
				.addParameter(FIELD_PHONE, client.getPhone())
				.addParameter(FIELD_LANGUAGE, client.getLanguage())
		        .addParameter(FIELD_EMAIL, client.getEmail())
		        .addParameter(FIELD_REFERENCE_LINK_LOGO, client.getReference_link_logo())
		        .addParameter(FIELD_PERIOCITY, client.getPeriocity())
		        .addParameter(FIELD_VIEW_PAYMENTS, client.isView_payments())
		        .executeUpdate();
    	    
    		//.addParameter(FIELD_REFERENCE_LINK_LOGO, client.getReference_link_logo())
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
        return client;
	}
}
