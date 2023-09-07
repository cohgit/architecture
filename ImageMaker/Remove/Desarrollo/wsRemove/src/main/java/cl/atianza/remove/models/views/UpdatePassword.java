package cl.atianza.remove.models.views;

public class UpdatePassword {
	private String oldPassword;
	private String newPassword;
	public UpdatePassword() {
		super();
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@Override
	public String toString() {
		return "UpdatePassword [oldPassword=" + oldPassword + ", newPassword=" + newPassword + "]";
	}
}
