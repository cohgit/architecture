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
import cl.atianza.remove.models.commons.ScannerResultNewsSnippet;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Scanner Result News Snippets DAOs methods.
 * @author pilin
 *
 */
public class ScannerResultNewsSnippetDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_results_news_snippets";
	
	private static final String FIELD_ID_SCANNER_RESULT = "id_scanner_result";
	private static final String FIELD_ID_SCANNER_RAW = "id_scanner_raw";
	private static final String FIELD_TITLE = "title";
	private static final String FIELD_LINK = "link";
	private static final String FIELD_DOMAIN = "domain";
	private static final String FIELD_SOURCE = "source";
	private static final String FIELD_SNIPPET = "snippet";
	private static final String FIELD_THUMBNAIL = "thumbnail";
	private static final String FIELD_DATE = "date";
	private static final String FIELD_DATE_UTC = "date_utc";
	private static final String FIELD_FEELING = "feeling";
	
	private static final String TABLE_RELATION_TW = "scanners_results_news_snippets_keyword_tw";
	private static final String FIELD_RELATION_ID_SCANNER_RESULT_SNIPPET = "id_scanner_result_news_snippet";
	private static final String FIELD_RELATION_ID_SCANNER_TRACKING_WORD = "id_scanner_tracking_word";
	
	ScannerTrackingWordsDao scannerTWDao = ScannerTrackingWordsDao.init();
	ScannerResultNewsSnippetTrackDao trackDao = ScannerResultNewsSnippetTrackDao.init();
	
	public ScannerResultNewsSnippetDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerResultNewsSnippetDao.class), TABLE_NAME);
	}
	
	public static ScannerResultNewsSnippetDao init() throws RemoveApplicationException {
		return new ScannerResultNewsSnippetDao();
	}	
	/**
	 * 
	 * @param idScannerResult
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ScannerResultNewsSnippet> list(long idScannerResult) throws RemoveApplicationException {
		@SuppressWarnings("unchecked")
		List<ScannerResultNewsSnippet> list = (List<ScannerResultNewsSnippet>) listByField(TABLE, FIELD_ID_SCANNER_RESULT, idScannerResult, ScannerResultNewsSnippet.class, FIELD_ID);
		
		loadInnerObjects(list);
		
		return list;
	}
	/**
	 * 
	 * @param list
	 */
	private void loadInnerObjects(List<ScannerResultNewsSnippet> list) {
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
	public ScannerResultNewsSnippet save(ScannerResultNewsSnippet snippet) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER_RESULT, FIELD_ID_SCANNER_RAW, FIELD_TITLE, FIELD_LINK,
				FIELD_DOMAIN, FIELD_SOURCE, FIELD_SNIPPET, FIELD_DATE, FIELD_DATE_UTC, FIELD_FEELING, FIELD_THUMBNAIL);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER_RESULT, snippet.getId_scanner_result())
    				.addParameter(FIELD_ID_SCANNER_RAW, snippet.getId_scanner_raw())
    				.addParameter(FIELD_TITLE, snippet.getTitle())
    				.addParameter(FIELD_LINK, snippet.getLink())
    				.addParameter(FIELD_DOMAIN, snippet.getDomain())
    				.addParameter(FIELD_SOURCE, snippet.getSource())
    				.addParameter(FIELD_SNIPPET, snippet.getSnippet())
    				.addParameter(FIELD_DATE, snippet.getDate())
    				.addParameter(FIELD_DATE_UTC, snippet.getDate_utc())
    				.addParameter(FIELD_FEELING, snippet.getFeeling() != null ? snippet.getFeeling() : EFeelings.NOT_APPLIED.getTag())
    				.addParameter(FIELD_THUMBNAIL, snippet.getThumbnail())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	snippet.setId(idRecord);
    	innerSave(snippet);
    	
    	return snippet;
	}
	/**
	 * 
	 * @param snippet
	 */
	private void innerSave(ScannerResultNewsSnippet snippet) {
		saveTrackingWords(snippet);
		saveTracking(snippet);
	}
	/**
	 * 
	 * @param snippet
	 */
	private void saveTrackingWords(ScannerResultNewsSnippet snippet) {
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
	 */
	private void saveTracking(ScannerResultNewsSnippet snippet) {
		if (snippet.getTracking() != null && !snippet.getTracking().isEmpty()) {
			snippet.getTracking().forEach(track -> {
				if (track.esNuevo()) {
					track.setId_scanner_result_news_snippet(snippet.getId());
					track.setId_scanner_raw(snippet.getId_scanner_raw());
//					getLog().info("Saving tracking: " + track);
					try {
						trackDao.save(track);
					} catch (RemoveApplicationException e) {
						getLog().error("Error saving: ", e);
					}
				} else {
					getLog().info("Is not new, ignore saving tracking: " + track);
				}
			});
		}
	}
	/**
	 * 
	 * @param snippet
	 * @throws RemoveApplicationException
	 * @return
	 */
	public boolean upsert(ScannerResultNewsSnippet snippet) throws RemoveApplicationException {
//		getLog().info("Updating News snippet: "+snippet.getId_scanner_result()+" - " +snippet.getLink());
		boolean isNew = false;
		ScannerResultNewsSnippet oldSnippet = getByLink(snippet.getId_scanner_result(), snippet.getLink());
		
		if (oldSnippet == null) {
//			getLog().info("Is new, Saving: "+snippet);
			save(snippet);
			isNew = true;
		} else {
			snippet.setId(oldSnippet.getId());
//			getLog().info("Is old, Updating Tracking: "+snippet);
			saveTracking(snippet);
		}
		
		return isNew;
	}
	/**
	 * 
	 * @param id_scanner_result
	 * @param link
	 * @return
	 * @throws RemoveApplicationException
	 */
	private ScannerResultNewsSnippet getByLink(Long id_scanner_result, String link) throws RemoveApplicationException {
		ScannerResultNewsSnippet snippet = null;
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_SCANNER_RESULT, FIELD_LINK);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			snippet = conn.createQuery(QUERY)
					.addParameter(FIELD_ID_SCANNER_RESULT, id_scanner_result)
					.addParameter(FIELD_LINK, link).executeAndFetchFirst(ScannerResultNewsSnippet.class);
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
	public void updateFeeling(ScannerResultNewsSnippet snippet) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, snippet.getId(), FIELD_FEELING);
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
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
	public ScannerResultNewsSnippet getById(Long id) throws RemoveApplicationException {
		return (ScannerResultNewsSnippet) getById(TABLE, id, ScannerResultNewsSnippet.class);
	}
	/**
	 * 
	 * @param idResultRaw
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<ScannerResultNewsSnippet> listByRaw(Long idResultRaw) throws RemoveApplicationException {
		List<ScannerResultNewsSnippet> lst = (List<ScannerResultNewsSnippet>) listByField(TABLE, FIELD_ID_SCANNER_RAW, idResultRaw, ScannerResultNewsSnippet.class, FIELD_ID);
		
		if (lst != null && !lst.isEmpty()) {
			lst.forEach(snippet -> {
				try {
					snippet.setTracking(ScannerResultNewsSnippetTrackDao.init().list(snippet.getId()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error getting tracks:" + snippet, e);
				}
			});
		}
		
		return lst;
	}

	public List<ScannerResultNewsSnippet> listByRangeDate(Long idResult, LocalDateTime fromScanTime, LocalDateTime toScanTime) throws RemoveApplicationException {
		@SuppressWarnings("unchecked")
		List<ScannerResultNewsSnippet> lstReference = (List<ScannerResultNewsSnippet>) listByField(TABLE, FIELD_ID_SCANNER_RESULT, idResult, ScannerResultNewsSnippet.class, FIELD_ID);
		List<ScannerResultNewsSnippet> lstSnippets = new ArrayList<ScannerResultNewsSnippet>();
		
		if (lstReference != null) {
			for (ScannerResultNewsSnippet snippet: lstReference) {
				snippet.setTracking(ScannerResultNewsSnippetTrackDao.init().listByDateRange(snippet.getId(), fromScanTime, toScanTime));
				
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

	public void addNewTrackingWordsRelations(ScannerResultNewsSnippet snippet) {
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
}
