package cl.atianza.remove.models.commons;

import cl.atianza.remove.dtos.commons.DeindexationHistoricDto;
import cl.atianza.remove.enums.EDeindexationStatus;
@Deprecated
public class DeindexationHistoric extends DeindexationHistoricDto {
	private EDeindexationStatus statusDef;

	public EDeindexationStatus getStatusDef() {
		if (statusDef == null && this.getStatus() != null) {
			statusDef = EDeindexationStatus.obtain(this.getStatus());
		}
		
		return statusDef;
	}

	public void setStatusDef(EDeindexationStatus statusDef) {
		this.statusDef = statusDef;
	}
}
