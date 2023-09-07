package cl.atianza.remove.daos.commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EFeelings;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerAlert;
import cl.atianza.remove.models.commons.ScannerTrackingWord;
import cl.atianza.remove.models.commons.TrackingWord;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Scanner Tracking Word DAOs methods.
 * @author pilin
 *
 */
public class ScannerTrackingWordsDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_tracking_words";
	
	private static final String FIELD_ID_SCANNER = "id_scanner";
	private static final String FIELD_WORD = "word";
	private static final String FIELD_TYPE = "type";
	private static final String FIELD_FEELING = "feeling";
	
	public ScannerTrackingWordsDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerTrackingWordsDao.class), TABLE_NAME);
	}
	
	public static ScannerTrackingWordsDao init() throws RemoveApplicationException {
		return new ScannerTrackingWordsDao();
	}	
	
	public List<ScannerTrackingWord> getByWordList(Long idScanner, String type) throws RemoveApplicationException {
		List<ScannerTrackingWord> trackingWord = null;
		
		String QUERY = "SELECT * FROM "+TABLE +" WHERE "+ FIELD_ID_SCANNER + " = "+ idScanner + " and "+ FIELD_TYPE + " = '"+ type +"'";
		getLog().info("QUERY URL: "+QUERY);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			trackingWord = conn.createQuery(QUERY).executeAndFetch(ScannerTrackingWord.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return trackingWord;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerTrackingWord get(Long id) throws RemoveApplicationException {
		ScannerTrackingWord trackingword = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID, id);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			trackingword = conn.createQuery(QUERY).executeAndFetchFirst(ScannerTrackingWord.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return trackingword;
	}
	/**
	 * 
	 * @param idScanner
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ScannerTrackingWord> list(long idScanner) throws RemoveApplicationException {
		List<ScannerTrackingWord> list = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER, idScanner);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).executeAndFetch(ScannerTrackingWord.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return list;
	}
	/**
	 * 
	 * @param loadTWIdsList
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ScannerTrackingWord> list(List<Long> loadTWIdsList) throws RemoveApplicationException {
		List<ScannerTrackingWord> list = null;
		
		if (loadTWIdsList != null && !loadTWIdsList.isEmpty()) {
			String QUERY = buildSelectINKeyQuery(TABLE, FIELD_ID, loadTWIdsList);
			try (Connection conn = ConnectionDB.getSql2oRO().open()) {
				list = conn.createQuery(QUERY).executeAndFetch(ScannerTrackingWord.class);
	        } catch (Exception ex) {
	        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
	        }
		}
		
		return list;
	}
	/**
	 * 
	 * @param scanner
	 * @throws RemoveApplicationException
	 */
	public void save(Scanner scanner) throws RemoveApplicationException {
		if (scanner != null && scanner.getTrackingWords() != null && !scanner.getTrackingWords().isEmpty()) {
			List<ScannerTrackingWord> saved = new ArrayList<ScannerTrackingWord>();
			
			scanner.getTrackingWords().forEach(word -> {
				word.setId_scanner(scanner.getId());
				if (scanner.esTransform() && word.esURL()) { // All URL in transform are "bad" (URL to eliminate)
					word.setFeeling(EFeelings.BAD.getTag());
				}
				
				try {
					saved.add(save(word));
				} catch (RemoveApplicationException e) {
					e.printStackTrace();
				}
			});
			
			scanner.setTrackingWords(saved);
		}
	}
	/**
	 * 
	 * @param scanner
	 * @throws RemoveApplicationException
	 */
	public void updateValuesAndFeelings(Scanner scanner) throws RemoveApplicationException {
		if (scanner != null && scanner.getTrackingWords() != null && !scanner.getTrackingWords().isEmpty()) {
			List<ScannerTrackingWord> saved = new ArrayList<ScannerTrackingWord>();
			
			scanner.getTrackingWords().forEach(word -> {
				try {
					if (word.esNuevo()) {
						if (!word.isMarkToDelete()) {
							ScannerTrackingWord oldTW = getByWord(scanner.getId(), word.getType(), word.getWord());
							
							if (oldTW == null) {
								getLog().info("Old tracking Word Not Found:" + oldTW);
								word.setId_scanner(scanner.getId());
								
								saved.add(save(word));	
							} else {
								getLog().info("Old tracking Word found: " + oldTW.getWord() + " - " + word.getFeeling() + " - " + word.getFeelingObj());
								word.setId(oldTW.getId());
								updateFeeling(word);
							}
						}
					} else {
						if (word.isMarkToDelete()) {
							delete(word);
						} else {
							updateFeeling(word);	
						}
					}
				} catch (RemoveApplicationException e) {
					getLog().error("Error upserting tracking word:" + word, e);
				}
			});
			
			ScannerResultDao.init().evaluateAndRefreshNewTrackingWords(scanner.getId(), saved);
			
			scanner.setTrackingWords(saved);
		}
	}
	
	private void delete(ScannerTrackingWord word) throws RemoveApplicationException {
		ScannerResultWebSnippetDao.init().deleteRelationByTrackingWord(word.getId());
		ScannerResultNewsSnippetDao.init().deleteRelationByTrackingWord(word.getId());
		ScannerResultImageSnippetDao.init().deleteRelationByTrackingWord(word.getId());
		
		String QUERY = buildDeleteQuery(TABLE, FIELD_ID, word.getId());
		
		try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	/**
	 * 
	 * @param word
	 * @throws RemoveApplicationException 
	 */
	private void updateFeeling(ScannerTrackingWord word) throws RemoveApplicationException {
		if (word.getFeeling() == null && word.getFeelingObj() != null) {
			word.setFeeling(word.getFeelingObj().getTag());
		}
		
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, word.getId(), FIELD_FEELING);
    	
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY).addParameter(FIELD_FEELING, word.getFeeling())
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
	 * @param word
	 * @return
	 * @throws RemoveApplicationException
	 */
	private ScannerTrackingWord save(ScannerTrackingWord word) throws RemoveApplicationException {
		if (word.getFeeling() == null) {
			if (word.getFeelingObj() != null) {
				word.setFeeling(word.getFeelingObj().getTag());	
			} else {
				word.setFeeling(EFeelings.BAD.getTag());	
			}
		}
		
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER, FIELD_WORD, FIELD_TYPE, FIELD_FEELING);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER, word.getId_scanner())
    				.addParameter(FIELD_TYPE, word.getType())
    				.addParameter(FIELD_WORD, word.getWord())
    				.addParameter(FIELD_FEELING, word.getFeeling())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	try {
    		if (word.esWord()) {
        		TrackingWordDao.init().checkAndSave(word.getWord(), word.getFeeling());
        	}	
    	} catch (RemoveApplicationException e) {
    		getLog().error("Couldn't save tracking word in tracking word management: ", e);
    	}
    	
    	word.setId(idRecord);
    	
    	return word;
	}

	public void countFeelings(TrackingWord tw) throws RemoveApplicationException {
		List<FeelingCounter> counters = null;
		
		String QUERY = "SELECT " + FIELD_FEELING + ", count(" + FIELD_ID + ") from " + TABLE + " WHERE " 
				+ FIELD_TYPE + " = 'WORD' and " + FIELD_WORD + " = '" + tw.getWord() + "' GROUP BY " + FIELD_FEELING;
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			counters = conn.createQuery(QUERY).executeAndFetch(FeelingCounter.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		if (counters != null && !counters.isEmpty()) {
			counters.forEach(count -> {
				if (count.getFeeling().equalsIgnoreCase(EFeelings.BAD.getTag())) {
					tw.setTotalNegative(count.getCount());
				} else if (count.getFeeling().equalsIgnoreCase(EFeelings.GOOD.getTag())) {
					tw.setTotalPositive(count.getCount());
				} else if (count.getFeeling().equalsIgnoreCase(EFeelings.NEUTRAL.getTag())) {
					tw.setTotalNeutral(count.getCount());
				}
			});
		}
	}
	
	private ScannerTrackingWord getByWord(Long idScanner, String type, String word) throws RemoveApplicationException {
		ScannerTrackingWord trackingWord = null;
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_SCANNER, FIELD_TYPE, FIELD_WORD);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			trackingWord = conn.createQuery(QUERY)
					.addParameter(FIELD_ID_SCANNER, idScanner)
					.addParameter(FIELD_TYPE, type)
					.addParameter(FIELD_WORD, word)
					.executeAndFetchFirst(ScannerTrackingWord.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return trackingWord;
	}
	
}

class FeelingCounter {
	private String feeling;
	private long count;
	public FeelingCounter() {
		super();
	}
	public String getFeeling() {
		return feeling;
	}
	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "FeelingCounter [feeling=" + feeling + ", count=" + count + "]";
	}
}