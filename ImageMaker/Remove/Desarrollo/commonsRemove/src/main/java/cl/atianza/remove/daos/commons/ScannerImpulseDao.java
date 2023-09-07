package cl.atianza.remove.daos.commons;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.sql2o.Connection;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.enums.EImpulseStatus;
import cl.atianza.remove.enums.EImpulseType;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.utils.RemoveDao;
import cl.atianza.remove.utils.RemoveTokenAccess;

/**
 * Scanner Impulse DAOs methods
 * @author pilin
 *
 */
public class ScannerImpulseDao extends RemoveDao {
	private static final String TABLE_NAME = "scanners_impulses";
	
	private static final String FIELD_ID_CREATOR = "id_creator";
	private static final String FIELD_CREATOR_PROFILE = "creator_profile";
	private static final String FIELD_ID_SCANNER = "id_scanner";
	private static final String FIELD_ID_KEYWORD = "id_keyword";
	private static final String FIELD_TYPE = "type";
	private static final String FIELD_REFERENCE_LINK = "reference_link";
	private static final String FIELD_COMMENTS = "comments";
	private static final String FIELD_STATUS = "status";
	private static final String FIELD_ESTIMATED_PUBLISH = "estimated_publish";
	private static final String FIELD_REAL_PUBLISH_LINK = "real_publish_link";
	private static final String FIELD_REAL_PUBLISH_DATE = "real_publish_date";
	private static final String FIELD_INITIAL_POSITION = "initial_position";
	private static final String FIELD_CREATION_DATE = "creation_date";
	private static final String FIELD_TARGET_REACHED = "target_reached";
	
	public ScannerImpulseDao() throws RemoveApplicationException {
		super(LogManager.getLogger(ScannerImpulseDao.class), TABLE_NAME);
	}
	
	public static ScannerImpulseDao init() throws RemoveApplicationException {
		return new ScannerImpulseDao();
	}	
	/**
	 * 
	 * @param idScanner
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<ScannerImpulse> list(long idScanner) throws RemoveApplicationException {
		List<ScannerImpulse> list = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID_SCANNER, idScanner) + " ORDER BY " + FIELD_CREATION_DATE + " DESC";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).executeAndFetch(ScannerImpulse.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		loadInnerData(list);
		
		return list;
	}
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulse get(Long id) throws RemoveApplicationException {
		ScannerImpulse impulse = null;
		
		String QUERY = buildSelectKeyQuery(TABLE, FIELD_ID, id);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			impulse = conn.createQuery(QUERY).executeAndFetchFirst(ScannerImpulse.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		loadInnerData(impulse);
		
		return impulse;
	}
	/**
	 * 
	 * @param list
	 */
	private void loadInnerData(List<ScannerImpulse> list) {
		if (list != null && !list.isEmpty()) {
			list.forEach(impulse -> {
				try {
					loadInnerData(impulse);
				} catch (RemoveApplicationException e) {
					getLog().error("Error loading Impulse: " + impulse, e);
				}
			});
		}
	}
	/**
	 * 
	 * @param impulse
	 * @throws RemoveApplicationException
	 */
	private void loadInnerData(ScannerImpulse impulse) throws RemoveApplicationException {
		if (impulse != null) {
			impulse.setContent(ScannerImpulseContentDao.init().getByImpulse(impulse.getId()));
			impulse.setKeyword(ScannerKeywordsDao.init().get(impulse.getId_keyword()));
			impulse.setTypeObj(EImpulseType.obtain(impulse.getType()));
			impulse.setStatusObj(EImpulseStatus.obtain(impulse.getStatus()));
		}
	}
	/**
	 * 
	 * @param impulse
	 * @throws RemoveApplicationException 
	 */
	
