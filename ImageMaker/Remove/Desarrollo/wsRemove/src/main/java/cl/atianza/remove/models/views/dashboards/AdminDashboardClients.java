package cl.atianza.remove.models.views.dashboards;

import java.util.ArrayList;
import java.util.List;
@Deprecated
public class AdminDashboardClients {
	private List<AdminDashboardClientExpiration> nextDue;
	private List<AdminDashboardGraphElement> newRegisters;
	public AdminDashboardClients() {
		super();
	}
	public List<AdminDashboardClientExpiration> getNextDue() {
		if (nextDue == null) {
			nextDue = new ArrayList<AdminDashboardClientExpiration>();
		}
		
		return nextDue;
	}
	public void setNextDue(List<AdminDashboardClientExpiration> nextDue) {
		this.nextDue = nextDue;
	}
	public List<AdminDashboardGraphElement> getNewRegisters() {
		if (newRegisters == null) {
			newRegisters = new ArrayList<AdminDashboardGraphElement>();
		}
		return newRegisters;
	}
	public void setNewRegisters(List<AdminDashboardGraphElement> newRegisters) {
		this.newRegisters = newRegisters;
	}
}