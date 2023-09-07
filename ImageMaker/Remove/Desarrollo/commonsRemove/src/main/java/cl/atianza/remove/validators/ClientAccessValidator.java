package cl.atianza.remove.validators;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.client.ClientAlertMessageDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerResultDao;
import cl.atianza.remove.daos.commons.ScannerResultImageSnippetDao;
import cl.atianza.remove.daos.commons.ScannerResultNewsSnippetDao;
import cl.atianza.remove.daos.commons.ScannerResultWebSnippetDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientAlertMessage;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.models.commons.ScannerResultImageSnippet;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippet;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;
import cl.atianza.remove.utils.RemoveTokenAccess;

/**
 * 
 * @author pilin
 *
 */
public class ClientAccessValidator {
	private Logger log = LogManager.getLogger(ClientAccessValidator.class);
	private Client client;
	
	public ClientAccessValidator(Client client) {
		super();
		this.client = client;
	}
	
	public static ClientAccessValidator init(RemoveTokenAccess token) throws RemoveApplicationException {
		return new ClientAccessValidator(ClientDao.init().getByUuid(token.getUuidAccount()));
	}
	public static ClientAccessValidator init(String uuid) throws RemoveApplicationException {
		return new ClientAccessValidator(ClientDao.init().getByUuid(uuid));
	}
	public static ClientAccessValidator init(Long idClient) throws RemoveApplicationException {
		return new ClientAccessValidator(ClientDao.init().getById(idClient));
	}
	public static ClientAccessValidator init(Client client) {
		return new ClientAccessValidator(client);
	}

	/**
	 * 
	 * @param verifyClientActive
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client validateClientAccess(boolean verifyClientActive) throws RemoveApplicationException {
		if (client == null) {
			log.error("Client is not found: access denied...");
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_NOT_FOUND);
		}
		
		if (verifyClientActive && !client.isActive()) {
			log.error("Client is not active: access denied...");
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_NOT_ACTIVE);
		}
		
		return client;
	}
	
	public void validateReadOnly() throws RemoveApplicationException {
		if (client.isReadOnly()) {
			log.error("Client is read only: access denied... ("+ client.getUuid() + ") - readonly: " + client.isReadOnly());
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_NOT_EMAIL);
			//RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_READ_ONLY);
			
		}
	}
	/**
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Client validateClientAccess() throws RemoveApplicationException {
		return validateClientAccess(true);
	}

	public void validateNotifications(List<ClientAlertMessage> clientsAlerts) throws RemoveApplicationException {
		validateReadOnly();
		
		if (clientsAlerts != null && !clientsAlerts.isEmpty()) {
			for (ClientAlertMessage message : clientsAlerts) {
				if (ClientAlertMessageDao.init().getById(message.getId()).getId_owner().longValue() != this.client.getId().longValue()) {
					log.error("Client Notification doesn't belong to this client:" + message);
					RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_NOTIFICATION_NOT_MATCH);
				}
			}
		}
	}

	public Client validateAccess(ScannerResultWebSnippet webSnippet) throws RemoveApplicationException {
		Client client = validateClientAccess();
		
		if (!webSnippet.esNuevo()) {
			ScannerResultWebSnippet oldSnippet = ScannerResultWebSnippetDao.init().getById(webSnippet.getId());
			validateResultScanner(ScannerResultDao.init().getById(oldSnippet.getId_scanner_result()).getId());
		} else {
			validateResultScanner(webSnippet.getId_scanner_result());
		}
		
		return client;
	}

	

	public Client validateAccess(ScannerResultImageSnippet imageSnippet) throws RemoveApplicationException {
		Client client = validateClientAccess();
		
		if (!imageSnippet.esNuevo()) {
			ScannerResultImageSnippet oldSnippet = ScannerResultImageSnippetDao.init().getById(imageSnippet.getId());
			validateResultScanner(ScannerResultDao.init().getById(oldSnippet.getId_scanner_result()).getId());
		} else {
			validateResultScanner(imageSnippet.getId_scanner_result());
		}
		
		return client;
	}

	public Client validateAccess(ScannerResultNewsSnippet newsSnippet) throws RemoveApplicationException {
		Client client = validateClientAccess();
		
		if (!newsSnippet.esNuevo()) {
			ScannerResultNewsSnippet oldSnippet = ScannerResultNewsSnippetDao.init().getById(newsSnippet.getId());
			validateResultScanner(ScannerResultDao.init().getById(oldSnippet.getId_scanner_result()).getId());
		} else {
			validateResultScanner(newsSnippet.getId_scanner_result());
		}
		
		return client;
	}
	
	private void validateResultScanner(Long idResult) throws RemoveApplicationException {
		ScannerResult result = ScannerResultDao.init().getById(idResult);
		Scanner scanner = ScannerDao.init().getBasicById(result.getId_scanner());
		
		if (scanner.getId_owner().longValue() != client.getId().longValue()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_AND_SCANNER_DONT_MATCH);
		}
	}
}
