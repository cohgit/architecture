package cl.atianza.remove.validators;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.commons.ForbiddenWordDao;
import cl.atianza.remove.daos.commons.ScannerConfigurationDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerImpulseContentDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.models.commons.ForbiddenWord;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.models.commons.ScannerImpulseContent;
import cl.atianza.remove.models.commons.ScannerImpulseObservation;
import cl.atianza.remove.utils.RemoveTokenAccess;

public class ScannerValidator {
	private Logger log = LogManager.getLogger(ScannerValidator.class);
	private Client client;
	private Scanner scanner;
	
	public ScannerValidator(Client client, Scanner scanner) throws RemoveApplicationException {
		super();
		this.client = client;
		
		if (client!= null) log.info("Validating access to client: " + client.getUuid());
		ClientAccessValidator.init(client).validateClientAccess();
	}
	
	public static ScannerValidator init(Client client) throws RemoveApplicationException {
		return new ScannerValidator(client, null);
	}
	public static ScannerValidator init(RemoveTokenAccess token) throws RemoveApplicationException {
		return new ScannerValidator(ClientDao.init().getByUuid(token.getUuidAccount()), null);
	}
	public static ScannerValidator init(String uuid) throws RemoveApplicationException {
		return new ScannerValidator(ClientDao.init().getByUuid(uuid), null);
	}
	public static ScannerValidator init(Long idClient) throws RemoveApplicationException {
		return new ScannerValidator(ClientDao.init().getById(idClient), null);
	}

	public void validateClientScanner(Scanner scanner) throws RemoveApplicationException {
		this.scanner = scanner;
		
		checkScannerClient();
		checkScannerType();
		checkScannerPlanLimitations();
		checkForbiddenWords();
	}	

	public void validateClientScannerRecurrent(Scanner scanner) throws RemoveApplicationException {
		if (scanner.esNuevo()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_SCANNER_NOT_ACTIVE);
		}
			
		Scanner oldScanner = ScannerDao.init().getBasicById(scanner.getId());
		
