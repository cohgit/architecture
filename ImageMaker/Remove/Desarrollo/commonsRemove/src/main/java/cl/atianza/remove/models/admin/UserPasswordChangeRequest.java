package cl.atianza.remove.models.admin;

import cl.atianza.remove.dtos.admin.UserPasswordChangeRequestDto;
import cl.atianza.remove.utils.RemoveDateUtils;

public class UserPasswordChangeRequest extends UserPasswordChangeRequestDto {

	public boolean expired() {
		return this.getExpiration_date() != null && this.getExpiration_date().isBefore(RemoveDateUtils.nowLocalDateTime());
	}
}
