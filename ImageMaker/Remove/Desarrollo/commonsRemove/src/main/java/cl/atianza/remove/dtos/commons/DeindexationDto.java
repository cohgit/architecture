package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;
import java.util.UUID;

import cl.atianza.remove.dtos.AbstractDto;
@Deprecated
public class DeindexationDto extends AbstractDto {
	private Long id_owner;
	private Long id_client_plan;
	private Long id_form;
	private String uuid = UUID.randomUUID().toString();
	
	private String person_name;
	private String person_lastname;
	private String person_institution;
	private String person_charge;
	
	private String url_to_deindex_keywords;
	
	private String comments;
	private LocalDateTime creation_date;
	private String status;
	private String tracking_code;
	
	public DeindexationDto() {
		super();
	}
	
	public Long getId_owner() {
		return id_owner;
	}
	public void setId_owner(Long id_owner) {
		this.id_owner = id_owner;
	}
	
	public Long getId_client_plan() {
		return id_client_plan;
	}

	public void setId_client_plan(Long id_client_plan) {
		this.id_client_plan = id_client_plan;
	}

	public Long getId_form() {
		return id_form;
	}

	public void setId_form(Long id_form) {
		this.id_form = id_form;
	}

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPerson_name() {
		return person_name;
	}
	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}
	public String getPerson_lastname() {
		return person_lastname;
	}
	public void setPerson_lastname(String person_lastname) {
		this.person_lastname = person_lastname;
	}
	public String getPerson_institution() {
		return person_institution;
	}
	public void setPerson_institution(String person_institution) {
		this.person_institution = person_institution;
	}
	public String getPerson_charge() {
		return person_charge;
	}
	public void setPerson_charge(String person_charge) {
		this.person_charge = person_charge;
	}

	public String getUrl_to_deindex_keywords() {
		return url_to_deindex_keywords;
	}
	public void setUrl_to_deindex_keywords(String url_to_deindex_keywords) {
		this.url_to_deindex_keywords = url_to_deindex_keywords;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getTracking_code() {
		return tracking_code;
	}

	public void setTracking_code(String tracking_code) {
		this.tracking_code = tracking_code;
	}

	@Override
	public String toString() {
		return "DeindexationDto [id_owner=" + id_owner + ", id_form=" + id_form + 
				", person_institution=" + person_institution + ", person_charge=" + person_charge
				+ ", url_to_deindex_keywords=" + url_to_deindex_keywords + 
				", comments=" + comments + ", creation_date=" + creation_date + ", status=" + status
				+ ", tracking_code=" + tracking_code + "]";
	}
}
