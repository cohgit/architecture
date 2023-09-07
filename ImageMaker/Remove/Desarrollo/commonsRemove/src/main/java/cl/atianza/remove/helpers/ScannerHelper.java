package cl.atianza.remove.helpers;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.commons.ConfigurationDao;
import cl.atianza.remove.daos.commons.CountryDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.daos.commons.ScannerKeywordsDao;
import cl.atianza.remove.daos.commons.ScannerResultDao;
import cl.atianza.remove.daos.commons.ScannerResultRawDao;
import cl.atianza.remove.daos.commons.ScannerResultViewDao;
import cl.atianza.remove.enums.ECountries;
import cl.atianza.remove.enums.ELanguages;
import cl.atianza.remove.enums.EScannerStatus;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.enums.ESearchTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.external.clients.serp.RemoveSERPEngine;
import cl.atianza.remove.external.clients.serp.RemoveSERPImageResults;
import cl.atianza.remove.external.clients.serp.RemoveSERPNewsResults;
import cl.atianza.remove.external.clients.serp.RemoveSERPRequest;
import cl.atianza.remove.external.clients.serp.RemoveSERPWebOrganicResults;
import cl.atianza.remove.external.clients.serp.RemoveSERPWebResponse;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.models.commons.Country;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerKeyword;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.models.commons.ScannerResultImageSnippet;
import cl.atianza.remove.models.commons.ScannerResultImageSnippetTrack;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippet;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippetTrack;
import cl.atianza.remove.models.commons.ScannerResultRaw;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;
import cl.atianza.remove.models.commons.ScannerResultWebSnippetTrack;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveFormula;
import cl.atianza.remove.utils.RemoveJsonUtil;
import cl.atianza.remove.utils.RemoveResponse;
import cl.atianza.remove.validators.ClientAccessValidator;

/**
 * Helper class for scanner consults and inspections.
 * @author pilin
 *
 */
public class ScannerHelper {
	protected Logger log = LogManager.getLogger(ScannerHelper.class);
	
	private String clientUUID;
	private int totalCalls = 0;
	private LocalDateTime scanTime = RemoveDateUtils.nowLocalDateTime();
	private Long recurrency = 1l;
	private Long creditsRemaining;
	private String expirationDate;
	private Long consumed;
	
	public ScannerHelper(String clientUUID) throws RemoveApplicationException {
		this.clientUUID = clientUUID;
	}

	public static ScannerHelper init(String clientUUID) throws RemoveApplicationException {
		return new ScannerHelper(clientUUID);
	}
	
	public static void externalCallForRecurrentTasks(String scannerUuid) throws RemoveApplicationException {
		Scanner scanner = ScannerDao.init().getForRecurrence(scannerUuid);
		Client client = ClientDao.init().getById(scanner.getId_owner());
		ScannerHelper.init(client.getUuid()).runSubsecuentScanners(scanner);
	}
	
	public Scanner executeFirstScan(Scanner scanner) throws RemoveApplicationException {
		return executeFirstScan(scanner, true);
	}
	
	public Scanner executeFirstScanForClon(Scanner scanner) throws RemoveApplicationException {
		return executeFirstScanForClon(scanner, true);
	}
	
	public Scanner executeFirstScan(Scanner scanner, boolean async) throws RemoveApplicationException {
		log.info("Executing first Scan (scanner: "+scanner.getUuid() + " ID: "+scanner.getId()+ ",async="+async+") at: " + RemoveDateUtils.nowLocalDateTime());
		ClientAccessValidator clientValidator = ClientAccessValidator.init(this.clientUUID);
		
		Client client = clientValidator.validateClientAccess();
		clientValidator.validateReadOnly();
		
		ScannerDao scannerDao = ScannerDao.init();
		
		Scanner _scanner = scannerDao.save(scanner);
		scannerDao.updateStatus(scanner, EScannerStatus.EXECUTING);
		
		if (async) {
			Thread thread = new Thread(){
				public void run(){
					try {
						log.info("Starting Scanner Thread (scanner: "+_scanner.getUuid() + ") at: " + RemoveDateUtils.nowLocalDateTime());
						// Thread.sleep(30l * 1000l);
						executeFirstScanProcess(client, _scanner);
						updateCreditsRemainingUsed();
						log.info("Finishing Scanner Thread (scanner: "+_scanner.getUuid() + ") at: " + RemoveDateUtils.nowLocalDateTime());
					// } catch (RemoveApplicationException | InterruptedException e) {
					} catch (Throwable e) {
						log.error("Error scanning: " + _scanner.getUuid(), e);
					}
				}
			};
			
			thread.start();
			
//			scanner.setExecuting(true);
			return scanner;
		} else {
			scanner = executeFirstScanProcess(client, _scanner);
			updateCreditsRemainingUsed();
			return scanner;
		}
	}
	
