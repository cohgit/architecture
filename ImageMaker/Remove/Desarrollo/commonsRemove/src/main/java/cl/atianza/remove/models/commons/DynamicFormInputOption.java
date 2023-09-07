package cl.atianza.remove.models.commons;

import java.util.ArrayList;
import java.util.List;

import cl.atianza.remove.dtos.commons.DynamicFormInputOptionDto;

public class DynamicFormInputOption extends DynamicFormInputOptionDto {
	private List<DynamicFormInputOptionsLabel> labels;
	private String label;
	private boolean checked;

	public DynamicFormInputOption() {
		super();
	}

	public DynamicFormInputOption(String name, boolean active) {
		super(name, active);
	}

	public List<DynamicFormInputOptionsLabel> getLabels() {
		if (labels == null) {
			labels = new ArrayList<DynamicFormInputOptionsLabel>();
		}
		return labels;
	}

	public void setLabels(List<DynamicFormInputOptionsLabel> labels) {
		this.labels = labels;
	}
	
	public void addLabel(DynamicFormInputOptionsLabel label) {
		this.getLabels().add(label);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void fixLabel(String language) {
		if (!this.getLabels().isEmpty()) {
			this.label = this.getLabels().get(0).getLabel();
			
			for (DynamicFormInputOptionsLabel lab : this.getLabels()) {
				if (lab.getLanguage().equals(language)) {
					this.label = lab.getLabel();
					break;
				}
			}
		}
	}
}
