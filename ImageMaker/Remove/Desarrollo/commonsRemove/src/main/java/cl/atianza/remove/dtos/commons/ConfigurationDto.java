package cl.atianza.remove.dtos.commons;

public class ConfigurationDto {
	private String key;
	private String value;
	
	public ConfigurationDto() {
		super();
	}
	public ConfigurationDto(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public ConfigurationDto(String key) {
		super();
		this.key = key;
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
		return "ConfiguracionDto [key=" + key + ", value=" + value + "]";
	}
}
