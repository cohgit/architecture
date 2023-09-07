package cl.atianza.remove.daos.commons;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.DeindexationUrl;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * DeindexationUrl DAOs methods.
 * @author pilin
 *
 */
@Deprecated
public class DeindexationUrlDao extends RemoveDao {
	private static final String TABLE_NAME = "deindexations_urls";
	
	private static final String FIELD_ID_DEINDEXATION = "id_deindexation";
	private static final String FIELD_URL = "url";
	private static final String FIELD_ASK_GOOGLE = "ask_google";
	private static final String FIELD_ASK_MEDIA = "ask_media";
	private static final String FIELD_PUBLISH_DATE = "publish_date";
	private static final String FIELD_SENT_TO_GOOGLE = "sent_to_google";
	private static final String FIELD_SENT_TO_GOOGLE_DATE = "sent_to_google_date";
	private static final String FIELD_SENT_TO_MEDIA = "sent_to_media";
	private static final String FIELD_SENT_TO_MEDIA_DATE = "sent_to_media_date";
	private static final String FIELD_GOOGLE_APPROVED = "google_approved";
	private static final String FIELD_MEDIA_APPROVED = "media_approved";
	
	public DeindexationUrlDao() throws RemoveApplicationException {
		super(LogManager.getLogger(DeindexationUrlDao.class), TABLE_NAME);
	}
	
	public static DeindexationUrlDao init() throws RemoveApplicationException {
		return new DeindexationUrlDao();
	}	
	
	/**
	 * Get by Scan Owner
	 * @param idOwner
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DeindexationUrl getById(long id) throws RemoveApplicationException {
		return (DeindexationUrl) getByField(TABLE, FIELD_ID, id, DeindexationUrl.class);
	}

	/**
	 * Save DeindexationUrl Object
	 * @param url
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DeindexationUrl save(DeindexationUrl url) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_DEINDEXATION, FIELD_URL, FIELD_PUBLISH_DATE, FIELD_ASK_GOOGLE,
				FIELD_ASK_MEDIA);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_DEINDEXATION, url.getId_deindexation())
    				.addParameter(FIELD_URL, url.getUrl())
    				.addParameter(FIELD_PUBLISH_DATE, url.getPublish_date())
    				.addParameter(FIELD_ASK_GOOGLE, url.isAsk_google())
    				.addParameter(FIELD_ASK_MEDIA, url.isAsk_media())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	url.setId(idRecord);
    	
    	return url;
	}
	
	public DeindexationUrl update(DeindexationUrl url) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, url.getId(), FIELD_ASK_GOOGLE, FIELD_ASK_MEDIA, 
				FIELD_PUBLISH_DATE, FIELD_URL);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_URL, url.getUrl())
    				.addParameter(FIELD_ASK_GOOGLE, url.isAsk_google())
    				.addParameter(FIELD_ASK_MEDIA, url.isAsk_media())
		    		.addParameter(FIELD_PUBLISH_DATE, url.getPublish_date())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return url;
	}

	@SuppressWarnings("unchecked")
	public List<DeindexationUrl> listByDeindexation(Long idDeindexation) throws RemoveApplicationException {
		return (List<DeindexationUrl>) listByField(TABLE, FIELD_ID_DEINDEXATION, idDeindexation, DeindexationUrl.class, FIELD_ID);
	}

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

	public void upsert(DeindexationUrl url) throws RemoveApplicationException {
		if (url.isMarkToDelete() && !url.esNuevo()) {
			delete(url.getId());
		} else {
			if (url.esNuevo()) {
				save(url);
			} else {
				update(url);
			}
		}
	}
	
	public void updateSentGoogle(Long id, boolean sent) throws RemoveApplicationException {
		updateSentGoogle(id, sent, null);
	}
	public void updateSentGoogle(Long id, boolean sent, LocalDate sentDate) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, id, FIELD_SENT_TO_GOOGLE, FIELD_SENT_TO_GOOGLE_DATE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_SENT_TO_GOOGLE, sent)
    				.addParameter(FIELD_SENT_TO_GOOGLE_DATE, sentDate != null ? sentDate : RemoveDateUtils.nowLocalDate())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	
	public void updateSentMedia(Long id, boolean sent) throws RemoveApplicationException {
		updateSentMedia(id, sent, null);
	}
	public void updateSentMedia(Long id, boolean sent, LocalDate sentDate) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, id, FIELD_SENT_TO_MEDIA, FIELD_SENT_TO_MEDIA_DATE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_SENT_TO_MEDIA, sent)
    				.addParameter(FIELD_SENT_TO_MEDIA_DATE, sentDate != null ? sentDate : RemoveDateUtils.nowLocalDate())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	
	public void updateMarkGoogleResponse(Long id, boolean response) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, id, FIELD_GOOGLE_APPROVED);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_GOOGLE_APPROVED, response)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}
	
	public void updateMarkMediaResponse(Long id, boolean response) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, id, FIELD_MEDIA_APPROVED);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_MEDIA_APPROVED, response)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public Integer countByPlan(Long idClientPlan) throws RemoveApplicationException {
		String QUERY = "SELECT COUNT("+TABLE+"."+FIELD_ID+") FROM " + TABLE + ", " + schemaTable(DeindexationDao.TABLE_NAME) 
			+ " WHERE "+TABLE_NAME+"."+FIELD_ID_DEINDEXATION + "=" + DeindexationDao.TABLE_NAME+"."+DeindexationDao.FIELD_ID 
			+ " AND " + DeindexationDao.TABLE_NAME+"."+DeindexationDao.FIELD_ID_CLIENT_PLAN + "=" + idClientPlan; 
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			return conn.createQuery(QUERY).executeAndFetchFirst(Integer.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return 0;
	}
}
