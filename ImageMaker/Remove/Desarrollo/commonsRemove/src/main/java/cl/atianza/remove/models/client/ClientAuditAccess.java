package cl.atianza.remove.models.client;

import java.util.HashMap;
import java.util.Map;

import cl.atianza.remove.dtos.clients.ClientAuditAccessDto;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.utils.RemoveJsonUtil;

public class ClientAuditAccess extends ClientAuditAccessDto {
	private String nameUserAction;
	private Map<String, Object> paramsObj;
	private EProfiles profileUserActionType;
	
	public ClientAuditAccess() {
		super();
	}
	public String getNameUserAction() {
		return nameUserAction;
	}
	public void setNameUserAction(String nameUserAction) {
		this.nameUserAction = nameUserAction;
	}
	public EProfiles getProfileUserActionType() {
		return profileUserActionType;
	}
	public void setProfileUserActionType(EProfiles profileUserActionType) {
		this.profileUserActionType = profileUserActionType;
	}
	public Map<String, Object> getParamsObj() {
		if (paramsObj == null && getParameters() != null) {
			try {
				paramsObj = RemoveJsonUtil.jsonToMap(this.getParameters());
			} catch (RemoveApplicationException e) {}
		}
		
		if (paramsObj == null) {
			paramsObj = new HashMap<String, Object>();
		}
		
		return paramsObj;
	}
	public void setParamsObj(Map<String, Object> paramsObj) {
		this.paramsObj = paramsObj;
	}
	@Override
	public String toString() {
		return "ClientAuditAccess [nameUserAction=" + nameUserAction + ", profileUserActionType="
				+ profileUserActionType + "]";
	}
	public void parseParams() {
		if (this.paramsObj != null) {
			try {
				this.setParameters(RemoveJsonUtil.dataToJson(this.paramsObj));
			} catch (RemoveApplicationException e) {}
		}
	}
}
