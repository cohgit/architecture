package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;
@Deprecated
public class OwnerPasswordDto {
	private Long id_owner;
	private String password;
	private LocalDateTime creation_date;
	private LocalDateTime expiration_date;
	private boolean active;
	
	public OwnerPasswordDto() {
		super();
	}

	public Long getId_owner() {
		return id_owner;
	}


	public void setId_owner(Long id_owner) {
		this.id_owner = id_owner;
	}


	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "UserPasswordDto [id_owner=" + id_owner + ", password=" + password + ", creation_date=" + creation_date
				+ ", expiration_date=" + expiration_date + ", active=" + active + "]";
	}
}
