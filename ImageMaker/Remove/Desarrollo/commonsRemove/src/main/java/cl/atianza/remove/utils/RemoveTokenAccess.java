package cl.atianza.remove.utils;

import java.util.StringJoiner;
import java.util.UUID;

import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.exceptions.RemoveApplicationException;

/**
 * Clase que contiene los atributos necesarios para el manejo de token de seguridad del sistema.
 * @author Jose Gutierrez
 */
public class RemoveTokenAccess {
	protected static final String WEB = "web";
	protected static final String MOBILE = "mobile";
	protected static final String DELIMITER = "|";
	protected static final String DELIMITER_SPLITTER = "\\|";
	
	protected static final String ADMIN_KIND = "ADMIN";
	protected static final String CLIENT_KIND = "CLIENT";
	
	protected static final int INDEX_UUID_SESSION = 0;
	protected static final int INDEX_UUID_ACCOUNT = 1;
	protected static final int INDEX_PLATFORM = 2;
	protected static final int INDEX_TIME = 3;
	protected static final int INDEX_USER_AGENT = 4;
	protected static final int INDEX_PROFILE = 5;
	protected static final int INDEX_KIND = 6;
	
	private String uuidSession = UUID.randomUUID().toString();
	private String token;
	private String uuidAccount;
	private String plataform;
	private String time;
	private String userAgent;
	private String profile;
	private String kind;
	
	public RemoveTokenAccess() {
		super();
	}
	
	public static RemoveTokenAccess initAdmin(String uuidAccount, String time, String userAgent, String profile) throws RemoveApplicationException {
		RemoveTokenAccess t = new RemoveTokenAccess(uuidAccount, WEB, time, userAgent);
		t.setProfile(profile);
		t.setKind(ADMIN_KIND);
		t.setToken(t.concatKey());
		
		return t;
	}
	
	public static RemoveTokenAccess initClient(String uuidAccount, String time, String userAgent) throws RemoveApplicationException {
		RemoveTokenAccess t = new RemoveTokenAccess(uuidAccount, WEB, time, userAgent);
		t.setProfile(EProfiles.CLIENT.getCode());
		t.setKind(CLIENT_KIND);
		t.setToken(t.concatKey());
		
		return t;
	}
	
	public static RemoveTokenAccess init(String token) throws RemoveApplicationException {
		String tokenDecrypted = CipherUtils.decrypt(token);
		String[] params = tokenDecrypted.split(DELIMITER_SPLITTER);
		
		RemoveTokenAccess tokenAccess = new RemoveTokenAccess(params[INDEX_UUID_ACCOUNT], 
				params[INDEX_PLATFORM], params[INDEX_TIME], params[INDEX_USER_AGENT]);
		tokenAccess.setUuidSession(params[INDEX_UUID_SESSION]);
		tokenAccess.setProfile(params[INDEX_PROFILE]); // Client doesnt use it, set something random
		tokenAccess.setKind(params[INDEX_KIND]);
		tokenAccess.setToken(token);
		
		return tokenAccess;
	}
	
	public RemoveTokenAccess(String uuidAccount, String plataform, String time, String userAgent) {
		super();
		this.uuidAccount = uuidAccount;
		this.plataform = plataform;
		this.time = time;
		this.userAgent = userAgent;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUuidAccount() {
		return uuidAccount;
	}
	public void setUuidAccount(String uuidAccount) {
		this.uuidAccount = uuidAccount;
	}
	public String getPlataform() {
		return plataform;
	}
	public void setPlataform(String plataform) {
		this.plataform = plataform;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUuidSession() {
		return uuidSession;
	}

	public void setUuidSession(String uuidSession) {
		this.uuidSession = uuidSession;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public boolean isAdmin() {
		return this.kind.equals(ADMIN_KIND);
	}
	public boolean isClient() {
		return this.kind.equals(CLIENT_KIND);
	}

	public String concatKey() throws RemoveApplicationException {
		String tokenValue = new StringJoiner(DELIMITER)
				.add(this.getUuidSession())					//INDEX_UUID_SESSION
				.add(this.getUuidAccount().toString())		//INDEX_UUID_ACCOUNT
				.add(this.getPlataform())					//INDEX_PLATFORM
				.add(String.valueOf(this.getTime()))		//INDEX_TIME
				.add(this.getUserAgent())					//INDEX_USER_AGENT
				.add(this.getProfile())						//INDEX_PROFILE
				.add(this.getKind())						//INDEX_KIND
				.toString();
		
		return CipherUtils.encrypt(tokenValue);
	}
	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public boolean checkMobile() {
		return this.getPlataform().equals(MOBILE);
	}
	public boolean checkWeb() {
		return this.getPlataform().equals(WEB);
	}

	@Override
	public String toString() {
		return "RemoveTokenAccess [uuidSession=" + uuidSession + ", token=" + token + ", uuidAccount=" + uuidAccount
				+ ", plataform=" + plataform + ", time=" + time + ", userAgent=" + userAgent + ", profile=" + profile
				+ ", kind=" + kind + "]";
	}
}
