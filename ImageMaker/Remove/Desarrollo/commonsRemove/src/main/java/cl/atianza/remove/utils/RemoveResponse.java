package cl.atianza.remove.utils;

import cl.atianza.remove.enums.EMessageBundleKeys;

public class RemoveResponse {
	private int code;
	private String message;
	private Object data;
	private boolean success;
	private boolean warning;
	private boolean error;
	
	public RemoveResponse() {
		super();
	}
	public RemoveResponse(int code, String message, Object data, boolean success, boolean warning, boolean error) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
		this.success = success;
		this.warning = warning;
		this.error = error;
	}
	
	public static RemoveResponse instanceDefaultOk() {
		return new RemoveResponse(EMessageBundleKeys.OK.getCode(), EMessageBundleKeys.OK.getTag(), null, true, false, false);
	}
	public static RemoveResponse instanceOk(Object data) {
		return new RemoveResponse(EMessageBundleKeys.OK.getCode(), EMessageBundleKeys.OK.getTag(), data, true, false, false);
	}
	public static RemoveResponse instanceWarning(Object data, EMessageBundleKeys mensaje) {
		return new RemoveResponse(mensaje.getCode(), mensaje.getTag(), data, false, true, false);
	}
	public static RemoveResponse instanceWarning(Object data, String mensaje) {
		return new RemoveResponse(EMessageBundleKeys.WARNING_DEFAULT.getCode(), mensaje, data, false, true, false);
	}
	public static RemoveResponse instanceWarning(String mensaje) {
		return new RemoveResponse(EMessageBundleKeys.WARNING_DEFAULT.getCode(), mensaje, null, false, true, false);
	}
	public static RemoveResponse instanceError(EMessageBundleKeys mensaje) {
		return new RemoveResponse(mensaje.getCode(), mensaje.getTag(), null, false, false, true);
	}
	public static RemoveResponse instanceError(EMessageBundleKeys mensaje, Object data) {
		return new RemoveResponse(mensaje.getCode(), mensaje.getTag(), data, false, false, true);
	}
	public static RemoveResponse instanceErrorMessage(EMessageBundleKeys mensaje, String mensajeExtra) {
		return new RemoveResponse(mensaje.getCode(), mensaje.getTag()+mensajeExtra, null, false, false, true);
	}
	public static RemoveResponse instanceErrorMessage(EMessageBundleKeys mensaje, String mensajeExtra, Object data) {
		return new RemoveResponse(mensaje.getCode(), mensaje.getTag()+mensajeExtra, data, false, false, true);
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getTag() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isWarning() {
		return warning;
	}
	public void setWarning(boolean warning) {
		this.warning = warning;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return "Respuesta [code=" + code + ", message=" + message + ", data=" + data + ", success=" + success
				+ ", warning=" + warning + ", error=" + error + "]";
	}
}
