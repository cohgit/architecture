package cl.atianza.remove.external.clients.serp;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class for images results in API SERP response.
 * @author pilin
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveSERPWebResponse {
	protected Logger log = LogManager.getLogger(RemoveSERPWebResponse.class);
	
	private List<RemoveSERPNewsResults> news_results;
	private List<RemoveSERPImageResults> image_results;
	private List<RemoveSERPWebOrganicResults> organic_results;
	
	private RemoveSERPRequestInfo request_info;
	private Object search_metadata;
	private Object search_parameters;
	private Object search_information;
	private Object knowledge_graph;
	private Object related_searches;
	private RemoveSERPPagination pagination;
	private Object ads;
	private Object autocomplete_results;
	private Object video_results;
	private Object scholar_results;
	private Object places_results;
	private Object shopping_results;
	private Object local_map;
	private Object knowledge_game;
	private Object math_problem;
	private Object answer_box;
	private Object weather_box;
	private Object events;
	private Object top_carousel;
	private Object top_stories;
	private Object top_stories_extra;
	private Object inline_hotels;
	private Object inline_images;
	private Object inline_videos;
	private Object inline_tweets;
	private Object inline_podcasts;
	private Object inline_shopping;
	private Object inline_shopping_comparison;
	private Object sports_results;
	private Object top_products;
	private Object questions_and_answers;
	private Object related_places;
	private Object related_questions;
	
	public RemoveSERPWebResponse() {
		super();
	}

	public RemoveSERPRequestInfo getRequest_info() {
		return request_info;
	}

	public void setRequest_info(RemoveSERPRequestInfo request_info) {
		this.request_info = request_info;
	}

	public Object getSearch_metadata() {
		return search_metadata;
	}

	public void setSearch_metadata(Object search_metadata) {
		this.search_metadata = search_metadata;
	}

	public Object getSearch_parameters() {
		return search_parameters;
	}

	public void setSearch_parameters(Object search_parameters) {
		this.search_parameters = search_parameters;
	}

	public Object getSearch_information() {
		return search_information;
	}

	public void setSearch_information(Object search_information) {
		this.search_information = search_information;
	}

	public Object getKnowledge_graph() {
		return knowledge_graph;
	}

	public void setKnowledge_graph(Object knowledge_graph) {
		this.knowledge_graph = knowledge_graph;
	}

	public Object getRelated_searches() {
		return related_searches;
	}

	public void setRelated_searches(Object related_searches) {
		this.related_searches = related_searches;
	}

	public RemoveSERPPagination getPagination() {
		return pagination;
	}

	public void setPagination(RemoveSERPPagination pagination) {
		this.pagination = pagination;
	}

	public List<RemoveSERPWebOrganicResults> getOrganic_results() {
		return organic_results;
	}

	public void setOrganic_results(List<RemoveSERPWebOrganicResults> organic_results) {
		this.organic_results = organic_results;
	}

	public List<RemoveSERPNewsResults> getNews_results() {
		return news_results;
	}

	public void setNews_results(List<RemoveSERPNewsResults> news_results) {
		this.news_results = news_results;
	}

	public List<RemoveSERPImageResults> getImage_results() {
		return image_results;
	}

	public void setImage_results(List<RemoveSERPImageResults> image_results) {
		this.image_results = image_results;
	}

	public Object getAds() {
		return ads;
	}

	public void setAds(Object ads) {
		this.ads = ads;
	}

	public Object getAutocomplete_results() {
		return autocomplete_results;
	}

	public void setAutocomplete_results(Object autocomplete_results) {
		this.autocomplete_results = autocomplete_results;
	}

	public Object getVideo_results() {
		return video_results;
	}

	public void setVideo_results(Object video_results) {
		this.video_results = video_results;
	}

	public Object getScholar_results() {
		return scholar_results;
	}

	public void setScholar_results(Object scholar_results) {
		this.scholar_results = scholar_results;
	}

	public Object getPlaces_results() {
		return places_results;
	}

	public void setPlaces_results(Object places_results) {
		this.places_results = places_results;
	}

	public Object getShopping_results() {
		return shopping_results;
	}

	public void setShopping_results(Object shopping_results) {
		this.shopping_results = shopping_results;
	}

	public Object getLocal_map() {
		return local_map;
	}

	public void setLocal_map(Object local_map) {
		this.local_map = local_map;
	}

	public Object getKnowledge_game() {
		return knowledge_game;
	}

	public void setKnowledge_game(Object knowledge_game) {
		this.knowledge_game = knowledge_game;
	}

	public Object getMath_problem() {
		return math_problem;
	}

	public void setMath_problem(Object math_problem) {
		this.math_problem = math_problem;
	}

	public Object getAnswer_box() {
		return answer_box;
	}

	public void setAnswer_box(Object answer_box) {
		this.answer_box = answer_box;
	}

	public Object getWeather_box() {
		return weather_box;
	}

	public void setWeather_box(Object weather_box) {
		this.weather_box = weather_box;
	}

	public Object getEvents() {
		return events;
	}

	public void setEvents(Object events) {
		this.events = events;
	}

	public Object getTop_carousel() {
		return top_carousel;
	}

	public void setTop_carousel(Object top_carousel) {
		this.top_carousel = top_carousel;
	}

	public Object getTop_stories() {
		return top_stories;
	}

	public void setTop_stories(Object top_stories) {
		this.top_stories = top_stories;
	}

	public Object getInline_hotels() {
		return inline_hotels;
	}

	public void setInline_hotels(Object inline_hotels) {
		this.inline_hotels = inline_hotels;
	}

	public Object getInline_images() {
		return inline_images;
	}

	public void setInline_images(Object inline_images) {
		this.inline_images = inline_images;
	}

	public Object getInline_videos() {
		return inline_videos;
	}

	public void setInline_videos(Object inline_videos) {
		this.inline_videos = inline_videos;
	}

	public Object getInline_tweets() {
		return inline_tweets;
	}

	public void setInline_tweets(Object inline_tweets) {
		this.inline_tweets = inline_tweets;
	}

	public Object getInline_podcasts() {
		return inline_podcasts;
	}

	public void setInline_podcasts(Object inline_podcasts) {
		this.inline_podcasts = inline_podcasts;
	}

	public Object getInline_shopping() {
		return inline_shopping;
	}

	public void setInline_shopping(Object inline_shopping) {
		this.inline_shopping = inline_shopping;
	}

	public Object getInline_shopping_comparison() {
		return inline_shopping_comparison;
	}

	public void setInline_shopping_comparison(Object inline_shopping_comparison) {
		this.inline_shopping_comparison = inline_shopping_comparison;
	}

	public Object getSports_results() {
		return sports_results;
	}

	public void setSports_results(Object sports_results) {
		this.sports_results = sports_results;
	}

	public Object getTop_products() {
		return top_products;
	}

	public void setTop_products(Object top_products) {
		this.top_products = top_products;
	}

	public Object getQuestions_and_answers() {
		return questions_and_answers;
	}

	public void setQuestions_and_answers(Object questions_and_answers) {
		this.questions_and_answers = questions_and_answers;
	}

	public Object getRelated_places() {
		return related_places;
	}

	public void setRelated_places(Object related_places) {
		this.related_places = related_places;
	}

	public Object getRelated_questions() {
		return related_questions;
	}

	public void setRelated_questions(Object related_questions) {
		this.related_questions = related_questions;
	}

	public Object getTop_stories_extra() {
		return top_stories_extra;
	}

	public void setTop_stories_extra(Object top_stories_extra) {
		this.top_stories_extra = top_stories_extra;
	}

	@Override
	public String toString() {
		return "RemoveSERPResponse [request_info=" + request_info + ", search_metadata=" + search_metadata
				+ ", search_parameters=" + search_parameters + ", search_information=" + search_information
				+ ", knowledge_graph=" + knowledge_graph + ", related_searches=" + related_searches + ", pagination="
				+ pagination + ", organic_results=" + organic_results + "]";
	}

	public boolean checkRepeatedResults() {
		log.info("Checking repeated snippets... " + this.getRequest_info());
		
		if (this.getOrganic_results() != null) {
			List<String> links = new ArrayList<String>();
			
			for (RemoveSERPWebOrganicResults snippet : this.getOrganic_results()) {
				if (links.contains(snippet.getLink())) {
					log.error("TODO Snippet repeated: " + snippet);
					return true;
				} else {
					links.add(snippet.getLink());
				}
			}
		}
		
		if (this.getNews_results() != null) {
			List<String> links = new ArrayList<String>();
			
			for (RemoveSERPNewsResults snippet : this.getNews_results()) {
				if (links.contains(snippet.getLink())) {
					log.error("News Snippet repeated: " + snippet);
					return true;
				} else {
					links.add(snippet.getLink());
				}
			}
		}
		
		if (this.getImage_results() != null) {
			List<String> links = new ArrayList<String>();
			
			for (RemoveSERPImageResults snippet : this.getImage_results()) {
				if (links.contains(snippet.getLink())) {
					log.error("Images Snippet repeated: " + snippet);
					return true;
				} else {
					links.add(snippet.getLink());
				}
			}
		}
		log.info("Repeated Snippets Not found... ");
		
		return false;
	}
}
