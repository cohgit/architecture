package cl.atianza.remove.api.admin.trackingwords;

import java.util.List;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.commons.TrackingWordDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.commons.TrackingWord;
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
public class AdminTrackingWordController extends RestController {
	public AdminTrackingWordController() {
		super(ERestPath.ADMIN_TRACKING_WORDS, LogManager.getLogger(AdminTrackingWordController.class));
	}

	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			TrackingWord tw = (TrackingWord) RemoveRequestUtil.getBodyObject(request, TrackingWord.class);
			tw.setFeeling(tw.getFeelingObj().getTag());
			tw.setActive(true);
			tw = TrackingWordDao.init().save(tw);
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_TRACKING_WORD_SAVED);
			return RemoveResponseUtil.buildOk(tw);
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
			
			return RemoveResponseUtil.buildOk(TrackingWordDao.init().list());
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
			
			TrackingWord tw = (TrackingWord) RemoveRequestUtil.getBodyObject(request, TrackingWord.class);
			tw.setFeeling(tw.getFeelingObj().getTag());
			tw = TrackingWordDao.init().update(tw);
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_TRACKING_WORD_UPDATED);
			return RemoveResponseUtil.buildOk(tw);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object loadFile(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LOAD_FILE: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccessFile(this);
			
			ListTrackingWords list = (ListTrackingWords) RemoveRequestUtil.getBodyObject(request, ListTrackingWords.class);
			
			if (list != null) {
				TrackingWordDao.init().saveOrUpdate(list.getList());
			}
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_TRACKING_WORD_FILE_SAVED);
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}

class ListTrackingWords {
	private List<TrackingWord> list;

	public ListTrackingWords() {
		super();
	}

	public ListTrackingWords(List<TrackingWord> list) {
		super();
		this.list = list;
	}
	
	public List<TrackingWord> getList() {
		return list;
	}

	public void setList(List<TrackingWord> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "ListTrackingWords [list=" + list + "]";
	}
}