	public Scanner executeFirstScanForClon(Scanner scanner, boolean async) throws RemoveApplicationException {
		log.info("Executing first Scan (scanner: "+scanner.getUuid() + " ID: "+scanner.getId()+ ",async="+async+") at: " + RemoveDateUtils.nowLocalDateTime());
		ClientAccessValidator clientValidator = ClientAccessValidator.init(this.clientUUID);
		
		Client client = clientValidator.validateClientAccess();
		clientValidator.validateReadOnly();
		
		ScannerDao scannerDao = ScannerDao.init();
		
		Scanner _scanner = scannerDao.saveForClon(scanner);
		scannerDao.updateStatus(scanner, EScannerStatus.EXECUTING);
		
		if (async) {
			Thread thread = new Thread(){
				public void run(){
					try {
						log.info("Starting Scanner Thread (scanner: "+_scanner.getUuid() + ") at: " + RemoveDateUtils.nowLocalDateTime());
						// Thread.sleep(30l * 1000l);
						executeFirstScanProcess(client, _scanner);
						updateCreditsRemainingUsed();
						log.info("Finishing Scanner Thread (scanner: "+_scanner.getUuid() + ") at: " + RemoveDateUtils.nowLocalDateTime());
					// } catch (RemoveApplicationException | InterruptedException e) {
					} catch (Throwable e) {
						log.error("Error scanning: " + _scanner.getUuid(), e);
					}
				}
			};
			
			thread.start();
			
//			scanner.setExecuting(true);
			return scanner;
		} else {
			scanner = executeFirstScanProcess(client, _scanner);
			updateCreditsRemainingUsed();
			return scanner;
		}
	}
	
	public void runSubsecuentScannersAsync(Scanner scanner) throws RemoveApplicationException {
		Thread thread = new Thread(){
			public void run(){
				try {
					runSubsecuentScanners(scanner);
				} catch (Throwable e) {
					log.error("Error scanning: " + scanner.getUuid(), e);
				}
			}
		};
		
		thread.start();
	}
	public void runSubsecuentScanners(Scanner scanner) throws RemoveApplicationException {
		try {
			log.info("Starting Scanner Thread (scanner: "+scanner.getUuid() + ") at: " + RemoveDateUtils.nowLocalDateTime());
			runSubsecuentScannersProcess(scanner);
			updateCreditsRemainingUsed();
			ScannerResultViewDao.init().refreshViewAsync(scanner.getUuid(), scanner.getType());
			log.info("Finishing Scanner Thread (scanner: "+scanner.getUuid() + ") at: " + RemoveDateUtils.nowLocalDateTime());
		} catch (Throwable e) {
			log.error("Error scanning: " + scanner.getUuid(), e);
		}
	}
	
	private void runSubsecuentScannersProcess(Scanner scanner) throws RemoveApplicationException {
		ScannerDao scannerDao = ScannerDao.init();
		
		try {
			if (!scannerDao.checkExecuting(scanner.getId())) {
				this.recurrency = scanner.getTotal_scans() + 1;
				log.info("Subsecuent scanning: "+scanner.getUuid() + " - Results: "+ (scanner.getResults() != null ? scanner.getResults().size() : null));
				
				ClientAccessValidator clientValidator = ClientAccessValidator.init(scanner.getId_owner());
				Client client = clientValidator.validateClientAccess();
//				clientValidator.validateReadOnly();		// Cambio (28/07/2022) (ticket SYMR-13) Solo se bloqueara si el cliente esta inactivo
				
//				log.info("After Merge: "+scan.getUuid() + " - Results: "+ (scan.getResults() != null ? scan.getResults().size() : null));
				// Commented change of status executing
//				scannerDao.updateStatus(scanner, EScannerStatus.EXECUTING);
				scannerDao.upsertResults(search(scanner));
//				scannerDao.updateStatus(scanner, EScannerStatus.ACTIVE);
				scanner.setTotal_scans(this.recurrency);
				scannerDao.updateTotalScans(scanner);
				
				ClientNotificationHelper.init(client).createNewAndLeavingContentNotification(scanner, this.scanTime);

				// TODO: This code is in ScannerResultRawDao... Move to a Helper... And do just with last result? 
				if (scanner != null) {
					scanner.setResults(ScannerResultDao.init().list(scanner.getId()));
				}
				
				scanner.getResults().forEach(res -> {
					try {
						res.setRaws(ScannerResultRawDao.init().listByResult(res.getId()));
						RemoveFormula.processResult(scanner, res);
						
						res.getRaws().forEach(_raw -> {
							try {
								ScannerResultRawDao.init().updateRatingsValues(_raw);
							} catch (Throwable e) {
								log.error("Error updating raw results for: " + _raw, e);
							}	
						});
						
						ScannerResultDao.init().updateScores(res);
						
						if (scanner.esTransform()) {
							ScannerResultDao.init().updateProgress(res.getId(), res.getProgress());
						}
					} catch (Throwable e) {
						log.error("Error recalcualting results for: " + res, e);
					}	
				});
			} else {
				log.warn("Scanner is already in process: " + scanner.getUuid());
				scannerDao.updateStatus(scanner, EScannerStatus.ACTIVE); // FIX ME: Should change status to prevent locks?
			}
		} catch (RemoveApplicationException ex) {
//			scannerDao.updateStatus(scanner, EScannerStatus.ACTIVE);
			throw ex;
		}
	}
	
