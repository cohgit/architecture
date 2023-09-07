package cl.atianza.remove.daos.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EPaymentMethods;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.EmailHelper;
import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.admin.PlanClientSuggestion;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Plans DAOs methods (Will be changed in V2)
 * @author pilin
 */
@Deprecated
public class PlanDao extends RemoveDao {
	private static final String TABLE_NAME = "plans";
	
	private static final String FIELD_NAME = "name";
	private static final String FIELD_DESCRIPTION = "description";
	private static final String FIELD_DURATION_MONTHS = "duration_months";
	private static final String FIELD_COST = "cost";
	private static final String FIELD_AUTOMATIC_RENEWAL = "automatic_renewal";
	private static final String FIELD_LIMIT_CREDITS = "limit_credits";
	private static final String FIELD_TOTAL_MONITOR_LICENSES = "total_monitor_licenses";
	private static final String FIELD_TOTAL_TRANSFORMA_LICENSES = "total_transforma_licenses";
	private static final String FIELD_MAX_KEYWORDS = "max_keywords";
	private static final String FIELD_MAX_COUNTRIES = "max_countries";
	private static final String FIELD_MAX_SEARCH_PAGE = "max_search_page";
	private static final String FIELD_SECTIONS_TO_SEARCH = "sections_to_search";
	private static final String FIELD_MAX_URL_TO_REMOVE = "max_url_to_remove";
	private static final String FIELD_MAX_URL_TO_IMPULSE = "max_url_to_impulse";
	private static final String FIELD_MAX_URL_TO_DEINDEXATE = "max_url_to_deindexate";
	private static final String FIELD_TARGET_PAGE = "target_page";
	private static final String FIELD_CUSTOMIZED = "customized";
	private static final String FIELD_QUOTE_REQUESTED = "quote_requested";
	private static final String FIELD_QUOTE_APPROVED = "quote_approved";
	private static final String FIELD_ACCESS_SCANNER = "access_scanner";
	private static final String FIELD_ACCESS_MONITOR = "access_monitor";
	private static final String FIELD_ACCESS_TRANSFORMA = "access_transforma";
	private static final String FIELD_ACCESS_DEINDEXATION = "access_deindexation";
	private static final String FIELD_ACTIVE = "active";
	
	/**
	 * 
	 * @throws RemoveApplicationException
	 */
	public PlanDao() throws RemoveApplicationException {
		super(LogManager.getLogger(PlanDao.class), TABLE_NAME);
	}
	
