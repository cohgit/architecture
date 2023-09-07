package cl.atianza.remove.models.admin;

import cl.atianza.remove.dtos.admin.UserAlertMessageDto;
import cl.atianza.remove.enums.EUserAlertMessageTypes;

public class UserAlertMessage extends UserAlertMessageDto {
	private Object parameters;
	private EUserAlertMessageTypes typeObj;
	
	public UserAlertMessage() {
		super();
	}
	
	public UserAlertMessage(Long idOwner, Object parameters) {
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
	public EUserAlertMessageTypes getTypeObj() {
		return typeObj;
	}
	public void setTypeObj(EUserAlertMessageTypes typeObj) {
		this.typeObj = typeObj;
	}
}