	private Scanner executeFirstScanProcess(Client client, Scanner scanner) throws RemoveApplicationException {
		ScannerDao scannerDao = ScannerDao.init();
		
		try {
			scanner.setResults(null);
			scanner.setId_owner(client.getId());
			
			generateStartingNotification(scanner);
			scanner = search(scanner);
			
			generateFinishNotification(scanner);
			scanner = scannerDao.saveResults(scanner);
			scannerDao.updateStatus(scanner, EScannerStatus.ACTIVE);
			ScannerResultViewDao.init().refreshViewAsync(scanner.getUuid(), scanner.getType());
			
			log.info("Total Credits Consumed: " + getTotalCalls());
			
			if (scanner.getType().equals(EScannerTypes.ONE_SHOT.getCode())) {
				scannerDao.deactivate(scanner.getId());
				ClientPlan clientPlan = ClientPlanDao.init().getById(scanner.getId_client_plan());
				clientPlan.addCreditsUsed(1);
//				clientPlan.addCreditsUsed(getTotalCalls());
				log.info("Updating Credits Consumed (" + clientPlan.getId() + ") to: " + clientPlan.getCredits_used());
				ClientPlanDao.init().refreshCredits(clientPlan);
				
				ClientNotificationHelper.init(client).createOneShotLimitCreditsRunningOutNotification(clientPlan);
			}
		} catch (RemoveApplicationException ex) {
			scannerDao.updateStatus(scanner, EScannerStatus.ACTIVE);
			throw ex;
		}
		
		return scanner;
	}

	private Scanner search(Scanner scanner) {
		log.info("Searching scanner: " + scanner.getUuid());
		scanner.getConfiguration().getCountries().forEach(country -> {
			log.info("(" + scanner.getUuid() + ") Searching countries: "+ country);
			scanner.getKeywords().forEach(keyword -> {
				log.info("(" + scanner.getUuid() + ") Searching keywords: "+ keyword);								
					searchKeyword(scanner, country, keyword);
					
				if (keyword.getListSuggested() != null && !keyword.getListSuggested().isEmpty()) {
					keyword.getListSuggested().forEach(sugKeyword -> {
						log.info("(" + scanner.getUuid() + ") Searching suggested keywords: "+ keyword);
						if (sugKeyword.isChecked()) {
							searchKeyword(scanner, country, sugKeyword);	
						}
					});
				}
			});
		});
		
//		scanner.removeRepeatedTracksForLoad();
//		printScannerResultsFound(scanner);
		
		return scanner;
	}
	
	private void generateStartingNotification(Scanner scanner) {
		try {
			Client client = ClientDao.init().getByUuid(this.clientUUID);
			
			if (scanner.getType().equals(EScannerTypes.ONE_SHOT.getCode())) {
				ClientNotificationHelper.init(client).createOneShotStartingScannerNotification(scanner.getUuid(), scanner.getName());
			} else if (scanner.getType().equals(EScannerTypes.MONITOR.getCode())) {
				ClientNotificationHelper.init(client).createRecurrentStartingScannerNotification(scanner.getUuid(), scanner.getName());
			} else if (scanner.getType().equals(EScannerTypes.TRANSFORM.getCode())) {
				ClientNotificationHelper.init(client).createTransformStartingScannerNotification(scanner.getUuid(), scanner.getName());
			}
		} catch (RemoveApplicationException e) {
			log.error("Error creating notification for: " + scanner, e);
		}
	}
	private void generateFinishNotification(Scanner scanner) {
		try {
			if (scanner.getType().equals(EScannerTypes.ONE_SHOT.getCode())) {
				Client client = ClientDao.init().getByUuid(this.clientUUID);
				ClientNotificationHelper.init(client).createOneShotFinishedScannerNotification(scanner.getUuid(), scanner.getName());
			}
		} catch (RemoveApplicationException e) {
			log.error("Error creating notification for: " + scanner, e);
		}
	}

	private void searchKeyword(Scanner scanner, Country country, ScannerKeyword keyword) {
		if (scanner.getConfiguration().getSearch_type().contains(ESearchTypes.WEB.getCode())) {
			try {
				searchWeb(scanner, keyword, country);
			} catch (RemoveApplicationException e) {
				log.error("Error Searching Web: ", e);
			}
		}
		if (scanner.getConfiguration().getSearch_type().contains(ESearchTypes.NEWS.getCode())) {
			try {
				if (scanner.getConfiguration().getDevice().equals("desktop")) {
					searchNews(scanner, keyword, country);					
				} else {
					log.warn("Search type not supported: mobile/news for: " + scanner.getUuid());
				}
			} catch (RemoveApplicationException e) {
				log.error("Error Searching News: ", e);
			}
		}
		if (scanner.getConfiguration().getSearch_type().contains(ESearchTypes.IMAGES.getCode())) {
			try {
				searchImages(scanner, keyword, country);
			} catch (RemoveApplicationException e) {
				log.error("Error Searching Images: ", e);
			}
		}
	}
	
