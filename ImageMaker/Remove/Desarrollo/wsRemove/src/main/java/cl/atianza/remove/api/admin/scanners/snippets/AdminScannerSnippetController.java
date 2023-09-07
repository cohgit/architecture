package cl.atianza.remove.api.admin.scanners.snippets;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.commons.ScannerResultImageSnippetDao;
import cl.atianza.remove.daos.commons.ScannerResultNewsSnippetDao;
import cl.atianza.remove.daos.commons.ScannerResultViewDao;
import cl.atianza.remove.daos.commons.ScannerResultWebSnippetDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.ScannerResultImageSnippet;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippet;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;
import cl.atianza.remove.models.views.SnippetViewList;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.AccessToClientDataValidator;
import cl.atianza.remove.validators.UserAccessValidator;
import spark.Request;
import spark.Response;

/**
 * Service that allows to change a snippet feeling. This recalculates ratios attributes if necessary. 
 * @author pilin
 *
 */
public class AdminScannerSnippetController extends RestController {
	public AdminScannerSnippetController() {
		super(ERestPath.ADMIN_SCANNER_SNIPPET, LogManager.getLogger(AdminScannerSnippetController.class));
	}

	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserAccessValidator.init(token).validateUserAccess();
			
			SnippetViewList list = (SnippetViewList) RemoveRequestUtil.getBodyObject(request, SnippetViewList.class);
			
			if (list != null) {
				if (list.getImages() != null) {
					log.info("Updating image feelings: " + list.getImages().size());
					for (ScannerResultImageSnippet snippet : list.getImages()) {
						updateImageSnippetFeeling(user, token, snippet);
					}
				}
				if (list.getNews() != null) {
					log.info("Updating news feelings: " + list.getNews().size());
					for (ScannerResultNewsSnippet snippet : list.getNews()) {
						updateNewsSnippetFeeling(user, token, snippet);
					}
				}
				if (list.getWeb() != null) {
					log.info("Updating webs feelings: " + list.getWeb().size());
					for (ScannerResultWebSnippet snippet : list.getWeb()) {
						updateWebSnippetFeeling(user, token, snippet);
					}
				}
			}
			
			Long idResult = list.findScannerResult();
			if (idResult != null) ScannerResultViewDao.init().refreshViewAsync(idResult);
			
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	private Object updateNewsSnippetFeeling(User user, RemoveTokenAccess token, ScannerResultNewsSnippet newsSnippet) throws RemoveApplicationException {
		log.info("Updating web feeling: " + newsSnippet.getId() + " - " + newsSnippet.getFeeling());
		Client client = AccessToClientDataValidator.init(token).validateAccessOperationByScannerResult(newsSnippet.getId_scanner_result(), true);
		ScannerResultNewsSnippetDao.init().updateFeeling(newsSnippet);
		
		ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_NEWS_SNIPPET_CHANGE_FEELING);
		UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_NEWS_SNIPPET_CHANGE_FEELING);
		return RemoveResponseUtil.buildDefaultOk();
	}

	private Object updateImageSnippetFeeling(User user, RemoveTokenAccess token, ScannerResultImageSnippet imageSnippet) throws RemoveApplicationException {
		log.info("Updating image feeling: " + imageSnippet.getId() + " - " + imageSnippet.getFeeling());
		Client client = AccessToClientDataValidator.init(token).validateAccessOperationByScannerResult(imageSnippet.getId_scanner_result(), true);
		ScannerResultImageSnippetDao.init().updateFeeling(imageSnippet);
		
		ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_IMAGE_SNIPPET_CHANGE_FEELING);
		UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_IMAGE_SNIPPET_CHANGE_FEELING);
		return RemoveResponseUtil.buildDefaultOk();
	}

	private Object updateWebSnippetFeeling(User user, RemoveTokenAccess token, ScannerResultWebSnippet webSnippet) throws RemoveApplicationException {
		log.info("Updating web feeling: " + webSnippet.getId() + " - " + webSnippet.getFeeling());
		Client client = AccessToClientDataValidator.init(token).validateAccessOperationByScannerResult(webSnippet.getId_scanner_result(), true);
		ScannerResultWebSnippetDao.init().updateFeeling(webSnippet);
		
		ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_WEB_SNIPPET_CHANGE_FEELING);
		UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_WEB_SNIPPET_CHANGE_FEELING);
		return RemoveResponseUtil.buildDefaultOk();
	}
}
