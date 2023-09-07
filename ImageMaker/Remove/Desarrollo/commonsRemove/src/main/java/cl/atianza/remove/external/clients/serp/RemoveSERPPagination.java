package cl.atianza.remove.external.clients.serp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveSERPPagination {
	private RemoveSERPApiPagination api_pagination;

	public RemoveSERPPagination() {
		super();
	}

	public RemoveSERPApiPagination getApi_pagination() {
		return api_pagination;
	}

	public void setApi_pagination(RemoveSERPApiPagination api_pagination) {
		this.api_pagination = api_pagination;
	}

	@Override
	public String toString() {
		return "RemoveSERPPagination [api_pagination=" + api_pagination + "]";
	}
}
