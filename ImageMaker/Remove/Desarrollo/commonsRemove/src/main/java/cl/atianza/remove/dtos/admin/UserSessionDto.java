package cl.atianza.remove.dtos.admin;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;

public class UserSessionDto extends AbstractDto {
	private Long id_owner;
	private String token;
	private LocalDateTime entry_date;
	private LocalDateTime refresh_date;
	private LocalDateTime close_date;
	private String ip;
	private String agent;
	
	public UserSessionDto() {
		super();
	}

	public Long getId_owner() {
		return id_owner;
	}

	public void setId_owner(Long id_owner) {
		this.id_owner = id_owner;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(LocalDateTime entry_date) {
		this.entry_date = entry_date;
	}

	public LocalDateTime getRefresh_date() {
		return refresh_date;
	}

	public void setRefresh_date(LocalDateTime refresh_date) {
		this.refresh_date = refresh_date;
	}

	public LocalDateTime getClose_date() {
		return close_date;
	}

	public void setClose_date(LocalDateTime close_date) {
		this.close_date = close_date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	@Override
	public String toString() {
		return "ClientSessionDto [id_owner=" + id_owner + ", token=" + token + ", entry_date="
				+ entry_date + ", refresh_date=" + refresh_date + ", close_date=" + close_date + ", ip=" + ip
				+ ", agent=" + agent + "]";
	}
}
