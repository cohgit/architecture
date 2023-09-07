package cl.atianza.remove.daos.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EDeindexationStatus;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ConditionEvaluatorHelper;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Deindexation;
import cl.atianza.remove.models.commons.DeindexationInputValue;
import cl.atianza.remove.models.commons.DynamicForm;
import cl.atianza.remove.models.commons.DynamicFormConditionResult;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Deindexation DAOs methods.
 * @author pilin
 *
 */
@Deprecated
public class DeindexationDao extends RemoveDao {
	public static final String TABLE_NAME = "deindexations";
	
	private static final String FIELD_ID_OWNER = "id_owner";
	public static final String FIELD_ID_CLIENT_PLAN = "id_client_plan";
	private static final String FIELD_ID_FORM = "id_form";
	private static final String FIELD_UUID = "uuid";
	
	private static final String FIELD_PERSON_NAME = "person_name";
	private static final String FIELD_PERSON_LASTNAME = "person_lastname";
	private static final String FIELD_PERSON_INSTITUTION = "person_institution";
	private static final String FIELD_PERSON_CHARGE = "person_charge";
	
	private static final String FIELD_URL_TO_DEINDEX_KEYWORDS = "url_to_deindex_keywords";

	private static final String FIELD_COMMENTS = "comments";
	private static final String FIELD_CREATION_DATE = "creation_date";
	private static final String FIELD_STATUS = "status";
	private static final String FIELD_TRACKING_CODE = "tracking_code";
	
	private static final String TABLE_NAME_REL_RESULT = "deindexations_results";
	private static final String FIELD_REL_ID_DEINDEXATION = "id_deindexation";
	private static final String FIELD_REL_ID_RESULT = "id_dynamic_result";
	
	public DeindexationDao() throws RemoveApplicationException {
		super(LogManager.getLogger(DeindexationDao.class), TABLE_NAME);
	}
	
