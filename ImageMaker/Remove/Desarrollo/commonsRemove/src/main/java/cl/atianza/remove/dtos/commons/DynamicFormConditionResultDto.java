package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class DynamicFormConditionResultDto extends AbstractDto {
	private Long id_form;
	private String type;
	private Integer position;
	private String condition;
	private boolean active;
	
	public DynamicFormConditionResultDto() {
		super();
	}

	
	public DynamicFormConditionResultDto(String type, Integer position, String condition, boolean active) {
		super();
		this.type = type;
		this.position = position;
		this.condition = condition;
		this.active = active;
	}

	public Long getId_form() {
		return id_form;
	}

	public void setId_form(Long id_form) {
		this.id_form = id_form;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer order) {
		this.position = order;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "DynamicFormConditionResultDto [id_form=" + id_form + ", type=" + type + ", position=" + position
				+ ", condition=" + condition + ", active=" + active + "]";
	}
}
