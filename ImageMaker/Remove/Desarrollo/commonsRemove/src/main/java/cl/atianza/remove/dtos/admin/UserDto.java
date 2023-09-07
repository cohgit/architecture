package cl.atianza.remove.dtos.admin;

import java.util.UUID;

import cl.atianza.remove.dtos.AbstractDto;

public class UserDto extends AbstractDto {
	private String uuid = UUID.randomUUID().toString();
	private String name;
	private String lastname;
	private String email;
	private String phone;
	private String profile;
	private String language;
	private boolean locked = false;
	private boolean active = true;
	private Integer failed_attempts;
	private boolean send_email;
	
	private boolean first_time;
	
	public UserDto() {
		super();
	}
	
	public UserDto(Long id) {
		super(id);
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
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Integer getFailed_attempts() {
		return failed_attempts;
	}
	public void setFailed_attempts(Integer failed_attempts) {
		this.failed_attempts = failed_attempts;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}

	public boolean isFirst_time() {
		return first_time;
	}

	public void setFirst_time(boolean first_time) {
		this.first_time = first_time;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isSend_email() {
		return send_email;
	}

	public void setSend_email(boolean send_email) {
		this.send_email = send_email;
	}

	@Override
	public String toString() {
		return "UserDto [uuid=" + uuid + ", name=" + name + ", email=" + email + ", phone=" + phone + ", profile="
				+ profile + ", language=" + language + ", locked=" + locked + ", active=" + active
				+ ", failed_attempts=" + failed_attempts + ", first_time=" + first_time + "]";
	}
}
