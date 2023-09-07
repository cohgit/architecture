package cl.atianza.remove.models.commons;

import cl.atianza.remove.dtos.commons.DeindexationInputValueDto;
@Deprecated
public class DeindexationInputValue extends DeindexationInputValueDto {
	private DynamicFormInput input;

	public DynamicFormInput getInput() {
		return input;
	}

	public void setInput(DynamicFormInput input) {
		this.input = input;
	}
}
