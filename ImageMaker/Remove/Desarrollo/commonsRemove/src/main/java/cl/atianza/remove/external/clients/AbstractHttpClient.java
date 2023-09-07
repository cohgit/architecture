package cl.atianza.remove.external.clients;

import org.apache.logging.log4j.Logger;

import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.utils.HttpClientModel;
import cl.atianza.remove.utils.RemoveJsonUtil;
import cl.atianza.remove.utils.RemoveResponse;

/**
 * Abstract class with http connections with external services
 * 
 * @author Jose Gutierrez
 *
 */
public abstract class AbstractHttpClient {
	protected Logger log;
	
	protected final int MAX_RETRIES = 1;
	protected final int REFRESH_WINDOW = 30000;

	public AbstractHttpClient(Logger log) {
		super();
		this.log = log;
	}
	
	/**
	 * Execute an http service call and retry if needed
	 * @param client
	 * @return
	 * @throws RemoveApplicationException
	 */
	protected RemoveResponse executeService(HttpClientModel client) throws RemoveApplicationException {
		return executeService(client, false);
	}
	
	protected RemoveResponse executeService(HttpClientModel client, boolean cleanPathParams) throws RemoveApplicationException {
		int tried = 1;
		String json = null;
		
		do {
			try {
				json = client.callService(cleanPathParams);
				return RemoveResponse.instanceOk(RemoveJsonUtil.jsonToMap(json));
			} catch (RemoveApplicationException e) {
				log.error(EMessageBundleKeys.ERROR_EXTERNAL_SERVICE.getTag() + " Intento: " + tried + " - " + client, e);
				json = null;
			}
		} while (tried++ < MAX_RETRIES && json == null);
		
		if(json == null) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_EXTERNAL_SERVICE);
		}
		
		return RemoveResponse.instanceError(EMessageBundleKeys.ERROR_EXTERNAL_SERVICE);
	}
}
