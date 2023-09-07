package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;

public class ScannerResultNewsSnippetTrackDto extends AbstractDto {
	private Long id_scanner_result_news_snippet;
	private Long id_scanner_raw;
	private Integer page;
	private Integer position;
	private Integer position_in_page;
	private LocalDateTime date_scan;
	
	public ScannerResultNewsSnippetTrackDto() {
		super();
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getPosition_in_page() {
		return position_in_page;
	}

	public void setPosition_in_page(Integer position_in_page) {
		this.position_in_page = position_in_page;
	}

	public Long getId_scanner_result_news_snippet() {
		return id_scanner_result_news_snippet;
	}

	public void setId_scanner_result_news_snippet(Long id_scanner_result_news_snippet) {
		this.id_scanner_result_news_snippet = id_scanner_result_news_snippet;
	}

	public Long getId_scanner_raw() {
		return id_scanner_raw;
	}

	public void setId_scanner_raw(Long id_scanner_raw) {
		this.id_scanner_raw = id_scanner_raw;
	}

	public LocalDateTime getDate_scan() {
		return date_scan;
	}

	public void setDate_scan(LocalDateTime date_scan) {
		this.date_scan = date_scan;
	}

	@Override
	public String toString() {
		return "ScannerResultSnippetTrackDto [id_scanner_result_news_snippet=" + id_scanner_result_news_snippet + ", page=" + page
				+ ", position=" + position + ", date_scan=" + date_scan + "]";
	}
}