	private void searchWeb(Scanner scanner, ScannerKeyword keyword, Country country) throws RemoveApplicationException {
		ScannerResult result = new ScannerResult();
		result.setCity(scanner.getConfiguration().getCities());
		result.setDevice(scanner.getConfiguration().getDevice());
		result.setId_country(country.getId());
		result.setId_keyword(keyword.getId());
		result.setId_scanner(scanner.getId());
		result.setLanguage(scanner.getConfiguration().getLanguage());
		result.setQuery_date(this.scanTime);
		result.setSearch_type(ESearchTypes.WEB.getCode());
		result = findOldResult(scanner, result);
		result.cleanRaws();
		
		RemoveSERPRequest req = new RemoveSERPRequest();
		
		req.setQ(keyword.getWord());
		req.setDevice(scanner.getConfiguration().getDevice());
		
		if (country.getTag().equalsIgnoreCase(ECountries.SPAIN.getCode())) {
			req.setLocation(ECountries.SPAIN.getCountry());
		} else {
			req.setGl(country.getTag());					// Alterate order results in search
			req.setGoogle_domain(country.getFullDomain());
		}
		
		if(!scanner.getConfiguration().getLanguage().equals(ELanguages.NOLANGUAGE.getCode())) {
			req.setHl(scanner.getConfiguration().getLanguage());
		}else {
			req.setHl(ELanguages.NOLANGUAGE.getCode());
		}
		// req.setLocation(location);
		// req.setOutput(output);
		// req.setPage("1");
		 req.setNum("10");
		
		//req.setMax_page(String.valueOf(scanner.getConfiguration().getPages().intValue()));
//			String nextPage = null;
//			for(int  i= 1; i <= scanner.getConfiguration().getPages().intValue(); i++) {
//				req.setPage(String.valueOf(i));
				
//				RemoveResponse scaleSerpResponse = nextPage == null ? RemoveSERPEngine.init().search(req) : RemoveSERPEngine.init().search(nextPage);
				//RemoveResponse scaleSerpResponse = RemoveSERPEngine.init().search(req);
				
//				if (scaleSerpResponse.isSuccess() && scaleSerpResponse.getData() != null) {
//					RemoveSERPWebResponse serpResponse = (RemoveSERPWebResponse) RemoveJsonUtil.jsonToData(RemoveJsonUtil.dataToJson(scaleSerpResponse.getData()), RemoveSERPWebResponse.class);	
//					nextPage = serpResponse.getPagination() != null && serpResponse.getPagination().getApi_pagination() != null ?
//							serpResponse.getPagination().getApi_pagination().getNext() : null;
//				}
				
				//if (!processResponseWeb(scanner, keyword, country, scaleSerpResponse, total, result)) { 
					//log.warn("Not resoult found in WEB: " + keyword);
//					break;
				//}
//			}
		
		AtomicInteger total = new AtomicInteger(1);
		
		for(int  i= 1; i <= scanner.getConfiguration().getPages().intValue(); i++) {
			req.setPage(String.valueOf(i));
			
			if (!processResponseWeb(scanner, keyword, country, RemoveSERPEngine.init().search(req), i, total, result)) { 
				break;
			}
		}

		
		scanner.addResult(result);
	}
	private void searchNews(Scanner scanner, ScannerKeyword keyword, Country country) throws RemoveApplicationException {
		ScannerResult result = new ScannerResult();
		
		result.setCity(scanner.getConfiguration().getCities());
		result.setDevice(scanner.getConfiguration().getDevice());
		result.setId_country(country.getId());
		result.setId_keyword(keyword.getId());
		result.setId_scanner(scanner.getId());
		result.setLanguage(scanner.getConfiguration().getLanguage());
		result.setQuery_date(this.scanTime);
		result.setSearch_type(ESearchTypes.NEWS.getCode());
		result = findOldResult(scanner, result);
		result.cleanRaws();
		
		RemoveSERPRequest req = new RemoveSERPRequest();
		
		req.setQ(keyword.getWord());
		req.setDevice(scanner.getConfiguration().getDevice());
		
		if (country.getTag().equalsIgnoreCase(ECountries.SPAIN.getCode())) {
			req.setLocation(ECountries.SPAIN.getCountry());
		} else {
			req.setGl(country.getTag());					// Alterate order results in search
			req.setGoogle_domain(country.getFullDomain());
		}
		
		if(!scanner.getConfiguration().getLanguage().equals(ELanguages.NOLANGUAGE.getCode())) {
			req.setHl(scanner.getConfiguration().getLanguage());
		}else {
			req.setHl(ELanguages.NOLANGUAGE.getCode());
		}

		// req.setLocation(location);
		// req.setOutput(output);
//		req.setPage("1");
		req.setNum("10");
		//req.setMax_page(String.valueOf(scanner.getConfiguration().getPages().intValue()));
		req.setSearch_type(ESearchTypes.NEWS.getCode());
		req.setNews_type(scanner.getConfiguration().getNews_type());
		
		AtomicInteger total = new AtomicInteger (1);
//		String nextPage = null;
		
//		for(int  i= 1; i <= scanner.getConfiguration().getPages().intValue(); i++) {
//			req.setPage(String.valueOf(i));
			
//			RemoveResponse scaleSerpResponse = nextPage == null ? RemoveSERPEngine.init().search(req) : RemoveSERPEngine.init().search(nextPage);
			//RemoveResponse scaleSerpResponse = RemoveSERPEngine.init().search(req);
			
//			if (scaleSerpResponse.isSuccess() && scaleSerpResponse.getData() != null) {
//				RemoveSERPWebResponse serpResponse = (RemoveSERPWebResponse) RemoveJsonUtil.jsonToData(RemoveJsonUtil.dataToJson(scaleSerpResponse.getData()), RemoveSERPWebResponse.class);	
//				nextPage = serpResponse.getPagination() != null && serpResponse.getPagination().getApi_pagination() != null && serpResponse.getPagination().getApi_pagination().getNext() != null ?
//						serpResponse.getPagination().getApi_pagination().getNext() : null;
//			}
			
			//if (!processResponseNews(scanner, keyword, country, scaleSerpResponse, total, result)) {
				//log.warn("Not resoult found in News: " + keyword);
//				break;
			//}
//		}
		
		for(int  i= 1; i <= scanner.getConfiguration().getPages().intValue(); i++) {
			req.setPage(String.valueOf(i));
			if (!processResponseNews(scanner, keyword, country, RemoveSERPEngine.init().search(req), i, total, result)) {
				break;
			}
		}
		scanner.addResult(result);
	}
	private void searchImages(Scanner scanner, ScannerKeyword keyword, Country country) throws RemoveApplicationException {
		ScannerResult result = new ScannerResult();
		
		result.setId_country(country.getId());
		result.setDevice(scanner.getConfiguration().getDevice());
		result.setId_keyword(keyword.getId());
		result.setLanguage(scanner.getConfiguration().getLanguage());
		result.setSearch_type(ESearchTypes.IMAGES.getCode());
		result.setCity(scanner.getConfiguration().getCities());
		result.setId_scanner(scanner.getId());
		result.setQuery_date(this.scanTime);
		
		result = findOldResult(scanner, result);
		result.cleanRaws();
//		String nextPage = null;
		
//		for(int page = 1; page <= scanner.getConfiguration().getPages(); page++) {
			RemoveSERPRequest req = new RemoveSERPRequest();
			
			req.setQ(keyword.getWord());
			req.setDevice(scanner.getConfiguration().getDevice());

			if (country.getTag().equalsIgnoreCase(ECountries.SPAIN.getCode())) {
				req.setLocation(ECountries.SPAIN.getCountry());
			} else {
				req.setGl(country.getTag());					// Alterate order results in search
				req.setGoogle_domain(country.getFullDomain());
			}
			
			if(!scanner.getConfiguration().getLanguage().equals(ELanguages.NOLANGUAGE.getCode())) {
				req.setHl(scanner.getConfiguration().getLanguage());
			}else {
				req.setHl(ELanguages.NOLANGUAGE.getCode());
			}
			// req.setLocation(location);
			// req.setOutput(output);
			//req.setMax_page(String.valueOf(scanner.getConfiguration().getPages().intValue()));v1
			
			//req.setSearch_type(ESearchTypes.IMAGES.getCode());v1
//			req.setImages_page(String.valueOf(page));
			//req.setImages_color(scanner.getConfiguration().getImages_color());v1
			//req.setImages_size(scanner.getConfiguration().getImages_size());v1
			//req.setImages_type(scanner.getConfiguration().getImages_type());v1
			//req.setImages_usage(scanner.getConfiguration().getImages_use_rights());v1
			
//			RemoveResponse scaleSerpResponse = nextPage == null ? RemoveSERPEngine.init().search(req) : RemoveSERPEngine.init().search(nextPage);
			//RemoveResponse scaleSerpResponse = RemoveSERPEngine.init().search(req);v1
			
//			if (scaleSerpResponse.isSuccess() && scaleSerpResponse.getData() != null) {
//				RemoveSERPWebResponse serpResponse = (RemoveSERPWebResponse) RemoveJsonUtil.jsonToData(RemoveJsonUtil.dataToJson(scaleSerpResponse.getData()), RemoveSERPWebResponse.class);	
				
//				nextPage = serpResponse.getPagination() != null && serpResponse.getPagination().getApi_pagination() != null && serpResponse.getPagination().getApi_pagination().getNext() != null ?
//						serpResponse.getPagination().getApi_pagination().getNext() : null;
//			}
			
			//if (!processResponseImage(scanner, keyword, country, scaleSerpResponse, result)) {v1
				//log.warn("Not result found in Image: " + keyword);v1
//				break;
			//}v1
//		}
			for(int page = 1; page <= scanner.getConfiguration().getPages(); page++) {
				//RemoveSERPRequest req = new RemoveSERPRequest();v1
				
				req.setQ(keyword.getWord());
				req.setDevice(scanner.getConfiguration().getDevice());
				req.setGl(country.getTag());
				req.setGoogle_domain(country.getFullDomain());
				req.setHl(scanner.getConfiguration().getLanguage());
				// req.setLocation(location);
				// req.setOutput(output);
				
				req.setSearch_type(ESearchTypes.IMAGES.getCode());
				req.setImages_page(String.valueOf(page));
				req.setImages_color(scanner.getConfiguration().getImages_color());
				req.setImages_size(scanner.getConfiguration().getImages_size());
				req.setImages_type(scanner.getConfiguration().getImages_type());
				req.setImages_usage(scanner.getConfiguration().getImages_use_rights());
				
				if (!processResponseImage(scanner, keyword, country, RemoveSERPEngine.init().search(req), page, result)) {
					break;
				}
			}
		scanner.addResult(result);
	}
	
