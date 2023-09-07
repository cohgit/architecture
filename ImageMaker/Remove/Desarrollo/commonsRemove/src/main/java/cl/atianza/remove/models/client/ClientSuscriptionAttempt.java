package cl.atianza.remove.models.client;

import cl.atianza.remove.dtos.clients.ClientSuscriptionAttemptDto;
import cl.atianza.remove.models.admin.Plan;
@Deprecated
public class ClientSuscriptionAttempt extends ClientSuscriptionAttemptDto {
	private Plan plan;

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
}
