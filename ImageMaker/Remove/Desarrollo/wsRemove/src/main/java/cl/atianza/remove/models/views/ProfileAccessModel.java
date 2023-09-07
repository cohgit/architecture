package cl.atianza.remove.models.views;

import java.util.HashMap;
import java.util.Map;

import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.utils.RestController;

public class ProfileAccessModel {
	private EProfiles profile;
	private Map<String, ServiceAccess> lstAccess = new HashMap<String, ServiceAccess>();

	public ProfileAccessModel() {
		super();
	}
	
	public EProfiles getProfile() {
		return profile;
	}

	public void setProfile(EProfiles profile) {
		this.profile = profile;
	}

	public Map<String, ServiceAccess> getLstAccess() {
		return lstAccess;
	}

	public void setLstAccess(Map<String, ServiceAccess> lstAccess) {
		this.lstAccess = lstAccess;
	}

	public void addAccess(ServiceAccess access) {
		this.lstAccess.put(access.getKey(), access);
	}
	public void addAccess(String key, boolean get, boolean post, boolean put, boolean delete, boolean list, boolean file) {
		this.addAccess(new ServiceAccess(key, get, post, put, delete, list, file));
	}
	public void addAccess(RestController controller, boolean get, boolean post, boolean put, boolean delete, boolean list, boolean file) {
		this.addAccess(new ServiceAccess(controller.getPath().getKey(), get, post, put, delete, list, file));
	}

	@Override
	public String toString() {
		return "ProfileAccessModel [profile=" + profile + ", lstAccess=" + lstAccess + "]";
	}
}