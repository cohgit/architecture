package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class DynamicFormInputDto extends AbstractDto {
	private Long id_section;
	private String name;
	private String type;
	private Integer width;
	private boolean required;
	private String required_condition;
	private boolean visible;
	private String visible_condition;
	private boolean enabled;
	private String enabled_condition;
	private String value;
	private String value_condition;
	private boolean active;
	private Integer position;
	
	public DynamicFormInputDto() {
		super();
	}

	public DynamicFormInputDto(String name, String type, Integer width, boolean required,
			String required_condition, boolean visible, String visible_condition, boolean enabled,
			String enabled_condition, String value, String value_condition, boolean active, Integer position) {
		super();
		this.name = name;
		this.type = type;
		this.width = width;
		this.required = required;
		this.required_condition = required_condition;
		this.visible = visible;
		this.visible_condition = visible_condition;
		this.enabled = enabled;
		this.enabled_condition = enabled_condition;
		this.value = value;
		this.value_condition = value_condition;
		this.active = active;
		this.position = position;
	}

	public Long getId_section() {
		return id_section;
	}

	public void setId_section(Long id_section) {
		this.id_section = id_section;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getRequired_condition() {
		return required_condition;
	}

	public void setRequired_condition(String required_condition) {
		this.required_condition = required_condition;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getVisible_condition() {
		return visible_condition;
	}

	public void setVisible_condition(String visible_condition) {
		this.visible_condition = visible_condition;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEnabled_condition() {
		return enabled_condition;
	}

	public void setEnabled_condition(String enabled_condition) {
		this.enabled_condition = enabled_condition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue_condition() {
		return value_condition;
	}

	public void setValue_condition(String value_condition) {
		this.value_condition = value_condition;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "DynamicFormInputDto [id_section=" + id_section + ", name=" + name + ", type=" + type + ", width="
				+ width + ", required=" + required + ", required_condition=" + required_condition + ", visible="
				+ visible + ", visible_condition=" + visible_condition + ", enabled=" + enabled + ", enabled_condition="
				+ enabled_condition + ", value=" + value + ", value_condition=" + value_condition + ", active=" + active
				+ ", position=" + position + "]";
	}
}
