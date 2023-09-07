package cl.atianza.remove.dtos.admin;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;

public class UserAuditAccessDto extends AbstractDto {
	private Long id_user;
	private String profile;
	private String description;
	private String parameters;
	private LocalDateTime action_date;
	
	public UserAuditAccessDto() {
		super();
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public LocalDateTime getAction_date() {
		return action_date;
	}

	public void setAction_date(LocalDateTime action_date) {
		this.action_date = action_date;
	}

	@Override
	public String toString() {
		return "UserAuditAccessDto [id_user=" + id_user + ", profile=" + profile + ", description=" + description
				+ ", parameters=" + parameters + ", action_date=" + action_date + "]";
	}
}
