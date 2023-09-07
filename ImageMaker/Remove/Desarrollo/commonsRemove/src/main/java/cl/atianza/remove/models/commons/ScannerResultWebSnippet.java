package cl.atianza.remove.models.commons;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cl.atianza.remove.dtos.commons.ScannerResultWebSnippetDto;
import cl.atianza.remove.enums.EFeelings;
import cl.atianza.remove.external.clients.serp.RemoveSERPWebNestedResults;

public class ScannerResultWebSnippet extends ScannerResultWebSnippetDto {
	private String uuidRaw;
	private String type;
	private EFeelings feelingObj;
	private List<ScannerTrackingWord> trackingWords;
	private List<ScannerResultWebSnippetTrack> tracking;
	private ArrayList<RemoveSERPWebNestedResults> nested_results;

	public ScannerResultWebSnippet() {
		super();
	}
	
	public String getUuidRaw() {
		return uuidRaw;
	}

	public void setUuidRaw(String uuidRaw) {
		this.uuidRaw = uuidRaw;
	}

	public EFeelings getFeelingObj() {
		if (feelingObj == null) {
			feelingObj = EFeelings.find(this.getFeeling());
		}
		return feelingObj;
	}
	public void setFeelingObj(EFeelings feelingObj) {
		this.feelingObj = feelingObj;
	}

	public List<ScannerTrackingWord> getTrackingWords() {
		if (trackingWords == null) {
			this.trackingWords = new ArrayList<ScannerTrackingWord>();
		}
		return trackingWords;
	}

	public void setTrackingWords(List<ScannerTrackingWord> trackingWords) {
		this.trackingWords = trackingWords;
	}

	public List<ScannerResultWebSnippetTrack> getTracking() {
		if (tracking == null) {
			this.tracking = new ArrayList<ScannerResultWebSnippetTrack>();
		}
		
		return tracking;
	}

	public void setTracking(List<ScannerResultWebSnippetTrack> tracking) {
		this.tracking = tracking;
	}
	
	public ArrayList<RemoveSERPWebNestedResults> getNested_results() {
		return nested_results;
	}

	public void setNested_results(ArrayList<RemoveSERPWebNestedResults> nested_results) {
		this.nested_results = nested_results;
	}

	@Override
	public String toString() {
		return "ScannerResultSnippet [trackingWords=" + trackingWords + "]";
	}
	
	public String getType() {
		if (type == null) {
			type = "web";
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ScannerResultWebSnippetTrack lastTracking() {
		return !this.getTracking().isEmpty() ? this.getTracking().get(this.getTracking().size() - 1) : null;
	}

	public void filterTrackingByPage(int... numberPages) {
		if (numberPages != null) {
			if (this.tracking != null && !this.tracking.isEmpty()) {
				this.tracking = this.tracking.stream().filter(track -> {
					boolean found = false;
					
					for (int page: numberPages) {
						if (track.getPage().intValue() <= page) {
							found = true;
						}
					}
					
					return found;
				}).collect(Collectors.toList());
			}
		}
	}

	public boolean containsTrackingWord(String[] filterTrackingWords) {
		if (filterTrackingWords != null && this.getTrackingWords() != null && !this.getTrackingWords().isEmpty()) {
			for (ScannerTrackingWord scannerTW : this.getTrackingWords()) {
				for (String ftw : filterTrackingWords) {
					if (scannerTW.getWord().trim().equalsIgnoreCase(ftw.trim())) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	public void filterTrackingByRangeDate(LocalDate startDate, LocalDate endDate) {
		if (startDate != null && endDate != null) {
			if (this.tracking != null && !this.tracking.isEmpty()) {
				this.tracking = this.tracking.stream().filter(track -> track.getDate_scan().isAfter(startDate.atStartOfDay()) &&  track.getDate_scan().isBefore(endDate.atTime(LocalTime.MAX))).collect(Collectors.toList());
			}
		}
	}

	public boolean containsFeelings(String[] feelings) {
		if (feelings != null) {
			for(String feel : feelings) {
				if (getFeeling().equalsIgnoreCase(feel.trim())) {
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean containsLinks(String[] urls) {
		if (urls != null) {
			for(String link : urls) {
				if (getLink().equalsIgnoreCase(link.trim())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public String trackingWordsTitles() {
		List<String> keys = new ArrayList<String>();
		
		if (this.getTrackingWords() != null && !this.getTrackingWords().isEmpty()) {
			for (ScannerTrackingWord tw : this.getTrackingWords()) {
				if (tw.esWord()) {
					keys.add(tw.getWord());	
				}
			}
		}
		
		return String.join(", ", keys);
	}
	
	public String urlsTitles() {
		List<String> keys = new ArrayList<String>();
		
		if (this.getTrackingWords() != null && !this.getTrackingWords().isEmpty()) {
			for (ScannerTrackingWord tw : this.getTrackingWords()) {
				if (tw.esURL()) {
					keys.add(tw.getWord());	
				}
			}
		}
		
		return String.join(",", keys);
	}
}
