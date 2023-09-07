package cl.atianza.remove.external.clients.serp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveSerpAccountDataResponse {
	private Object request_info;
	private RemoveSerpAccountInfo account_info;
	public RemoveSerpAccountDataResponse() {
		super();
	}
	public Object getRequest_info() {
		return request_info;
	}
	public void setRequest_info(Object request_info) {
		this.request_info = request_info;
	}
	public RemoveSerpAccountInfo getAccount_info() {
		return account_info;
	}
	public void setAccount_info(RemoveSerpAccountInfo account_info) {
		this.account_info = account_info;
	}
	@Override
	public String toString() {
		return "RemoveSerpAccountDataResponse [request_info=" + request_info + ", account_info=" + account_info + "]";
	}
}
