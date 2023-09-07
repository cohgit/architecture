package cl.atianza.remove.models.client;

import cl.atianza.remove.dtos.clients.ClientPlanDto;
import cl.atianza.remove.models.admin.Plan;
@Deprecated
public class ClientPlan extends ClientPlanDto {
	private Plan detail;
	private ClientPaybill paybill;

	public ClientPlan() {
		super();
	}

	public Plan getDetail() {
		return detail;
	}

	public void setDetail(Plan detail) {
		this.detail = detail;
	}

	public ClientPaybill getPaybill() {
		return paybill;
	}

	public void setPaybill(ClientPaybill paybill) {
		this.paybill = paybill;
	}

	@Override
	public String toString() {
		return "ClientPlan ["+ super.toString() + " detail=" + detail + "]";
	}

	public void addCreditsUsed(long totalCalls) {
		this.setCredits_used(this.getCredits_used().longValue() + totalCalls);
	}
	
	public void outCreditsUsed(long totalCalls) {
		this.setCredits_used(this.getCredits_used().longValue() - totalCalls);
	}
}
