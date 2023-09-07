package cl.atianza.remove.daos.client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Client-Plan Relation DAOs methods. (Replaced in V2 by Suscriptions)
 * @author pilin
 *
 */
@Deprecated
public class ClientPlanDao extends RemoveDao {
	public static final String TABLE_NAME = "clients_plans";
	
	private static final String FIELD_ID_CLIENT = "id_client";
	private static final String FIELD_ID_PLAN = "id_plan";
	private static final String FIELD_ID_CLIENT_PAYBILLS = "id_client_paybills";
	private static final String FIELD_UUID = "uuid";
	private static final String FIELD_SUSCRIBE_DATE = "suscribe_date";
	private static final String FIELD_EXPIRATION_DATE = "expiration_date";
	private static final String FIELD_CANCELLATION_DATE = "cancellation_date";
	private static final String FIELD_CREDITS_USED = "credits_used";
	private static final String FIELD_DEINDEX_USED = "deindex_used";
	private static final String FIELD_EXTERNAL_SUBSCRIPTION_KEY = "external_subscription_key";
	private static final String FIELD_EXTERNAL_SUBSCRIPTION_PLATFORM = "external_subscription_platform";
	private static final String FIELD_ACTIVE = "active";
	
	public ClientPlanDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ClientPlanDao.class), TABLE_NAME);
	}

	public static ClientPlanDao init() throws RemoveApplicationException {
		return new ClientPlanDao();
	}

	/**
	 * Obtain a Client-Plan Data from client id.
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientPlan getById(Long id) throws RemoveApplicationException {
		ClientPlan clientPlan = (ClientPlan) getById(TABLE, id, ClientPlan.class);
		clientPlan.setDetail(PlanDao.init().get(clientPlan.getId_plan()));
		return clientPlan;
	}
	
	/**
	 * Obtain a Client-Plan Data from client id.
	 * @param clientPlan
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ClientPlan> getByClientId(ClientPlan clientPlan) throws RemoveApplicationException {
		return clientPlan != null ? listByClientId(clientPlan.getId_client()) : null;
	}
	/**
	 * Obtain a Client-Plan Data from client id.
	 * @param idClient
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ClientPlan> listByClientId(Long idClient) throws RemoveApplicationException {
		if (idClient == null) {
			return null;
		}
		
		List<ClientPlan> list = null;
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_CLIENT, FIELD_ACTIVE);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY)
					.addParameter(FIELD_ID_CLIENT, idClient)
					.addParameter(FIELD_ACTIVE, true).executeAndFetch(ClientPlan.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (list != null) {
			list.forEach(clientPlan -> {
				try {
					clientPlan.setDetail(PlanDao.init().get(clientPlan.getId_plan()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading client-plan info: ", e);
				}
			});
		}
		
		return list;
    }
	
	/**
	 * @param clientUuid
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ClientPlan> getByClientUuid(String clientUuid) throws RemoveApplicationException {
		if (clientUuid == null) {
			return null;
		}
		
		Client client = ClientDao.init().getByUuid(clientUuid);
		
		return listByClientId(client.getId());
    }
	
	/**
	 * @param clientUuid
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientPlan getLastPlanClientUuid(String clientUuid) throws RemoveApplicationException {
		if (clientUuid == null) {
			return null;
		}
		
		Client client = ClientDao.init().getByUuid(clientUuid);
		
		return getLastByClientId(client.getId());
    }
	
	/**
	 * @param clientUuid
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientPlan getActiveOrLastClientUuid(String clientUuid) throws RemoveApplicationException {
		List<ClientPlan> listClienPlans = ClientPlanDao.init().getByClientUuid(clientUuid);
		return !listClienPlans.isEmpty() ? listClienPlans.get(0) : ClientPlanDao.init().getLastPlanClientUuid(clientUuid);
    }
	
	/**
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientPlan getActiveOrLastClientId(Long id) throws RemoveApplicationException {
		List<ClientPlan> listClienPlans = ClientPlanDao.init().listByClientId(id);
		return !listClienPlans.isEmpty() ? listClienPlans.get(0) : ClientPlanDao.init().getLastByClientId(id);
    }
	
	/**
	 * @param idClient
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientPlan getLastByClientId(Long idClient) throws RemoveApplicationException {
		if (idClient == null) {
			return null;
		}
		
		ClientPlan clientPlan = null;
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_CLIENT, idClient) + " ORDER BY " + FIELD_SUSCRIBE_DATE + " DESC ";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			clientPlan = conn.createQuery(QUERY).executeAndFetchFirst(ClientPlan.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (clientPlan != null) {
			try {
				clientPlan.setDetail(PlanDao.init().get(clientPlan.getId_plan()));
			} catch (RemoveApplicationException e) {
				getLog().error("Error loading client-plan info: ", e);
			}
		}
		
		return clientPlan;
    }
	/**
	 * Insert client plan relation into database
	 * @param clientPlan
	 * @return
	 * @throws RemoveApplicationException
	 */
    public ClientPlan save(ClientPlan clientPlan) throws RemoveApplicationException {
    	if (clientPlan.getSuscribe_date() == null) {
    		clientPlan.setSuscribe_date(RemoveDateUtils.nowLocalDateTime());	
    	}
    	
    	String QUERY = buildInsertQuery(TABLE, FIELD_ID_CLIENT, FIELD_ID_PLAN, FIELD_ACTIVE, FIELD_UUID,
    			FIELD_ID_CLIENT_PAYBILLS, FIELD_SUSCRIBE_DATE, FIELD_EXPIRATION_DATE, 
    			FIELD_EXTERNAL_SUBSCRIPTION_KEY, FIELD_EXTERNAL_SUBSCRIPTION_PLATFORM);
    	
    	Long idRecord = INIT_RECORD_VALUE;
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_CLIENT, clientPlan.getId_client())
    				.addParameter(FIELD_ACTIVE, clientPlan.isActive())
    				.addParameter(FIELD_ID_CLIENT_PAYBILLS, clientPlan.getId_client_paybills())
    				.addParameter(FIELD_UUID, clientPlan.getUuid())
    				.addParameter(FIELD_SUSCRIBE_DATE, clientPlan.getSuscribe_date())
    				.addParameter(FIELD_ID_PLAN, clientPlan.getId_plan())
    				.addParameter(FIELD_EXTERNAL_SUBSCRIPTION_KEY, clientPlan.getExternal_subscription_key())
    				.addParameter(FIELD_EXTERNAL_SUBSCRIPTION_PLATFORM, clientPlan.getExternal_subscription_platform())
    				.addParameter(FIELD_EXPIRATION_DATE, clientPlan.getExpiration_date()).executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	validateIdRecord(idRecord);
    	clientPlan.setId(idRecord);
    	
        return clientPlan;
    }
    /**
     * Update information of Client-Plan in database, canceling suscription to the plan.
     * @param clientPlan
     * @return
     * @throws RemoveApplicationException
     */
    public ClientPlan cancel(ClientPlan clientPlan) throws RemoveApplicationException {
    	getLog().info("Canceling suscription: " + clientPlan);
    	clientPlan.setCancellation_date(RemoveDateUtils.nowLocalDateTime());
    	clientPlan.setActive(false);
    	
    	String QUERY = buildUpdateQuery(TABLE, FIELD_ID, clientPlan.getId(), FIELD_CANCELLATION_DATE, FIELD_ACTIVE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_CANCELLATION_DATE, clientPlan.getCancellation_date())
    				.addParameter(FIELD_ACTIVE, clientPlan.isActive()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
        return clientPlan;
    }
    
	/**
	 * @param clientPlan
	 * @return
	 * @throws RemoveApplicationException
	 */
    public ClientPlan activate(ClientPlan clientPlan) throws RemoveApplicationException {
    	getLog().info("Activating suscription: " + clientPlan);
    	clientPlan.setActive(true);
    	
    	String QUERY = buildUpdateQuery(TABLE, FIELD_ID, clientPlan.getId(), FIELD_ACTIVE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_ACTIVE, clientPlan.isActive()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
        return clientPlan;
    }
    
	/**
	 * @param clientPlan
	 * @throws RemoveApplicationException
	 */
    public void updateExpirationDate(ClientPlan clientPlan) throws RemoveApplicationException {
    	String QUERY = buildUpdateQuery(TABLE, FIELD_ID, clientPlan.getId(), FIELD_EXPIRATION_DATE);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_EXPIRATION_DATE, clientPlan.getExpiration_date()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
    /**
     * Refresh amount of credits used by the plan in database.
     * @param clientPlan
     * @return
     * @throws RemoveApplicationException
     */
    public ClientPlan refreshCredits(ClientPlan clientPlan) throws RemoveApplicationException {
    	String QUERY = buildUpdateQuery(TABLE, FIELD_ID, clientPlan.getId(), FIELD_CREDITS_USED);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_CREDITS_USED, clientPlan.getCredits_used()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
        return clientPlan;
    }
    
    
    /**
     * Refresh amount of credits used by the plan in database.
     * @param clientPlan
     * @return
     * @throws RemoveApplicationException
     */
    public ClientPlan refreshDeindexUsed(ClientPlan clientPlan) throws RemoveApplicationException {
    	String QUERY = buildUpdateQuery(TABLE, FIELD_ID, clientPlan.getId(), FIELD_DEINDEX_USED);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_DEINDEX_USED, clientPlan.getDeindex_used()).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch (Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
        return clientPlan;
    }

    /**
     * Count how many active clients are suscribed to a plan.
     * @param idPlan
     * @return
     * @throws RemoveApplicationException
     */
	public Long countClientsByPlans(Long idPlan) throws RemoveApplicationException {
		String QUERY = "SELECT COUNT(" + FIELD_ID_CLIENT + ") FROM " + TABLE + " WHERE " + FIELD_ID_PLAN + " = " + idPlan 
				+ " AND " + FIELD_ACTIVE + " = true";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetchFirst(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return 0l;
	}

	/**
	 * @param idClientPlan
	 * @param newPlan
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientPlan savePlanChange(Long idClientPlan, ClientPlan newPlan) throws RemoveApplicationException {
		cancel(getById(idClientPlan));

		return save(newPlan);
	}

	/**
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<ClientPlan> listActives() throws RemoveApplicationException {
		List<ClientPlan> list = (List<ClientPlan>) listByField(TABLE, FIELD_ACTIVE, true, ClientPlan.class);
		
		if (list != null) {
			list.forEach(clientPlan -> {
				try {
					clientPlan.setDetail(PlanDao.init().get(clientPlan.getId_plan()));
					clientPlan.setPaybill(ClientPaybillDao.init().getById(clientPlan.getId_client_paybills()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading paybill: " + clientPlan, e);
				}
			});
		}
		
		return list;
	}

	/**
	 * @param initDate
	 * @param endDate
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ClientPlan> expiresBetween(LocalDate initDate, LocalDate endDate) throws RemoveApplicationException {
		String QUERY = "SELECT * FROM " + TABLE + " WHERE " + FIELD_EXPIRATION_DATE + " >= :INIT_DATE AND " + FIELD_EXPIRATION_DATE + " <= :END_DATE AND " + FIELD_ACTIVE + " = true";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).addParameter("INIT_DATE", initDate).addParameter("END_DATE", endDate).executeAndFetch(ClientPlan.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return new ArrayList<ClientPlan>();
	}

	/**
	 * @param referenceDate
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Long countSubscriptionsAtMonth(LocalDate referenceDate) throws RemoveApplicationException {
		LocalDate initDate = referenceDate.withDayOfMonth(1);
		LocalDate endDate = initDate.plusMonths(1);
		
		String QUERY = "SELECT COUNT(" + FIELD_ID + ") FROM " + TABLE + " WHERE " + FIELD_SUSCRIBE_DATE + " >= :INIT_DATE AND " + FIELD_SUSCRIBE_DATE + " <= :END_DATE";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).addParameter("INIT_DATE", initDate).addParameter("END_DATE", endDate).executeAndFetchFirst(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return 0l;
	}

	/**
	 * @param idPlan
	 * @return
	 * @throws RemoveApplicationException
	 */
	public long countActives(Long idPlan) throws RemoveApplicationException {
		String QUERY = "SELECT COUNT(" + FIELD_ID + ") FROM " + TABLE + " WHERE " + FIELD_ID_PLAN + " = :" + FIELD_ID_PLAN + " AND " + FIELD_ACTIVE + " = true";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).addParameter(FIELD_ID_PLAN, idPlan).executeAndFetchFirst(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return 0l;
	}

	/**
	 * @param id_client
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientPlan getLastSuscriptionByClient(Long id_client) throws RemoveApplicationException {
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_CLIENT, id_client) + " ORDER BY " + FIELD_SUSCRIBE_DATE + " DESC ";
		ClientPlan clientPlan = null;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			clientPlan = conn.createQuery(QUERY).executeAndFetchFirst(ClientPlan.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (clientPlan != null && clientPlan.getId_client_paybills() != null) {
			clientPlan.setPaybill(ClientPaybillDao.init().getById(clientPlan.getId_client_paybills()));
		}
		
		return clientPlan;
	}
}
