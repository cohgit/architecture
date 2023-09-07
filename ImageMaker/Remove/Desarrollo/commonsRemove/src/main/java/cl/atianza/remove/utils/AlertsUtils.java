package cl.atianza.remove.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.models.commons.GenericAlert;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.commons.CountryDao;
import cl.atianza.remove.daos.commons.ScannerAlertDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.daos.commons.ScannerKeywordsDao;
import cl.atianza.remove.daos.commons.ScannerResultDao;
import cl.atianza.remove.daos.commons.ScannerResultImageSnippetTrackDao;
import cl.atianza.remove.daos.commons.ScannerResultNewsSnippetTrackDao;
import cl.atianza.remove.daos.commons.ScannerResultViewDao;
import cl.atianza.remove.daos.commons.ScannerResultWebSnippetDao;
import cl.atianza.remove.daos.commons.ScannerResultWebSnippetTrackDao;
import cl.atianza.remove.daos.commons.ScannerTrackingWordsDao;
import cl.atianza.remove.enums.EFeelings;
import cl.atianza.remove.enums.EObjectPageOfAlert;
import cl.atianza.remove.enums.EScannerStatus;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.enums.ESearchTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ClientNotificationHelper;
import cl.atianza.remove.helpers.UserNotificationHelper;
import cl.atianza.remove.helpers.reports.beans.TableRow;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Country;

import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerAlert;
import cl.atianza.remove.models.commons.ScannerKeyword;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.models.commons.ScannerResultImageSnippet;
import cl.atianza.remove.models.commons.ScannerResultImageSnippetTrack;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippet;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippetTrack;
import cl.atianza.remove.models.commons.ScannerResultView;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;
import cl.atianza.remove.models.commons.ScannerResultWebSnippetTrack;
import cl.atianza.remove.models.commons.ScannerTrackingWord;



public class AlertsUtils {
	private static Logger log;
	private Scanner scanner;
	private Scanner scannerTrack;
	private Scanner scannerKey;
	private AlertSectionUtils section = new AlertSectionUtils();
	Integer page = 0;
	Integer pageHtml = 0;
	Scanner scan = null;
	public void alertInitialize() {
		ValidationAlert();
	}
	
