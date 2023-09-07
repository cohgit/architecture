package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;
import java.util.UUID;

import cl.atianza.remove.dtos.AbstractDto;
import cl.atianza.remove.enums.EScannerStatus;

public class ScannerDto extends AbstractDto {
	private String uuid = UUID.randomUUID().toString();
	private String uuid_from;
	private Long id_owner;
	private Long id_client_plan;
	private LocalDateTime creation_date;
	private LocalDateTime activation_date;
	private LocalDateTime deactivation_date;
	private String name;
	private String status;
	private String type;
	private Long total_scans = 1l;
	
	public ScannerDto() {
		super();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid_from() {
		return uuid_from;
	}

	public void setUuid_from(String uuid_from) {
		this.uuid_from = uuid_from;
	}

	public Long getId_owner() {
		return id_owner;
	}

	public void setId_owner(Long id_owner) {
		this.id_owner = id_owner;
	}

	public Long getId_client_plan() {
		return id_client_plan;
	}

	public void setId_client_plan(Long id_client_plan) {
		this.id_client_plan = id_client_plan;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isActive() {
		return status != null && status.equals(EScannerStatus.ACTIVE.getTag());
	}

	public void setActive(boolean active) {}

	public boolean isExecuting() {
		return status != null && status.equals(EScannerStatus.EXECUTING.getTag());
	}

	public void setExecuting(boolean executing) {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getTotal_scans() {
		if (this.total_scans == null) {
			total_scans = 1l;
		}
		return total_scans;
	}

	public void setTotal_scans(Long total_scans) {
		this.total_scans = total_scans;
	}

	@Override
	public String toString() {
		return "ScannerDto [" + super.toString() + ", uuid=" + uuid + ", id_owner=" + id_owner + ", creation_date=" + creation_date
				+ ", activation_date=" + activation_date + ", deactivation_date=" + deactivation_date + ", type=" + type + "]";
	}
}
