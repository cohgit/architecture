package cl.atianza.remove.helpers.reports.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cl.atianza.remove.models.commons.ScannerTrackingWord;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class TableRow implements Comparable<TableRow>{
	private String keyword;
	private String title;
	private String description;
	private String pagePos;
	private Integer ranking;
	private String evolution;
	private String visibility;
	private String dateScan;
	private String country;
	private String section;
	private String sectionCode;
	private String url;
	private String feeling;
	private String trackingWordOrUrl;
	private int target_page;
	private int monitor_page;
	private int position_in_page;
	private String scanType;
	private String lastDateScan;
	private String end_date;
	private List<TrackingWordBean> trackingWordList = new ArrayList<TrackingWordBean>();
	private JRBeanCollectionDataSource trackingWordDS;

	
	public TableRow() {
		super();
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPagePos() {
		return pagePos;
	}
	public void setPagePos(String pagePos) {
		this.pagePos = pagePos;
	}
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public String getEvolution() {
		return evolution;
	}
	public void setEvolution(String evolution) {
		this.evolution = evolution;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getDateScan() {
		return dateScan;
	}
	public void setDateScan(String dateScan) {
		this.dateScan = dateScan;
	}
	
	
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTrackingWordOrUrl() {
		return trackingWordOrUrl;
	}
	public void setTrackingWordOrUrl(String trackingWordOrUrl) {
		this.trackingWordOrUrl = trackingWordOrUrl;
	}
	public String getFeeling() {
		return feeling;
	}
	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}	
	
	public int getTarget_page() {
		return target_page;
	}
	public void setTarget_page(int target_page) {
		this.target_page = target_page;
	}
	
	public int getMonitor_page() {
		return monitor_page;
	}
	public void setMonitor_page(int monitor_page) {
		this.monitor_page = monitor_page;
	}
	
	
	
	public String getLastDateScan() {
		return lastDateScan;
	}
	public void setLastDateScan(String lastDateScan) {
		this.lastDateScan = lastDateScan;
	}
	public int getPosition_in_page() {
		return position_in_page;
	}
	public void setPosition_in_page(int position_in_page) {
		this.position_in_page = position_in_page;
	}
	
	
	
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public JRBeanCollectionDataSource getTrackingWordDS() {
		return trackingWordDS;
	}
	public void setTrackingWordDS(JRBeanCollectionDataSource trackingWordDS) {
		this.trackingWordDS = trackingWordDS;
	}
	
	public List<TrackingWordBean> getTrackingWordList() {
		return trackingWordList;
	}
	public void setTrackingWordList(List<TrackingWordBean> trackingWordList) {
		this.trackingWordList = trackingWordList;
	}
	public void addTrackingWords(List<ScannerTrackingWord> trackingWords) {
		if (trackingWords != null && !trackingWords.isEmpty()) {
			for (ScannerTrackingWord tw: trackingWords) {
				if (tw.esWord()) this.trackingWordList.add(new TrackingWordBean(tw.getWord(), tw.getFeeling()));
			}
		}
	}
	public void prepareDS() {
		Collections.sort(this.trackingWordList);
		this.trackingWordDS = new JRBeanCollectionDataSource(this.trackingWordList);
	}
	@Override
	public int compareTo(TableRow o) {
		return o != null ? 
			this.getKeyword().equals(o.getKeyword()) ?
				this.getCountry().equals(o.getCountry()) ?
					this.getSection().equals(o.getSection()) ?
						this.getRanking().compareTo(o.getRanking())
							: this.getSection().compareTo(o.getSection())
								: this.getCountry().compareTo(o.getCountry())
									: this.getKeyword().compareTo(o.getKeyword()) : 0;
	}
}
