package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class DynamicFormSectionDto extends AbstractDto {
	private Long id_form;
	private Integer position;
	private boolean visible;
	private String visible_condition;
	private boolean active;
	
	public DynamicFormSectionDto() {
		super();
	}

	public Long getId_form() {
		return id_form;
	}

	public void setId_form(Long id_form) {
		this.id_form = id_form;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer order) {
		this.position = order;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "DynamicFormSectionDto [id_form=" + id_form + ", position=" + position + ", visible=" + visible
				+ ", visible_condition=" + visible_condition + ", active=" + active + "]";
	}
}
