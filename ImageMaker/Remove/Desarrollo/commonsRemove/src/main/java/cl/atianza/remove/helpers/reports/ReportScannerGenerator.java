package cl.atianza.remove.helpers.reports;

import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.commons.*;
import cl.atianza.remove.enums.EFeelings;
import cl.atianza.remove.enums.ELanguages;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.enums.EReportTypes;
import cl.atianza.remove.enums.ESearchTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.EmailReportHelper;
import cl.atianza.remove.helpers.reports.beans.FeelingCounter;
import cl.atianza.remove.helpers.reports.beans.*;
import cl.atianza.remove.models.ReportParams;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.models.commons.*;
import cl.atianza.remove.utils.RemoveBundle;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveJsonUtil;
import net.sf.jasperreports.engine.JRParameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Report generator class for Scanner and Dashboard PDF file.
 *
 * @author Quiliano Gutierrez
 */
public class ReportScannerGenerator extends AbstractReportGenerator {
    private final String KEY_PARAM_FILTER_USER_UUID = "KEY_PARAM_FILTER_USER_UUID";
    private final String KEY_PARAM_FILTER_USER_PROFILE = "KEY_PARAM_FILTER_USER_PROFILE";
    private final String KEY_PARAM_FILTER_SCANNER_UUID = "KEY_PARAM_FILTER_SCANNER_UUID";

    private final String KEY_PARAM_FILTER_COUNTRIES = "KEY_PARAM_FILTER_COUNTRIES";
    private final String KEY_PARAM_FILTER_KEYWORDS = "KEY_PARAM_FILTER_KEYWORDS";
    private final String KEY_PARAM_FILTER_PAGES = "KEY_PARAM_FILTER_PAGES";
    private final String KEY_PARAM_FILTER_TRACKING_WORDS = "KEY_PARAM_FILTER_TRACKING_WORDS";
    private final String KEY_PARAM_FILTER_FEELINGS = "KEY_PARAM_FILTER_FEELINGS";
    private final String KEY_PARAM_FILTER_FEELINGS_NAMES = "KEY_PARAM_FILTER_FEELINGS_NAMES";
    private final String KEY_PARAM_FILTER_URLS = "KEY_PARAM_FILTER_URLS";
    private final String KEY_PARAM_FILTER_SECTIONS = "KEY_PARAM_FILTER_SECTIONS";
    private final String KEY_PARAM_FILTER_SECTIONS_NAMES = "KEY_PARAM_FILTER_SECTIONS_NAMES";
    private final String KEY_PARAM_FILTER_SCANNER_DATE_START = "KEY_PARAM_FILTER_SCANNER_DATE_START";
    private final String KEY_PARAM_FILTER_SCANNER_DATE_END = "KEY_PARAM_FILTER_SCANNER_DATE_END";
    private final String KEY_PARAM_FILTER_IS_NEW_CONTENT = "KEY_PARAM_FILTER_IS_NEW_CONTENT";

    private final String KEY_PARAM_CLIENT_NAME = "KEY_PARAM_CLIENT_NAME";
    private final String KEY_PARAM_USER_NAME_GENERATOR = "KEY_PARAM_USER_NAME_GENERATOR";
    private final String KEY_PARAM_CREATION_DATE = "KEY_PARAM_CREATION_DATE";

    private final String KEY_PARAM_CONFIG_SCANNER_NAME = "KEY_PARAM_CONFIG_SCANNER_NAME";
    private final String KEY_PARAM_CONFIG_SCANNER_TYPE = "KEY_PARAM_CONFIG_SCANNER_TYPE";
    private final String KEY_PARAM_CONFIG_SCANNER_TYPE_CODE = "KEY_PARAM_CONFIG_SCANNER_TYPE_CODE";
    private final String KEY_PARAM_CONFIG_KEYWORDS = "KEY_PARAM_CONFIG_KEYWORDS";
    //	private final String KEY_PARAM_CONFIG_COUNTRIES = "KEY_PARAM_CONFIG_COUNTRIES";
    private final String KEY_PARAM_CONFIG_COUNTRIES_NAMES = "KEY_PARAM_CONFIG_COUNTRIES_NAMES";
    private final String KEY_PARAM_CONFIG_TRACKING_WORDS = "KEY_PARAM_CONFIG_TRACKING_WORDS";
    private final String KEY_PARAM_CONFIG_TRACKING_WORDS_DS = "KEY_PARAM_CONFIG_TRACKING_WORDS_DS";
    private final String KEY_PARAM_CONFIG_URL_TO_REMOVE = "KEY_PARAM_CONFIG_URL_TO_REMOVE";
    private final String KEY_PARAM_CONFIG_URL_TO_REMOVE_DS = "KEY_PARAM_CONFIG_URL_TO_REMOVE_DS";

    private final String KEY_PARAM_CONFIG_PAGES = "KEY_PARAM_CONFIG_PAGES";
    //	private final String KEY_PARAM_CONFIG_LANGUAGE = "KEY_PARAM_CONFIG_LANGUAGE";
    private final String KEY_PARAM_CONFIG_LANGUAGE_NAME = "KEY_PARAM_CONFIG_LANGUAGE_NAME";
    //	private final String KEY_PARAM_CONFIG_DEVICES = "KEY_PARAM_CONFIG_DEVICES";
    private final String KEY_PARAM_CONFIG_DEVICES_NAMES = "KEY_PARAM_CONFIG_DEVICES_NAMES";
    //	private final String KEY_PARAM_CONFIG_SECTIONS = "KEY_PARAM_CONFIG_SECTIONS";
    private final String KEY_PARAM_CONFIG_SECTIONS_NAMES = "KEY_PARAM_CONFIG_SECTIONS_NAMES";

    private final String KEY_PARAM_PROGRESS_BAR = "KEY_PARAM_PROGRESS_BAR";
    private final String KEY_PARAM_TOTAL_THREATS_TO_REMOVE = "KEY_PARAM_TOTAL_THREATS_TO_REMOVE";
    private final String KEY_PARAM_TOTAL_THREATS_REMOVED = "KEY_PARAM_TOTAL_THREATS_REMOVED";
    private final String KEY_PARAM_TOTAL_THREATS_IN_PROGRESS = "KEY_PARAM_TOTAL_THREATS_IN_PROGRESS";
    private final String KEY_PARAM_SECTION_CONTENT_TO_REMOVE = "KEY_PARAM_SECTION_CONTENT_TO_REMOVE";
    private final String KEY_PARAM_CHART_TIMELINE_TO_REMOVE = "KEY_PARAM_CHART_TIMELINE_TO_REMOVE";
    
    //private final String KEY_PARAM_CHART_TIMELINE_TO_REMOVE_NEW = "KEY_PARAM_CHART_TIMELINE_TO_REMOVE_NEW";

    private final String KEY_PARAM_TOTAL_IMPULSES_REACHED = "KEY_PARAM_TOTAL_IMPULSES_REACHED";
    private final String KEY_PARAM_SECTION_CONTENT_TO_IMPULSE = "KEY_PARAM_SECTION_CONTENT_TO_IMPULSE";
    private final String KEY_PARAM_CHART_TIMELINE_TO_IMPULSE = "KEY_PARAM_CHART_TIMELINE_TO_IMPULSE";

    private final String KEY_PARAM_SECTION_KEYWORDS_BY_TRACKING_WORD = "KEY_PARAM_SECTION_KEYWORDS_BY_TRACKING_WORD";
    private final String KEY_PARAM_SECTION_KEYWORDS_BY_URL = "KEY_PARAM_SECTION_KEYWORDS_BY_URL";
    private final String KEY_PARAM_SECTION_KEYWORDS_BY_IMPULSE = "KEY_PARAM_SECTION_KEYWORDS_BY_IMPULSE";

    private final String KEY_PARAM_CHART_PIE_FEELING = "KEY_PARAM_CHART_PIE_FEELING";
    private final String KEY_PARAM_CHART_BAR_FEELING_BY_KEYWORDS = "KEY_PARAM_CHART_BAR_FEELING_BY_KEYWORDS";

    private final String KEY_PARAM_SECTION_NEW_CONTENT = "KEY_PARAM_SECTION_NEW_CONTENT";
    private final String KEY_PARAM_SECTION_NEGATIVE_CONTENT = "KEY_PARAM_SECTION_NEGATIVE_CONTENT";
    private final String KEY_PARAM_SECTION_RESULTS = "KEY_PARAM_SECTION_RESULTS";
    
    private final String KEY_PARAM_PIE_BAD = "KEY_PARAM_PIE_BAD";
    private final String KEY_PARAM_PIE_NEUTRAL = "KEY_PARAM_PIE_NEUTRAL";
    private final String KEY_PARAM_PIE_GOOD = "KEY_PARAM_PIE_GOOD";
    
    private final String KEY_PARAM_REFERENCE_LINK_LOGO = "KEY_PARAM_REFERENCE_LINK_LOGO";
    
    private final String KEY_PARAM_COMMENT = "KEY_PARAM_COMMENT";
    private final String KEY_PARAM_COMMENT_AUTHOR_NAME = "KEY_PARAM_COMMENT_AUTHOR_NAME";
    private final String KEY_PARAM_COMMENT_AUTHOR_PROFILE = "KEY_PARAM_COMMENT_AUTHOR_PROFILE";
    private final String KEY_PARAM_COMMENT_DATE = "KEY_PARAM_COMMENT_DATE";
    
    //private final String KEY_PARAM_QUERY_DATE = "KEY_PARAM_QUERY_DATE";
    

    private String destinataryName = "";
    private String languageNotification = ELanguages.SPANISH.getCode();
    
    private Scanner scanner;
    int totalContentRemoved = 0;
    int totalContentToRemove = 0;
    
    int total_threat_to_remove = 0;
    int total_content_removed = 0;
    
    float total_threats = 0;
    float total_threats_removed = 0;
    float total_progress_bar = 0;
    

