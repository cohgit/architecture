package cl.atianza.remove.models.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cl.atianza.remove.dtos.admin.PlanDto;

public class Plan extends PlanDto {
	private List<PlanClientSuggestion> clientSuggestions;
	private List<String> sectionsToSearch;
	private Long totalActiveClients;
	private PlanExternalKey stripeKey;

	public Plan() {
		super();
	}

	public List<PlanClientSuggestion> getClientSuggestions() {
		if (this.clientSuggestions == null) {
			this.clientSuggestions = new ArrayList<PlanClientSuggestion>();
		}
		return clientSuggestions;
	}

	public void setClientSuggestions(List<PlanClientSuggestion> clientSuggestions) {
		this.clientSuggestions = clientSuggestions;
	}

	public Long getTotalActiveClients() {
		return totalActiveClients;
	}

	public void setTotalActiveClients(Long totalActiveClients) {
		this.totalActiveClients = totalActiveClients;
	}

	public List<String> getSectionsToSearch() {
		if (sectionsToSearch == null) {
			this.sectionsToSearch = new ArrayList<String>();
		}
		return sectionsToSearch;
	}

	public void setSectionsToSearch(List<String> sectionsToSearch) {
		this.sectionsToSearch = sectionsToSearch;
	}

	public PlanExternalKey getStripeKey() {
		return stripeKey;
	}

	public void setStripeKey(PlanExternalKey stripeKey) {
		this.stripeKey = stripeKey;
	}

	@Override
	public String toString() {
		return "Plan [" + super.toString() + ", clientSuggestions=" + clientSuggestions + "]";
	}

	public void splitSearchType() {
		if (this.getSections_to_search() != null) {
			this.setSectionsToSearch(Arrays.asList(this.getSections_to_search().split(",")));
		}
	}

	public void joinSearchList() {
		if (this.getSectionsToSearch() != null && !this.getSectionsToSearch().isEmpty()) {
			this.setSections_to_search(this.getSectionsToSearch().stream().collect(Collectors.joining(",")));
		}
	}

	public boolean esCostZero() {
		return this.getCost() != null && this.getCost().floatValue() == 0f;
	}
}
