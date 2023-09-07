package cl.atianza.remove.daos.commons;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.Deindexation;
import cl.atianza.remove.models.commons.DeindexationHistoric;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * DeindexationHistoric DAOs methods.
 * @author pilin
 *
 */
@Deprecated
public class DeindexationHistoricDao extends RemoveDao {
	private static final String TABLE_NAME = "deindexations_historic";
	
	private static final String FIELD_ID_DEINDEXATION = "id_deindexation";
	private static final String FIELD_ID_OWNER = "id_owner";
	private static final String FIELD_PROFILE = "profile";
	private static final String FIELD_STATUS = "status";
	private static final String FIELD_COMMENTS = "comments";
	private static final String FIELD_CREATION_DATE = "creation_date";
	
	public DeindexationHistoricDao() throws RemoveApplicationException {
		super(LogManager.getLogger(DeindexationHistoricDao.class), TABLE_NAME);
	}
	
	public static DeindexationHistoricDao init() throws RemoveApplicationException {
		return new DeindexationHistoricDao();
	}	
	
	/**
	 * Get by Scan Owner
	 * @param idDeindexation
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<DeindexationHistoric> listByDeindexation(Long idDeindexation) throws RemoveApplicationException {
		return  (List<DeindexationHistoric>) listByField(TABLE, FIELD_ID_DEINDEXATION, idDeindexation, DeindexationHistoric.class, FIELD_CREATION_DATE);
	}
	
	/**
	 * Save DeindexationHistoric Object
	 * @param historic
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DeindexationHistoric save(DeindexationHistoric historic) throws RemoveApplicationException {
		historic.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_DEINDEXATION, FIELD_ID_OWNER, FIELD_PROFILE, 
				FIELD_STATUS, FIELD_COMMENTS, FIELD_CREATION_DATE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_DEINDEXATION, historic.getId_deindexation())
    				.addParameter(FIELD_ID_OWNER, historic.getId_owner())
    				.addParameter(FIELD_PROFILE, historic.getProfile())
    				.addParameter(FIELD_STATUS, historic.getStatus())
    				.addParameter(FIELD_COMMENTS, historic.getComments())
    				.addParameter(FIELD_CREATION_DATE, historic.getCreation_date())
    				
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	historic.setId(idRecord);
    	
    	return historic;
	}
	
	public void save(Deindexation deindexation, Long idOwner, String profile, String comment) throws RemoveApplicationException {
		DeindexationHistoric historic = new DeindexationHistoric();
		
		historic.setId_deindexation(deindexation.getId());
		historic.setStatus(deindexation.getStatus());
		historic.setComments(comment);
		historic.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		historic.setId_owner(idOwner);
		historic.setProfile(profile);
		
		save(historic);
	}
	public void save(Long deindexationId, String status, Long idOwner, String profile, String comment) throws RemoveApplicationException {
		DeindexationHistoric historic = new DeindexationHistoric();
		
		historic.setId_deindexation(deindexationId);
		historic.setStatus(status);
		historic.setComments(comment);
		historic.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		historic.setId_owner(idOwner);
		historic.setProfile(profile);
		
		save(historic);
	}
	
	public DeindexationHistoric getBasicById(Long id) throws RemoveApplicationException {
		return (DeindexationHistoric) getByField(TABLE, FIELD_ID, id, DeindexationHistoric.class);
	}
}
