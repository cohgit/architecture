package cl.atianza.remove.helpers.reports.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.models.commons.ScannerResultImageSnippet;
import cl.atianza.remove.models.commons.ScannerResultImageSnippetTrack;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippet;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippetTrack;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;
import cl.atianza.remove.models.commons.ScannerResultWebSnippetTrack;
import cl.atianza.remove.utils.RemoveBundle;
import cl.atianza.remove.utils.RemoveDateUtils;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class TableResults implements Comparable<TableResults> {
	private String keyword;
	private String country;
	private String section;
	private String sectionCode;
	private String queryDate;
	private LocalDate resultsDate;
	private String end_date;
	
	private List<TableRow> results = new ArrayList<TableRow>();
	private JRBeanCollectionDataSource resultsDS;
	
	public TableResults() {
		super();
	}
	public TableResults(ScannerResult result, RemoveBundle bundle, LocalDate resultsDate, LocalDate end_date) {
		if (result != null) {
			this.keyword = result.getKeyword().getWord();
			this.country = bundle.getLabelCountryBundle(result.getCountry().getTag());
			this.sectionCode = result.getSearch_type();
			this.section = bundle.getLabelBundle("scanner.section."+result.getSearch_type());
			this.resultsDate = resultsDate;
			this.queryDate = RemoveDateUtils.formatDateStringDash(result.getQuery_date()).trim();
			this.end_date = RemoveDateUtils.formatDateDash(end_date).trim();
			//this.queryDate = RemoveDateUtils.formatDateTimeDash(result.getQuery_date());
		}
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getSectionCode() {
		return sectionCode;
	}
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	public List<TableRow> getResults() {
		return results;
	}
	public void setResults(List<TableRow> results) {
		this.results = results;
	}
	public JRBeanCollectionDataSource getResultsDS() {
		return resultsDS;
	}
	public void setResultsDS(JRBeanCollectionDataSource resultsDS) {
		this.resultsDS = resultsDS;
	}
	public void createDataSource() {
		Collections.sort(this.results);
		this.resultsDS = new JRBeanCollectionDataSource(this.results);
	}
	
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	
	
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public void pushResult(ScannerResultImageSnippet snippet) {
		ScannerResultImageSnippetTrack refTrack = findTrack(snippet);
		
		if (refTrack != null) {
			ScannerResultImageSnippetTrack firstTrack = snippet.getTracking().get(0);
			
			TableRow row = new TableRow();
			
			row.setKeyword(this.getKeyword());
			row.setTitle(snippet.getTitle());
			row.setDescription(snippet.getDescription());
			row.setPagePos(refTrack.getPage() + " / " + refTrack.getPosition_in_page());
			row.setRanking(refTrack.getPosition());
			row.setEvolution(calculateEvolution(firstTrack.getPosition(), refTrack.getPosition()));
			row.setVisibility("");
			row.setDateScan(this.getQueryDate().trim());
			row.setUrl(snippet.getLink());
			row.setTrackingWordOrUrl(snippet.trackingWordsTitles());
			row.setCountry(this.getCountry());
			row.setSection(this.getSection());
			row.setSectionCode(this.getSectionCode());
			row.setEnd_date(this.end_date);
			row.setFeeling(snippet.getFeeling());
			//row.setLastDateScan(lastDate.trim());
			row.addTrackingWords(snippet.getTrackingWords());
			row.prepareDS();
			
			this.getResults().add(row);
		}
	}

	public void pushResult(ScannerResultNewsSnippet snippet) {
		ScannerResultNewsSnippetTrack refTrack = findTrack(snippet);
		
		if (refTrack != null) {
			ScannerResultNewsSnippetTrack firstTrack = snippet.getTracking().get(0);
			
			TableRow row = new TableRow();
			
			row.setKeyword(this.getKeyword());
			row.setTitle(snippet.getTitle());
			row.setDescription(snippet.getSnippet());
			row.setPagePos(refTrack.getPage() + " / " + refTrack.getPosition_in_page());
			row.setRanking(refTrack.getPosition());
			row.setEvolution(calculateEvolution(firstTrack.getPosition(), refTrack.getPosition()));
			row.setVisibility("");
			row.setDateScan(this.getQueryDate().trim());
			row.setUrl(snippet.getLink());
			row.setTrackingWordOrUrl(snippet.trackingWordsTitles());
			row.setCountry(this.getCountry());
			row.setSection(this.getSection());
			row.setSectionCode(this.getSectionCode());
			row.setFeeling(snippet.getFeeling());
			row.setEnd_date(this.end_date);
			//row.setLastDateScan(lastDate.trim());
			row.addTrackingWords(snippet.getTrackingWords());
			row.prepareDS();
			
			this.getResults().add(row);
		}
	}
	public void pushResult(ScannerResultWebSnippet snippet) {
		ScannerResultWebSnippetTrack refTrack = findTrack(snippet);
		 
		if (refTrack != null) {
			ScannerResultWebSnippetTrack firstTrack = snippet.getTracking().get(0);
			
			TableRow row = new TableRow();
			
			row.setKeyword(this.getKeyword());
			row.setTitle(snippet.getTitle());
			row.setDescription(snippet.getSnippet());
			row.setPagePos(refTrack.getPage() + " / " + refTrack.getPosition_in_page());
			row.setRanking(refTrack.getPosition());
			row.setEvolution(calculateEvolution(firstTrack.getPosition(), refTrack.getPosition()));
			row.setVisibility("");
			row.setDateScan(this.getQueryDate().trim());
			row.setUrl(snippet.getLink());
			row.setTrackingWordOrUrl(snippet.trackingWordsTitles());
			row.setCountry(this.getCountry());
			row.setSection(this.getSection());
			row.setSectionCode(this.getSectionCode());
			row.setFeeling(snippet.getFeeling());
			row.setEnd_date(this.end_date);
			//row.setLastDateScan(lastDate.trim());
			row.addTrackingWords(snippet.getTrackingWords());
			row.prepareDS();
			this.getResults().add(row);
		}
	}
	
	private ScannerResultImageSnippetTrack findTrack(ScannerResultImageSnippet snippet) {
		ScannerResultImageSnippetTrack refTrack = null;
		
		if (snippet.getTracking() != null) {
			for (ScannerResultImageSnippetTrack track : snippet.getTracking()) {
				if (track.getDate_scan().toLocalDate().isEqual(this.resultsDate)) {
					refTrack = track;
				}
			}
		}
		
		return refTrack;
	}
	private ScannerResultWebSnippetTrack findTrack(ScannerResultWebSnippet snippet) {
		ScannerResultWebSnippetTrack refTrack = null;
		
		if (snippet.getTracking() != null) {
			for (ScannerResultWebSnippetTrack track : snippet.getTracking()) {
				if (track.getDate_scan().toLocalDate().isEqual(this.resultsDate)) {
					refTrack = track;
				}
			}
		}
		
		return refTrack;
	}
	private ScannerResultNewsSnippetTrack findTrack(ScannerResultNewsSnippet snippet) {
		ScannerResultNewsSnippetTrack refTrack = null;
		
		if (snippet.getTracking() != null) {
			for (ScannerResultNewsSnippetTrack track : snippet.getTracking()) {
				if (track.getDate_scan().toLocalDate().isEqual(this.resultsDate)) {
					refTrack = track;
				}
			}
		}
		
		return refTrack;
	}
	
	private String calculateEvolution(Integer firstPosition, Integer lastPosition) {
		return firstPosition.intValue() == lastPosition.intValue() ? "SAME" :
			firstPosition.intValue() < lastPosition.intValue() ? "UP" : "DOWN";
	}
	
	@Override
	public int compareTo(TableResults o) {
		return o != null ? 
			this.getKeyword().equals(o.getKeyword()) ?
				this.getCountry().equals(o.getCountry()) ?
					this.getSection().compareTo(o.getSection())
						: this.getCountry().compareTo(o.getCountry())
							: this.getKeyword().compareTo(o.getKeyword()) : 0;
	}
	
}
