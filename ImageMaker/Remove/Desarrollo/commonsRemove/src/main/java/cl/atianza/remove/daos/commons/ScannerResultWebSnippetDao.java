
package cl.atianza.remove.daos.commons;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EFeelings;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.WebSnippetHelper;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;
import cl.atianza.remove.models.commons.ScannerResultWebSnippetTrack;
import cl.atianza.remove.models.commons.ScannerTrackingWord;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Scanner Result Web Snippet DAOs methods.
 * @author pilin
 *
 */
public class ScannerResultWebSnippetDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_results_web_snippets";
	
	private static final String FIELD_ID_SCANNER_RESULT = "id_scanner_result";
	private static final String FIELD_ID_SCANNER_RAW = "id_scanner_raw";
	private static final String FIELD_TITLE = "title";
	private static final String FIELD_LINK = "link";
	private static final String FIELD_DOMAIN = "domain";
	private static final String FIELD_DISPLAYED_LINK = "displayed_link";
	private static final String FIELD_SNIPPET = "snippet";
	private static final String FIELD_DATE = "date";
	private static final String FIELD_DATE_UTC = "date_utc";
	private static final String FIELD_FEELING = "feeling";
	
	private static final String TABLE_RELATION_TW = "scanners_results_web_snippets_tw";
	private static final String FIELD_RELATION_ID_SCANNER_RESULT_SNIPPET = "id_scanner_result_web_snippet";
	private static final String FIELD_RELATION_ID_SCANNER_TRACKING_WORD = "id_scanner_tracking_word";
	
	ScannerTrackingWordsDao scannerTWDao = ScannerTrackingWordsDao.init();
	ScannerResultWebSnippetTrackDao trackDao = ScannerResultWebSnippetTrackDao.init();
	
	public ScannerResultWebSnippetDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerResultWebSnippetDao.class), TABLE_NAME);
	}
	/**
	 * 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public static ScannerResultWebSnippetDao init() throws RemoveApplicationException {
		return new ScannerResultWebSnippetDao();
	}	
	/**
	 * 
	 * @param idScannerResult
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ScannerResultWebSnippet> list(long idScannerResult) throws RemoveApplicationException {
		@SuppressWarnings("unchecked")
		List<ScannerResultWebSnippet> list = (List<ScannerResultWebSnippet>) listByField(TABLE, FIELD_ID_SCANNER_RESULT, idScannerResult, ScannerResultWebSnippet.class, FIELD_ID);
		
		loadInnerObjects(list);
		
		return list;
	}
	/**
	 * 
	 * @param list
	 */
	private void loadInnerObjects(List<ScannerResultWebSnippet> list) {
		if (list != null && !list.isEmpty()) {
			list.forEach(snippet -> {
				try {
					snippet.setTrackingWords(scannerTWDao.list(loadTWIdsList(snippet.getId())));
					snippet.setTracking(trackDao.list(snippet.getId()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading: ", e);
				}
			});
		}
	}
	/**
	 * 
	 * @param idSnippet
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<Long> loadTWIdsList(Long idSnippet) throws RemoveApplicationException {
		List<Long> list = null;
		
		String QUERY = "SELECT " + FIELD_RELATION_ID_SCANNER_TRACKING_WORD + " FROM " + schemaTable(TABLE_RELATION_TW) 
				+ " WHERE " + FIELD_RELATION_ID_SCANNER_RESULT_SNIPPET + " = " + idSnippet + "";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).executeAndFetch(Long.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return list;
	}
	/**
	 * 
	 * @param snippet
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerResultWebSnippet save(ScannerResultWebSnippet snippet) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER_RESULT, FIELD_ID_SCANNER_RAW, FIELD_TITLE, FIELD_LINK,
				FIELD_DOMAIN, FIELD_DISPLAYED_LINK, FIELD_SNIPPET, FIELD_DATE, FIELD_DATE_UTC, FIELD_FEELING);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER_RESULT, snippet.getId_scanner_result())
    				.addParameter(FIELD_ID_SCANNER_RAW, snippet.getId_scanner_raw())
    				.addParameter(FIELD_TITLE, snippet.getTitle())
    				.addParameter(FIELD_LINK, snippet.getLink())
    				.addParameter(FIELD_DOMAIN, snippet.getDomain())
    				.addParameter(FIELD_DISPLAYED_LINK, snippet.getDisplayed_link())
    				.addParameter(FIELD_SNIPPET, snippet.getSnippet())
    				.addParameter(FIELD_DATE, snippet.getDate())
    				.addParameter(FIELD_DATE_UTC, snippet.getDate_utc())
    				.addParameter(FIELD_FEELING, snippet.getFeeling() != null ? snippet.getFeeling() : EFeelings.NOT_APPLIED.getTag())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	snippet.setId(idRecord);
    	//innerSave(snippet);
    	
    	return snippet;
	}
	/**
	 * 
	 * @param snippet
	 */
	private void innerSave(ScannerResultWebSnippet snippet) {
		saveTrackingWords(snippet);
		saveTracking(snippet);
	}
	/**
	 * 
	 * @param snippet
	 */
	private void saveTrackingWords(ScannerResultWebSnippet snippet) {
		if (snippet.getTrackingWords() != null && !snippet.getTrackingWords().isEmpty()) {
			snippet.getTrackingWords().forEach(tw -> {
				try {
					saveTrackingWord(snippet.getId(), tw.getId());
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving: ", e);
				}
			});
		}
	}
	/**
	 * 
	 * @param idSnippet
	 * @param idTrackingWord
	 * @throws RemoveApplicationException
	 */
	private void saveTrackingWord(Long idSnippet, Long idTrackingWord) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(schemaTable(TABLE_RELATION_TW), FIELD_RELATION_ID_SCANNER_RESULT_SNIPPET, 
				FIELD_RELATION_ID_SCANNER_TRACKING_WORD);
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_RELATION_ID_SCANNER_RESULT_SNIPPET, idSnippet)
    				.addParameter(FIELD_RELATION_ID_SCANNER_TRACKING_WORD, idTrackingWord)
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
	 * @param snippet
	 * @param idRaw
	 */
	private void saveTracking(ScannerResultWebSnippet snippet) {
		if (snippet.getTracking() != null && !snippet.getTracking().isEmpty()) {
			snippet.getTracking().forEach(track -> {
				track.setId_scanner_result_web_snippet(snippet.getId());
				track.setId_scanner_raw(snippet.getId_scanner_raw());
				
				try {
					trackDao.save(track);
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving: ", e);
				}
			});
		}
	}
	/**
	 * 
	 * @param snippet
	 * @throws RemoveApplicationException
	 */
	/*public boolean upsert(ScannerResultWebSnippet snippet) throws RemoveApplicationException {
		boolean isNew = false;
//		getLog().info("Updating Web snippet: "+snippet.getId_scanner_result()+" - " +snippet.getLink());
		ScannerResultWebSnippet oldSnippet = getByLink(snippet.getId_scanner_result(), snippet.getLink());
		
		if (oldSnippet == null) {
//			getLog().info("Is new, Saving: "+snippet);
			save(snippet);
			isNew = true;
		} else {
//			getLog().info("Is old, Updating tracking: "+snippet);
			snippet.setId(oldSnippet.getId());
			saveTracking(snippet);
		}
		
		return isNew;
	}*/
	/**
	 * 
	 * @param id_scanner_result
	 * @param link
	 * @return
	 * @throws RemoveApplicationException
	 */
	private ScannerResultWebSnippet getByLink(Long id_scanner_result, String link) throws RemoveApplicationException {
		ScannerResultWebSnippet snippet = null;
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_SCANNER_RESULT, FIELD_LINK);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			snippet = conn.createQuery(QUERY)
					.addParameter(FIELD_ID_SCANNER_RESULT, id_scanner_result)
					.addParameter(FIELD_LINK, link).executeAndFetchFirst(ScannerResultWebSnippet.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return snippet;
	}
	/**
	 * 
	 * @param snippet
	 * @throws RemoveApplicationException
	 */
	public void updateFeeling(ScannerResultWebSnippet snippet) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, snippet.getId(), FIELD_FEELING);
    	try (Connection con = ConnectionDB.getSql2oRO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_FEELING, snippet.getFeeling())
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
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerResultWebSnippet getById(Long id) throws RemoveApplicationException {
		return (ScannerResultWebSnippet) getById(TABLE, id, ScannerResultWebSnippet.class);
	}
	/**
	 * 
	 * @param idResultRaw
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<ScannerResultWebSnippet> listByRaw(Long idResultRaw) throws RemoveApplicationException {
		List<ScannerResultWebSnippet> lst = (List<ScannerResultWebSnippet>) listByField(TABLE, FIELD_ID_SCANNER_RAW, idResultRaw, ScannerResultWebSnippet.class, FIELD_ID);
		
		if (lst != null && !lst.isEmpty()) {
			lst.forEach(snippet -> {
				try {
					snippet.setTracking(ScannerResultWebSnippetTrackDao.init().list(snippet.getId()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error getting tracks:" + snippet, e);
				}
			});
		}
		
		return lst;
	}
	public List<ScannerResultWebSnippet> listByRangeDate(Long idResult, LocalDateTime fromScanTime, LocalDateTime toScanTime) throws RemoveApplicationException {
		@SuppressWarnings("unchecked")
		List<ScannerResultWebSnippet> lstReference = (List<ScannerResultWebSnippet>) listByField(TABLE, FIELD_ID_SCANNER_RESULT, idResult, ScannerResultWebSnippet.class, FIELD_ID);
		List<ScannerResultWebSnippet> lstSnippets = new ArrayList<ScannerResultWebSnippet>();
		
		if (lstReference != null) {
			for (ScannerResultWebSnippet snippet: lstReference) {
				snippet.setTracking(ScannerResultWebSnippetTrackDao.init().listByDateRange(snippet.getId(), fromScanTime, toScanTime));
				
				if (snippet.getTracking() != null && !snippet.getTracking().isEmpty()) {
					snippet.setTrackingWords(scannerTWDao.list(loadTWIdsList(snippet.getId())));
					lstSnippets.add(snippet);
				}
			}
		}
		
		return lstSnippets;
	}
	
	public void deleteRelationByTrackingWord(Long id) throws RemoveApplicationException {
		String QUERY = buildDeleteQuery(schemaTable(TABLE_RELATION_TW), FIELD_RELATION_ID_SCANNER_TRACKING_WORD, id);
		
		try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	
	public void addNewTrackingWordsRelations(ScannerResultWebSnippet snippet) {
		if (snippet != null && snippet.getTrackingWords() != null && !snippet.getTrackingWords().isEmpty()) { 
			snippet.getTrackingWords().forEach(tw -> {
				try {
					saveTrackingWord(snippet.getId(), tw.getId());
				} catch (RemoveApplicationException e) {
					// Relation already exists
				}
			});
		}
	}
	/*************************************************************************************/
	public void saveNestedResults(List<WebSnippetHelper> data) throws RemoveApplicationException {
			try {
				Integer contador = 1;
				Integer page = 1;
				Integer position;
				
			for(int i = 0;i<data.size();i++) {
				ScannerResultWebSnippet snippet = new ScannerResultWebSnippet();;
				ScannerResultWebSnippetTrack track = new ScannerResultWebSnippetTrack();;
				List<ScannerTrackingWord> tw = new ArrayList<ScannerTrackingWord>(); 
				position = i + 1;

				snippet.setId_scanner_result(data.get(i).getId_scanner_result());
				snippet.setId_scanner_raw(data.get(i).getId_scanner_raw());
				snippet.setTitle(data.get(i).getTitle());
				snippet.setLink(data.get(i).getLink());
				snippet.setDomain(data.get(i).getDomain());
				snippet.setDisplayed_link(data.get(i).getDisplayed_link());
				snippet.setSnippet(data.get(i).getSnippet());
				snippet.setDate(data.get(i).getDate());
				snippet.setDate_utc(data.get(i).getDate_utc());
				snippet.setFeeling(data.get(i).getFeeling());

				track.setDate_scan(data.get(i).getDate_scan());
				track.setPage(data.get(i).getPage());
				track.setPosition(position);
				track.setPosition_in_page(contador);
				

				data.get(i).getSnippetTW().forEach(tracking -> {
					ScannerTrackingWord stw = new ScannerTrackingWord();
					stw.setId(tracking.getId());
					stw.setId_scanner(tracking.getId_scanner());
					stw.setWord(tracking.getWord());
					stw.setType(tracking.getType());
					stw.setFeeling(tracking.getFeeling());
					stw.setFeelingObj(tracking.getFeelingObj());
					stw.setMarkToDelete(tracking.isMarkToDelete());
					
					tw.add(stw);
				});
				
				if(page == data.get(i).getPage()) {
					//save(snippet);
					saveNested(snippet, tw, track);
					contador++;
				}else {
					contador = 1;
			    	WebSnippetHelper wsh = new WebSnippetHelper();
	
					track.setPosition(position);
					track.setPosition_in_page(contador);
						
				
					//save(snippet);
					saveNested(snippet, tw, track);
					page = data.get(i).getPage();
					contador++;
				}
			}
		}catch(RemoveApplicationException e) {
			getLog().error("Error saving saveNestedResults: ", e);
		}
	}
	
	/**
	 * 
	 * @param snippet
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerResultWebSnippet saveNested(ScannerResultWebSnippet snippet,List<ScannerTrackingWord> tw, ScannerResultWebSnippetTrack track) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER_RESULT, FIELD_ID_SCANNER_RAW, FIELD_TITLE, FIELD_LINK,
				FIELD_DOMAIN, FIELD_DISPLAYED_LINK, FIELD_SNIPPET, FIELD_DATE, FIELD_DATE_UTC, FIELD_FEELING);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER_RESULT, snippet.getId_scanner_result())
    				.addParameter(FIELD_ID_SCANNER_RAW, snippet.getId_scanner_raw())
    				.addParameter(FIELD_TITLE, snippet.getTitle())
    				.addParameter(FIELD_LINK, snippet.getLink())
    				.addParameter(FIELD_DOMAIN, snippet.getDomain())
    				.addParameter(FIELD_DISPLAYED_LINK, snippet.getDisplayed_link())
    				.addParameter(FIELD_SNIPPET, snippet.getSnippet())
    				.addParameter(FIELD_DATE, snippet.getDate())
    				.addParameter(FIELD_DATE_UTC, snippet.getDate_utc())
    				.addParameter(FIELD_FEELING, snippet.getFeeling() != null ? snippet.getFeeling() : EFeelings.NOT_APPLIED.getTag())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	snippet.setId(idRecord);
    	innerSaveNested(tw,idRecord, track, snippet.getId_scanner_raw());
    	
    	return snippet;
	}
	
	/**
	 * 
	 * @param snippet
	 */
	private void innerSaveNested(List<ScannerTrackingWord> tw,Long IdRecord, ScannerResultWebSnippetTrack track,Long Id_scanner_raw) {
		saveTrackingWordsNested(tw,IdRecord);
		saveTrackingNested(track,IdRecord, Id_scanner_raw);
	}
	
	/**
	 * 
	 * @param snippet
	 */
	private void saveTrackingWordsNested(List<ScannerTrackingWord> tw,Long IdRecord) {
		if (tw != null && !tw.isEmpty()) {
			tw.forEach(result ->{
				try {
					saveTrackingWordNested(IdRecord, result.getId());
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving: ", e);
				}
			});
		}
	}
	
	/**
	 * 
	 * @param idSnippet
	 * @param idTrackingWord
	 * @throws RemoveApplicationException
	 */
	private void saveTrackingWordNested(Long idSnippet, Long idTrackingWord) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(schemaTable(TABLE_RELATION_TW), FIELD_RELATION_ID_SCANNER_RESULT_SNIPPET, 
				FIELD_RELATION_ID_SCANNER_TRACKING_WORD);
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_RELATION_ID_SCANNER_RESULT_SNIPPET, idSnippet)
    				.addParameter(FIELD_RELATION_ID_SCANNER_TRACKING_WORD, idTrackingWord)
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
	 * @param snippet
	 * @param idRaw
	 */
	private void saveTrackingNested(ScannerResultWebSnippetTrack track, Long IdRecord, Long Id_scanner_raw) {
		if (track != null) {
				track.setId_scanner_result_web_snippet(IdRecord);
				track.setId_scanner_raw(Id_scanner_raw);
				
				try {
					trackDao.save(track);
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving: ", e);
				}
		}
	}
	
	private void updateTrackingNested(ScannerResultWebSnippetTrack track, Long IdRecord, Long Id_scanner_raw) {
		if (track != null) {
				track.setId_scanner_result_web_snippet(IdRecord);
				track.setId_scanner_raw(Id_scanner_raw);
				
				try {
					trackDao.update(track, IdRecord,Id_scanner_raw);
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving: ", e);
				}
		}
	}
	
	/*******************************************************************************************/
	/**
	 * 
	 * @param snippet
	 * @throws RemoveApplicationException
	 */public void upsertNestedResult(List<WebSnippetHelper> data) throws RemoveApplicationException {

			boolean isNew = false;
			
			Integer contador = 1;
			Integer page = 1;
			Integer position;
		for(int i = 0;i<data.size();i++) {
			
			ScannerResultWebSnippet snippet = new ScannerResultWebSnippet();;
			ScannerResultWebSnippetTrack track = new ScannerResultWebSnippetTrack();;
			List<ScannerTrackingWord> tw = new ArrayList<ScannerTrackingWord>();
			
			position = i + 1;
			snippet = new ScannerResultWebSnippet();
			snippet.setId_scanner_result(data.get(i).getId_scanner_result());
			snippet.setId_scanner_raw(data.get(i).getId_scanner_raw());
			snippet.setTitle(data.get(i).getTitle());
			snippet.setLink(data.get(i).getLink());
			snippet.setDomain(data.get(i).getDomain());
			snippet.setDisplayed_link(data.get(i).getDisplayed_link());
			snippet.setSnippet(data.get(i).getSnippet());
			snippet.setDate(data.get(i).getDate());
			snippet.setDate_utc(data.get(i).getDate_utc());
			snippet.setFeeling(data.get(i).getFeeling());
			
			track.setDate_scan(data.get(i).getDate_scan());
			track.setPage(data.get(i).getPage());
			track.setPosition(position);
			track.setPosition_in_page(contador);
			
			data.get(i).getSnippetTW().forEach(tracking -> {
				ScannerTrackingWord stw = new ScannerTrackingWord();
				stw.setId(tracking.getId());
				stw.setId_scanner(tracking.getId_scanner());
				stw.setWord(tracking.getWord());
				stw.setType(tracking.getType());
				stw.setFeeling(tracking.getFeeling());
				stw.setFeelingObj(tracking.getFeelingObj());
				stw.setMarkToDelete(tracking.isMarkToDelete());
				
				tw.add(stw);
			});
			
			ScannerResultWebSnippet oldSnippet = getByLink(snippet.getId_scanner_result(), snippet.getLink());
			
			if (oldSnippet == null) {
				if(page == data.get(i).getPage()) {
					
					saveNested(snippet, tw, track);
					contador++;
					isNew = true;
				}else {
					contador = 1;
			    	WebSnippetHelper wsh = new WebSnippetHelper();
	
					track.setPosition(position);
					track.setPosition_in_page(contador);
						
				
					//save(snippet);
					saveNested(snippet, tw, track);
					page = data.get(i).getPage();
					contador++;
				}
				
			} else {
				snippet.setId(oldSnippet.getId());
				if(page == data.get(i).getPage()) {
					
					saveTrackingNested(track,snippet.getId(),snippet.getId_scanner_raw());
					contador++;						
				}else {
					contador = 1;
			    	WebSnippetHelper wsh = new WebSnippetHelper();
	
					track.setPosition(position);
					track.setPosition_in_page(contador);
						
					saveTrackingNested(track,snippet.getId(),snippet.getId_scanner_raw());
					page = data.get(i).getPage();
					contador++;
				}
				
			}											
			
		}

}

	/*public boolean upsertNestedResult(List<WebSnippetHelper> data) throws RemoveApplicationException {
		boolean isNew = false;

		try {
			Integer contador = 1;
			Integer page = 1;
			Integer position;
			ScannerResultWebSnippet snippet;
			ScannerResultWebSnippetTrack track;
			ScannerTrackingWord tw;
		for(int i = 0;i<data.size();i++) {
			position = i + 1;
			snippet = new ScannerResultWebSnippet();
			snippet.setId_scanner_result(data.get(i).getId_scanner_result());
			snippet.setId_scanner_raw(data.get(i).getId_scanner_raw());
			snippet.setTitle(data.get(i).getTitle());
			snippet.setLink(data.get(i).getLink());
			snippet.setDomain(data.get(i).getDomain());
			snippet.setDisplayed_link(data.get(i).getDisplayed_link());
			snippet.setSnippet(data.get(i).getSnippet());
			snippet.setDate(data.get(i).getDate());
			snippet.setDate_utc(data.get(i).getDate_utc());
			snippet.setFeeling(data.get(i).getFeeling());
			
			track = new ScannerResultWebSnippetTrack();
				
			track.setDate_scan(data.get(i).getDate_scan());
			track.setPage(data.get(i).getPage());
			track.setPosition(position);
			track.setPosition_in_page(contador);
			
			tw = new ScannerTrackingWord();
			tw.setId(data.get(i).getId());
			tw.setId_scanner(data.get(i).getId_scanner());
			tw.setWord(data.get(i).getWord());
			tw.setType(data.get(i).getType());
			tw.setFeeling(data.get(i).getFeeling());
			tw.setFeelingObj(data.get(i).getFeelingObj());
			tw.setMarkToDelete(data.get(i).isMarkToDelete());
			
			ScannerResultWebSnippet oldSnippet = getByLink(snippet.getId_scanner_result(), snippet.getLink());
			
			if (oldSnippet == null) {
				if(page == data.get(i).getPage()) {
					
					saveNested(snippet, tw, track);
					contador++;
					isNew = true;
				}else {
					contador = 1;
			    	WebSnippetHelper wsh = new WebSnippetHelper();
	
					track.setPosition(position);
					track.setPosition_in_page(contador);
						
				
					//save(snippet);
					saveNested(snippet, tw, track);
					page = data.get(i).getPage();
					contador++;
				}
				
			} else {
				snippet.setId(oldSnippet.getId());
				
				if(page == data.get(i).getPage()) {
					
					updateTrackingNested(track,snippet.getId(),snippet.getId_scanner_raw());
					contador++;						
				}else {
					contador = 1;
			    	WebSnippetHelper wsh = new WebSnippetHelper();
	
					track.setPosition(position);
					track.setPosition_in_page(contador);
						
					updateTrackingNested(track,snippet.getId(),snippet.getId_scanner_raw());
					page = data.get(i).getPage();
					contador++;
				}
				
			}
		}
	}catch(RemoveApplicationException e) {
		getLog().error("Error saving saveNestedResults: ", e);
	}
		return isNew;
	}*/
	
	
	public boolean upsert(ScannerResultWebSnippet snippet) throws RemoveApplicationException {
		boolean isNew = false;
//		getLog().info("Updating Web snippet: "+snippet.getId_scanner_result()+" - " +snippet.getLink());
		ScannerResultWebSnippet oldSnippet = getByLink(snippet.getId_scanner_result(), snippet.getLink());
		
		if (oldSnippet == null) {
//			getLog().info("Is new, Saving: "+snippet);
			save(snippet);
			isNew = true;
		} else {
//			getLog().info("Is old, Updating tracking: "+snippet);
			snippet.setId(oldSnippet.getId());
			saveTracking(snippet);
		}
		
		return isNew;
	}
}
