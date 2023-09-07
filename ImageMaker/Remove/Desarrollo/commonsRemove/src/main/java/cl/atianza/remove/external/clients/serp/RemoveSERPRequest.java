package cl.atianza.remove.external.clients.serp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Request class for API SERP.
 * @author pilin
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveSERPRequest {
	// Web Params
	private String q;
	private String search_type;
	private String device;
	private String page;
	private String max_page;
	private String num;
	private String google_domain;
	private String gl;
	private String hl;
//	private String start;
	
	@Deprecated
	private String location;
	@Deprecated
	private String time_period;
	@Deprecated
	private String sort_by;
	@Deprecated
	private String output;
	@Deprecated
	private String filter;
	@Deprecated
	private String include_answer_box;
	@Deprecated
	private String include_advertiser_info;
	@Deprecated
	private String include_html;
	@Deprecated
	private String flatten_results;
	@Deprecated
	private String url;
	@Deprecated
	private String cookie;
	
	// News Params
	private String news_type;
	@Deprecated
	private String show_duplicates;
	
	// Image Params
	private String images_page;
	private String images_size;
	private String images_color;
	private String images_type;
	private String images_usage;
	
//	private String csv_fields;
//	private String location_auto;
//	private String uule;
//	private String lr;
//	private String cr;
//	private String time_period_min;
//	private String time_period_max;
//	private String nfpr;
//	private String safe;
//	private String skip_on_incident;
//	private String hide_base64_images;
	
	public RemoveSERPRequest() {
		super();
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getSearch_type() {
		return search_type;
	}

	public void setSearch_type(String search_type) {
		this.search_type = search_type;
	}

	@Deprecated
	public String getUrl() {
		return url;
	}
	@Deprecated
	public void setUrl(String url) {
		this.url = url;
	}
	@Deprecated
	public String getOutput() {
		return output;
	}
	@Deprecated
	public void setOutput(String output) {
		this.output = output;
	}
	@Deprecated
	public String getInclude_html() {
		return include_html;
	}
	@Deprecated
	public void setInclude_html(String include_html) {
		this.include_html = include_html;
	}
	@Deprecated
	public String getFlatten_results() {
		return flatten_results;
	}
	@Deprecated
	public void setFlatten_results(String flatten_results) {
		this.flatten_results = flatten_results;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}
	@Deprecated
	public String getLocation() {
		return location;
	}
	@Deprecated
	public void setLocation(String location) {
		this.location = location;
	}

	public String getGoogle_domain() {
		return google_domain;
	}

	public void setGoogle_domain(String google_domain) {
		this.google_domain = google_domain;
	}
	public String getGl() {
		return gl;
	}

	public void setGl(String gl) {
		this.gl = gl;
	}

	public String getHl() {
		return hl;
	}

	public void setHl(String hl) {
		this.hl = hl;
	}
	@Deprecated
	public String getTime_period() {
		return time_period;
	}
	@Deprecated
	public void setTime_period(String time_period) {
		this.time_period = time_period;
	}
	@Deprecated
	public String getSort_by() {
		return sort_by;
	}
	@Deprecated
	public void setSort_by(String sort_by) {
		this.sort_by = sort_by;
	}
	@Deprecated
	public String getFilter() {
		return filter;
	}
	@Deprecated
	public void setFilter(String filter) {
		this.filter = filter;
	}
	@Deprecated
	public String getInclude_answer_box() {
		return include_answer_box;
	}
	@Deprecated
	public void setInclude_answer_box(String include_answer_box) {
		this.include_answer_box = include_answer_box;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getMax_page() {
		return max_page;
	}

	public void setMax_page(String max_page) {
		this.max_page = max_page;
	}

	public String getNews_type() {
		return news_type;
	}

	public void setNews_type(String news_type) {
		this.news_type = news_type;
	}
	@Deprecated
	public String getShow_duplicates() {
		return show_duplicates;
	}
	@Deprecated
	public void setShow_duplicates(String show_duplicates) {
		this.show_duplicates = show_duplicates;
	}
	@Deprecated
	public String getInclude_advertiser_info() {
		return include_advertiser_info;
	}
	@Deprecated
	public void setInclude_advertiser_info(String include_advertiser_info) {
		this.include_advertiser_info = include_advertiser_info;
	}
	@Deprecated
	public String getCookie() {
		return cookie;
	}
	@Deprecated
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getImages_page() {
		return images_page;
	}

	public void setImages_page(String images_page) {
		this.images_page = images_page;
	}

	public String getImages_size() {
		return images_size;
	}

	public void setImages_size(String images_size) {
		this.images_size = images_size;
	}

	public String getImages_color() {
		return images_color;
	}

	public void setImages_color(String images_color) {
		this.images_color = images_color;
	}

	public String getImages_type() {
		return images_type;
	}

	public void setImages_type(String images_type) {
		this.images_type = images_type;
	}

	public String getImages_usage() {
		return images_usage;
	}

	public void setImages_usage(String images_usage) {
		this.images_usage = images_usage;
	}
	
	

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "RemoveSERPRequest [q=" + q + ", search_type=" + search_type + ", device=" + device + ", page=" + page
				+ ", google_domain=" + google_domain + ", gl=" + gl + ", hl=" + hl + ", location="
				+ location + ", time_period=" + time_period + ", sort_by=" + sort_by + ", output=" + output
				+ ", filter=" + filter + ", include_answer_box=" + include_answer_box + ", include_advertiser_info="
				+ include_advertiser_info + ", include_html=" + include_html + ", flatten_results=" + flatten_results
				+ ", url=" + url + ", cookie=" + cookie + ", news_type=" + news_type + ", show_duplicates="
				+ show_duplicates + ", images_page=" + images_page + ", images_size=" + images_size + ", images_color="
				+ images_color + ", images_type=" + images_type + ", images_usage=" + images_usage + "]";
	}
}
