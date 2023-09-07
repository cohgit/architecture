package cl.atianza.remove.dtos.clients;

import java.time.LocalDateTime;
import java.util.UUID;

import cl.atianza.remove.dtos.AbstractDto;
@Deprecated
public class ClientPlanDto extends AbstractDto {
	private Long id_client;
	private Long id_plan;
	private Long id_client_paybills;
	private String uuid = UUID.randomUUID().toString();
	private LocalDateTime suscribe_date;
	private LocalDateTime expiration_date;
	private LocalDateTime cancellation_date;
	private Long credits_used;
	private Long deindex_used;
	private String external_subscription_key;
	private String external_subscription_platform;
	private boolean active;
	
	public ClientPlanDto() {
		super();
	}

	public Long getId_client() {
		return id_client;
	}

	public void setId_client(Long id_client) {
		this.id_client = id_client;
	}

	public Long getId_plan() {
		return id_plan;
	}

	public void setId_plan(Long id_plan) {
		this.id_plan = id_plan;
	}

	public Long getId_client_paybills() {
		return id_client_paybills;
	}

	public void setId_client_paybills(Long id_client_paybills) {
		this.id_client_paybills = id_client_paybills;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public LocalDateTime getSuscribe_date() {
		return suscribe_date;
	}

	public void setSuscribe_date(LocalDateTime suscribe_date) {
		this.suscribe_date = suscribe_date;
	}

	public LocalDateTime getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(LocalDateTime expiration_date) {
		this.expiration_date = expiration_date;
	}

	public LocalDateTime getCancellation_date() {
		return cancellation_date;
	}

	public void setCancellation_date(LocalDateTime cancellation_date) {
		this.cancellation_date = cancellation_date;
	}

	public Long getCredits_used() {
		return credits_used;
	}

	public void setCredits_used(Long credits_used) {
		this.credits_used = credits_used;
	}

	public Long getDeindex_used() {
		return deindex_used;
	}

	public void setDeindex_used(Long deindex_used) {
		this.deindex_used = deindex_used;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getExternal_subscription_key() {
		return external_subscription_key;
	}

	public void setExternal_subscription_key(String external_subscription_key) {
		this.external_subscription_key = external_subscription_key;
	}

	public String getExternal_subscription_platform() {
		return external_subscription_platform;
	}

	public void setExternal_subscription_platform(String external_subscription_platform) {
		this.external_subscription_platform = external_subscription_platform;
	}

	@Override
	public String toString() {
		return "ClientPlanDto [id_client=" + id_client + ", id_plan=" + id_plan + ", id_client_paybills="
				+ id_client_paybills + ", suscribe_date=" + suscribe_date + ", expiration_date=" + expiration_date
				+ ", cancellation_date=" + cancellation_date + ", credits_used=" + credits_used + ", active=" + active
				+ "]";
	}
}
