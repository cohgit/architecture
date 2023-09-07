package cl.atianza.remove.helpers.reports.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cl.atianza.remove.enums.ELanguages;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;
import cl.atianza.remove.utils.RemoveBundle;
import cl.atianza.remove.utils.RemoveDateUtils;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class TableChartElementResults {	
	private List<ChartElement> results = new ArrayList<ChartElement>();
	private List<List<ChartElement>> timeLineList = new ArrayList<List<ChartElement>>();
	private JRBeanCollectionDataSource DSTimeLineResults;
	
	public TableChartElementResults() {
		super();
	}

	public List<ChartElement> getResults() {
		return results;
	}

	public void setResults(List<ChartElement> results) {
		this.results = results;
	}
	
	public List<List<ChartElement>> getTimeLineList() {
		return timeLineList;
	}

	public void setTimeLineList(List<List<ChartElement>> timeLineList) {
		this.timeLineList = timeLineList;
	}



	public JRBeanCollectionDataSource getDSTimeLineResults() {
		return DSTimeLineResults;
	}

	public void setDSTimeLineResults(JRBeanCollectionDataSource dSTimeLineResults) {
		DSTimeLineResults = dSTimeLineResults;
	}

	public void createDataSource() {
		this.DSTimeLineResults = new JRBeanCollectionDataSource(this.timeLineList);
	}

	public void pushChartWebElement(ScannerResult result, ScannerResultWebSnippet snippet) {
        String timelineLabel = buildTimelineLabel(result, snippet.getLink());
        String snippetId = "web-" + String.valueOf(snippet.getId());

        if (snippet != null && snippet.getTracking() != null && !snippet.getTracking().isEmpty()) {
            snippet.getTracking().forEach(track -> {
                //getLog().info(uuid + ": Pushing web snippet track to Remove/Impulse Content: " + snippet.getId() + " - " + track.getId());
                String date = RemoveDateUtils.formatDateDash(track.getDate_scan().toLocalDate());

//    			list.add(new ChartElement(timelineLabel, snippetId, track.getDate_scan().toLocalDate(), track.getPosition()));
                
                this.getResults().add(new ChartElement(snippetId, timelineLabel, date, track.getPosition() * -1));
            });
        }
	}
	
    private String buildTimelineLabel(ScannerResult result, String link) {
        int capChars = 120;
        String label = result.getKeyword().getWord() + " - " + RemoveBundle.init(ELanguages.SPANISH.getCode()).getLabelBundle("scanner.section." + result.getSearch_type()) + " - " + RemoveBundle.init(ELanguages.SPANISH.getCode()).getLabelCountryBundle(result.getCountry().getTag()) + " - " + link;

        return label.length() <= capChars ? label : label.substring(0, capChars) + "...";
    }

}
