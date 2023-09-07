package cl.atianza.remove.daos.commons;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EImpulseStatus;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.ScannerImpulseContent;
import cl.atianza.remove.models.commons.ScannerImpulseObservation;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveTokenAccess;

/**
 * Scanner Impulse Observation DAOs methods.
 * @author pilin
 *
 */
public class ScannerImpulseObservationDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_impulses_observations";
	
	private static final String FIELD_ID_SCANNER_IMPULSE_CONTENT = "id_scanner_impulse_content";
	private static final String FIELD_STATUS = "status";
	private static final String FIELD_CREATION_DATE = "creation_date";
	private static final String FIELD_COMMENTARY = "commentary";
	private static final String FIELD_ID_OWNER = "id_owner";
	private static final String FIELD_OWNER_PROFILE = "owner_profile";
	
	public ScannerImpulseObservationDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerImpulseObservationDao.class), TABLE_NAME);
	}
	
	public static ScannerImpulseObservationDao init() throws RemoveApplicationException {
		return new ScannerImpulseObservationDao();
	}	
	/**
	 * 
	 * @param idScannerContent
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ScannerImpulseObservation> list(long idScannerContent) throws RemoveApplicationException {
		List<ScannerImpulseObservation> list = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER_IMPULSE_CONTENT, idScannerContent) + " ORDER BY " + FIELD_CREATION_DATE + " DESC";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).executeAndFetch(ScannerImpulseObservation.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		loadInnerData(list);
		
		return list;
	}
	/**
	 * 
	 * @param list
	 */
	private void loadInnerData(List<ScannerImpulseObservation> list) {
		if (list != null && !list.isEmpty()) {
			list.forEach(obs -> {
				try {
					loadInnerData(obs);
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading observation: ", obs);
				}
			});
		}
	}
	/**
	 * 
	 * @param obs
	 * @throws RemoveApplicationException 
	 */
	private void loadInnerData(ScannerImpulseObservation obs) throws RemoveApplicationException {
		if (obs != null) {
			loadOwnerData(obs);
		}
	}
	/**
	 * 
	 * @param obs
	 * @throws RemoveApplicationException 
	 */
	private void loadOwnerData(ScannerImpulseObservation obs) throws RemoveApplicationException {
		if (obs.getOwner_profile() != null) {
			if (obs.getOwner_profile().equals(EProfiles.CLIENT.getCode())) {
				Client client = ClientDao.init().getById(obs.getId_owner());
				obs.setOwner_name(client != null ? client.getName() + " " + client.getLastname() : "");
			} else {
				User user = UserDao.init().getById(obs.getId_owner());
				obs.setOwner_name(user != null ? user.getName() : "");
			}
		}
	}
	/**
	 * 
	 * @param observation
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseObservation save(ScannerImpulseObservation observation) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER_IMPULSE_CONTENT, FIELD_ID_OWNER, FIELD_OWNER_PROFILE, FIELD_COMMENTARY,
				FIELD_STATUS, FIELD_CREATION_DATE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER_IMPULSE_CONTENT, observation.getId_scanner_impulse_content())
    				.addParameter(FIELD_ID_OWNER, observation.getId_owner())
    				.addParameter(FIELD_OWNER_PROFILE, observation.getOwner_profile())
    				.addParameter(FIELD_COMMENTARY, observation.getCommentary())
    				.addParameter(FIELD_STATUS, observation.getStatus())
    				.addParameter(FIELD_CREATION_DATE, observation.getCreation_date())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	observation.setId(idRecord);
    	
    	return observation;
	}
	/**
	 * 
	 * @param token
	 * @param observation
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseObservation awaitingDrafting(RemoveTokenAccess token, ScannerImpulseObservation observation) throws RemoveApplicationException {
		return createObservation(token, observation, EImpulseStatus.AWAITING_DRAFTING);
	}
	/**
	 * 
	 * @param token
	 * @param observation
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseObservation toBeApproved(RemoveTokenAccess token, ScannerImpulseObservation observation) throws RemoveApplicationException {
		return createObservation(token, observation, EImpulseStatus.TO_BE_APPROVED);
	}
	/**
	 * 
	 * @param token
	 * @param observation
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseObservation approve(RemoveTokenAccess token, ScannerImpulseObservation observation) throws RemoveApplicationException {
		return createObservation(token, observation, EImpulseStatus.APPROVED);
	}
	/**
	 * 
	 * @param token
	 * @param observation
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseObservation reject(RemoveTokenAccess token, ScannerImpulseObservation observation) throws RemoveApplicationException {
		return createObservation(token, observation, EImpulseStatus.REJECTED);
	}
	/**
	 * 
	 * @param token
	 * @param observation
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseObservation publish(RemoveTokenAccess token, ScannerImpulseObservation observation) throws RemoveApplicationException {
		return createObservation(token, observation, EImpulseStatus.PUBLISHED);
	}
	
	/**
	 * 
	 * @param token
	 * @param observation
	 * @param status
	 * @return
	 * @throws RemoveApplicationException
	 */
	private ScannerImpulseObservation createObservation(RemoveTokenAccess token, ScannerImpulseObservation observation, EImpulseStatus status) throws RemoveApplicationException {
		Long idOwner = null;
		
		if (EProfiles.CLIENT.getCode().equals(token.getProfile())) {
			idOwner = ClientDao.init().getByUuid(token.getUuidAccount()).getId();
		} else {
			idOwner = UserDao.init().getByUuid(token.getUuidAccount()).getId();
		}
		
		observation.setId_owner(idOwner);
		observation.setOwner_profile(token.getProfile());
		observation.setStatus(status.getTag());
		observation.setCreation_date(RemoveDateUtils.nowLocalDateTime());

		save(observation);
		ScannerImpulseContent content = ScannerImpulseContentDao.init().getById(observation.getId_scanner_impulse_content());
		ScannerImpulseDao.init().updateStatus(content.getId_scanner_impulse(), observation.getStatus());
		
		return observation;
	}
	/**
	 * 
	 * @param token
	 * @param idImpulseContent
	 * @throws RemoveApplicationException
	 */
	public void publish(RemoveTokenAccess token, Long idImpulseContent) throws RemoveApplicationException {
		ScannerImpulseObservation obs = new ScannerImpulseObservation();
		obs.setId_scanner_impulse_content(idImpulseContent);
		publish(token, obs);
	}
}
