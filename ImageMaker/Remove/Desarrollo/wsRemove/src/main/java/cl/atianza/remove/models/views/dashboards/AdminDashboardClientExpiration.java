package cl.atianza.remove.models.views.dashboards;

import java.time.LocalDate;
@Deprecated
public class AdminDashboardClientExpiration {
	private String clientName;
	private String plan;
	private LocalDate expirationDate;
	
	public AdminDashboardClientExpiration() {
		super();
	}
	
	public AdminDashboardClientExpiration(String clientName, String plan, LocalDate expirationDate) {
		super();
		this.clientName = clientName;
		this.plan = plan;
		this.expirationDate = expirationDate;
	}

	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	@Override
	public String toString() {
		return "AdminDashboardClientExpiration [clientName=" + clientName + ", plan=" + plan + ", expirationDate="
				+ expirationDate + "]";
	}
}