    List<ChartElement> tempList = new ArrayList<ChartElement>();
    List<ChartElement> tempListado = new ArrayList<ChartElement>();
    String keyword = "";
    String section;
    String country;
    String concatSerie;
    
    float progressBar;
    
    public String lastDateScan = "";
    public LocalDateTime lastDateScanFormat;
    public String queryDate = "";

    public ReportScannerGenerator(Logger log, String jasperTemplate, ReportParams parameters) {
        super(log, jasperTemplate, parameters);
        this.setAsync(true);
        this.bundle = RemoveBundle.init(languageNotification);
        getLog().info(uuid + ": Report Scanner Generator Created... ");
    }

    public static ReportScannerGenerator init(String template, ReportParams parameters) {
        return new ReportScannerGenerator(LogManager.getLogger(ReportScannerGenerator.class), template, parameters);
    }

    @Override
    protected void organizeParams() {
        if (!this.getParameters().isNull("uuid_scanner"))
            this.getParameters().overrideParam("uuid_scanner", KEY_PARAM_FILTER_SCANNER_UUID);

        if (!this.getParameters().isNull("countries"))
            this.getParameters().overrideParam("countries", KEY_PARAM_FILTER_COUNTRIES);
        if (!this.getParameters().isNull("keywords"))
            this.getParameters().overrideParam("keywords", KEY_PARAM_FILTER_KEYWORDS);
        if (!this.getParameters().isNull("pages")) this.getParameters().overrideParam("pages", KEY_PARAM_FILTER_PAGES);
        if (!this.getParameters().isNull("trackingWords"))
            this.getParameters().overrideParam("trackingWords", KEY_PARAM_FILTER_TRACKING_WORDS);
        if (!this.getParameters().isNull("feelings"))
            this.getParameters().overrideParam("feelings", KEY_PARAM_FILTER_FEELINGS);
        if (!this.getParameters().isNull("feelingsNames"))
            this.getParameters().overrideParam("feelingsNames", KEY_PARAM_FILTER_FEELINGS_NAMES);
        if (!this.getParameters().isNull("urls")) this.getParameters().overrideParam("urls", KEY_PARAM_FILTER_URLS);
        if (!this.getParameters().isNull("section"))
            this.getParameters().overrideParam("section", KEY_PARAM_FILTER_SECTIONS);
        if (!this.getParameters().isNull("sectionName"))
            this.getParameters().overrideParam("sectionName", KEY_PARAM_FILTER_SECTIONS_NAMES);
        if (!this.getParameters().isNull("init_date"))
            this.getParameters().overrideParam("init_date", KEY_PARAM_FILTER_SCANNER_DATE_START);
        if (!this.getParameters().isNull("end_date"))
            this.getParameters().overrideParam("end_date", KEY_PARAM_FILTER_SCANNER_DATE_END);
        if (!this.getParameters().isNull("isNew"))
            this.getParameters().overrideParam("isNew", KEY_PARAM_FILTER_IS_NEW_CONTENT);
    }

    /**
     * Validate required parameters for report creation.
     */
    @Override
    protected boolean validateParams() {
        getLog().info(uuid + ": Validating Parameters...");
        return this.getParameters()
                .validateParams(KEY_PARAM_FILTER_USER_UUID,
                        KEY_PARAM_FILTER_USER_PROFILE,
                        KEY_PARAM_FILTER_SCANNER_UUID);
    }

    /**
     * Fill data for report generation (Load Scanner, Client and User Info)
     */
    @Override
    protected void fillDataParameters() {
        getLog().info(uuid + ": Filling Parameters Data...");
        try {
            scanner = loadScannerInfo(this.getParameters().extractParamAsString(KEY_PARAM_FILTER_SCANNER_UUID));
            String userName = loadUserNameInfo(this.getParameters().extractParamAsString(KEY_PARAM_FILTER_USER_UUID),
                    this.getParameters().extractParamAsString(KEY_PARAM_FILTER_USER_PROFILE));
            loadClientNameInfo(scanner.getId_owner());
            
            filterScannerData();
            fillJrParameters(userName, destinataryName);
            
            createListScannerComment(scanner.getId());
        } catch (RemoveApplicationException | Exception e) {
            getLog().error(uuid + ": Error building report: ", e);
        }

        getLog().info(uuid + ": Parameters Data Filled... ");
    }

    /**
     * Filter scanner information by filters defined.
     *
     * @param scanner
     */
    private void filterScannerData() {
        getLog().info(uuid + ": Filtering Scanner Data for scanner: " + scanner.getUuid());
        filterSections();
        filterKeywords();
        filterCountries();
        filterFeelings();
        filterTrackingWords();
        filterURLs();
        filterPages();
        filterNewContent();
        getLog().info(uuid + ": Scanner Data Filtered... ");
    }
    

    /**
     * Filter scanner data by Keywords filter parameter.
     *
     * @param scanner
     */
    private void filterKeywords() {
        getLog().info(uuid + ": Filtering Keywords: " + this.getParameters().extractParamAsStringArray(KEY_PARAM_FILTER_KEYWORDS));
        scanner.filterResultsByKeywords(this.getParameters().extractParamAsStringArray(KEY_PARAM_FILTER_KEYWORDS));
    }
    
    private void filterCountries() {
        getLog().info(uuid + ": Filtering Countries: " + this.getParameters().extractParamAsStringArray(KEY_PARAM_FILTER_COUNTRIES));
        scanner.filterResultsByCountries(this.getParameters().extractParamAsStringArray(KEY_PARAM_FILTER_COUNTRIES));
    }

    /**
     * Filter scanner Data by Page filter parameter
     *
     * @param scanner
     */
    private void filterPages() {
        getLog().info(uuid + ": Filtering Pages: (" + this.getParameters().extractParamIntArrayAsString(KEY_PARAM_FILTER_PAGES) + ") " + scanner.getResults().size());
        scanner.filterResultsByPage(this.getParameters().extractParamAsIntArray(KEY_PARAM_FILTER_PAGES));
    }

    /**
     * Filter scanner Data by Tracking Words filter parameter
     *
     * @param scanner
     */
    private void filterTrackingWords() {
        getLog().info(uuid + ": Filtering TrackingWords: (" + this.getParameters().extractParamAsStringArray(KEY_PARAM_FILTER_TRACKING_WORDS) + ") " + scanner.getResults().size());
        scanner.filterResultsByTrackingWords(this.getParameters().extractParamAsStringArray(KEY_PARAM_FILTER_TRACKING_WORDS));
    }

    /**
     * Filter scanner data y Feelings filter parameter
     *
     * @param scanner
     */
    private void filterFeelings() {
        getLog().info(uuid + ": Filtering Feelings: (" + this.getParameters().extractParamAsStringArray(KEY_PARAM_FILTER_FEELINGS) + ") " + scanner.getResults().size());
        scanner.filterResultsByFeelings(this.getParameters().extractParamAsStringArray(KEY_PARAM_FILTER_FEELINGS));
    }

    /**
     * filter scanner data by URLs filter parameter
     *
     * @param scanner
     */
    private void filterURLs() {
        getLog().info(uuid + ": Filtering URLs: (" + this.getParameters().extractParamAsStringArray(KEY_PARAM_FILTER_URLS) + ") " + scanner.getResults().size());
        scanner.filterResultsByURLs(this.getParameters().extractParamAsStringArray(KEY_PARAM_FILTER_URLS));
    }

    /**
     * filter scanner data by Sections filter parameter
     *
     * @param scanner
     */
    private void filterSections() {
        getLog().info(uuid + ": Filtering Sections: (" + this.getParameters().extractParamAsStringArray(KEY_PARAM_FILTER_SECTIONS) + ") : " + scanner.getResults().size());
        scanner.filterResultsBySections(this.getParameters().extractParamAsStringArray(KEY_PARAM_FILTER_SECTIONS));
    }

    /**
     * Filter scanner data by Dates filter parameter
     *
     * @param scanner
     */
    private void filterDates() {
        getLog().info(uuid + ": Filtering Dates: (" +
                this.getParameters().extractParamAsDate(KEY_PARAM_FILTER_SCANNER_DATE_START) + "/" + this.getParameters().extractParamAsDate(KEY_PARAM_FILTER_SCANNER_DATE_END) + ") " +
                scanner.getResults().size());
        scanner.filterResultsByDates(this.getParameters().extractParamAsDate(KEY_PARAM_FILTER_SCANNER_DATE_START),
                this.getParameters().extractParamAsDate(KEY_PARAM_FILTER_SCANNER_DATE_END));
    }

    /**
     * Filter scanner data by New Content filter parameters
     *
     * @param scanner
     */
    private void filterNewContent() {
        getLog().info(uuid + ": Filtering New Content: (" + this.getParameters().extractParamAsBoolean(KEY_PARAM_FILTER_IS_NEW_CONTENT) + ") " + scanner.getResults().size());
        if (this.getParameters().extractParamAsBoolean(KEY_PARAM_FILTER_IS_NEW_CONTENT)) {
            scanner.filterResultsByLastContent();
        } else {
            filterDates();
        }
    }

    /**
     * Fill all parameters into Jasper Report Map for .pdf generation
     *
     * @param scanner
     * @param userName
     * @param clientName
     * @throws RemoveApplicationException 
     */
    private void fillJrParameters(String userName, String clientName){
        getLog().info(uuid + ": Filling Data for Report Template... ");
        this.addJrParameter(KEY_PARAM_USER_NAME_GENERATOR, userName);
        this.addJrParameter(KEY_PARAM_CLIENT_NAME, clientName);
        this.addJrParameter(KEY_PARAM_CREATION_DATE, RemoveDateUtils.nowLocalDateString());
        //this.addJrParameter(KEY_PARAM_CREATION_DATE, RemoveDateUtils.nowLocalDateTimeString());

        fillJrFilterParameters();
        fillJrConfigParameters();

        fillJrSectionsParameters();

        getLog().info(uuid + ": Data for Report Template Filled... ");
    }

