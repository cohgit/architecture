package cl.atianza.remove.properties;

/**
 * 
 * @author pilin
 *
 */
public class RemoveWsProps {
	private int port;
	private String restPath;
	private int loginRetries;
	private int defaultTokenDuration;
	
	private boolean useSecure;
	private String secureKey;
	private String securePassword;
	
	public RemoveWsProps() {
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

	public int getLoginRetries() {
		return loginRetries;
	}

	public void setLoginRetries(int loginRetries) {
		this.loginRetries = loginRetries;
	}

	public int getDefaultTokenDuration() {
		return defaultTokenDuration;
	}

	public void setDefaultTokenDuration(int defaultTokenDuration) {
		this.defaultTokenDuration = defaultTokenDuration;
	}

	public boolean isUseSecure() {
		return useSecure;
	}

	public void setUseSecure(boolean useSecure) {
		this.useSecure = useSecure;
	}

	public String getSecureKey() {
		return secureKey;
	}

	public void setSecureKey(String secureKey) {
		this.secureKey = secureKey;
	}

	public String getSecurePassword() {
		return securePassword;
	}

	public void setSecurePassword(String securePassword) {
		this.securePassword = securePassword;
	}

	@Override
	public String toString() {
		return "RemoveWsProps [port=" + port + ", restPath=" + restPath + ", loginRetries=" + loginRetries
				+ ", defaultTokenDuration=" + defaultTokenDuration + "]";
	}
}
