package cl.atianza.remove.models.views;

import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.utils.CipherUtils;

/**
 * Model for system login.
 * @author pilin
 *
 */
public class RemoveLogin {
	private String login;
	private String password;
	
	public RemoveLogin() {
		super();
	}
	public RemoveLogin(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String encriptPassword() throws RemoveApplicationException {
		return CipherUtils.encrypt(this.getPassword());
	}
	@Override
	public String toString() {
		return "AbstractLogin [login=" + login + ", password=" + password + "]";
	}
}
