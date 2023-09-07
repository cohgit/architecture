package cl.atianza.remove.models.commons;

import cl.atianza.remove.dtos.commons.ScannerImpulseInteractionDto;
import cl.atianza.remove.enums.EImpulseInteractionCategory;
import cl.atianza.remove.enums.EImpulseInteractionConcept;
import cl.atianza.remove.enums.ESearchTypes;

public class ScannerImpulseInteraction extends ScannerImpulseInteractionDto {
	private EImpulseInteractionCategory categoryType;
	private EImpulseInteractionConcept conceptType;
	private ESearchTypes sectionType;
	private boolean markToDelete;

	public boolean isMarkToDelete() {
		return markToDelete;
	}

	public void setMarkToDelete(boolean markToDelete) {
		this.markToDelete = markToDelete;
	}

	public EImpulseInteractionCategory getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(EImpulseInteractionCategory categoryType) {
		this.categoryType = categoryType;
	}

	public EImpulseInteractionConcept getConceptType() {
		return conceptType;
	}

	public void setConceptType(EImpulseInteractionConcept conceptType) {
		this.conceptType = conceptType;
	}

	public ESearchTypes getSectionType() {
		return sectionType;
	}

	public void setSectionType(ESearchTypes sectionType) {
		this.sectionType = sectionType;
	}
}
