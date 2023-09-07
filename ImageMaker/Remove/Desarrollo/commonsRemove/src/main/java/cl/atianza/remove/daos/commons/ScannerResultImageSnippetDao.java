package cl.atianza.remove.daos.commons;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EFeelings;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.ScannerResultImageSnippet;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Scanner Result Image Snippet DAOs methods.
 * @author pilin
 *
 */
public class ScannerResultImageSnippetDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_results_images_snippets";
	
	private static final String FIELD_ID_SCANNER_RESULT = "id_scanner_result";
	private static final String FIELD_ID_SCANNER_RAW = "id_scanner_raw";
	private static final String FIELD_TITLE = "title";
	private static final String FIELD_LINK = "link";
	private static final String FIELD_DOMAIN = "domain";
	private static final String FIELD_WIDTH = "width";
	private static final String FIELD_HEIGHT = "height";
	private static final String FIELD_IMAGE = "image";
	private static final String FIELD_DESCRIPTION = "description";
	private static final String FIELD_BRAND = "brand";
	private static final String FIELD_FEELING = "feeling";
	
	private static final String TABLE_RELATION_TW = "scanners_results_images_snippets_keyword_tw";
	private static final String FIELD_RELATION_ID_SCANNER_RESULT_SNIPPET = "id_scanner_result_image_snippet";
	private static final String FIELD_RELATION_ID_SCANNER_TRACKING_WORD = "id_scanner_tracking_word";
	
	ScannerTrackingWordsDao scannerTWDao = ScannerTrackingWordsDao.init();
	ScannerResultImageSnippetTrackDao trackDao = ScannerResultImageSnippetTrackDao.init();
	
	public ScannerResultImageSnippetDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerResultImageSnippetDao.class), TABLE_NAME);
	}
	
	public static ScannerResultImageSnippetDao init() throws RemoveApplicationException {
		return new ScannerResultImageSnippetDao();
	}	
	/**
	 * 
	 * @param idScannerResult
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ScannerResultImageSnippet> list(long idScannerResult) throws RemoveApplicationException {
		@SuppressWarnings("unchecked")
		List<ScannerResultImageSnippet> list = (List<ScannerResultImageSnippet>) listByField(TABLE, FIELD_ID_SCANNER_RESULT, idScannerResult, ScannerResultImageSnippet.class, FIELD_ID);
		
		loadInnerObjects(list);
		
		return list;
	}
	/**
	 * 
	 * @param list
	 */
	private void loadInnerObjects(List<ScannerResultImageSnippet> list) {
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
	public ScannerResultImageSnippet save(ScannerResultImageSnippet snippet) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER_RESULT, FIELD_TITLE, FIELD_LINK, FIELD_IMAGE,
				FIELD_DOMAIN, FIELD_WIDTH, FIELD_HEIGHT, FIELD_DESCRIPTION, FIELD_BRAND, FIELD_FEELING, FIELD_ID_SCANNER_RAW);
		Long idRecord = INIT_RECORD_VALUE;
		
		if (snippet.getImage() == null) { // FIXME: This shouldn't happen (Put a path of a notfound or broken link?
			snippet.setImage("");
		}
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER_RESULT, snippet.getId_scanner_result())
    				.addParameter(FIELD_ID_SCANNER_RAW, snippet.getId_scanner_raw())
    				.addParameter(FIELD_TITLE, snippet.getTitle())
    				.addParameter(FIELD_LINK, snippet.getLink())
    				.addParameter(FIELD_DOMAIN, snippet.getDomain())
    				.addParameter(FIELD_WIDTH, snippet.getWidth())
    				.addParameter(FIELD_HEIGHT, snippet.getHeight())
    				.addParameter(FIELD_DESCRIPTION, snippet.getDescription())
    				.addParameter(FIELD_BRAND, snippet.getBrand())
    				.addParameter(FIELD_FEELING, snippet.getFeeling() != null ? snippet.getFeeling() : EFeelings.NOT_APPLIED.getTag())
    				.addParameter(FIELD_IMAGE, snippet.getImage())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		getLog().error("Error saving image snippet:" + snippet.getImage());
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
	private void innerSave(ScannerResultImageSnippet snippet) {
		saveTrackingWords(snippet);
		saveTracking(snippet);
	}
	/**
	 * 
	 * @param snippet
	 */
	private void saveTrackingWords(ScannerResultImageSnippet snippet) {
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
	private void saveTracking(ScannerResultImageSnippet snippet) {
		if (snippet.getTracking() != null && !snippet.getTracking().isEmpty()) {
			snippet.getTracking().forEach(track -> {
				track.setId_scanner_result_image_snippet(snippet.getId());
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
	 * @param idRaw
	 * @throws RemoveApplicationException
	 */
	public void upsert(ScannerResultImageSnippet snippet) throws RemoveApplicationException {
//		getLog().info("Updating Image snippet: "+snippet.getId_scanner_result()+" - " +snippet.getLink());
		ScannerResultImageSnippet oldSnippet = getByLink(snippet.getId_scanner_result(), snippet.getLink());
		
		if (oldSnippet == null) {
//			getLog().info("Is new, Saving: "+snippet);
			save(snippet);
		} else {
//			getLog().info("Is old, Updating tracking: "+snippet);
			snippet.setId(oldSnippet.getId());
			saveTracking(snippet);
		}
	}
	/**
	 * 
	 * @param id_scanner_result
	 * @param link
	 * @return
	 * @throws RemoveApplicationException
	 */
	private ScannerResultImageSnippet getByLink(Long id_scanner_result, String link) throws RemoveApplicationException {
		ScannerResultImageSnippet snippet = null;
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_SCANNER_RESULT, FIELD_LINK);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			snippet = conn.createQuery(QUERY)
					.addParameter(FIELD_ID_SCANNER_RESULT, id_scanner_result)
					.addParameter(FIELD_LINK, link).executeAndFetchFirst(ScannerResultImageSnippet.class);
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
	public void updateFeeling(ScannerResultImageSnippet snippet) throws RemoveApplicationException {
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
	public ScannerResultImageSnippet getById(Long id) throws RemoveApplicationException {
		return (ScannerResultImageSnippet) getById(TABLE, id, ScannerResultImageSnippet.class);
	}
	/**
	 * 
	 * @param idResultRaw
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<ScannerResultImageSnippet> listByRaw(Long idResultRaw) throws RemoveApplicationException {
		List<ScannerResultImageSnippet> lst = (List<ScannerResultImageSnippet>) listByField(TABLE, FIELD_ID_SCANNER_RAW, idResultRaw, ScannerResultImageSnippet.class, FIELD_ID);
		
		if (lst != null && !lst.isEmpty()) {
			lst.forEach(snippet -> {
				try {
					snippet.setTracking(ScannerResultImageSnippetTrackDao.init().list(snippet.getId()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error getting tracks:" + snippet, e);
				}
			});
		}
		
		return lst;
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

	public void addNewTrackingWordsRelations(ScannerResultImageSnippet snippet) {
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
