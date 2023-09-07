package cl.atianza.remove.models.client;

import java.util.ArrayList;
import java.util.List;

import cl.atianza.remove.dtos.clients.ClientDto;
import cl.atianza.remove.external.clients.payments.ListExternalClientPayment;
import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.commons.Country;

public class Client extends ClientDto {
	private Country country;
	private List<ClientPlan> planActives;
	private List<Plan> planSuggestions;
	@Deprecated
	private List<ListExternalClientPayment> payments;
	private Float progress;
	private String message;
	private Object paymentPortal;
	private ClientPlan lastSuscription;
	private List<ClientPaybill> paybills;

	public Client() {
		super();
	}

	public Client(Long id) {
		super(id);
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<ClientPlan> getPlanActives() {
		return planActives;
	}

	public void setPlanActives(List<ClientPlan> planActives) {
		this.planActives = planActives;
	}

	public List<Plan> getPlanSuggestions() {
		return planSuggestions;
	}

	public void setPlanSuggestions(List<Plan> planSuggestions) {
		this.planSuggestions = planSuggestions;
	}

	
	public Float getProgress() {
		return progress;
	}

	public void setProgress(Float progress) {
		this.progress = progress;
	}

	public List<ListExternalClientPayment> getPayments() {
		if (payments == null) {
			this.payments = new ArrayList<ListExternalClientPayment>();
		}
		
		return payments;
	}

	public void setPayments(List<ListExternalClientPayment> payments) {
		this.payments = payments;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getPaymentPortal() {
		return paymentPortal;
	}

	public void setPaymentPortal(Object paymentPortal) {
		this.paymentPortal = paymentPortal;
	}

	public ClientPlan getLastSuscription() {
		return lastSuscription;
	}

	public void setLastSuscription(ClientPlan lastSuscription) {
		this.lastSuscription = lastSuscription;
	}

	public List<ClientPaybill> getPaybills() {
		if (paybills == null) {
			paybills = new ArrayList<ClientPaybill>();
		}
		
		return paybills;
	}

	public void setPaybills(List<ClientPaybill> paybills) {
		this.paybills = paybills;
	}

	@Override
	public String toString() {
		return "Client [" + super.toString() + ", country=" + country + ", plan=" + planActives + ", planSuggestions=" + planSuggestions + "]";
	}

	public String fullName() {
		return this.getName() + " " + this.getLastname();
	}
}
