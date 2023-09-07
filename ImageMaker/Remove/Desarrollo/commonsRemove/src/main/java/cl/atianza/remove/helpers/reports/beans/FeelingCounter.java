package cl.atianza.remove.helpers.reports.beans;

import cl.atianza.remove.enums.EFeelings;

public class FeelingCounter {
	private long bad = 0;
	private long neutral = 0;
	private long good = 0;
	
	public FeelingCounter() {
		super();
	}
	public long getBad() {
		return bad;
	}
	public void setBad(long bad) {
		this.bad = bad;
	}
	public long getNeutral() {
		return neutral;
	}
	public void setNeutral(long neutral) {
		this.neutral = neutral;
	}
	public long getGood() {
		return good;
	}
	public void setGood(long good) {
		this.good = good;
	}
	
	public void incByFeeling(EFeelings feeling) {
		if (feeling.getTag().equals(EFeelings.BAD.getTag())) {
			bad++;
		} else if (feeling.getTag().equals(EFeelings.NEUTRAL.getTag())) {
			neutral++;
		} else if (feeling.getTag().equals(EFeelings.GOOD.getTag())) {
			good++;
		}
	}
	public void incByFeeling(String feeling) {
		if (feeling.equals(EFeelings.BAD.getTag())) {
			bad++;
		} else if (feeling.equals(EFeelings.NEUTRAL.getTag())) {
			neutral++;
		} else if (feeling.equals(EFeelings.GOOD.getTag())) {
			good++;
		}
	}
	public boolean notEmpty() {
		return bad > 0 || neutral > 0 || good > 0;
	}
	@Override
	public String toString() {
		return "FeelingCounter [bad=" + bad + ", neutral=" + neutral + ", good=" + good + "]";
	}
}
