package cl.atianza.remove.dtos.clients;

import java.time.LocalDateTime;
import java.util.UUID;

import cl.atianza.remove.dtos.AbstractDto;
@Deprecated
public class ClientSuscriptionAttemptDto extends AbstractDto {
	private Long id_plan;
	private String uuid = UUID.randomUUID().toString();
	private String email;
	private String name;
	private String lastname;
	private LocalDateTime creation_date;
	private boolean finished;
	private boolean successful;
	
	public ClientSuscriptionAttemptDto() {
		super();
	}

	public Long getId_plan() {
		return id_plan;
	}

	public void setId_plan(Long id_plan) {
		this.id_plan = id_plan;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	@Override
	public String toString() {
		return "ClientSuscriptionAttempt [id_plan=" + id_plan + ", email=" + email + ", name=" + name + ", lastname="
				+ lastname + ", creation_date=" + creation_date + ", finished=" + finished + ", successful="
				+ successful + "]";
	}
}
