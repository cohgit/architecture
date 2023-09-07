package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class DynamicFormInputOptionsLabelDto extends AbstractDto {
	private Long id_dynamic_forms_input_options;
	private String label;
	private String language;
	
	public DynamicFormInputOptionsLabelDto() {
		super();
	}

	public DynamicFormInputOptionsLabelDto(String label, String language) {
		super();
		this.label = label;
		this.language = language;
	}

	public Long getId_dynamic_forms_input_options() {
		return id_dynamic_forms_input_options;
	}

	public void setId_dynamic_forms_input_options(Long id_dynamic_forms_input_options) {
		this.id_dynamic_forms_input_options = id_dynamic_forms_input_options;
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
		return "DynamicFormInputOptionsLabelDto [id_dynamic_forms_input_options=" + id_dynamic_forms_input_options
				+ ", label=" + label + ", language=" + language + "]";
	}
}
