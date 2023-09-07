package cl.atianza.remove.models.commons;

import java.util.ArrayList;
import java.util.List;

import cl.atianza.remove.dtos.commons.DynamicFormConditionResultDto;

public class DynamicFormConditionResult extends DynamicFormConditionResultDto implements Comparable<DynamicFormConditionResult> {
	private List<DynamicFormConditionResultLabel> labels;
	private String label;

	public DynamicFormConditionResult() {
		super();
	}

	public DynamicFormConditionResult(String type, Integer order, String condition, boolean active) {
		super(type, order, condition, active);
	}
	
	public List<DynamicFormConditionResultLabel> getLabels() {
		if (labels == null) {
			labels = new ArrayList<DynamicFormConditionResultLabel>();
		}
		return labels;
	}

	public void setLabels(List<DynamicFormConditionResultLabel> labels) {
		this.labels = labels;
	}
	
	public void addLabel(DynamicFormConditionResultLabel label) {
		this.getLabels().add(label);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void fixLabels(String language) {
		if (!this.getLabels().isEmpty()) {
			this.label = this.getLabels().get(0).getLabel();
			
			for (DynamicFormConditionResultLabel lab : this.getLabels()) {
				if (lab.getLanguage().equals(language)) {
					this.label = lab.getLabel();
					break;
				}
			}
		}
	}

	@Override
	public int compareTo(DynamicFormConditionResult o) {
		return Integer.compare(getPosition(), o.getPosition());
	}
}
