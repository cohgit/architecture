package cl.atianza.remove.api.admin.scanners;

import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.jsoup.select.Evaluator.IsEmpty;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.commons.ScannerAlertDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EScannerStatus;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ScannerControllerHelper;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerAlert;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.AccessToClientDataValidator;
import cl.atianza.remove.validators.ScannerValidator;
import cl.atianza.remove.validators.UserProfileAccessValidator;
import spark.Request;
import spark.Response;

/**
 * REST Scanner service for system user, allows to manage clients scanners.
 * @author pilin
 *
 */
public class AdminScannerController extends RestController {
	public AdminScannerController() {
		super(ERestPath.ADMIN_SCANNER, LogManager.getLogger(AdminScannerController.class));
	}
	
	@Override
	public Object list(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LIST: " + this.getClass());
		// TODO: Limitar acceso de Formula solo a transforma
		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			String scannerType = RemoveRequestUtil.getParamString(request, EWebParam.SCANNER_TYPE);
			
			if (!EScannerTypes.valid(scannerType)) {
				getLog().error("Invalid scanner Type: " + scannerType);
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
			}
			
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			if (lockFormulaAccess(user, scannerType)) {
				getLog().error("Formula profile just have access to scanner type 'transform': "+ user.getProfile() + " | " + scannerType);
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_USER_NOT_ALLOWED);
			}
			
			String uuidClient = RemoveRequestUtil.getParamString(request, EWebParam.UUID_CLIENT);
			Client client = AccessToClientDataValidator.init(token).validateAccessOperation(uuidClient);
			