	/*
	 * private boolean processResponseWeb(Scanner scanner, ScannerKeyword keyword, Country country, RemoveResponse resp, 
			AtomicInteger positionInTotal, ScannerResult result)
	 * */
	private boolean processResponseWeb(Scanner scanner, ScannerKeyword keyword, Country country, RemoveResponse resp, 
			int page, AtomicInteger positionInTotal, ScannerResult result) throws RemoveApplicationException {
		if (resp.isSuccess() && resp.getData() != null) {
			incTotalCalls();
									
			ScannerResultRaw raw = new ScannerResultRaw(scanner.getId(), this.scanTime, this.recurrency, ESearchTypes.WEB.getCode(), RemoveJsonUtil.dataToJson(resp.getData()));
			raw.setId_scanner_result(result.getId());
			result.getRaws().add(raw);
			
			RemoveSERPWebResponse serpResponse = (RemoveSERPWebResponse) RemoveJsonUtil.jsonToData(raw.getRaw(), RemoveSERPWebResponse.class);
			
			if (serpResponse != null && serpResponse.getOrganic_results() != null && !serpResponse.getOrganic_results().isEmpty()) {
				log.info("Total Webs Found:" + serpResponse.getOrganic_results().size());
				for (RemoveSERPWebOrganicResults organicResult : serpResponse.getOrganic_results()) {
					//processWebSnippet(scanner, result, keyword, country, organicResult, raw.getUuid(), positionInTotal.getAndIncrement());
					processWebSnippet(scanner, result, keyword, country, organicResult, raw.getUuid(), page, positionInTotal.getAndIncrement());
				}
				
				this.creditsRemaining = serpResponse.getRequest_info().getCredits_remaining();
				this.expirationDate = serpResponse.getRequest_info().getCredits_reset_at();
				this.consumed = serpResponse.getRequest_info().getCredits_used().longValue();
			} else {
				this.creditsRemaining = serpResponse.getRequest_info().getCredits_remaining();
				this.expirationDate = serpResponse.getRequest_info().getCredits_reset_at();
				this.consumed = serpResponse.getRequest_info().getCredits_used().longValue();
				
				return false;
			}
		}
		
		return true;
	}
	
