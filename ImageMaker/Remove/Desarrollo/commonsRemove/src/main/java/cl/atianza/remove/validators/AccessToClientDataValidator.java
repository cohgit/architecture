package cl.atianza.remove.validators;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerImpulseContentDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.daos.commons.ScannerResultDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.models.commons.ScannerImpulseContent;
import cl.atianza.remove.models.commons.ScannerImpulseObservation;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.utils.RemoveTokenAccess;

/**
 * Validate if users have access to clients data and workflow
 * @author Jose Gutierrez
 *
 */
public class AccessToClientDataValidator {
	private Logger log = LogManager.getLogger(AccessToClientDataValidator.class);
	private User user;
	
	
	public AccessToClientDataValidator(RemoveTokenAccess tokenUser) throws RemoveApplicationException {
		super();
		this.user = UserAccessValidator.init(tokenUser).validateUserAccess();
	}
	
	public static AccessToClientDataValidator init(RemoveTokenAccess tokenUser) throws RemoveApplicationException {
		return new AccessToClientDataValidator(tokenUser);
	}
	/**
	 * Validate if tokenUser can operate uuidClient transactions.
	 * @param idClient
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client validateAccessOperation(Long idClient) throws RemoveApplicationException {
		return validateAccessOperation(idClient, false);
	}
	/**
	 * Validate if tokenUser can operate uuidClient transactions.
	 * @param idClient
	 * @param verifyClientActive
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client validateAccessOperation(Long idClient, boolean verifyClientActive) throws RemoveApplicationException {
		log.info("Validating access to client: " + idClient);
		
		Client client = ClientAccessValidator.init(idClient).validateClientAccess(verifyClientActive);
		validateUserClientRelation(client);
		
		return client;
	}
	/**
	 * Validate if tokenUser can operate uuidClient transactions.
	 * @param uuidClient
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client validateAccessOperation(String uuidClient) throws RemoveApplicationException {
		return validateAccessOperation(uuidClient, false);
	}
	/**
	 * Validate if tokenUser can operate uuidClient transactions.
	 * @param uuidClient
	 * @param verifyClientActive
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client validateAccessOperation(String uuidClient, boolean verifyClientActive) throws RemoveApplicationException {
		log.info("Validating access to client: " + uuidClient);
		
		Client client = ClientAccessValidator.init(uuidClient).validateClientAccess(verifyClientActive);
		validateUserClientRelation(client);
		return client;
	}

	/**
	 * Validate if tokenUser can operate scanner transactions by its client owner.
	 * @param scanner
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client validateAccessOperation(Scanner scanner) throws RemoveApplicationException {
		return validateAccessOperation(scanner, false);
	}
	
	/**
	 * Validate if tokenUser can operate scanner transactions by its client owner.
	 * @param scanner
	 * @param verifyClientActive
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client validateAccessOperation(Scanner scanner, boolean verifyClientActive) throws RemoveApplicationException {
		log.info("Validating access to scanner: " + scanner);

		Client client = ClientAccessValidator.init(scanner.getId_owner()).validateClientAccess(verifyClientActive);
		validateUserClientRelation(client);
		ScannerValidator.init(client).validateClientScanner(scanner);
		
		return client;
	}
	
	public Client validateAccessOperationByScannerResult(Long idScannerResult, boolean verifyClientActive) throws RemoveApplicationException {
		ScannerResult result = ScannerResultDao.init().getById(idScannerResult);
		return validateAccessOperation(ScannerDao.init().getBasicById(result.getId_scanner()));
	}

	/**
	 * Validate if tokenUser can operate impulse transactions by its client owner.
	 * @param impulse
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client validateAccessOperation(ScannerImpulse impulse) throws RemoveApplicationException {
		return validateAccessOperation(impulse, false);
	}
	/**
	 * Validate if tokenUser can operate impulse transactions by its client owner.
	 * @param impulse
	 * @param verifyClientActive
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client validateAccessOperation(ScannerImpulse impulse, boolean verifyClientActive) throws RemoveApplicationException {
		log.info("Validating access to impulse: " + impulse);

		Client client = null;
				
		if (impulse.getUuidClient() != null) {
			client = ClientAccessValidator.init(impulse.getUuidClient()).validateClientAccess(verifyClientActive);
		} else if (impulse.getId_scanner() != null && impulse.getId_scanner().longValue() != 0){
			client = ClientAccessValidator.init(ScannerDao.init().getBasicById(impulse.getId_scanner()).getId_owner()).validateClientAccess(verifyClientActive);
		} else if (impulse.getUuidScanner() != null){
			Scanner scanner = ScannerDao.init().getBasicByUuid(impulse.getUuidScanner());
			impulse.setId_scanner(scanner.getId());
			client = ClientAccessValidator.init(scanner.getId_owner()).validateClientAccess(verifyClientActive);
		} else {
			log.error("Impulse doesn't belong to any scanner: " + impulse);
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_AND_SCANNER_DONT_MATCH);
		}
		
		validateUserClientRelation(client);
		ScannerValidator.init(client).validateClientScanner(impulse);
		
		return client;
	}

	/**
	 * Validate if tokenUser can operate impulse observation transactions by its client owner.
	 * @param observation
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client validateAccessOperation(ScannerImpulseObservation observation) throws RemoveApplicationException {
		return validateAccessOperation(observation, false);
	}
	
	public Client validateAccessOperation(ScannerImpulseObservation observation, boolean verifyClientActive) throws RemoveApplicationException {
		log.info("Validating access to observation: " + observation);
		
		ScannerImpulseContent content = ScannerImpulseContentDao.init().getById(observation.getId_scanner_impulse_content());
		ScannerImpulse impulse = ScannerImpulseDao.init().get(content.getId_scanner_impulse());
		Scanner scanner = ScannerDao.init().getBasicById(impulse.getId_scanner());
		
		Client client = ClientAccessValidator.init(scanner.getId_owner()).validateClientAccess(verifyClientActive);
		validateUserClientRelation(client);
		ScannerValidator.init(client).validateClientScanner(impulse);
		
		return ClientDao.init().getById(scanner.getId_owner());
	}
	
	/**
	 * Validate if tokenUser is related with a client for its operations
	 * @param tokenUser
	 * @param client
	 * @throws RemoveApplicationException
	 */
	private void validateUserClientRelation(Client client) throws RemoveApplicationException {
		List<Client> lstClientAssigned = ClientDao.init().listByUser(user.getId());
		
		if (lstClientAssigned != null && !lstClientAssigned.isEmpty()) {
			boolean found = false;
			
			for (Client assigned: lstClientAssigned) {
				if (assigned.getId().longValue() == client.getId().longValue()) {
					found = true;
					break;
				}
			}
			
			if (!found) {
				log.error("Client is not found in user relations: Access Denied: " + user.getUuid() + " - " + client.getUuid());
				RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_USER_NOT_ALLOWED_THIS_CLIENT);
			}
		}
	}
}
