package cl.atianza.remove.daos.commons;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.models.commons.ScannerResultView;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveJsonUtil;

/**
 * Scanner Result Raw DAOs methods.
 * @author pilin
 *
 */
public class ScannerResultViewDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_results_views";
	
	private static final String FIELD_UUID = "uuid";
	private static final String FIELD_CONTENT = "content";
	
	public ScannerResultViewDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerResultViewDao.class), TABLE_NAME);
	}
	
	public static ScannerResultViewDao init() throws RemoveApplicationException {
		return new ScannerResultViewDao();
	}	
	
	public ScannerResultView getByUuid(String uuid) throws RemoveApplicationException {
		ScannerResultView view = (ScannerResultView) getByField(TABLE, FIELD_UUID, uuid, ScannerResultView.class);
		
		return view;
	}

	public void refreshViewAsync(Long idScannerResult) throws RemoveApplicationException {
		ScannerResult result = ScannerResultDao.init().getById(idScannerResult);
		Scanner scanner = ScannerDao.init().getBasicById(result.getId_scanner());
		refreshViewAsync(scanner.getUuid(), scanner.getType());
	}
	
	public void refreshViewAsync(String uuidScanner, String type) throws RemoveApplicationException {
		new Thread(){
			public void run(){
				getLog().info("Refreshing view for scanner: " + uuidScanner + ", " + type);
				try {
					Scanner scanner = ScannerDao.init().getJustConfigByUuid(uuidScanner, type);
					
					if (scanner != null) {
						scanner.setResults(ScannerResultDao.init().list(scanner.getId()));
						upsert(scanner);
						getLog().info("View for scanner Refreshed: " + uuidScanner + ", " + type);
					} else {
						getLog().error("Cant refresh scanner view for: " + uuidScanner + " - Not Found...");
					}
				} catch (RemoveApplicationException | Exception e) {
					getLog().error("Error refreshing Scanner View: " + uuidScanner, e);
				}
			}
		}.start();
	}
	
	private void upsert(Scanner scanner) throws RemoveApplicationException {
		getLog().info("Upserting view for scanner: " + scanner.getUuid());
		ScannerResultView view = new ScannerResultView();
		view.setUuid(scanner.getUuid());
		view.setContent(RemoveJsonUtil.dataToJson(scanner));
		
		upsert(view);
	}
	
	private void upsert(ScannerResultView view) throws RemoveApplicationException {
		ScannerResultView old = getByUuid(view.getUuid());
		
		if (old == null) {
			save(view);
		} else {
			update(view);
		}
	}
	
	private ScannerResultView save(ScannerResultView view) throws RemoveApplicationException {
		getLog().info("Saving view for scanner: " + view.getUuid());
		String QUERY = buildInsertQuery(TABLE, FIELD_UUID, FIELD_CONTENT);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_UUID, view.getUuid())
    				.addParameter(FIELD_CONTENT, view.getContent())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	view.setId(idRecord);
    	getLog().info("View for scanner Saved: " + view.getUuid());
    	return view;
	}

	private void update(ScannerResultView view) throws RemoveApplicationException {
		getLog().info("Updating view for scanner: " + view.getUuid());
		String QUERY = buildUpdateQuery(TABLE, FIELD_UUID, view.getUuid(), FIELD_CONTENT);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_CONTENT, view.getContent())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	getLog().info("View for scanner updated: " + view.getUuid());
	}
}
