package cl.atianza.remove.models.commons;

import java.util.List;

import cl.atianza.remove.daos.AbstractDao;

public class ScannerAlert{

	private Long id;
	private Long id_scanner;
	private String periocity;
	private String page_object;
	private boolean new_content_detected;
	private boolean negative_content_detected;
	private boolean displaced_url;
	private boolean matching_tracking_word;
	private boolean matching_keyword;
	private String id_tracking;
	private String tracking_word;
	private String id_keyword;
	private String keyword;
	private boolean status;
	
	public ScannerAlert() {
		super();
	}


	
	
	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public Long getId_scanner() {
		return id_scanner;
	}

	public void setId_scanner(Long id_scanner) {
		this.id_scanner = id_scanner;
	}

	public String getPeriocity() {
		return periocity;
	}

	public void setPeriocity(String periocity) {
		this.periocity = periocity;
	}

	public String getPage_object() {
		return page_object;
	}

	public void setPage_object(String page_object) {
		this.page_object = page_object;
	}

	public boolean isNew_content_detected() {
		return new_content_detected;
	}

	public void setNew_content_detected(boolean new_content_detected) {
		this.new_content_detected = new_content_detected;
	}

	public boolean isNegative_content_detected() {
		return negative_content_detected;
	}

	public void setNegative_content_detected(boolean negative_content_detected) {
		this.negative_content_detected = negative_content_detected;
	}

	public boolean isDisplaced_url() {
		return displaced_url;
	}

	public void setDisplaced_url(boolean displaced_url) {
		this.displaced_url = displaced_url;
	}

	public boolean isMatching_tracking_word() {
		return matching_tracking_word;
	}

	public void setMatching_tracking_word(boolean matching_tracking_word) {
		this.matching_tracking_word = matching_tracking_word;
	}

	public boolean isMatching_keyword() {
		return matching_keyword;
	}

	public void setMatching_keyword(boolean matching_keyword) {
		this.matching_keyword = matching_keyword;
	}

	public String getId_tracking() {
		return id_tracking;
	}

	public void setId_tracking(String id_tracking) {
		this.id_tracking = id_tracking;
	}

	public String getTracking_word() {
		return tracking_word;
	}

	public void setTracking_word(String tracking_word) {
		this.tracking_word = tracking_word;
	}

	public String getId_keyword() {
		return id_keyword;
	}

	public void setId_keyword(String id_keyword) {
		this.id_keyword = id_keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "ScannerAlert [id_scanner=" + id_scanner + ", periocity=" + periocity + ", page_object=" + page_object
				+ ", new_content_detected=" + new_content_detected + ", negative_content_detected="
				+ negative_content_detected + ", displaced_url=" + displaced_url + ", matching_tracking_word="
				+ matching_tracking_word + ", matching_keyword=" + matching_keyword + ", id_tracking=" + id_tracking
				+ ", tracking_word=" + tracking_word + ", id_keyword=" + id_keyword + ", keyword=" + keyword
				+ ", status=" + status + "]";
	}

	


}
