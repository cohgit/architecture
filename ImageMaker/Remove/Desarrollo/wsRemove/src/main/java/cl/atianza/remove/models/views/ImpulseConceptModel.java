package cl.atianza.remove.models.views;

import java.util.List;

import cl.atianza.remove.enums.EImpulseInteractionCategory;
import cl.atianza.remove.enums.EImpulseInteractionConcept;

public class ImpulseConceptModel {
	private EImpulseInteractionConcept concept;
	private List<EImpulseInteractionCategory> categories;
	
	public ImpulseConceptModel() {
		super();
	}
	public ImpulseConceptModel(EImpulseInteractionConcept concept, List<EImpulseInteractionCategory> categories) {
		super();
		this.concept = concept;
		this.categories = categories;
	}
	public EImpulseInteractionConcept getConcept() {
		return concept;
	}
	public void setConcept(EImpulseInteractionConcept concept) {
		this.concept = concept;
	}
	public List<EImpulseInteractionCategory> getCategories() {
		return categories;
	}
	public void setCategories(List<EImpulseInteractionCategory> categories) {
		this.categories = categories;
	}
}
