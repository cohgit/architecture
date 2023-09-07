package cl.atianza.remove.exceptions;

import java.util.List;

import cl.atianza.remove.enums.EMessageBundleKeys;

/**
 * This class handle applications exceptions.
 * @author Jose Gutierrez
 *
 */
public class RemoveExceptionHandler {
	public static void throwNotImplementedException() throws RemoveApplicationException {
		throw RemoveApplicationException.initSeveralError(EMessageBundleKeys.ERROR_SERVICE_NOT_IMPLEMENTED);
	}
	
	public static void throwAppException(EMessageBundleKeys mensaje) throws RemoveApplicationException {
		throw RemoveApplicationException.initError(mensaje);
	}
	public static void throwAppException(EMessageBundleKeys mensaje, List<String> params) throws RemoveApplicationException {
		throw RemoveApplicationException.initError(mensaje);
	}
	public static void throwSeveralAppException(EMessageBundleKeys mensaje) throws RemoveApplicationException {
		throw RemoveApplicationException.initSeveralError(mensaje);
	}
}
