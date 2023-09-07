package cl.atianza.remove.dtos;

import java.time.LocalDateTime;

public class AlertMessageDto extends AbstractDto {
	private Long id_owner;
	private String title;
	private String description;
	private String type;
	private String params;
	private LocalDateTime creation_date;
	private boolean readed;
	private boolean hidden;
	private boolean urgent;
	private boolean must_send_email;
	private boolean email_sent;
	
	public AlertMessageDto() {
		super();
	}
	
	public Long getId_owner() {
		return id_owner;
	}
	public void setId_owner(Long id_owner) {
		this.id_owner = id_owner;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public LocalDateTime getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}
	public boolean isReaded() {
		return readed;
	}
	public void setReaded(boolean readed) {
		this.readed = readed;
	}
	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isUrgent() {
		return urgent;
	}
	public void setUrgent(boolean urgent) {
		this.urgent = urgent;
	}
	
	public boolean isMust_send_email() {
		return must_send_email;
	}

	public void setMust_send_email(boolean must_send_email) {
		this.must_send_email = must_send_email;
	}

	public boolean isEmail_sent() {
		return email_sent;
	}

	public void setEmail_sent(boolean email_sent) {
		this.email_sent = email_sent;
	}

	@Override
	public String toString() {
		return "AlertMessageDto [id_owner=" + id_owner + ", title=" + title + ", description=" + description + ", type="
				+ type + ", params=" + params + ", creation_date=" + creation_date + ", readed=" + readed + ", urgent="
				+ urgent + "]";
	}
}
