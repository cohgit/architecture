package cl.atianza.remove.models.views;

public class NewPassword {
	private String newPassword;
	private String uuidRequest;
	public NewPassword() {
		super();
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getUuidRequest() {
		return uuidRequest;
	}
	public void setUuidRequest(String uuidRequest) {
		this.uuidRequest = uuidRequest;
	}
	@Override
	public String toString() {
		return "NewPassword [newPassword=" + newPassword + ", uuidRequest=" + uuidRequest + "]";
	}
}
