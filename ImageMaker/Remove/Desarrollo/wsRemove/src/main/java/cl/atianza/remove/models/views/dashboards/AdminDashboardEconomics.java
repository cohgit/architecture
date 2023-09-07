package cl.atianza.remove.models.views.dashboards;
@Deprecated
public class AdminDashboardEconomics {
	private String currency;
	private Long currentMonth;
	private Long currentYear;
	private Long totalAmount;
	private Long mrr;
	private Long clv;
	
	public AdminDashboardEconomics() {
		super();
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Long getCurrentMonth() {
		return currentMonth;
	}
	public void setCurrentMonth(Long currentMonth) {
		this.currentMonth = currentMonth;
	}
	public Long getCurrentYear() {
		return currentYear;
	}
	public void setCurrentYear(Long currentYear) {
		this.currentYear = currentYear;
	}
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getMrr() {
		return mrr;
	}
	public void setMrr(Long mrr) {
		this.mrr = mrr;
	}
	public Long getClv() {
		return clv;
	}
	public void setClv(Long clv) {
		this.clv = clv;
	}
	public void plusCurrentYear(long value) {
		this.currentYear += value;
	}
}
