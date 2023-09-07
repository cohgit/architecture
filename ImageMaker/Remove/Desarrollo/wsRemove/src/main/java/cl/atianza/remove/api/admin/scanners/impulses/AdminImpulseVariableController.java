package cl.atianza.remove.api.admin.scanners.impulses;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.daos.commons.ScannerImpulseVariableDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.models.commons.ScannerImpulseVariable;
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
public class AdminImpulseVariableController extends RestController {
	public AdminImpulseVariableController() {
		super(ERestPath.ADMIN_IMPULSE_VARIABLE, LogManager.getLogger(AdminImpulseVariableController.class));
	}
	
	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());
		
		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			ScannerImpulse impulse = ScannerImpulseDao.init().get(RemoveRequestUtil.getParamLong(request, EWebParam.ID_IMPULSE));
			
			AccessToClientDataValidator.init(token).validateAccessOperation(impulse);
			return RemoveResponseUtil.buildOk(ScannerImpulseVariableDao.init().getByImpulse(impulse.getId()));
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			ScannerImpulseVariable variable = (ScannerImpulseVariable) RemoveRequestUtil.getBodyObject(request, ScannerImpulseVariable.class);
			
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			ScannerImpulse impulse = ScannerImpulseDao.init().get(variable.getId_scanner_impulse());
			
			AccessToClientDataValidator.init(token).validateAccessOperation(impulse);
			
			variable.setId_country(variable.getCountry().getId());
			variable.setId_author(user.getId());
			variable.setAuthor_profile(token.getProfile());
			
			variable = ScannerImpulseVariableDao.init().save(variable);
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_VARIABLE_SAVED);
			
			return RemoveResponseUtil.buildOk(variable);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			ScannerImpulseVariable variable = (ScannerImpulseVariable) RemoveRequestUtil.getBodyObject(request, ScannerImpulseVariable.class);
			
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			ScannerImpulse impulse = ScannerImpulseDao.init().get(variable.getId_scanner_impulse());
			
			AccessToClientDataValidator.init(token).validateAccessOperation(impulse);
			
			variable.setId_country(variable.getCountry().getId());
			variable = ScannerImpulseVariableDao.init().update(variable);
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_IMPULSE_VARIABLE_UPDATED);
			return RemoveResponseUtil.buildOk(variable);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
