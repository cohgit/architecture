package cl.atianza.remove.models.admin;

import java.util.HashMap;
import java.util.Map;

import cl.atianza.remove.dtos.admin.UserAuditAccessDto;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.utils.RemoveJsonUtil;

public class UserAuditAccess extends UserAuditAccessDto {
	private Map<String, Object> paramsObj;

	public UserAuditAccess() {
		super();
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
	
	public void parseParams() {
		if (this.paramsObj != null) {
			try {
				this.setParameters(RemoveJsonUtil.dataToJson(this.paramsObj));
			} catch (RemoveApplicationException e) {}
		}
	}
}