	private void ValidationAlert() {
		List<ScannerAlert> lstScannersAlert = null;
		
		try {
			lstScannersAlert = ScannerAlertDao.init().listRecurrentsDaily();
			
			if (lstScannersAlert != null && !lstScannersAlert.isEmpty()) {
				lstScannersAlert.forEach(alert -> {
					try {
						scan = ScannerDao.init().getBasicById(alert.getId_scanner());
						Client client = ClientDao.init().getBasicById(scan.getId_owner());
						
						if(client.isActive() &&  client.isSend_email()) {
							
							if(scan.getStatus().equals(EScannerStatus.ACTIVE.getTag()) && (scan.getType().equals(EScannerTypes.MONITOR.getCode()) || scan.getType().equals(EScannerTypes.TRANSFORM.getCode()))) {
								fillDataParameters(alert);
								try {
									matchingTrack(alert);
									matchingKey(alert);
								} catch (RemoveApplicationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								LoadHtml(alert);
								this.section = new AlertSectionUtils();
							}
						}
						
					} catch (RemoveApplicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
				});
			}
			
		}
		catch(RemoveApplicationException | Exception e) {
				getLog().error("Error Executing tasks ValidationAlert: ", e);
		}
	}
	
	private void LoadHtml(ScannerAlert alert) {

		try {

			Client client = ClientDao.init().getById(scanner.getId_owner());
			
			ClientNotificationHelper notification = ClientNotificationHelper.init(client);
			
			String description = "";
			
			if(alert.getPage_object().equalsIgnoreCase(EObjectPageOfAlert.MONITOR.getCode())) {
				pageHtml = scanner.getConfiguration().getPages();
				description = "Páginas monitoreo configurada en tu servicio: "+pageHtml;
			}else {
				pageHtml = scanner.getRestrictions().getDetail().getTarget_page();
				description = "Páginas objetivo configurada en tu servicio: "+pageHtml;
			}

			String query_date = scanner.getResults() != null && !scanner.getResults().isEmpty() ? RemoveDateUtils.formatDateStringDash( scanner.getResults().get(0).getQuery_date()) : ""; 
			notification.createContentAlertClient(scanner.getName(),description, client.getName() + " "+ client.getLastname(), this.section, client, query_date,alert.getTracking_word(),alert.getKeyword(), alert, scanner.getType());
		} catch (RemoveApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	
    private void fillDataParameters(ScannerAlert alert) {
        try {
        	scanner = ScannerDao.init().getBasicById(alert.getId_scanner());
            scanner = loadScannerInfo(scanner.getUuid());
            
            filterScannerData(alert);
            fillJrParameters();
        } catch (RemoveApplicationException | Exception e) {
            getLog().error("Error building fillDataParameters: ", e);
        }
    }
    
    private void filterScannerData(ScannerAlert alert) {
        getLog().info("Filtering Scanner Data for scanner: " + scanner.getUuid());
        filterSections();
        //filterKeywords(alert);
        //filterTrackingWords(alert);
        filterPages(alert);
        filterNewContent(alert);
        getLog().info("Scanner Data Filtered... ");
    }
    
    private void filterSections() {
    	String[] sec  = scanner.getConfiguration().getSearch_type().split(",");
        scanner.filterResultsBySections(sec);
    }
    /**
     * Filter scanner data by Keywords filter parameter.
     *
     * @param scanner
     */
    private void filterKeywords(ScannerAlert alert) {  
    	String[] key  = alert.getKeyword().split(",");
        scanner.filterResultsByKeywords(key);
    }
    /**
     * Filter scanner Data by Page filter parameter
     *
     * @param scanner
     */
    private void filterPages(ScannerAlert alert) {  
		if(alert.getPage_object().equalsIgnoreCase(EObjectPageOfAlert.MONITOR.getCode())) {
			page = scanner.getConfiguration().getPages();
		}else {
			page = scanner.getRestrictions().getDetail().getTarget_page();
		}
        scanner.filterResultsByPage(page);
    }

    /**
     * Filter scanner Data by Tracking Words filter parameter
     *
     * @param scanner
     */
    private void filterTrackingWords(ScannerAlert alert) {
    	String[] words  = alert.getTracking_word().split(",");
        scanner.filterResultsByTrackingWords(words);
    }
    /**
     * Filter scanner data by Dates filter parameter
     *
     * @param scanner
     */
    private void filterDates(ScannerAlert alert) {
    	LocalDate referenceFirstDate = scanner.getResults().get(0).getQuery_date().toLocalDate();
        scanner.filterResultsByDates(referenceFirstDate,
        		referenceFirstDate);
    }

    /**
     * Filter scanner data by New Content filter parameters
     *
     * @param scanner
     */
    private void filterNewContent(ScannerAlert alert) {
            filterDates(alert);
    }
    
    private void fillJrParameters(){

        fillJrSectionsParameters();

    }
    
    private void fillJrSectionsParameters(){
    	List<GenericAlert> listNew = new ArrayList<GenericAlert>(); 
    	List<GenericAlert> listNegative = new ArrayList<GenericAlert>(); 
    	List<GenericAlert> listKeywordsByURL = new ArrayList<GenericAlert>();
    	
    	
    	if (scanner.getResults() != null && !scanner.getResults().isEmpty()) {
    		LocalDate referenceFirstDate = scanner.getResults().get(0).getQuery_date().toLocalDate();
    		for (ScannerResult result : scanner.getResults()) {
                result.getSnippetsImages().forEach(snippet -> {
                    // Check new content for respective table
                    if (!snippet.getFeeling().equalsIgnoreCase(EFeelings.NOT_APPLIED.getTag())
                            && this.isNewContent(snippet.getTracking().get(0).getDate_scan(), referenceFirstDate)) {
                        pushNewNegativeContent(listNew, result, snippet);
                    }

                    // Check negative content for respective table
                    if (snippet.getFeeling().equalsIgnoreCase(EFeelings.BAD.getTag())) {
                        pushNewNegativeContent(listNegative, result, snippet);
                    }

                    // Checking TrackingWords against Snippets
                    scanner.getTrackingWords().forEach(trackingWord -> {
                        if (trackingWord.esURL()) {
                            if (snippet.getLink().equals(trackingWord.getWord())) {
                                pushKeywordByContent(listKeywordsByURL, result, snippet);
                            }
                        }
                    });

                    /*if (scanner.getTrackingWords() != null && !scanner.getTrackingWords().isEmpty() &&
                            snippet.containsTrackingWord(scanner.justTrackingWordsTitles().toArray(new String[scanner.justTrackingWordsTitles().size()]))) {
                        pushKeywordByContent(listKeywordsByTW, result, snippet);
                        pushKeywordByContent(listKeyword, result, snippet);
                    }*/

                });
                
                result.getSnippetsNews().forEach(snippet -> {
                    // Check new content for respective table
                    if (!snippet.getFeeling().equalsIgnoreCase(EFeelings.NOT_APPLIED.getTag())
                            && this.isNewContent(snippet.getTracking().get(0).getDate_scan(), referenceFirstDate)) {
                        pushNewNegativeContent(listNew, result, snippet);
                    }

                    // Check negative content for respective table
                    if (snippet.getFeeling().equalsIgnoreCase(EFeelings.BAD.getTag())) {
                        pushNewNegativeContent(listNegative, result, snippet);
                    }

                    // Checking TrackingWords against Snippets
                    scanner.getTrackingWords().forEach(trackingWord -> {
                        if (trackingWord.esURL()) {
                            if (snippet.getLink().equals(trackingWord.getWord())) {
                                pushKeywordByContent(listKeywordsByURL, result, snippet);
                            }
                        }
                    });

                    /*if (scanner.getTrackingWords() != null && !scanner.getTrackingWords().isEmpty() &&
                            snippet.containsTrackingWord(scanner.justTrackingWordsTitles().toArray(new String[scanner.justTrackingWordsTitles().size()]))) {
                        pushKeywordByContent(listKeywordsByTW, result, snippet);
                        pushKeywordByContent(listKeyword, result, snippet);
                    }*/

                });
                
                result.getSnippetsWebs().forEach(snippet -> {
                    // Check new content for respective table
                    if (!snippet.getFeeling().equalsIgnoreCase(EFeelings.NOT_APPLIED.getTag())
                            && this.isNewContent(snippet.getTracking().get(0).getDate_scan(), referenceFirstDate)) {
                        pushNewNegativeContent(listNew, result, snippet);
                    }

                    // Check negative content for respective table
                    if (snippet.getFeeling().equalsIgnoreCase(EFeelings.BAD.getTag())) {
                        pushNewNegativeContent(listNegative, result, snippet);
                    }

                    // Checking TrackingWords against Snippets
                    scanner.getTrackingWords().forEach(trackingWord -> {
                        if (trackingWord.esURL()) {
                            if (snippet.getLink().equals(trackingWord.getWord())) {
                                pushKeywordByContent(listKeywordsByURL, result, snippet);
                            }
                        }
                    });

                   /* if (scanner.getTrackingWords() != null && !scanner.getTrackingWords().isEmpty() &&
                            snippet.containsTrackingWord(scanner.justTrackingWordsTitles().toArray(new String[scanner.justTrackingWordsTitles().size()]))) {
                        pushKeywordByContent(listKeywordsByTW, result, snippet);
                        pushKeywordByContent(listKeyword, result, snippet);
                    }*/

                });
    		}
    	}
    	this.section.setNew_content(listNew);
    	getLog().info("NewContent: "+ this.section.getNew_content().size());
		this.section.setNegative_content(listNegative);
		getLog().info("NegativeContent: "+ this.section.getNegative_content().size());
		this.section.setDisplaced_url(listKeywordsByURL);
		getLog().info("DisplacedUrl: "+ this.section.getDisplaced_url().size());
    }
    
    private void matchingTrack(ScannerAlert alert) throws RemoveApplicationException {
    	List<GenericAlert> listKeywordsByTW = new ArrayList<GenericAlert>();
    	
    	scannerTrack = ScannerDao.init().getBasicById(alert.getId_scanner());
    	scannerTrack = loadScannerInfo(scannerTrack.getUuid());
    	String[] sec = scannerTrack.getConfiguration().getSearch_type().split(",");
    	scannerTrack.filterResultsBySections(sec);
    	scannerTrack.filterResultsByPage(page);
    	LocalDate referenceFirstDate = scannerTrack.getResults().get(0).getQuery_date().toLocalDate();
    	scannerTrack.filterResultsByDates(referenceFirstDate,
        		referenceFirstDate);
    	String[] word = alert.getTracking_word().split(",");
    	scannerTrack.filterResultsByTrackingWords(word);
    	
    	if (scannerTrack.getResults() != null && !scannerTrack.getResults().isEmpty()) {
    		for (ScannerResult result : scannerTrack.getResults()) {
                result.getSnippetsWebs().forEach(snippet -> {

                   if (scannerTrack.getTrackingWords() != null && !scannerTrack.getTrackingWords().isEmpty() &&
                            snippet.containsTrackingWord(scannerTrack.justTrackingWordsTitles().toArray(new String[scannerTrack.justTrackingWordsTitles().size()]))) {
                        pushKeywordByContent(listKeywordsByTW, result, snippet);
                        //pushKeywordByContent(listKeyword, result, snippet);
                    }

                });
                
                result.getSnippetsNews().forEach(snippet -> {

                    if (scannerTrack.getTrackingWords() != null && !scannerTrack.getTrackingWords().isEmpty() &&
                             snippet.containsTrackingWord(scannerTrack.justTrackingWordsTitles().toArray(new String[scannerTrack.justTrackingWordsTitles().size()]))) {
                         pushKeywordByContent(listKeywordsByTW, result, snippet);
                         //pushKeywordByContent(listKeyword, result, snippet);
                     }

                 });
                
                result.getSnippetsImages().forEach(snippet -> {

                    if (scannerTrack.getTrackingWords() != null && !scannerTrack.getTrackingWords().isEmpty() &&
                             snippet.containsTrackingWord(scannerTrack.justTrackingWordsTitles().toArray(new String[scannerTrack.justTrackingWordsTitles().size()]))) {
                         pushKeywordByContent(listKeywordsByTW, result, snippet);
                         //pushKeywordByContent(listKeyword, result, snippet);
                     }

                 });
    		}
    	}
    	this.section.setMatch_track(listKeywordsByTW);
		getLog().info("MatchingTrack: "+ this.section.getMatch_track().size());
    }
    
    private void matchingKey(ScannerAlert alert) throws RemoveApplicationException {
    	List<GenericAlert> listKeyword = new ArrayList<GenericAlert>();
    	
    	scannerKey = ScannerDao.init().getBasicById(alert.getId_scanner());
    	scannerKey = loadScannerInfo(scannerKey.getUuid());
    	String[] sec = scannerKey.getConfiguration().getSearch_type().split(",");
    	scannerKey.filterResultsBySections(sec);
    	scannerKey.filterResultsByPage(page);
    	LocalDate referenceFirstDate = scannerKey.getResults().get(0).getQuery_date().toLocalDate();
    	scannerKey.filterResultsByDates(referenceFirstDate,
        		referenceFirstDate);
    	String[] key = alert.getKeyword().split(",");
    	scannerKey.filterResultsByKeywords(key);
    	
    	if (scannerKey.getResults() != null && !scannerKey.getResults().isEmpty()) {
    		for (ScannerResult result : scannerKey.getResults()) {
                result.getSnippetsWebs().forEach(snippet -> {

                   if (scannerKey.getTrackingWords() != null && !scannerKey.getTrackingWords().isEmpty() &&
                            snippet.containsTrackingWord(scannerKey.justTrackingWordsTitles().toArray(new String[scannerKey.justTrackingWordsTitles().size()]))) {
                        pushKeywordByContent(listKeyword, result, snippet);
                    }

                });
                
                result.getSnippetsNews().forEach(snippet -> {

                    if (scannerKey.getTrackingWords() != null && !scannerKey.getTrackingWords().isEmpty() &&
                             snippet.containsTrackingWord(scannerKey.justTrackingWordsTitles().toArray(new String[scannerKey.justTrackingWordsTitles().size()]))) {                         
                         pushKeywordByContent(listKeyword, result, snippet);
                     }

                 });
                
                result.getSnippetsImages().forEach(snippet -> {

                    if (scannerKey.getTrackingWords() != null && !scannerKey.getTrackingWords().isEmpty() &&
                             snippet.containsTrackingWord(scannerKey.justTrackingWordsTitles().toArray(new String[scannerKey.justTrackingWordsTitles().size()]))) {
                         pushKeywordByContent(listKeyword, result, snippet);
                     }

                 });
    		}
    	}
		this.section.setMatch_key(listKeyword);
		getLog().info("MatchingKey: "+ this.section.getMatch_key().size());
    }
    
    public boolean isNewContent(LocalDateTime firstTimeSnippetFound, LocalDate refenreceFirstDate) {
        if (firstTimeSnippetFound.toLocalDate().isAfter(refenreceFirstDate)) {
            return true;
        }

        return false;
    }
    
    private Scanner loadScannerInfo(String uuidScanner) throws RemoveApplicationException {
        Scanner scanner = ScannerDao.init().getBasicByUuid(uuidScanner);

        ScannerResultView view = ScannerResultViewDao.init().getByUuid(uuidScanner);

        if (view != null && view.getContent() != null) {
            scanner = (Scanner) RemoveJsonUtil.jsonToData(view.getContent(), Scanner.class);
            scanner.setImpulses(ScannerImpulseDao.init().listPublishedByScanner(scanner.getId()));
        } else { // Deprecado temporal mientras se migran todos los scanners al view
            scanner = ScannerDao.init().getJustConfigByUuid(uuidScanner, scanner.getType());

            if (scanner != null) {
                scanner.setResults(ScannerResultDao.init().list(scanner.getId()));
                scanner.setImpulses(ScannerImpulseDao.init().listPublishedByScanner(scanner.getId()));
            }
        }

        if (scanner != null) {
            scanner.clearImagesResults();
        }

        if (scanner.getTrackingWords().isEmpty()) {
            scanner.setTrackingWords(ScannerTrackingWordsDao.init().list(scanner.getId()));
            scanner.splitTrackingWords();
        }

        scanner.setRestrictions(ClientPlanDao.init().getById(scanner.getId_client_plan()));

        return scanner;
    }
    
    private void pushKeywordByContent(List<GenericAlert> list, ScannerResult result,
			ScannerResultWebSnippet snippet) {
		try {
			ScannerKeyword keyword = ScannerKeywordsDao.init().get(result.getId_keyword());
			List<Long> listIdTW = ScannerResultWebSnippetDao.init().loadTWIdsList(snippet.getId());
			List<ScannerTrackingWord> listTW = ScannerTrackingWordsDao.init().list(listIdTW);
			
			if(listTW != null)
				listTW =  listTW.stream().filter( x -> !x.getType().equalsIgnoreCase("URL") ).collect(Collectors.toList());
			
			GenericAlert generic = new GenericAlert();
			generic.setTitle(snippet.getTitle());
			generic.setLink(snippet.getLink());
			generic.setCountry(result.getCountry().getTag());
			generic.setSection(result.getSearch_type());
			generic.setKeyword(keyword.getWord());
			
			if(listTW != null) {
				listTW.forEach(tw -> {
					generic.getTracking_word().add(tw.getWord());
				});
			}
			
			generic.setPage(snippet.getTracking().get(snippet.getTracking().size()-1).getPage());
			generic.setPosition_in_page(snippet.getTracking().get(snippet.getTracking().size()-1).getPosition_in_page());
			generic.setQuery_date(result.getQuery_date());
	    	
			list.add(generic);
		} catch (RemoveApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void pushKeywordByContent(List<GenericAlert> list, ScannerResult result,
			ScannerResultNewsSnippet snippet) {
		try {
			ScannerKeyword keyword = ScannerKeywordsDao.init().get(result.getId_keyword());
			List<Long> listIdTW = ScannerResultWebSnippetDao.init().loadTWIdsList(snippet.getId());
			List<ScannerTrackingWord> listTW = ScannerTrackingWordsDao.init().list(listIdTW);
			
			if(listTW != null)
				listTW =  listTW.stream().filter( x -> !x.getType().equalsIgnoreCase("URL") ).collect(Collectors.toList());
			
			GenericAlert generic = new GenericAlert();
			generic.setTitle(snippet.getTitle());
			generic.setLink(snippet.getLink());
			generic.setCountry(result.getCountry().getTag());
			generic.setSection(result.getSearch_type());
			generic.setKeyword(keyword.getWord());
			
			if(listTW != null) {
				listTW.forEach(tw -> {
					generic.getTracking_word().add(tw.getWord());
				});
			}
			
			generic.setPage(snippet.getTracking().get(snippet.getTracking().size()-1).getPage());
			generic.setPosition_in_page(snippet.getTracking().get(snippet.getTracking().size()-1).getPosition_in_page());
			generic.setQuery_date(result.getQuery_date());
	    	
			list.add(generic);
		} catch (RemoveApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void pushKeywordByContent(List<GenericAlert> list, ScannerResult result,
			ScannerResultImageSnippet snippet) {
		try {
			ScannerKeyword keyword = ScannerKeywordsDao.init().get(result.getId_keyword());
			List<Long> listIdTW = ScannerResultWebSnippetDao.init().loadTWIdsList(snippet.getId());
			List<ScannerTrackingWord> listTW = ScannerTrackingWordsDao.init().list(listIdTW);
			
			if(listTW != null)
				listTW =  listTW.stream().filter( x -> !x.getType().equalsIgnoreCase("URL") ).collect(Collectors.toList());
			
			GenericAlert generic = new GenericAlert();
			generic.setTitle(snippet.getTitle());
			generic.setLink(snippet.getLink());
			generic.setCountry(result.getCountry().getTag());
			generic.setSection(result.getSearch_type());
			generic.setKeyword(keyword.getWord());
			
			if(listTW != null) {
				listTW.forEach(tw -> {
					generic.getTracking_word().add(tw.getWord());
				});
			}
			
			generic.setPage(snippet.getTracking().get(snippet.getTracking().size()-1).getPage());
			generic.setPosition_in_page(snippet.getTracking().get(snippet.getTracking().size()-1).getPosition_in_page());
			generic.setQuery_date(result.getQuery_date());
	    	
			list.add(generic);
		} catch (RemoveApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void pushNewNegativeContent(List<GenericAlert> list, ScannerResult result,
			ScannerResultWebSnippet snippet) {
		try {
			ScannerKeyword keyword = ScannerKeywordsDao.init().get(result.getId_keyword());
			GenericAlert generic = new GenericAlert();
			generic.setTitle(snippet.getTitle());
			generic.setLink(snippet.getLink());
			generic.setCountry(result.getCountry().getTag());
			generic.setSection(result.getSearch_type());
			generic.setKeyword(keyword.getWord());
			generic.setPage(snippet.getTracking().get(snippet.getTracking().size()-1).getPage());
			generic.setPosition_in_page(snippet.getTracking().get(snippet.getTracking().size()-1).getPosition_in_page());
			generic.setQuery_date(result.getQuery_date());
	    	
			list.add(generic);
		} catch (RemoveApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void pushNewNegativeContent(List<GenericAlert> list, ScannerResult result,
			ScannerResultNewsSnippet snippet) {
		try {
			ScannerKeyword keyword = ScannerKeywordsDao.init().get(result.getId_keyword());
			GenericAlert generic = new GenericAlert();
			generic.setTitle(snippet.getTitle());
			generic.setLink(snippet.getLink());
			generic.setCountry(result.getCountry().getTag());
			generic.setSection(result.getSearch_type());
			generic.setKeyword(keyword.getWord());
			generic.setPage(snippet.getTracking().get(snippet.getTracking().size()-1).getPage());
			generic.setPosition_in_page(snippet.getTracking().get(snippet.getTracking().size()-1).getPosition_in_page());
			generic.setQuery_date(result.getQuery_date());
	    	
			list.add(generic);
		} catch (RemoveApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void pushNewNegativeContent(List<GenericAlert> list, ScannerResult result, ScannerResultImageSnippet snippet){
    	
		//ScannerKeyword keyword;
		try {
			ScannerKeyword keyword = ScannerKeywordsDao.init().get(result.getId_keyword());
			GenericAlert generic = new GenericAlert();
			generic.setTitle(snippet.getTitle());
			generic.setLink(snippet.getLink());
			generic.setCountry(result.getCountry().getTag());
			generic.setSection(result.getSearch_type());
			generic.setKeyword(keyword.getWord());
			generic.setPage(snippet.getTracking().get(snippet.getTracking().size()-1).getPage());
			generic.setPosition_in_page(snippet.getTracking().get(snippet.getTracking().size()-1).getPosition_in_page());
			generic.setQuery_date(result.getQuery_date());
	    	
			list.add(generic);
		} catch (RemoveApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	/**
	 * Obtiene el log para registrar las trazas.
	 * @return
	 */
	private static Logger getLog() {
		if(log == null) {
			log = LogManager.getLogger(AlertsUtils.class);
		}
		
		return log;
	}
}
