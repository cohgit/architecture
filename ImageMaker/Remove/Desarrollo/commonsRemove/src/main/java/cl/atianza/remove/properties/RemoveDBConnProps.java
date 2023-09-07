package cl.atianza.remove.properties;

/**
 * 
 * @author pilin
 *
 */
public class RemoveDBConnProps {
	private String jndiDsName;
	private String hostWO;
	private String hostRO;
	private int port;
	private String params;
	private String dbname;
	private String user;
	private String password;
	private int tryTimes;
	private int waitTime;
	private String schemaName;
	private String init_script_path;
	private String update_folder;
	private String script_token;
	
	public RemoveDBConnProps() {
		super();
	}
	public String getJndiDsName() {
		return jndiDsName;
	}
	public void setJndiDsName(String jndiDsName) {
		this.jndiDsName = jndiDsName;
	}
	public String getHostWO() {
		return hostWO;
	}
	public String getHostRO() {
		return hostRO;
	}
	public void setHostRO(String hostRO) {
		this.hostRO = hostRO;
	}
	public void setHostWO(String hostWO) {
		this.hostWO = hostWO;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getTryTimes() {
		return tryTimes;
	}
	public void setTryTimes(int tryTimes) {
		this.tryTimes = tryTimes;
	}
	public int getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getInit_script_path() {
		return init_script_path;
	}
	public void setInit_script_path(String init_script_path) {
		this.init_script_path = init_script_path;
	}
	public String getUpdate_folder() {
		return update_folder;
	}
	public void setUpdate_folder(String update_folder) {
		this.update_folder = update_folder;
	}
	public String getScript_token() {
		return script_token;
	}
	public void setScript_token(String script_token) {
		this.script_token = script_token;
	}
	@Override
	public String toString() {
		return "RemoveDBConnProps [jndiDsName=" + jndiDsName + ", hostWO=" + hostWO + ", port=" + port + ", params="
				+ params + ", dbname=" + dbname + ", user=" + user + ", password=" + password + ", tryTimes=" + tryTimes
				+ ", waitTime=" + waitTime + ", schemaName=" + schemaName + ", init_script_path=" + init_script_path
				+ ", update_folder=" + update_folder + ", script_token=" + script_token + "]";
	}
}
