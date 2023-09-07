package cl.atianza.remove.utils;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.helpers.FileManager;
import spark.Response;


/**
 * Clase padre de los controladores de servicios.
 * 
 * @author Jose Gutierrez
 *
 */
public abstract class RestController extends AbstractRestController {
	private ERestPath path;

	/**
	 * Constructor del controlador.
	 * 
	 * @param path Endpoint del servicio.
	 * @param _log Instancia de Log4j encargado de imprimir el log del servicio.
	 */
	public RestController(ERestPath path, Logger _log) {
		super(_log);
		
		this.setPath(path);
	}
	
	/**
	 * Extrae la instancia correspondiente de Log4j para éste servicio.
	 * 
	 * @return
	 */
	protected Logger getLog() {
		if(this.log == null) {
			this.log = LogManager.getLogger(RestController.class);
			
			log.warn("***** Se olvidó instanciar la clase del LOGGER");
		}
		
		return this.log;
	}

	public ERestPath getPath() {
		return path;
	}
	public void setPath(ERestPath path) {
		this.path = path;
	}

	public String getAsignedPath() {
		getLog().info("Exposing path: " + this.getPath() != null ? this.getPath().getPath() : null);
		return this.getPath() != null ? this.getPath().getPath() : null;
	}
	
	protected HttpServletResponse buildFileRawResponse(Response response, String contentType, String filePath) throws RemoveApplicationException {
		response.raw().setContentType(contentType);
		
	    try (ServletOutputStream os = response.raw().getOutputStream()){
			os.write(FileManager.init().obtainingFileBytes(filePath));
		} catch (IOException ex) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_FILE_NOT_FOUND);
		} catch (RemoveApplicationException ex) {
			throw ex;
		}
	    
	    return response.raw();
	}
}
