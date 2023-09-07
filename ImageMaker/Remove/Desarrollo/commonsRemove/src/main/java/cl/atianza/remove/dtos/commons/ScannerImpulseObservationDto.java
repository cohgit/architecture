package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;

public class ScannerImpulseObservationDto extends AbstractDto {
	private Long id_scanner_impulse_content;
	private String status;
	private LocalDateTime creation_date;
	private String commentary;
	private Long id_owner;
	private String owner_profile;
	
	public ScannerImpulseObservationDto() {
		super();
	}

	public Long getId_scanner_impulse_content() {
		return id_scanner_impulse_content;
	}

	public void setId_scanner_impulse_content(Long id_scanner_impulse_content) {
		this.id_scanner_impulse_content = id_scanner_impulse_content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public Long getId_owner() {
		return id_owner;
	}

	public void setId_owner(Long id_owner) {
		this.id_owner = id_owner;
	}

	public String getOwner_profile() {
		return owner_profile;
	}

	public void setOwner_profile(String owner_profile) {
		this.owner_profile = owner_profile;
	}

	@Override
	public String toString() {
		return "ScannerImpulseObservationDto [id_scanner_impulse_content=" + id_scanner_impulse_content + ", status=" + status
				+ ", creation_date=" + creation_date + ", commentary=" + commentary + ", id_owner=" + id_owner + ", owner_profile="
				+ owner_profile + "]";
	}
}
