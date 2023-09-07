package cl.atianza.remove.models.admin;

import cl.atianza.remove.dtos.admin.PlanClientSuggestionDto;

public class PlanClientSuggestion extends PlanClientSuggestionDto {
	private Plan plan;
	private boolean markToDelete;
	
	public PlanClientSuggestion() {
		super();
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public boolean isMarkToDelete() {
		return markToDelete;
	}

	public void setMarkToDelete(boolean markToDelete) {
		this.markToDelete = markToDelete;
	}
}
