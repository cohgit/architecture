package cl.atianza.remove.models.commons;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cl.atianza.remove.dtos.commons.ScannerResultNewsSnippetDto;
import cl.atianza.remove.enums.EFeelings;

public class ScannerResultNewsSnippet extends ScannerResultNewsSnippetDto {
	private String uuidRaw;
	private String type;
	private EFeelings feelingObj;
	private List<ScannerTrackingWord> trackingWords;
	private List<ScannerResultNewsSnippetTrack> tracking;
	
	public ScannerResultNewsSnippet() {
		super();
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

	public String getUuidRaw() {
		return uuidRaw;
	}

	public void setUuidRaw(String uuidRaw) {
		this.uuidRaw = uuidRaw;
	}

	public List<ScannerTrackingWord> getTrackingWords() {
		if (this.trackingWords == null) {
			this.trackingWords = new ArrayList<ScannerTrackingWord>();
		}
		return trackingWords;
	}

	public void setTrackingWords(List<ScannerTrackingWord> trackingWords) {
		this.trackingWords = trackingWords;
	}

	public List<ScannerResultNewsSnippetTrack> getTracking() {
		if (this.tracking == null) {
			this.tracking = new ArrayList<ScannerResultNewsSnippetTrack>();
		}
		return tracking;
	}

	public void setTracking(List<ScannerResultNewsSnippetTrack> tracking) {
		this.tracking = tracking;
	}

	public String getType() {
		if (type == null) {
			type = "news";
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ScannerResultNewsSnippet [" + super.toString() + ", trackingWords=" + trackingWords + ", tracking=" + tracking + "]";
	}

	public ScannerResultNewsSnippetTrack lastTracking() {
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
		
		return String.join(",", keys);
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
