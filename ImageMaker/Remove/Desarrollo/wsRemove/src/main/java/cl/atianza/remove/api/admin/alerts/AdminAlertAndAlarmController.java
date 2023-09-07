package cl.atianza.remove.api.admin.alerts;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.api.client.alerts.ClientAlertAndAlarmController;
import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.commons.ScannerAlertDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.daos.commons.ScannerResultViewDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientScannerAlert;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerAlert;
import cl.atianza.remove.models.commons.ScannerResultView;
import cl.atianza.remove.utils.RemoveJsonUtil;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.AccessToClientDataValidator;
import cl.atianza.remove.validators.UserProfileAccessValidator;
import spark.Request;
import spark.Response;

public class AdminAlertAndAlarmController extends RestController{
	public AdminAlertAndAlarmController() {
		super(ERestPath.ADMIN_SCANNER_ALERTS, LogManager.getLogger(AdminAlertAndAlarmController.class));
	}
	
	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());
		
		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			Long id_scanner = RemoveRequestUtil.getParamLong(request, EWebParam.ID_SCANNER);		
			ScannerAlert alert = (ScannerAlert) ScannerAlertDao.init().getBasicById(id_scanner);
			
			
			return RemoveResponseUtil.buildOk(alert);
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
			ScannerAlert alert = (ScannerAlert) RemoveRequestUtil.getBodyObject(request, ScannerAlert.class);
			
			Scanner scanner = ScannerDao.init().getBasicById(alert.getId_scanner());
			ScannerResultView view = ScannerResultViewDao.init().getByUuid(scanner.getUuid());
			
			scanner = (Scanner) RemoveJsonUtil.jsonToData(view.getContent(), Scanner.class);
			scanner.setRestrictions(ClientPlanDao.init().getById(scanner.getId_client_plan()));
			scanner.setImpulses(ScannerImpulseDao.init().listPublishedByScanner(scanner.getId()));
			
			Client client = AccessToClientDataValidator.init(token).validateAccessOperation(scanner, true);
						
			
			ScannerAlert result = (ScannerAlert) ScannerAlertDao.init().save(alert);
			//ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.USER_ALERT_CREATE);	
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.USER_ALERT_CREATE);
						
			return RemoveResponseUtil.buildOk(result);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			ScannerAlert alert = (ScannerAlert) RemoveRequestUtil.getBodyObject(request, ScannerAlert.class);
			
			Scanner scanner = ScannerDao.init().getBasicById(alert.getId_scanner());
			ScannerResultView view = ScannerResultViewDao.init().getByUuid(scanner.getUuid());
			
			scanner = (Scanner) RemoveJsonUtil.jsonToData(view.getContent(), Scanner.class);
			scanner.setRestrictions(ClientPlanDao.init().getById(scanner.getId_client_plan()));
			scanner.setImpulses(ScannerImpulseDao.init().listPublishedByScanner(scanner.getId()));
			
			Client client = AccessToClientDataValidator.init(token).validateAccessOperation(scanner, true);
			
			ScannerAlert result = (ScannerAlert) ScannerAlertDao.init().update(alert);
			
			//ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.USER_ALERT_UPDATE);	
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.USER_ALERT_UPDATE);

						
			return RemoveResponseUtil.buildOk(result);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	@Override
	public Object delete(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			ScannerAlert alert = (ScannerAlert) RemoveRequestUtil.getBodyObject(request, ScannerAlert.class);

			
			
			ScannerAlert result = (ScannerAlert) ScannerAlertDao.init().update(alert);
			
			//ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.USER_ALERT_UPDATE);	
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.USER_ALERT_UPDATE);

						
			return RemoveResponseUtil.buildOk(result);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
