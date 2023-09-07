package cl.atianza.remove.dtos.clients;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;

public class ClientAuditAccessDto extends AbstractDto {
	private Long id_client;
	private Long id_user_action;
	private String profile_user_action;
	private String description;
	private String parameters;
	private LocalDateTime action_date;
	
	public ClientAuditAccessDto() {
		super();
	}

	public Long getId_client() {
		return id_client;
	}

	public void setId_client(Long id_client) {
		this.id_client = id_client;
	}

	public Long getId_user_action() {
		return id_user_action;
	}

	public void setId_user_action(Long id_user_action) {
		this.id_user_action = id_user_action;
	}

	public String getProfile_user_action() {
		return profile_user_action;
	}

	public void setProfile_user_action(String profile_user_action) {
		this.profile_user_action = profile_user_action;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getAction_date() {
		return action_date;
	}

	public void setAction_date(LocalDateTime action_date) {
		this.action_date = action_date;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "ClientAuditAccessDto [id_client=" + id_client + ", id_user_action=" + id_user_action
				+ ", profile_user_action=" + profile_user_action + ", description=" + description + ", action_date="
				+ action_date + "]";
	}
}
