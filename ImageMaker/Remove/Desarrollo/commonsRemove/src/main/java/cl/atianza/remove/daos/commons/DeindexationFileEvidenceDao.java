package cl.atianza.remove.daos.commons;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.DeindexationFileEvidence;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * DeindexationFileEvidence DAOs methods.
 * @author pilin
 *
 */
@Deprecated
public class DeindexationFileEvidenceDao extends RemoveDao {
	private static final String TABLE_NAME = "deindexations_files_evidences";
	
	private static final String FIELD_ID_DEINDEXATION = "id_deindexation";
	private static final String FIELD_ID_UPLOADER = "id_uploader";
	private static final String FIELD_UPLOADER_PROFILE = "uploader_profile";
	
	private static final String FIELD_FILE_ADDRESS = "file_address";
	private static final String FIELD_FILE_DESCRIPTION = "file_description";
	private static final String FIELD_FILE_TYPE = "file_type";
	private static final String FIELD_CREATION_DATE = "creation_date";
	
	public DeindexationFileEvidenceDao() throws RemoveApplicationException {
		super(LogManager.getLogger(DeindexationFileEvidenceDao.class), TABLE_NAME);
	}
	
	public static DeindexationFileEvidenceDao init() throws RemoveApplicationException {
		return new DeindexationFileEvidenceDao();
	}	
	
	/**
	 * Get by Scan Owner
	 * @param idDeindexation
	 * @param scannerType 
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<DeindexationFileEvidence> listByDeindexation(long idDeindexation) throws RemoveApplicationException {
		return  (List<DeindexationFileEvidence>) listByField(TABLE, FIELD_ID_DEINDEXATION, idDeindexation, DeindexationFileEvidence.class, FIELD_CREATION_DATE);
	}
	
	public DeindexationFileEvidence getById(Long id) throws RemoveApplicationException {
		return  (DeindexationFileEvidence) getByField(TABLE, FIELD_ID, id, DeindexationFileEvidence.class);
	}
	
	/**
	 * Save DeindexationFileEvidence Object
	 * @param file
	 * @return
	 * @throws RemoveApplicationException
	 */
	public DeindexationFileEvidence save(DeindexationFileEvidence file) throws RemoveApplicationException {
		file.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_DEINDEXATION, FIELD_ID_UPLOADER, FIELD_UPLOADER_PROFILE,
				FIELD_FILE_ADDRESS, FIELD_FILE_DESCRIPTION, FIELD_FILE_TYPE, FIELD_CREATION_DATE);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_DEINDEXATION, file.getId_deindexation())
    				.addParameter(FIELD_ID_UPLOADER, file.getId_uploader())
    				.addParameter(FIELD_UPLOADER_PROFILE, file.getUploader_profile())
    				
    				.addParameter(FIELD_FILE_ADDRESS, file.getFile_address())
    				.addParameter(FIELD_FILE_DESCRIPTION, file.getFile_description())
    				.addParameter(FIELD_FILE_TYPE, file.getFile_type())
    				.addParameter(FIELD_CREATION_DATE, file.getCreation_date())
    				
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	file.setId(idRecord);
    	
    	return file;
	}
	
	public DeindexationFileEvidence getBasicById(Long id) throws RemoveApplicationException {
		return (DeindexationFileEvidence) getByField(TABLE, FIELD_ID, id, DeindexationFileEvidence.class);
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

	public void upsert(DeindexationFileEvidence file) throws RemoveApplicationException {
		if (file.esNuevo()) {
			save(file);
		} else {
			update(file);
		}
	}

	private DeindexationFileEvidence update(DeindexationFileEvidence file) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, FIELD_ID_UPLOADER, FIELD_UPLOADER_PROFILE,
				FIELD_FILE_ADDRESS, FIELD_FILE_DESCRIPTION, FIELD_FILE_TYPE, FIELD_CREATION_DATE);
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_ID_UPLOADER, file.getId_uploader())
    				.addParameter(FIELD_UPLOADER_PROFILE, file.getUploader_profile())
    				
    				.addParameter(FIELD_FILE_ADDRESS, file.getFile_address())
    				.addParameter(FIELD_FILE_DESCRIPTION, file.getFile_description())
    				.addParameter(FIELD_FILE_TYPE, file.getFile_type())
    				.addParameter(FIELD_CREATION_DATE, file.getCreation_date())
    				
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return file;
	}
}
