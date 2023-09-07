package cl.atianza.remove.dtos.clients;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;
@Deprecated
public class ClientPaybillDto extends AbstractDto {
	private Long id_client_key;
	private Long id_plan;
	private Long id_approved_by;
	private Float amount;
	private LocalDateTime payment_date;
	private String tracking_code;
	private String raw_response;
	private boolean confirmed;
	
	public ClientPaybillDto() {
		super();
	}

	public Long getId_plan() {
		return id_plan;
	}

	public void setId_plan(Long id_plan) {
		this.id_plan = id_plan;
	}

	public Long getId_approved_by() {
		return id_approved_by;
	}

	public void setId_approved_by(Long id_approved_by) {
		this.id_approved_by = id_approved_by;
	}

	public String getRaw_response() {
		return raw_response;
	}

	public void setRaw_response(String raw_response) {
		this.raw_response = raw_response;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public LocalDateTime getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(LocalDateTime payment_date) {
		this.payment_date = payment_date;
	}

	public String getTracking_code() {
		return tracking_code;
	}

	public void setTracking_code(String tracking_code) {
		this.tracking_code = tracking_code;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Long getId_client_key() {
		return id_client_key;
	}

	public void setId_client_key(Long id_client_key) {
		this.id_client_key = id_client_key;
	}

	@Override
	public String toString() {
		return "ClientPaybillDto [id_client_key=" + id_client_key + ", id_plan=" + id_plan + ", amount=" + amount
				+ ", payment_date=" + payment_date + ", tracking_code=" + tracking_code + ", raw_response="
				+ raw_response + ", confirmed=" + confirmed + "]";
	}
}
