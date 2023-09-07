package cl.atianza.remove.daos.commons;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sql2o.Connection;

import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EScannerStatus;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerComment;
import cl.atianza.remove.models.commons.ScannerImpulseContent;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveDateUtils;

public class ScannerCommentDao extends RemoveDao{
	private static final String TABLE_NAME = "scanners_comment";
	
	private static final String FIELD_ID_SCANER = "id_scanner";
	private static final String FIELD_ID_USER = "id_user";
	private static final String FIELD_COMMENT_DATE = "comment_date";
	private static final String FIELD_PROFILE = "profile";
	private static final String FIELD_COMMENT = "comment";

	public ScannerCommentDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerCommentDao.class), TABLE_NAME);
	}
	
	public static ScannerCommentDao init() throws RemoveApplicationException{
		return new ScannerCommentDao();
	}
	
	@SuppressWarnings("unchecked")
	public List<ScannerComment> listAll() throws RemoveApplicationException {
		return (List<ScannerComment>) listAll(TABLE, ScannerComment.class);
	}
	
	public ScannerComment getBasicById(Long id) throws RemoveApplicationException {
		
		ScannerComment comment = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID, id);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			comment = conn.createQuery(QUERY).executeAndFetchFirst(ScannerComment.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		loadInnerAuthorData(comment);
		
		return comment;
	}
	
	public ScannerComment save(ScannerComment scanner_comment, User user) throws RemoveApplicationException {
		scanner_comment.setComment_date(RemoveDateUtils.nowLocalDateTime());
		
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANER, FIELD_ID_USER, FIELD_COMMENT_DATE, 
				FIELD_PROFILE, FIELD_COMMENT);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANER, scanner_comment.getId_scanner())
    				.addParameter(FIELD_ID_USER, user.getId())
    				.addParameter(FIELD_COMMENT_DATE, scanner_comment.getComment_date())
    				.addParameter(FIELD_PROFILE, user.getProfile())
    				.addParameter(FIELD_COMMENT, scanner_comment.getComment())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}    	
    	return scanner_comment;
	}
	
	public List<ScannerComment> list(long idScanner) throws RemoveApplicationException {
		List<ScannerComment> list = listWithoutTracking(idScanner);
				
		return list;
	}
	/**
	 * 
	 * @param idScanner
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ScannerComment> listWithoutTracking(Long idScanner) throws RemoveApplicationException {
		List<ScannerComment> list = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANER, idScanner) + " ORDER BY " + FIELD_COMMENT_DATE + " DESC";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).executeAndFetch(ScannerComment.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		loadInnerData(list);
		
		return list;
	}
	
	public void loadInnerAuthorData(ScannerComment comment) throws RemoveApplicationException {
		if(comment != null && comment.getId_user() != null) {
			User user = UserDao.init().getById(comment.getId_user());
			
			if(user != null) {
				comment.setAuthor_name(user.fullName());
			}
		}
	}
	
	public void loadInnerData(List<ScannerComment> list) throws RemoveApplicationException {
		if (list != null && !list.isEmpty()) {
			list.forEach(comment -> {
				try {
					loadInnerData(comment);
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading observation: ", comment);
				}
			});
		}
	}
	
	public void loadInnerData(ScannerComment comment) throws RemoveApplicationException{
		if (comment != null) {
			loadOwnerData(comment);
		}
	}
	
	public void loadOwnerData(ScannerComment comment) throws RemoveApplicationException{
		if(comment.getProfile() != null) {
			User user = UserDao.init().getById(comment.getId_user());
			comment.setAuthor_name(user != null ? user.fullName() : "");
		}
	}

}
