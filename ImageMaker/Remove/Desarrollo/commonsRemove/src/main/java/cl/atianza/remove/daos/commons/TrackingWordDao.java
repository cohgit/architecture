package cl.atianza.remove.daos.commons;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.TrackingWord;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveUtils;

/**
 * Tracking Words DAOs methods.
 * @author pilin
 *
 */
public class TrackingWordDao extends RemoveDao {
	private static final String TABLE_NAME = "tracking_words";
	
	private static final String FIELD_WORD = "word";
	private static final String FIELD_FEELING = "feeling";
	private static final String FIELD_ACTIVE = "active";
	private Boolean active = true;

	public TrackingWordDao() throws RemoveApplicationException {
		super(LogManager.getLogger(TrackingWordDao.class), TABLE_NAME);
	}
	
	public static TrackingWordDao init() throws RemoveApplicationException {
		return new TrackingWordDao();
	}	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public TrackingWord get(long id) throws RemoveApplicationException {
		TrackingWord country = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID, id);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			country = conn.createQuery(QUERY).executeAndFetchFirst(TrackingWord.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return country;
	}
	/**
	 * 
	 * @param word
	 * @return
	 * @throws RemoveApplicationException
	 */
	public TrackingWord get(String word) throws RemoveApplicationException {
		TrackingWord country = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_WORD, word);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			country = conn.createQuery(QUERY).executeAndFetchFirst(TrackingWord.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return country;
	}
	/**
	 * 
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<TrackingWord> list() throws RemoveApplicationException {
		List<TrackingWord> list = (List<TrackingWord>) listAll(TABLE, TrackingWord.class);
		
		if (list != null) {
			ScannerTrackingWordsDao stwDao = ScannerTrackingWordsDao.init();
			list.forEach(tw -> {
				try {
					stwDao.countFeelings(tw);
				} catch (RemoveApplicationException e) {
					getLog().error("Error counting tracking word feelings:" + tw, e);
				}
			});
		}
		
		return list;
	}
	/**
	 * 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<TrackingWord> listActives() throws RemoveApplicationException {
		List<TrackingWord> list = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ACTIVE, active);
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).executeAndFetch(TrackingWord.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return list;
	}
	/**
	 * 
	 * @param trackingWord
	 * @return
	 * @throws RemoveApplicationException
	 */
	public TrackingWord save(TrackingWord trackingWord) throws RemoveApplicationException {
		if (trackingWord != null && !RemoveUtils.isEmptyOrNull(trackingWord.getWord())) {
			String QUERY = buildInsertQuery(TABLE, FIELD_WORD, FIELD_FEELING, FIELD_ACTIVE);
			Long idRecord = INIT_RECORD_VALUE;
	    	
	    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
	    		idRecord = (Long) con.createQuery(QUERY, true)
	    				.addParameter(FIELD_WORD, trackingWord.getWord().trim().toUpperCase())
	    				.addParameter(FIELD_FEELING, trackingWord.getFeeling())
	    				.addParameter(FIELD_ACTIVE, trackingWord.getActive()).executeUpdate().getKey();
	    	    
	    	    // Remember to call commit() when a transaction is opened,
	    	    // default is to roll back.
	    	    con.commit();
	    	} catch(Exception ex) {
	    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
	    	}
	    	
	    	trackingWord.setId(idRecord);
		}
		
		return trackingWord;
	}
	/**
	 * 
	 * @param trackingWord
	 * @return
	 * @throws RemoveApplicationException
	 */
	public TrackingWord update(TrackingWord trackingWord) throws RemoveApplicationException {
		if (trackingWord != null && !RemoveUtils.isEmptyOrNull(trackingWord.getWord())) {
			String QUERY = buildUpdateQuery(TABLE, FIELD_ID, trackingWord.getId(), FIELD_WORD, FIELD_FEELING, FIELD_ACTIVE);
	    	
	    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
	    		con.createQuery(QUERY)
					.addParameter(FIELD_WORD, trackingWord.getWord().trim().toUpperCase())
					.addParameter(FIELD_FEELING, trackingWord.getFeeling())
					.addParameter(FIELD_ACTIVE, trackingWord.getActive()).executeUpdate();
	    	    
	    	    // Remember to call commit() when a transaction is opened,
	    	    // default is to roll back.
	    	    con.commit();
	    	} catch(Exception ex) {
	    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
	    	}
		}
		
		return trackingWord;
	}
	/**
	 * 
	 * @param trackingWord
	 * @return
	 * @throws RemoveApplicationException
	 */
	public TrackingWord saveOrUpdate(TrackingWord trackingWord) throws RemoveApplicationException {
		if (trackingWord != null && !RemoveUtils.isEmptyOrNull(trackingWord.getWord())) {
			TrackingWord old = get(trackingWord.getWord().trim().toUpperCase());
			
			if (old == null) {
				trackingWord = save(trackingWord);
			} else {
				trackingWord.setId(old.getId());
				update(trackingWord);
			}
		}
		
		return trackingWord;
	}
	/**
	 * 
	 * @param list
	 */
	public void saveOrUpdate(List<TrackingWord> list) {
		if (list != null && !list.isEmpty()) {
			list.forEach(trackingWord -> {
				try {
					trackingWord.setActive(true);
					saveOrUpdate(trackingWord);
				} catch (RemoveApplicationException e) {
					getLog().error("Error saving/updating Tracking Word:" + trackingWord, e);
				}
			});
		}
	}

	public void checkAndSave(String word, String feeling) throws RemoveApplicationException {
		if (get(word.toUpperCase()) == null) save(new TrackingWord(word, feeling, true));
	}
}
