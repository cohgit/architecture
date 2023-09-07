package cl.atianza.remove.daos.commons;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.WebSnippetHelper;
import cl.atianza.remove.models.admin.PlanClientSuggestion;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.models.commons.ScannerResultWebSnippetTrack;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Scanner Result Web Snippet Tracking DAOs methods.
 * @author pilin
 *
 */
public class ScannerResultWebSnippetTrackDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_results_web_snippets_tracks";
	
	private static final String FIELD_ID_SCANNER_RESULT_WEB_SNIPPET = "id_scanner_result_web_snippet";
	private static final String FIELD_ID_SCANNER_RAW = "id_scanner_raw";
	private static final String FIELD_PAGE = "page";
	private static final String FIELD_POSITION = "position";
	private static final String FIELD_POSITION_IN_PAGE = "position_in_page";
	private static final String FIELD_DATE_SCAN = "date_scan";
		
	public ScannerResultWebSnippetTrackDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerResultWebSnippetTrackDao.class), TABLE_NAME);
	}
	
	public static ScannerResultWebSnippetTrackDao init() throws RemoveApplicationException {
		return new ScannerResultWebSnippetTrackDao();
	}	
	/**
	 * 
	 * @param idScannerResultSnippet
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<ScannerResultWebSnippetTrack> list(long idScannerResultSnippet) throws RemoveApplicationException {
		return (List<ScannerResultWebSnippetTrack>) listByField(TABLE, FIELD_ID_SCANNER_RESULT_WEB_SNIPPET, idScannerResultSnippet, ScannerResultWebSnippetTrack.class, FIELD_ID);
	}
	/**
	 * 
	 * @param track
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerResultWebSnippetTrack save(ScannerResultWebSnippetTrack track) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER_RESULT_WEB_SNIPPET, FIELD_ID_SCANNER_RAW, FIELD_DATE_SCAN, FIELD_PAGE, FIELD_POSITION, FIELD_POSITION_IN_PAGE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER_RESULT_WEB_SNIPPET, track.getId_scanner_result_web_snippet())
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

	public List<ScannerResultWebSnippetTrack> listByDateRange(Long idSnippetResult, LocalDateTime fromScanTime,
			LocalDateTime toScanTime) throws RemoveApplicationException {
		List<ScannerResultWebSnippetTrack> reference = list(idSnippetResult);
		List<ScannerResultWebSnippetTrack> result = new ArrayList<ScannerResultWebSnippetTrack>();
		
		if (reference != null) {
			reference.forEach(track -> {
				if (RemoveDateUtils.isInRangeImplicit(track.getDate_scan(), fromScanTime, toScanTime)) {
					result.add(track);
				}
			});
		}
		
		return result;
	}
	
	public ScannerResultWebSnippetTrack getById(Long id) throws RemoveApplicationException {
		ScannerResultWebSnippetTrack track = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER_RESULT_WEB_SNIPPET, id)  + " ORDER BY "+ FIELD_ID +" DESC " + " limit 1";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			track = (ScannerResultWebSnippetTrack) conn.createQuery(QUERY).executeAndFetchFirst(ScannerResultWebSnippetTrack.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return track;
	}
	
	public ScannerResultWebSnippetTrack getByIdAndDate(Long id) throws RemoveApplicationException {
		ScannerResultWebSnippetTrack track = null;
		
		//String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER_RESULT_WEB_SNIPPET, id)  + " ORDER BY "+ FIELD_ID +" DESC " + " limit 1";
		
		String QUERY = "SELECT * FROM "+TABLE+" WHERE DATE("+FIELD_DATE_SCAN+")"+" = "+"current_date"+" AND "+FIELD_ID_SCANNER_RESULT_WEB_SNIPPET+" = "+id;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			track = (ScannerResultWebSnippetTrack) conn.createQuery(QUERY).executeAndFetchFirst(ScannerResultWebSnippetTrack.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return track;
	}
	
	public void update(ScannerResultWebSnippetTrack track, Long IdRecord, Long Id_scanner_raw) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID_SCANNER_RESULT_WEB_SNIPPET, IdRecord, FIELD_DATE_SCAN,FIELD_PAGE,FIELD_POSITION,FIELD_POSITION_IN_PAGE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
			.addParameter(FIELD_DATE_SCAN, track.getDate_scan())
			.addParameter(FIELD_PAGE, track.getPage())
			.addParameter(FIELD_POSITION, track.getPosition())
			.addParameter(FIELD_POSITION_IN_PAGE, track.getPosition_in_page())
			.executeUpdate();
    
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	
	public void delete(ScannerResultWebSnippetTrack track) throws RemoveApplicationException {
		
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
