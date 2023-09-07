package cl.atianza.remove.utils;

import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;

public class RemoveResponseUtil {
	public static String buildOk(Object response) {
		if(response != null) {
			try {
				return RemoveJsonUtil.dataToJson(RemoveResponse.instanceOk(response));
			} catch (RemoveApplicationException e) {
				return buildError(e);
			}
		}
		
		return buildNullResponseError();
	}

	public static String buildNullResponseError() {
		try {
			return RemoveJsonUtil.dataToJson(RemoveResponse.instanceError(EMessageBundleKeys.ERROR_NULL_VALUE));
		} catch (RemoveApplicationException e) {
			return buildError(e);
		}
	}

	public static String buildDefaultOk() {
		try {
			return RemoveJsonUtil.dataToJson(RemoveResponse.instanceOk(null));
		} catch (RemoveApplicationException e) {
			return buildError(e);
		}
	}

	public static String buildDefaultError() {
		try {
			return RemoveJsonUtil.dataToJson(RemoveResponse.instanceError(EMessageBundleKeys.ERROR_INTERNAL_SERVER));
		} catch (RemoveApplicationException e) {
			return buildError(e);
		}
	}
	public static String buildErrorNoImplementado() {
		try {
			return RemoveJsonUtil.dataToJson(RemoveResponse.instanceError(EMessageBundleKeys.ERROR_SERVICE_NOT_IMPLEMENTED));
		} catch (RemoveApplicationException e) {
			return buildError(e);
		}
	}
	public static String buildError(EMessageBundleKeys mensaje) {
		try {
			return RemoveJsonUtil.dataToJson(RemoveResponse.instanceError(mensaje));
		} catch (RemoveApplicationException e) {
			return buildError(e);
		}
	}
	public static String buildError(EMessageBundleKeys mensaje, String mensajeComplementario) {
		try {
			return RemoveJsonUtil.dataToJson(RemoveResponse.instanceErrorMessage(mensaje, mensajeComplementario));
		} catch (RemoveApplicationException e) {
			return buildError(e);
		}
	}
	public static String buildError(RemoveApplicationException ex) {
		try {
			return RemoveJsonUtil.dataToJson(RemoveResponse.instanceError(ex.getError()));
		} catch (RemoveApplicationException e) {
			return "";
		}
	}
	public static String buildError(Exception ex) {
		try {
			return RemoveJsonUtil.dataToJson(RemoveResponse.instanceError(EMessageBundleKeys.ERROR_INTERNAL_SERVER));
		} catch (RemoveApplicationException e) {
			return buildError(e);
		}
	}

	public static String buildWarning(Object response, EMessageBundleKeys mensaje) {
		try {
			return RemoveJsonUtil.dataToJson(RemoveResponse.instanceWarning(response, mensaje));
		} catch (RemoveApplicationException e) {
			return buildError(e);
		}
	}
	public static String buildWarning(EMessageBundleKeys mensaje) {
		try {
			return RemoveJsonUtil.dataToJson(RemoveResponse.instanceWarning(null, mensaje));
		} catch (RemoveApplicationException e) {
			return buildError(e);
		}
	}

	public static String stringfy(RemoveResponse response) {
		try {
			return RemoveJsonUtil.dataToJson(response);
		} catch (RemoveApplicationException e) {
			return buildError(e);
		}
	}
}
