package cl.atianza.remove.models.client;

import cl.atianza.remove.dtos.clients.ClientPaybillDto;
import cl.atianza.remove.models.admin.User;
@Deprecated
public class ClientPaybill extends ClientPaybillDto {
	private String method;
	private String planName;
	private String approvedBy;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
}
