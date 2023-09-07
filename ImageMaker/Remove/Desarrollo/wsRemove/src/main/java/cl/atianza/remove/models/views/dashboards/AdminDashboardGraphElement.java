package cl.atianza.remove.models.views.dashboards;
@Deprecated
public class AdminDashboardGraphElement {
	private String name;
	private long value = 0;
	
	public AdminDashboardGraphElement() {
		super();
	}
	public AdminDashboardGraphElement(String name, long value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "AdminDashboardPieElement [name=" + name + ", value=" + value + "]";
	}
}