    /**
     * Fill Sections report into Jasper Report Map
     *
     * @param scanner
     * @throws RemoveApplicationException 
     */
    private void fillJrSectionsParameters(){
        getLog().info(uuid + ": Data for Report Scanner: " + scanner.getName() + " (" + scanner.getUuid() + ") - Total Results: " + scanner.getResults().size());
        List<TableRemoveImpulseRow> listContentToRemove = new ArrayList<TableRemoveImpulseRow>();
        List<ChartElement> listChartTimelineToRemove = new ArrayList<ChartElement>();
        List<TableRemoveImpulseRow> listContentToImpulse = new ArrayList<TableRemoveImpulseRow>();            // Content to Impulse Section
        List<ChartElement> listChartTimelineToImpulse = new ArrayList<ChartElement>();

        List<TableRow> listKeywordsByTW = new ArrayList<TableRow>();
        List<TableRow> listKeywordsByURL = new ArrayList<TableRow>();
        List<TableRow> listKeywordsByImpulse = new ArrayList<TableRow>();

        List<TableRow> listNew = new ArrayList<TableRow>();                        // New Content List
        List<TableRow> listNegative = new ArrayList<TableRow>();                    // Negative Content List

        List<TableResults> listResults = new ArrayList<TableResults>();

        FeelingCounter feelingCounter = new FeelingCounter();
        KeywordFeelingCounter keywordFeelingCounter = new KeywordFeelingCounter();
        
        float progressAcum = 0;
        long progresCount = 0;

        //TableChartElementResults chartResult = new TableChartElementResults();
        
        //List<TableChartElementResults> listTimeLineToRemoveNew = new ArrayList<TableChartElementResults>();

        if (scanner.getResults() != null && !scanner.getResults().isEmpty()) {
            // Reference Parameters
            LocalDate filterDate = this.getParameters().extractParamAsDate(KEY_PARAM_FILTER_SCANNER_DATE_START);
            LocalDate firstScan = scanner.getCreation_date().toLocalDate();
            LocalDate referenceFirstDate = filterDate != null ? filterDate : firstScan.isBefore(LocalDate.now().minusMonths(1)) ? LocalDate.now().minusMonths(1) : firstScan;
            LocalDate lastDate = this.getParameters().extractParamAsDate(KEY_PARAM_FILTER_SCANNER_DATE_END) != null ?
                    this.getParameters().extractParamAsDate(KEY_PARAM_FILTER_SCANNER_DATE_END) : scanner.getResults().get(0).getQuery_date().toLocalDate();
            
            // Start of Results Iterations
            for (ScannerResult result : scanner.getResults()) {
                TableResults resultTable = new TableResults(result, bundle, lastDate,lastDate);
                
                
                getLog().info(uuid + ": Filling data for Report Result: " + result.getUuid());
                result.getSnippetsImages().forEach(snippet -> {
                    feelingCounter.incByFeeling(snippet.getFeeling());
                    keywordFeelingCounter.inc(result.getKeyword().getWord(), snippet.getFeeling());

                    // Check new content for respective table
                    if (!snippet.getFeeling().equalsIgnoreCase(EFeelings.NOT_APPLIED.getTag())
                            && this.isNewContent(snippet.getTracking().get(0).getDate_scan(), referenceFirstDate)) {
                        getLog().info(uuid + ": Pushing snippet to new content: " + snippet.getId());
                        pushNewNegativeContent(listNew, result, snippet, lastDate);
                    }

                    // Check negative content for respective table
                    if (snippet.getFeeling().equalsIgnoreCase(EFeelings.BAD.getTag())) {
                        getLog().info(uuid + ": Pushing snippet to negative content: " + snippet.getId());
                        pushNewNegativeContent(listNegative, result, snippet, lastDate);
                    }

                    // Checking TrackingWords against Snippets
                    scanner.getTrackingWords().forEach(trackingWord -> {
                        if (trackingWord.esURL()) {
                            if (snippet.getLink().equals(trackingWord.getWord())) {
                                getLog().info(uuid + ": Pushing snippet to Remove Content: " + snippet.getId());
                                boolean targetReached =  verifyTargetReachedForTransform(scanner.getRestrictions(), result.getQuery_date(),
                                        snippet.lastTracking().getDate_scan(), snippet.lastTracking().getPage());
                                
                                if (targetReached) totalContentRemoved++; else  totalContentToRemove++;
                                
                                pushRemoveImpulse(listContentToRemove, result, snippet, targetReached);
                                pushInChartTimeline(result, listChartTimelineToRemove, snippet);

                                getLog().info(uuid + ": Pushing snippet to Keyword by Url Content: " + snippet.getId());
                                pushKeywordByContent(listKeywordsByURL, result, snippet);
                            }
                        }
                    });

                    if (scanner.getTrackingWords() != null && !scanner.getTrackingWords().isEmpty() &&
                            snippet.containsTrackingWord(scanner.justTrackingWordsTitles().toArray(new String[scanner.justTrackingWordsTitles().size()]))) {
                        getLog().info(uuid + ": Pushing snippet to Keyword by TrackingWord Content: " + snippet.getId());
                        pushKeywordByContent(listKeywordsByTW, result, snippet);
                    }

                    // Checking Impulses against Snippets
                    scanner.getImpulses().forEach(impulse -> {
                        if (snippet.getLink().equals(impulse.getReal_publish_link())) {
                            getLog().info(uuid + ": Pushing snippet to Content to Impulse: " + snippet.getId());
                            pushRemoveImpulse(listContentToImpulse, result, snippet, impulse.isTarget_reached());

                            if (impulse.isTarget_reached()) {
                            	pushInChartTimeline(result, listChartTimelineToImpulse, snippet);
                            }

                            getLog().info(uuid + ": Pushing snippet to Keyword by Impulse Content: " + snippet.getId());
                            pushKeywordByContent(listKeywordsByImpulse, result, snippet);

                        }
                    });
                    
                    resultTable.pushResult(snippet);
                });
                result.getSnippetsNews().forEach(snippet -> {
                    feelingCounter.incByFeeling(snippet.getFeeling());
                    keywordFeelingCounter.inc(result.getKeyword().getWord(), snippet.getFeeling());

                    // Check new content for respective table
                    if (!snippet.getFeeling().equalsIgnoreCase(EFeelings.NOT_APPLIED.getTag())
                            && this.isNewContent(snippet.getTracking().get(0).getDate_scan(), referenceFirstDate)) {
                        getLog().info(uuid + ": Pushing snippet to new content: " + snippet.getId());
                        pushNewNegativeContent(listNew, result, snippet, lastDate);
                    }

                    // Check negative content for respective table
                    if (snippet.getFeeling().equalsIgnoreCase(EFeelings.BAD.getTag())) {
                        getLog().info(uuid + ": Pushing snippet to negative content: " + snippet.getId());
                        pushNewNegativeContent(listNegative, result, snippet, lastDate);
                    }

                    // Checking TrackingWords against Snippets
                    scanner.getTrackingWords().forEach(trackingWord -> {
                        if (trackingWord.esURL()) {
                            if (snippet.getLink().equals(trackingWord.getWord())) {
                                getLog().info(uuid + ": Pushing snippet to Remove Content: " + snippet.getId());
                                
                                boolean targetReached =  verifyTargetReachedForTransform(scanner.getRestrictions(), result.getQuery_date(),
                                        snippet.lastTracking().getDate_scan(), snippet.lastTracking().getPage());
                                
                                if (targetReached) totalContentRemoved++; else  totalContentToRemove++;
                                
                                pushRemoveImpulse(listContentToRemove, result, snippet, targetReached);
                                pushInChartTimeline(result, listChartTimelineToRemove, snippet);

                                getLog().info(uuid + ": Pushing snippet to Keyword by Url Content: " + snippet.getId());
                                pushKeywordByContent(listKeywordsByURL, result, snippet);
                            }
                        }
                    });

                    if (scanner.getTrackingWords() != null && !scanner.getTrackingWords().isEmpty() &&
                            snippet.containsTrackingWord(scanner.justTrackingWordsTitles().toArray(new String[scanner.justTrackingWordsTitles().size()]))) {
                        getLog().info(uuid + ": Pushing snippet to Keyword by TrackingWord Content: " + snippet.getId());
                        pushKeywordByContent(listKeywordsByTW, result, snippet);
                    }

                    // Checking Impulses against Snippets
                    scanner.getImpulses().forEach(impulse -> {
                        if (snippet.getLink().equals(impulse.getReal_publish_link())) {
                            getLog().info(uuid + ": Pushing snippet to Content to Impulse: " + snippet.getId());
                            pushRemoveImpulse(listContentToImpulse, result, snippet, impulse.isTarget_reached());

                            if (impulse.isTarget_reached()) {
                            	pushInChartTimeline(result, listChartTimelineToImpulse, snippet);
                            }

                            getLog().info(uuid + ": Pushing snippet to Keyword by Impulse Content: " + snippet.getId());
                            pushKeywordByContent(listKeywordsByImpulse, result, snippet);
                        }
                    });
                    
                    resultTable.pushResult(snippet);
                });
                result.getSnippetsWebs().forEach(snippet -> {
                    feelingCounter.incByFeeling(snippet.getFeeling());
                    keywordFeelingCounter.inc(result.getKeyword().getWord(), snippet.getFeeling());

                    // Check new content for respective table
                    if (!snippet.getFeeling().equalsIgnoreCase(EFeelings.NOT_APPLIED.getTag())
                            && this.isNewContent(snippet.getTracking().get(0).getDate_scan(), referenceFirstDate)) {
                        getLog().info(uuid + ": Pushing snippet to new content: " + snippet.getId());
                        pushNewNegativeContent(listNew, result, snippet, lastDate);
                    }

                    // Check negative content for respective table
                    if (snippet.getFeeling().equalsIgnoreCase(EFeelings.BAD.getTag())) {
                        getLog().info(uuid + ": Pushing snippet to negative content: " + snippet.getId());
                        pushNewNegativeContent(listNegative, result, snippet, lastDate);
                    }

                    // Checking TrackingWords against Snippets
                    scanner.getTrackingWords().forEach(trackingWord -> {
                        if (trackingWord.esURL()) {
                            if (snippet.getLink().equals(trackingWord.getWord())) {
                                getLog().info(uuid + ": Pushing snippet to Remove Content: " + snippet.getId());
                                
                                boolean targetReached =  verifyTargetReachedForTransform(scanner.getRestrictions(), result.getQuery_date(),
                                        snippet.lastTracking().getDate_scan(), snippet.lastTracking().getPage());
                                
                                if (targetReached) totalContentRemoved++; else  totalContentToRemove++;
                                
                                pushRemoveImpulse(listContentToRemove, result, snippet, targetReached);
                                pushInChartTimeline(result, listChartTimelineToRemove, snippet);
                                //chartResult.pushChartWebElement(result, snippet);
                                
                                getLog().info(uuid + ": Pushing snippet to Keyword by Url Content: " + snippet.getId());
                                pushKeywordByContent(listKeywordsByURL, result, snippet);

                            }
                        } else if (trackingWord.esWord()) {

                        }
                    });
                         

                    if (scanner.getTrackingWords() != null && !scanner.getTrackingWords().isEmpty() &&
                            snippet.containsTrackingWord(scanner.justTrackingWordsTitles().toArray(new String[scanner.justTrackingWordsTitles().size()]))) {
                        getLog().info(uuid + ": Pushing snippet to Keyword by TrackingWord Content: " + snippet.getId());
                        pushKeywordByContent(listKeywordsByTW, result, snippet);
                    }

                    // Checking Impulses against Snippets
                    scanner.getImpulses().forEach(impulse -> {
                        if (snippet.getLink().equals(impulse.getReal_publish_link())) {
                            getLog().info(uuid + ": Pushing snippet to Content to Impulse: " + snippet.getId());
                            pushRemoveImpulse(listContentToImpulse, result, snippet, impulse.isTarget_reached());

                            if (impulse.isTarget_reached()) {
                            	pushInChartTimeline(result, listChartTimelineToImpulse, snippet);
                            }

                            getLog().info(uuid + ": Pushing snippet to Keyword by Impulse Content: " + snippet.getId());
                            pushKeywordByContent(listKeywordsByImpulse, result, snippet);
                        }
                    });
                    
                    resultTable.pushResult(snippet);
                });
                resultTable.createDataSource();
                listResults.add(resultTable);

                progresCount++;
                progressAcum += result.getProgress();
            }
        }

        /*for(int i=0;i<chartResult.getResults().size();i++) {
        	
        	String[] parts = listChartTimelineToRemove.get(i).getSerie().split("-");
        	keyword = parts[0];
        	section = parts[1];
        	country = parts[2];
        	concatSerie = keyword +" - "+section+" - "+country;

        	tempList.add(new ChartElement(listChartTimelineToRemove.get(i).getLabel(),listChartTimelineToRemove.get(i).getSerie(),listChartTimelineToRemove.get(i).getCategory(),listChartTimelineToRemove.get(i).getValue()));
        	
    		String[] partList = tempList.get(0).getSerie().split("-");
    		String keywordList = partList[0];
    		String sectionList = partList[1];
    		String countryList = partList[2];
    		String concatSerieList = keywordList +" - "+sectionList+" - "+countryList;
    		
        	if(concatSerie.equalsIgnoreCase(concatSerieList)) {
        		tempListado.add(new ChartElement(listChartTimelineToRemove.get(i).getLabel(),listChartTimelineToRemove.get(i).getSerie(),listChartTimelineToRemove.get(i).getCategory(),listChartTimelineToRemove.get(i).getValue()));
        	}else {
        		chartResult.getTimeLineList().add(tempListado);
        		tempList = new ArrayList<ChartElement>();
        		tempListado = new ArrayList<ChartElement>();
        	}
        }
        
        chartResult.getTimeLineList().add(tempListado);
		tempList = new ArrayList<ChartElement>();
		tempListado = new ArrayList<ChartElement>();
		chartResult.createDataSource();
		
		listTimeLineToRemoveNew.add(chartResult);*/
        
        total_threat_to_remove = this.scanner.getConfiguration().getCountries().size()* 
    			this.scanner.getConfiguration().getSearch_type().split(",").length * 
    			this.scanner.getKeywords().size() * 
    			this.scanner.getJustTrackingURLs().size() - listContentToRemove.size();

        total_content_removed = total_threat_to_remove + totalContentRemoved;
                
        /*progressBar = progressAcum / (float) progresCount;
        progressBar = (float) Math.ceil(progressBar);
        this.addJrParameter(KEY_PARAM_PROGRESS_BAR, progresCount > 0 ? progressBar  : 0);
        */
        
        total_threats = this.scanner.getConfiguration().getCountries().size()* 
    			this.scanner.getConfiguration().getSearch_type().split(",").length * 
    			this.scanner.getKeywords().size() * 
    			this.scanner.getJustTrackingURLs().size();
        total_threats_removed = total_content_removed;

        total_progress_bar = (float) Math.ceil((total_threats_removed * 100)/total_threats);
        
        this.addJrParameter(KEY_PARAM_PROGRESS_BAR, total_progress_bar);
        this.addJrParameter(KEY_PARAM_TOTAL_THREATS_TO_REMOVE, 
    			this.scanner.getConfiguration().getCountries().size()* 
    			this.scanner.getConfiguration().getSearch_type().split(",").length * 
    			this.scanner.getKeywords().size() * 
    			this.scanner.getJustTrackingURLs().size());
        this.addJrParameter(KEY_PARAM_TOTAL_THREATS_REMOVED, total_content_removed);
        this.addJrParameter(KEY_PARAM_TOTAL_THREATS_IN_PROGRESS, totalContentToRemove);
        
        
        getLog().info(uuid + ": Total Impulse to remove chart: " + listContentToRemove.size());
        this.addJrParameterCollection(KEY_PARAM_SECTION_CONTENT_TO_REMOVE, listContentToRemove);
        this.addJrParameterCollection(KEY_PARAM_CHART_TIMELINE_TO_REMOVE, listChartTimelineToRemove);


        //this.addJrParameterCollection(KEY_PARAM_CHART_TIMELINE_TO_REMOVE_NEW, listTimeLineToRemoveNew);
        
        getLog().info(uuid + ": Total Impulse to Impulse chart: " + listContentToImpulse.size());
        
        this.addJrParameter(KEY_PARAM_TOTAL_IMPULSES_REACHED, countTotalImpulsesReached(listContentToImpulse));
        this.addJrParameterCollection(KEY_PARAM_SECTION_CONTENT_TO_IMPULSE, listContentToImpulse);
        this.addJrParameterCollection(KEY_PARAM_CHART_TIMELINE_TO_IMPULSE, listChartTimelineToImpulse);

        this.addJrParameterCollection(KEY_PARAM_SECTION_NEW_CONTENT, listNew);
        this.addJrParameterCollection(KEY_PARAM_SECTION_NEGATIVE_CONTENT, listNegative);

        this.addJrParameterCollection(KEY_PARAM_SECTION_KEYWORDS_BY_TRACKING_WORD, listKeywordsByTW);
        this.addJrParameterCollection(KEY_PARAM_SECTION_KEYWORDS_BY_URL, listKeywordsByURL);
        this.addJrParameterCollection(KEY_PARAM_SECTION_KEYWORDS_BY_IMPULSE, listKeywordsByImpulse);

        this.addJrParameterCollection(KEY_PARAM_CHART_PIE_FEELING, createListFeelingPieData(feelingCounter));
        this.addJrParameterCollection(KEY_PARAM_CHART_BAR_FEELING_BY_KEYWORDS, createListChartStackedBarFeelingsByKeyword(keywordFeelingCounter));

        this.addJrParameterCollection(KEY_PARAM_SECTION_RESULTS, listResults);
        
        this.addJrParameter(KEY_PARAM_PIE_BAD, feelingCounter.getBad());
        this.addJrParameter(KEY_PARAM_PIE_NEUTRAL, feelingCounter.getNeutral());
        this.addJrParameter(KEY_PARAM_PIE_GOOD, feelingCounter.getGood());
        
        //this.addJrParameter(KEY_PARAM_QUERY_DATE, RemoveDateUtils.formatDateStringDash(scanner.getResults().get(0).getQuery_date()));

    }

