package cl.atianza.remove.daos.commons;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.ForbiddenWord;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveUtils;

/**
 * Tracking Words DAOs methods.
 * @author pilin
 *
 */
public class ForbiddenWordDao extends RemoveDao {
	private static final String TABLE_NAME = "forbidden_words";
	
	private static final String FIELD_WORD = "word";
	private static final String FIELD_LANGUAGES = "languages";

	public ForbiddenWordDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ForbiddenWordDao.class), TABLE_NAME);
	}
	
	public static ForbiddenWordDao init() throws RemoveApplicationException {
		return new ForbiddenWordDao();
	}	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ForbiddenWord get(long id) throws RemoveApplicationException {
		return (ForbiddenWord) getByField(TABLE, FIELD_ID, id, ForbiddenWord.class);
	}
	/**
	 * 
	 * @param word
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ForbiddenWord get(String word) throws RemoveApplicationException {
		return (ForbiddenWord) getByField(TABLE, FIELD_WORD, word, ForbiddenWord.class);
	}
	/**
	 * 
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<ForbiddenWord> list() throws RemoveApplicationException {
		return (List<ForbiddenWord>) listAll(TABLE, ForbiddenWord.class);
	}
	
	/**
	 * 
	 * @param forbiddenWord
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ForbiddenWord save(ForbiddenWord forbiddenWord) throws RemoveApplicationException {
		if (forbiddenWord != null && !RemoveUtils.isEmptyOrNull(forbiddenWord.getWord())) {
			String QUERY = buildInsertQuery(TABLE, FIELD_WORD, FIELD_LANGUAGES);
			Long idRecord = INIT_RECORD_VALUE;
	    	
	    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
	    		idRecord = (Long) con.createQuery(QUERY, true)
	    				.addParameter(FIELD_WORD, forbiddenWord.getWord().trim().toUpperCase())
	    				.addParameter(FIELD_LANGUAGES, forbiddenWord.getLanguages()).executeUpdate().getKey();
	    	    
	    	    // Remember to call commit() when a transaction is opened,
	    	    // default is to roll back.
	    	    con.commit();
	    	} catch(Exception ex) {
	    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
	    	}
	    	
	    	forbiddenWord.setId(idRecord);
		}
		
		return forbiddenWord;
	}
	/**
	 * 
	 * @param forbiddenWord
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ForbiddenWord update(ForbiddenWord forbiddenWord) throws RemoveApplicationException {
		if (forbiddenWord != null && !RemoveUtils.isEmptyOrNull(forbiddenWord.getWord())) {
			String QUERY = buildUpdateQuery(TABLE, FIELD_ID, forbiddenWord.getId(), FIELD_WORD, FIELD_LANGUAGES);
	    	
	    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
	    		con.createQuery(QUERY)
	    				.addParameter(FIELD_WORD, forbiddenWord.getWord().trim().toUpperCase())
	    				.addParameter(FIELD_LANGUAGES, forbiddenWord.getLanguages()).executeUpdate();
	    	    
	    	    // Remember to call commit() when a transaction is opened,
	    	    // default is to roll back.
	    	    con.commit();
	    	} catch(Exception ex) {
	    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
	    	}
		}
		
		return forbiddenWord;
	}
	
	/**
	 * 
	 * @param id
	 * @throws RemoveApplicationException 
	 */
	public void delete(Long id) throws RemoveApplicationException {
		String QUERY = buildDeleteQuery(TABLE, FIELD_ID, id);
		
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
