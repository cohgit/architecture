package cl.atianza.remove.models.views;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for user access info at login.
 * @author pilin
 *
 */
public class UserAccess {
	private String uuid;
	private String name;
	private String token;
	private ProfileAccessModel permissions;
	private boolean first_time = true;
	
	private Map<String, String> config;
	
	public UserAccess() {
		super();
	}
	
	public static UserAccess init() {
		return new UserAccess();
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public boolean isFirst_time() {
		return first_time;
	}

	public void setFirst_time(boolean first_time) {
		this.first_time = first_time;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Map<String, String> getConfig() {
		if (this.config == null) {
			this.config = new HashMap<String, String>();
		}
		
		return config;
	}
	public void setConfig(Map<String, String> config) {
		this.config = config;
	}
	public void addConfig(String key, String value) {
		this.getConfig().put(key, value);
	}
	
	public ProfileAccessModel getPermissions() {
		return permissions;
	}

	public void setPermissions(ProfileAccessModel profile) {
		this.permissions = profile;
	}
	
	@Override
	public String toString() {
		return "UserAccess [name=" + name + ", token=" + token + ", profile=" + permissions
				+ ", config=" + config + "]";
	}
}