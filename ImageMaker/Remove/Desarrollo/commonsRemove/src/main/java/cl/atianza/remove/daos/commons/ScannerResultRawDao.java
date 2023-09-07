package cl.atianza.remove.daos.commons;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.models.commons.ScannerResultRaw;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveFormula;

/**
 * Scanner Result Raw DAOs methods.
 * @author pilin
 *
 */
public class ScannerResultRawDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_results_raw";
	
	private static final String FIELD_ID_SCANNER_RESULT = "id_scanner_result";
	private static final String FIELD_UUID = "uuid";
	private static final String FIELD_RATING_VISUALIZATION = "rating_visualization";
	private static final String FIELD_RATING_REPUTATION = "rating_reputation";
	private static final String FIELD_DATE_SCAN = "date_scan";
	private static final String FIELD_TYPE = "type";
	private static final String FIELD_SCAN_NUMBER = "scan_number";
	private static final String FIELD_RAW = "raw";
	
	public ScannerResultRawDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerResultRawDao.class), TABLE_NAME);
	}
	
	public static ScannerResultRawDao init() throws RemoveApplicationException {
		return new ScannerResultRawDao();
	}	
	
	public ScannerResultRaw getById(Long id) throws RemoveApplicationException {
		ScannerResultRaw raw = (ScannerResultRaw) getByField(TABLE, FIELD_ID, id, ScannerResultRaw.class);
		
		return raw;
	}
	@SuppressWarnings("unchecked")
	public List<ScannerResultRaw> listByResult(Long idScannerResult) throws RemoveApplicationException {
		return (List<ScannerResultRaw>) listByField(TABLE, FIELD_ID_SCANNER_RESULT, idScannerResult, ScannerResultRaw.class, FIELD_DATE_SCAN);
	}

	public ScannerResultRaw save(ScannerResultRaw raw) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER_RESULT, FIELD_UUID, FIELD_RATING_VISUALIZATION, FIELD_RATING_REPUTATION, FIELD_DATE_SCAN, FIELD_TYPE, FIELD_SCAN_NUMBER, FIELD_RAW);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER_RESULT, raw.getId_scanner_result())
    				.addParameter(FIELD_UUID, raw.getUuid())
    				.addParameter(FIELD_RATING_VISUALIZATION, raw.getRating_visualization())
    				.addParameter(FIELD_RATING_REPUTATION, raw.getRating_reputation())
    				.addParameter(FIELD_DATE_SCAN, raw.getDate_scan())
    				.addParameter(FIELD_RAW, raw.getRaw())
    				.addParameter(FIELD_TYPE, raw.getType())
    				.addParameter(FIELD_SCAN_NUMBER, raw.getScan_number())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	raw.setId(idRecord);
    	
    	return raw;
	}

	public void recalculateRatings(Long idResultRaw) throws RemoveApplicationException {
		ScannerResultRaw raw = getById(idResultRaw);
		
		getLog().info("Recalculating Scanner Result Raw:" + raw.getUuid());
		ScannerResult result = ScannerResultDao.init().getById(raw.getId_scanner_result());
		Scanner scanner = ScannerDao.init().getBasicById(result.getId_scanner());
		
		if (scanner != null) {
			scanner.setResults(ScannerResultDao.init().list(scanner.getId()));
		}
		
		scanner.getResults().forEach(res -> {
			try {
				res.setRaws(listByResult(res.getId()));
				RemoveFormula.processResult(scanner, res);
				
				res.getRaws().forEach(_raw -> {
					try {
						updateRatingsValues(_raw);
					} catch (RemoveApplicationException e) {
						getLog().error("Error updating raw results for: " + _raw, e);
					}	
				});
				
				ScannerResultDao.init().updateScores(res);
			} catch (RemoveApplicationException e) {
				getLog().error("Error recalcualting results for: " + res, e);
			}	
		});
		
		/*List<ScannerResultWebSnippet> lstWebs = ScannerResultWebSnippetDao.init().listByRaw(idResultRaw);
		
		/*if (lstWebs != null && !lstWebs.isEmpty()) {
			getLog().info("Recalculating Ratings Raw by Webs");
			RemoveFormula.calulateRawWebsRatings(raw, lstWebs);
		} else {
			List<ScannerResultNewsSnippet> lstNews = ScannerResultNewsSnippetDao.init().listByRaw(idResultRaw);
			if (lstNews != null && !lstNews.isEmpty()) {
				getLog().info("Recalculating Ratings Raw by News");
				RemoveFormula.calulateRawNewsRatings(raw, lstNews);
			} else {
				List<ScannerResultImageSnippet> lstImages = ScannerResultImageSnippetDao.init().listByRaw(idResultRaw);
				if (lstImages != null && !lstImages.isEmpty()) {
					getLog().info("Recalculating Ratings Raw by Images");
					RemoveFormula.calulateRawImagesRatings(raw, lstImages);
				}
			}
		}*/
		/*
		updateRatingsValues(raw);
		
		ScannerResult result = new ScannerResult();
		result.setId(raw.getId_scanner_result());
		result.setRating_reputation(raw.getRating_reputation());
		result.setRating_visualization(raw.getRating_visualization());
		
		ScannerResultDao.init().updateScores(result);*/
	}
	
	public void updateRatingsValues(ScannerResultRaw raw) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, raw.getId(), FIELD_RATING_VISUALIZATION, FIELD_RATING_REPUTATION);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_RATING_VISUALIZATION, raw.getRating_visualization())
    				.addParameter(FIELD_RATING_REPUTATION, raw.getRating_reputation())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public LocalDateTime getPenultScanTime(Long idResult, String search_type, LocalDateTime lastScanTime) throws RemoveApplicationException {
		LocalDateTime reference = null;
		LocalDateTime penultimateDate = null;
		String QUERY = "SELECT " + FIELD_DATE_SCAN + " FROM " + TABLE + 
				" WHERE " + FIELD_DATE_SCAN + " = :"+ FIELD_DATE_SCAN + " AND " + FIELD_ID_SCANNER_RESULT + " = :" + FIELD_ID_SCANNER_RESULT + 
				" AND " + FIELD_TYPE + " = :"+FIELD_TYPE + " ORDER BY " + FIELD_DATE_SCAN + " DESC";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			reference = conn.createQuery(QUERY)
					.addParameter(FIELD_DATE_SCAN, lastScanTime)
					.addParameter(FIELD_ID_SCANNER_RESULT, idResult)
					.addParameter(FIELD_TYPE, search_type)
					.executeAndFetchFirst(LocalDateTime.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (reference != null) {
			QUERY = "SELECT " + FIELD_DATE_SCAN + " FROM " + TABLE + " WHERE " + FIELD_DATE_SCAN + " < :"+ FIELD_DATE_SCAN + 
					" AND " + FIELD_ID_SCANNER_RESULT + " = :" + FIELD_ID_SCANNER_RESULT + 
					" AND " + FIELD_TYPE + " = :"+FIELD_TYPE + " ORDER BY " + FIELD_DATE_SCAN + " DESC";
			
			try (Connection conn = ConnectionDB.getSql2oRO().open()) {
				penultimateDate = conn.createQuery(QUERY)
						.addParameter(FIELD_DATE_SCAN, lastScanTime)
						.addParameter(FIELD_ID_SCANNER_RESULT, idResult)
						.addParameter(FIELD_TYPE, search_type).executeAndFetchFirst(LocalDateTime.class);
	        } catch (Exception ex) {
	        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
	        }
		}
		
		return penultimateDate;
	}
}
