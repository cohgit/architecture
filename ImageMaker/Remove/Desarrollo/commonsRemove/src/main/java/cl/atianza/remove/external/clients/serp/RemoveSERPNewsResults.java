package cl.atianza.remove.external.clients.serp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class for news results in API SERP response.
 * @author pilin
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveSERPNewsResults {
	private Integer position;
	private String title;
	private String link;
	private String domain;
	private String source;
	private String date;
	private String date_utc;
	private String snippet;
	private String thumbnail;
	private Integer page;
	
	public RemoveSERPNewsResults() {
		super();
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
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

	public String getDate_utc() {
		return date_utc;
	}

	public void setDate_utc(String date_utc) {
		this.date_utc = date_utc;
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

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "RemoveSERPNewsResults [position=" + position + ", title=" + title + ", link=" + link + ", domain="
				+ domain + ", source=" + source + ", date=" + date + ", date_utc=" + date_utc + ", snippet=" + snippet
				+ ", thumbnail=" + thumbnail + "]";
	}
}