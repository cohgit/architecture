package cl.atianza.remove.api.client.scanners.comment;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.api.admin.scanners.comment.AdminScannerCommentController;
import cl.atianza.remove.daos.commons.ScannerCommentDao;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.ScannerComment;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.ClientAccessValidator;
import cl.atianza.remove.validators.UserProfileAccessValidator;
import spark.Request;
import spark.Response;

public class ClientScannerCommentController extends RestController{
	public ClientScannerCommentController() {
		super(ERestPath.CLIENT_SCANNER_COMMENT, LogManager.getLogger(ClientScannerCommentController.class));
	}
	
	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());
		
		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
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
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
			Long id_scanner = RemoveRequestUtil.getParamLong(request, EWebParam.ID_SCANNER);
			return RemoveResponseUtil.buildOk(ScannerCommentDao.init().list(id_scanner));
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
