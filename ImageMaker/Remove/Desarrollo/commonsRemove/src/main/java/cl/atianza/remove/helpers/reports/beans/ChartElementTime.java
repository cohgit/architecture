package cl.atianza.remove.helpers.reports.beans;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ChartElementTime {
	private String label;
	private String serie;
	private Date category;
	private long value = 0;

	public ChartElementTime() {
		super();
	}

	public ChartElementTime(String label, String serie, LocalDate category, long value) {
		super();
		this.label = label;
		this.serie = serie;
		this.category = Date.from(category.atStartOfDay(ZoneId.systemDefault()).toInstant());
		this.value = value;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Date getCategory() {
		return category;
	}

	public void setCategory(Date category) {
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
		return "ChartStackedBarElement [serie=" + serie + ", category=" + category + ", value=" + value + "]";
	}
}
