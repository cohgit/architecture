package cl.atianza.remove.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import spark.Request;
import spark.Response;

/**
 * Clase padre de los controladores de servicios.
 * 
 * @author Jose Gutierrez
 *
 */
public abstract class AbstractRestController {
	protected Logger log;

	public AbstractRestController(Logger _log) {
		super();
		
		this.log = _log;
	}
	
	/**
	 * Extrae la instancia correspondiente de Log4j para �ste servicio.
	 * 
	 * @return
	 */
	protected Logger getLog() {
		if(this.log == null) {
			this.log = LogManager.getLogger(AbstractRestController.class);
			
			log.warn("***** Se olvid� instanciar la clase del LOGGER");
		}
		
		return this.log;
	}

	/**
	 * Implementaci�n del servicio GET. Se debe sobreescribir, hasta que no lo haga retornar� un error indicando "Servicio no implementado)
	 * 
	 * @param request Objeto request del servicio.
	 * @param response Objeto response del servicio.
	 * @return Respuesta del servicio (Generalmente un string con formato JSon)
	 * @throws RemoveApplicationException 
	 */
	public Object get(Request request, Response response) throws RemoveApplicationException{
		RemoveExceptionHandler.throwNotImplementedException();
		return null;
	};
	
	/**
	 * Implementaci�n del servicio GET (List). Se debe sobreescribir, hasta que no lo haga retornar� un error indicando "Servicio no implementado)
	 * @param request
	 * @param response
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Object list(Request request, Response response) throws RemoveApplicationException{
		RemoveExceptionHandler.throwNotImplementedException();
		return null;
	};
	
	/**
	 * Implementaci�n del servicio POST. Se debe sobreescribir, hasta que no lo haga retornar� un error indicando "Servicio no implementado)
	 * @param request
	 * @param response
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Object post(Request request, Response response) throws RemoveApplicationException{
		RemoveExceptionHandler.throwNotImplementedException();
		return null;
	};
	
	/**
	 * Implementaci�n del servicio PUT. Se debe sobreescribir, hasta que no lo haga retornar� un error indicando "Servicio no implementado)
	 * @param request
	 * @param response
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Object put(Request request, Response response) throws RemoveApplicationException{
		RemoveExceptionHandler.throwNotImplementedException();
		return null;
	};
	
	/**
	 * Implementaci�n del servicio PUT. Se debe sobreescribir, hasta que no lo haga retornar� un error indicando "Servicio no implementado)
	 * @param request
	 * @param response
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Object SaveScannerConfiguration(Request request, Response response) throws RemoveApplicationException{
		RemoveExceptionHandler.throwNotImplementedException();
		return null;
	};
	
	/**
	 * Implementaci�n del servicio PUT. Se debe sobreescribir, hasta que no lo haga retornar� un error indicando "Servicio no implementado)
	 * @param request
	 * @param response
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Object delete(Request request, Response response) throws RemoveApplicationException{
		RemoveExceptionHandler.throwNotImplementedException();
		return null;
	};
	
	/**
	 * Implementaci�n del servicio POST-File. Se debe sobreescribir, hasta que no lo haga retornar� un error indicando "Servicio no implementado)
	 * @param request
	 * @param response
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Object loadFile(Request request, Response response) throws RemoveApplicationException{
		RemoveExceptionHandler.throwNotImplementedException();
		return null;
	};
	
	/**
	 * Implementaci�n del servicio POST-File. Se debe sobreescribir, hasta que no lo haga retornar� un error indicando "Servicio no implementado)
	 * @param request
	 * @param response
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Object downloadFile(Request request, Response response) throws RemoveApplicationException{
		RemoveExceptionHandler.throwNotImplementedException();
		return null;
	};
	
	/**
	 * Implementaci�n del servicio OPTIONS. Se debe sobreescribir, hasta que no lo haga retornar� un error indicando "Servicio no implementado)
	 * @param request
	 * @param response
	 * @return
	 * @throws RemoveApplicationException
	 */
	public Object options(Request request, Response response) throws RemoveApplicationException{
		RemoveExceptionHandler.throwNotImplementedException();
		return null;
	};
	
	/**
	 * Atrapa una excepci�n del servicio y prepara una respuesta apropiada.
	 * 
	 * @param t
	 * @param params
	 * @return
	 */
	protected Object catchException(Throwable t, String... params) {
		return catchException(getLog(), t, params);
	}
	
	/**
	 * Atrapa una excepci�n del servicio y prepara una respuesta apropiada.
	 * 
	 * @param log
	 * @param t
	 * @param params
	 * @return
	 */
	public static Object catchException(Logger log, Throwable t, String... params) {
		if(t instanceof RemoveApplicationException) {
			RemoveApplicationException ae = (RemoveApplicationException) t;
			if (ae.getParams() != null && !ae.getParams().isEmpty()) {
				params = ae.getParams().toArray(new String[ae.getParams().size()]);
			}
			
			if(ae.isSeveral()) {
				log.error(ae.getSeveralError(), ae);
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INTERNAL_SERVER);
			} else {
				if(params != null && params.length > 0) {
					log.error(ae.getError()+": (params: "+StringUtils.join(params, ", "), t);
				} else {
					log.error(ae.getError(), t);
				}
				return RemoveResponseUtil.buildError(ae.getError());
			}
		} else if(t instanceof Exception) {
			log.error(EMessageBundleKeys.ERROR_INTERNAL_SERVER, t);
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INTERNAL_SERVER);
		} else {
			log.error(EMessageBundleKeys.ERROR_INTERNAL_SERVER, t);
		}
		
		return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INTERNAL_SERVER);
	}
	
	protected abstract String getAsignedPath();
}
