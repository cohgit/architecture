package cl.atianza.remove.api.client.scanners.snippets;

import java.util.List;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.commons.ScannerResultImageSnippetDao;
import cl.atianza.remove.daos.commons.ScannerResultImageSnippetTrackDao;
import cl.atianza.remove.daos.commons.ScannerResultNewsSnippetDao;
import cl.atianza.remove.daos.commons.ScannerResultNewsSnippetTrackDao;
import cl.atianza.remove.daos.commons.ScannerResultRawDao;
import cl.atianza.remove.daos.commons.ScannerResultViewDao;
import cl.atianza.remove.daos.commons.ScannerResultWebSnippetDao;
import cl.atianza.remove.daos.commons.ScannerResultWebSnippetTrackDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.ScannerResultImageSnippet;
import cl.atianza.remove.models.commons.ScannerResultImageSnippetTrack;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippet;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippetTrack;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;
import cl.atianza.remove.models.commons.ScannerResultWebSnippetTrack;
import cl.atianza.remove.models.views.SnippetViewList;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.ClientAccessValidator;
import spark.Request;
import spark.Response;

/**
 * Service that allows to change a snippet feeling. This recalculates ratios attributes if necessary. 
 * @author pilin
 *
 */
public class ClientScannerSnippetController extends RestController {
	public ClientScannerSnippetController() {
		super(ERestPath.CLIENT_SCANNER_SNIPPET, LogManager.getLogger(ClientScannerSnippetController.class));
	}

	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			SnippetViewList list = (SnippetViewList) RemoveRequestUtil.getBodyObject(request, SnippetViewList.class);
			
			if (list != null) {
				if (list.getImages() != null) {
					list.getImages().forEach(snippet -> {
						try {
							updateImageSnippetFeeling(token, snippet);
						} catch (RemoveApplicationException e) {
							getLog().error("Error updating snippet feel:", snippet, e);
						}
					});
				}
				if (list.getNews() != null) {
					list.getNews().forEach(snippet -> {
						try {
							updateNewsSnippetFeeling(token, snippet);
						} catch (RemoveApplicationException e) {
							getLog().error("Error updating snippet feel:", snippet, e);
						}
					});
				}
				if (list.getWeb() != null) {
					list.getWeb().forEach(snippet -> {
						try {
							updateWebSnippetFeeling(token, snippet);
						} catch (RemoveApplicationException e) {
							getLog().error("Error updating snippet feel:", snippet, e);
						}
					});
				}
			}
			
			Long idResult = list.findScannerResult();
			
			if (idResult != null) ScannerResultViewDao.init().refreshViewAsync(idResult);
			
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	private Object updateNewsSnippetFeeling(RemoveTokenAccess token, ScannerResultNewsSnippet newsSnippet) throws RemoveApplicationException {
		Client client = ClientAccessValidator.init(token).validateAccess(newsSnippet);
		ScannerResultNewsSnippetDao.init().updateFeeling(newsSnippet);
		List<ScannerResultNewsSnippetTrack> tracks = ScannerResultNewsSnippetTrackDao.init().list(newsSnippet.getId());
		
		if (tracks != null && !tracks.isEmpty()) {
			tracks.forEach(track -> {
				try {
					recalculateRatings(track.getId_scanner_raw());
				} catch (RemoveApplicationException e) {
					getLog().error("Error recalculating Raw: ", track.getId_scanner_raw());
				}	
			});
		}
		
		ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_NEWS_SNIPPET_CHANGE_FEELING);
		return RemoveResponseUtil.buildDefaultOk();
	}

	private Object updateImageSnippetFeeling(RemoveTokenAccess token, ScannerResultImageSnippet imageSnippet) throws RemoveApplicationException {
		Client client = ClientAccessValidator.init(token).validateAccess(imageSnippet);
		ScannerResultImageSnippetDao.init().updateFeeling(imageSnippet);
		List<ScannerResultImageSnippetTrack> tracks = ScannerResultImageSnippetTrackDao.init().list(imageSnippet.getId());
		
		if (tracks != null && !tracks.isEmpty()) {
			tracks.forEach(track -> {
				try {
					recalculateRatings(track.getId_scanner_raw());
				} catch (RemoveApplicationException e) {
					getLog().error("Error recalculating Raw: ", track.getId_scanner_raw());
				}	
			});
		}
		
		ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_IMAGE_SNIPPET_CHANGE_FEELING);
		return RemoveResponseUtil.buildDefaultOk();
	}

	private Object updateWebSnippetFeeling(RemoveTokenAccess token, ScannerResultWebSnippet webSnippet) throws RemoveApplicationException {
		Client client = ClientAccessValidator.init(token).validateAccess(webSnippet);
		
		ScannerResultWebSnippetDao.init().updateFeeling(webSnippet);
		List<ScannerResultWebSnippetTrack> tracks = ScannerResultWebSnippetTrackDao.init().list(webSnippet.getId());
		
		if (tracks != null && !tracks.isEmpty()) {
			tracks.forEach(track -> {
				try {
					recalculateRatings(track.getId_scanner_raw());
				} catch (RemoveApplicationException e) {
					getLog().error("Error recalculating Raw: ", track.getId_scanner_raw());
				}	
			});
		}
		
		ClientAuditAccessDao.init().saveForClient(client.getId(), EMessageBundleKeys.MESSAGE_AUDIT_SCANNER_WEB_SNIPPET_CHANGE_FEELING);
		return RemoveResponseUtil.buildDefaultOk();
	}
	
	private void recalculateRatings(Long idResultRaw) throws RemoveApplicationException {
		ScannerResultRawDao.init().recalculateRatings(idResultRaw);
	}
}
