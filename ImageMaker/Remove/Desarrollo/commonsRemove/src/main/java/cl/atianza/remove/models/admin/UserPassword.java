package cl.atianza.remove.models.admin;

import cl.atianza.remove.dtos.commons.OwnerPasswordDto;
@Deprecated
public class UserPassword extends OwnerPasswordDto {
	private User user;

	public UserPassword() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
