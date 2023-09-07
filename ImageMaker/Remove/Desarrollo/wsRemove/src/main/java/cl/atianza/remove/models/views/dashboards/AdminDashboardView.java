package cl.atianza.remove.models.views.dashboards;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Deprecated
public class AdminDashboardView {
	private LocalDate filterMonthYear;
	
	private AdminDashboardEconomics economics;
	private AdminDashboardSerps serps;
	private AdminDashboardPlanIndicator plansIndicators;
	private AdminDashboardClients clients;
	
	private List<AdminDashboardGraphElement> piePlan;
	private List<AdminDashboardGraphElement> incomesBars;
	
	public AdminDashboardView() {
		super();
		economics = new AdminDashboardEconomics();
		serps = new AdminDashboardSerps();
		plansIndicators = new AdminDashboardPlanIndicator();
		clients = new AdminDashboardClients();
	}

	public LocalDate getFilterMonthYear() {
		return filterMonthYear;
	}

	public void setFilterMonthYear(LocalDate filterMonthYear) {
		this.filterMonthYear = filterMonthYear;
	}

	public AdminDashboardEconomics getEconomics() {
		return economics;
	}

	public void setEconomics(AdminDashboardEconomics economics) {
		this.economics = economics;
	}

	public AdminDashboardSerps getSerps() {
		return serps;
	}

	public void setSerps(AdminDashboardSerps serps) {
		this.serps = serps;
	}

	public AdminDashboardPlanIndicator getPlansIndicators() {
		return plansIndicators;
	}

	public void setPlansIndicators(AdminDashboardPlanIndicator plansIndicators) {
		this.plansIndicators = plansIndicators;
	}

	public AdminDashboardClients getClients() {
		return clients;
	}

	public void setClients(AdminDashboardClients clients) {
		this.clients = clients;
	}

	public List<AdminDashboardGraphElement> getPiePlan() {
		if (piePlan == null) {
			piePlan = new ArrayList<AdminDashboardGraphElement>();
		}
		return piePlan;
	}

	public void setPiePlan(List<AdminDashboardGraphElement> piePlans) {
		this.piePlan = piePlans;
	}

	public List<AdminDashboardGraphElement> getIncomesBars() {
		if (incomesBars == null) {
			incomesBars = new ArrayList<AdminDashboardGraphElement>();
		}
		return incomesBars;
	}

	public void setIncomesBars(List<AdminDashboardGraphElement> incomesBars) {
		this.incomesBars = incomesBars;
	}

	public void calculateEconomicsCurrentYear() {
		this.getEconomics().setCurrentYear(0l);
		
		if (this.getIncomesBars() != null) {
			this.getIncomesBars().forEach(bar -> {
				this.getEconomics().plusCurrentYear(bar.getValue());
			});
		}
	}
}
