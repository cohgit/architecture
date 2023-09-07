package cl.atianza.remove.external.clients.serp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveSERPWebNestedResults {
	
	private int position;
	private String title;
	private String link;
	private String displayed_link;
	private String snippet;
	
	public RemoveSERPWebNestedResults() {
		super();
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
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

	@Override
	public String toString() {
		return "RemoveSERPWebNestedResults [position=" + position + ", title=" + title + ", link=" + link
				+ ", displayed_link=" + displayed_link + ", snippet=" + snippet + "]";
	}
	
	

}
