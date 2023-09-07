package cl.atianza.remove.daos.commons;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EFeelings;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.external.clients.serp.RemoveSERPWebNestedResults;
import cl.atianza.remove.helpers.HtmlInspectorHelper;
import cl.atianza.remove.helpers.WebSnippetHelper;
import cl.atianza.remove.models.commons.ScannerKeyword;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippet;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippetTrack;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;
import cl.atianza.remove.models.commons.ScannerResultWebSnippetTrack;
import cl.atianza.remove.models.commons.ScannerTrackingWord;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Scanner Results DAOs methods.
 * @author pilin
 *
 */
public class ScannerResultDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_results";
	
	private static final String FIELD_UUID = "uuid";
	private static final String FIELD_ID_SCANNER = "id_scanner";
	private static final String FIELD_ID_COUNTRY = "id_country";
	private static final String FIELD_ID_KEYWORD = "id_keyword";
	private static final String FIELD_RATING_VISUALIZATION = "rating_visualization";
	private static final String FIELD_RATING_REPUTATION = "rating_reputation";
	private static final String FIELD_TOTAL_LAST_SCAN = "total_last_scan";
	private static final String FIELD_PROGRESS = "progress";
	private static final String FIELD_LANGUAGE = "language";
	private static final String FIELD_DEVICE = "device";
	private static final String FIELD_SEARCH_TYPE = "search_type";
	private static final String FIELD_CITY = "city";
	private static final String FIELD_QUERY_DATE = "query_date";
	
	CountryDao countryDao = CountryDao.init();
	ScannerKeywordsDao keywordDao = ScannerKeywordsDao.init();
	ScannerResultRawDao rawDao = ScannerResultRawDao.init();
	ScannerResultWebSnippetDao snippetWebDao = ScannerResultWebSnippetDao.init();
	ScannerResultNewsSnippetDao snippetNewsDao = ScannerResultNewsSnippetDao.init();
	ScannerResultImageSnippetDao snippetImageDao = ScannerResultImageSnippetDao.init();
	ScannerResultWebSnippetTrackDao trackDao = ScannerResultWebSnippetTrackDao.init();
	ScannerResultNewsSnippetTrackDao trackNewsDao = ScannerResultNewsSnippetTrackDao.init();
	
	
	
	
	public ScannerResultDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerResultDao.class), TABLE_NAME);
	}
	
	public static ScannerResultDao init() throws RemoveApplicationException {
		return new ScannerResultDao();
	}	
	/**
	 * 
	 * @param idScanner
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ScannerResult> list(long idScanner) throws RemoveApplicationException {
		List<ScannerResult> list = listWithoutTracking(idScanner);
		
		loadInnerData(list);
		
		return list;
	}
	/**
	 * 
	 * @param idScanner
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ScannerResult> listWithoutTracking(Long idScanner) throws RemoveApplicationException {
		List<ScannerResult> list = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER, idScanner) + " ORDER BY " + FIELD_QUERY_DATE;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).executeAndFetch(ScannerResult.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return list;
	}
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerResult getById(Long id) throws RemoveApplicationException {
		ScannerResult result = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID, id);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			result = (ScannerResult) conn.createQuery(QUERY).executeAndFetchFirst(ScannerResult.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return result;
	}
	
	public ScannerResult getLastDateExecute(Long id) throws RemoveApplicationException {
		ScannerResult result = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER, id) + " ORDER BY " + FIELD_ID +" DESC limit 1";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			result = (ScannerResult) conn.createQuery(QUERY).executeAndFetchFirst(ScannerResult.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return result;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerResult getByIdScan(Long idScan) throws RemoveApplicationException {
		ScannerResult result = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER, idScan);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			result = (ScannerResult) conn.createQuery(QUERY).executeAndFetchFirst(ScannerResult.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return result;
	}
	
	/**
	 * 
	 * @param list
	 */
	private void loadInnerData(List<ScannerResult> list) {
		if (list != null && !list.isEmpty()) {
			list.forEach(result -> {
				try {
					loadInnerData(result);
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading snippet data: ", e);
				}
			});
		}
	}
	/**
	 * 
	 * @param result
	 * @throws RemoveApplicationException
	 */
	private void loadInnerData(ScannerResult result) throws RemoveApplicationException {
		if (result != null) {
			result.setCountry(CountryDao.init().get(result.getId_country()));
			result.setSnippetsWebs(ScannerResultWebSnippetDao.init().list(result.getId()));
			result.setSnippetsNews(ScannerResultNewsSnippetDao.init().list(result.getId()));
			result.setSnippetsImages(ScannerResultImageSnippetDao.init().list(result.getId()));
			
			ScannerKeyword keyword = ScannerKeywordsDao.init().get(result.getId_keyword());
			
			result.setKeyword(keyword);
			if (keyword.isSuggested()) {
				result.setId_parent_keyword(keyword.getId_suggested_from());
			}
		}
	}
	/**
	 * 
	 * @param result
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerResult save(ScannerResult result) throws RemoveApplicationException {
		result.setUuid(UUID.randomUUID().toString());
		
		String QUERY = buildInsertQuery(TABLE, FIELD_UUID, FIELD_ID_SCANNER, FIELD_ID_COUNTRY, FIELD_ID_KEYWORD, 
				FIELD_LANGUAGE, FIELD_DEVICE, FIELD_SEARCH_TYPE, FIELD_CITY, FIELD_QUERY_DATE, FIELD_RATING_REPUTATION,
				FIELD_RATING_VISUALIZATION);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_UUID, result.getUuid())
    				.addParameter(FIELD_ID_SCANNER, result.getId_scanner())
    				.addParameter(FIELD_ID_COUNTRY, result.getId_country())
    				.addParameter(FIELD_ID_KEYWORD, result.getId_keyword())
    				.addParameter(FIELD_LANGUAGE, result.getLanguage())
    				.addParameter(FIELD_DEVICE, result.getDevice())
    				.addParameter(FIELD_SEARCH_TYPE, result.getSearch_type())
    				.addParameter(FIELD_CITY, result.getCity())
    				.addParameter(FIELD_QUERY_DATE, result.getQuery_date())
    				.addParameter(FIELD_RATING_REPUTATION, result.getRating_reputation())
    				.addParameter(FIELD_RATING_VISUALIZATION, result.getRating_visualization())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	result.setId(idRecord);
    	
    	innerSaves(result);
    	
    	return result;
	}
	/**
	 * 
	 * @param result
	 * @throws RemoveApplicationException
	 */
	private void innerSaves(ScannerResult result) throws RemoveApplicationException {
		saveRaw(result);
		saveSnippets(result);
	}
	/**
	 * 
	 * @param result
	 * @throws RemoveApplicationException
	 */
	private void saveRaw(ScannerResult result) throws RemoveApplicationException {
		if (result.getRaws() != null && !result.getRaws().isEmpty()) {
			result.getRaws().forEach(raw -> {
				if (raw.esNuevo()) {
					raw.setId_scanner_result(result.getId());
					try {
						rawDao.save(raw);
					} catch (RemoveApplicationException e) {
						getLog().error("Error saving raw: ", e);
					}
				}
				
				raw.setRaw(null);
			});
		}
	}
	/**
	 * 
	 * @param result
	 * @param idRaw
	 * @throws RemoveApplicationException
	 */
	private void saveSnippets(ScannerResult result) throws RemoveApplicationException {
		getLog().info("Saving snippets...");
		List<WebSnippetHelper> listWsh = new ArrayList<WebSnippetHelper>();
		int total = 0;
		
		
		if (result.getSnippetsWebs() != null && !result.getSnippetsWebs().isEmpty()) {
			List<String> linksWeb = new ArrayList<String>();
			for(int i=0;i<result.getSnippetsWebs().size();i++) {
				if (linksWeb.contains(result.getSnippetsWebs().get(i).getLink())) {
					getLog().error("TODO Snippet repeated: " + result.getSnippetsWebs().get(i).getLink());
					result.getSnippetsWebs().remove(i);
				} else {
					linksWeb.add(result.getSnippetsWebs().get(i).getLink());
				}
			}
			getLog().info("Saving WEB snippets... Total:"+result.getSnippetsWebs().size());
			total += result.getSnippetsWebs().size();

			result.getSnippetsWebs().forEach(snippetWeb -> {
				snippetWeb.setId_scanner_result(result.getId());
				snippetWeb.setId_scanner_raw(result.findUuidRaw(snippetWeb.getUuidRaw()));
				
				WebSnippetHelper wsh = new WebSnippetHelper();
				
				if(snippetWeb.getNested_results() == null) {
					wsh.setId_scanner_result(snippetWeb.getId_scanner_result());
					wsh.setId_scanner_raw(snippetWeb.getId_scanner_raw());
					wsh.setTitle(snippetWeb.getTitle());
					wsh.setLink(snippetWeb.getLink());
					wsh.setDomain(snippetWeb.getDomain());
					wsh.setDisplayed_link(snippetWeb.getDisplayed_link());
					wsh.setSnippet(snippetWeb.getSnippet());
					wsh.setDate(snippetWeb.getDate());
					wsh.setDate_utc(snippetWeb.getDate_utc());
					wsh.setFeeling(snippetWeb.getFeeling() != null ? snippetWeb.getFeeling() : EFeelings.NOT_APPLIED.getTag());
					
					wsh.setId_scanner_result_web_snippet(snippetWeb.getId());
					wsh.setId_scanner_raw_track(snippetWeb.getId_scanner_raw());
					snippetWeb.getTracking().forEach(track -> {
						wsh.setDate_scan(track.getDate_scan());
						wsh.setPage(track.getPage());
						wsh.setPosition(0);
						wsh.setPosition_in_page(0);
					});
					
					snippetWeb.getTrackingWords().forEach(tw -> {
						ScannerTrackingWord snippetTW = new ScannerTrackingWord();
						snippetTW.setId(tw.getId());
						snippetTW.setId_scanner(tw.getId_scanner());
						snippetTW.setWord(tw.getWord());
						snippetTW.setType(tw.getType());
						snippetTW.setFeeling(tw.getFeeling());
						snippetTW.setFeelingObj(tw.getFeelingObj());
						snippetTW.setMarkToDelete(tw.isMarkToDelete());
						
						wsh.getSnippetTW().add(snippetTW);
					});
					wsh.setPage_final(0);
					
					listWsh.add(wsh);
					
					
				}else {
					wsh.setId_scanner_result(snippetWeb.getId_scanner_result());
					wsh.setId_scanner_raw(snippetWeb.getId_scanner_raw());
					wsh.setTitle(snippetWeb.getTitle());
					wsh.setLink(snippetWeb.getLink());
					wsh.setDomain(snippetWeb.getDomain());
					wsh.setDisplayed_link(snippetWeb.getDisplayed_link());
					wsh.setSnippet(snippetWeb.getSnippet());
					wsh.setDate(snippetWeb.getDate());
					wsh.setDate_utc(snippetWeb.getDate_utc());
					wsh.setFeeling(snippetWeb.getFeeling() != null ? snippetWeb.getFeeling() : EFeelings.NOT_APPLIED.getTag());
					
					wsh.setId_scanner_result_web_snippet(snippetWeb.getId());
					wsh.setId_scanner_raw_track(snippetWeb.getId_scanner_raw());
					snippetWeb.getTracking().forEach(track -> {
						wsh.setDate_scan(track.getDate_scan());
						wsh.setPage(track.getPage());
						wsh.setPosition(0);
						wsh.setPosition_in_page(0);
					});
					
					snippetWeb.getTrackingWords().forEach(tw -> {
						ScannerTrackingWord snippetTW = new ScannerTrackingWord();
						snippetTW.setId(tw.getId());
						snippetTW.setId_scanner(tw.getId_scanner());
						snippetTW.setWord(tw.getWord());
						snippetTW.setType(tw.getType());
						snippetTW.setFeeling(tw.getFeeling());
						snippetTW.setFeelingObj(tw.getFeelingObj());
						snippetTW.setMarkToDelete(tw.isMarkToDelete());
						
						wsh.getSnippetTW().add(snippetTW);
					});

					wsh.setPage_final(0);
					listWsh.add(wsh);
					
					WebSnippetHelper wshh = new WebSnippetHelper();
					wshh.setId_scanner_result(snippetWeb.getId_scanner_result());
					wshh.setId_scanner_raw(snippetWeb.getId_scanner_raw());
					//wsh.setTitle(snippetWeb.getTitle());
					//wsh.setLink(snippetWeb.getLink());
					wshh.setDomain(snippetWeb.getDomain());
					//wsh.setDisplayed_link(snippetWeb.getDisplayed_link());
					//wsh.setSnippet(snippetWeb.getSnippet());
					wshh.setDate(snippetWeb.getDate());
					wshh.setDate_utc(snippetWeb.getDate_utc());
					wshh.setFeeling(snippetWeb.getFeeling() != null ? snippetWeb.getFeeling() : EFeelings.NOT_APPLIED.getTag());
					
					wshh.setId_scanner_result_web_snippet(snippetWeb.getId());
					wshh.setId_scanner_raw_track(snippetWeb.getId_scanner_raw());
					snippetWeb.getTracking().forEach(track -> {
						wshh.setDate_scan(track.getDate_scan());
						wshh.setPage(track.getPage());
						wshh.setPosition(0);
						wshh.setPosition_in_page(0);
					});
					
					snippetWeb.getTrackingWords().forEach(tw -> {
						ScannerTrackingWord snippetTW = new ScannerTrackingWord();
						snippetTW.setId(tw.getId());
						snippetTW.setId_scanner(tw.getId_scanner());
						snippetTW.setWord(tw.getWord());
						snippetTW.setType(tw.getType());
						snippetTW.setFeeling(tw.getFeeling());
						snippetTW.setFeelingObj(tw.getFeelingObj());
						snippetTW.setMarkToDelete(tw.isMarkToDelete());
						
						wsh.getSnippetTW().add(snippetTW);
					});
					
					snippetWeb.getNested_results().forEach(res -> {
						RemoveSERPWebNestedResults nested_results = new RemoveSERPWebNestedResults();
						nested_results.setPosition(0);
						nested_results.setTitle(res.getTitle());
						nested_results.setDisplayed_link(res.getDisplayed_link());
						nested_results.setLink(res.getLink());
						nested_results.setSnippet(res.getSnippet());
						
						wshh.setTitle(nested_results.getTitle());
						wshh.setLink(nested_results.getLink());
						wshh.setDisplayed_link(nested_results.getDisplayed_link());
						wshh.setSnippet(nested_results.getSnippet());
					});
					wshh.setPage_final(0);
					listWsh.add(wshh);
				}
				
				/*try {
					snippetWebDao.save(snippetWeb);
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving: ", e);
				}*/
			});
			try {
				snippetWebDao.saveNestedResults(listWsh);
			}catch(RemoveApplicationException e) {
				getLog().error("Error saving: ", e);				
			}
			
		}
		
		if (result.getSnippetsNews() != null && !result.getSnippetsNews().isEmpty()) {
			List<String> linksNews = new ArrayList<String>();
			for(int i=0;i<result.getSnippetsNews().size();i++) {
				if (linksNews.contains(result.getSnippetsNews().get(i).getLink())) {
					getLog().error("TODO Snippet repeated: " + result.getSnippetsNews().get(i).getLink());
					result.getSnippetsNews().remove(i);
				} else {
					linksNews.add(result.getSnippetsNews().get(i).getLink());
				}
			}
			getLog().info("Saving NEWS snippets... Total:"+result.getSnippetsNews().size());
			total += result.getSnippetsNews().size();
			result.getSnippetsNews().forEach(snippetNews -> {
				snippetNews.setId_scanner_result(result.getId());
				snippetNews.setId_scanner_raw(result.findUuidRaw(snippetNews.getUuidRaw()));
				
				try {
					snippetNewsDao.save(snippetNews);
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving: ", e);
				}
			});
		}
		
		if (result.getSnippetsImages() != null && !result.getSnippetsImages().isEmpty()) {
			List<String> linksImage = new ArrayList<String>();
			for(int i=0;i<result.getSnippetsImages().size();i++) {
				if (linksImage.contains(result.getSnippetsImages().get(i).getLink())) {
					getLog().error("TODO Snippet repeated: " + result.getSnippetsImages().get(i).getLink());
					result.getSnippetsImages().remove(i);
				} else {
					linksImage.add(result.getSnippetsImages().get(i).getLink());
				}
			}
			getLog().info("Saving IMAGES snippets... Total:"+result.getSnippetsImages().size());
			total += result.getSnippetsImages().size();
			result.getSnippetsImages().forEach(snippetImage -> {
				snippetImage.setId_scanner_result(result.getId());
				snippetImage.setId_scanner_raw(result.findUuidRaw(snippetImage.getUuidRaw()));
				
				try {
					snippetImageDao.save(snippetImage);
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving: ", e);
				}
			});
		}
		
		result.setTotal_last_scan(total);
		updateRatings(result);
		//updateProgress(result.getId(), result.getProgress());
	}
	/**
	 * 
	 * @param result
	 * @throws RemoveApplicationException
	 */
	public void upsertInnerObjects(ScannerResult result) throws RemoveApplicationException {
		getLog().info("Updating raw & snippets...");
		saveRaw(result);
		List<WebSnippetHelper> listWsh = new ArrayList<WebSnippetHelper>();
		int total = 0;
		
		if (result.getSnippetsWebs() != null && !result.getSnippetsWebs().isEmpty()) {
			
		 	List<ScannerResultWebSnippet> lstWeb = ScannerResultWebSnippetDao.init().list(result.getId());
		 	List<ScannerResultWebSnippetTrack> lstWebTrack = new ArrayList<ScannerResultWebSnippetTrack>();
		 
		 	lstWeb.forEach(web -> {
		 		try {
		 			ScannerResultWebSnippetTrack wst = trackDao.getByIdAndDate(web.getId());
		 			if(wst != null) {
		 				lstWebTrack.add(wst);
		 			}
				} catch (RemoveApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 	});
		 	
		 	if(lstWebTrack != null && !lstWebTrack.isEmpty()) {
			 	lstWebTrack.forEach(wtrack -> {
			 		try {
						trackDao.delete(wtrack);
					} catch (RemoveApplicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	});
		 	}
		 
		 
			
			List<String> linksWeb = new ArrayList<String>();
			for(int i=0;i<result.getSnippetsWebs().size();i++) {
				if (linksWeb.contains(result.getSnippetsWebs().get(i).getLink())) {
					getLog().error("TODO Snippet repeated: " + result.getSnippetsWebs().get(i).getLink());
					result.getSnippetsWebs().remove(i);
				} else {
					linksWeb.add(result.getSnippetsWebs().get(i).getLink());
				}
			}

			getLog().info("Updating WEB snippets... Total:"+result.getSnippetsWebs().size());
			total += result.getSnippetsWebs().size();
			result.getSnippetsWebs().forEach(snippetWeb -> {

				snippetWeb.setId_scanner_result(result.getId());
				snippetWeb.setId_scanner_raw(result.findUuidRaw(snippetWeb.getUuidRaw()));
				
				WebSnippetHelper wsh = new WebSnippetHelper();
				
				if(snippetWeb.getNested_results() == null) {
					wsh.setId_scanner_result(snippetWeb.getId_scanner_result());
					wsh.setId_scanner_raw(snippetWeb.getId_scanner_raw());
					wsh.setTitle(snippetWeb.getTitle());
					wsh.setLink(snippetWeb.getLink());
					wsh.setDomain(snippetWeb.getDomain());
					wsh.setDisplayed_link(snippetWeb.getDisplayed_link());
					wsh.setSnippet(snippetWeb.getSnippet());
					wsh.setDate(snippetWeb.getDate());
					wsh.setDate_utc(snippetWeb.getDate_utc());
					wsh.setFeeling(snippetWeb.getFeeling() != null ? snippetWeb.getFeeling() : EFeelings.NOT_APPLIED.getTag());
					
					wsh.setId_scanner_result_web_snippet(snippetWeb.getId());
					wsh.setId_scanner_raw_track(snippetWeb.getId_scanner_raw());
					snippetWeb.getTracking().forEach(track -> {
						wsh.setDate_scan(track.getDate_scan());
						wsh.setPage(track.getPage());
						wsh.setPosition(0);
						wsh.setPosition_in_page(0);
					});
					
					snippetWeb.getTrackingWords().forEach(tw -> {
						ScannerTrackingWord snippetTW = new ScannerTrackingWord();
						snippetTW.setId(tw.getId());
						snippetTW.setId_scanner(tw.getId_scanner());
						snippetTW.setWord(tw.getWord());
						snippetTW.setType(tw.getType());
						snippetTW.setFeeling(tw.getFeeling());
						snippetTW.setFeelingObj(tw.getFeelingObj());
						snippetTW.setMarkToDelete(tw.isMarkToDelete());
						
						wsh.getSnippetTW().add(snippetTW);
					});
					wsh.setPage_final(0);
					
					listWsh.add(wsh);
					
					
				}else {
					wsh.setId_scanner_result(snippetWeb.getId_scanner_result());
					wsh.setId_scanner_raw(snippetWeb.getId_scanner_raw());
					wsh.setTitle(snippetWeb.getTitle());
					wsh.setLink(snippetWeb.getLink());
					wsh.setDomain(snippetWeb.getDomain());
					wsh.setDisplayed_link(snippetWeb.getDisplayed_link());
					wsh.setSnippet(snippetWeb.getSnippet());
					wsh.setDate(snippetWeb.getDate());
					wsh.setDate_utc(snippetWeb.getDate_utc());
					wsh.setFeeling(snippetWeb.getFeeling() != null ? snippetWeb.getFeeling() : EFeelings.NOT_APPLIED.getTag());
					
					wsh.setId_scanner_result_web_snippet(snippetWeb.getId());
					wsh.setId_scanner_raw_track(snippetWeb.getId_scanner_raw());
					snippetWeb.getTracking().forEach(track -> {
						wsh.setDate_scan(track.getDate_scan());
						wsh.setPage(track.getPage());
						wsh.setPosition(0);
						wsh.setPosition_in_page(0);
					});
					
					snippetWeb.getTrackingWords().forEach(tw -> {
						ScannerTrackingWord snippetTW = new ScannerTrackingWord();
						snippetTW.setId(tw.getId());
						snippetTW.setId_scanner(tw.getId_scanner());
						snippetTW.setWord(tw.getWord());
						snippetTW.setType(tw.getType());
						snippetTW.setFeeling(tw.getFeeling());
						snippetTW.setFeelingObj(tw.getFeelingObj());
						snippetTW.setMarkToDelete(tw.isMarkToDelete());
						
						wsh.getSnippetTW().add(snippetTW);
					});

					wsh.setPage_final(0);
					listWsh.add(wsh);
					
					WebSnippetHelper wshh = new WebSnippetHelper();
					wshh.setId_scanner_result(snippetWeb.getId_scanner_result());
					wshh.setId_scanner_raw(snippetWeb.getId_scanner_raw());
					//wsh.setTitle(snippetWeb.getTitle());
					//wsh.setLink(snippetWeb.getLink());
					wshh.setDomain(snippetWeb.getDomain());
					//wsh.setDisplayed_link(snippetWeb.getDisplayed_link());
					//wsh.setSnippet(snippetWeb.getSnippet());
					wshh.setDate(snippetWeb.getDate());
					wshh.setDate_utc(snippetWeb.getDate_utc());
					wshh.setFeeling(snippetWeb.getFeeling() != null ? snippetWeb.getFeeling() : EFeelings.NOT_APPLIED.getTag());
					
					wshh.setId_scanner_result_web_snippet(snippetWeb.getId());
					wshh.setId_scanner_raw_track(snippetWeb.getId_scanner_raw());
					snippetWeb.getTracking().forEach(track -> {
						wshh.setDate_scan(track.getDate_scan());
						wshh.setPage(track.getPage());
						wshh.setPosition(0);
						wshh.setPosition_in_page(0);
					});
					
					snippetWeb.getTrackingWords().forEach(tw -> {
						ScannerTrackingWord snippetTW = new ScannerTrackingWord();
						snippetTW.setId(tw.getId());
						snippetTW.setId_scanner(tw.getId_scanner());
						snippetTW.setWord(tw.getWord());
						snippetTW.setType(tw.getType());
						snippetTW.setFeeling(tw.getFeeling());
						snippetTW.setFeelingObj(tw.getFeelingObj());
						snippetTW.setMarkToDelete(tw.isMarkToDelete());
						
						wsh.getSnippetTW().add(snippetTW);
					});
					
					snippetWeb.getNested_results().forEach(res -> {
						RemoveSERPWebNestedResults nested_results = new RemoveSERPWebNestedResults();
						nested_results.setPosition(0);
						nested_results.setTitle(res.getTitle());
						nested_results.setDisplayed_link(res.getDisplayed_link());
						nested_results.setLink(res.getLink());
						nested_results.setSnippet(res.getSnippet());
						
						wshh.setTitle(nested_results.getTitle());
						wshh.setLink(nested_results.getLink());
						wshh.setDisplayed_link(nested_results.getDisplayed_link());
						wshh.setSnippet(nested_results.getSnippet());
					});
					wshh.setPage_final(0);
					listWsh.add(wshh);
				}
			});
			
			try {
				snippetWebDao.upsertNestedResult(listWsh);				
			} catch (RemoveApplicationException e) {
				getLog().error("Error updating: ", e);
			}
		}
		
		if (result.getSnippetsNews() != null && !result.getSnippetsNews().isEmpty()) {
			
		    List<ScannerResultNewsSnippet> newsSnippet = ScannerResultNewsSnippetDao.init().list(result.getId());
		    List<ScannerResultNewsSnippetTrack> newsTrack = new ArrayList<ScannerResultNewsSnippetTrack>();
		    
		    newsSnippet.forEach(news -> {
		    	try {
		    		ScannerResultNewsSnippetTrack wsnt = trackNewsDao.getByIdAndDate(news.getId());
		    		if(wsnt != null) {
		    			newsTrack.add(wsnt);
		    		}
					
				} catch (RemoveApplicationException e) {
					e.printStackTrace();
				}
		    });
			
		 	if(newsTrack != null && !newsTrack.isEmpty()) {
		 		newsTrack.forEach(ntrack -> {
			 		try {
			 			trackNewsDao.delete(ntrack);
					} catch (RemoveApplicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	});
		 	}
			
			List<String> linksNews = new ArrayList<String>();
			for(int i=0;i<result.getSnippetsNews().size();i++) {
				if (linksNews.contains(result.getSnippetsNews().get(i).getLink())) {
					getLog().error("TODO Snippet repeated: " + result.getSnippetsNews().get(i).getLink());
					result.getSnippetsNews().remove(i);
				} else {
					linksNews.add(result.getSnippetsNews().get(i).getLink());
				}
			}
			getLog().info("Updating NEWS snippets... Total:"+result.getSnippetsNews().size());
			total += result.getSnippetsNews().size();
			result.getSnippetsNews().forEach(snippetNews -> {
				snippetNews.setId_scanner_result(result.getId());
				snippetNews.setId_scanner_raw(result.findUuidRaw(snippetNews.getUuidRaw()));
				
				try {
					snippetNewsDao.upsert(snippetNews);
				} catch (RemoveApplicationException e) {
					getLog().error("Error updating: ", e);
				}
			});
		}
		
		if (result.getSnippetsImages() != null && !result.getSnippetsImages().isEmpty()) {
			List<String> linksImage = new ArrayList<String>();
			for(int i=0;i<result.getSnippetsImages().size();i++) {
				if (linksImage.contains(result.getSnippetsImages().get(i).getLink())) {
					getLog().error("TODO Snippet repeated: " + result.getSnippetsImages().get(i).getLink());
					result.getSnippetsImages().remove(i);
				} else {
					linksImage.add(result.getSnippetsImages().get(i).getLink());
				}
			}
			getLog().info("Updating IMAGES snippets... Total:"+result.getSnippetsImages().size());
			total += result.getSnippetsImages().size();
			result.getSnippetsImages().forEach(snippetImage -> {
				snippetImage.setId_scanner_result(result.getId());
				snippetImage.setId_scanner_raw(result.findUuidRaw(snippetImage.getUuidRaw()));
				
				try {
					snippetImageDao.upsert(snippetImage);
				} catch (RemoveApplicationException e) {
					getLog().error("Error updating: ", e);
				}
			});
		}
		
		result.setTotal_last_scan(total);
		updateRatings(result);
		//updateProgress(result.getId(), result.getProgress());
	}
	/**
	 * 
	 * @param result
	 * @throws RemoveApplicationException
	 */
	private void updateRatings(ScannerResult result) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, result.getId(), FIELD_RATING_REPUTATION, FIELD_RATING_VISUALIZATION, FIELD_TOTAL_LAST_SCAN, FIELD_QUERY_DATE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_RATING_REPUTATION, result.getRating_reputation())
    				.addParameter(FIELD_RATING_VISUALIZATION, result.getRating_visualization())
    				.addParameter(FIELD_TOTAL_LAST_SCAN, result.getTotal_last_scan())
    				.addParameter(FIELD_QUERY_DATE, result.getQuery_date())
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
	 * @param result
	 * @throws RemoveApplicationException
	 */
	public void updateScores(ScannerResult result) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, result.getId(), FIELD_RATING_REPUTATION, FIELD_RATING_VISUALIZATION);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_RATING_REPUTATION, result.getRating_reputation())
    				.addParameter(FIELD_RATING_VISUALIZATION, result.getRating_visualization())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public void findIfExists(Long idScanner, ScannerResult result) throws RemoveApplicationException {
		result.setId_scanner(idScanner);
		ScannerResult oldResult = null;
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_SCANNER, FIELD_ID_COUNTRY, FIELD_ID_KEYWORD, FIELD_DEVICE, FIELD_LANGUAGE, FIELD_SEARCH_TYPE);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			oldResult = (ScannerResult) conn.createQuery(QUERY)
					.addParameter(FIELD_ID_SCANNER, result.getId_scanner())
					.addParameter(FIELD_ID_COUNTRY, result.getId_country())
					.addParameter(FIELD_ID_KEYWORD, result.getId_keyword())
					.addParameter(FIELD_DEVICE, result.getDevice())
					.addParameter(FIELD_LANGUAGE, result.getLanguage())
					.addParameter(FIELD_SEARCH_TYPE, result.getSearch_type()).executeAndFetchFirst(ScannerResult.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (oldResult != null) {
			result.setId(oldResult.getId());
		}
	}
	
	public void updateProgress(Long idResult, Float progress) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, idResult, FIELD_PROGRESS);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_PROGRESS, progress)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public List<ScannerResult> listLastTwoScans(Long idScanner, LocalDateTime lastScanTime) throws RemoveApplicationException {
		List<ScannerResult> listResults = listWithoutTracking(idScanner);
		
		if (listResults != null) {
			listResults.forEach(result -> {
				try {
					LocalDateTime penultimateScanTime = ScannerResultRawDao.init().getPenultScanTime(result.getId(), result.getSearch_type(), lastScanTime);
					
					if (penultimateScanTime != null) {
						result.setSnippetsWebs(ScannerResultWebSnippetDao.init().listByRangeDate(result.getId(), penultimateScanTime, lastScanTime));
						result.setSnippetsNews(ScannerResultNewsSnippetDao.init().listByRangeDate(result.getId(), penultimateScanTime, lastScanTime));
						result.setCountry(CountryDao.init().get(result.getId_country()));
						result.setKeyword(ScannerKeywordsDao.init().get(result.getId_keyword()));
					}
				} catch (RemoveApplicationException e) {
					getLog().error("Error searching last scan:" + result, e);
				}
			});
		}
		
		return listResults;
	}

	public void evaluateAndRefreshNewTrackingWords(Long idScanner, List<ScannerTrackingWord> trackingWords) throws RemoveApplicationException {
		if (trackingWords != null && !trackingWords.isEmpty()) {
			List<ScannerResult> results = list(idScanner);		
			
			if (results != null && !results.isEmpty()) {
				results.forEach(result -> {
					if (result.getSnippetsWebs() != null && !result.getSnippetsWebs().isEmpty()) {
						result.getSnippetsWebs().forEach(snippet -> {
							HtmlInspectorHelper.inspect(snippet, trackingWords);
							snippetWebDao.addNewTrackingWordsRelations(snippet);
						});
					}
					
					if (result.getSnippetsNews() != null && !result.getSnippetsNews().isEmpty()) {
						result.getSnippetsNews().forEach(snippet -> {
							HtmlInspectorHelper.inspect(snippet, trackingWords);
							snippetNewsDao.addNewTrackingWordsRelations(snippet);
						});
					}
					
					if (result.getSnippetsImages() != null && !result.getSnippetsImages().isEmpty()) {
						result.getSnippetsImages().forEach(snippet -> {
							HtmlInspectorHelper.inspect(snippet, trackingWords);
							snippetImageDao.addNewTrackingWordsRelations(snippet);
						});
					}
				});
			}
		}
	}
}
