package cl.atianza.remove.utils;

import java.util.ArrayList;
import java.util.List;

import cl.atianza.remove.models.commons.GenericAlert;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;

public class AlertSectionUtils {
	
	private List<GenericAlert> new_content;
	private List<GenericAlert> negative_content;
	private List<GenericAlert> displaced_url;
	private List<GenericAlert> match_track;
	private List<GenericAlert> match_key;
	
	public AlertSectionUtils() {
		super();
	}

	public List<GenericAlert> getNew_content() {
		if (new_content == null) {
			this.new_content = new ArrayList<GenericAlert>();
		}
		return new_content;
	}

	public void setNew_content(List<GenericAlert> new_content) {
		this.new_content = new_content;
	}

	public List<GenericAlert> getNegative_content() {
		if (negative_content == null) {
			this.negative_content = new ArrayList<GenericAlert>();
		}

		return negative_content;
	}

	public void setNegative_content(List<GenericAlert> negative_content) {
		this.negative_content = negative_content;
	}

	public List<GenericAlert> getDisplaced_url() {
		if (displaced_url == null) {
			this.displaced_url = new ArrayList<GenericAlert>();
		}
		
		return displaced_url;
	}

	public void setDisplaced_url(List<GenericAlert> displaced_url) {
		this.displaced_url = displaced_url;
	}

	public List<GenericAlert> getMatch_track() {
		if (match_track == null) {
			this.match_track = new ArrayList<GenericAlert>();
		}
		
		return match_track;
	}

	public void setMatch_track(List<GenericAlert> match_track) {
		this.match_track = match_track;
	}

	public List<GenericAlert> getMatch_key() {
		if (match_key == null) {
			this.match_key = new ArrayList<GenericAlert>();
		}
		
		return match_key;
	}

	public void setMatch_key(List<GenericAlert> match_key) {
		this.match_key = match_key;
	}

	@Override
	public String toString() {
		return "AlertSectionUtils [new_content=" + new_content + ", negative_content=" + negative_content
				+ ", displaced_url=" + displaced_url + ", match_track=" + match_track + ", match_key=" + match_key
				+ "]";
	}
	
	

}
