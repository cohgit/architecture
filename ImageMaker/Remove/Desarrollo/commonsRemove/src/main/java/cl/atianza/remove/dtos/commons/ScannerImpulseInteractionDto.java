package cl.atianza.remove.dtos.commons;

import java.time.LocalDate;

import cl.atianza.remove.dtos.AbstractDto;

public class ScannerImpulseInteractionDto extends AbstractDto {
	private Long id_scanner_impulse_variables;
	private String concept;
	private String category;
	private String section;
	private String action_url;
	private String exact_url;
	private Long quantity;
	private LocalDate init_date;
	private LocalDate end_date;
	
	public ScannerImpulseInteractionDto() {
		super();
	}

	public Long getId_scanner_impulse_variables() {
		return id_scanner_impulse_variables;
	}

	public void setId_scanner_impulse_variables(Long id_scanner_impulse_variables) {
		this.id_scanner_impulse_variables = id_scanner_impulse_variables;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getAction_url() {
		return action_url;
	}

	public void setAction_url(String action_url) {
		this.action_url = action_url;
	}

	public String getExact_url() {
		return exact_url;
	}

	public void setExact_url(String exact_url) {
		this.exact_url = exact_url;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public LocalDate getInit_date() {
		return init_date;
	}

	public void setInit_date(LocalDate init_date) {
		this.init_date = init_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	@Override
	public String toString() {
		return "ScannerImpulseInteractionDto [id_scanner_impulse_variables=" + id_scanner_impulse_variables
				+ ", concept=" + concept + ", category=" + category + ", section=" + section + ", action_url="
				+ action_url + ", exact_url=" + exact_url + ", quantity=" + quantity + ", init_date=" + init_date
				+ ", end_date=" + end_date + "]";
	}
}
