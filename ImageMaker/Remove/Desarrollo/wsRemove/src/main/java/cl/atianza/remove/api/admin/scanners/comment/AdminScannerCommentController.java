package cl.atianza.remove.api.admin.scanners.comment;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.api.admin.scanners.AdminScannerController;
import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.commons.ScannerCommentDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ScannerControllerHelper;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerComment;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.AccessToClientDataValidator;
import cl.atianza.remove.validators.ScannerValidator;
import cl.atianza.remove.validators.UserAccessValidator;
import cl.atianza.remove.validators.UserProfileAccessValidator;
import spark.Request;
import spark.Response;

public class AdminScannerCommentController extends RestController{
	public AdminScannerCommentController() {
		super(ERestPath.ADMIN_SCANNER_COMMENT, LogManager.getLogger(AdminScannerCommentController.class));
	}
	
	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());
		
		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			Long id_comment = RemoveRequestUtil.getParamLong(request, EWebParam.ID);
			
			ScannerComment scanner_comment = (ScannerComment) ScannerCommentDao.init().getBasicById(id_comment);
			
			return RemoveResponseUtil.buildOk(scanner_comment);
		} catch (Throwable t) {
			return catchException(t);
		}		
	}
	
	@Override
	public Object list(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LIST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccessList(this);
			Long id_scanner = RemoveRequestUtil.getParamLong(request, EWebParam.ID_SCANNER);
			return RemoveResponseUtil.buildOk(ScannerCommentDao.init().list(id_scanner));
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
			ScannerComment scanner_comment = (ScannerComment) RemoveRequestUtil.getBodyObject(request, ScannerComment.class);
			
			ScannerComment result = (ScannerComment) ScannerCommentDao.init().save(scanner_comment, user);

						
			return RemoveResponseUtil.buildOk(result);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
