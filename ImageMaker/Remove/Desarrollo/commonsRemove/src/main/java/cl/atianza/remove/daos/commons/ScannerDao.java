package cl.atianza.remove.daos.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EDevices;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EScannerStatus;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerConfiguration;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveFormula;

/**
 * Scanner DAOs methods.
 * @author pilin
 *
 */
public class ScannerDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners";
	
	private static final String FIELD_UUID = "uuid";
	private static final String FIELD_UUID_FROM = "uuid_from";
	private static final String FIELD_ID_OWNER = "id_owner";
	private static final String FIELD_ID_CLIENT_PLAN = "id_client_plan";
	private static final String FIELD_CREATION_DATE = "creation_date";
	private static final String FIELD_ACTIVATION_DATE = "activation_date";
	private static final String FIELD_DEACTIVATION_DATE = "deactivation_date";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_STATUS = "status";
	private static final String FIELD_TYPE = "type";
	private static final String FIELD_TOTAL_SCANS = "total_scans";
	
	public ScannerDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerDao.class), TABLE_NAME);
	}
	
	public static ScannerDao init() throws RemoveApplicationException {
		return new ScannerDao();
	}	
	
	public List<Scanner> listByOwner(long idOwner) throws RemoveApplicationException {
		List<Scanner> list = new ArrayList<Scanner>();
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_OWNER) 
				+ " AND " + FIELD_STATUS + " IN ('" + EScannerStatus.ACTIVE.getTag() + "') "
				+ " AND " + FIELD_TYPE + " NOT IN ('" + EScannerTypes.ONE_SHOT.getCode() + "') "
				+ " ORDER BY " + FIELD_CREATION_DATE + " DESC";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).addParameter(FIELD_ID_OWNER, idOwner)
					.executeAndFetch(Scanner.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return list;
	}
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Scanner> listByOwnerAndTypeIgnoreDeleted(long idOwner, long idClientPlan, String scannerType) throws RemoveApplicationException {
		List<Scanner> list = new ArrayList<Scanner>();
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_OWNER, FIELD_ID_CLIENT_PLAN, FIELD_TYPE) 
				+ " AND " + FIELD_STATUS + " NOT IN ('" + EScannerStatus.DELETED.getTag() + "') "
				+ " ORDER BY " + FIELD_CREATION_DATE + " DESC";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).addParameter(FIELD_ID_OWNER, idOwner)
					.addParameter(FIELD_ID_CLIENT_PLAN, idClientPlan)
					.addParameter(FIELD_TYPE, scannerType).executeAndFetch(Scanner.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return list;
	}
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Scanner getByUuidExecutingRestriction(String uuidScanner, String scannerType) throws RemoveApplicationException {
		Scanner scanner = getJustConfigByUuid(uuidScanner, scannerType);

		if (scanner != null) {
			if (!scanner.isExecuting()) {
				scanner.setResults(ScannerResultDao.init().list(scanner.getId()));	
			}
		}
		
		return scanner;
	}
	
	public Scanner getJustConfigByUuid(String uuid, String scannerType) throws RemoveApplicationException {
		Scanner scanner = getBasicByUuid(uuid, scannerType);
		
		if (scanner != null) {
			loadConfigData(scanner);
		}
		
		return scanner;
	}
	public Scanner getBasicByUuid(String uuid, String scannerType) throws RemoveApplicationException {
		Scanner scanner = null;
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_UUID, FIELD_TYPE) + " ORDER BY " + FIELD_CREATION_DATE + " DESC";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).addParameter(FIELD_UUID, uuid)
					.addParameter(FIELD_TYPE, scannerType).executeAndFetchFirst(Scanner.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return scanner;
	}
	
	public Scanner getBasicByUuid(String uuid) throws RemoveApplicationException {
		return (Scanner) getByField(TABLE, FIELD_UUID, uuid, Scanner.class);
	}
	
	/**
	 * Build a Default Scanner for Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Scanner buildDefaultScanner(long idOwner, ClientPlan clientPlanActive, String scannerType) throws RemoveApplicationException {
		Client client = ClientDao.init().getById(idOwner);
		
		Scanner scanner = new Scanner();
		scanner.setId_owner(client.getId());
		scanner.setActive(true);
		scanner.setType(scannerType);
		scanner.setId_client_plan(clientPlanActive.getId());
		
		ScannerConfiguration configuration = new ScannerConfiguration();
		configuration.setCountries(Arrays.asList(client.getCountry()));
		configuration.setLanguage(client.getLanguage());
		configuration.setPages(clientPlanActive.getDetail().getMax_search_page());
		configuration.setDevice(EDevices.DESKTOP.getCode());
		configuration.setSearch_type(clientPlanActive.getDetail().getSections_to_search());
		configuration.setLstSearchTypes(clientPlanActive.getDetail().getSectionsToSearch());
		scanner.setConfiguration(configuration);
		
		return scanner;
	}

	/**
	 * Load Inner data (Configuration, Keywords, TrackingWords, Results)
	 * @param scanner
	 * @throws RemoveApplicationException
	 */
	private void loadConfigData(Scanner scanner) throws RemoveApplicationException {
		if (scanner != null) {
			scanner.setConfiguration(ScannerConfigurationDao.init().get(scanner.getId()));
			scanner.setKeywords(ScannerKeywordsDao.init().list(scanner.getId()));
			scanner.setTrackingWords(ScannerTrackingWordsDao.init().list(scanner.getId()));
			
			scanner.splitTrackingWords();
			scanner.getConfiguration().splitSearchTypes();
		}
	}

	/**
	 * Save Scanner Object
	 * @param scanner
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Scanner save(Scanner scanner) throws RemoveApplicationException {
		scanner.setUuid(UUID.randomUUID().toString());
		scanner.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		scanner.setStatus(EScannerStatus.ACTIVE.getTag());
		
		String QUERY = buildInsertQuery(TABLE, FIELD_UUID, FIELD_UUID_FROM, FIELD_ID_OWNER, 
				FIELD_ID_CLIENT_PLAN, 
				FIELD_CREATION_DATE, FIELD_ACTIVATION_DATE, FIELD_NAME,
				FIELD_TYPE, FIELD_TOTAL_SCANS, FIELD_STATUS);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true).addParameter(FIELD_UUID, scanner.getUuid())
    				.addParameter(FIELD_UUID_FROM, scanner.getUuid_from())
    				.addParameter(FIELD_ID_OWNER, scanner.getId_owner())
    				.addParameter(FIELD_ID_CLIENT_PLAN, scanner.getId_client_plan())
    				.addParameter(FIELD_CREATION_DATE, scanner.getCreation_date())
    				.addParameter(FIELD_ACTIVATION_DATE, scanner.getActivation_date())
    				.addParameter(FIELD_TYPE, scanner.getType())
    				.addParameter(FIELD_NAME, scanner.getName())
    				.addParameter(FIELD_STATUS, scanner.getStatus())
    				.addParameter(FIELD_TOTAL_SCANS, scanner.getTotal_scans())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	scanner.setId(idRecord);
    	innerSaves(scanner);
    	
    	return scanner;
	}
	
	/**
	 * Save Scanner Object
	 * @param scanner
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Scanner saveForClon(Scanner scanner) throws RemoveApplicationException {
		scanner.setUuid(UUID.randomUUID().toString());
		scanner.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		scanner.setStatus(EScannerStatus.ACTIVE.getTag());
		
		String QUERY = buildInsertQuery(TABLE, FIELD_UUID, FIELD_UUID_FROM, FIELD_ID_OWNER, 
				FIELD_ID_CLIENT_PLAN, 
				FIELD_CREATION_DATE, FIELD_ACTIVATION_DATE, FIELD_NAME,
				FIELD_TYPE, FIELD_TOTAL_SCANS, FIELD_STATUS);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true).addParameter(FIELD_UUID, scanner.getUuid())
    				.addParameter(FIELD_UUID_FROM, scanner.getUuid_from())
    				.addParameter(FIELD_ID_OWNER, scanner.getId_owner())
    				.addParameter(FIELD_ID_CLIENT_PLAN, scanner.getId_client_plan())
    				.addParameter(FIELD_CREATION_DATE, scanner.getCreation_date())
    				.addParameter(FIELD_ACTIVATION_DATE, scanner.getActivation_date())
    				.addParameter(FIELD_TYPE, scanner.getType())
    				.addParameter(FIELD_NAME, scanner.getName())
    				.addParameter(FIELD_STATUS, scanner.getStatus())
    				.addParameter(FIELD_TOTAL_SCANS, scanner.getTotal_scans())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	scanner.setId(idRecord);
    	innerSavesForClon(scanner);
    	
    	return scanner;
	}
	
	/**
	 * Save Scanner Inner Objects Previous Scan (Configuration, Keywords, TrakingWords)
	 * @param scanner
	 * @throws RemoveApplicationException
	 */
	private void innerSaves(Scanner scanner) throws RemoveApplicationException {
		scanner.getConfiguration().joinSearchTypes();
		ScannerConfigurationDao.init().save(scanner);
		ScannerKeywordsDao.init().save(scanner);
		scanner.joinTrackingWords();
		ScannerTrackingWordsDao.init().save(scanner);
		getLog().info("Scanner saved: " + scanner.getUuid());
		
		ScannerResultViewDao.init().refreshViewAsync(scanner.getUuid(), scanner.getType());
	}
	
	/**
	 * Save Scanner Inner Objects Previous Scan (Configuration, Keywords, TrakingWords)
	 * @param scanner
	 * @throws RemoveApplicationException
	 */
	private void innerSavesForClon(Scanner scanner) throws RemoveApplicationException {
		scanner.getConfiguration().joinSearchTypes();
		ScannerConfigurationDao.init().save(scanner);
		ScannerKeywordsDao.init().save(scanner);
		scanner.joinTrackingWords();
		ScannerTrackingWordsDao.init().save(scanner);
		
		getLog().info("Scanner saved: " + scanner.getUuid());
		
		ScannerResultViewDao.init().refreshViewAsync(scanner.getUuid(), scanner.getType());
	}

	/**
	 * Deactivate Scanner
	 * @param idScanner
	 * @throws RemoveApplicationException
	 */
	public void deactivate(Long idScanner) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, idScanner, FIELD_DEACTIVATION_DATE, FIELD_STATUS);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_DEACTIVATION_DATE, RemoveDateUtils.nowLocalDateTime())
    				.addParameter(FIELD_STATUS, EScannerStatus.DEACTIVE.getTag())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public void updateStatus(Scanner scanner, EScannerStatus status) throws RemoveApplicationException {
		if (isValidChange(scanner, status)) {
			updateStatusInner(scanner, status);
		} else {
			updateStatusInner(scanner, EScannerStatus.SUSPENDED);
		}
	}
	
	public void updateStatusInner(Scanner scanner, EScannerStatus status) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, scanner.getId(), FIELD_STATUS);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_STATUS, status.getTag())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	

	private boolean isValidChange(Scanner scanner, EScannerStatus status) throws RemoveApplicationException {
		if (status.getTag().equals(EScannerStatus.ACTIVE.getTag()) || status.getTag().equals(EScannerStatus.EXECUTING.getTag())) {
			Client client = ClientDao.init().getById(scanner.getId_owner());
			
			if (!client.isActive() || client.isReadOnly()) {
				return false;
			}
		}
		
		return true;
	}

	public void changeStatusByOwner(Long id_client,  EScannerStatus oldStatus, EScannerStatus newStatus) throws RemoveApplicationException {
		String QUERY = "UPDATE " + TABLE + " SET " + FIELD_STATUS + " = '"+newStatus.getTag() 
				+ "' WHERE " + FIELD_STATUS + " = '" + oldStatus.getTag() + "' AND "+ FIELD_ID_OWNER + " = " + id_client;
    	
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
	 * Save Scanner Results
	 * @param scanner
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Scanner saveResults(Scanner scanner) throws RemoveApplicationException {
		if (scanner != null && scanner.getResults() != null && !scanner.getResults().isEmpty()) {
			ScannerResultDao resultDao = ScannerResultDao.init();
			List<ScannerResult> results = new ArrayList<ScannerResult>();
			
			scanner.getResults().forEach(result -> {
				result.setId_scanner(scanner.getId());
				try {
					RemoveFormula.processResult(scanner, result);
					results.add(resultDao.save(result));
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving result:" + result, e);
				}
			});
			
			scanner.setResults(results);
			
			if (scanner.esTransform()) {
				scanner.getResults().forEach(res -> {
					try {
						ScannerResultDao.init().updateProgress(res.getId(), res.getProgress());
					} catch (RemoveApplicationException e) {
						getLog().error("Error updatign progress for transform scanner: " + res, e);
					}	
				});
			}
		}
		
		return scanner;
	}

	/**
	 * List Recurrent Scanners for Jobs
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Scanner> listRecurrents() throws RemoveApplicationException {
		List<Scanner> scanners = null;
		
		String QUERY = "SELECT * FROM " + TABLE + " WHERE " + FIELD_STATUS + " = '" + EScannerStatus.ACTIVE.getTag() + "' AND " 
					+ FIELD_TYPE + " IN ('" + EScannerTypes.MONITOR.getCode() + "','" + EScannerTypes.TRANSFORM.getCode() + "')" ;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			scanners = conn.createQuery(QUERY).executeAndFetch(Scanner.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return scanners;
	}

	/**
	 * 
	 * @param scanner
	 * @throws RemoveApplicationException
	 */
	public void upsertResults(Scanner scanner) throws RemoveApplicationException {
		getLog().info("Updating scanner: "+scanner.getUuid());
		if (scanner != null && scanner.getResults() != null && !scanner.getResults().isEmpty()) {
			getLog().info("Total Results: "+scanner.getResults().size());
			ScannerResultDao resultDao = ScannerResultDao.init();
			
			scanner.getResults().forEach(result -> {
				getLog().info("Processing result: " + result.getUuid());
				try {
					resultDao.findIfExists(scanner.getId(), result);
					
					// RemoveFormula.processResult(scanner, result);
					
					if (result.esNuevo()) {
						result = resultDao.save(result);
					} else {
						resultDao.upsertInnerObjects(result);
					}
				} catch (RemoveApplicationException e) {
					getLog().error("Error updating result: "+result, e);
				}
			});
		}
	}

	/**
	 * 
	 * @param idResult
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Scanner loadByResult(Long idResult) throws RemoveApplicationException {
		ScannerResult result = ScannerResultDao.init().getById(idResult);
		return (Scanner) getById(TABLE, result.getId_scanner(), Scanner.class);
	}

	/**
	 * 
	 * @param scan
	 * @throws RemoveApplicationException 
	 */
	public void updateTotalScans(Scanner scan) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, scan.getId(), FIELD_TOTAL_SCANS);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_TOTAL_SCANS, scan.getTotal_scans())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public Scanner getForRecurrence(String scanner_uuid) throws RemoveApplicationException {
		Scanner scanner = (Scanner) getByField(TABLE, FIELD_UUID, scanner_uuid, Scanner.class);
		
		if (scanner != null) {
			scanner.setConfiguration(ScannerConfigurationDao.init().get(scanner.getId()));
			scanner.setKeywords(ScannerKeywordsDao.init().list(scanner.getId()));
			scanner.setTrackingWords(ScannerTrackingWordsDao.init().list(scanner.getId()));
			
			scanner.splitTrackingWords();
			scanner.getConfiguration().splitSearchTypes();
		}
		
		return scanner;
	}

	public Scanner updateConfiguration(Scanner scanner) throws RemoveApplicationException {
		updateName(scanner);
		ScannerKeywordsDao.init().saveOnlyNews(scanner);
		scanner.getConfiguration().joinSearchTypes();
		ScannerConfigurationDao.init().update(scanner);
		scanner.joinTrackingWords();
		ScannerTrackingWordsDao.init().updateValuesAndFeelings(scanner);
		
		return scanner;
	}

	private void updateName(Scanner scanner) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, scanner.getId(), FIELD_NAME);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_NAME, scanner.getName())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public Scanner getBasicById(Long id) throws RemoveApplicationException {
		return (Scanner) getByField(TABLE, FIELD_ID, id, Scanner.class);
	}

	public boolean checkExecuting(Long idScanner) throws RemoveApplicationException {
		return getBasicById(idScanner).isExecuting();
	}

	public Long countActivesForType(EScannerTypes type) throws RemoveApplicationException {
		String QUERY = "SELECT COUNT ("+FIELD_ID+") FROM "+TABLE+" WHERE "+FIELD_TYPE+" = :"+FIELD_TYPE+" AND "+FIELD_STATUS+" = '" + EScannerStatus.ACTIVE.getTag() + "' ";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).addParameter(FIELD_TYPE, type.getCode()).executeAndFetchFirst(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return 0l;
	}
	public Long countOnSuscriptionForType(Long idClientPlan, EScannerTypes type) throws RemoveApplicationException {
		String QUERY = "SELECT COUNT ("+FIELD_ID+") FROM "+TABLE
				+" WHERE "+FIELD_TYPE+" = :"+FIELD_TYPE+" AND "+FIELD_ID_CLIENT_PLAN+" = " + idClientPlan
				+" AND "+FIELD_STATUS+" NOT IN ('" + EScannerStatus.FIXED.getTag() +"', '" + EScannerStatus.DELETED.getTag() + "')";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).addParameter(FIELD_TYPE, type.getCode()).executeAndFetchFirst(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return 0l;
	}
	public Long countForType(EScannerTypes type) throws RemoveApplicationException {
		String QUERY = "SELECT COUNT ("+FIELD_ID+") FROM "+TABLE+" WHERE "+FIELD_TYPE+" = :"+FIELD_TYPE;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).addParameter(FIELD_TYPE, type.getCode()).executeAndFetchFirst(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return 0l;
	}

	public List<Scanner> listActivesByClientPlanId(Long idClientPlan) throws RemoveApplicationException {
		List<Scanner> list = new ArrayList<Scanner>();
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_CLIENT_PLAN) 
				+ " AND " + FIELD_STATUS + " IN ("
						+ "'" + EScannerStatus.ACTIVE.getTag() + "', "
						+ "'" + EScannerStatus.DEACTIVE.getTag() + "', "
						+ "'" + EScannerStatus.EXECUTING.getTag() + "', "
						+ "'" + EScannerStatus.SUSPENDED.getTag() + "', "
						+ "'" + EScannerStatus.FAILED.getTag() + "', "
						+ "'" + EScannerStatus.PAUSED.getTag() + "', "						
						+ "'" + EScannerStatus.FIXED.getTag() + "') "
				+ " ORDER BY " + FIELD_CREATION_DATE + " DESC";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).addParameter(FIELD_ID_CLIENT_PLAN, idClientPlan)
					.executeAndFetch(Scanner.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return list;
	}
	
	public List<Scanner> listByClientPlanIdForSuscriptionChange(Long idClientPlan) throws RemoveApplicationException {
		List<Scanner> list = listActivesByClientPlanId(idClientPlan);
		
		if (list != null) {
			list.forEach(scanner -> {
				try {
					scanner.setKeywords(ScannerKeywordsDao.init().list(scanner.getId()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading keyword: " + scanner, e);
				}
			});
		}
		
		return list;
	}

	public void updateClientPlanId(Long idClientPlanNew, Long idClientPlanOld, List<Long> idsToUpdate) throws RemoveApplicationException {
		updateClientPlanId(idClientPlanNew, idClientPlanOld, StringUtils.join(idsToUpdate, ","));
	}
	public void updateClientPlanId(Long idClientPlanNew, Long idClientPlanOld, String idsToUpdate) throws RemoveApplicationException {
		if (idsToUpdate != null && !idsToUpdate.isEmpty()) {
			String QUERY = "UPDATE " + TABLE + " SET " + FIELD_ID_CLIENT_PLAN + " = " + idClientPlanNew + " WHERE " 
					+ FIELD_ID_CLIENT_PLAN + " = " + idClientPlanOld 
					+ " AND " + FIELD_ID + " IN (" + idsToUpdate + ") ";
	    	
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

	public void deactivateByClientPlan(Long idClientPlanOld) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID_CLIENT_PLAN, idClientPlanOld, FIELD_DEACTIVATION_DATE, FIELD_STATUS);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_DEACTIVATION_DATE, RemoveDateUtils.nowLocalDateTime())
    				.addParameter(FIELD_STATUS, EScannerStatus.DEACTIVE.getTag())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public void markScannerAsFixed(String uuid) throws RemoveApplicationException {
		Scanner scanner = getBasicByUuid(uuid);
		
		if (scanner != null) {
			updateStatus(scanner, EScannerStatus.FIXED);
		}
	}
}
