package cl.atianza.remove.models.client;

import cl.atianza.remove.dtos.commons.OwnerPasswordDto;
@Deprecated
public class ClientPassword extends OwnerPasswordDto {
	private Client client;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
