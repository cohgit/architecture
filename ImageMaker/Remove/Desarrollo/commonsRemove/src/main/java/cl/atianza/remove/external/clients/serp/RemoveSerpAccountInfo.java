package cl.atianza.remove.external.clients.serp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveSerpAccountInfo {
	private Long credits_used;
	private Long credits_remaining;
	private String credits_reset_at;
	private Long credits_limit;
	private List<RemoveSerpAccountUsageHistory> usage_history;
	public RemoveSerpAccountInfo() {
		super();
	}
	public Long getCredits_used() {
		return credits_used;
	}
	public void setCredits_used(Long credits_used) {
		this.credits_used = credits_used;
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
	public Long getCredits_limit() {
		return credits_limit;
	}
	public void setCredits_limit(Long credits_limit) {
		this.credits_limit = credits_limit;
	}
	public List<RemoveSerpAccountUsageHistory> getUsage_history() {
		return usage_history;
	}
	public void setUsage_history(List<RemoveSerpAccountUsageHistory> usage_history) {
		this.usage_history = usage_history;
	}
	@Override
	public String toString() {
		return "RemoveSerpAccountInfo [credits_used=" + credits_used + ", credits_remaining=" + credits_remaining
				+ ", credits_reset_at=" + credits_reset_at + ", credits_limit=" + credits_limit + ", usage_history="
				+ usage_history + "]";
	}
}
