package cl.atianza.remove.models.views;

import java.util.ArrayList;

import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.utils.RemoveDateUtils;
@Deprecated
public class ClientSuscription {
	private NewClient client;
	private String password;
	private String uuidSuscription;
	private Plan plan;
	private String paymentTrackingCode;
	private String paymentMethod;
	private boolean attempt;
	
	public ClientSuscription() {
		super();
	}
	
	public NewClient getClient() {
		return client;
	}
	public void setClient(NewClient client) {
		this.client = client;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
	public String getPaymentTrackingCode() {
		return paymentTrackingCode;
	}

	public String getUuidSuscription() {
		return uuidSuscription;
	}

	public void setUuidSuscription(String uuidSuscription) {
		this.uuidSuscription = uuidSuscription;
	}

	public void setPaymentTrackingCode(String paymentTrackingCode) {
		this.paymentTrackingCode = paymentTrackingCode;
	}

	public boolean isAttempt() {
		return attempt;
	}

	public void setAttempt(boolean attempt) {
		this.attempt = attempt;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String toString() {
		return "ClientSuscription [client=" + client + ", password=" + password + ", plan=" + plan + "]";
	}

	public Client buildClientObject() {
		Client client = new Client();

		client.setName(this.getClient().getName());
		client.setLastname(this.getClient().getLastname());
		client.setEmail(this.getClient().getEmail());
		client.setActive(true);
		client.setBusiness_name("");
		client.setCorporative(false);
		client.setDni("");
		client.setEmail_verified(false);
		client.setFirst_time(true);
		client.setFiscal_address("");
		client.setPhone(this.getClient().getPhone());
		client.setPostal("");
		
		ClientPlan clientPlan = new ClientPlan();
		clientPlan.setDetail(this.getPlan());
		clientPlan.setActive(true);
		clientPlan.setCredits_used(0l);
		clientPlan.setSuscribe_date(RemoveDateUtils.nowLocalDateTime());
		client.setPlanActives(new ArrayList<ClientPlan>());
		client.getPlanActives().add(clientPlan);
		
		return client;
	}
}