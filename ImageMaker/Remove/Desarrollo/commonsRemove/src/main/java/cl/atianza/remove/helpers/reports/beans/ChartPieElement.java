package cl.atianza.remove.helpers.reports.beans;

public class ChartPieElement implements Comparable<ChartPieElement>{
	private String label;
    private Long value;
    private String key;
    
	public ChartPieElement() {
		super();
	}
	public ChartPieElement(String key, String label, Long value) {
		super();
		this.key = key;
		this.label = label;
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	@Override
	public int compareTo(ChartPieElement o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
