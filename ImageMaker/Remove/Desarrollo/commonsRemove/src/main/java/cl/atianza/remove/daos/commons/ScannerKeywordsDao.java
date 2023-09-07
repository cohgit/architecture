package cl.atianza.remove.daos.commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerKeyword;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Scanner Keywords DAOs methods
 * @author pilin
 *
 */
public class ScannerKeywordsDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_keywords";
	
	private static final String FIELD_ID_SCANNER = "id_scanner";
	private static final String FIELD_ID_SUGGESTED_FROM = "id_suggested_from";
	private static final String FIELD_ID_COUNTRY_SUGGESTED = "id_country_suggested";
	private static final String FIELD_WORD = "word";
	private static final String FIELD_SUGGESTED = "SUGGESTED";
	
	public ScannerKeywordsDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerKeywordsDao.class), TABLE_NAME);
	}
	
	public static ScannerKeywordsDao init() throws RemoveApplicationException {
		return new ScannerKeywordsDao();
	}	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerKeyword get(Long id) throws RemoveApplicationException {
		ScannerKeyword keyword = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID, id);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			keyword = conn.createQuery(QUERY).executeAndFetchFirst(ScannerKeyword.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return keyword;
	}
	/**
	 * 
	 * @param idScanner
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ScannerKeyword> list(long idScanner) throws RemoveApplicationException {
		List<ScannerKeyword> list = null;
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_SCANNER, FIELD_SUGGESTED);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY)
					.addParameter(FIELD_ID_SCANNER, idScanner)
					.addParameter(FIELD_SUGGESTED, false)
					.executeAndFetch(ScannerKeyword.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (list != null) {
			list.forEach(key -> {
				try {
					key.setListSuggested(listByParent(key.getId()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading suggested keywords: " + key, e);
				}
			});
		}
		return list;
	}
	/**
	 * 
	 * @param idParent
	 * @return
	 * @throws RemoveApplicationException
	 */
	private List<ScannerKeyword> listByParent(Long idParent) throws RemoveApplicationException {
		List<ScannerKeyword> list = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SUGGESTED_FROM, idParent);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).executeAndFetch(ScannerKeyword.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (list != null) {
			list.forEach(key -> {
				try {
					key.setCountryObj(CountryDao.init().get(key.getId_country_suggested()));
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading country for key: " + key, e);
				}
				key.setChecked(true);
			});
		}
		
		return list;
	}
	/**
	 * 
	 * @param scanner
	 * @throws RemoveApplicationException
	 */
	public void save(Scanner scanner) throws RemoveApplicationException {
		if (scanner != null && scanner.getKeywords() != null && !scanner.getKeywords().isEmpty()) {
			List<ScannerKeyword> saved = new ArrayList<ScannerKeyword>();
			
			scanner.getKeywords().forEach(keyword -> {
				keyword.setId_scanner(scanner.getId());
				try {
					saved.add(save(keyword));
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving keywords:" + scanner, e);
				}
			});
			
			scanner.setKeywords(saved);
		}
	}
	/**
	 * 
	 * @param scanner
	 * @throws RemoveApplicationException
	 */
	public void saveOnlyNews(Scanner scanner) throws RemoveApplicationException {
		if (scanner != null && scanner.getKeywords() != null && !scanner.getKeywords().isEmpty()) {
			List<ScannerKeyword> saved = new ArrayList<ScannerKeyword>();
			
			scanner.getKeywords().forEach(keyword -> {
				if (keyword.esNuevo()) {
					keyword.setId_scanner(scanner.getId());
					try {
						saved.add(save(keyword));
					} catch (RemoveApplicationException e) {
						getLog().error("Error saving keywords:" + scanner, e);
					}
				}
			});
			
			scanner.setKeywords(saved);
		}
	}
	/**
	 * 
	 * @param keyword
	 * @return
	 * @throws RemoveApplicationException
	 */
	private ScannerKeyword save(ScannerKeyword keyword) throws RemoveApplicationException {
		if (keyword.getId_country_suggested() == null && keyword.getCountryObj() != null && !keyword.getCountryObj().esNuevo()) {
			keyword.setId_country_suggested(keyword.getCountryObj().getId());
		}
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER, FIELD_ID_SUGGESTED_FROM, FIELD_ID_COUNTRY_SUGGESTED, FIELD_WORD, FIELD_SUGGESTED);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER, keyword.getId_scanner())
    				.addParameter(FIELD_ID_SUGGESTED_FROM, keyword.getId_suggested_from())
    				.addParameter(FIELD_ID_COUNTRY_SUGGESTED, keyword.getId_country_suggested())
    				.addParameter(FIELD_WORD, keyword.getWord())
    				.addParameter(FIELD_SUGGESTED, keyword.isSuggested())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	keyword.setId(idRecord);
    	saveSuggested(keyword);
    	
    	return keyword;
	}
	/**
	 * 
	 * @param keyword
	 */
	private void saveSuggested(ScannerKeyword keyword) {
		if (keyword.getListSuggested() != null && !keyword.getListSuggested().isEmpty()) {
			List<ScannerKeyword> sugs = new ArrayList<ScannerKeyword>();
			
			keyword.getListSuggested().forEach(suggested -> {
				if (suggested.isChecked()) {
					suggested.setSuggested(true);
					suggested.setId_scanner(keyword.getId_scanner());
					suggested.setId_suggested_from(keyword.getId());
					
					try {
						save(suggested);
					} catch (RemoveApplicationException e) {
						getLog().error("Error saving suggested keyword:" + suggested, e);
					
					}
					
					sugs.add(suggested);
				}
			});
			
			keyword.setListSuggested(sugs);
		}
	}
}
