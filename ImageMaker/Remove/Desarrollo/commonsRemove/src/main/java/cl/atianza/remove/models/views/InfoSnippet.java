package cl.atianza.remove.models.views;

import java.util.List;

import cl.atianza.remove.models.commons.Country;
import cl.atianza.remove.models.commons.ScannerKeyword;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippet;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;
import cl.atianza.remove.models.commons.ScannerTrackingWord;

public class InfoSnippet {
	private ScannerKeyword keyword;
	private List<ScannerTrackingWord> trackingWords;
	private String typeSearch;
	private Float rating_visualization;
	private Float rating_reputation;
	private Country country;
	private String snippet;
	private String link;
	private String display_link;
	private String domain;
	private String tittle;
	private int page;
	private int position;
	
	public InfoSnippet() {
		super();
	}
	
	public static InfoSnippet init(ScannerResult result, ScannerResultNewsSnippet snippet) {
		InfoSnippet info = new InfoSnippet();
		
		info.setCountry(result.getCountry());
		info.setKeyword(result.getKeyword());
		info.setRating_reputation(result.getRating_reputation());
		info.setRating_visualization(result.getRating_visualization());
		info.setTypeSearch(result.getSearch_type());
		
		info.setDisplay_link("");
		info.setDomain(snippet.getDomain());
		info.setLink(snippet.getLink());
		info.setSnippet(snippet.getSnippet());
		info.setTittle(snippet.getTitle());
		info.setTrackingWords(snippet.getTrackingWords());
		
		info.setPage(snippet.lastTracking().getPage());
		info.setPosition(snippet.lastTracking().getPosition());
		
		return info;
	}
	public static InfoSnippet init(ScannerResult result, ScannerResultWebSnippet snippet) {
		InfoSnippet info = new InfoSnippet();
		
		info.setCountry(result.getCountry());
		info.setKeyword(result.getKeyword());
		info.setRating_reputation(result.getRating_reputation());
		info.setRating_visualization(result.getRating_visualization());
		info.setTypeSearch(result.getSearch_type());
		
		info.setDisplay_link(snippet.getDisplayed_link());
		info.setDomain(snippet.getDomain());
		info.setLink(snippet.getLink());
		info.setSnippet(snippet.getSnippet());
		info.setTittle(snippet.getTitle());
		info.setTrackingWords(snippet.getTrackingWords());
		
		info.setPage(snippet.lastTracking().getPage());
		info.setPosition(snippet.lastTracking().getPosition());
		
		return info;
	}
	
	public InfoSnippet(String snippet, String link, String display_link, String domain, String tittle, int page,
			int position) {
		super();
		this.snippet = snippet;
		this.link = link;
		this.display_link = display_link;
		this.domain = domain;
		this.tittle = tittle;
		this.page = page;
		this.position = position;
	}
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDisplay_link() {
		return display_link;
	}
	public void setDisplay_link(String display_link) {
		this.display_link = display_link;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	public ScannerKeyword getKeyword() {
		return keyword;
	}

	public void setKeyword(ScannerKeyword keyword) {
		this.keyword = keyword;
	}

	public List<ScannerTrackingWord> getTrackingWords() {
		return trackingWords;
	}

	public void setTrackingWords(List<ScannerTrackingWord> trackingWords) {
		this.trackingWords = trackingWords;
	}

	public String getTypeSearch() {
		return typeSearch;
	}

	public void setTypeSearch(String typeSearch) {
		this.typeSearch = typeSearch;
	}

	public Float getRating_visualization() {
		return rating_visualization;
	}

	public void setRating_visualization(Float rating_visualization) {
		this.rating_visualization = rating_visualization;
	}

	public Float getRating_reputation() {
		return rating_reputation;
	}

	public void setRating_reputation(Float rating_reputation) {
		this.rating_reputation = rating_reputation;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "InfoSnippet [snippet=" + snippet + ", link=" + link + ", display_link=" + display_link + ", domain="
				+ domain + ", tittle=" + tittle + ", page=" + page + ", position=" + position + "]";
	}
}