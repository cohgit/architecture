package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;

public class ScannerResultNewsSnippetDto extends AbstractDto {
	private Long id_scanner_result;
	private Long id_scanner_raw;
	private String title;
	private String link;
	private String domain;
	private String source;
	private String snippet;
	private String thumbnail;
	private String date;
	private LocalDateTime date_utc;
	private String feeling;
	
	public ScannerResultNewsSnippetDto() {
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	@Override
	public String toString() {
		return "ScannerResultNewsSnippetDto [id_scanner_result=" + id_scanner_result + ", title=" + title + ", link="
				+ link + ", domain=" + domain + ", source=" + source + ", snippet=" + snippet + ", thumbnail="
				+ thumbnail + ", date=" + date + ", date_utc=" + date_utc + ", feeling=" + feeling + "]";
	}
}
