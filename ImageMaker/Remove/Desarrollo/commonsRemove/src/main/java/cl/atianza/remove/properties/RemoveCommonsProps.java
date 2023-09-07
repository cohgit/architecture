package cl.atianza.remove.properties;

/**
 * 
 * @author pilin
 *
 */
public class RemoveCommonsProps {
	private boolean emails_active;
	private boolean serp_active;
	private String temp_files_path;
	
	private RemoveDBConnProps connection;
	
	public RemoveCommonsProps() {
		super();
	}

	public RemoveDBConnProps getConnection() {
		return connection;
	}

	public void setConnection(RemoveDBConnProps connection) {
		this.connection = connection;
	}

	public boolean isEmails_active() {
		return emails_active;
	}

	public void setEmails_active(boolean emails_active) {
		this.emails_active = emails_active;
	}

	public boolean isSerp_active() {
		return serp_active;
	}

	public void setSerp_active(boolean serp_active) {
		this.serp_active = serp_active;
	}

	public String getTemp_files_path() {
		return temp_files_path;
	}

	public void setTemp_files_path(String temp_files_path) {
		this.temp_files_path = temp_files_path;
	}

	@Override
	public String toString() {
		return "RemoveProps [emails_active=" + emails_active + ", serp_active=" + serp_active + ", connection="
				+ connection + "]";
	}
}
