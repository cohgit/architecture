package cl.atianza.remove.api.admin.scanners.impulses;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ImpulseControllerHelper;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.AccessToClientDataValidator;
import cl.atianza.remove.validators.UserProfileAccessValidator;
import spark.Request;
import spark.Response;

/**
 * REST service for impulse management.
 * @author pilin
 *
 */
public class AdminImpulseController extends RestController {
	public AdminImpulseController() {
		super(ERestPath.ADMIN_IMPULSE, LogManager.getLogger(AdminImpulseController.class));
	}
	
	@Override
	public Object list(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LIST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccessList(this);
			
			String uuidScanner = RemoveRequestUtil.getParamString(request, EWebParam.UUID_SCANNER);
			Scanner scanner = ScannerDao.init().getBasicByUuid(uuidScanner, EScannerTypes.TRANSFORM.getCode());
			Client client = AccessToClientDataValidator.init(token).validateAccessOperation(scanner.getId_owner());
			
			return ImpulseControllerHelper.init(getLog()).processListImpulses(uuidScanner, client, client.getPlanActives().get(0).getId(), user.getId(), user.getProfile());
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			ScannerImpulse impulse = (ScannerImpulse) RemoveRequestUtil.getBodyObject(request, ScannerImpulse.class);
			if (impulse.getType() == null) {
				impulse.setType(impulse.getTypeObj().getTag());
			}
			Client client = AccessToClientDataValidator.init(token).validateAccessOperation(impulse, true);
			
			User user = UserDao.init().getByUuid(token.getUuidAccount());
			impulse.setIdClientPlan(client.getPlanActives().get(0).getId());
			impulse.setCreator_profile(user.getProfile());
			impulse.setId_creator(user.getId());
			
			String resp = ImpulseControllerHelper.init(getLog()).processSaveImpulse(client, impulse, impulse.getIdClientPlan(), token);
			
			if (resp == null) {
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
			}
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_CREATED);
			ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_CREATED);	
			
			return resp;
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			ScannerImpulse impulse = (ScannerImpulse) RemoveRequestUtil.getBodyObject(request, ScannerImpulse.class);
			Client client = AccessToClientDataValidator.init(token).validateAccessOperation(impulse, true);
			User user = UserDao.init().getByUuid(token.getUuidAccount());
			
			String resp = ImpulseControllerHelper.init(getLog()).processUpdateImpulse(client, impulse, impulse.getIdClientPlan(), user.getId(), user.getProfile());
			
			if (resp == null) {
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
			}
			
			return resp;
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
