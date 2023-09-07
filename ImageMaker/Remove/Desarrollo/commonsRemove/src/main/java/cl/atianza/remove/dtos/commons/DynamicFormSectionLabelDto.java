package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class DynamicFormSectionLabelDto extends AbstractDto {
	private Long id_dynamic_forms_section;
	private String label;
	private String language;
	
	public DynamicFormSectionLabelDto() {
		super();
	}

	public DynamicFormSectionLabelDto(String label, String language) {
		super();
		this.label = label;
		this.language = language;
	}

	public Long getId_dynamic_forms_section() {
		return id_dynamic_forms_section;
	}

	public void setId_dynamic_forms_section(Long id_dynamic_forms_input_options) {
		this.id_dynamic_forms_section = id_dynamic_forms_input_options;
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
		return "DynamicFormSectionLabelDto [id_dynamic_forms_section=" + id_dynamic_forms_section + ", label=" + label
				+ ", language=" + language + "]";
	}
}
