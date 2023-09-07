package cl.atianza.remove.models.client;

import cl.atianza.remove.dtos.clients.ClientPasswordChangeRequestDto;
import cl.atianza.remove.utils.RemoveDateUtils;

public class ClientPasswordChangeRequest extends ClientPasswordChangeRequestDto {

	public boolean expired() {
		return this.getExpiration_date() != null && this.getExpiration_date().isBefore(RemoveDateUtils.nowLocalDateTime());
	}

}