	/*
	 * private boolean processResponseNews(Scanner scanner, ScannerKeyword keyword, Country country, RemoveResponse resp, 
			AtomicInteger positionInTotal, ScannerResult result)
	 * */
	private boolean processResponseNews(Scanner scanner, ScannerKeyword keyword, Country country, RemoveResponse resp, 
			int page, AtomicInteger positionInTotal, ScannerResult result) throws RemoveApplicationException {
		if (resp.isSuccess() && resp.getData() != null) {
			incTotalCalls();
			
			ScannerResultRaw raw = new ScannerResultRaw(scanner.getId(), this.scanTime, this.recurrency, ESearchTypes.NEWS.getCode(), RemoveJsonUtil.dataToJson(resp.getData()));
			raw.setId_scanner_result(result.getId());
			result.getRaws().add(raw);
			
			RemoveSERPWebResponse serpResponse = (RemoveSERPWebResponse) RemoveJsonUtil.jsonToData(raw.getRaw(), RemoveSERPWebResponse.class);
			
			
			if (serpResponse != null && serpResponse.getNews_results() != null && !serpResponse.getNews_results().isEmpty()) {
				log.info("Total News Found:" + serpResponse.getNews_results().size());
				for (RemoveSERPNewsResults newsResults: serpResponse.getNews_results()) {
					//processNewsSnippet(scanner, result, keyword, country, newsResults, raw.getUuid(), positionInTotal.getAndIncrement());
					processNewsSnippet(scanner, result, keyword, country, newsResults, raw.getUuid(), page, positionInTotal.getAndIncrement());
				}
				
				this.creditsRemaining = serpResponse.getRequest_info().getCredits_remaining();
				this.expirationDate = serpResponse.getRequest_info().getCredits_reset_at();
				this.consumed = serpResponse.getRequest_info().getCredits_used().longValue();
			} else {
				this.creditsRemaining = serpResponse.getRequest_info().getCredits_remaining();
				this.expirationDate = serpResponse.getRequest_info().getCredits_reset_at();
				this.consumed = serpResponse.getRequest_info().getCredits_used().longValue();
				
				return false;
			}
		}
		
		return true;
	}
	
	/*
	 * private boolean processResponseImage(Scanner scanner, ScannerKeyword keyword, Country country, RemoveResponse resp, ScannerResult result)
	 * */
	private boolean processResponseImage(Scanner scanner, ScannerKeyword keyword, Country country, RemoveResponse resp, int page, ScannerResult result) throws RemoveApplicationException {
		if (resp.isSuccess() && resp.getData() != null) {
			incTotalCalls();
			
			ScannerResultRaw raw = new ScannerResultRaw(result.getId(), this.scanTime, this.recurrency, ESearchTypes.IMAGES.getCode(), RemoveJsonUtil.dataToJson(resp.getData()));
			raw.setId_scanner_result(result.getId());
			result.getRaws().add(raw);
			
			RemoveSERPWebResponse serpResponse = (RemoveSERPWebResponse) RemoveJsonUtil.jsonToData(raw.getRaw(), RemoveSERPWebResponse.class);
			
			if (serpResponse != null && serpResponse.getImage_results() != null && !serpResponse.getImage_results().isEmpty()) {
				log.info("Total Images Found:" + serpResponse.getImage_results().size());
				//processImageSnippet(scanner, result, keyword, country, imageResults, serpResponse.getImage_results().size(), raw.getUuid()
				serpResponse.getImage_results().forEach(imageResults -> processImageSnippet(scanner, result, keyword, country, imageResults, serpResponse.getImage_results().size(), page, raw.getUuid()));
				
				this.creditsRemaining = serpResponse.getRequest_info().getCredits_remaining();
				this.expirationDate = serpResponse.getRequest_info().getCredits_reset_at();
				this.consumed = serpResponse.getRequest_info().getCredits_used().longValue();
			} else {
				this.creditsRemaining = serpResponse.getRequest_info().getCredits_remaining();
				this.expirationDate = serpResponse.getRequest_info().getCredits_reset_at();
				this.consumed = serpResponse.getRequest_info().getCredits_used().longValue();
				return false;
			}
		}
		
		return true;
	}
	