	public static DeindexationDao init() throws RemoveApplicationException {
		return new DeindexationDao();
	}	
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Deindexation> listByOwner(long idOwner) throws RemoveApplicationException {
		List<Deindexation> list = listBasicByOwner(idOwner);

		if (list != null) {
			list.forEach(deindexation -> {
				try {
					deindexation.setEvidenceFiles(DeindexationFileEvidenceDao.init().listByDeindexation(deindexation.getId()));
					deindexation.setHistoric(DeindexationHistoricDao.init().listByDeindexation(deindexation.getId()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading deindexation inner info: " + deindexation, e);
				}
			});
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Deindexation> listBasicByOwner(long idOwner) throws RemoveApplicationException {
		return  (List<Deindexation>) listByField(TABLE, FIELD_ID_OWNER, idOwner, Deindexation.class, FIELD_CREATION_DATE);
	}
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Deindexation> listByOwner(String uuid) throws RemoveApplicationException {
		Client client = ClientDao.init().getByUuid(uuid);
		
		if (client != null) {
			return listByOwner(client.getId());
		}
		
		return null;
	}

	/**
	 * Save Deindexation Object
	 * @param deindexation
	 * @param idOwner 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Deindexation save(Deindexation deindexation, Long idOwner, String profileUploader) throws RemoveApplicationException {
		deindexation.setUuid(UUID.randomUUID().toString());
		deindexation.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		deindexation.setStatus(EDeindexationStatus.CREATED.getTag());
		deindexation.setId_form(deindexation.getDynamicForm().getId());
		deindexation.setId_owner(idOwner);
		
		String QUERY = buildInsertQuery(TABLE, FIELD_UUID, FIELD_ID_OWNER, FIELD_ID_CLIENT_PLAN, FIELD_ID_FORM,
				FIELD_PERSON_NAME, FIELD_PERSON_LASTNAME, FIELD_PERSON_CHARGE, FIELD_PERSON_INSTITUTION,
				FIELD_URL_TO_DEINDEX_KEYWORDS,
				FIELD_COMMENTS, FIELD_CREATION_DATE, FIELD_STATUS);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_UUID, deindexation.getUuid())
    				.addParameter(FIELD_ID_OWNER, deindexation.getId_owner())
    				.addParameter(FIELD_ID_CLIENT_PLAN, deindexation.getId_client_plan())
    				.addParameter(FIELD_ID_FORM, deindexation.getId_form())
    				
    				.addParameter(FIELD_PERSON_CHARGE, deindexation.getPerson_charge())
    				.addParameter(FIELD_PERSON_INSTITUTION, deindexation.getPerson_institution())
    				.addParameter(FIELD_PERSON_LASTNAME, deindexation.getPerson_lastname())
    				.addParameter(FIELD_PERSON_NAME, deindexation.getPerson_name())
    				
    				.addParameter(FIELD_URL_TO_DEINDEX_KEYWORDS, deindexation.getUrl_to_deindex_keywords())
    				
    				.addParameter(FIELD_COMMENTS, deindexation.getComments())
    				
    				.addParameter(FIELD_CREATION_DATE, deindexation.getCreation_date())
    				.addParameter(FIELD_STATUS, deindexation.getStatus())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	deindexation.setId(idRecord);
    	innerUpserts(deindexation, idOwner, profileUploader);
    	
    	return deindexation;
	}
	
	public Deindexation update(Deindexation deindexation, Long idUploader, String profileUploader) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, deindexation.getId(), FIELD_ID_FORM,
				FIELD_PERSON_CHARGE, FIELD_PERSON_INSTITUTION, FIELD_PERSON_LASTNAME, FIELD_PERSON_NAME,
				FIELD_URL_TO_DEINDEX_KEYWORDS, 
				FIELD_COMMENTS);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_ID_FORM, deindexation.getId_form())
    				.addParameter(FIELD_PERSON_CHARGE, deindexation.getPerson_charge())
    				.addParameter(FIELD_PERSON_INSTITUTION, deindexation.getPerson_institution())
    				.addParameter(FIELD_PERSON_LASTNAME, deindexation.getPerson_lastname())
    				.addParameter(FIELD_PERSON_NAME, deindexation.getPerson_name())
    				
    				.addParameter(FIELD_URL_TO_DEINDEX_KEYWORDS, deindexation.getUrl_to_deindex_keywords())
    				
    				.addParameter(FIELD_COMMENTS, deindexation.getComments())
    				
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	innerUpserts(deindexation, idUploader, profileUploader);
    	
    	return deindexation;
	}
	
	private void innerUpserts(Deindexation deindexation, Long idUploader, String profileUploader) throws RemoveApplicationException {
		// FIX ME: Esto se moverá a otro servicio Upload/Download
		if (deindexation.getEvidenceFiles() != null) {
			deindexation.getEvidenceFiles().forEach(file -> {
				try {
					if (file.isMarkForDelete()) {
						DeindexationFileEvidenceDao.init().delete(file.getId());
					} else {
						file.setId_deindexation(deindexation.getId());
						file.setId_uploader(idUploader);
						file.setUploader_profile(profileUploader);
						DeindexationFileEvidenceDao.init().upsert(file);
					}
				} catch (RemoveApplicationException e) {
					getLog().error("Error upserting inner deindexation data: ", file);
				}
			});
		}
		if (deindexation.getUrls() != null) {
			deindexation.getUrls().forEach(url -> {
				url.setId_deindexation(deindexation.getId());
				try {
					DeindexationUrlDao.init().upsert(url);
				} catch (RemoveApplicationException e) {
					getLog().error("Error upserting url:" + url, e);
				}
			});
		}
		
		if (deindexation.getDynamicForm() != null) {
			DeindexationInputValueDao.init().delete(deindexation.getId());
			DeindexationInputValueDao.init().save(deindexation.getId(), deindexation.getDynamicForm());
		}
	}
	
	public void updateStatus(Long idDeindexation, String status) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, idDeindexation, FIELD_STATUS);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_STATUS, status)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	public void updateTrackingCode(Long idDeindexation, String trackingCode) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, idDeindexation, FIELD_TRACKING_CODE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_TRACKING_CODE, trackingCode)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public Deindexation getBasicById(Long id) throws RemoveApplicationException {
		return (Deindexation) getByField(TABLE, FIELD_ID, id, Deindexation.class);
	}

	public Deindexation buildTemplate(Long idOwner, String language) throws RemoveApplicationException {
		Deindexation deindexation = new Deindexation();
		
		deindexation.setId_owner(idOwner);
		deindexation.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		deindexation.setDynamicForm(DynamicFormDao.init().getTemplateActiveForClientUtility());
		
		if (language != null) {
			deindexation.fixLabelsAndDescriptions(language);
		}
		
		return deindexation;
	}

	public Deindexation getByUuid(String uuidDeindexation, String language) throws RemoveApplicationException {
		Deindexation deindexation = getBasicByUuid(uuidDeindexation);
		
		if (deindexation != null) {
			deindexation.setEvidenceFiles(DeindexationFileEvidenceDao.init().listByDeindexation(deindexation.getId()));
			deindexation.setUrls(DeindexationUrlDao.init().listByDeindexation(deindexation.getId()));
			
			DynamicForm actualActiveForm = DynamicFormDao.init().getTemplateActiveForClientUtility();
			
			if (deindexation.getId_form() != null) {
				if (!deindexation.getStatus().equals(EDeindexationStatus.CREATED.getTag())) {
					DynamicForm dynamicFormSetted = DynamicFormDao.init().getByIdForClientUtility(deindexation.getId_form());
				
					deindexation.setDynamicForm(dynamicFormSetted);
					DeindexationInputValueDao.init().loadSavedValues(deindexation);
				} else {
					deindexation.setId_form(actualActiveForm.getId());
					deindexation.setDynamicForm(actualActiveForm);
					DeindexationInputValueDao.init().loadSavedValues(deindexation);
				}
			} else {
				deindexation.setDynamicForm(actualActiveForm);
			}
			
			if (language != null) {
				deindexation.fixLabelsAndDescriptions(language);
			}
		}
		
		return deindexation;
	}
	
	public Deindexation getBasicByUuid(String uuidDeindexation) throws RemoveApplicationException {
		return (Deindexation) getByField(TABLE, FIELD_UUID, uuidDeindexation, Deindexation.class);
	}

	public Deindexation getTemplate(String language) throws RemoveApplicationException {
		Deindexation deindexation = new Deindexation();
		DynamicForm activeForm = DynamicFormDao.init().getTemplateActiveForClientUtility();
		deindexation.setDynamicForm(activeForm);
		deindexation.fixLabelsAndDescriptions(language);
		return deindexation;
	}

	public void calculateResults(Long idDeindexation) throws RemoveApplicationException {
		List<DeindexationInputValue> listValues = DeindexationInputValueDao.init().listByDeindexation(idDeindexation);
		
		if (listValues != null) {
			listValues.forEach(val -> {
				try {
					val.setInput(DynamicFormInputDao.init().getBasicById(val.getId_form_input()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error finding input for: "+ val, e);
				}
			});
			
			ConditionEvaluatorHelper evaluator = ConditionEvaluatorHelper.init(listValues);
			DeindexationDao deindexDao = DeindexationDao.init();
			
			Deindexation deindexation = deindexDao.getBasicById(idDeindexation);
			List<DynamicFormConditionResult> lstResults = DynamicFormConditionResultDao.init().listBasicByDynamicFormId(deindexation.getId_form());
			
			deindexDao.removeRelationResults(idDeindexation);
			
			if (lstResults != null) {
				lstResults.forEach(result -> {
					if (evaluator.evaluate(result.getCondition())) {
						try {
							deindexDao.saveRelationResults(idDeindexation, result.getId());
						} catch (RemoveApplicationException e) {
							getLog().error("Error saving evaluation:" + result, e);
						}
					}
				});
			}
		}
 	}

	private void removeRelationResults(Long idDeindexation) throws RemoveApplicationException {
		String QUERY = buildDeleteQuery(schemaTable(TABLE_NAME_REL_RESULT), FIELD_REL_ID_DEINDEXATION, idDeindexation);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	private void saveRelationResults(Long idDeindexation, Long idResult) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(schemaTable(TABLE_NAME_REL_RESULT), FIELD_REL_ID_DEINDEXATION, FIELD_REL_ID_RESULT);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_REL_ID_DEINDEXATION, idDeindexation)
    				.addParameter(FIELD_REL_ID_RESULT, idResult)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public void loadResults(Deindexation deindexation) throws RemoveApplicationException {
		List<Long> idResults = new ArrayList<Long>();
		
		String QUERY = "SELECT " + FIELD_REL_ID_RESULT + " FROM " + schemaTable(TABLE_NAME_REL_RESULT) + " WHERE " + FIELD_REL_ID_DEINDEXATION + "=" + deindexation.getId();
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			idResults = conn.createQuery(QUERY).executeAndFetch(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		deindexation.setResults(DynamicFormConditionResultDao.init().listByIds(idResults));
	}
}
