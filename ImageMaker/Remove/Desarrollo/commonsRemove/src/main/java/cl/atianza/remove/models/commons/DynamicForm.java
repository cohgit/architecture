package cl.atianza.remove.models.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cl.atianza.remove.dtos.commons.DynamicFormDto;

public class DynamicForm extends DynamicFormDto {
	private List<DynamicFormSection> sections;
	private List<DynamicFormConditionResult> conditionResults;

	public DynamicForm() {
		super();
	}

	public List<DynamicFormSection> getSections() {
		if (sections == null) {
			sections = new ArrayList<DynamicFormSection>();
		}
		return sections;
	}

	public void setSections(List<DynamicFormSection> sections) {
		this.sections = sections;
	}

	public List<DynamicFormConditionResult> getConditionResults() {
		if (conditionResults == null) {
			conditionResults = new ArrayList<DynamicFormConditionResult>();
		}
		
		return conditionResults;
	}

	public void setConditionResults(List<DynamicFormConditionResult> conditionResults) {
		this.conditionResults = conditionResults;
	}

	public void fixLabelsAndDescriptions(String language) {
		this.getSections().forEach(section -> section.fixLabelsAndDescriptions(language));
		this.getConditionResults().forEach(condition -> condition.fixLabels(language));
	}

	@Override
	public String toString() {
		return "DynamicForm [" + super.toString() + ", sections=" + sections + ", conditionResults=" + conditionResults + "]";
	}

	public void addCondition(DynamicFormConditionResult condition) {
		this.getConditionResults().add(condition);
	}

	public void sort() {
		if (this.sections != null) {
			Collections.sort(sections);
			
			for(DynamicFormSection section : sections) {
				section.sort();
			}
		}
		
		if (conditionResults != null) {
			Collections.sort(conditionResults);
		}
	}
}