	/**
	 * 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public static PlanDao init() throws RemoveApplicationException {
		return new PlanDao();
	}	
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Plan get(long id) throws RemoveApplicationException {
		Plan plan = (Plan) getById(TABLE, id, Plan.class);
		
		if (plan != null) {
			plan.splitSearchType();
		}
		
		return plan;
	}
	
	/**
	 * 
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<Plan> listAll() throws RemoveApplicationException {
		return (List<Plan>) listAllOrderByField(TABLE, Plan.class, FIELD_ID, "DESC");
	}
	
	/**
	 * 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Plan> listActiveNotCostumized() throws RemoveApplicationException {
		List<Plan> list = null;
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ACTIVE, FIELD_CUSTOMIZED) + " ORDER BY " + FIELD_ID + " ASC";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY)
					.addParameter(FIELD_ACTIVE, true)
					.addParameter(FIELD_CUSTOMIZED, false)
					.executeAndFetch(Plan.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (list != null) {
			list.forEach(plan -> {
				plan.splitSearchType();
				try {
					plan.setStripeKey(PlanExternalKeyDao.init().getByPlan(plan.getId(), EPaymentMethods.STRIPE));
				} catch (RemoveApplicationException e) {
					getLog().error("Error finding External key for:" + plan, e);
				}
			});
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param idPlan
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Plan getForCheckout(Long idPlan) throws RemoveApplicationException {
		Plan plan = get(idPlan);
		
		if (plan != null && plan.isActive()) {
			plan.splitSearchType();
			try {
				plan.setStripeKey(PlanExternalKeyDao.init().getByPlan(plan.getId(), EPaymentMethods.STRIPE));
			} catch (RemoveApplicationException e) {
				getLog().error("Error finding External key for:" + plan, e);
			}
		} else {
			return null;
		}
		
		return plan;
	}

	/**
	 * 
	 * @param plan
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Plan save(Plan plan) throws RemoveApplicationException {
		plan.joinSearchList();
//		plan.setActive(false);
		String QUERY = buildInsertQuery(TABLE, FIELD_DESCRIPTION, FIELD_NAME, FIELD_COST, FIELD_LIMIT_CREDITS, FIELD_ACTIVE,
				FIELD_DURATION_MONTHS, FIELD_AUTOMATIC_RENEWAL, FIELD_MAX_KEYWORDS, FIELD_MAX_COUNTRIES, FIELD_MAX_SEARCH_PAGE,
				FIELD_SECTIONS_TO_SEARCH, FIELD_MAX_URL_TO_REMOVE, FIELD_MAX_URL_TO_IMPULSE, FIELD_MAX_URL_TO_DEINDEXATE, 
				FIELD_TARGET_PAGE, FIELD_CUSTOMIZED, FIELD_QUOTE_REQUESTED, FIELD_ACCESS_SCANNER, FIELD_ACCESS_MONITOR, FIELD_ACCESS_TRANSFORMA, 
				FIELD_ACCESS_DEINDEXATION, FIELD_TOTAL_MONITOR_LICENSES, FIELD_TOTAL_TRANSFORMA_LICENSES);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_DESCRIPTION, plan.getDescription())
    				.addParameter(FIELD_NAME, plan.getName())
    				.addParameter(FIELD_COST, plan.getCost())
    				.addParameter(FIELD_LIMIT_CREDITS, plan.getLimit_credits())
    				.addParameter(FIELD_ACTIVE, plan.isActive())
    				.addParameter(FIELD_DURATION_MONTHS, plan.getDuration_months())
    				.addParameter(FIELD_AUTOMATIC_RENEWAL, plan.isAutomatic_renewal())
    				.addParameter(FIELD_MAX_KEYWORDS, plan.getMax_keywords())
    				.addParameter(FIELD_MAX_COUNTRIES, plan.getMax_countries())
    				.addParameter(FIELD_MAX_SEARCH_PAGE, plan.getMax_search_page())
    				.addParameter(FIELD_SECTIONS_TO_SEARCH, plan.getSections_to_search())
    				.addParameter(FIELD_MAX_URL_TO_REMOVE, plan.getMax_url_to_remove())
    				.addParameter(FIELD_MAX_URL_TO_IMPULSE, plan.getMax_url_to_impulse())
    				.addParameter(FIELD_MAX_URL_TO_DEINDEXATE, plan.getMax_url_to_deindexate())
    				.addParameter(FIELD_TARGET_PAGE, plan.getTarget_page())
    				.addParameter(FIELD_CUSTOMIZED, plan.isCustomized())
    				.addParameter(FIELD_QUOTE_REQUESTED, plan.getQuote_requested())
    				.addParameter(FIELD_ACCESS_SCANNER, plan.isAccess_scanner())
    				.addParameter(FIELD_ACCESS_MONITOR, plan.isAccess_monitor())
    				.addParameter(FIELD_ACCESS_TRANSFORMA, plan.isAccess_transforma())
    				.addParameter(FIELD_ACCESS_DEINDEXATION, plan.isAccess_deindexation())
    				.addParameter(FIELD_TOTAL_MONITOR_LICENSES, plan.getTotal_monitor_licenses())
    				.addParameter(FIELD_TOTAL_TRANSFORMA_LICENSES, plan.getTotal_transforma_licenses())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	plan.setId(idRecord);
    	
    	refreshSuggestions(plan);
    	
    	return plan;
	}
	
	/**
	 * 
	 * @param plan
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Plan update(Plan plan) throws RemoveApplicationException {
		plan.joinSearchList();
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, plan.getId(), FIELD_DESCRIPTION, FIELD_NAME, FIELD_COST, FIELD_LIMIT_CREDITS, FIELD_ACTIVE,
				FIELD_DURATION_MONTHS, FIELD_AUTOMATIC_RENEWAL, FIELD_MAX_KEYWORDS, FIELD_MAX_COUNTRIES, FIELD_MAX_SEARCH_PAGE,
				FIELD_SECTIONS_TO_SEARCH, FIELD_MAX_URL_TO_REMOVE, FIELD_MAX_URL_TO_IMPULSE, FIELD_MAX_URL_TO_DEINDEXATE, 
				FIELD_TARGET_PAGE, FIELD_CUSTOMIZED, FIELD_ACCESS_SCANNER, FIELD_ACCESS_MONITOR, FIELD_ACCESS_TRANSFORMA, FIELD_ACCESS_DEINDEXATION,
				FIELD_TOTAL_MONITOR_LICENSES, FIELD_TOTAL_TRANSFORMA_LICENSES);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY, true)
    				.addParameter(FIELD_DESCRIPTION, plan.getDescription())
    				.addParameter(FIELD_NAME, plan.getName())
    				.addParameter(FIELD_COST, plan.getCost())
    				.addParameter(FIELD_LIMIT_CREDITS, plan.getLimit_credits())
    				.addParameter(FIELD_ACTIVE, plan.isActive())
    				.addParameter(FIELD_DURATION_MONTHS, plan.getDuration_months())
    				.addParameter(FIELD_AUTOMATIC_RENEWAL, plan.isAutomatic_renewal())
    				.addParameter(FIELD_MAX_KEYWORDS, plan.getMax_keywords())
    				.addParameter(FIELD_MAX_COUNTRIES, plan.getMax_countries())
    				.addParameter(FIELD_MAX_SEARCH_PAGE, plan.getMax_search_page())
    				.addParameter(FIELD_SECTIONS_TO_SEARCH, plan.getSections_to_search())
    				.addParameter(FIELD_MAX_URL_TO_REMOVE, plan.getMax_url_to_remove())
    				.addParameter(FIELD_MAX_URL_TO_IMPULSE, plan.getMax_url_to_impulse())
    				.addParameter(FIELD_MAX_URL_TO_DEINDEXATE, plan.getMax_url_to_deindexate())
    				.addParameter(FIELD_TARGET_PAGE, plan.getTarget_page())
    				.addParameter(FIELD_CUSTOMIZED, plan.isCustomized())
    				.addParameter(FIELD_ACCESS_SCANNER, plan.isAccess_scanner())
    				.addParameter(FIELD_ACCESS_MONITOR, plan.isAccess_monitor())
    				.addParameter(FIELD_ACCESS_TRANSFORMA, plan.isAccess_transforma())
    				.addParameter(FIELD_ACCESS_DEINDEXATION, plan.isAccess_deindexation())
    				.addParameter(FIELD_TOTAL_MONITOR_LICENSES, plan.getTotal_monitor_licenses())
    				.addParameter(FIELD_TOTAL_TRANSFORMA_LICENSES, plan.getTotal_transforma_licenses())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	refreshSuggestions(plan);
    	
    	return plan;
	}

	/**
	 * 
	 * @param plan
	 * @throws RemoveApplicationException
	 */
	private void refreshSuggestions(Plan plan) {
		if (plan.getClientSuggestions() != null) {
			plan.getClientSuggestions().forEach(sugg -> {
				try {
					sugg.setId_plan(plan.getId());
					if (sugg.esNuevo()) {
						if (!sugg.isMarkToDelete()) {
							PlanClientSuggestionDao.init().save(sugg);	
							EmailHelper.init().sendSuggestionPlanMail(sugg);
						}
					} else {
						if (sugg.isMarkToDelete()) {
							PlanClientSuggestionDao.init().delete(sugg);
						}
					}
				} catch (RemoveApplicationException e) {
					getLog().error("Error upserting suggestion:" + sugg, e);
				}	
			});
		}
	}

