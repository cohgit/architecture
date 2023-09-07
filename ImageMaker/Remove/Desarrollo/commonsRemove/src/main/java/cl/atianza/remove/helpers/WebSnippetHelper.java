package cl.atianza.remove.helpers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cl.atianza.remove.enums.EFeelings;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;
import cl.atianza.remove.models.commons.ScannerTrackingWord;

public class WebSnippetHelper {
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
	
	private Long id_scanner_result_web_snippet;
	private Long id_scanner_raw_track;
	private LocalDateTime date_scan;
	private Integer page;
	private Integer position;
	private Integer position_in_page;
	
	private List<ScannerTrackingWord> snippetTW;
	
	private Integer page_final;
	public WebSnippetHelper() {
		super();
	}



	public WebSnippetHelper(Long id_scanner_result, Long id_scanner_raw, String title, String link, String domain,
			String displayed_link, String snippet, String date, LocalDateTime date_utc, String feeling,
			Long id_scanner_result_web_snippet, Long id_scanner_raw_track, LocalDateTime date_scan, Integer page,
			Integer position, Integer position_in_page) {
		super();
		this.id_scanner_result = id_scanner_result;
		this.id_scanner_raw = id_scanner_raw;
		this.title = title;
		this.link = link;
		this.domain = domain;
		this.displayed_link = displayed_link;
		this.snippet = snippet;
		this.date = date;
		this.date_utc = date_utc;
		this.feeling = feeling;
		this.id_scanner_result_web_snippet = id_scanner_result_web_snippet;
		this.id_scanner_raw_track = id_scanner_raw_track;
		this.date_scan = date_scan;
		this.page = page;
		this.position = position;
		this.position_in_page = position_in_page;
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

	public Long getId_scanner_result_web_snippet() {
		return id_scanner_result_web_snippet;
	}

	public void setId_scanner_result_web_snippet(Long id_scanner_result_web_snippet) {
		this.id_scanner_result_web_snippet = id_scanner_result_web_snippet;
	}

	public Long getId_scanner_raw_track() {
		return id_scanner_raw_track;
	}

	public void setId_scanner_raw_track(Long id_scanner_raw_track) {
		this.id_scanner_raw_track = id_scanner_raw_track;
	}

	public LocalDateTime getDate_scan() {
		return date_scan;
	}

	public void setDate_scan(LocalDateTime date_scan) {
		this.date_scan = date_scan;
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


	
	

	public Integer getPage_final() {
		return page_final;
	}



	public void setPage_final(Integer page_final) {
		this.page_final = page_final;
	}

	

	public List<ScannerTrackingWord> getSnippetTW() {
		if (snippetTW == null) {
			this.snippetTW = new ArrayList<ScannerTrackingWord>();
		}

		return snippetTW;
	}



	public void setSnippetTW(List<ScannerTrackingWord> snippetTW) {
		this.snippetTW = snippetTW;
	}



	@Override
	public String toString() {
		return "WebSnippetHelper [id_scanner_result=" + id_scanner_result + ", id_scanner_raw=" + id_scanner_raw
				+ ", title=" + title + ", link=" + link + ", domain=" + domain + ", displayed_link=" + displayed_link
				+ ", snippet=" + snippet + ", date=" + date + ", date_utc=" + date_utc + ", feeling=" + feeling
				+ ", id_scanner_result_web_snippet=" + id_scanner_result_web_snippet + ", id_scanner_raw_track="
				+ id_scanner_raw_track + ", date_scan=" + date_scan + ", page=" + page + ", position=" + position
				+ ", position_in_page=" + position_in_page + ", page_final=" + page_final + "]";
	}




	
	
	
	

}