    private Integer countTotalImpulsesReached(List<TableRemoveImpulseRow> listContentToImpulse) {
        int total = 0;

        if (listContentToImpulse != null) {
            for (TableRemoveImpulseRow row : listContentToImpulse) {
                if (row.isTargetReached()) {
                    total++;
                }
            }
        }
        getLog().info("Total impulses: " + total);
        return total;
    }
    
    private String lastDateScan(Long id) throws RemoveApplicationException {
    	ScannerResult result = null;
    	
    	result = ScannerResultDao.init().getLastDateExecute(id);
    	lastDateScan = RemoveDateUtils.formatDateStringDash(result.getQuery_date());
    	
    	return lastDateScan;
    }
    
    private LocalDateTime lastDateScanNoFormat(Long id) throws RemoveApplicationException {
    	ScannerResult result = null;
    	
    	result = ScannerResultDao.init().getLastDateExecute(id);
    	lastDateScanFormat = result.getQuery_date();
    	
    	return lastDateScanFormat;
    }
    
    
	private void createListScannerComment(Long id) throws RemoveApplicationException{
    	List<ScannerComment> list = new ArrayList<ScannerComment>();
    	ScannerComment comment = null;
    	
    	list =  ScannerCommentDao.init().list(id);
    	
    	
    	
    	if(list == null || list.size() == 0) {
    		
    	}else {
    		
    		comment = (ScannerComment) list.get(0);
        	this.addJrParameter(KEY_PARAM_COMMENT, comment.getComment());
        	this.addJrParameter(KEY_PARAM_COMMENT_AUTHOR_NAME, comment.getAuthor_name());
        	this.addJrParameter(KEY_PARAM_COMMENT_AUTHOR_PROFILE, comment.getProfile());
        	this.addJrParameter(KEY_PARAM_COMMENT_DATE, RemoveDateUtils.formatDateStringDash(comment.getComment_date()));
    	}

    }

