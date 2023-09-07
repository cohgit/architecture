package cl.atianza.remove.daos.admin;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.EmailHelper;
import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.admin.PlanClientSuggestion;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Client's plans suggestion DAO methods.
 * @author pilin
 *
 */
public class PlanClientSuggestionDao extends RemoveDao {
	private static final String TABLE_NAME = "plans_clients_suggestions";
	
	private static final String FIELD_ID_PLAN = "id_plan";
	private static final String FIELD_ID_CLIENT = "id_client";
	private static final String FIELD_UUID = "uuid";
	private static final String FIELD_CLIENT_NAME = "client_name";
	private static final String FIELD_CLIENT_EMAIL = "client_email";
	private static final String FIELD_EMAIL_SENT = "email_sent";
	private static final String FIELD_CLIENT_ALREADY_REGISTRED = "client_already_registred";
	private static final String FIELD_CLIENT_ATTEND_SUGGESTION = "client_attend_suggestion";
	
	/**
	 * 
	 * @throws RemoveApplicationException
	 */
	public PlanClientSuggestionDao() throws RemoveApplicationException {
		super(LogManager.getLogger(PlanClientSuggestionDao.class), TABLE_NAME);
	}
	
	/**
	 * 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public static PlanClientSuggestionDao init() throws RemoveApplicationException {
		return new PlanClientSuggestionDao();
	}	

	/**
	 * 
	 * @param plan
	 * @throws RemoveApplicationException
	 */
	public void upsert(Plan plan) throws RemoveApplicationException {
		if (plan.getClientSuggestions() != null && !plan.getClientSuggestions().isEmpty()) {
			plan.getClientSuggestions().forEach(suggestion -> {
				try {
					suggestion.setId_plan(plan.getId());
					suggestion.setEmail_sent(false);
					suggestion.setClient_attend_suggestion(false);
					EmailHelper.init().sendSuggestionPlanMail(save(suggestion));
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving plan suggestion:" + suggestion, e);
				}
			});
		}
	}
	
	/**
	 * 
	 * @param suggestion
	 * @return
	 * @throws RemoveApplicationException
	 */
	public PlanClientSuggestion save(PlanClientSuggestion suggestion) throws RemoveApplicationException {
		Client client = ClientDao.init().getByEmail(suggestion.getClient_email());
		if (client != null && !client.esNuevo()) {
			suggestion.setId_client(client.getId());
		}
		
		String QUERY = buildInsertQuery(TABLE, FIELD_UUID, FIELD_ID_PLAN, FIELD_ID_CLIENT, FIELD_CLIENT_NAME, FIELD_CLIENT_EMAIL, FIELD_EMAIL_SENT,
				FIELD_CLIENT_ALREADY_REGISTRED, FIELD_CLIENT_ATTEND_SUGGESTION);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_UUID, suggestion.getUuid())
    				.addParameter(FIELD_ID_PLAN, suggestion.getId_plan())
    				.addParameter(FIELD_ID_CLIENT, suggestion.getId_client())
    				.addParameter(FIELD_CLIENT_NAME, suggestion.getClient_name())
    				.addParameter(FIELD_CLIENT_EMAIL, suggestion.getClient_email())
    				.addParameter(FIELD_EMAIL_SENT, suggestion.isEmail_sent())
    				.addParameter(FIELD_CLIENT_ALREADY_REGISTRED, suggestion.isClient_already_registred())
    				.addParameter(FIELD_CLIENT_ATTEND_SUGGESTION, suggestion.isClient_attend_suggestion())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	suggestion.setId(idRecord);
    	suggestion.setPlan(PlanDao.init().get(suggestion.getId_plan()));
    	
    	return suggestion;
	}
	
	/**
	 * 
	 * @param idClient
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<PlanClientSuggestion> listForClientSuggestions(Long idClient) throws RemoveApplicationException {
		List<PlanClientSuggestion> list = null;
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_CLIENT, FIELD_CLIENT_ATTEND_SUGGESTION) + " ORDER BY " + FIELD_ID + " ASC";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY)
					.addParameter(FIELD_ID_CLIENT, idClient)
					.addParameter(FIELD_CLIENT_ATTEND_SUGGESTION, false)
					.executeAndFetch(PlanClientSuggestion.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return list;
	}

	/**
	 * 
	 * @param uuid
	 * @return
	 * @throws RemoveApplicationException
	 */
	public PlanClientSuggestion getByUuid(String uuid) throws RemoveApplicationException {
		return (PlanClientSuggestion) getByField(TABLE, FIELD_UUID, uuid, PlanClientSuggestion.class);
	}

	/**
	 * 
	 * @param idPlan
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<PlanClientSuggestion> listForPlan(Long idPlan) throws RemoveApplicationException {
		List<PlanClientSuggestion> list = null;
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_PLAN) + " ORDER BY " + FIELD_ID + " ASC";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY)
					.addParameter(FIELD_ID_PLAN, idPlan)
					.executeAndFetch(PlanClientSuggestion.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return list;
	}

	/**
	 * 
	 * @param uuid
	 * 
	 * @throws RemoveApplicationException
	 */
	public void updateAttempted(String uuid) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_UUID, uuid, FIELD_CLIENT_ATTEND_SUGGESTION);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_CLIENT_ATTEND_SUGGESTION, true)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	
	/**
	 * 
	 * @param uuid
	 * 
	 * @throws RemoveApplicationException
	 */
	public void updateRegistred(String uuid) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_UUID, uuid, FIELD_CLIENT_ALREADY_REGISTRED);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_CLIENT_ALREADY_REGISTRED, true)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	/**
	 * 
	 * @param suggestion
	 * 
	 * @throws RemoveApplicationException
	 */
	public void update(PlanClientSuggestion suggestion) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, suggestion.getId(), FIELD_CLIENT_NAME, FIELD_CLIENT_EMAIL);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_CLIENT_NAME, suggestion.getClient_name())
    				.addParameter(FIELD_CLIENT_EMAIL, suggestion.getClient_email())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	/**
	 * 
	 * @param sugg
	 * 
	 * @throws RemoveApplicationException
	 */
	public void delete(PlanClientSuggestion sugg) throws RemoveApplicationException {
		String QUERY = buildDeleteQuery(TABLE, FIELD_ID, sugg.getId());
		
		try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
}
