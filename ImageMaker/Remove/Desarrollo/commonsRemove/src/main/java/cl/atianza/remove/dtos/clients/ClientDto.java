package cl.atianza.remove.dtos.clients;

import java.util.UUID;

import cl.atianza.remove.dtos.AbstractDto;

public class ClientDto extends AbstractDto {
	private String uuid = UUID.randomUUID().toString();
	private Long id_country;
	private String name;
	private String lastname;
	private String email;
	private String language;
	private String phone;
	private String project_name;
	private boolean corporative;
	private String business_name;
	private String dni;
	private String fiscal_address;
	private String postal;
	private boolean active = true;
	private Integer failed_attempts;
	private boolean first_time;
	private boolean email_verified;
	private boolean readOnly;
	private String message;
	private boolean send_email;
	private String reference_link_logo;
	private Integer periocity;
	private boolean view_payments;
	
	public ClientDto() {
		super();
	}
	
	public ClientDto(Long id) {
		super(id);
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
	public Long getId_country() {
		return id_country;
	}

	public void setId_country(Long id_country) {
		this.id_country = id_country;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public boolean isCorporative() {
		return corporative;
	}

	public void setCorporative(boolean corporative) {
		this.corporative = corporative;
	}

	public String getBusiness_name() {
		return business_name;
	}

	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getFiscal_address() {
		return fiscal_address;
	}

	public void setFiscal_address(String fiscal_address) {
		this.fiscal_address = fiscal_address;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public boolean isFirst_time() {
		return first_time;
	}

	public void setFirst_time(boolean first_time) {
		this.first_time = first_time;
	}

	public boolean isEmail_verified() {
		return email_verified;
	}

	public void setEmail_verified(boolean email_verified) {
		this.email_verified = email_verified;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSend_email() {
		return send_email;
	}

	public void setSend_email(boolean send_email) {
		this.send_email = send_email;
	}
	
	public String getReference_link_logo() {
		return reference_link_logo;
	}

	public void setReference_link_logo(String reference_link_logo) {
		this.reference_link_logo = reference_link_logo;
	}
	
	

	public Integer getPeriocity() {
		return periocity;
	}

	public void setPeriocity(Integer periocity) {
		this.periocity = periocity;
	}
	
	

	public boolean isView_payments() {
		return view_payments;
	}

	public void setView_payments(boolean view_payments) {
		this.view_payments = view_payments;
	}

	@Override
	public String toString() {
		return "ClientDto [uuid=" + uuid + ", id_country=" + id_country + ", name=" + name + ", lastname=" + lastname
				+ ", email=" + email + ", language=" + language + ", phone=" + phone + ", project_name=" + project_name
				+ ", corporative=" + corporative + ", business_name=" + business_name + ", dni=" + dni
				+ ", fiscal_address=" + fiscal_address + ", postal=" + postal + ", active=" + active
				+ ", failed_attempts=" + failed_attempts + ", first_time=" + first_time + ", email_verified="
				+ email_verified + ", readOnly=" + readOnly + ", message=" + message + ", send_email=" + send_email
				+ ", reference_link_logo=" + reference_link_logo + ", periocity=" + periocity + ", view_payments="
				+ view_payments + "]";
	}




		
}
