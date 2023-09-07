package cl.atianza.remove.dtos.clients;

import cl.atianza.remove.dtos.AbstractDto;
@Deprecated
public class ClientPaymentKeyDto extends AbstractDto {
	private Long id_client;
	private String platform;
	private String key;
	private boolean active;
	
	public ClientPaymentKeyDto() {
		super();
	}

	public Long getId_client() {
		return id_client;
	}

	public void setId_client(Long id_client) {
		this.id_client = id_client;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "ClientPaymentKeyDto [id_client=" + id_client + ", platform=" + platform + ", key=" + key + ", active="
				+ active + "]";
	}
}
