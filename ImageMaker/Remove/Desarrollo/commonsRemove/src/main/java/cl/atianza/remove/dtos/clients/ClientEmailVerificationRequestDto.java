package cl.atianza.remove.dtos.clients;

import java.time.LocalDateTime;
import java.util.UUID;

import cl.atianza.remove.dtos.AbstractDto;

public class ClientEmailVerificationRequestDto extends AbstractDto {
	private Long id_client;
	private String uuid = UUID.randomUUID().toString();
	private LocalDateTime creation_date;
	private LocalDateTime expiration_date;
	private String email;
	private boolean confirmed;
	private boolean actived;
	
	public ClientEmailVerificationRequestDto() {
		super();
	}
	public ClientEmailVerificationRequestDto(String uuid) {
		super();
		this.uuid = uuid;
	}

	public Long getId_client() {
		return id_client;
	}

	public void setId_client(Long id_client) {
		this.id_client = id_client;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	public LocalDateTime getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(LocalDateTime expiration_date) {
		this.expiration_date = expiration_date;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}

	@Override
	public String toString() {
		return "ClientEmailVerificationRequestDto [id_client=" + id_client + ", uuid=" + uuid + ", creation_date="
				+ creation_date + ", expiration_date=" + expiration_date + ", email=" + email + ", confirmed="
				+ confirmed + ", actived=" + actived + "]";
	}
}
