package cl.atianza.remove.external.clients.payments;

import cl.atianza.remove.models.client.ClientPaybill;
import cl.atianza.remove.models.client.ClientPaymentKey;
@Deprecated
public class ExternalPaymentReference {
	private ClientPaymentKey clientPaymentKey;
	private ClientPaybill clientPaybill;
	private String customerEmail;
	private String subscriptionTrackingCode;
	
	public ExternalPaymentReference() {
		super();
		this.clientPaybill = new ClientPaybill();
		this.clientPaymentKey = new ClientPaymentKey();
		this.clientPaymentKey.setActive(true);
	}
	public ExternalPaymentReference(String platform) {
		super();
		this.clientPaybill = new ClientPaybill();
		this.clientPaymentKey = new ClientPaymentKey();
		this.clientPaymentKey.setPlatform(platform);
		this.clientPaymentKey.setActive(true);
	}
	
	public ClientPaymentKey getClientPaymentKey() {
		return clientPaymentKey;
	}
	public void setClientPaymentKey(ClientPaymentKey clientPaymentKey) {
		this.clientPaymentKey = clientPaymentKey;
	}
	public ClientPaybill getClientPaybill() {
		return clientPaybill;
	}
	public void setClientPaybill(ClientPaybill clientPaybill) {
		this.clientPaybill = clientPaybill;
	}
	
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getSubscriptionTrackingCode() {
		return subscriptionTrackingCode;
	}
	public void setSubscriptionTrackingCode(String subscriptionTrackingCode) {
		this.subscriptionTrackingCode = subscriptionTrackingCode;
	}
	@Override
	public String toString() {
		return "ExternalPaymentReference [clientPaymentKey=" + clientPaymentKey + ", clientPaybill=" + clientPaybill
				+ ", customerEmail=" + customerEmail + ", subscriptionTrackingCode=" + subscriptionTrackingCode + "]";
	}
}