    private List<ChartElement> createListChartStackedBarFeelingsByKeyword(KeywordFeelingCounter keywordFeelingCounter) {
        List<ChartElement> list = new ArrayList<ChartElement>();

        if (keywordFeelingCounter.getMap() != null && !keywordFeelingCounter.getMap().isEmpty()) {
            for (String key : keywordFeelingCounter.getMap().keySet()) {
                FeelingCounter counter = keywordFeelingCounter.getMap().get(key);

                if (counter.notEmpty()) {
                    list.add(new ChartElement(key, bundle.getLabelBundle("feeling." + EFeelings.BAD.getTag()), key, counter.getBad()));
                    list.add(new ChartElement(key, bundle.getLabelBundle("feeling." + EFeelings.NEUTRAL.getTag()), key, counter.getNeutral()));
                    list.add(new ChartElement(key, bundle.getLabelBundle("feeling." + EFeelings.GOOD.getTag()), key, counter.getGood()));
                }
            }
        }

        return list;
    }
    

    private List<ChartPieElement> createListFeelingPieData(FeelingCounter feelingCounter) {
        List<ChartPieElement> listChartPieFeelings = new ArrayList<ChartPieElement>();

//        if (feelingCounter.getBad() > 0) {
        listChartPieFeelings.add(new ChartPieElement(EFeelings.BAD.getTag(), bundle.getLabelBundle("feeling." + EFeelings.BAD.getTag()), feelingCounter.getBad()));
//        }
//        if (feelingCounter.getNeutral() > 0) {
        listChartPieFeelings.add(new ChartPieElement(EFeelings.NEUTRAL.getTag(), bundle.getLabelBundle("feeling." + EFeelings.NEUTRAL.getTag()), feelingCounter.getNeutral()));
//        }
//        if (feelingCounter.getGood() > 0) {
        listChartPieFeelings.add(new ChartPieElement(EFeelings.GOOD.getTag(), bundle.getLabelBundle("feeling." + EFeelings.GOOD.getTag()), feelingCounter.getGood()));
//        }

        return listChartPieFeelings;
    }

    /**
     * Push element into list section for sections Keywords by ***** in Jasper Report for Web Snippet.
     *
     * @param list
     * @param result
     * @param snippet
     */
    private void pushKeywordByContent(List<TableRow> list, ScannerResult result,
                                      ScannerResultWebSnippet snippet) {
        ScannerResultWebSnippetTrack lastTrack = snippet.getTracking().get(snippet.getTracking().size() - 1);
        ScannerResultWebSnippetTrack firstTrack = snippet.getTracking().get(0);

        TableRow row = new TableRow();

        row.setKeyword(result.getKeyword().getWord());
        row.setUrl(snippet.getLink());
        row.setTrackingWordOrUrl(snippet.trackingWordsTitles());
        row.setTitle(snippet.getTitle());
        row.setDescription(snippet.getSnippet());
        row.setPagePos(lastTrack.getPage() + " / " + lastTrack.getPosition_in_page());
        row.setRanking(lastTrack.getPosition());
        row.setEvolution(calculateEvolution(firstTrack.getPosition(), lastTrack.getPosition()));
        row.setVisibility(bundle.getLabelBundle(visibilityKey(lastTrack.getPosition())));
        //row.setDateScan(RemoveDateUtils.formatDateTimeDash(lastTrack.getDate_scan()));
        row.setDateScan(RemoveDateUtils.formatDateStringDash(lastTrack.getDate_scan()).trim());
        row.setCountry(bundle.getLabelCountryBundle(result.getCountry().getTag()));
        row.setSection(bundle.getLabelBundle("scanner.section." + result.getSearch_type()));
        row.setSectionCode(result.getSearch_type());

        row.setFeeling(snippet.getFeeling());
        try {
			row.setLastDateScan(RemoveDateUtils.formatDateStringDash(lastDateScanNoFormat(scanner.getId())).trim());
		} catch (RemoveApplicationException e) {
		}
        row.setMonitor_page(this.scanner.getConfiguration().getPages());
        row.setScanType(scanner.getType());
        row.addTrackingWords(snippet.getTrackingWords());
        row.prepareDS();

        list.add(row);
    }

    /**
     * Push element into list section for sections Keywords by ***** in Jasper Report for News Snippet.
     *
     * @param list
     * @param result
     * @param snippet
     */
    private void pushKeywordByContent(List<TableRow> list, ScannerResult result,
                                      ScannerResultNewsSnippet snippet) {
        ScannerResultNewsSnippetTrack lastTrack = snippet.getTracking().get(snippet.getTracking().size() - 1);
        ScannerResultNewsSnippetTrack firstTrack = snippet.getTracking().get(0);

        TableRow row = new TableRow();

        row.setKeyword(result.getKeyword().getWord());
        row.setUrl(snippet.getLink());
        row.setTrackingWordOrUrl(snippet.trackingWordsTitles());
        row.setTitle(snippet.getTitle());
        row.setDescription(snippet.getSnippet());
        row.setPagePos(lastTrack.getPage() + " / " + lastTrack.getPosition_in_page());
        row.setRanking(lastTrack.getPosition());
        row.setEvolution(calculateEvolution(firstTrack.getPosition(), lastTrack.getPosition()));
        row.setVisibility(bundle.getLabelBundle(visibilityKey(lastTrack.getPosition())));
        //row.setDateScan(RemoveDateUtils.formatDateTimeDash(lastTrack.getDate_scan()));
        row.setDateScan(RemoveDateUtils.formatDateStringDash(lastTrack.getDate_scan()).trim());
        row.setCountry(bundle.getLabelCountryBundle(result.getCountry().getTag()));
        row.setSection(bundle.getLabelBundle("scanner.section." + result.getSearch_type()));
        row.setSectionCode(result.getSearch_type());

        row.setFeeling(snippet.getFeeling());
        try {
			row.setLastDateScan(RemoveDateUtils.formatDateStringDash(lastDateScanNoFormat(scanner.getId())).trim());
		} catch (RemoveApplicationException e) {
		}
        row.setMonitor_page(this.scanner.getConfiguration().getPages());
        row.setScanType(scanner.getType());
        row.addTrackingWords(snippet.getTrackingWords());
        row.prepareDS();

        list.add(row);
    }

    /**
     * Push element into list section for sections Keywords by ***** in Jasper Report for Image Snippet.
     *
     * @param list
     * @param result
     * @param snippet
     */
    private void pushKeywordByContent(List<TableRow> list, ScannerResult result,
                                      ScannerResultImageSnippet snippet) {
        ScannerResultImageSnippetTrack lastTrack = snippet.getTracking().get(snippet.getTracking().size() - 1);
        ScannerResultImageSnippetTrack firstTrack = snippet.getTracking().get(0);

        TableRow row = new TableRow();

        row.setKeyword(result.getKeyword().getWord());
        row.setUrl(snippet.getLink());
        row.setTrackingWordOrUrl(snippet.trackingWordsTitles());
        row.setTitle(snippet.getTitle());
        row.setDescription(snippet.getDescription());
        row.setPagePos(lastTrack.getPage() + " / " + lastTrack.getPosition_in_page());
        row.setRanking(lastTrack.getPosition());
        row.setEvolution(calculateEvolution(firstTrack.getPosition(), lastTrack.getPosition()));
        row.setVisibility(bundle.getLabelBundle(visibilityKey(lastTrack.getPosition())));
        //row.setDateScan(RemoveDateUtils.formatDateTimeDash(lastTrack.getDate_scan()));
        row.setDateScan(RemoveDateUtils.formatDateStringDash(lastTrack.getDate_scan()).trim());
        row.setCountry(bundle.getLabelCountryBundle(result.getCountry().getTag()));
        row.setSection(bundle.getLabelBundle("scanner.section." + result.getSearch_type()));
        row.setSectionCode(result.getSearch_type());

        row.setFeeling(snippet.getFeeling());
        try {
			row.setLastDateScan(RemoveDateUtils.formatDateStringDash(lastDateScanNoFormat(scanner.getId())).trim());
		} catch (RemoveApplicationException e) {
		}
        row.setMonitor_page(this.scanner.getConfiguration().getPages());
        row.setScanType(scanner.getType());
        row.addTrackingWords(snippet.getTrackingWords());
        row.prepareDS();


        list.add(row);
    }

    /**
     * Verify if target was reached in content to remove sections
     *
     * @param restrictions
     * @param lastScanDate
     * @param lastTrackDate
     * @param page
     * @return
     */
    private boolean verifyTargetReachedForTransform(ClientPlan restrictions, LocalDateTime lastScanDate,
                                                    LocalDateTime lastTrackDate, Integer page) {
        try {
            return lastScanDate.toLocalDate().isEqual(lastTrackDate.toLocalDate()) ? page.intValue() > restrictions.getDetail().getTarget_page().intValue() : true;
        } catch (Exception e) {
            getLog().error(uuid + ": Error: ", e);
        }
        return false;
    }

