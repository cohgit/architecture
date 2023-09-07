package cl.atianza.remove.models.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.atianza.remove.models.client.ClientPlan;

/**
 * Class for client access info at login.
 * @author pilin
 *
 */
@Deprecated
public class ClientAccess {
	private String uuid;
	private String name;
	private String project;
	private String token;
	private boolean first_time = true;
	private boolean readOnly;
	private String message;
	private List<ClientPlan> plans;
	private ProfileAccessModel permissions;
	
	private Map<String, String> config;
	
	public ClientAccess() {
		super();
	}
	
	public static ClientAccess init() {
		return new ClientAccess();
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

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
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
	
	public List<ClientPlan> getPlan() {
		return plans;
	}

	public void setPlan(List<ClientPlan> clientPlan) {
		this.plans = clientPlan;
	}

	public ProfileAccessModel getPermissions() {
		return permissions;
	}

	public void setPermissions(ProfileAccessModel permissions) {
		this.permissions = permissions;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ClientAccess [name=" + name + ", project=" + project + ", token=" + token + ", first_time=" + first_time
				+ ", plans=" + plans + ", permissions=" + permissions + ", config=" + config + "]";
	}
}