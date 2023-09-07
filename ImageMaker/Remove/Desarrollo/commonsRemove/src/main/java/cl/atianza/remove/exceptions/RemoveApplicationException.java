package cl.atianza.remove.exceptions;

import java.util.List;

import cl.atianza.remove.enums.EMessageBundleKeys;

/**
 * Exception class for applications exceptions.
 * 
 * @author Jose Gutierrez
 */ 
public class RemoveApplicationException extends Throwable {
	private static final long serialVersionUID = 6675897307477684570L;

	private EMessageBundleKeys severalError;
	private EMessageBundleKeys error;
	private List<String> params;
	
	public static RemoveApplicationException initError(EMessageBundleKeys error) {
		return new RemoveApplicationException(null, error);
	}
	public static RemoveApplicationException initError(EMessageBundleKeys error, List<String> params) {
		return new RemoveApplicationException(null, error, params);
	}
	public static RemoveApplicationException initSeveralError(EMessageBundleKeys severalError) {
		return new RemoveApplicationException(severalError, null);
	}
	
	public RemoveApplicationException(EMessageBundleKeys severalError, EMessageBundleKeys error) {
		super();
		this.severalError = severalError;
		this.error = error;
	}
	public RemoveApplicationException(EMessageBundleKeys severalError, EMessageBundleKeys error, List<String> params) {
		super();
		this.severalError = severalError;
		this.error = error;
		this.params = params;
	}

	public EMessageBundleKeys getError() {
		return error;
	}
	public void setError(EMessageBundleKeys error) {
		this.error = error;
	}

	public EMessageBundleKeys getSeveralError() {
		return severalError;
	}

	public void setSeveralError(EMessageBundleKeys severalError) {
		this.severalError = severalError;
	}
	
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
		this.params = params;
	}
	public boolean isSeveral() {
		return this.getSeveralError() != null;
	}
}
