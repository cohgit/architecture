package cl.atianza.remove.helpers;

import java.util.List;

import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.daos.commons.ScannerImpulseObservationDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.models.commons.ScannerImpulseObservation;
import cl.atianza.remove.models.views.ImpulsesTableView;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.validators.ScannerValidator;

public class ImpulseControllerHelper {
	private Logger log;
	
	public ImpulseControllerHelper(Logger log) {
		this.log = log;
	}

	public static ImpulseControllerHelper init(Logger log) {
		return new ImpulseControllerHelper(log);
	}
	
	/**
	 * Load a list of impulses and scanner definition.
	 * @param client
	 * @return
	 * @throws RemoveApplicationException
	 */
	public String processListImpulses(String uuidScanner, Client client, Long idClientPlan, Long idUserAccess, String profile) throws RemoveApplicationException {
		if (client == null) {
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_NULL_VALUE);
		}
		
		// Scanner scanner = ScannerDao.init().getJustConfigByOwner(client.getId(), idClientPlan, EScannerTypes.TRANSFORM.getCode());
		Scanner scanner = ScannerDao.init().getJustConfigByUuid(uuidScanner, EScannerTypes.TRANSFORM.getCode());
		if (scanner != null) {
			List<ScannerImpulse> list = ScannerImpulseDao.init().list(scanner.getId());
			
			list.forEach(impulse -> {
				impulse.checkAccess(idUserAccess, profile);
			});
			
			scanner.setRestrictions(ClientPlanDao.init().getById(idClientPlan));
			return RemoveResponseUtil.buildOk(new ImpulsesTableView(scanner, list));
		}
		
		return RemoveResponseUtil.buildOk(new ImpulsesTableView());
	}
	
	/**
	 * Process saving a new Impulse
	 * @param client
	 * @param impulse
	 * @param token
	 * @return
	 * @throws RemoveApplicationException
	 */
	public String processSaveImpulse(Client client, ScannerImpulse impulse, Long idClientPlan, RemoveTokenAccess token) throws RemoveApplicationException {
		ScannerValidator.init(client).validateScannerImpulse(impulse, idClientPlan);
		Scanner scanner = ScannerDao.init().getJustConfigByUuid(impulse.getUuidScanner(), EScannerTypes.TRANSFORM.getCode());
		
		Long idAuthor = null;
		
		if (token.getProfile().equalsIgnoreCase(EProfiles.CLIENT.getCode())) {
			idAuthor = ClientDao.init().getBasicByUuid(token.getUuidAccount()).getId();
		} else {
			idAuthor = UserDao.init().getBasicByUuid(token.getUuidAccount()).getId();
		}
		
		if (scanner == null) {
			log.error("Scanner not found for this impulse:", impulse);
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		}
		
		impulse.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		impulse.setId_scanner(scanner.getId());
		
		if (impulse.esWordingRequest()) {
			return RemoveResponseUtil.buildOk(ScannerImpulseDao.init().saveAsWordingRequested(impulse));
		} else if (impulse.esOwnWriting()) {
			impulse.getContent().setAuthor_profile(token.getProfile());
			impulse.getContent().setId_author(idAuthor);
			return RemoveResponseUtil.buildOk(ScannerImpulseDao.init().saveAsOwnWritting(impulse));
		} else if (impulse.esPublishedUrl()) {
			impulse.getContent().setAuthor_profile(token.getProfile());
			impulse.getContent().setId_author(idAuthor);
			return RemoveResponseUtil.buildOk(ScannerImpulseDao.init().saveAsPublished(impulse));
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param client
	 * @param impulse
	 * @param idAuthor
	 * @param profileAuthor
	 * @return
	 * @throws RemoveApplicationException
	 */
	public String processUpdateImpulse(Client client, ScannerImpulse impulse, Long idClientPlan, Long idAuthor, String profileAuthor) throws RemoveApplicationException {
		ScannerValidator.init(client).validateScannerImpulse(impulse, idClientPlan);
		if (impulse.esWordingRequest()) {
			impulse.getContent().setAuthor_profile(profileAuthor);
			impulse.getContent().setId_author(idAuthor);
			return RemoveResponseUtil.buildOk(ScannerImpulseDao.init().updateAsClientWordingRequested(impulse));
		} else if (impulse.esOwnWriting()) {
			impulse.getContent().setAuthor_profile(profileAuthor);
			impulse.getContent().setId_author(idAuthor);
			return RemoveResponseUtil.buildOk(ScannerImpulseDao.init().updateAsClientOwnWritting(impulse));
		} else if (impulse.esPublishedUrl()) {
			return RemoveResponseUtil.buildOk(ScannerImpulseDao.init().updateAsPublished(impulse));
		}
		
		return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
	}

	private String askApproval(RemoveTokenAccess token, ScannerImpulseObservation observation) throws RemoveApplicationException {
		return RemoveResponseUtil.buildOk(ScannerImpulseObservationDao.init().toBeApproved(token, observation));
	}
	
	private String approve(RemoveTokenAccess token, ScannerImpulseObservation observation) throws RemoveApplicationException {
//		ScannerImpulseDao.init().updateEstimatedPublishDate(observation.getEstimatedDate(), 
//				ScannerImpulseContentDao.init().getById(observation.getId_scanner_impulse_content()).getId_scanner_impulse());
		
		return RemoveResponseUtil.buildOk(ScannerImpulseObservationDao.init().approve(token, observation));
	}

	private String reject(RemoveTokenAccess token, ScannerImpulseObservation observation) throws RemoveApplicationException {
		return RemoveResponseUtil.buildOk(ScannerImpulseObservationDao.init().reject(token, observation));
	}

	public String processAproverFlow(Client client, RemoveTokenAccess token, ScannerImpulseObservation observation) throws RemoveApplicationException {
		ScannerValidator.init(client).validateScannerImpulseObservation(observation, observation.getIdClientPlan());
		if (observation.esToBeApproved()) return askApproval(token, observation);
		if (observation.esApproved()) return approve(token, observation);
		if (observation.esRejected()) return reject(token, observation);
		
		log.error("Invaild Impulse Observation Status:", observation);
		RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		return null;
	}
}
