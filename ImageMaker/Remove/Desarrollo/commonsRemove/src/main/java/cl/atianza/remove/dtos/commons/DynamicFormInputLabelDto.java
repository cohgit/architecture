package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class DynamicFormInputLabelDto extends AbstractDto {
	private Long id_dynamic_forms_input;
	private String label;
	private String language;
	
	public DynamicFormInputLabelDto() {
		super();
	}

	public DynamicFormInputLabelDto(String label, String language) {
		super();
		this.label = label;
		this.language = language;
	}

	public Long getId_dynamic_forms_input() {
		return id_dynamic_forms_input;
	}

	public void setId_dynamic_forms_input(Long id_dynamic_forms_input) {
		this.id_dynamic_forms_input = id_dynamic_forms_input;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "DynamicFormInputLabelDto [id_dynamic_forms_input=" + id_dynamic_forms_input + ", label=" + label
				+ ", language=" + language + "]";
	}
}
