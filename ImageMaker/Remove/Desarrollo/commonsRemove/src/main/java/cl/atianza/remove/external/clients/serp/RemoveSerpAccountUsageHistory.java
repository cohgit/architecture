package cl.atianza.remove.external.clients.serp;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveSerpAccountUsageHistory {
	private Integer month_number;
	private Integer year;
	private boolean is_current_month;
	private Long credits_total_for_month;
	private Map<String, Long> credits_total_per_day;
	public RemoveSerpAccountUsageHistory() {
		super();
	}
	public Integer getMonth_number() {
		return month_number;
	}
	public void setMonth_number(Integer month_number) {
		this.month_number = month_number;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public boolean isIs_current_month() {
		return is_current_month;
	}
	public void setIs_current_month(boolean is_current_month) {
		this.is_current_month = is_current_month;
	}
	public Long getCredits_total_for_month() {
		return credits_total_for_month;
	}
	public void setCredits_total_for_month(Long credits_total_for_month) {
		this.credits_total_for_month = credits_total_for_month;
	}
	public Map<String, Long> getCredits_total_per_day() {
		return credits_total_per_day;
	}
	public void setCredits_total_per_day(Map<String, Long> credits_total_per_day) {
		this.credits_total_per_day = credits_total_per_day;
	}
	@Override
	public String toString() {
		return "RemoveSerpAccountUsageHistory [month_number=" + month_number + ", year=" + year + ", is_current_month="
				+ is_current_month + ", credits_total_for_month=" + credits_total_for_month + ", credits_total_per_day="
				+ credits_total_per_day + "]";
	}
}
