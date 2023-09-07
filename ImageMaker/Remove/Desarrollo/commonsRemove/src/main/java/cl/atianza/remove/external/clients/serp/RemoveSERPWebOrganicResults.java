package cl.atianza.remove.external.clients.serp;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class for standard results in API SERP response.
 * @author pilin
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveSERPWebOrganicResults {
	private Integer position;
	private String title;
	private String link;
	private String domain;	
	private String displayed_link;
	private String snippet;
	private Boolean prerender;
	private List<String> snippet_matched;
	private List<String> missing_words;
	private String cached_page_link;
	private String related_page_link;
	private Integer block_position;
	private RemoveSerpSiteLink sitelinks;
	private Object rich_snippet;
	private Object sitelinks_search_box;
	private Boolean thumbnail;
	private String date;
	private String date_utc;
	private Integer page;
	private ArrayList<RemoveSERPWebNestedResults> nested_results;
	
	public RemoveSERPWebOrganicResults() {
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

	public Boolean getPrerender() {
		return prerender;
	}

	public void setPrerender(Boolean prerender) {
		this.prerender = prerender;
	}

	public List<String> getSnippet_matched() {
		return snippet_matched;
	}

	public void setSnippet_matched(List<String> snippet_matched) {
		this.snippet_matched = snippet_matched;
	}

	public String getCached_page_link() {
		return cached_page_link;
	}

	public void setCached_page_link(String cached_page_link) {
		this.cached_page_link = cached_page_link;
	}

	public Integer getBlock_position() {
		return block_position;
	}

	public void setBlock_position(Integer block_position) {
		this.block_position = block_position;
	}

	public List<String> getMissing_words() {
		return missing_words;
	}

	public void setMissing_words(List<String> missing_words) {
		this.missing_words = missing_words;
	}

	public RemoveSerpSiteLink getSitelinks() {
		return sitelinks;
	}

	public void setSitelinks(RemoveSerpSiteLink sitelinks) {
		this.sitelinks = sitelinks;
	}

	public Object getRich_snippet() {
		return rich_snippet;
	}

	public void setRich_snippet(Object rich_snippet) {
		this.rich_snippet = rich_snippet;
	}

	public String getRelated_page_link() {
		return related_page_link;
	}

	public void setRelated_page_link(String related_page_link) {
		this.related_page_link = related_page_link;
	}

	public Boolean getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Boolean thumbnail) {
		this.thumbnail = thumbnail;
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

	public Object getSitelinks_search_box() {
		return sitelinks_search_box;
	}

	public void setSitelinks_search_box(Object sitelinks_search_box) {
		this.sitelinks_search_box = sitelinks_search_box;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
	

	public ArrayList<RemoveSERPWebNestedResults> getNested_results() {
		return nested_results;
	}

	public void setNested_results(ArrayList<RemoveSERPWebNestedResults> nested_results) {
		this.nested_results = nested_results;
	}

	@Override
	public String toString() {
		return "RemoveSERPWebOrganicResults [position=" + position + ", title=" + title + ", link=" + link + ", domain="
				+ domain + ", displayed_link=" + displayed_link + ", snippet=" + snippet + ", prerender=" + prerender
				+ ", snippet_matched=" + snippet_matched + ", missing_words=" + missing_words + ", cached_page_link="
				+ cached_page_link + ", related_page_link=" + related_page_link + ", block_position=" + block_position
				+ ", sitelinks=" + sitelinks + ", rich_snippet=" + rich_snippet + ", sitelinks_search_box="
				+ sitelinks_search_box + ", thumbnail=" + thumbnail + ", date=" + date + ", date_utc=" + date_utc
				+ ", page=" + page + "]";
	}
	
	

	
}

@JsonIgnoreProperties(ignoreUnknown = true)
class RemoveSerpSiteLink {
	private List<InlineLink> expanded;
	private List<InlineLink> inline;

	public RemoveSerpSiteLink() {
		super();
	}

	public List<InlineLink> getExpanded() {
		return expanded;
	}

	public void setExpanded(List<InlineLink> expanded) {
		this.expanded = expanded;
	}

	public List<InlineLink> getInline() {
		return inline;
	}

	public void setInline(List<InlineLink> inline) {
		this.inline = inline;
	}

	@Override
	public String toString() {
		return "RemoveSerpSiteLink [inline=" + inline + "]";
	}
}
@JsonIgnoreProperties(ignoreUnknown = true)
class InlineLink {
	private String title;
	private String link;
	public InlineLink() {
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
	@Override
	public String toString() {
		return "InlineLink [title=" + title + ", link=" + link + "]";
	}
}
