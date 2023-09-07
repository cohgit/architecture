package cl.atianza.remove.dtos.admin;

import cl.atianza.remove.dtos.AbstractDto;
@Deprecated
public class PlanExternalKeyDto extends AbstractDto {
	private Long id_plan;
	private String key;
	private String key_payment;
	private String platform;
	private boolean active;
	
	public PlanExternalKeyDto() {
		super();
	}

	public Long getId_plan() {
		return id_plan;
	}

	public void setId_plan(Long id_plan) {
		this.id_plan = id_plan;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey_payment() {
		return key_payment;
	}

	public void setKey_payment(String key_payment) {
		this.key_payment = key_payment;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "PlanExternalKeyDto [id_plan=" + id_plan + ", key=" + key + ", platform=" + platform + ", active="
				+ active + "]";
	}
}