    /**
     * Push element into content to Remove/Impulse section for report generation for Image snippets.
     *
     * @param list
     * @param result
     * @param snippet
     * @param targetReached
     */
    private void pushRemoveImpulse(List<TableRemoveImpulseRow> list, ScannerResult result,
                                   ScannerResultImageSnippet snippet, boolean targetReached) {
        ScannerResultImageSnippetTrack lastTrack = snippet.getTracking().get(snippet.getTracking().size() - 1);
        ScannerResultImageSnippetTrack firstTrack = snippet.getTracking().get(0);

        TableRemoveImpulseRow row = new TableRemoveImpulseRow();

        row.setKeyword(result.getKeyword().getWord());
        row.setUrl(snippet.getLink());
        row.setTrackingWordOrUrl(snippet.trackingWordsTitles());
        row.setTitle(snippet.getTitle());
        row.setDescription(snippet.getDescription());
        row.setPagePos(lastTrack.getPage() + " / " + lastTrack.getPosition_in_page());
        row.setRanking(lastTrack.getPosition());
        row.setEvolution(calculateEvolution(firstTrack.getPosition(), lastTrack.getPosition()));
        row.setCountry(bundle.getLabelCountryBundle(result.getCountry().getTag()));
        row.setSection(bundle.getLabelBundle("scanner.section." + result.getSearch_type()));
        row.setSectionCode(result.getSearch_type());
        //row.setDateScan(RemoveDateUtils.formatDateTimeDash(lastTrack.getDate_scan()));
        row.setDateScan(RemoveDateUtils.formatDateStringDash(lastTrack.getDate_scan()).trim());
        row.setTargetReached(targetReached);

        if(this.scanner.getRestrictions().getDetail().getTarget_page() != null) {
        	row.setTarget_page(this.scanner.getRestrictions().getDetail().getTarget_page());
        }
        row.setMonitor_page(this.scanner.getConfiguration().getPages());
        row.setPosition_in_page(lastTrack.getPosition_in_page());
        
        row.setFeeling(snippet.getFeeling());
        try {
			row.setLastDateScan(RemoveDateUtils.formatDateStringDash(lastDateScanNoFormat(scanner.getId())).trim());
		} catch (RemoveApplicationException e) {
		}
        row.setScanType(scanner.getType());
        row.addTrackingWords(snippet.getTrackingWords());
        row.prepareDS();

        list.add(row);
    }

    /**
     * Push element into content to Remove/Impulse section for report generation for News snippets.
     *
     * @param list
     * @param result
     * @param snippet
     * @param targetReached
     */
    private void pushRemoveImpulse(List<TableRemoveImpulseRow> list, ScannerResult result,
                                   ScannerResultNewsSnippet snippet, boolean targetReached) {
        ScannerResultNewsSnippetTrack lastTrack = snippet.getTracking().get(snippet.getTracking().size() - 1);
        ScannerResultNewsSnippetTrack firstTrack = snippet.getTracking().get(0);

        TableRemoveImpulseRow row = new TableRemoveImpulseRow();

        row.setKeyword(result.getKeyword().getWord());
        row.setUrl(snippet.getLink());
        row.setTrackingWordOrUrl(snippet.trackingWordsTitles());
        row.setTitle(snippet.getTitle());
        row.setDescription(snippet.getSnippet());
        row.setPagePos(lastTrack.getPage() + " / " + lastTrack.getPosition_in_page());
        row.setRanking(lastTrack.getPosition());
        row.setEvolution(calculateEvolution(firstTrack.getPosition(), lastTrack.getPosition()));
        row.setCountry(bundle.getLabelCountryBundle(result.getCountry().getTag()));
        row.setSection(bundle.getLabelBundle("scanner.section." + result.getSearch_type()));
        row.setSectionCode(result.getSearch_type());
        //row.setDateScan(RemoveDateUtils.formatDateTimeDash(lastTrack.getDate_scan()));
        row.setDateScan(RemoveDateUtils.formatDateStringDash(lastTrack.getDate_scan()).trim());
        row.setTargetReached(targetReached);
        
        if(this.scanner.getRestrictions().getDetail().getTarget_page() != null) {
        	row.setTarget_page(this.scanner.getRestrictions().getDetail().getTarget_page());
        }
        
        row.setMonitor_page(this.scanner.getConfiguration().getPages());
        row.setPosition_in_page(lastTrack.getPosition_in_page());
        row.setFeeling(snippet.getFeeling());
        try {
			row.setLastDateScan(RemoveDateUtils.formatDateStringDash(lastDateScanNoFormat(scanner.getId())).trim());
		} catch (RemoveApplicationException e) {
		}
        row.setScanType(scanner.getType());
        row.addTrackingWords(snippet.getTrackingWords());
        row.prepareDS();

        list.add(row);
    }

    /**
     * Push element into content to Remove/Impulse section for report generation for Web snippets.
     *
     * @param list
     * @param result
     * @param snippet
     * @param targetReached
     */
    private void pushRemoveImpulse(List<TableRemoveImpulseRow> list, ScannerResult result,
                                   ScannerResultWebSnippet snippet, boolean targetReached) {
        ScannerResultWebSnippetTrack lastTrack = snippet.getTracking().get(snippet.getTracking().size() - 1);
        ScannerResultWebSnippetTrack firstTrack = snippet.getTracking().get(0);

        TableRemoveImpulseRow row = new TableRemoveImpulseRow();

        row.setKeyword(result.getKeyword().getWord());
        row.setUrl(snippet.getLink());
        row.setTrackingWordOrUrl(snippet.trackingWordsTitles());
        row.setTitle(snippet.getTitle());
        row.setDescription(snippet.getSnippet());
        row.setPagePos(lastTrack.getPage() + " / " + lastTrack.getPosition_in_page());
        row.setRanking(lastTrack.getPosition());
        row.setEvolution(calculateEvolution(firstTrack.getPosition(), lastTrack.getPosition()));
        row.setCountry(bundle.getLabelCountryBundle(result.getCountry().getTag()));
        row.setSection(bundle.getLabelBundle("scanner.section." + result.getSearch_type()));
        row.setSectionCode(result.getSearch_type());
        //row.setDateScan(RemoveDateUtils.formatDateTimeDash(lastTrack.getDate_scan()));
        row.setDateScan(RemoveDateUtils.formatDateStringDash(lastTrack.getDate_scan()).trim());
        row.setTargetReached(targetReached);
        
        if(this.scanner.getRestrictions().getDetail().getTarget_page() != null) {
        	row.setTarget_page(this.scanner.getRestrictions().getDetail().getTarget_page());
        }
        
        row.setMonitor_page(this.scanner.getConfiguration().getPages());
        row.setPosition_in_page(lastTrack.getPosition_in_page());
        
        row.setFeeling(snippet.getFeeling());
        try {
			row.setLastDateScan(RemoveDateUtils.formatDateStringDash(lastDateScanNoFormat(scanner.getId())).trim());
		} catch (RemoveApplicationException e) {
		}
        row.setScanType(scanner.getType());
        row.addTrackingWords(snippet.getTrackingWords());
        row.prepareDS();

        list.add(row);
    }

    /**
     * Push element into New/Negative Content section for report generation for News snippets.
     *
     * @param list
     * @param result
     * @param snippet
     * @param lastDate
     */
    private void pushNewNegativeContent(List<TableRow> list, ScannerResult result, ScannerResultNewsSnippet snippet,
                                        LocalDate lastDate) {
        ScannerResultNewsSnippetTrack lastTrack = snippet.getTracking().get(snippet.getTracking().size() - 1);
        ScannerResultNewsSnippetTrack firstTrack = snippet.getTracking().get(0);

        TableRow row = new TableRow();

        row.setKeyword(result.getKeyword().getWord());
        row.setUrl(snippet.getLink());
        row.setTrackingWordOrUrl(snippet.trackingWordsTitles());
        row.setTitle(snippet.getTitle());
        row.setDescription(snippet.getSnippet());
        row.setPagePos(lastTrack.getPage() + " / " + lastTrack.getPosition_in_page());
        row.setRanking(lastTrack.getPosition());
        row.setEvolution(calculateEvolution(firstTrack.getPosition(), lastTrack.getPosition()));
        row.setVisibility(bundle.getLabelBundle(visibilityKey(lastTrack.getPosition())));
        //row.setDateScan(RemoveDateUtils.formatDateTimeDash(lastTrack.getDate_scan()));
        row.setDateScan(RemoveDateUtils.formatDateStringDash(lastTrack.getDate_scan()).trim());
        row.setCountry(bundle.getLabelCountryBundle(result.getCountry().getTag()));
        row.setSection(bundle.getLabelBundle("scanner.section." + result.getSearch_type()));
        row.setSectionCode(result.getSearch_type());
        
        if(this.scanner.getRestrictions().getDetail().getTarget_page() != null) {
        	row.setTarget_page(this.scanner.getRestrictions().getDetail().getTarget_page());
        }
        row.setMonitor_page(this.scanner.getConfiguration().getPages());
        row.setPosition_in_page(lastTrack.getPosition_in_page());
        
        row.setFeeling(snippet.getFeeling());
        try {
			row.setLastDateScan(RemoveDateUtils.formatDateStringDash(lastDateScanNoFormat(scanner.getId())).trim());
		} catch (RemoveApplicationException e) {
		}
        row.setScanType(scanner.getType());
        row.addTrackingWords(snippet.getTrackingWords());
        row.prepareDS();

        list.add(row);
    }

