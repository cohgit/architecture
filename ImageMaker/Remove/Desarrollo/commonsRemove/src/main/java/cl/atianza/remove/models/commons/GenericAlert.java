package cl.atianza.remove.models.commons;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GenericAlert {
	private String title;
	private String link;
	private String country;
	private String section;
	private String keyword;
	private List<String> tracking_word;
	private Integer page;
	private Integer position_in_page;
	private LocalDateTime query_date;
	
	public GenericAlert() {
		super();
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
	

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	

	public List<String> getTracking_word() {
		if (tracking_word == null) {
			this.tracking_word = new ArrayList<String>();
		}
		return tracking_word;
	}

	public void setTracking_word(List<String> tracking_word) {
		this.tracking_word = tracking_word;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPosition_in_page() {
		return position_in_page;
	}

	public void setPosition_in_page(Integer position_in_page) {
		this.position_in_page = position_in_page;
	}

	public LocalDateTime getQuery_date() {
		return query_date;
	}

	public void setQuery_date(LocalDateTime query_date) {
		this.query_date = query_date;
	}

}
