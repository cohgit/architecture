package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;

public class DynamicFormDto extends AbstractDto {
	private String version;
	private String type;
	private LocalDateTime creation_date;
	private LocalDateTime activation_date;
	private LocalDateTime deactivation_date;
	private String template;
	private boolean active;
	
	public DynamicFormDto() {
		super();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	public LocalDateTime getActivation_date() {
		return activation_date;
	}

	public void setActivation_date(LocalDateTime activation_date) {
		this.activation_date = activation_date;
	}

	public LocalDateTime getDeactivation_date() {
		return deactivation_date;
	}

	public void setDeactivation_date(LocalDateTime deactivation_date) {
		this.deactivation_date = deactivation_date;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "DynamicFormDto [version=" + version + ", type=" + type + ", creation_date=" + creation_date
				+ ", activation_date=" + activation_date + ", deactivation_date=" + deactivation_date + ", template="
				+ template + ", active=" + active + "]";
	}
}
