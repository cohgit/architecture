package cl.atianza.remove.daos.commons;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.ScannerImpulseContent;
import cl.atianza.remove.utils.RemoveDao;

/**
 * Scanner Impulse Contents DAOs methods
 * @author pilin
 *
 */
public class ScannerImpulseContentDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_impulses_contents";
	
	private static final String FIELD_ID_SCANNER_IMPULSE = "id_scanner_impulse";
	private static final String FIELD_TITLE = "title";
	private static final String FIELD_CONTENT = "content";
	private static final String FIELD_IMAGE_LINK = "image_link";
	private static final String FIELD_CREATION_DATE = "creation_date";
	private static final String FIELD_ID_AUTHOR = "id_author";
	private static final String FIELD_AUTHOR_PROFILE = "author_profile";
	
	
	public ScannerImpulseContentDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerImpulseContentDao.class), TABLE_NAME);
	}
	
	public static ScannerImpulseContentDao init() throws RemoveApplicationException {
		return new ScannerImpulseContentDao();
	}	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseContent getById(long id) throws RemoveApplicationException {
		ScannerImpulseContent content = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID, id);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			content = conn.createQuery(QUERY).executeAndFetchFirst(ScannerImpulseContent.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		loadInnerData(content);
		
		return content;
	}
	/**
	 * 
	 * @param idScannerImpulse
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseContent getByImpulse(long idScannerImpulse) throws RemoveApplicationException {
		ScannerImpulseContent content = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER_IMPULSE, idScannerImpulse);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			content = conn.createQuery(QUERY).executeAndFetchFirst(ScannerImpulseContent.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		loadInnerData(content);
		
		return content;
	}
	/**
	 * 
	 * @param content
	 * @throws RemoveApplicationException
	 */
	private void loadInnerData(ScannerImpulseContent content) throws RemoveApplicationException {
		if (content != null) {
			loadAuthorData(content);
			content.setObservations(ScannerImpulseObservationDao.init().list(content.getId()));
		}
	}
	
	private void loadAuthorData(ScannerImpulseContent content) throws RemoveApplicationException {
		if (content != null && content.getId_author() != null) {
			if (content.getAuthor_profile().equals(EProfiles.CLIENT.getCode())) {
				Client client = ClientDao.init().getById(content.getId_author());
				
				if (client != null) {
					content.setAuthor_name(client.fullName());	
				}
			} else {
				User user = UserDao.init().getById(content.getId_author());
				
				if (user != null) {
					content.setAuthor_name(user.getName());	
				}
			}	
		}
	}
	/**
	 * 
	 * @param content
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseContent save(ScannerImpulseContent content) throws RemoveApplicationException {
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER_IMPULSE, FIELD_TITLE, FIELD_IMAGE_LINK, FIELD_ID_AUTHOR, 
				FIELD_AUTHOR_PROFILE, FIELD_CONTENT, FIELD_CREATION_DATE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER_IMPULSE, content.getId_scanner_impulse())
    				.addParameter(FIELD_ID_AUTHOR, content.getId_author())
    				.addParameter(FIELD_AUTHOR_PROFILE, content.getAuthor_profile())
    				.addParameter(FIELD_CONTENT, content.getContent())
    				.addParameter(FIELD_CREATION_DATE, content.getCreation_date())
    				.addParameter(FIELD_TITLE, content.getTitle())
    				.addParameter(FIELD_IMAGE_LINK, content.getImage_link())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	content.setId(idRecord);
    	
    	return content;
	}
	/**
	 * 
	 * @param content
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulseContent update(ScannerImpulseContent content) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, content.getId(), FIELD_TITLE, FIELD_IMAGE_LINK, FIELD_CONTENT);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_CONTENT, content.getContent())
    				.addParameter(FIELD_TITLE, content.getTitle())
    				.addParameter(FIELD_IMAGE_LINK, content.getImage_link())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	content.setId(idRecord);
    	
    	return content;
	}
}
