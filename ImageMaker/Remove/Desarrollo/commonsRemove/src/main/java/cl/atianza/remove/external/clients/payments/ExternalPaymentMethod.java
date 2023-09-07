package cl.atianza.remove.external.clients.payments;
@Deprecated
public class ExternalPaymentMethod {
	private String platform;
	private String key;
	private String brand;
	private Long exp_month;
	private Long exp_year;
	private String last4;
	private String type;
	
	public ExternalPaymentMethod() {
		super();
	}
	public ExternalPaymentMethod(String type, String key, String brand, Long exp_month, Long exp_year, String last4, String platform) {
		super();
		this.type = type;
		this.key = key;
		this.brand = brand;
		this.exp_month = exp_month;
		this.exp_year = exp_year;
		this.last4 = last4;
		this.platform = platform;
	}

	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Long getExp_month() {
		return exp_month;
	}
	public void setExp_month(Long exp_month) {
		this.exp_month = exp_month;
	}
	public Long getExp_year() {
		return exp_year;
	}
	public void setExp_year(Long exp_year) {
		this.exp_year = exp_year;
	}
	public String getLast4() {
		return last4;
	}
	public void setLast4(String last4) {
		this.last4 = last4;
	} 
}
