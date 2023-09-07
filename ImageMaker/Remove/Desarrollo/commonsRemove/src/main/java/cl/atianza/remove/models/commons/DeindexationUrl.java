package cl.atianza.remove.models.commons;

import cl.atianza.remove.dtos.commons.DeindexationUrlDto;
@Deprecated
public class DeindexationUrl extends DeindexationUrlDto {
	private boolean markToDelete;

	public boolean isMarkToDelete() {
		return markToDelete;
	}

	public void setMarkToDelete(boolean markToDelete) {
		this.markToDelete = markToDelete;
	}
}
