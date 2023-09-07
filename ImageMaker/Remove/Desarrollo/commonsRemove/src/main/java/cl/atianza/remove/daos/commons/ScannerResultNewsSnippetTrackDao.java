package cl.atianza.remove.daos.commons;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.PlanClientSuggestion;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippetTrack;
import cl.atianza.remove.models.commons.ScannerResultWebSnippetTrack;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Scanner Result News Snippets Trackings DAOs methods.
 * @author pilin
 *
 */
public class ScannerResultNewsSnippetTrackDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_results_news_snippets_tracks";
	
	private static final String FIELD_ID_SCANNER_RESULT_NEWS_SNIPPET = "id_scanner_result_news_snippet";
	private static final String FIELD_ID_SCANNER_RAW = "id_scanner_raw";
	private static final String FIELD_PAGE = "page";
	private static final String FIELD_POSITION = "position";
	private static final String FIELD_POSITION_IN_PAGE = "position_in_page";
	private static final String FIELD_DATE_SCAN = "date_scan";
	
	public ScannerResultNewsSnippetTrackDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerResultNewsSnippetTrackDao.class), TABLE_NAME);
	}
	
	public static ScannerResultNewsSnippetTrackDao init() throws RemoveApplicationException {
		return new ScannerResultNewsSnippetTrackDao();
	}	
	/**
	 * 
	 * @param idScannerResultSnippet
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<ScannerResultNewsSnippetTrack> list(long idScannerResultSnippet) throws RemoveApplicationException {
		return (List<ScannerResultNewsSnippetTrack>) listByField(TABLE, FIELD_ID_SCANNER_RESULT_NEWS_SNIPPET, idScannerResultSnippet, ScannerResultNewsSnippetTrack.class, FIELD_ID);
	}
	/**
	 * 
	 * @param track
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerResultNewsSnippetTrack save(ScannerResultNewsSnippetTrack track) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER_RESULT_NEWS_SNIPPET, FIELD_ID_SCANNER_RAW, FIELD_DATE_SCAN, FIELD_PAGE, FIELD_POSITION, FIELD_POSITION_IN_PAGE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER_RESULT_NEWS_SNIPPET, track.getId_scanner_result_news_snippet())
    				.addParameter(FIELD_ID_SCANNER_RAW, track.getId_scanner_raw())
    				.addParameter(FIELD_DATE_SCAN, track.getDate_scan())
    				.addParameter(FIELD_PAGE, track.getPage())
    				.addParameter(FIELD_POSITION, track.getPosition())
    				.addParameter(FIELD_POSITION_IN_PAGE, track.getPosition_in_page())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	track.setId(idRecord);
    	return track;
	}

	public List<ScannerResultNewsSnippetTrack> listByDateRange(Long idSnippetResult, LocalDateTime fromScanTime,
			LocalDateTime toScanTime) throws RemoveApplicationException {
		List<ScannerResultNewsSnippetTrack> reference = list(idSnippetResult);
		List<ScannerResultNewsSnippetTrack> result = new ArrayList<ScannerResultNewsSnippetTrack>();
		
		if (reference != null) {
			reference.forEach(track -> {
				if (RemoveDateUtils.isInRangeImplicit(track.getDate_scan(), fromScanTime, toScanTime)) {
					result.add(track);
				}
			});
		}
		
		return result;
	}
	
	public ScannerResultNewsSnippetTrack getById(Long id) throws RemoveApplicationException {
		ScannerResultNewsSnippetTrack track = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER_RESULT_NEWS_SNIPPET, id)  + " ORDER BY "+ FIELD_ID +" DESC " + " limit 1";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			track = (ScannerResultNewsSnippetTrack) conn.createQuery(QUERY).executeAndFetchFirst(ScannerResultNewsSnippetTrack.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return track;
	}
	
	public ScannerResultNewsSnippetTrack getByIdAndDate(Long id) throws RemoveApplicationException {
		ScannerResultNewsSnippetTrack track = null;
		
		//String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER_RESULT_WEB_SNIPPET, id)  + " ORDER BY "+ FIELD_ID +" DESC " + " limit 1";
		
		String QUERY = "SELECT * FROM "+TABLE+" WHERE DATE("+FIELD_DATE_SCAN+")"+" = "+"current_date"+" AND "+FIELD_ID_SCANNER_RESULT_NEWS_SNIPPET+" = "+id;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			track = (ScannerResultNewsSnippetTrack) conn.createQuery(QUERY).executeAndFetchFirst(ScannerResultNewsSnippetTrack.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return track;
	}
	
	public void delete(ScannerResultNewsSnippetTrack track) throws RemoveApplicationException {
		
			String QUERY = buildDeleteQuery(TABLE, FIELD_ID, track.getId());
			
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
