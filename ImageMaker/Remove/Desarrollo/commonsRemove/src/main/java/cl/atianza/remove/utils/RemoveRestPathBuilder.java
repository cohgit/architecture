package cl.atianza.remove.utils;

import static spark.Spark.*;

import cl.atianza.remove.exceptions.RemoveApplicationException;
import spark.Request;
import spark.Response;

/**
 * Clase utilitaria encargada de construir los paths de los servicios definidos en los controladores.
 * Por defecto intenta construir los siguientes métodos REST:
 * 
 * - LIST: Método GET que tiende a retornar un listado del recurso.
 * - GET: Obtiene una instancia del recurso.
 * - POST: Guarda una nueva instancia del recurso.
 * - PUT: Actualiza una instancia del recurso.
 * - DELETE: Elimina (Física o lógicamente) una instancia del recurso.
 * - OPTIONS: Indica las distintas opciones de este recurso.
 * 
 * @author Jose Gutierrez
 */
public class RemoveRestPathBuilder {
	private static final String APP_JSON = "application/json";
	private static final String ROOT = "";
	private static final String LIST = "/list";
	private static final String FILE = "/file";
	
	/**
	 * Construye los endpoints del micro-servicio.
	 * 
	 * @param controller Controlador con los endpoints y la implementación de cada servicio.
	 */
	public static void build(AbstractRestController controller) {
		path(controller.getAsignedPath(), () -> {
			get(LIST, (Request request, Response response) -> {
	        	try {
					return controller.list(request, response);
				} catch (RemoveApplicationException e) {
					return e.getMessage();
				}
	        });
			get(ROOT, (request, response) -> {
				try {
					return controller.get(request, response);
				} catch (RemoveApplicationException e) {
					return e.getMessage();
				}
			});
			post(ROOT, APP_JSON, (Request request, Response response) -> {
	        	try {
					return controller.post(request, response);
				} catch (RemoveApplicationException e) {
					return e.getMessage();
				}
	        });
			post(FILE, (Request request, Response response) -> {
	        	try {
					return controller.loadFile(request, response);
				} catch (RemoveApplicationException e) {
					return e.getMessage();
				}
	        });
			get(FILE, (Request request, Response response) -> {
	        	try {
					return controller.downloadFile(request, response);
				} catch (RemoveApplicationException e) {
					return e.getMessage();
				}
	        });
	        put(ROOT, APP_JSON, (Request request, Response response) -> {
	        	try {
					return controller.put(request, response);
				} catch (RemoveApplicationException e) {
					return e.getMessage();
				}
	        });
	        delete(ROOT, (Request request, Response response) -> {
	        	try {
					return controller.delete(request, response);
				} catch (RemoveApplicationException e) {
					return e.getMessage();
				}
	        });
	        options(ROOT, (Request request, Response response) -> {
	        	try {
					return controller.options(request, response);
				} catch (RemoveApplicationException e) {
					return e.getMessage();
				}
	        });
	    });
	}
}
