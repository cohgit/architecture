package cl.atianza.remove.dtos.commons;

import java.time.LocalDate;
import java.time.LocalDateTime;
import cl.atianza.remove.dtos.AbstractDto;

public class ScannerImpulseDto extends AbstractDto {
	private Long id_creator;
	private String creator_profile;
	private Long id_scanner;
	private Long id_keyword;
	private String type;
	private String reference_link;
	private String comments;
	private String status;
	private LocalDate estimated_publish;
	private String real_publish_link;
	private LocalDateTime real_publish_date;
	private LocalDateTime creation_date;
	private Integer initial_position;
	private boolean target_reached;
	
	public ScannerImpulseDto() {
		super();
	}

	public Long getId_creator() {
		return id_creator;
	}

	public void setId_creator(Long id_creator) {
		this.id_creator = id_creator;
	}

	public String getCreator_profile() {
		return creator_profile;
	}

	public void setCreator_profile(String creator_profile) {
		this.creator_profile = creator_profile;
	}

	public Long getId_scanner() {
		return id_scanner;
	}

	public void setId_scanner(Long id_scanner) {
		this.id_scanner = id_scanner;
	}

	public Long getId_keyword() {
		return id_keyword;
	}

	public void setId_keyword(Long id_keyword) {
		this.id_keyword = id_keyword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReference_link() {
		return reference_link;
	}

	public void setReference_link(String reference_link) {
		this.reference_link = reference_link;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getEstimated_publish() {
		return estimated_publish;
	}

	public void setEstimated_publish(LocalDate estimated_publish) {
		this.estimated_publish = estimated_publish;
	}

	public String getReal_publish_link() {
		return real_publish_link;
	}

	public void setReal_publish_link(String real_publish_link) {
		this.real_publish_link = real_publish_link;
	}

	public LocalDateTime getReal_publish_date() {
		return real_publish_date;
	}

	public void setReal_publish_date(LocalDateTime real_publish_date) {
		this.real_publish_date = real_publish_date;
	}

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	public Integer getInitial_position() {
		return initial_position;
	}

	public void setInitial_position(Integer initial_position) {
		this.initial_position = initial_position;
	}

	public boolean isTarget_reached() {
		return target_reached;
	}

	public void setTarget_reached(boolean target_reached) {
		this.target_reached = target_reached;
	}

	@Override
	public String toString() {
		return "ScannerImpulseDto [id_creator=" + id_creator + ", creator_profile=" + creator_profile + ", id_scanner="
				+ id_scanner + ", id_keyword=" + id_keyword + ", type=" + type + ", reference_link=" + reference_link
				+ ", comments=" + comments + ", status=" + status + ", estimated_publish=" + estimated_publish
				+ ", real_publish_link=" + real_publish_link + ", real_publish_date=" + real_publish_date
				+ ", creation_date=" + creation_date + "]";
	}
}
