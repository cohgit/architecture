package cl.atianza.remove.models.commons;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import cl.atianza.remove.dtos.commons.ScannerImpulseObservationDto;
import cl.atianza.remove.enums.EImpulseStatus;
import cl.atianza.remove.utils.RemoveDateUtils;

public class ScannerImpulseObservation extends ScannerImpulseObservationDto {
	private String owner_name;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = RemoveDateUtils.STRING_LOCALDATE_FORMAT_FOR_FRONTEND)
	private LocalDate estimatedDate;
	private Long idClientPlan;

	public ScannerImpulseObservation() {
		super();
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	
	public LocalDate getEstimatedDate() {
		return estimatedDate;
	}

	public void setEstimatedDate(LocalDate estimatedDate) {
		this.estimatedDate = estimatedDate;
	}

	public Long getIdClientPlan() {
		return idClientPlan;
	}

	public void setIdClientPlan(Long idClientPlan) {
		this.idClientPlan = idClientPlan;
	}

	public boolean esApproved() {
		return EImpulseStatus.APPROVED.getTag().equals(this.getStatus());
	}
	public boolean esRejected() {
		return EImpulseStatus.REJECTED.getTag().equals(this.getStatus());
	}
	public boolean esToBeApproved() {
		return EImpulseStatus.TO_BE_APPROVED.getTag().equals(this.getStatus());
	}

	@Override
	public String toString() {
		return "ScannerImpulseObservation [" + super.toString() + ", owner_name=" + owner_name + "]";
	}
}
