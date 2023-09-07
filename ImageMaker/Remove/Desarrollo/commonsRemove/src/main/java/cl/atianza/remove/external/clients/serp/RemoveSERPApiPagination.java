package cl.atianza.remove.external.clients.serp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveSERPApiPagination {
	private String next;

	public RemoveSERPApiPagination() {
		super();
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}
}
