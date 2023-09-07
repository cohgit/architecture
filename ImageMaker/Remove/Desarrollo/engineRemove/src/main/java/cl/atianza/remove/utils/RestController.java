package cl.atianza.remove.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cl.atianza.remove.enums.ERestPath;


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
}
