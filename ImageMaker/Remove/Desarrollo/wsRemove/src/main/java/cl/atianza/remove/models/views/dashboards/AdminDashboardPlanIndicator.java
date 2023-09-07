package cl.atianza.remove.models.views.dashboards;
@Deprecated
public class AdminDashboardPlanIndicator {
	private AdminDashboardPlanIndicatorDetail transform;
	private AdminDashboardPlanIndicatorDetail one_shot;
	private AdminDashboardPlanIndicatorDetail monitor;
	private AdminDashboardPlanIndicatorDetail desindexation;
	
	public AdminDashboardPlanIndicator() {
		super();
	}

	public AdminDashboardPlanIndicatorDetail getTransform() {
		return transform;
	}

	public void setTransform(AdminDashboardPlanIndicatorDetail transform) {
		this.transform = transform;
	}

	public AdminDashboardPlanIndicatorDetail getOne_shot() {
		return one_shot;
	}

	public void setOne_shot(AdminDashboardPlanIndicatorDetail one_shot) {
		this.one_shot = one_shot;
	}

	public AdminDashboardPlanIndicatorDetail getMonitor() {
		return monitor;
	}

	public void setMonitor(AdminDashboardPlanIndicatorDetail monitor) {
		this.monitor = monitor;
	}

	public AdminDashboardPlanIndicatorDetail getDesindexation() {
		return desindexation;
	}

	public void setDesindexation(AdminDashboardPlanIndicatorDetail desindexation) {
		this.desindexation = desindexation;
	}

	public void instanceTransform(Long scanActive, Long totalUrl, Long success) {
		this.transform = new AdminDashboardPlanIndicatorDetail(scanActive, totalUrl, success);
	}
	public void instanceMonitor(Long scanActive) {
		this.monitor = new AdminDashboardPlanIndicatorDetail(scanActive);
	}
	public void instanceOneShot(Long scanActive) {
		this.one_shot = new AdminDashboardPlanIndicatorDetail(scanActive);
	}
	public void instanceDesindexation(Long scanActive, Long totalUrl) {
		this.desindexation = new AdminDashboardPlanIndicatorDetail(scanActive, totalUrl);
	}
}

class AdminDashboardPlanIndicatorDetail {
	private Long scanActive;
	private Long totalUrl;
	private Long success;
	
	public AdminDashboardPlanIndicatorDetail() {
		super();
	}
	
	public AdminDashboardPlanIndicatorDetail(Long scanActive) {
		super();
		this.scanActive = scanActive;
	}

	public AdminDashboardPlanIndicatorDetail(Long scanActive, Long totalUrl) {
		super();
		this.scanActive = scanActive;
		this.totalUrl = totalUrl;
	}

	public AdminDashboardPlanIndicatorDetail(Long scanActive, Long totalUrl, Long success) {
		super();
		this.scanActive = scanActive;
		this.totalUrl = totalUrl;
		this.success = success;
	}

	public Long getScanActive() {
		return scanActive;
	}
	public void setScanActive(Long scanActive) {
		this.scanActive = scanActive;
	}
	public Long getTotalUrl() {
		return totalUrl;
	}
	public void setTotalUrl(Long totalUrl) {
		this.totalUrl = totalUrl;
	}
	public Long getSuccess() {
		return success;
	}
	public void setSuccess(Long success) {
		this.success = success;
	}
}