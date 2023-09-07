package cl.atianza.remove.external.clients.serp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveSERPRequestInfo {
	private boolean success;
	private Integer credits_used;
	private Integer credits_used_this_request;
	private Long credits_remaining;
	private String credits_reset_at;
	
	public RemoveSERPRequestInfo() {
		super();
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Integer getCredits_used() {
		return credits_used;
	}
	public void setCredits_used(Integer credits_used) {
		this.credits_used = credits_used;
	}
	public Integer getCredits_used_this_request() {
		return credits_used_this_request;
	}
	public void setCredits_used_this_request(Integer credits_used_this_request) {
		this.credits_used_this_request = credits_used_this_request;
	}
	public Long getCredits_remaining() {
		return credits_remaining;
	}
	public void setCredits_remaining(Long credits_remaining) {
		this.credits_remaining = credits_remaining;
	}
	public String getCredits_reset_at() {
		return credits_reset_at;
	}
	public void setCredits_reset_at(String credits_reset_at) {
		this.credits_reset_at = credits_reset_at;
	}
	@Override
	public String toString() {
		return "RemoveSERPRequestInfo [success=" + success + ", credits_used=" + credits_used
				+ ", credits_used_this_request=" + credits_used_this_request + ", credits_remaining="
				+ credits_remaining + ", credits_reset_at=" + credits_reset_at + "]";
	}
}