    /**
     * Push element into New/Negative Content section for report generation for Web snippets.
     *
     * @param list
     * @param result
     * @param snippet
     * @param lastDate
     */
    private void pushNewNegativeContent(List<TableRow> list, ScannerResult result, ScannerResultWebSnippet snippet,
                                        LocalDate lastDate) {
        ScannerResultWebSnippetTrack lastTrack = snippet.getTracking().get(snippet.getTracking().size() - 1);
        ScannerResultWebSnippetTrack firstTrack = snippet.getTracking().get(0);

        TableRow row = new TableRow();

        row.setKeyword(result.getKeyword().getWord());
        row.setUrl(snippet.getLink());
        row.setTrackingWordOrUrl(snippet.trackingWordsTitles());
        row.setTitle(snippet.getTitle());
        row.setDescription(snippet.getSnippet());
        row.setPagePos(lastTrack.getPage() + " / " + lastTrack.getPosition_in_page());
        row.setRanking(lastTrack.getPosition());
        row.setEvolution(calculateEvolution(firstTrack.getPosition(), lastTrack.getPosition()));
        row.setVisibility(bundle.getLabelBundle(visibilityKey(lastTrack.getPosition())));
        //row.setDateScan(RemoveDateUtils.formatDateTimeDash(lastTrack.getDate_scan()));
        row.setDateScan(RemoveDateUtils.formatDateStringDash(lastTrack.getDate_scan()).trim());
        row.setCountry(bundle.getLabelCountryBundle(result.getCountry().getTag()));
        row.setSection(bundle.getLabelBundle("scanner.section." + result.getSearch_type()));
        row.setSectionCode(result.getSearch_type());
        
        if(this.scanner.getRestrictions().getDetail().getTarget_page() != null) {
        	row.setTarget_page(this.scanner.getRestrictions().getDetail().getTarget_page());
        }
        
        row.setMonitor_page(this.scanner.getConfiguration().getPages());
        row.setPosition_in_page(lastTrack.getPosition_in_page());
        
        row.setFeeling(snippet.getFeeling());
        try {
			row.setLastDateScan(RemoveDateUtils.formatDateStringDash(lastDateScanNoFormat(scanner.getId())).trim());
		} catch (RemoveApplicationException e) {
		}
        row.setScanType(scanner.getType());
        row.addTrackingWords(snippet.getTrackingWords());
        row.prepareDS();

        list.add(row);
    }

    /**
     * Push element into New/Negative Content section for report generation for Image snippets.
     *
     * @param list
     * @param result
     * @param snippet
     * @param lastDate
     */
    private void pushNewNegativeContent(List<TableRow> list, ScannerResult result, ScannerResultImageSnippet snippet,
                                        LocalDate lastDate) {
        ScannerResultImageSnippetTrack lastTrack = snippet.getTracking().get(snippet.getTracking().size() - 1);
        ScannerResultImageSnippetTrack firstTrack = snippet.getTracking().get(0);

        TableRow row = new TableRow();

        row.setKeyword(result.getKeyword().getWord());
        row.setUrl(snippet.getLink());
        row.setTrackingWordOrUrl(snippet.trackingWordsTitles());
        row.setTitle(snippet.getTitle());
        row.setDescription(snippet.getDescription());
        row.setPagePos(lastTrack.getPage() + " / " + lastTrack.getPosition_in_page());
        row.setRanking(lastTrack.getPosition());
        row.setEvolution(calculateEvolution(firstTrack.getPosition(), lastTrack.getPosition()));
        row.setVisibility(bundle.getLabelBundle(visibilityKey(lastTrack.getPosition())));
        //row.setDateScan(RemoveDateUtils.formatDateTimeDash(lastTrack.getDate_scan()));
        row.setDateScan(RemoveDateUtils.formatDateStringDash(lastTrack.getDate_scan()).trim());
        row.setCountry(bundle.getLabelCountryBundle(result.getCountry().getTag()));
        row.setSection(bundle.getLabelBundle("scanner.section." + result.getSearch_type()));
        row.setSectionCode(result.getSearch_type());
        
        if(this.scanner.getRestrictions().getDetail().getTarget_page() != null) {
        	row.setTarget_page(this.scanner.getRestrictions().getDetail().getTarget_page());
        }
        row.setMonitor_page(this.scanner.getConfiguration().getPages());
        row.setPosition_in_page(lastTrack.getPosition_in_page());
        
        row.setFeeling(snippet.getFeeling());
        try {
			row.setLastDateScan(RemoveDateUtils.formatDateStringDash(lastDateScanNoFormat(scanner.getId())).trim());
		} catch (RemoveApplicationException e) {
		}
        row.setScanType(scanner.getType());
        row.addTrackingWords(snippet.getTrackingWords());
        row.prepareDS();

        list.add(row);
    }

    private void pushInChartTimeline(ScannerResult result, List<ChartElement> list, ScannerResultImageSnippet snippet) {
        String timelineLabel = buildTimelineLabel(result, snippet.getLink());
        String snippetId = "img-" + String.valueOf(snippet.getId());

        if (snippet != null && snippet.getTracking() != null && !snippet.getTracking().isEmpty()) {
            snippet.getTracking().forEach(track -> {
                getLog().info(uuid + ": Pushing img snippet track to Remove/Impulse Content: " + snippet.getId() + " - " + track.getId());
                String date = RemoveDateUtils.formatDateDash(track.getDate_scan().toLocalDate());

//    			list.add(new ChartElement(timelineLabel, snippetId, track.getDate_scan().toLocalDate(), track.getPosition()));
                if (track.getPosition().intValue() <= 20) {
                	list.add(new ChartElement(snippetId, timelineLabel, date, track.getPosition() * -1));                	
                }
            });
        }
    }

    private void pushInChartTimeline(ScannerResult result, List<ChartElement> list, ScannerResultWebSnippet snippet) {
        String timelineLabel = buildTimelineLabel(result, snippet.getLink());
        String snippetId = "web-" + String.valueOf(snippet.getId());

        if (snippet != null && snippet.getTracking() != null && !snippet.getTracking().isEmpty()) {
            snippet.getTracking().forEach(track -> {
                getLog().info(uuid + ": Pushing web snippet track to Remove/Impulse Content: " + snippet.getId() + " - " + track.getId());
                String date = RemoveDateUtils.formatDateDash(track.getDate_scan().toLocalDate());

//    			list.add(new ChartElement(timelineLabel, snippetId, track.getDate_scan().toLocalDate(), track.getPosition()));
                list.add(new ChartElement(snippetId, timelineLabel, date, track.getPosition() * -1));
            });
        }
    }
    
    private void pushInChartTimelineWebNew(ScannerResult result, List<ChartElement> list, ScannerResultWebSnippet snippet) {
        String timelineLabel = buildTimelineLabel(result, snippet.getLink());
        String snippetId = "web-" + String.valueOf(snippet.getId());

        if (snippet != null && snippet.getTracking() != null && !snippet.getTracking().isEmpty()) {
            snippet.getTracking().forEach(track -> {
                getLog().info(uuid + ": Pushing web snippet track to Remove/Impulse Content: " + snippet.getId() + " - " + track.getId());
                String date = RemoveDateUtils.formatDateDash(track.getDate_scan().toLocalDate());

//    			list.add(new ChartElement(timelineLabel, snippetId, track.getDate_scan().toLocalDate(), track.getPosition()));
                list.add(new ChartElement(snippetId, timelineLabel, date, track.getPosition() * -1));
            });
        }
    }

    private void pushInChartTimeline(ScannerResult result, List<ChartElement> list, ScannerResultNewsSnippet snippet) {
        String timelineLabel = buildTimelineLabel(result, snippet.getLink());
        String snippetId = "nws-" + String.valueOf(snippet.getId());

        if (snippet != null && snippet.getTracking() != null && !snippet.getTracking().isEmpty()) {
            snippet.getTracking().forEach(track -> {
                getLog().info(uuid + ": Pushing news snippet track to Remove/Impulse Content: " + snippet.getId() + " - " + track.getId());
                String date = RemoveDateUtils.formatDateDash(track.getDate_scan().toLocalDate());

//    			list.add(new ChartElement(timelineLabel, snippetId, track.getDate_scan().toLocalDate(), track.getPosition()));
                list.add(new ChartElement(snippetId, timelineLabel, date, track.getPosition() * -1));
            });
        }
    }

    private String buildTimelineLabel(ScannerResult result, String link) {
        int capChars = 120;
        String label = result.getKeyword().getWord() + " - " + bundle.getLabelBundle("scanner.section." + result.getSearch_type()) + " - " + bundle.getLabelCountryBundle(result.getCountry().getTag()) + " - " + link;

        return label.length() <= capChars ? label : label.substring(0, capChars) + "...";
    }


    /**
     * Verify if a Snippet is considered as new Content.
     *
     * @param firstTimeSnippetFound
     * @param refenreceFirstDate
     * @return
     */
    private boolean isNewContent(LocalDateTime firstTimeSnippetFound, LocalDate refenreceFirstDate) {
        if (firstTimeSnippetFound.toLocalDate().isAfter(refenreceFirstDate)) {
            return true;
        }

        return false;
    }

    /**
     * Fill all information for Scanner Configuration Info in report.
     *
     * @param scanner
     */
    private void fillJrConfigParameters() {
    	if (this.languageNotification.equals(ELanguages.ENGLISH.getCode())) {
    		this.addJrParameter(JRParameter.REPORT_LOCALE, new Locale("en", "US"));	
    	} else {
    		this.addJrParameter(JRParameter.REPORT_LOCALE, new Locale("es", "ES"));	
    	}
    	
        this.addJrParameter(KEY_PARAM_CONFIG_SCANNER_NAME, scanner.getName());
        this.addJrParameter(KEY_PARAM_CONFIG_SCANNER_TYPE, bundle.getLabelBundle("scanner.type." + scanner.getType()));
        this.addJrParameter(KEY_PARAM_CONFIG_SCANNER_TYPE_CODE, scanner.getType());
        this.addJrParameter(KEY_PARAM_CONFIG_KEYWORDS, scanner.keywordsTitles());
        this.addJrParameter(KEY_PARAM_CONFIG_COUNTRIES_NAMES, scanner.countriesNames(languageNotification));
        this.addJrParameter(KEY_PARAM_CONFIG_TRACKING_WORDS, scanner.trackingWordsTitles());
        this.addJrParameterCollection(KEY_PARAM_CONFIG_TRACKING_WORDS_DS, createTWURLLists(scanner.getJustTrackingWords()));
        this.addJrParameter(KEY_PARAM_CONFIG_URL_TO_REMOVE, scanner.urlsTitles());
        this.addJrParameterCollection(KEY_PARAM_CONFIG_URL_TO_REMOVE_DS, createTWURLLists(scanner.getJustTrackingURLs()));

        this.addJrParameter(KEY_PARAM_CONFIG_PAGES, scanner.getConfiguration().getPages());
        this.addJrParameter(KEY_PARAM_CONFIG_LANGUAGE_NAME, bundle.getLabelLanguageBundle(scanner.getConfiguration().getLanguage()));
        
        String device = bundle.getLabelBundle("scanner.device." + scanner.getConfiguration().getDevice());

        if(device.equalsIgnoreCase("Computadora")) {
        	device = "Ordenador";
        }else if(device.equalsIgnoreCase("Móvil")) {
        	device = "Móvil";
        }else if (device.equalsIgnoreCase("Tablet")) {
        	device = "Tablet";
        }
        
        this.addJrParameter(KEY_PARAM_CONFIG_DEVICES_NAMES, device);
        this.addJrParameter(KEY_PARAM_CONFIG_SECTIONS_NAMES, scanner.sectionsNames(languageNotification));
        
        

    }

