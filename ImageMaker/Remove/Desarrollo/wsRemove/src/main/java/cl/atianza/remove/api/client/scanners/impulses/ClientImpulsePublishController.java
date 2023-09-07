package cl.atianza.remove.api.client.scanners.impulses;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import spark.Request;
import spark.Response;

/**
 * Service to mark as "published" impulse publications.
 * @author pilin
 *
 */
@Deprecated
public class ClientImpulsePublishController extends RestController {
	public ClientImpulsePublishController() {
		super(ERestPath.CLIENT_IMPULSE_PUBLISH, LogManager.getLogger(ClientImpulsePublishController.class));
	}
	
	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			ScannerImpulse impulse = (ScannerImpulse) RemoveRequestUtil.getBodyObject(request, ScannerImpulse.class);
			
			return RemoveResponseUtil.buildOk(ScannerImpulseDao.init().updatePublishData(token, impulse));
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
