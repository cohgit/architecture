package cl.atianza.remove.dtos.admin;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;
@Deprecated
public class UserPasswordDto extends AbstractDto {
	private Long id_user;
	private String password;
	private LocalDateTime creation_date;
	private LocalDateTime expiration_date;
	private boolean active;
	
	public UserPasswordDto() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
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

	@Override
	public String toString() {
		return "UserPasswordDto [id_user=" + id_user + ", password=" + password + ", creation_date=" + creation_date
				+ ", expiration_date=" + expiration_date + ", active=" + active + "]";
	}
}
