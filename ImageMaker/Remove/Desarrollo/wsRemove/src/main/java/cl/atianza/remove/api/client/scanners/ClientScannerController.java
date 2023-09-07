package cl.atianza.remove.api.client.scanners;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ScannerControllerHelper;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.ClientAccessValidator;
import cl.atianza.remove.validators.ScannerValidator;
import spark.Request;
import spark.Response;

/**
 * REST scanner service for scanner operations.
 * @author pilin
 *
 */
public class ClientScannerController extends RestController {
	public ClientScannerController() {
		super(ERestPath.CLIENT_SCANNER, LogManager.getLogger(ClientScannerController.class));
	}

	@Override
	public Object list(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LIST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			String scannerType = RemoveRequestUtil.getParamString(request, EWebParam.SCANNER_TYPE);
			
			if (!EScannerTypes.valid(scannerType)) {
				getLog().error("Invalid scanner Type: " + scannerType);
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
			}
			
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
			
			return ScannerControllerHelper.init(getLog()).processListScanner(client, client.getPlanActives().get(0).getId(), scannerType);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}


	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			String scannerType = RemoveRequestUtil.getParamString(request, EWebParam.SCANNER_TYPE);
			
			if (!EScannerTypes.valid(scannerType)) {
				getLog().error("Invalid scanner Type: " + scannerType);
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
			}
			
			String uuid_scanner = RemoveRequestUtil.getOptionalParamString(request, EWebParam.UUID);
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
			Scanner scanner = null;
			
			if (uuid_scanner != null) {
				scanner = ScannerControllerHelper.init(getLog()).processGetScanner(uuid_scanner, scannerType); 
				ScannerValidator.init(client).validateScannerType(scanner);
			} else {
				scanner = ScannerControllerHelper.init(getLog()).buildDefaultEmptyScanner(client, scannerType);
			}
			
			return RemoveResponseUtil.buildOk(scanner); 
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
			
			Scanner scanner = (Scanner) RemoveRequestUtil.getBodyObject(request, Scanner.class);
			String result = ScannerControllerHelper.init(getLog()).generateScanner(client, scanner);
			
			if (scanner.esOneShot()) {
				ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_ONE_SHOT_GENERATED, scanner.buildAuditParams());	
			} else if (scanner.esRecurrent()) {
				ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_RECURRENT_GENERATED, scanner.buildAuditParams());
			} else if (scanner.esTransform()) {
				ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_TRANSFORM_GENERATED, scanner.buildAuditParams());
			}
			
			return result;
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
			
			Scanner scanner = (Scanner) RemoveRequestUtil.getBodyObject(request, Scanner.class);
			String result = ScannerControllerHelper.init(getLog()).updateConfiguration(client, scanner);

			if (scanner.esRecurrent()) {
				ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_RECURRENT_CONFIG_UPDATED, scanner.buildAuditParams());
			} else if (scanner.esTransform()) {
				ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_TRANSFORM_CONFIG_UPDATED, scanner.buildAuditParams());
			}
			
			return result;
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	/**
	 * Doesn't delete a scanner, just build an empty template for this client.
	 */
	@Override
	public Object delete(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());

		try {
			/*RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			String scannerType = RemoveRequestUtil.getParamString(request, EWebParam.SCANNER_TYPE);
			
			if (!EScannerTypes.valid(scannerType)) {
				getLog().error("Invalid scanner Type: " + scannerType);
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
			}
			
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
			Scanner scanner = ScannerControllerHelper.init(getLog()).buildDefaultEmptyScanner(client, scannerType);
			
			if (scannerType.equals(EScannerTypes.ONE_SHOT.getCode())) {
				ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_ONE_SHOT_RESETED, scanner.buildAuditParams());	
			} else if (scannerType.equals(EScannerTypes.MONITOR.getCode())) {
				ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_RECURRENT_RESETED, scanner.buildAuditParams());
			} else if (scannerType.equals(EScannerTypes.TRANSFORM.getCode())) {
				ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_TRANSFORM_RESETED, scanner.buildAuditParams());
			}*/
			
			return RemoveResponseUtil.buildErrorNoImplementado();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