			return ScannerControllerHelper.init(getLog()).processListScanner(client, client.getPlanActives().get(0).getId(), scannerType);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());
		// TODO: Limitar acceso de Formula solo a transforma
		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			String scannerType = RemoveRequestUtil.getParamString(request, EWebParam.SCANNER_TYPE);
			
			if (!EScannerTypes.valid(scannerType)) {
				getLog().error("Invalid scanner Type: " + scannerType);
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
			}
			
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			String uuid_scanner = RemoveRequestUtil.getOptionalParamString(request, EWebParam.UUID);
			
			if (lockFormulaAccess(user, scannerType)) {
				getLog().error("Formula profile just have access to scanner type 'transform': "+ user.getProfile() + " | " + scannerType);
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_USER_NOT_ALLOWED);
			}
			
			Scanner scanner = null;
			
			if (uuid_scanner != null) {
				scanner = ScannerControllerHelper.init(getLog()).processGetScanner(uuid_scanner, scannerType);
				
				Client client = AccessToClientDataValidator.init(token).validateAccessOperation(scanner.getId_owner());
				ScannerValidator.init(client).validateScannerType(scannerType);
			} else {
				String uuidClient = RemoveRequestUtil.getParamString(request, EWebParam.UUID_CLIENT);
				Client client = AccessToClientDataValidator.init(token).validateAccessOperation(uuidClient, true);
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
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			Scanner scanner = (Scanner) RemoveRequestUtil.getBodyObject(request, Scanner.class);
			
			Client client = AccessToClientDataValidator.init(token).validateAccessOperation(scanner, true);
			String result = null;
			getLog().info("Saving scanner clon base: " + scanner.getUuid_from());
			
			if (scanner.getUuid_from() != null) {
				scanner = cleanForClon(scanner);
				getLog().info("Saving scanner clon cleaned..." + scanner.getUuid() + ", " + scanner.getId());
				result = ScannerControllerHelper.init(getLog()).generateScannerForClon(client, scanner);
			}else {
				result = ScannerControllerHelper.init(getLog()).generateScanner(client, scanner);
			}
			
			
			
			if (scanner.esOneShot()) {
				ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_ONE_SHOT_GENERATED);	
				UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_ONE_SHOT_GENERATED);	
			} else if (scanner.esRecurrent()) {
				ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_RECURRENT_GENERATED);
				UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_RECURRENT_GENERATED);
			} else if (scanner.esTransform()) {
				ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_TRANSFORM_GENERATED);
				UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_TRANSFORM_GENERATED);
			}
			
			return result;
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	private Scanner cleanForClon(Scanner scanner) throws RemoveApplicationException {
		ScannerDao.init().markScannerAsFixed(scanner.getUuid_from());
		ScannerAlert alert = ScannerAlertDao.init().getBasicById(scanner.getId());
		if(alert != null) {
			ScannerAlertDao.init().updateStatus(scanner.getId(),false); 
		}
		scanner.setUuid(UUID.randomUUID().toString());
		scanner.setActivation_date(RemoveDateUtils.nowLocalDateTime());
		scanner.setId(null);
		scanner.setResults(null);
		scanner.setImpulses(null);
		
		scanner.setJustTrackingWords(scanner.getJustTrackingWords().stream()
									  .filter(x -> !x.isMarkToDelete()).collect(Collectors.toList()));
		
		scanner.setJustTrackingURLs(scanner.getJustTrackingURLs().stream()
		  							 .filter(x -> !x.isMarkToDelete()).collect(Collectors.toList()));
		
		return scanner;
	}

	@Override
	public Object put(Request request,Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());
		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			Scanner scanner = (Scanner) RemoveRequestUtil.getBodyObject(request, Scanner.class);
			String result = "";
			if(scanner.getAction().equalsIgnoreCase("save")) {
				if (lockFormulaAccess(user, scanner.getType())) {
					getLog().error("Formula profile just have access to scanner type 'transform': "+ user.getProfile() + " | " + scanner.getType());
					return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_USER_NOT_ALLOWED);
				}
				
				Client client = AccessToClientDataValidator.init(token).validateAccessOperation(scanner, true);
				
				 result = ScannerControllerHelper.init(getLog()).updateConfiguration(client, scanner);
			}else {
				if (lockFormulaAccess(user, scanner.getType())) {
					getLog().error("Formula profile just have access to scanner type 'transform': "+ user.getProfile() + " | " + scanner.getType());
					return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_USER_NOT_ALLOWED);
				}
				
				Client client = AccessToClientDataValidator.init(token).validateAccessOperation(scanner, true);
				
				 result = ScannerControllerHelper.init(getLog()).updateConfiguration(client, scanner);
				
				scanner = ScannerDao.init().getForRecurrence(scanner.getUuid());
				
				
				ScannerControllerHelper.init(getLog()).repeatScanner(client, scanner);
				
				if (scanner.esRecurrent()) {
					ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_RECURRENT_CONFIG_UPDATED);
					UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_RECURRENT_CONFIG_UPDATED);
				} else if (scanner.esTransform()) {
					ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_TRANSFORM_CONFIG_UPDATED);
					UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_TRANSFORM_CONFIG_UPDATED);
				}
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
		getLog().info("DELETE: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			String uuid_scanner = RemoveRequestUtil.getParamString(request, EWebParam.UUID);
			String operation = RemoveRequestUtil.getParamString(request, EWebParam.OPERATION);
			getLog().info("Operation: " + operation + ", Scanner: " + uuid_scanner);
			
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			Scanner scanner = ScannerDao.init().getBasicByUuid(uuid_scanner);
			
			if (scanner != null) {
				Client client = ClientDao.init().getBasicById(scanner.getId_owner());
				AccessToClientDataValidator.init(token).validateAccessOperation(client.getUuid(), true);
				
				switch (operation) {
					case "delete": {
						ScannerDao.init().updateStatus(scanner, EScannerStatus.DELETED);
						ScannerAlert alert = ScannerAlertDao.init().getBasicById(scanner.getId());
						if(alert != null) {
							ScannerAlertDao.init().updateStatus(scanner.getId(),false); 
						}
						
						ClientAuditAccessDao.init().save(scanner.getId_owner(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_DELETED);	
						UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_DELETED);
						break;
					}
					case "pause": {
						if (scanner.getStatus().equals(EScannerStatus.PAUSED.getTag())) {
							ScannerDao.init().updateStatus(scanner, EScannerStatus.ACTIVE);
							ScannerAlert alert = ScannerAlertDao.init().getBasicById(scanner.getId());
							if(alert != null) {
								ScannerAlertDao.init().updateStatus(scanner.getId(),true); //Se activa la alerta si el scanner se activa
							}
							ClientAuditAccessDao.init().save(scanner.getId_owner(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_RESUMED);	
							UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_RESUMED);
						} else if (scanner.getStatus().equals(EScannerStatus.ACTIVE.getTag())) {
							ScannerDao.init().updateStatus(scanner, EScannerStatus.PAUSED);
							ScannerAlert alert = ScannerAlertDao.init().getBasicById(scanner.getId());
							if(alert != null) {
								ScannerAlertDao.init().updateStatus(scanner.getId(),false); 
							}
							ClientAuditAccessDao.init().save(scanner.getId_owner(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_STOPPED);	
							UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_STOPPED);
						} else {
							getLog().error("Invalid scanner status: "+ scanner.getStatus());
							RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
						}
						break;
					}
					default: {
						getLog().error("Invalid operation: "+ operation);
						RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
					}
				}
			}
			
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	/**
	 * M�todo creado para limitar los accesos de perfil formula a scanner transforma (Eliminar esto una vez se cree el gestor de perfiles)
	 * @deprecated
	 * @param user
	 * @param scannerType
	 * @return
	 */
	private boolean lockFormulaAccess(User user, String scannerType) {
		return user.getProfile().equals(EProfiles.FORMULE.getCode()) && !EScannerTypes.TRANSFORM.getCode().equals(scannerType);
	}
}
