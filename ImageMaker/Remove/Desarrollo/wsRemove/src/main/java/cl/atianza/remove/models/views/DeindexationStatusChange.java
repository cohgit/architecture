package cl.atianza.remove.models.views;

@Deprecated
public class DeindexationStatusChange {
	private Long idDeindexation;
	private String status;
	private String comment;
	private String trackingCode;
	
	public DeindexationStatusChange() {
		super();
	}
	
	public Long getIdDeindexation() {
		return idDeindexation;
	}
	public void setIdDeindexation(Long idDeindexation) {
		this.idDeindexation = idDeindexation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getTrackingCode() {
		return trackingCode;
	}

	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}

	@Override
	public String toString() {
		return "DeindexationStatusChange [idDeindexation=" + idDeindexation + ", status=" + status + ", comment="
				+ comment + ", trackingCode=" + trackingCode + "]";
	}
}
