package cl.atianza.remove.models.commons;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;
import cl.atianza.remove.enums.EProfiles;

public class ScannerComment extends AbstractDto{
	private Long id_scanner;
	private Long id_user;
	private LocalDateTime comment_date;
	private String profile;
	private String comment;
	private String author_name;
	
	public ScannerComment() {
		super();
	}

	public Long getId_scanner() {
		return id_scanner;
	}

	public void setId_scanner(Long id_scanner) {
		this.id_scanner = id_scanner;
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

	public LocalDateTime getComment_date() {
		return comment_date;
	}

	public void setComment_date(LocalDateTime comment_date) {
		this.comment_date = comment_date;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	@Override
	public String toString() {
		return "ScannerComment [id_scanner=" + id_scanner + ", id_user=" + id_user + ", comment_date=" + comment_date
				+ ", profile=" + profile + ", comment=" + comment + "]";
	}
}
