package cl.atianza.remove.helpers.reports.beans;

public class TableRemoveImpulseRow extends TableRow {
	private boolean targetReached;
	public TableRemoveImpulseRow() {
		super();
	}
	public boolean isTargetReached() {
		return targetReached;
	}
	public void setTargetReached(boolean targetReached) {
		this.targetReached = targetReached;
	}
}
