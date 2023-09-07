package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class DynamicFormConditionResultLabelDto extends AbstractDto {
	private Long id_dynamic_forms_conditions_results;
	private String label;
	private String language;
	
	public DynamicFormConditionResultLabelDto() {
		super();
	}

	public DynamicFormConditionResultLabelDto(String label, String language) {
		super();
		this.label = label;
		this.language = language;
	}

	public Long getId_dynamic_forms_conditions_results() {
		return id_dynamic_forms_conditions_results;
	}

	public void setId_dynamic_forms_conditions_results(Long id_dynamic_forms_conditions_results) {
		this.id_dynamic_forms_conditions_results = id_dynamic_forms_conditions_results;
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
		return "DynamicFormConditionResultLabelDto [id_dynamic_forms_conditions_results="
				+ id_dynamic_forms_conditions_results + ", label=" + label + ", language=" + language + "]";
	}
}
