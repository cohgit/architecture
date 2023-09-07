package cl.atianza.remove.external.clients.payments;

import java.time.LocalDateTime;
@Deprecated
public class ExternalClientPayment {
	private String country;
	private Long amount_due;
	private Long amount_paid;
	private Long amount_remaining;
	private String billing_reason;
	private String currency;
	private Long subtotal;
	private Long total;
	private LocalDateTime created;
	
	public ExternalClientPayment() {
		super();
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Long getAmount_due() {
		return amount_due;
	}
	public void setAmount_due(Long amount_due) {
		this.amount_due = amount_due;
	}
	public Long getAmount_paid() {
		return amount_paid;
	}
	public void setAmount_paid(Long amount_paid) {
		this.amount_paid = amount_paid;
	}
	public Long getAmount_remaining() {
		return amount_remaining;
	}
	public void setAmount_remaining(Long amount_remaining) {
		this.amount_remaining = amount_remaining;
	}
	public String getBilling_reason() {
		return billing_reason;
	}
	public void setBilling_reason(String billing_reason) {
		this.billing_reason = billing_reason;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Long getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Long subtotal) {
		this.subtotal = subtotal;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "ExternalClientPayment [country=" + country + ", amount_due=" + amount_due + ", amount_paid="
				+ amount_paid + ", amount_remaining=" + amount_remaining + ", billing_reason=" + billing_reason
				+ ", currency=" + currency + ", subtotal=" + subtotal + ", total=" + total + "]";
	}
}
