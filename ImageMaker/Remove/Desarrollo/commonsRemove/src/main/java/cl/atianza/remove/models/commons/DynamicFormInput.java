package cl.atianza.remove.models.commons;

import java.util.ArrayList;
import java.util.List;

import cl.atianza.remove.dtos.commons.DynamicFormInputDto;

public class DynamicFormInput extends DynamicFormInputDto implements Comparable<DynamicFormInput> {
	private List<DynamicFormInputLabel> labels;
	private List<DynamicFormInputDescription> descriptions;
	private List<DynamicFormInputOption> options;
	private DynamicFormInputOption selectedOption;
	private List<DynamicFormInputOption> selectedOptions;
	private String label;
	private String description;
	private String errorMessage;

	public DynamicFormInput() {
		super();
	}

	public DynamicFormInput(String name, String type, Integer width, boolean required, String required_condition,
			boolean visible, String visible_condition, boolean enabled, String enabled_condition, String value,
			String value_condition, boolean active, Integer order) {
		super(name, type, width, required, required_condition, visible, visible_condition, enabled, enabled_condition, value,
				value_condition, active, order);
	}

	public List<DynamicFormInputLabel> getLabels() {
		if (labels == null) {
			labels = new ArrayList<DynamicFormInputLabel>();
		}
		return labels;
	}

	public void setLabels(List<DynamicFormInputLabel> labels) {
		this.labels = labels;
	}
	public void addLabel(DynamicFormInputLabel label) {
		this.getLabels().add(label);
	}
	
	public List<DynamicFormInputOption> getOptions() {
		if (options == null) {
			options = new ArrayList<DynamicFormInputOption>();
		}
		
		return options;
	}

	public void setOptions(List<DynamicFormInputOption> options) {
		this.options = options;
	}
	public void addOption(DynamicFormInputOption option) {
		this.getOptions().add(option);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public DynamicFormInputOption getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(DynamicFormInputOption selectedOption) {
		this.selectedOption = selectedOption;
	}

	public List<DynamicFormInputOption> getSelectedOptions() {
		return selectedOptions;
	}

	public void setSelectedOptions(List<DynamicFormInputOption> selectedOptions) {
		this.selectedOptions = selectedOptions;
	}

	public List<DynamicFormInputDescription> getDescriptions() {
		if (this.descriptions == null) {
			this.descriptions = new ArrayList<DynamicFormInputDescription>();
		}
		return descriptions;
	}

	public void setDescriptions(List<DynamicFormInputDescription> descriptions) {
		this.descriptions = descriptions;
	}
	
	public void addDescription(DynamicFormInputDescription description) {
		this.getDescriptions().add(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void fixLabelAndDescriptions(String language) {
		this.getOptions().forEach(option -> option.fixLabel(language));
		if (!this.getLabels().isEmpty()) {
			this.label = this.getLabels().get(0).getLabel();
			for (DynamicFormInputLabel lab : this.getLabels()) {
				if (lab.getLanguage().equals(language)) {
					this.label = lab.getLabel();
					break;
				}
			}
		}
		
		if (!this.getDescriptions().isEmpty()) {
			this.description =  this.getDescriptions().get(0).getDescription();
			for (DynamicFormInputDescription desc : this.getDescriptions()) {
				if (desc.getLanguage().equals(language)) {
					this.description = desc.getDescription();
					break;
				}
			}
		}
	}

	public Long extractSelectedOption() {
		for (DynamicFormInputOption option : this.getOptions()) {
			if (option.getName().equals(this.getValue())) {
				return option.getId();
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "DynamicFormInput [options=" + options + ", selectedOption=" + selectedOption + ", selectedOptions="
				+ selectedOptions + ", label=" + label + "]";
	}

	@Override
	public int compareTo(DynamicFormInput o) {
		return Integer.compare(getPosition(), o.getPosition());
	}
}
