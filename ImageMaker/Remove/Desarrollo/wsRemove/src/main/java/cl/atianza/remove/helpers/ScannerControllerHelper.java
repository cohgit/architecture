package cl.atianza.remove.helpers;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.commons.ScannerConfigurationDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.daos.commons.ScannerKeywordsDao;
import cl.atianza.remove.daos.commons.ScannerResultDao;
import cl.atianza.remove.daos.commons.ScannerResultViewDao;
import cl.atianza.remove.daos.commons.ScannerTrackingWordsDao;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerResultView;
import cl.atianza.remove.models.views.TableScannerListView;
import cl.atianza.remove.utils.RemoveJsonUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.validators.ScannerValidator;

public class ScannerControllerHelper {
	private Logger log;
	
	public ScannerControllerHelper(Logger log) {
		this.log = log;
	}

	public static ScannerControllerHelper init(Logger log) {
		return new ScannerControllerHelper(log);
	}
	
	public String processListScanner(Client client, Long idClientPlan, String scannerType) throws RemoveApplicationException {
		ScannerValidator.init(client).validateScannerType(scannerType);
		
		List<Scanner> list = ScannerDao.init().listByOwnerAndTypeIgnoreDeleted(client.getId(), idClientPlan, scannerType);
		
		if (list != null) {
			list.forEach(scanner -> {
				try {
					scanner.setResults(ScannerResultDao.init().listWithoutTracking(scanner.getId()));
					scanner.clearImagesResults();
					scanner.setConfiguration(ScannerConfigurationDao.init().get(scanner.getId()));
					scanner.setKeywords(ScannerKeywordsDao.init().list(scanner.getId()));
					scanner.splitKeywords();
					scanner.getConfiguration().splitSearchTypes();
					scanner.setTrackingWords(ScannerTrackingWordsDao.init().list(scanner.getId()));
					scanner.splitTrackingWords();
					scanner.setImpulses(ScannerImpulseDao.init().list(scanner.getId()));
				} catch (RemoveApplicationException e) {
					log.error("Error loading keywords: " + scanner, e);
				}
			});
		} else {
			list = new ArrayList<Scanner>();
		}
		
		ClientPlan clientPlan = client.getPlanActives().get(0);
		log.info("Listing scanners: " + list.size() + " - for: " + clientPlan);
		
		return RemoveResponseUtil.buildOk(TableScannerListView.init(list, clientPlan));
	}
	
	public Scanner processGetScanner(String uuidScanner, String scannerType) throws RemoveApplicationException {
		Scanner scanner = ScannerDao.init().getBasicByUuid(uuidScanner);
		
		ScannerResultView view = ScannerResultViewDao.init().getByUuid(uuidScanner);

		if (view != null && view.getContent() != null) {
			log.info("Scanner view Found: " + view.getUuid());
			scanner = (Scanner) RemoveJsonUtil.jsonToData(view.getContent(), Scanner.class);
			log.info("Scanner view result size: " + scanner.getResults().size());
			scanner.setRestrictions(ClientPlanDao.init().getById(scanner.getId_client_plan()));
			scanner.setImpulses(ScannerImpulseDao.init().listPublishedByScanner(scanner.getId()));
		} else { // Deprecado temporal mientras se migran todos los scanners al view
			log.info("Scanner view not Found, Looking for main tables");
			scanner = ScannerDao.init().getJustConfigByUuid(uuidScanner, scannerType);
			
			if (scanner != null) {
				scanner.setResults(ScannerResultDao.init().list(scanner.getId()));
				scanner.setRestrictions(ClientPlanDao.init().getById(scanner.getId_client_plan()));
				
				if (!scanner.isExecuting()) {
					scanner.setImpulses(ScannerImpulseDao.init().listPublishedByScanner(scanner.getId()));	
				}
			}
		}
		
		if (scanner != null) {
			scanner.clearImagesResults();
		}
		
		if (scanner.getTrackingWords().isEmpty()) {
			scanner.setTrackingWords(ScannerTrackingWordsDao.init().list(scanner.getId()));
			scanner.splitTrackingWords();
		}
		
		return scanner;
	}
	
	public String generateScanner(Client client, Scanner scanner) throws RemoveApplicationException {
		log.info("Generate scanner: "+ scanner.getUuid());
		ScannerValidator scannerValidator = ScannerValidator.init(client);
		scannerValidator.validateClientScanner(scanner);
		scannerValidator.validateScannerLicensesAvailables(scanner.getType());
		
		ScannerHelper helper = ScannerHelper.init(client.getUuid());
		log.info("Executing scanner: "+ scanner.getUuid());
		scanner = helper.executeFirstScan(scanner);
		//ScannerDao.init().updateStatus(scanner, EScannerStatus.EXECUTING); TODO: Verificar esto a ver porque esta aqui
		return RemoveResponseUtil.buildOk(ScannerDao.init().getByUuidExecutingRestriction(scanner.getUuid(), scanner.getType()));
	}
	
	public String generateScannerForClon(Client client, Scanner scanner) throws RemoveApplicationException {
		log.info("Generate scanner: "+ scanner.getUuid());
		ScannerValidator scannerValidator = ScannerValidator.init(client);
		scannerValidator.validateClientScanner(scanner);
		scannerValidator.validateScannerLicensesAvailables(scanner.getType());
		
		ScannerHelper helper = ScannerHelper.init(client.getUuid());
		log.info("Executing scanner: "+ scanner.getUuid());
		scanner = helper.executeFirstScanForClon(scanner);
		//ScannerDao.init().updateStatus(scanner, EScannerStatus.EXECUTING); TODO: Verificar esto a ver porque esta aqui
		return RemoveResponseUtil.buildOk(ScannerDao.init().getByUuidExecutingRestriction(scanner.getUuid(), scanner.getType()));
	}
	
	public void repeatScanner(Client client, Scanner scanner) throws RemoveApplicationException {
		log.info("Repeat scanner: "+ scanner.getUuid());
		ScannerValidator scannerValidator = ScannerValidator.init(client);
		scannerValidator.validateClientScanner(scanner);
		
		ScannerHelper helper = ScannerHelper.init(client.getUuid());
		log.info("Executing scanner: "+ scanner.getUuid());
		helper.runSubsecuentScannersAsync(scanner);
	}
	
	public Scanner buildDefaultEmptyScanner(Client client, String scannerType) throws RemoveApplicationException { 
		ScannerValidator.init(client).validateScannerLicensesAvailables(scannerType);
		
		Scanner scanner = ScannerDao.init().buildDefaultScanner(client.getId(), client.getPlanActives().get(0), scannerType);
		scanner.setRestrictions(client.getPlanActives().get(0));
		scanner.setId_client_plan(client.getPlanActives().get(0).getId());
		
		return scanner;
	}

	public String updateConfiguration(Client client, Scanner scanner) throws RemoveApplicationException {
		ScannerValidator.init(client).validateClientScanner(scanner);
		scanner = ScannerDao.init().updateConfiguration(scanner);
		ScannerResultViewDao.init().refreshViewAsync(scanner.getUuid(), scanner.getType());
		
		if (scanner.getType().equals(EScannerTypes.MONITOR.getCode())) {
			ClientNotificationHelper.init(client).createRecurrentConfigurationUpdatedNotification(scanner.getUuid(), scanner.getName());
		} else if (scanner.getType().equals(EScannerTypes.TRANSFORM.getCode())) {
			ClientNotificationHelper.init(client).createTransformConfigurationUpdatedNotification(scanner.getUuid(), scanner.getName());
		}
		
		return RemoveResponseUtil.buildOk(scanner);
	}
}