	/**
	 * 
	 * @param impulse
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulse saveAsWordingRequested(ScannerImpulse impulse) throws RemoveApplicationException {
		impulse.setStatus(EImpulseStatus.AWAITING_DRAFTING.getTag());
		
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER, FIELD_ID_CREATOR, FIELD_CREATOR_PROFILE, FIELD_ID_KEYWORD,
				FIELD_TYPE, FIELD_STATUS, FIELD_CREATION_DATE, FIELD_REFERENCE_LINK, FIELD_COMMENTS);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER, impulse.getId_scanner())
    				.addParameter(FIELD_ID_CREATOR, impulse.getId_creator())
    				.addParameter(FIELD_CREATOR_PROFILE, impulse.getCreator_profile())
    				.addParameter(FIELD_ID_KEYWORD, impulse.getId_keyword())
    				.addParameter(FIELD_TYPE, impulse.getType())
    				.addParameter(FIELD_STATUS, impulse.getStatus())
    				.addParameter(FIELD_CREATION_DATE, impulse.getCreation_date())
    				.addParameter(FIELD_REFERENCE_LINK, impulse.getReference_link())
    				.addParameter(FIELD_COMMENTS, impulse.getComments())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	impulse.setId(idRecord);
    	
    	return impulse;
	}
	/**
	 * 
	 * @param impulse
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulse saveAsPublished(ScannerImpulse impulse) throws RemoveApplicationException {
		impulse.setStatus(EImpulseStatus.PUBLISHED.getTag());
		
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER, FIELD_ID_CREATOR, FIELD_CREATOR_PROFILE, FIELD_ID_KEYWORD,
				FIELD_TYPE, FIELD_REAL_PUBLISH_LINK, FIELD_STATUS, FIELD_REAL_PUBLISH_DATE, FIELD_CREATION_DATE, FIELD_COMMENTS);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER, impulse.getId_scanner())
    				.addParameter(FIELD_ID_CREATOR, impulse.getId_creator())
    				.addParameter(FIELD_CREATOR_PROFILE, impulse.getCreator_profile())
    				.addParameter(FIELD_ID_KEYWORD, impulse.getId_keyword())
    				.addParameter(FIELD_TYPE, impulse.getType())
    				.addParameter(FIELD_REAL_PUBLISH_LINK, impulse.getReal_publish_link())
    				.addParameter(FIELD_STATUS, impulse.getStatus())
    				.addParameter(FIELD_REAL_PUBLISH_DATE, impulse.getReal_publish_date())
    				.addParameter(FIELD_CREATION_DATE, impulse.getCreation_date())
    				.addParameter(FIELD_COMMENTS, impulse.getComments())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	impulse.setId(idRecord);
    	
    	if (impulse.getContent() != null) {
    		impulse.getContent().setId_scanner_impulse(impulse.getId());
    		ScannerImpulseContentDao.init().save(impulse.getContent());
    	}
    	
    	updateEstimatedPublishDate(impulse.getReal_publish_date().toLocalDate().withDayOfMonth(1), impulse.getId());
    	
    	return impulse;
	}
	/**
	 * 
	 * @param impulse
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulse saveAsOwnWritting(ScannerImpulse impulse) throws RemoveApplicationException {
		impulse.setStatus(EImpulseStatus.DRAFT.getTag());
		
		String QUERY = buildInsertQuery(TABLE, FIELD_ID_SCANNER, FIELD_ID_CREATOR, FIELD_CREATOR_PROFILE, FIELD_ID_KEYWORD,
				FIELD_TYPE, FIELD_STATUS, FIELD_CREATION_DATE, FIELD_COMMENTS);
		Long idRecord = INIT_RECORD_VALUE;
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		idRecord = (Long) con.createQuery(QUERY, true)
    				.addParameter(FIELD_ID_SCANNER, impulse.getId_scanner())
    				.addParameter(FIELD_ID_CREATOR, impulse.getId_creator())
    				.addParameter(FIELD_CREATOR_PROFILE, impulse.getCreator_profile())
    				.addParameter(FIELD_ID_KEYWORD, impulse.getId_keyword())
    				.addParameter(FIELD_TYPE, impulse.getType())
    				.addParameter(FIELD_STATUS, impulse.getStatus())
    				.addParameter(FIELD_CREATION_DATE, impulse.getCreation_date())
    				.addParameter(FIELD_COMMENTS, impulse.getComments())
    				.executeUpdate().getKey();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	impulse.setId(idRecord);
    	
    	if (impulse.getContent() != null) {
    		impulse.getContent().setId_scanner_impulse(impulse.getId());
    		ScannerImpulseContentDao.init().save(impulse.getContent());
    	}
    	
    	return impulse;
	}
	
	public ScannerImpulse updateAsClientWordingRequested(ScannerImpulse impulse) throws RemoveApplicationException {
		if (impulse.getContent().esNuevo()) {
			impulse.getContent().setId_scanner_impulse(impulse.getId());
			ScannerImpulseContentDao.init().save(impulse.getContent());
		} else {
			ScannerImpulseContentDao.init().update(impulse.getContent());
		}
    	
    	return impulse;
	}

	public ScannerImpulse updateAsClientOwnWritting(ScannerImpulse impulse) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, impulse.getId(), FIELD_ID_KEYWORD, FIELD_TYPE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_ID_KEYWORD, impulse.getId_keyword())
    				.addParameter(FIELD_TYPE, impulse.getType())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	ScannerImpulseContentDao.init().update(impulse.getContent());
    	
    	return impulse;
	}

	public ScannerImpulse updateAsPublished(ScannerImpulse impulse) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, impulse.getId(), FIELD_REAL_PUBLISH_DATE, FIELD_REAL_PUBLISH_LINK);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_REAL_PUBLISH_DATE, impulse.getReal_publish_date())
    				.addParameter(FIELD_REAL_PUBLISH_LINK, impulse.getReal_publish_link())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	return impulse;
	}
	/**
	 * 
	 * @param token
	 * @param impulse
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ScannerImpulse updatePublishData(RemoveTokenAccess token, ScannerImpulse impulse) throws RemoveApplicationException {
		impulse.setStatusObj(EImpulseStatus.PUBLISHED);
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, impulse.getId(), FIELD_REAL_PUBLISH_LINK, FIELD_REAL_PUBLISH_DATE);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY)
    				.addParameter(FIELD_REAL_PUBLISH_LINK, impulse.getReal_publish_link())
    				.addParameter(FIELD_REAL_PUBLISH_DATE, impulse.getReal_publish_date())
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
    	
    	if (impulse.getContent() != null) {
    		ScannerImpulseObservationDao.init().publish(token, impulse.getContent().getId());
    	}
    	if (impulse.getEstimated_publish() == null) {
    		updateEstimatedPublishDate(impulse.getReal_publish_date().toLocalDate().withDayOfMonth(1), impulse.getId());
    	}
    	
    	return impulse;
	}
	/**
	 * 
	 * @param id
	 * @param status
	 * @throws RemoveApplicationException
	 */
	public void updateStatus(Long id, String status) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, id, FIELD_STATUS);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY, true)
    				.addParameter(FIELD_STATUS, status)
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
	 * @param estimatedDateMonth
	 * @param id
	 * @throws RemoveApplicationException
	 */
	public void updateEstimatedPublishDate(LocalDate estimatedDateMonth, Long id) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, id, FIELD_ESTIMATED_PUBLISH);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY, true)
    				.addParameter(FIELD_ESTIMATED_PUBLISH, estimatedDateMonth)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public List<ScannerImpulse> listPublishedByScanner(Long idScanner) throws RemoveApplicationException {
		List<ScannerImpulse> list = null;
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_SCANNER, FIELD_STATUS) + " ORDER BY " + FIELD_CREATION_DATE + " DESC";
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			list = conn.createQuery(QUERY).addParameter(FIELD_ID_SCANNER, idScanner)
					.addParameter(FIELD_STATUS, EImpulseStatus.PUBLISHED.getTag()).executeAndFetch(ScannerImpulse.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		loadInnerData(list);
		
		return list;
	}

	private void updateInitialPosition(Long id, int position) throws RemoveApplicationException {
		String QUERY = buildUpdateQuery(TABLE, FIELD_ID, id, FIELD_INITIAL_POSITION);
		
    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
    		con.createQuery(QUERY, true)
    				.addParameter(FIELD_INITIAL_POSITION, position)
    				.executeUpdate();
    	    
    	    // Remember to call commit() when a transaction is opened,
    	    // default is to roll back.
    	    con.commit();
    	} catch(Exception ex) {
    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
    	}
	}

	public void verifyImpulseInitialPosition(Long idScanner, Long idKeyword, String link, Integer position) throws RemoveApplicationException {
		ScannerImpulse impulse = findForParameters(idScanner, idKeyword, link);
		
		if (impulse != null && impulse.getInitial_position() == null) {
			updateInitialPosition(impulse.getId(), position);
		}
	}

	private ScannerImpulse findForParameters(Long idScanner, Long idKeyword, String link) throws RemoveApplicationException {
		ScannerImpulse impulse = null;
		
		String QUERY = buildSelectManyWheres(TABLE, FIELD_ID_SCANNER, FIELD_ID_KEYWORD, FIELD_REAL_PUBLISH_LINK);
		
		try (Connection conn = ConnectionDB.getSql2oRO().open()) {
			impulse = conn.createQuery(QUERY)
					.addParameter(FIELD_ID_SCANNER, idScanner)
					.addParameter(FIELD_ID_KEYWORD, idKeyword)
					.addParameter(FIELD_REAL_PUBLISH_LINK, link).executeAndFetchFirst(ScannerImpulse.class);
        } catch (Exception ex) {
        	catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
        }
		
		return impulse;
	}
	
	public void markTargetReached(Long idImpulse) throws RemoveApplicationException {
		ScannerImpulse impulse = get(idImpulse);
		
		if (!impulse.isTarget_reached()) {
			String QUERY = buildUpdateQuery(TABLE, FIELD_ID, idImpulse, FIELD_TARGET_REACHED);
			
	    	try (Connection con = ConnectionDB.getSql2oWO().beginTransaction()) {
	    		con.createQuery(QUERY, true)
	    				.addParameter(FIELD_TARGET_REACHED, true)
	    				.executeUpdate();
	    	    
	    	    // Remember to call commit() when a transaction is opened,
	    	    // default is to roll back.
	    	    con.commit();
	    	} catch(Exception ex) {
	    		catchSeveralException(ex, EMessageBundleKeys.ERROR_DATABASE);
	    	}
		}
	}
}