	/**
	 * 
	 * @param plan
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Plan switchActive(Plan plan) throws RemoveApplicationException {
		plan.setActive(!plan.isActive());
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, plan.getId(), FIELD_ACTIVE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY, true)
    				.addParameter(FIELD_ACTIVE, plan.isActive())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return plan;
	}
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Plan switchActive(Long id) throws RemoveApplicationException {
		return switchActive(get(id));
	}
	
	/**
	 * 
	 * @param clientPlan
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Plan> listAvailablesForClient(ClientPlan clientPlan) throws RemoveApplicationException {
		List<Plan> listActives = listActiveNotCostumized();
		List<Plan> listPlans = new ArrayList<Plan>();

		if (listActives != null) {
			listActives.forEach(plan -> {
				if (plan.getId().longValue() != clientPlan.getId_plan().longValue()) {
					listPlans.add(plan);
				}
			});
		}

		return listPlans;
	}
	
	/**
	 * 
	 * @param clientPlan
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Plan> listCustomizedForClient(ClientPlan clientPlan) throws RemoveApplicationException {
		List<PlanClientSuggestion> lstSuggestions = PlanClientSuggestionDao.init().listForClientSuggestions(clientPlan.getId_client());
		
		List<Plan> listPlans = new ArrayList<Plan>();
		
		if (lstSuggestions != null) {
			lstSuggestions.forEach(sug -> {
				try {
					Plan planSug = get(sug.getId_plan());
					
					if (planSug.isActive() && planSug.getId().longValue() != clientPlan.getId_plan().longValue()) {
						planSug.setStripeKey(PlanExternalKeyDao.init().getByPlan(planSug.getId(), EPaymentMethods.STRIPE));
						listPlans.add(planSug);
					}
				} catch (RemoveApplicationException e) {
					getLog().error("Error getting plan:" + sug, e);
				}
			});
		}
		
		return listPlans;
	}
	
	/**
	 * 
	 * @throws RemoveApplicationException
	 */
	public void createDefaultPlans() throws RemoveApplicationException {
//		PaymentStripeClient.init().deleteAllProducts();
		
		try {
			Plan demo = new Plan();
			demo.setActive(true);
			demo.setCost(0f);
			demo.setName("Demo");
			demo.setDescription("Full Access Demo");
			demo.setDuration_months(1);
			demo.setAccess_scanner(true);
			demo.setAccess_monitor(true);
			demo.setAccess_transforma(true);
			demo.setAutomatic_renewal(true);
			demo.setCustomized(false);
			
			demo.setLimit_credits(100l);
			demo.setMax_keywords(3);
			demo.setMax_countries(3);
			demo.setMax_search_page(3);
			demo.setMax_url_to_impulse(3);
			demo.setMax_url_to_remove(3);
			demo.setSections_to_search("web,news,images");
			demo.setMax_url_to_deindexate(3);
			demo.setTarget_page(2);
			demo.setTotal_monitor_licenses(5);
			demo.setTotal_transforma_licenses(5);
			demo = save(demo);
//			PaymentStripeClient.init().createProduct(scanner1);
			
			Plan scanner1 = new Plan();
			scanner1.setActive(true);
			scanner1.setCost(99f);
			scanner1.setName("PLAN DETECTA & ALERTA PUNTUAL Lite");
			scanner1.setDescription("Lite");
			scanner1.setDuration_months(1);
			scanner1.setAccess_scanner(true);
			scanner1.setAccess_monitor(false);
			scanner1.setAccess_transforma(false);
			scanner1.setAutomatic_renewal(true);
			scanner1.setCustomized(false);
			
			scanner1.setLimit_credits(100l);
			scanner1.setMax_keywords(1);
			scanner1.setMax_countries(1);
			scanner1.setMax_search_page(2);
			scanner1.setMax_url_to_impulse(0);
			scanner1.setMax_url_to_remove(0);
			scanner1.setSections_to_search("web");
			scanner1 = save(scanner1);
//			PaymentStripeClient.init().createProduct(scanner1);
			
			Plan scanner2 = new Plan();
			scanner2.setActive(true);
			scanner2.setCost(150f);
			scanner2.setName("PLAN DETECTA & ALERTA PUNTUAL Estandar");
			scanner2.setDescription("Estandar");
			scanner2.setDuration_months(1);
			scanner2.setAccess_scanner(true);
			scanner2.setAccess_monitor(false);
			scanner2.setAccess_transforma(false);
			scanner2.setAutomatic_renewal(true);
			scanner2.setCustomized(false);
			
			scanner2.setLimit_credits(100l);
			scanner2.setMax_keywords(5);
			scanner2.setMax_countries(1);
			scanner2.setMax_search_page(2);
			scanner2.setMax_url_to_impulse(0);
			scanner2.setMax_url_to_remove(0);
			scanner2.setSections_to_search("web,news");
			scanner2 = save(scanner2);
//			PaymentStripeClient.init().createProduct(scanner2);
			
			Plan scanner3 = new Plan();
			scanner3.setActive(true);
			scanner3.setCost(150f);
			scanner3.setName("PLAN DETECTA & ALERTA PUNTUAL Premium");
			scanner3.setDescription("Premium");
			scanner3.setDuration_months(1);
			scanner3.setAccess_scanner(true);
			scanner3.setAccess_monitor(false);
			scanner3.setAccess_transforma(false);
			scanner3.setAutomatic_renewal(true);
			scanner3.setCustomized(false);
			
			scanner3.setLimit_credits(190l);
			scanner3.setMax_keywords(10);
			scanner3.setMax_countries(5);
			scanner3.setMax_search_page(10);
			scanner3.setMax_url_to_impulse(0);
			scanner3.setMax_url_to_remove(0);
			scanner3.setSections_to_search("web,news,images");
			scanner3 = save(scanner3);
//			PaymentStripeClient.init().createProduct(scanner3);
			
			Plan recurrentScanner1 = new Plan();
			recurrentScanner1.setActive(true);
			recurrentScanner1.setCost(83f);
			recurrentScanner1.setName("PLAN DETECTA & ALERTA RECURRENTE Lite");
			recurrentScanner1.setDescription("Lite");
			recurrentScanner1.setDuration_months(1);
			recurrentScanner1.setAccess_monitor(true);
			recurrentScanner1.setAccess_scanner(false);
			recurrentScanner1.setAccess_transforma(false);
			recurrentScanner1.setAutomatic_renewal(true);
			recurrentScanner1.setCustomized(false);
			recurrentScanner1.setMax_countries(1);
			recurrentScanner1.setMax_keywords(1);
			recurrentScanner1.setMax_search_page(2);
			recurrentScanner1.setMax_url_to_impulse(0);
			recurrentScanner1.setMax_url_to_remove(0);
			recurrentScanner1.setSections_to_search("web");
			recurrentScanner1.setTotal_monitor_licenses(1);
			recurrentScanner1 = save(recurrentScanner1);
//			PaymentStripeClient.init().createProduct(recurrentScanner1);
			
			Plan recurrentScanner2 = new Plan();
			recurrentScanner2.setActive(true);
			recurrentScanner2.setCost(120f);
			recurrentScanner2.setName("PLAN DETECTA & ALERTA RECURRENTE Estandar");
			recurrentScanner2.setDescription("Estandar");
			recurrentScanner2.setDuration_months(1);
			recurrentScanner2.setAccess_monitor(true);
			recurrentScanner2.setAccess_scanner(false);
			recurrentScanner2.setAccess_transforma(false);
			recurrentScanner2.setAutomatic_renewal(true);
			recurrentScanner2.setCustomized(false);
			recurrentScanner2.setMax_countries(1);
			recurrentScanner2.setMax_keywords(5);
			recurrentScanner2.setMax_search_page(2);
			recurrentScanner2.setMax_url_to_impulse(0);
			recurrentScanner2.setMax_url_to_remove(0);
			recurrentScanner2.setSections_to_search("web,news");
			recurrentScanner2.setTotal_monitor_licenses(1);
			recurrentScanner2 = save(recurrentScanner2);
//			PaymentStripeClient.init().createProduct(recurrentScanner2);
			
			Plan recurrentScanner3 = new Plan();
			recurrentScanner3.setActive(true);
			recurrentScanner3.setCost(175f);
			recurrentScanner3.setName("PLAN DETECTA & ALERTA RECURRENTE Premium");
			recurrentScanner3.setDescription("Premium");
			recurrentScanner3.setDuration_months(1);
			recurrentScanner3.setAccess_monitor(true);
			recurrentScanner3.setAccess_scanner(false);
			recurrentScanner3.setAccess_transforma(false);
			recurrentScanner3.setAutomatic_renewal(true);
			recurrentScanner3.setCustomized(false);
			recurrentScanner3.setMax_countries(5);
			recurrentScanner3.setMax_keywords(10);
			recurrentScanner3.setMax_search_page(10);
			recurrentScanner3.setMax_url_to_impulse(0);
			recurrentScanner3.setMax_url_to_remove(0);
			recurrentScanner3.setSections_to_search("web,news,images");
			recurrentScanner3.setTotal_monitor_licenses(1);
			recurrentScanner3 = save(recurrentScanner3);
//			PaymentStripeClient.init().createProduct(recurrentScanner2);
			
			Plan transforma1 = new Plan();
			transforma1.setActive(true);
			transforma1.setCost(900f);
			transforma1.setName("PLAN DE TRANSFORMA Lite");
			transforma1.setDescription("Lite");
			transforma1.setDuration_months(1);
			transforma1.setAccess_monitor(false);
			transforma1.setAccess_scanner(false);
			transforma1.setAccess_transforma(true);
			transforma1.setAutomatic_renewal(true);
			transforma1.setCustomized(false);
			transforma1.setMax_countries(1);
			transforma1.setMax_keywords(1);
			transforma1.setMax_search_page(2);
			transforma1.setTarget_page(1);
			transforma1.setMax_url_to_impulse(1);
			transforma1.setMax_url_to_remove(1);
			transforma1.setSections_to_search("web");
			transforma1.setTotal_transforma_licenses(1);
			transforma1 = save(transforma1);
//			PaymentStripeClient.init().createProduct(transforma1);
			
			Plan transforma2 = new Plan();
			transforma2.setActive(true);
			transforma2.setCost(1800f);
			transforma2.setName("PLAN DE TRANSFORMA Estandar");
			transforma2.setDescription("Estandar");
			transforma2.setDuration_months(1);
			transforma2.setAccess_monitor(false);
			transforma2.setAccess_scanner(false);
			transforma2.setAccess_transforma(true);
			transforma2.setAutomatic_renewal(true);
			transforma2.setCustomized(false);
			transforma2.setMax_countries(1);
			transforma2.setMax_keywords(5);
			transforma2.setMax_search_page(2);
			transforma2.setTarget_page(1);
			transforma2.setMax_url_to_impulse(2);
			transforma2.setMax_url_to_remove(5);
			transforma2.setSections_to_search("web,news");
			transforma2.setTotal_transforma_licenses(1);
			transforma2 = save(transforma2);
//			PaymentStripeClient.init().createProduct(transforma2);
			
			Plan transforma3 = new Plan();
			transforma3.setActive(true);
			transforma3.setCost(1800f);
			transforma3.setName("PLAN DE TRANSFORMA Premium");
			transforma3.setDescription("Premium");
			transforma3.setDuration_months(1);
			transforma3.setAccess_monitor(false);
			transforma3.setAccess_scanner(false);
			transforma3.setAccess_transforma(true);
			transforma3.setAutomatic_renewal(true);
			transforma3.setCustomized(false);
			transforma3.setMax_countries(5);
			transforma3.setMax_keywords(10);
			transforma3.setMax_search_page(2);
			transforma3.setTarget_page(1);
			transforma3.setMax_url_to_impulse(3);
			transforma3.setMax_url_to_remove(10);
			transforma3.setSections_to_search("web,news,images");
			transforma3.setTotal_transforma_licenses(1);
			transforma3 = save(transforma3);
//			PaymentStripeClient.init().createProduct(transforma3);
			
			Plan deindexation1 = new Plan();
			deindexation1.setActive(true);
			deindexation1.setCost(150f);
			deindexation1.setName("PLAN DE ELIMINACIÓN / DESINDEXACIÓN Estandar");
			deindexation1.setDescription("Estandar");
			deindexation1.setDuration_months(1);
			deindexation1.setAccess_monitor(false);
			deindexation1.setAccess_scanner(false);
			deindexation1.setAccess_transforma(false);
			deindexation1.setAccess_deindexation(true);
			deindexation1.setAutomatic_renewal(false);
			deindexation1.setCustomized(false);
			deindexation1.setMax_keywords(1);
			deindexation1.setMax_url_to_deindexate(1);
			deindexation1 = save(deindexation1);
//			PaymentStripeClient.init().createProduct(transforma3);
			
		} catch (Exception ex) {
			
		}
	}

