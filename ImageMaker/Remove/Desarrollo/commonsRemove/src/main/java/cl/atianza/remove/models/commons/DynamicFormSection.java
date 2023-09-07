package cl.atianza.remove.models.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cl.atianza.remove.dtos.commons.DynamicFormSectionDto;

public class DynamicFormSection extends DynamicFormSectionDto implements Comparable<DynamicFormSection> {
	private List<DynamicFormSectionLabel> labels;
	private List<DynamicFormInput> inputs;
	private String label;

	public DynamicFormSection() {
		super();
	}

	public List<DynamicFormSectionLabel> getLabels() {
		if (labels == null) {
			labels = new ArrayList<DynamicFormSectionLabel>();
		}
		return labels;
	}

	public void setLabels(List<DynamicFormSectionLabel> labels) {
		this.labels = labels;
	}
	
	public void addLabel(DynamicFormSectionLabel label) {
		this.getLabels().add(label);
	}

	public List<DynamicFormInput> getInputs() {
		if (inputs == null) {
			inputs = new ArrayList<DynamicFormInput>();
		}
		return inputs;
	}

	public void setInputs(List<DynamicFormInput> inputs) {
		this.inputs = inputs;
	}
	public void addInput(DynamicFormInput input) {
		this.getInputs().add(input);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void fixLabelsAndDescriptions(String language) {
		this.getInputs().forEach(input -> input.fixLabelAndDescriptions(language));
		
		if (!this.getLabels().isEmpty()) {
			this.label = this.getLabels().get(0).getLabel();
			
			for (DynamicFormSectionLabel lab : this.getLabels()) {
				if (lab.getLanguage().equals(language)) {
					this.label = lab.getLabel();
					break;
				}
			}
		}
	}

	@Override
	public int compareTo(DynamicFormSection o) {
		return Integer.compare(getPosition(), o.getPosition());
	}

	public void sort() {
		if (inputs != null) {
			Collections.sort(inputs);			
		}
	}
}
