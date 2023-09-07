package cl.atianza.remove.dtos.commons;

import java.time.LocalDate;

import cl.atianza.remove.dtos.AbstractDto;
@Deprecated
public class DeindexationInputValueDto extends AbstractDto {
	private Long id_deindexation;
	private Long id_form_input;
	private Long id_option;
	private String value;
	private Float value_numeric;
	private LocalDate value_date;
	private Boolean value_boolean;
	public DeindexationInputValueDto() {
		super();
	}
	public Long getId_deindexation() {
		return id_deindexation;
	}
	public void setId_deindexation(Long id_deindexation) {
		this.id_deindexation = id_deindexation;
	}
	public Long getId_form_input() {
		return id_form_input;
	}
	public void setId_form_input(Long id_form_input) {
		this.id_form_input = id_form_input;
	}
	public Long getId_option() {
		return id_option;
	}
	public void setId_option(Long id_option) {
		this.id_option = id_option;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Float getValue_numeric() {
		return value_numeric;
	}
	public void setValue_numeric(Float value_numeric) {
		this.value_numeric = value_numeric;
	}
	public LocalDate getValue_date() {
		return value_date;
	}
	public void setValue_date(LocalDate value_date) {
		this.value_date = value_date;
	}
	public Boolean getValue_boolean() {
		return value_boolean;
	}
	public void setValue_boolean(Boolean value_boolean) {
		this.value_boolean = value_boolean;
	}
	@Override
	public String toString() {
		return "DeindexationInputValue [id_deindexation=" + id_deindexation + ", id_form_input=" + id_form_input
				+ ", id_option=" + id_option + ", value=" + value + ", value_numeric=" + value_numeric + ", value_date="
				+ value_date + ", value_boolean=" + value_boolean + "]";
	}
}
