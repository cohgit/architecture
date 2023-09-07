package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class DynamicFormInputDescriptionDto extends AbstractDto {
	private Long id_dynamic_forms_input;
	private String description;
	private String language;
	
	public DynamicFormInputDescriptionDto() {
		super();
	}

	public DynamicFormInputDescriptionDto(String description, String language) {
		super();
		this.description = description;
		this.language = language;
	}

	public Long getId_dynamic_forms_input() {
		return id_dynamic_forms_input;
	}

	public void setId_dynamic_forms_input(Long id_dynamic_forms_input) {
		this.id_dynamic_forms_input = id_dynamic_forms_input;
	}

	public String getDescription() {
		return description;
	}

	public void setDesctiption(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "DynamicFormInputLabelDto [id_dynamic_forms_input=" + id_dynamic_forms_input + ", description=" + description
				+ ", language=" + language + "]";
	}
}
