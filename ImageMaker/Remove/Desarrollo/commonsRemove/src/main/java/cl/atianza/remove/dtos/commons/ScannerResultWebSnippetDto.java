package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;

public class ScannerResultWebSnippetDto extends AbstractDto {
	private Long id_scanner_result;
	private Long id_scanner_raw;
	private String title;
	private String link;
	private String domain;
	private String displayed_link;
	private String snippet;
	private String date;
	private LocalDateTime date_utc;
	private String feeling;
	
	public ScannerResultWebSnippetDto() {
		super();
	}

	public Long getId_scanner_result() {
		return id_scanner_result;
	}

	public void setId_scanner_result(Long id_scanner_result) {
		this.id_scanner_result = id_scanner_result;
	}

	public Long getId_scanner_raw() {
		return id_scanner_raw;
	}

	public void setId_scanner_raw(Long id_scanner_raw) {
		this.id_scanner_raw = id_scanner_raw;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDisplayed_link() {
		return displayed_link;
	}

	public void setDisplayed_link(String displayed_link) {
		this.displayed_link = displayed_link;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public LocalDateTime getDate_utc() {
		return date_utc;
	}

	public void setDate_utc(LocalDateTime date_utc) {
		this.date_utc = date_utc;
	}

	public String getFeeling() {
		return feeling;
	}

	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}

	@Override
	public String toString() {
		return "ScannerResultSnippetDto [id_scanner_result=" + id_scanner_result + ", title=" + title + 
				", link=" + link + ", domain=" + domain + ", displayed_link="
				+ displayed_link + ", snippet=" + snippet + ", date=" + date + ", date_utc=" + date_utc + ", feeling="
				+ feeling + "]";
	}
}
