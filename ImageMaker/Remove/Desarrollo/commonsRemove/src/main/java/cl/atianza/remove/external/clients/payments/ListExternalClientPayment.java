package cl.atianza.remove.external.clients.payments;

import java.util.ArrayList;
import java.util.List;

import cl.atianza.remove.enums.EPaymentMethods;
@Deprecated
public class ListExternalClientPayment {
	private EPaymentMethods platform;
	private List<ExternalClientPayment> payments;
	
	public ListExternalClientPayment() {
		super();
	}
	public ListExternalClientPayment(EPaymentMethods platform, List<ExternalClientPayment> payments) {
		super();
		this.platform = platform;
		this.payments = payments;
	}
	
	public ListExternalClientPayment(EPaymentMethods platform, ExternalClientPayment detail) {
		this.platform = platform;
		this.getPayments().add(detail);
	}
	
	public EPaymentMethods getPlatform() {
		return platform;
	}
	public void setPlatform(EPaymentMethods platform) {
		this.platform = platform;
	}
	public List<ExternalClientPayment> getPayments() {
		if (payments == null) {
			payments = new ArrayList<ExternalClientPayment>();
		}
		return payments;
	}
	public void setPayments(List<ExternalClientPayment> payments) {
		this.payments = payments;
	}
	
	@Override
	public String toString() {
		return "ListExternalClientPayment [platform=" + platform + ", payments=" + payments + "]";
	}
}