		if (!oldScanner.isActive()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_SCANNER_NOT_ACTIVE);
		}
			
		validateClientScanner(scanner);
	}
	
	private void checkScannerPlanLimitations() throws RemoveApplicationException {
		if(scanner.getType().equals(EScannerTypes.ONE_SHOT.getCode())) {
			checkLimitationsForOneShot();
		} else if(scanner.getType().equals(EScannerTypes.MONITOR.getCode())) {
			checkLimitationsForRecurrent();
		} else if(scanner.getType().equals(EScannerTypes.TRANSFORM.getCode())) {
			checkLimitationsForTransform();
		} else {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PARAMS);	
		}
	}
	
	private void checkForbiddenWords() throws RemoveApplicationException {
		List<ForbiddenWord> forbiddenWords = ForbiddenWordDao.init().list();
		List<ForbiddenWord> found = new ArrayList<ForbiddenWord>();
		
		if (forbiddenWords != null && !forbiddenWords.isEmpty()) {
			scanner.getKeywords().forEach(keyword -> {
				for (String splitedword : keyword.getWord().trim().split(" ")) {
					forbiddenWords.forEach(forbidden -> {
						if (splitedword.equalsIgnoreCase(forbidden.getWord())) {
							found.add(forbidden);
						}
					});		
				}
				
				keyword.getListSuggested().forEach(suggested -> {
					for (String splitedword : suggested.getWord().trim().split(" ")) {
						forbiddenWords.forEach(forbidden -> {
							if (splitedword.equalsIgnoreCase(forbidden.getWord())) {
								found.add(forbidden);
							}
						});		
					}
				});				
			});
			
			scanner.getTrackingWords().forEach(trackingWord -> {
				for (String splitedword : trackingWord.getWord().trim().split(" ")) {
					forbiddenWords.forEach(forbidden -> {
						if (splitedword.equalsIgnoreCase(forbidden.getWord())) {
							found.add(forbidden);
						}
					});		
				}		
			});
		}
		
		if (!found.isEmpty()) {
			List<String> params = new ArrayList<String>();
			found.forEach(forbidden -> params.add(forbidden.getWord()));
			
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_SCANNER_FORBIDDEN_WORDS_FOUND, params);
		}
	}

	private void checkLimitationsForOneShot() throws RemoveApplicationException {
		checkBasicValidation();
		
		if (scanner.getRestrictions().getDetail().getLimit_credits() != null && scanner.getRestrictions().getCredits_used().longValue() >= scanner.getRestrictions().getDetail().getLimit_credits().longValue()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_SCANNER_EXCEEDS_CREDITS);
		}
	}
	
	private void checkLimitationsForRecurrent() throws RemoveApplicationException {
		checkBasicValidation();
	}

	private void checkLimitationsForTransform() throws RemoveApplicationException {
		checkBasicValidation();

		if (scanner.listJustTrackingUrlsActives().size() > scanner.getRestrictions().getDetail().getMax_url_to_remove().intValue()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_SCANNER_EXCEEDS_MAX_URL_TO_REMOVE);
		}
	}
	
	private void checkBasicValidation() throws RemoveApplicationException {
		if (scanner.getConfiguration() == null) {
			scanner.setConfiguration(ScannerConfigurationDao.init().get(scanner.getId()));
		}
		if (scanner.getRestrictions() == null) {
			scanner.setRestrictions(ClientPlanDao.init().getById(scanner.getId_client_plan()));
		}
		
		if (scanner.countTotalKeywords() > scanner.getRestrictions().getDetail().getMax_keywords().intValue()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_SCANNER_EXCEEDS_MAX_KEYWORDS);
		}
		if (scanner.getConfiguration().getCountries().size() > scanner.getRestrictions().getDetail().getMax_countries().intValue()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_SCANNER_EXCEEDS_MAX_COUNTRIES);
		}
		if (scanner.getConfiguration().getPages().intValue() > scanner.getRestrictions().getDetail().getMax_search_page().intValue()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_SCANNER_EXCEEDS_MAX_PAGES);
		}
		if (!validSearchTypes(scanner.getConfiguration().getSearch_type())) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_SCANNER_INVALID_SEARCH_TYPES);
		}
	}
	
	private boolean validSearchTypes(String search_type) {
		String[] types = search_type.split(",");
		
		for (String type : types) {
			if (!this.scanner.getRestrictions().getDetail().getSections_to_search().contains(type)) return false;
		}
		
		return true;
	}

	public void validateScannerLicensesAvailables(String scannerType) throws RemoveApplicationException {
		this.validateScannerType(scannerType);
		EScannerTypes type =  EScannerTypes.obtain(scannerType);
		ClientPlan suscription = this.client.getPlanActives().get(0);
		
		Long totalScannersOccupied = ScannerDao.init().countOnSuscriptionForType(suscription.getId(), type);
		
		switch (type) {
			case ONE_SHOT: {
				if (totalScannersOccupied.longValue() >= suscription.getDetail().getLimit_credits().longValue()) {
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_MAX_SCANNERS_ALLOWED_EXCEDED);
				}
				break;
			}
			case MONITOR: {
				if (totalScannersOccupied.longValue() >= suscription.getDetail().getTotal_monitor_licenses().longValue()) {
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_MAX_SCANNERS_ALLOWED_EXCEDED);
				}
				break;
			}
			case TRANSFORM: {
				if (totalScannersOccupied.longValue() >= suscription.getDetail().getTotal_transforma_licenses().longValue()) {
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_MAX_SCANNERS_ALLOWED_EXCEDED);
				}
				break;
			}
		}
	}
	
	public void validateScannerType(String scannerType) throws RemoveApplicationException {
		checkScannerType(scannerType);
	}
	public void validateScannerType(Scanner _scanner) throws RemoveApplicationException {
		this.scanner = _scanner;
		
		checkScannerType(scanner.getType());
		
		if (scanner.getId_owner().longValue() != this.client.getId().longValue() || 
				scanner.getId_client_plan().longValue() != this.client.getPlanActives().get(0).getId().longValue()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_SCANNER_DONT_MATCH);
		}
	}

	public void validateClientScanner(ScannerImpulse impulse) throws RemoveApplicationException {
		this.scanner = ScannerDao.init().getBasicById(impulse.getId_scanner());
		checkScannerClient();
	}
	
	public void validateScannerSnippet(Long idResult) {
		
	}
	public void validateScannerImpulse(ScannerImpulse impulse, Long idClientPlan) throws RemoveApplicationException {
		Scanner scanner;
		
		if (impulse.getUuidScanner() != null && !impulse.getUuidScanner().isEmpty()) {
			scanner = ScannerDao.init().getBasicByUuid(impulse.getUuidScanner(), EScannerTypes.TRANSFORM.getCode());
		} else {
			scanner = ScannerDao.init().getBasicById(impulse.getId_scanner());			
		}
		 // Aqui estaba uuidScanner, verificar si se elimina ese atributo
		
		if (!impulse.esNuevo()) {
			if (impulse.getId_scanner().longValue() != scanner.getId().longValue()) {
				log.error("Impulse old scanner doesnt match with actual impulse scanner...");
				RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_AND_SCANNER_DONT_MATCH);
			}
		}
	}
	public void validateScannerImpulseObservation(ScannerImpulseObservation observation, Long idClientPlan) throws RemoveApplicationException {
		ScannerImpulseContent content = ScannerImpulseContentDao.init().getById(observation.getId_scanner_impulse_content());
		ScannerImpulse impulse = ScannerImpulseDao.init().get(content.getId_scanner_impulse());
		
		validateScannerImpulse(impulse, idClientPlan);
	}

	private void checkScannerClient() throws RemoveApplicationException {
		if (scanner.getId_owner().longValue() != client.getId().longValue()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_AND_SCANNER_DONT_MATCH);
		}
		
		checkScannerType();
	}
	
	private void checkScannerType() throws RemoveApplicationException {
		checkScannerType(scanner.getType());
	}
	private void checkScannerType(String scannerType) throws RemoveApplicationException {
		if (scanner != null) {
			checkScannerRestrictions();
			
			if(scannerType.equals(EScannerTypes.ONE_SHOT.getCode())) {
				if (!scanner.getRestrictions().getDetail().isAccess_scanner()) {
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_SCANNER_DONT_MATCH_PLAN);	
				}
			} else if(scannerType.equals(EScannerTypes.MONITOR.getCode())) {
				if (!scanner.getRestrictions().getDetail().isAccess_monitor()) {
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_SCANNER_DONT_MATCH_PLAN);	
				}
			} else if(scannerType.equals(EScannerTypes.TRANSFORM.getCode())) {
				if (!scanner.getRestrictions().getDetail().isAccess_transforma()) {
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_SCANNER_DONT_MATCH_PLAN);	
				}
			} else {
				RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PARAMS);	
			}
		}
	}

	private void checkScannerRestrictions() throws RemoveApplicationException {
		if (scanner.getRestrictions() == null) {
			scanner.setRestrictions(ClientPlanDao.init().listByClientId(client.getId()).get(0));
		}
	}
}
