package cl.atianza.remove.models.views;

public class ContactAttsMailView {
	private String key;
	private String value;
	public ContactAttsMailView() {
		super();
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "ContactAtts [key=" + key + ", value=" + value + "]";
	}
}
