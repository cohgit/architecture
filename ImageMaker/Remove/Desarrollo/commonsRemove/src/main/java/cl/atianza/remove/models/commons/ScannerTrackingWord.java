package cl.atianza.remove.models.commons;

import cl.atianza.remove.dtos.commons.ScannerTrackingWordDto;
import cl.atianza.remove.enums.EFeelings;

public class ScannerTrackingWord extends ScannerTrackingWordDto {
	private EFeelings feelingObj;
	private boolean markToDelete;

	public ScannerTrackingWord() {
		super();
	}
	
	public EFeelings getFeelingObj() {
		if (this.getFeeling() != null) {
			this.feelingObj = EFeelings.find(this.getFeeling());
		}
		
		return feelingObj;
	}

	public void setFeelingObj(EFeelings feelingObj) {
		this.feelingObj = feelingObj;
	}
	
	public ScannerTrackingWord(Long id, Long id_scanner, String word, String type, String feeling) {
		super(id, id_scanner, word, type, feeling);
	}
	
	public boolean esWord() {
		return this.getType().equalsIgnoreCase("WORD");
	}
	public boolean esURL() {
		return this.getType().equalsIgnoreCase("URL");
	}

	public boolean isMarkToDelete() {
		return markToDelete;
	}

	public void setMarkToDelete(boolean markToDelete) {
		this.markToDelete = markToDelete;
	}

	@Override
	public String toString() {
		return "ScannerTrackingWord [" + super.toString() + "]";
	}
}