	/**
	 * @param planQuoteRequest
	 * @param clientIdentification
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Plan saveAsQuoteRequest(Plan planQuoteRequest, String clientIdentification) throws RemoveApplicationException {
		planQuoteRequest.setCustomized(true);
		planQuoteRequest.setQuote_requested(true);
		planQuoteRequest.setName("Request Quote For: " + clientIdentification);
		planQuoteRequest.setDescription("Request Quote For: " + clientIdentification);
		planQuoteRequest.setActive(false);
		
		planQuoteRequest.setAccess_scanner(planQuoteRequest.getLimit_credits() != null && planQuoteRequest.getLimit_credits().longValue() > 0);
		planQuoteRequest.setAccess_monitor(planQuoteRequest.getTotal_monitor_licenses() != null && planQuoteRequest.getTotal_monitor_licenses().longValue() > 0);
		planQuoteRequest.setAccess_transforma(planQuoteRequest.getTotal_transforma_licenses() != null && planQuoteRequest.getTotal_transforma_licenses().longValue() > 0);
		planQuoteRequest.setAccess_deindexation(planQuoteRequest.getMax_url_to_deindexate() != null && planQuoteRequest.getMax_url_to_deindexate().longValue() > 0);
		
		return save(planQuoteRequest);
	}
}
