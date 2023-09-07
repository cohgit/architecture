package cl.atianza.remove.models.commons;

import cl.atianza.remove.dtos.commons.TrackingWordDto;
import cl.atianza.remove.enums.EFeelings;

public class TrackingWord extends TrackingWordDto {
	private EFeelings feelingObj;
	private long totalPositive;
	private long totalNegative;
	private long totalNeutral;

	public TrackingWord() {
		super();
	}

	public TrackingWord(String word, String feeling, Boolean active) {
		super(word, feeling, active);
	}

	public EFeelings getFeelingObj() {
		if (this.feelingObj == null && this.getFeeling() != null) {
			this.feelingObj = EFeelings.find(this.getFeeling());
		}
		
		return feelingObj;
	}

	public void setFeelingObj(EFeelings feelingObj) {
		this.feelingObj = feelingObj;
	}

	public long getTotalAsigned() {
		return this.totalPositive + this.totalNegative + this.totalNeutral;
	}

	public void setTotalAsigned(long totalAsigned) {}

	public long getTotalPositive() {
		return totalPositive;
	}

	public void setTotalPositive(long totalPositive) {
		this.totalPositive = totalPositive;
	}

	public long getTotalNegative() {
		return totalNegative;
	}

	public void setTotalNegative(long totalNegative) {
		this.totalNegative = totalNegative;
	}

	public long getTotalNeutral() {
		return totalNeutral;
	}

	public void setTotalNeutral(long totalNeutral) {
		this.totalNeutral = totalNeutral;
	}

	@Override
	public String toString() {
		return "TrackingWord [feelingObj=" + feelingObj + ", totalPositive="
				+ totalPositive + ", totalNegative=" + totalNegative + ", totalNeutral=" + totalNeutral + "]";
	}
}
