package cl.atianza.remove.daos.commons;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.ScannerResultImageSnippetTrack;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippetTrack;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Scanner Result Image Snippet Tracking DAOs methods.
 * @author pilin
 *
 */
public class ScannerResultImageSnippetTrackDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_results_images_snippets_tracks";
	
	private static final String FIELD_ID_SCANNER_RESULT_IMAGE_SNIPPET = "id_scanner_result_image_snippet";
	private static final String FIELD_ID_SCANNER_RAW = "id_scanner_raw";
	private static final String FIELD_PAGE = "page";
	private static final String FIELD_POSITION = "position";
	private static final String FIELD_POSITION_IN_PAGE = "position_in_page";
	private static final String FIELD_DATE_SCAN = "date_scan";
	
	public ScannerResultImageSnippetTrackDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerResultImageSnippetTrackDao.class), TABLE_NAME);
	}
	
	public static ScannerResultImageSnippetTrackDao init() throws RemoveApplicationException {
		return new ScannerResultImageSnippetTrackDao();
	}	
	
	/**
	 * 
	 * @param idScannerResultSnippet
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<ScannerResultImageSnippetTrack> list(long idScannerResultSnippet) throws RemoveApplicationException {
		return (List<ScannerResultImageSnippetTrack>) listByField(TABLE, FIELD_ID_SCANNER_RESULT_IMAGE_SNIPPET, idScannerResultSnippet, ScannerResultImageSnippetTrack.class, FIELD_ID);
	}
	/**
	 * 
	 * @param track
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerResultImageSnippetTrack save(ScannerResultImageSnippetTrack track) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER_RESULT_IMAGE_SNIPPET, FIELD_ID_SCANNER_RAW, FIELD_DATE_SCAN, FIELD_PAGE, FIELD_POSITION, FIELD_POSITION_IN_PAGE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER_RESULT_IMAGE_SNIPPET, track.getId_scanner_result_image_snippet())
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
	
	public ScannerResultImageSnippetTrack getById(Long id) throws RemoveApplicationException {
		ScannerResultImageSnippetTrack track = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER_RESULT_IMAGE_SNIPPET, id)  + " ORDER BY "+ FIELD_ID +" DESC " + " limit 1";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			track = (ScannerResultImageSnippetTrack) conn.createQuery(QUERY).executeAndFetchFirst(ScannerResultImageSnippetTrack.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return track;
	}
}
