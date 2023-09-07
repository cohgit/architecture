package cl.atianza.remove.helpers.reports.beans;

public class TrackingWordBean implements Comparable<TrackingWordBean> {
	private String name;
	private String feelingCode;
	
	public TrackingWordBean() {
		super();
	}
	public TrackingWordBean(String name, String feelingCode) {
		super();
		this.name = name;
		this.feelingCode = feelingCode;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFeelingCode() {
		return feelingCode;
	}
	public void setFeelingCode(String feelingCode) {
		this.feelingCode = feelingCode;
	}
	@Override
	public String toString() {
		return "TrackingWordBean [name=" + name + ", feelingCode=" + feelingCode + "]";
	}
	
	@Override
	public int compareTo(TrackingWordBean o) {
		return o != null ? this.getName().compareTo(o.getName()) : 0;
	}
}
