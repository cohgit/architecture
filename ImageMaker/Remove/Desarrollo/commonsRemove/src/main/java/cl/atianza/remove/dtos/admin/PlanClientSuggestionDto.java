package cl.atianza.remove.dtos.admin;

import java.util.UUID;

import cl.atianza.remove.dtos.AbstractDto;

public class PlanClientSuggestionDto extends AbstractDto {
	private String uuid = UUID.randomUUID().toString();
	private Long id_plan;
	private Long id_client;
	private String client_name;
	private String client_email;
	private boolean email_sent;
	private boolean client_already_registred;
	private boolean client_attend_suggestion;
	
	public PlanClientSuggestionDto() {
		super();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getId_plan() {
		return id_plan;
	}

	public void setId_plan(Long id_plan) {
		this.id_plan = id_plan;
	}

	public Long getId_client() {
		return id_client;
	}

	public void setId_client(Long id_client) {
		this.id_client = id_client;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getClient_email() {
		return client_email;
	}

	public void setClient_email(String client_email) {
		this.client_email = client_email;
	}

	public boolean isEmail_sent() {
		return email_sent;
	}

	public void setEmail_sent(boolean email_sent) {
		this.email_sent = email_sent;
	}

	public boolean isClient_already_registred() {
		return client_already_registred;
	}

	public void setClient_already_registred(boolean client_already_registred) {
		this.client_already_registred = client_already_registred;
	}

	public boolean isClient_attend_suggestion() {
		return client_attend_suggestion;
	}

	public void setClient_attend_suggestion(boolean client_attend_suggestion) {
		this.client_attend_suggestion = client_attend_suggestion;
	}

	@Override
	public String toString() {
		return "PlanClientSuggestionDto [id_plan=" + id_plan + ", id_client=" + id_client + ", client_name="
				+ client_name + ", client_email=" + client_email + ", email_sent=" + email_sent
				+ ", client_already_registred=" + client_already_registred + ", client_attend_suggestion="
				+ client_attend_suggestion + "]";
	}
}
