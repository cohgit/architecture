package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class DynamicFormInputOptionDto extends AbstractDto {
	private Long id_input;
	private String name;
	private boolean active;
	
	public DynamicFormInputOptionDto() {
		super();
	}

	public DynamicFormInputOptionDto(String name, boolean active) {
		super();
		this.name = name;
		this.active = active;
	}

	public Long getId_input() {
		return id_input;
	}

	public void setId_input(Long id_input) {
		this.id_input = id_input;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "DynamicFormInputOptionDto [id_input=" + id_input + ", name=" + name + ", active=" + active + "]";
	}
}