	/*
	 * 	private void processWebSnippet(Scanner scanner, ScannerResult result, ScannerKeyword keyword, 
			Country country, RemoveSERPWebOrganicResults organicResult, String uuidRaw,
			Integer positionInTotal)
	 * */
	private void processWebSnippet(Scanner scanner, ScannerResult result, ScannerKeyword keyword, 
			Country country, RemoveSERPWebOrganicResults organicResult, String uuidRaw,
			int page, Integer positionInTotal) {
//		log.info("WEB Snippet to process: '" + organicResult.getTitle() + "' - " + organicResult.getSnippet() + " - " + organicResult.getPosition());
		ScannerResultWebSnippet snippet = new ScannerResultWebSnippet();
		snippet.setDisplayed_link(organicResult.getDisplayed_link());
		snippet.setDomain(organicResult.getDomain());
		snippet.setLink(organicResult.getLink());
		snippet.setSnippet(organicResult.getSnippet());
		snippet.setTitle(organicResult.getTitle());
		snippet.setDate(organicResult.getDate());
		snippet.setDate_utc(RemoveDateUtils.parseFromSerp(organicResult.getDate_utc()));
		snippet.setUuidRaw(uuidRaw);
		snippet.setNested_results(organicResult.getNested_results());
		
		// Check for tracking words ;
		//processWebTracking(snippet, organicResult, positionInTotal);
		processWebTracking(snippet, organicResult, page, positionInTotal);
		HtmlInspectorHelper.inspect(snippet, scanner.getTrackingWords());
		

		
		if (scanner.esTransform()) {
			try {
				ScannerImpulseDao.init().verifyImpulseInitialPosition(scanner.getId(), keyword.getId(), snippet.getLink(), snippet.getTracking().get(0).getPosition());
			} catch (RemoveApplicationException e) {
				log.error("Error updating impulse initial position: " + snippet, e);
			}
		}
		
		result.getSnippetsWebs().add(snippet);
	}
	
	/*
	 * (Scanner scanner, ScannerResult result, ScannerKeyword keyword, Country country,
			RemoveSERPNewsResults newsResult, String uuidRaw, Integer positionInTotal)
	 * */
	private void processNewsSnippet(Scanner scanner, ScannerResult result, ScannerKeyword keyword, Country country,
			RemoveSERPNewsResults newsResult, String uuidRaw, Integer page, Integer positionInTotal) {
//		log.info("News Snippet to process: '" + newsResult.getTitle() + "' - " + newsResult.getSnippet() + " - " + newsResult.getPosition());
		
		ScannerResultNewsSnippet snippet = new ScannerResultNewsSnippet();
		snippet.setDomain(newsResult.getDomain());
		snippet.setLink(newsResult.getLink());
		snippet.setSnippet(newsResult.getSnippet());
		snippet.setTitle(newsResult.getTitle());
		snippet.setDate(newsResult.getDate());
		snippet.setDate_utc(RemoveDateUtils.parseFromSerp(newsResult.getDate_utc()));
		snippet.setThumbnail(newsResult.getThumbnail());
		snippet.setSource(newsResult.getSource());
		snippet.setUuidRaw(uuidRaw);
		
		// Check for tracking words ;
		//processNewsTracking(snippet, newsResult, positionInTotal);
		processNewsTracking(snippet, newsResult, page, positionInTotal);
		HtmlInspectorHelper.inspect(snippet, scanner.getTrackingWords());
		
		if (scanner.esTransform()) {
			try {
				ScannerImpulseDao.init().verifyImpulseInitialPosition(scanner.getId(), keyword.getId(), snippet.getLink(), snippet.getTracking().get(0).getPosition());
			} catch (RemoveApplicationException e) {
				log.error("Error updating impulse initial position: " + snippet, e);
			}
		}
		
		result.getSnippetsNews().add(snippet);
	}
	
	/*
	 * (Scanner scanner, ScannerResult result, ScannerKeyword keyword, Country country,
			RemoveSERPImageResults imageResults, int total, String uuidRaw)
	 * */
	private void processImageSnippet(Scanner scanner, ScannerResult result, ScannerKeyword keyword, Country country,
			RemoveSERPImageResults imageResults, int total, int page, String uuidRaw) {
//		log.info("Images Snippet to process: '" + imageResults.getTitle() + "' - " + imageResults.getDescription() + " - " + imageResults.getPosition());
		
		ScannerResultImageSnippet snippet = new ScannerResultImageSnippet();
		snippet.setDomain(imageResults.getDomain());
		snippet.setLink(imageResults.getLink());
		snippet.setBrand(imageResults.getBrand());
		snippet.setTitle(imageResults.getTitle());
		snippet.setDescription(imageResults.getDescription());
		snippet.setHeight(imageResults.getHeight());
		snippet.setWidth(imageResults.getWidth());
		snippet.setImage(imageResults.getImage());
		snippet.setUuidRaw(uuidRaw);
		
		// Check for tracking words ;
		//processImagesTracking(snippet, imageResults, total);
		processImagesTracking(snippet, imageResults, total, page);
		HtmlInspectorHelper.inspect(snippet, scanner.getTrackingWords());
		
		if (scanner.esTransform()) {
			try {
				ScannerImpulseDao.init().verifyImpulseInitialPosition(scanner.getId(), keyword.getId(), snippet.getLink(), snippet.getTracking().get(0).getPosition());
			} catch (RemoveApplicationException e) {
				log.error("Error updating impulse initial position: " + snippet, e);
			}
		}
		
		result.getSnippetsImages().add(snippet);
	}
	