    private List<TrackingWordBean> createTWURLLists(List<ScannerTrackingWord> trackingWords) {
        List<TrackingWordBean> list = new ArrayList<TrackingWordBean>();

        if (trackingWords != null && !trackingWords.isEmpty()) {
            trackingWords.forEach(tw -> {
                list.add(new TrackingWordBean(tw.getWord(), tw.getFeeling()));
            });
        }

        return list;
    }

    /**
     * Fill all information for Scanner Filter Info in report.
     */
    private void fillJrFilterParameters() {
        this.addJrParameter(KEY_PARAM_FILTER_KEYWORDS, this.getParameters().extractParamStringArrayAsString(KEY_PARAM_FILTER_KEYWORDS));
        this.addJrParameter(KEY_PARAM_FILTER_COUNTRIES, scanner.countriesNamesFilter(languageNotification, this.getParameters().extractParamStringArrayAsString(KEY_PARAM_FILTER_COUNTRIES)));
        
        //this.addJrParameter(KEY_PARAM_CONFIG_COUNTRIES_NAMES, scanner.countriesNames(languageNotification));

        if (this.getParameters().extractParamAsString(KEY_PARAM_FILTER_PAGES) != null) {
            this.addJrParameter(KEY_PARAM_FILTER_PAGES, this.getParameters().extractParamIntArrayAsString(KEY_PARAM_FILTER_PAGES));
        }

        this.addJrParameter(KEY_PARAM_FILTER_TRACKING_WORDS, this.getParameters().extractParamStringArrayAsString(KEY_PARAM_FILTER_TRACKING_WORDS));
        this.addJrParameter(KEY_PARAM_FILTER_FEELINGS_NAMES, this.getParameters().extractParamStringArrayAsString(KEY_PARAM_FILTER_FEELINGS_NAMES));
        this.addJrParameter(KEY_PARAM_FILTER_URLS, this.getParameters().extractParamStringArrayAsString(KEY_PARAM_FILTER_URLS, "\n"));
        this.addJrParameter(KEY_PARAM_FILTER_SECTIONS_NAMES, this.getParameters().extractParamStringArrayAsString(KEY_PARAM_FILTER_SECTIONS_NAMES));

        if (this.getParameters().extractParamAsDate(KEY_PARAM_FILTER_SCANNER_DATE_START) != null) {
            this.addJrParameter(KEY_PARAM_FILTER_SCANNER_DATE_START, RemoveDateUtils.formatDateDash(this.getParameters().extractParamAsDate(KEY_PARAM_FILTER_SCANNER_DATE_START)));
        }
        if (this.getParameters().extractParamAsDate(KEY_PARAM_FILTER_SCANNER_DATE_END) != null) {
            this.addJrParameter(KEY_PARAM_FILTER_SCANNER_DATE_END, RemoveDateUtils.formatDateDash(this.getParameters().extractParamAsDate(KEY_PARAM_FILTER_SCANNER_DATE_END)));
        }

        this.addJrParameter(KEY_PARAM_FILTER_IS_NEW_CONTENT, this.getParameters().extractParamAsBoolean(KEY_PARAM_FILTER_IS_NEW_CONTENT));
        
        
    }

    /**
     * Load Client info for report generation
     *
     * @param id_owner
     * @throws RemoveApplicationException
     */
    private void loadClientNameInfo(Long id_owner) throws RemoveApplicationException {
        Client client = ClientDao.init().getBasicById(id_owner);

        destinataryName = client.fullName();
        languageNotification = client.getLanguage();
        this.addJrParameter(KEY_PARAM_REFERENCE_LINK_LOGO, client.getReference_link_logo());
    }

    /**
     * Loas User Info for report generation
     *
     * @param uuid
     * @param profile
     * @return
     * @throws RemoveApplicationException
     */
    private String loadUserNameInfo(String uuid, String profile) throws RemoveApplicationException {
        if (EProfiles.CLIENT.getCode().equalsIgnoreCase(profile)) {
            Client client = ClientDao.init().getBasicByUuid(uuid);
            return client.fullName();
        } else {
            User user = UserDao.init().getBasicByUuid(uuid);
            return user.fullName();
        }
    }

    /**
     * Load Scanner info for report generation
     *
     * @param uuidScanner
     * @return
     * @throws RemoveApplicationException
     */
    private Scanner loadScannerInfo(String uuidScanner) throws RemoveApplicationException {
        Scanner scanner = ScannerDao.init().getBasicByUuid(uuidScanner);

        ScannerResultView view = ScannerResultViewDao.init().getByUuid(uuidScanner);

        if (view != null && view.getContent() != null) {
            scanner = (Scanner) RemoveJsonUtil.jsonToData(view.getContent(), Scanner.class);
            scanner.setImpulses(ScannerImpulseDao.init().listPublishedByScanner(scanner.getId()));
        } else { // Deprecado temporal mientras se migran todos los scanners al view
            scanner = ScannerDao.init().getJustConfigByUuid(uuidScanner, scanner.getType());

            if (scanner != null) {
                scanner.setResults(ScannerResultDao.init().list(scanner.getId()));
                scanner.setImpulses(ScannerImpulseDao.init().listPublishedByScanner(scanner.getId()));
            }
        }

        if (scanner != null) {
            scanner.clearImagesResults();
        }

        if (scanner.getTrackingWords().isEmpty()) {
            scanner.setTrackingWords(ScannerTrackingWordsDao.init().list(scanner.getId()));
            scanner.splitTrackingWords();
        }

        scanner.setRestrictions(ClientPlanDao.init().getById(scanner.getId_client_plan()));

        return scanner;
    }

    /**
     * Send report to mail specified in parameters
     */
    @Override
    protected void sendNotifications(String filePath) {
        try {
        	Map<String, String> tokens = new HashMap<String, String>();
        	tokens.put("{{SCANNER_NAME}}", scanner.getName());
        	
            EmailReportHelper.init().sendReportEmail(languageNotification,
                    this.getParameters().getEmail(), destinataryName, tokens,
                    Arrays.asList(filePath));
        } catch (RemoveApplicationException e) {
            getLog().error(uuid + ": Error sending notification report: ", e);
        }
    }

    /**
     * Condition that replies evolution of snippet in time.
     *
     * @param firstPosition
     * @param lastPosition
     * @return
     */
    private String calculateEvolution(Integer firstPosition, Integer lastPosition) {
        return firstPosition.intValue() == lastPosition.intValue() ? "SAME" :
                firstPosition.intValue() < lastPosition.intValue() ? "DOWN" : "UP";
    }

    /**
     * @param position
     * @return
     */
    private String visibilityKey(Integer position) {
        if (position != null) {
            if (position.intValue() <= 3) {
                return "visibility.very_high";
            } else if (position.intValue() <= 7) {
                return "visibility.high";
            } else if (position.intValue() <= 10) {
                return "visibility.medium";
            } else if (position.intValue() <= 13) {
                return "visibility.low";
            }
        }

        return "visibility.null";
    }

    /**
     * Main method for test purposes
     * @param args
     */
    @Deprecated
    public static void main(String[] args) {
    	System.out.println("");
    	
        ReportParams params = new ReportParams();
        params.setEmail("israel.navarrete@atianza.com");
        params.setCode("scanner_dashboard");
        params.setToEmail(true);
        params.setToNotification(false);
        params.setCleanFile(false);
        params.addParameter("KEY_PARAM_FILTER_USER_UUID", "79a45fab-75e5-44e7-8f04-3a7403d5845e");        
        params.addParameter("KEY_PARAM_FILTER_USER_PROFILE", EProfiles.CLIENT.getCode());

//		  params.addParameter("KEY_PARAM_FILTER_SCANNER_UUID", "64ed1a1a-91c6-425f-9259-7fb85a9ffef2");		// Bit2Me English
//        params.addParameter("KEY_PARAM_FILTER_SCANNER_UUID", "047e5ce3-7f0b-4e1b-be4c-3213ca9f582c");		// Bit2Me
        //params.addParameter("KEY_PARAM_FILTER_SCANNER_UUID", "6420ca1c-365c-45f7-9e38-d0a2924b4aa8");		// Paola
        //params.addParameter("KEY_PARAM_FILTER_SCANNER_UUID", "3cd6537e-a8d0-4c00-9e8a-58eeb7f931d1");		// Centro de Ayuda Cristiano
       // params.addParameter("KEY_PARAM_FILTER_SCANNER_UUID", "b58fc115-147c-4628-b130-19d9d905eb96");		// Portobello Capital
         //params.addParameter("KEY_PARAM_FILTER_SCANNER_UUID", "ac03449f-7f83-43e4-afd2-798c1964a2bd"); bvvbb		// Juan Carlos Meneu
      //  params.addParameter("KEY_PARAM_FILTER_SCANNER_UUID", "dc18f3cd-07a3-4077-bcc1-080f10bb725c"); // ocean
        //params.addParameter("KEY_PARAM_FILTER_SCANNER_UUID", "79a5563a-7118-4bfd-89a7-25c93d67966d"); // de la cebosa clonado
        
     params.addParameter("KEY_PARAM_FILTER_SCANNER_UUID", "d07a11ff-4ca2-4f3e-b6e2-aea19564c0d3"); //Susana DLP
        
        
        EReportTypes.SCANNER_DASHBOARD.getBuildFunction().apply(params);
    }
}
