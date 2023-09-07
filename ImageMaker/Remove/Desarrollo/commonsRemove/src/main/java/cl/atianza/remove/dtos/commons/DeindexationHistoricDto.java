package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;
@Deprecated
public class DeindexationHistoricDto extends AbstractDto {
	private Long id_deindexation;
	
	private Long id_owner;
	private String profile;
	private String status;
	private String comments;
	private LocalDateTime creation_date;
	
	public DeindexationHistoricDto() {
		super();
	}

	public Long getId_deindexation() {
		return id_deindexation;
	}

	public void setId_deindexation(Long id_deindexation) {
		this.id_deindexation = id_deindexation;
	}

	public Long getId_owner() {
		return id_owner;
	}

	public void setId_owner(Long id_owner) {
		this.id_owner = id_owner;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	@Override
	public String toString() {
		return "DeindexationHistoricDto [id_deindexation=" + id_deindexation + ", id_owner=" + id_owner + ", profile="
				+ profile + ", status=" + status + ", comments=" + comments + ", creation_date=" + creation_date + "]";
	}

}