	/*
	 *	private void processWebTracking(ScannerResultWebSnippet snippet, RemoveSERPWebOrganicResults organicResult, 
			Integer positionInTotal)	 
	 * */
	private void processWebTracking(ScannerResultWebSnippet snippet, RemoveSERPWebOrganicResults organicResult, 
			int page, Integer positionInTotal) {
		ScannerResultWebSnippetTrack track = new ScannerResultWebSnippetTrack();
		
		track.setDate_scan(this.scanTime);
		track.setPosition(positionInTotal.intValue());
		//track.setPage(organicResult.getPage());
		track.setPage(page);
		track.setPosition_in_page(organicResult.getPosition());
		
		snippet.getTracking().add(track);
	}
	
	/*
	 * 	private void processNewsTracking(ScannerResultNewsSnippet snippet, RemoveSERPNewsResults newsResult, 
			Integer positionInTotal)
	 * */
	private void processNewsTracking(ScannerResultNewsSnippet snippet, RemoveSERPNewsResults newsResult, 
			Integer page, Integer positionInTotal) {
		ScannerResultNewsSnippetTrack track = new ScannerResultNewsSnippetTrack();
		
		track.setDate_scan(this.scanTime);
		track.setPosition(positionInTotal.intValue());
		//track.setPage(newsResult.getPage());
		track.setPage(page);
		track.setPosition_in_page(newsResult.getPosition());
		
		snippet.getTracking().add(track);
	}
	
	/*
	 * private void processImagesTracking(ScannerResultImageSnippet snippet, RemoveSERPImageResults imageResults,
			int total)
	 * */
	private void processImagesTracking(ScannerResultImageSnippet snippet, RemoveSERPImageResults imageResults,
			int total, Integer page) {
		ScannerResultImageSnippetTrack track = new ScannerResultImageSnippetTrack();
		
		track.setDate_scan(this.scanTime);
		track.setPosition(imageResults.getPosition());
		track.setPosition_in_page(imageResults.getPosition());
		//track.setPage(imageResults.getPage());
		track.setPage(page);
		
		snippet.getTracking().add(track);
	}
	
	private void incTotalCalls() {
		this.totalCalls++;
	}
	public int getTotalCalls() {
		return this.totalCalls;
	}

	public Long getCreditsRemaining() {
		return creditsRemaining;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Long getConsumed() {
		return consumed;
	}

	public void setConsumed(Long consumed) {
		this.consumed = consumed;
	}

	private ScannerResult findOldResult(Scanner scanner, ScannerResult newResult) {
		for (ScannerResult result: scanner.getResults()) {
			if (result.same(newResult)) {
				log.info("Id result found: " + result.getUuid() + " as " + result.getId());
				return result;
			}
		}
		log.info("Id result not found: " + newResult.getUuid());
		return newResult;
	}
	
	private void updateCreditsRemainingUsed() {
		try {
			if (this.getTotalCalls() > 0) {
				ConfigurationDao.init().upsertScaleSerpConsumption(this.getTotalCalls());
				ConfigurationDao.init().upsertScaleSerpRemaining(this.getCreditsRemaining());
				ConfigurationDao.init().upsertScaleSerpExpirationDate(this.getExpirationDate());
				ConfigurationDao.init().upsertScaleSerpConsumed(this.getConsumed());
			}
		} catch (RemoveApplicationException e) {
			log.error("Error updating credits used:", e);
		}
	}
	
	private void printScannerResultsFound(Scanner scanner) {
		if (scanner != null) {
			log.info("######### Scanner: " + scanner.getName());
			if (scanner.getResults() != null) {
				scanner.getResults().forEach(res -> {
					log.info("");
					try {
						log.info("##### Result: " + ScannerKeywordsDao.init().get(res.getId_keyword()).getWord() + " | " + res.getSearch_type() + " | " + CountryDao.init().get(res.getId_country()).getName());
					} catch (RemoveApplicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (res.getSnippetsImages() != null) {
						res.getSnippetsImages().forEach(snip -> {
							log.info("### Snipp IMG Found: " + snip.getLink() + " | ");
							
							if (snip.getTracking() != null) {
								snip.getTracking().forEach(track -> {
									log.info("## Track " + track.getPosition() + " | " + track.getPage()+"/"+track.getPosition_in_page());
								});
							}
						});
					}

					if (res.getSnippetsNews() != null) {
						res.getSnippetsNews().forEach(snip -> {
							log.info("### Snipp NEWS Found: " + snip.getLink() + " | ");
							
							if (snip.getTracking() != null) {
								snip.getTracking().forEach(track -> {
									log.info("## Track " + track.getPosition() + " | " + track.getPage()+"/"+track.getPosition_in_page());
								});
							}
						});
					}
					
					if (res.getSnippetsWebs() != null) {
						res.getSnippetsWebs().forEach(snip -> {
							log.info("### Snipp WEB Found: " + snip.getLink() + " | ");
							
							if (snip.getTracking() != null) {
								snip.getTracking().forEach(track -> {
									log.info("## Track " + track.getPosition() + " | " + track.getPage()+"/"+track.getPosition_in_page());
								});
							}
						});
					}
				});
			}
		}
	}
	
	public static void main(String[] args) {
		
		try {
			Scanner scanner = ScannerDao.init().getForRecurrence("b58fc115-147c-4628-b130-19d9d905eb96");
			ScannerHelper.init("047e5ce3-7f0b-4e1b-be4c-3213ca9f582c").runSubsecuentScanners(scanner);
		} catch (RemoveApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
