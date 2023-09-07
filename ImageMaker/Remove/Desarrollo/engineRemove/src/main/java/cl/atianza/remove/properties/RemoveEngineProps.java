package cl.atianza.remove.properties;

/**
 * 
 * @author pilin
 *
 */
public class RemoveEngineProps {
	private int port;
	private String restPath;
	
	public RemoveEngineProps() {
		super();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRestPath() {
		return restPath;
	}

	public void setRestPath(String restPath) {
		this.restPath = restPath;
	}

	@Override
	public String toString() {
		return "RemoveEngineProps [port=" + port + ", restPath=" + restPath + "]";
	}
}
