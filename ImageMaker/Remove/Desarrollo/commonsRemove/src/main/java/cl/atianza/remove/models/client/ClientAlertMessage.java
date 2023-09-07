package cl.atianza.remove.models.client;

import cl.atianza.remove.dtos.clients.ClientAlertMessageDto;
import cl.atianza.remove.enums.EClientAlertMessageTypes;

public class ClientAlertMessage extends ClientAlertMessageDto {
	private Object parameters;
	private EClientAlertMessageTypes typeObj;
	
	public ClientAlertMessage() {
		super();
	}
	public ClientAlertMessage(Long idOwner, Object parameters) {
		super();
		this.setId_owner(idOwner);
		this.parameters = parameters;
	}
	
	public Object getParameters() {
		return parameters;
	}
	public void setParameters(Object parameters) {
		this.parameters = parameters;
	}
	public EClientAlertMessageTypes getTypeObj() {
		return typeObj;
	}
	public void setTypeObj(EClientAlertMessageTypes typeObj) {
		this.typeObj = typeObj;
	}
}
