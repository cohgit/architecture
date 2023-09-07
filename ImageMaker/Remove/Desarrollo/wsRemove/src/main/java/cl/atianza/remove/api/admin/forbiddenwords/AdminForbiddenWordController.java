package cl.atianza.remove.api.admin.forbiddenwords;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.commons.ForbiddenWordDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.commons.ForbiddenWord;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.UserProfileAccessValidator;
import spark.Request;
import spark.Response;

/**
 * Rest services for Tracking Words management.
 * @author pilin
 *
 */
public class AdminForbiddenWordController extends RestController {
	public AdminForbiddenWordController() {
		super(ERestPath.ADMIN_FORBIDDEN_WORDS, LogManager.getLogger(AdminForbiddenWordController.class));
	}

	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			ForbiddenWord word = ForbiddenWordDao.init()
					.save((ForbiddenWord) RemoveRequestUtil.getBodyObject(request, ForbiddenWord.class));
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_FORBIDDEN_WORD_SAVED);
			
			return RemoveResponseUtil.buildOk(word);
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
			
			return RemoveResponseUtil.buildOk(ForbiddenWordDao.init().list());
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			ForbiddenWord word = ForbiddenWordDao.init()
					.update((ForbiddenWord) RemoveRequestUtil.getBodyObject(request, ForbiddenWord.class));
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_FORBIDDEN_WORD_UPDATED);
			return RemoveResponseUtil.buildOk(word);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object delete(Request request, Response response) throws RemoveApplicationException {
		getLog().info("DELETE: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			ForbiddenWordDao.init().delete(RemoveRequestUtil.getParamLong(request, EWebParam.ID));
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}