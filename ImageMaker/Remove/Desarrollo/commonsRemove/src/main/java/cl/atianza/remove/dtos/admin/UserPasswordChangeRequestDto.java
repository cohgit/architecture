package cl.atianza.remove.dtos.admin;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserPasswordChangeRequestDto {
	private Long id_owner;
	private String uuid = UUID.randomUUID().toString();
	private LocalDateTime creation_date;
	private LocalDateTime expiration_date;
	private boolean confirmed;
	
	public UserPasswordChangeRequestDto() {
		super();
	}

	public Long getId_owner() {
		return id_owner;
	}

	public void setId_owner(Long id_owner) {
		this.id_owner = id_owner;
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

	@Override
	public String toString() {
		return "ClientPasswordChangeRequest [id_owner=" + id_owner + ", uuid=" + uuid + ", creation_date="
				+ creation_date + ", expiration_date=" + expiration_date + ", confirmed=" + confirmed + "]";
	}
}
