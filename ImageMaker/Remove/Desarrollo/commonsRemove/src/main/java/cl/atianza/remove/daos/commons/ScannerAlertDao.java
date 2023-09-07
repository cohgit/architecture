package cl.atianza.remove.daos.commons;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EAlertPeriocity;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EScannerStatus;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerAlert;
import cl.atianza.remove.utils.RemoveDao;

public class ScannerAlertDao extends RemoveDao{
	
	private static final String TABLE_NAME = "scanners_alerts";
	
	private static final String FIELD_ID_SCANER = "id_scanner";
	private static final String FIELD_PERIOCITY = "periocity";
	private static final String FIELD_PAGE_OBJECT = "page_object";
	private static final String FIELD_NEW_CONTENT_DETECTED = "new_content_detected";
	private static final String FIELD_NEGATIVE_CONTENT_DETECTED = "negative_content_detected";
	private static final String FIELD_DISPLACED_URL = "displaced_url";
	private static final String FIELD_MATCHING_TRACKING_WORD= "matching_tracking_word";
	private static final String FIELD_MATCHING_KEYWORD= "matching_keyword";
	private static final String FIELD_ID_TRACKING = "id_tracking";
	private static final String FIELD_TRACKING_WORD = "tracking_word";
	private static final String FIELD_ID_KEYWORD = "id_keyword";
	private static final String FIELD_KEYWORD = "keyword";
	private static final String FIELD_STATUS = "status";

	public ScannerAlertDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerAlertDao.class), TABLE_NAME);
	}
	
	public static ScannerAlertDao init() throws RemoveApplicationException {
		return new ScannerAlertDao();
	}	
	
	public ScannerAlert getBasicById(Long id_scanner) throws RemoveApplicationException {
		ScannerAlert alert = (ScannerAlert) getByField(TABLE, FIELD_ID_SCANER, id_scanner, ScannerAlert.class);
		if(alert == null)
			alert = new ScannerAlert();
		
		return alert;
	}
	
	
	public List<ScannerAlert> listRecurrentsDaily() throws RemoveApplicationException {
		List<ScannerAlert> scanners = null;
		
		String QUERY = "SELECT * FROM " +  TABLE +" WHERE "+FIELD_STATUS+" = true "+ "AND periocity = 'daily'";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			scanners = conn.createQuery(QUERY).executeAndFetch(ScannerAlert.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return scanners;
	}
	
	public List<ScannerAlert> listRecurrentsWeekly() throws RemoveApplicationException {
		List<ScannerAlert> scanners = null;
		
		String QUERY = "SELECT * FROM " +  TABLE +" WHERE "+FIELD_STATUS+" = true "+ "AND periocity = 'weekly'";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			scanners = conn.createQuery(QUERY).executeAndFetch(ScannerAlert.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return scanners;
	}
	
	public ScannerAlert save(ScannerAlert alert) throws RemoveApplicationException {
		
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANER, FIELD_PERIOCITY, FIELD_PAGE_OBJECT, 
				FIELD_NEW_CONTENT_DETECTED, FIELD_NEGATIVE_CONTENT_DETECTED, FIELD_DISPLACED_URL,FIELD_MATCHING_TRACKING_WORD,FIELD_MATCHING_KEYWORD,
				FIELD_ID_TRACKING, FIELD_TRACKING_WORD, FIELD_ID_KEYWORD, FIELD_KEYWORD,FIELD_STATUS);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANER, alert.getId_scanner())
    				.addParameter(FIELD_PERIOCITY, alert.getPeriocity())
    				.addParameter(FIELD_PAGE_OBJECT, alert.getPage_object())
    				.addParameter(FIELD_NEW_CONTENT_DETECTED, alert.isNew_content_detected())
    				.addParameter(FIELD_NEGATIVE_CONTENT_DETECTED, alert.isNegative_content_detected())
    				.addParameter(FIELD_DISPLACED_URL, alert.isDisplaced_url())
    				.addParameter(FIELD_MATCHING_TRACKING_WORD, alert.isMatching_tracking_word())
    				.addParameter(FIELD_MATCHING_KEYWORD, alert.isMatching_keyword())
    				.addParameter(FIELD_ID_TRACKING, alert.getId_tracking())
    				.addParameter(FIELD_TRACKING_WORD, alert.getTracking_word())
    				.addParameter(FIELD_ID_KEYWORD, alert.getId_keyword())
    				.addParameter(FIELD_KEYWORD, alert.getKeyword())
    				.addParameter(FIELD_STATUS, alert.isStatus())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}    	
    	
    	//alert.setId(idRecord);
    	return alert;
    	
	}
	

	public ScannerAlert update(ScannerAlert alert) throws RemoveApplicationException {
		
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID_SCANER, alert.getId_scanner(), FIELD_PERIOCITY, FIELD_PAGE_OBJECT, 
				FIELD_NEW_CONTENT_DETECTED, FIELD_NEGATIVE_CONTENT_DETECTED, FIELD_DISPLACED_URL,FIELD_MATCHING_TRACKING_WORD,FIELD_MATCHING_KEYWORD,
				FIELD_ID_TRACKING, FIELD_TRACKING_WORD, FIELD_ID_KEYWORD, FIELD_KEYWORD,FIELD_STATUS);
		
		try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
			 con.createQuery(QUERY, true)
    				//.addParameter(FIELD_ID_SCANER, alert.getId_scanner())
    				.addParameter(FIELD_PERIOCITY, alert.getPeriocity())
    				.addParameter(FIELD_PAGE_OBJECT, alert.getPage_object())
    				.addParameter(FIELD_NEW_CONTENT_DETECTED, alert.isNew_content_detected())
    				.addParameter(FIELD_NEGATIVE_CONTENT_DETECTED, alert.isNegative_content_detected())
    				.addParameter(FIELD_DISPLACED_URL, alert.isDisplaced_url())
    				.addParameter(FIELD_MATCHING_TRACKING_WORD, alert.isMatching_tracking_word())
    				.addParameter(FIELD_MATCHING_KEYWORD, alert.isMatching_keyword())
    				.addParameter(FIELD_ID_TRACKING, alert.getId_tracking())
    				.addParameter(FIELD_TRACKING_WORD, alert.getTracking_word())
    				.addParameter(FIELD_ID_KEYWORD, alert.getId_keyword())
    				.addParameter(FIELD_KEYWORD, alert.getKeyword())
    				.addParameter(FIELD_STATUS, alert.isStatus())
    				.executeUpdate();
			 
		    
			//.addParameter(FIELD_REFERENCE_LINK_LOGO, client.getReference_link_logo())
		    // Remember to call commit() when a transaction is opened,
		    // default is to roll back.
		    con.commit();
		} catch(Exception ex) {
			catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
		}
		
	    return alert;
	}
	
	public void updateStatus(Long id_scanner, boolean status) throws RemoveApplicationException {
		
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID_SCANER, id_scanner, FIELD_STATUS);
		
		try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
			 con.createQuery(QUERY, true)
    				//.addParameter(FIELD_ID_SCANER, alert.getId_scanner())
    				.addParameter(FIELD_STATUS, status)
    				.executeUpdate();
		    
			//.addParameter(FIELD_REFERENCE_LINK_LOGO, client.getReference_link_logo())
		    // Remember to call commit() when a transaction is opened,
		    // default is to roll back.
		    con.commit();
		} catch(Exception ex) {
			catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
		}

	}

}
