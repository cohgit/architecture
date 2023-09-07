package cl.atianza.remove.helpers.reports.beans;

public class ChartElement implements Comparable<ChartElement>{
	private String label;
	private String serie;
	private String category;
	private long value = 0;

	public ChartElement() {
		super();
	}

	public ChartElement(String label, String serie, String category, long value) {
		super();
		this.label = label;
		this.serie = serie;
		this.category = category;
		this.value = value;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "ChartElement [label=" + label + ", serie=" + serie + ", category=" + category + ", value=" + value
				+ "]";
	}

	@Override
	public int compareTo(ChartElement o) {
		// TODO Auto-generated method stub
		return o != null ? this.getSerie().compareTo(o.getSerie()) : 0;
	}
}
