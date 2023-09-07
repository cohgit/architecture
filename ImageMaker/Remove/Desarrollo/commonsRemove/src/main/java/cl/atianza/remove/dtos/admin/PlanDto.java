package cl.atianza.remove.dtos.admin;

import cl.atianza.remove.dtos.AbstractDto;

@Deprecated
public class PlanDto extends AbstractDto {
	private String name;
	private String description;
	private Integer duration_months;
	private Float cost;
	private boolean automatic_renewal;
	
	private Integer max_keywords;
	private Integer max_countries;
	private Integer max_search_page;
	private String sections_to_search;
	
	private boolean access_scanner;
	private Long limit_credits;
	
	private boolean access_monitor;
	private Integer total_monitor_licenses;
	
	private boolean access_transforma;
	private Integer total_transforma_licenses;
	private Integer max_url_to_remove;
	private Integer max_url_to_impulse;
	private Integer target_page;
	
	private boolean access_deindexation;
	private Integer max_url_to_deindexate;
	
	private boolean customized;
	private Boolean quote_requested;
	private Boolean quote_approved;
	private boolean active = true;
	
	public PlanDto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDuration_months() {
		return duration_months;
	}

	public void setDuration_months(Integer duration_months) {
		this.duration_months = duration_months;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public boolean isAutomatic_renewal() {
		return automatic_renewal;
	}

	public void setAutomatic_renewal(boolean automatic_renewal) {
		this.automatic_renewal = automatic_renewal;
	}

	public Integer getMax_url_to_deindexate() {
		return max_url_to_deindexate;
	}

	public void setMax_url_to_deindexate(Integer max_url_to_deindexate) {
		this.max_url_to_deindexate = max_url_to_deindexate;
	}

	public boolean isAccess_deindexation() {
		return access_deindexation;
	}

	public void setAccess_deindexation(boolean access_deindexation) {
		this.access_deindexation = access_deindexation;
	}

	public Long getLimit_credits() {
		return limit_credits;
	}

	public void setLimit_credits(Long limit_credits) {
		this.limit_credits = limit_credits;
	}

	public Integer getMax_keywords() {
		return max_keywords;
	}

	public void setMax_keywords(Integer max_keywords) {
		this.max_keywords = max_keywords;
	}

	public Integer getMax_countries() {
		return max_countries;
	}

	public void setMax_countries(Integer max_countries) {
		this.max_countries = max_countries;
	}

	public Integer getMax_search_page() {
		return max_search_page;
	}

	public void setMax_search_page(Integer max_search_page) {
		this.max_search_page = max_search_page;
	}

	public String getSections_to_search() {
		return sections_to_search;
	}

	public void setSections_to_search(String sections_to_search) {
		this.sections_to_search = sections_to_search;
	}

	public Integer getMax_url_to_remove() {
		return max_url_to_remove;
	}

	public void setMax_url_to_remove(Integer max_url_to_remove) {
		this.max_url_to_remove = max_url_to_remove;
	}

	public Integer getMax_url_to_impulse() {
		return max_url_to_impulse;
	}

	public void setMax_url_to_impulse(Integer max_url_to_impulse) {
		this.max_url_to_impulse = max_url_to_impulse;
	}

	public Integer getTarget_page() {
		return target_page;
	}

	public void setTarget_page(Integer target_page) {
		this.target_page = target_page;
	}

	public boolean isCustomized() {
		return customized;
	}

	public void setCustomized(boolean customized) {
		this.customized = customized;
	}

	public boolean isAccess_scanner() {
		return access_scanner;
	}

	public void setAccess_scanner(boolean access_scanner) {
		this.access_scanner = access_scanner;
	}

	public boolean isAccess_monitor() {
		return access_monitor;
	}

	public void setAccess_monitor(boolean access_monitor) {
		this.access_monitor = access_monitor;
	}

	public boolean isAccess_transforma() {
		return access_transforma;
	}

	public void setAccess_transforma(boolean access_transforma) {
		this.access_transforma = access_transforma;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Boolean getQuote_requested() {
		return quote_requested;
	}

	public void setQuote_requested(Boolean quote_requested) {
		this.quote_requested = quote_requested;
	}

	public Boolean getQuote_approved() {
		return quote_approved;
	}

	public void setQuote_approved(Boolean quote_approved) {
		this.quote_approved = quote_approved;
	}

	public Integer getTotal_monitor_licenses() {
		return total_monitor_licenses;
	}

	public void setTotal_monitor_licenses(Integer total_monitor_licenses) {
		this.total_monitor_licenses = total_monitor_licenses;
	}

	public Integer getTotal_transforma_licenses() {
		return total_transforma_licenses;
	}

	public void setTotal_transforma_licenses(Integer total_transforma_licenses) {
		this.total_transforma_licenses = total_transforma_licenses;
	}

	@Override
	public String toString() {
		return "PlanDto [name=" + name + ", description=" + description + ", duration_months=" + duration_months
				+ ", cost=" + cost + ", automatic_renewal=" + automatic_renewal + ", limit_credits=" + limit_credits
				+ ", max_keywords=" + max_keywords + ", max_countries=" + max_countries + ", max_search_page="
				+ max_search_page + ", sections_to_search=" + sections_to_search + ", max_url_to_remove="
				+ max_url_to_remove + ", max_url_to_impulse=" + max_url_to_impulse + ", customized=" + customized
				+ ", access_scanner=" + access_scanner + ", access_monitor=" + access_monitor + ", access_transforma="
				+ access_transforma + ", active=" + active + "]";
	}
}
