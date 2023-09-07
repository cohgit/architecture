package cl.atianza.remove.models.views.dashboards;
@Deprecated
public class AdminDashboardSerps {
	private Long total;
	private Long consumed;
	private Long remaining;
	private String renovationDate;
	
	public AdminDashboardSerps() {
		super();
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Long getConsumed() {
		return consumed;
	}
	public void setConsumed(Long consumed) {
		this.consumed = consumed;
	}
	public Long getValue() {
		return (long) (this.consumed.floatValue() * 100 / this.total.floatValue());
	}
	public void setValue(Long value) {}
	public Long getRemaining() {
		return remaining;
	}
	public void setRemaining(Long remaining) {
		this.remaining = remaining;
	}
	public String getRenovationDate() {
		return renovationDate;
	}
	public void setRenovationDate(String renovationDate) {
		this.renovationDate = renovationDate;
	}
}
