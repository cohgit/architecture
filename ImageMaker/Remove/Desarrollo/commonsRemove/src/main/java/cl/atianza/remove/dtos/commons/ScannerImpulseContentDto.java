package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;

public class ScannerImpulseContentDto extends AbstractDto {
	private Long id_scanner_impulse;
	private String title;
	private String content;
	private String image_link;
	private LocalDateTime creation_date;
	private Long id_author;
	private String author_profile;
	
	public ScannerImpulseContentDto() {
		super();
	}

	public Long getId_scanner_impulse() {
		return id_scanner_impulse;
	}

	public void setId_scanner_impulse(Long id_scanner_impulse) {
		this.id_scanner_impulse = id_scanner_impulse;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage_link() {
		return image_link;
	}

	public void setImage_link(String image_link) {
		this.image_link = image_link;
	}

	public Long getId_author() {
		return id_author;
	}

	public void setId_author(Long id_author) {
		this.id_author = id_author;
	}

	public String getAuthor_profile() {
		return author_profile;
	}

	public void setAuthor_profile(String author_profile) {
		this.author_profile = author_profile;
	}

	@Override
	public String toString() {
		return "ScannerImpulseContentDto [id_scanner_impulse=" + id_scanner_impulse + ", title=" + title + ", content="
				+ content + ", image_link=" + image_link + ", creation_date=" + creation_date + ", id_author="
				+ id_author + ", author_profile=" + author_profile + "]";
	}
}
