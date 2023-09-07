package cl.atianza.remove.models.views;

import java.util.ArrayList;
import java.util.List;

import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.external.clients.payments.ExternalPaymentMethod;
import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.commons.Scanner;
@Deprecated
public class PlanChangeSuscriptionInfo {
	private Plan newPlan;
	private Plan actualPlan;
	private ExternalPaymentMethod paymentMethod;
	private Long clientPlanToChangeId;
	private List<Plan> plansAvailables;
	private List<Plan> plansCustomized;
	private List<ExternalPaymentMethod> paymentsMethodsAvailables;
	private List<ScannerSuscriptionInfo> scannersToMigrate;
	private List<ScannerSuscriptionInfo> scannersToMigrateRecurrent;
	private List<ScannerSuscriptionInfo> scannersToMigrateTransforma;
	private boolean programmedToNextPayment;
	
	public PlanChangeSuscriptionInfo() {
		super();
	}
	public Plan getNewPlan() {
		return newPlan;
	}
	public void setNewPlan(Plan newPlan) {
		this.newPlan = newPlan;
	}
	public Plan getActualPlan() {
		return actualPlan;
	}
	public void setActualPlan(Plan actualPlan) {
		this.actualPlan = actualPlan;
	}
	public Long getClientPlanToChangeId() {
		return clientPlanToChangeId;
	}
	public void setClientPlanToChangeId(Long clientPlanToChangeId) {
		this.clientPlanToChangeId = clientPlanToChangeId;
	}
	public ExternalPaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(ExternalPaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public List<Plan> getPlansCustomized() {
		if (plansCustomized == null) {
			plansCustomized = new ArrayList<Plan>();
		}
		return plansCustomized;
	}
	public void setPlansCustomized(List<Plan> plansCustomized) {
		this.plansCustomized = plansCustomized;
	}
	public List<Plan> getPlansAvailables() {
		if (plansAvailables == null) {
			plansAvailables = new ArrayList<Plan>();
		}
		return plansAvailables;
	}
	public void setPlansAvailables(List<Plan> plansAvailables) {
		this.plansAvailables = plansAvailables;
	}
	public List<ExternalPaymentMethod> getPaymentsMethodsAvailables() {
		if (paymentsMethodsAvailables == null) {
			paymentsMethodsAvailables = new ArrayList<ExternalPaymentMethod>();
		}
		return paymentsMethodsAvailables;
	}
	public void setPaymentsMethodsAvailables(List<ExternalPaymentMethod> paymentsMethodsAvailables) {
		this.paymentsMethodsAvailables = paymentsMethodsAvailables;
	}
	public List<ScannerSuscriptionInfo> getScannersToMigrate() {
		if (scannersToMigrate == null) {
			scannersToMigrate = new ArrayList<ScannerSuscriptionInfo>();
		}
		return scannersToMigrate;
	}
	public void setScannersToMigrate(List<ScannerSuscriptionInfo> scannersToMigrate) {
		this.scannersToMigrate = scannersToMigrate;
	}
	public boolean isProgrammedToNextPayment() {
		return programmedToNextPayment;
	}
	public void setProgrammedToNextPayment(boolean programmedToNextPayment) {
		this.programmedToNextPayment = programmedToNextPayment;
	}
	
	public List<ScannerSuscriptionInfo> getScannersToMigrateRecurrent() {
		if (scannersToMigrateRecurrent == null) {
			scannersToMigrateRecurrent = new ArrayList<ScannerSuscriptionInfo>();
			
			this.getScannersToMigrate().forEach(scan -> {
				if (scan.getType().equals(EScannerTypes.MONITOR.getCode())) {
					this.scannersToMigrateRecurrent.add(scan);
				}
			});
		}
		return scannersToMigrateRecurrent;
	}
	public void setScannersToMigrateRecurrent(List<ScannerSuscriptionInfo> scannersToMigrateRecurrent) {
		this.scannersToMigrateRecurrent = scannersToMigrateRecurrent;
	}
	public List<ScannerSuscriptionInfo> getScannersToMigrateTransforma() {
		if (scannersToMigrateTransforma == null) {
			scannersToMigrateTransforma = new ArrayList<ScannerSuscriptionInfo>();
			
			this.getScannersToMigrate().forEach(scan -> {
				
				if (scan.getType().equals(EScannerTypes.TRANSFORM.getCode())) {
					this.scannersToMigrateTransforma.add(scan);
				}
			});
		}
		return scannersToMigrateTransforma;
	}
	public void setScannersToMigrateTransforma(List<ScannerSuscriptionInfo> scannersToMigrateTransforma) {
		this.scannersToMigrateTransforma = scannersToMigrateTransforma;
	}
	public void addScanners(List<Scanner> listScanners) {
		if (listScanners != null) {
			listScanners.forEach(scanner -> {
				scanner.splitKeywords();
				this.getScannersToMigrate().add(new ScannerSuscriptionInfo(scanner.getType(), scanner.getId(), 
						scanner.getUuid(), scanner.getName(), scanner.getKeywordsSplited(), 
						scanner.getCreation_date(), false));
			});
		}
	}
	public List<Long> buildScannersToMigrateIds() {
		List<Long> idsScanners = new ArrayList<Long>();
		
		this.getScannersToMigrateRecurrent().forEach(scan -> {
			if (scan.isChecked()) idsScanners.add(scan.getId());
		});
		this.getScannersToMigrateTransforma().forEach(scan -> {
			if (scan.isChecked()) idsScanners.add(scan.getId());
		});
		
		return idsScanners;
	}
}
