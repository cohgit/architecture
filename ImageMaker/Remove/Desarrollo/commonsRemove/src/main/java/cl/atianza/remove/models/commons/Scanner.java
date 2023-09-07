package cl.atianza.remove.models.commons;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.daos.commons.ScannerResultDao;
import cl.atianza.remove.daos.commons.ScannerResultViewDao;
import cl.atianza.remove.daos.commons.ScannerTrackingWordsDao;
import cl.atianza.remove.dtos.commons.ScannerDto;
import cl.atianza.remove.enums.ECountries;
import cl.atianza.remove.enums.EScannerStatus;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.enums.ESearchTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.reports.beans.TableRemoveImpulseRow;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.utils.RemoveBundle;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveJsonUtil;

public class Scanner extends ScannerDto {
	protected Logger log = LogManager.getLogger(Scanner.class);
	
	private EScannerStatus statusDef;
	private String uuidClient;
	private ScannerConfiguration configuration;
	private ClientPlan restrictions;
	private List<ScannerKeyword> keywords;
	private List<ScannerTrackingWord> trackingWords;
	private List<ScannerResult> results;
	private List<ScannerImpulse> impulses;
	
	private List<ScannerTrackingWord> justTrackingWords;
	private List<ScannerTrackingWord> justTrackingURLs;
	
	private String keywordsSplited;
	
	private String action;
	
    int totalContentRemoved = 0;
    int totalContentToRemove = 0;
    int total_threat_to_remove = 0;
    int total_content_removed = 0;
    
    float total_threats = 0;
    float total_threats_removed = 0;
    float total_progress_bar = 0;
    
    Scanner scanner = null;
	
	public Scanner() {
		super();
	}

	public EScannerStatus getStatusDef() {
		if (this.getStatus() != null) {
			this.statusDef = EScannerStatus.obtain(this.getStatus());
		}
		
		return statusDef;
	}

	public void setStatusDef(EScannerStatus statusDef) {
		this.statusDef = statusDef;
	}

	public String getUuidClient() {
		return uuidClient;
	}

	public void setUuidClient(String uuidClient) {
		this.uuidClient = uuidClient;
	}

	public ScannerConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(ScannerConfiguration configuration) {
		this.configuration = configuration;
	}

	public ClientPlan getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(ClientPlan restrictions) {
		this.restrictions = restrictions;
	}

	public List<ScannerKeyword> getKeywords() {
		if (keywords == null) {
			this.keywords = new ArrayList<ScannerKeyword>();
		}
		
		return keywords;
	}

	public void setKeywords(List<ScannerKeyword> keywords) {
		this.keywords = keywords;
	}

	public List<ScannerTrackingWord> getTrackingWords() {
		if (trackingWords == null) {
			this.trackingWords = new ArrayList<ScannerTrackingWord>();
		}
		
		return trackingWords;
	}

	public void setTrackingWords(List<ScannerTrackingWord> trakingWords) {
		this.trackingWords = trakingWords;
	}

	public List<ScannerResult> getResults() {
		if (results == null) {
			results = new ArrayList<ScannerResult>();
		}
		return results;
	}

	public void setResults(List<ScannerResult> results) {
		this.results = results;
	}

	public List<ScannerImpulse> getImpulses() {
		if (impulses == null) {
			impulses = new ArrayList<ScannerImpulse>();
		}
		return impulses;
	}

	public void setImpulses(List<ScannerImpulse> impulses) {
		this.impulses = impulses;
	}

	public List<ScannerTrackingWord> getJustTrackingWords() {
		if (this.justTrackingWords == null) {
			this.justTrackingWords = new ArrayList<ScannerTrackingWord>();
		}
		
		return justTrackingWords;
	}
	public List<String> justTrackingWordsTitles() {
		return this.getJustTrackingWords().stream().map(ScannerTrackingWord::getWord).collect(Collectors.toList());
	}

	public void setJustTrackingWords(List<ScannerTrackingWord> justTrackingWords) {
		this.justTrackingWords = justTrackingWords;
	}

	public List<ScannerTrackingWord> getJustTrackingURLs() {
		if (this.justTrackingURLs == null) {
			this.justTrackingURLs = new ArrayList<ScannerTrackingWord>();
		}
		
		return justTrackingURLs;
	}
	
	public List<ScannerTrackingWord> listJustTrackingUrlsActives() {
		return this.getJustTrackingURLs().stream().filter( url -> !url.isMarkToDelete()).collect(Collectors.toList());
	}

	public void setJustTrackingURLs(List<ScannerTrackingWord> justTrackingURLs) {
		this.justTrackingURLs = justTrackingURLs;
	}

	@Override
	public String toString() {
		return "Scanner [" + super.toString() + ", configuration=" + configuration + ", keywords=" + keywords + ", trackingWords=" + trackingWords
				+ ", results=" + results + ", impulses=" + impulses + "]";
	}

	public void splitTrackingWords() {
		List<ScannerTrackingWord> words = new ArrayList<ScannerTrackingWord>();
		List<ScannerTrackingWord> urls = new ArrayList<ScannerTrackingWord>();
		
		this.getTrackingWords().forEach(tw -> {
			if (tw.getType().equalsIgnoreCase("URL")) {
				urls.add(tw);
			} else if (tw.getType().equalsIgnoreCase("WORD")) {
				words.add(tw);
			}
		});
		
		this.setJustTrackingURLs(urls);
		this.setJustTrackingWords(words);
	}
	public void joinTrackingWords() {
		List<ScannerTrackingWord> tws = new ArrayList<ScannerTrackingWord>();
		
		this.getJustTrackingURLs().forEach(url -> {
			url.setType("URL");
			tws.add(url);
		});
		this.getJustTrackingWords().forEach(tw -> {
			tw.setType("WORD");
			tws.add(tw);
		});
		
		this.setTrackingWords(tws);
	}
	
//	public void merge(Scanner updatedScan) {
//		Logger log = LogManager.getLogger(Scanner.class);
//		log.info("Merging: " + this.getUuid() + " - results : " + this.getResults().size());
//		log.info("Merging: " + updatedScan.getUuid() + " - results : " + updatedScan.getResults().size());
//		this.getResults().forEach(result -> {
//			updatedScan.getResults().forEach(newResult -> {
//				tryMerge(result, newResult);
//			});
//		});
//	}
//
//	private boolean tryMerge(ScannerResult result, ScannerResult newResult) {
//		Logger log = LogManager.getLogger(Scanner.class);
//		log.info("TryMerge Result: " + result.getUuid());
//		if (result.same(newResult)) {
//			log.info("same: " + newResult.getUuid());
//			result.merge(newResult);
//			return true;
//		}
//		
//		return false;
//	}

	public void addResult(ScannerResult result) {
		this.getResults().add(result);
	}

	public boolean esOneShot() {
		return EScannerTypes.ONE_SHOT.getCode().equalsIgnoreCase(this.getType());
	}
	public boolean esRecurrent() {
		return EScannerTypes.MONITOR.getCode().equalsIgnoreCase(this.getType());
	}
	public boolean esTransform() {
		return EScannerTypes.TRANSFORM.getCode().equalsIgnoreCase(this.getType());
	}
	
	public double getProgress() throws RemoveApplicationException {
		//if (result.getProgress() != null) total += result.getProgress().floatValue();
		float total = 0f;
		List<TableRemoveImpulseRow> listContentToRemove = new ArrayList<TableRemoveImpulseRow>();
		
		scanner = loadScannerInfo(this.getUuid());

		if(scanner != null) {
			if (scanner.getResults() != null && !scanner.getResults().isEmpty()) {
				for (ScannerResult result : scanner.getResults()) {
					result.getSnippetsImages().forEach(snippet -> {
						scanner.getTrackingWords().forEach(trackingWord -> {
							if (trackingWord.esURL()) {
								if (snippet.getLink().equals(trackingWord.getWord())) {
			                        boolean targetReached =  verifyTargetReachedForTransform(scanner.getRestrictions(), result.getQuery_date(),
			                                snippet.lastTracking().getDate_scan(), snippet.lastTracking().getPage());
			                        
			                        if (targetReached) totalContentRemoved++; else  totalContentToRemove++;
			                        pushRemoveImpulse(listContentToRemove, result, snippet, targetReached);
								}
							}

						});
					});
					
					result.getSnippetsNews().forEach(snippet -> {
						scanner.getTrackingWords().forEach(trackingWord -> {
							if (trackingWord.esURL()) {
								if (snippet.getLink().equals(trackingWord.getWord())) {
			                        boolean targetReached =  verifyTargetReachedForTransform(scanner.getRestrictions(), result.getQuery_date(),
			                                snippet.lastTracking().getDate_scan(), snippet.lastTracking().getPage());
			                        
			                        if (targetReached) totalContentRemoved++; else  totalContentToRemove++;
			                        pushRemoveImpulse(listContentToRemove, result, snippet, targetReached);
								}
							}
						});
					});
					
					result.getSnippetsWebs().forEach(snippet -> {
						scanner.getTrackingWords().forEach(trackingWord -> {
							if (trackingWord.esURL()) {
								if (snippet.getLink().equals(trackingWord.getWord())) {
			                        boolean targetReached =  verifyTargetReachedForTransform(scanner.getRestrictions(), result.getQuery_date(),
			                                snippet.lastTracking().getDate_scan(), snippet.lastTracking().getPage());
			                        
			                        if (targetReached) totalContentRemoved++; else  totalContentToRemove++;
			                        pushRemoveImpulse(listContentToRemove, result, snippet, targetReached);
								}
							}
						});
					});
				}
				total_threat_to_remove = this.scanner.getConfiguration().getCountries().size()* 
		    			this.scanner.getConfiguration().getSearch_type().split(",").length * 
		    			this.scanner.getKeywords().size() * 
		    			this.scanner.getJustTrackingURLs().size() - listContentToRemove.size();
				
				total_content_removed = total_threat_to_remove + totalContentRemoved;
				
				total_threats = this.scanner.getConfiguration().getCountries().size()* 
		    			this.scanner.getConfiguration().getSearch_type().split(",").length * 
		    			this.scanner.getKeywords().size() * 
		    			this.scanner.getJustTrackingURLs().size();
				
				total_threats_removed = total_content_removed;
				
				total = (total_threats_removed * 100)/total_threats;
				
				//total = total / this.getResults().size();
			}
		}
		
		if(Double.isNaN(total))
			total = 0f;
		
		return total;
	}
	public void setProgress(Float progress) {}
	
    private boolean verifyTargetReachedForTransform(ClientPlan restrictions, LocalDateTime lastScanDate,
            LocalDateTime lastTrackDate, Integer page) {
    	try {
    		return lastScanDate.toLocalDate().isEqual(lastTrackDate.toLocalDate()) ? page.intValue() > restrictions.getDetail().getTarget_page().intValue() : true;
    	} catch (Exception e) {
    		log.error(this.getUuid() + ": Error: ", e);
    	}
    	return false;
    }
	
	public boolean isRemovalScanner() {
		if (this.getJustTrackingURLs().isEmpty() && !this.getTrackingWords().isEmpty()) {
			this.splitTrackingWords();
		}
		return this.esTransform() && !this.getJustTrackingURLs().isEmpty();
	}
	public void setRemovalScanner(boolean notRelevant) {}

	public boolean isImpulserScanner() {
		if (this.getJustTrackingURLs().isEmpty() && !this.getTrackingWords().isEmpty()) {
			this.splitTrackingWords();
		}
		return this.esTransform() && this.getJustTrackingURLs().isEmpty();
	}
	public void setImpulserScanner(boolean notRelevant) {}
	
	public int countTotalKeywords() {
		int total = 0;
		
		if (!this.getKeywords().isEmpty()) {
			for (ScannerKeyword keyword : this.getKeywords()) {
				total++;
				
				if (keyword.getListSuggested() != null && !keyword.getListSuggested().isEmpty()) {
					for(ScannerKeyword suggested : keyword.getListSuggested()) if (suggested.isChecked()) total ++;
				}
			}
		}
		
		return total;
	}

	public String getKeywordsSplited() {
		return keywordsSplited;
	}

	public void setKeywordsSplited(String keywordsSplited) {
		this.keywordsSplited = keywordsSplited;
	}
	
	

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void splitKeywords() {
		this.keywordsSplited = "";
		
		if (this.getKeywords() != null) {
			for (ScannerKeyword keyw: this.getKeywords()) {
				this.keywordsSplited += keyw.getWord() + ", ";
				
				if (keyw.getListSuggested() != null) {
					for (ScannerKeyword keyws: keyw.getListSuggested()) {
						this.keywordsSplited += keyws.getWord() + ", ";
					}
				}
			}
		}
		
		if (!this.keywordsSplited.isEmpty()) {
			this.keywordsSplited = this.keywordsSplited.substring(0, this.keywordsSplited.length()-2);
		}
	}

	public Map<String, Object> buildAuditParams() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("name", this.getName());
		map.put("uuid", this.getUuid());
		
		return map;
	}

	/**
	 * Metodo temporal para agilizar el renderizado del scanner
	 */
	@Deprecated
	public void clearImagesResults() {
		if (this.getResults() != null && !this.getResults().isEmpty()) {
			this.setResults(this.getResults().stream().filter(result -> !result.getSearch_type().equals(ESearchTypes.IMAGES.getCode() )).collect(Collectors.toList()));
		}
	}
	
	public void filterResultsByCountries(String... filterCountries) {
		if (filterCountries != null && filterCountries.length > 0 && this.results != null) {
			log.info("Filtering Countries: " + Arrays.toString(filterCountries));
			this.results = this.results.stream().filter((result) -> {
				boolean found = false;
				for (String cs: filterCountries) {
					
					if (result.getCountry().getName().trim().equalsIgnoreCase(cs.trim())) {
						found = true;
						break;
					}
				}
				log.info("Filtering Countries: " + result.getCountry().getName() +  " false");
				return found;
			}).collect(Collectors.toList());
		}
	}

	public void filterResultsByKeywords(String... filterKeywords) {
		if (filterKeywords != null && filterKeywords.length > 0 && this.results != null) {
			log.info("Filtering Keywords: " + Arrays.toString(filterKeywords));
			this.results = this.results.stream().filter((result) -> {
				boolean found = false;
				for (String kw: filterKeywords) {
					if (result.getKeyword().getWord().trim().equalsIgnoreCase(kw.trim())) {
						found = true;
						break;
					}
				}
				log.info("Filtering Keyword: " + result.getKeyword().getWord() +  " false");
				return found;
			}).collect(Collectors.toList());
		}
	}
	
	public void filterResultsBySections(String... sections) {
		if (sections != null && sections.length > 0 && !this.results.isEmpty()) {
			this.results = this.results.stream().filter((result) -> {
				for (String section: sections) {
					if (result.getSearch_type().equalsIgnoreCase(section.trim())) {
						return true;
					}
				}
				
				return false;
			}).collect(Collectors.toList());
		}
	}
	
	public void filterResultsByTrackingWords(String... filterTrackingWords) {//FIXME:
		if (filterTrackingWords != null && filterTrackingWords.length > 0 && this.results != null) {
			this.results = this.results.stream().filter((result) -> {
				result.filterSnippetsByTrackingWords(filterTrackingWords);
				return !result.getSnippetsImages().isEmpty() || !result.getSnippetsNews().isEmpty() || !result.getSnippetsWebs().isEmpty();
			}).collect(Collectors.toList());
		}
	}

	public void filterResultsByFeelings(String... feelings) {//FIXME:
		if (feelings != null && feelings.length > 0 && this.results != null) {
			this.results = this.results.stream().filter((result) -> {
				result.filterSnippetsByFeeling(feelings);
				return !result.getSnippetsImages().isEmpty() || !result.getSnippetsNews().isEmpty() || !result.getSnippetsWebs().isEmpty();
			}).collect(Collectors.toList());
		}
	}
	
	public void filterResultsByURLs(String... urls) {//FIXME:
		if (urls != null && urls.length > 0 && this.results != null) {
			this.results = this.results.stream().filter((result) -> {
				result.filterSnippetsByUrl(urls);
				return !result.getSnippetsImages().isEmpty() || !result.getSnippetsNews().isEmpty() || !result.getSnippetsWebs().isEmpty();
			}).collect(Collectors.toList());
		}
	}
	
	public void filterResultsByPage(int... numberPages) {
		if (numberPages != null && numberPages.length > 0 && this.results != null) {
			this.results = this.results.stream().filter(
					(result) -> {
						result.filterSnippetsByPage(numberPages);
						return !result.getSnippetsImages().isEmpty() || !result.getSnippetsNews().isEmpty() || !result.getSnippetsWebs().isEmpty();
					}
			).collect(Collectors.toList());
		}
	}

	public void filterResultsByDates(LocalDate startDate, LocalDate endDate) {
		if (startDate != null && endDate != null) {
			this.results = this.results.stream().filter(
					(result) -> {
						result.filterSnippetsByDate(startDate, endDate);
						return !result.getSnippetsImages().isEmpty() || !result.getSnippetsNews().isEmpty() || !result.getSnippetsWebs().isEmpty();
					}
			).collect(Collectors.toList());
		}
	}

	public void filterResultsByLastContent() {
		LocalDateTime lastScan = findLastScan();
		
		if (lastScan != null) {
			LocalDate lastScanDate = lastScan.toLocalDate();
			
			filterResultsByDates(lastScanDate, lastScanDate);	
		}
	}

	private LocalDateTime findLastScan() {
		LocalDateTime lastScan = null;
		
		if (this.getResults() != null) {
			for (ScannerResult result : this.getResults()) {
				if (lastScan == null || lastScan.isBefore(result.getQuery_date())) {
					lastScan = result.getQuery_date();
				}
			}
		}
		
		return lastScan;
	}

	public String keywordsTitles() {
		List<String> keys = new ArrayList<String>();
		
		if (this.getKeywords() != null && !this.getKeywords().isEmpty()) {
			for (ScannerKeyword k : this.getKeywords()) {
				keys.add(k.getWord());
				
				if (k.getListSuggested() != null) {
					for (ScannerKeyword k2: k.getListSuggested()) {
						keys.add(k2.getWord());
					}
				}
			}
		}
		
		return String.join(", ", keys);
	}

	public String countriesNames(String language) {
		RemoveBundle bundle = RemoveBundle.init(language);
		List<String> names = new ArrayList<String>();
		
		if (this.getConfiguration().getCountries() != null && !this.getConfiguration().getCountries().isEmpty()) {
			for (Country c : this.getConfiguration().getCountries()) {
				names.add(bundle.getLabelCountryBundle(c.getTag()));
			}
		}
		
		return String.join(", ", names);
	}
	
	public String countriesNamesFilter(String language, String... filterCountries) {
		RemoveBundle bundle = RemoveBundle.init(language);
		List<String> names = new ArrayList<String>();
		String[] count = filterCountries;
		String str = "";
		String country = "";
		for(int i=0;i<count.length;i++) {
			str = count[i];
		}
		
		List<String> countries = new ArrayList<String>(Arrays.asList(str.split("\\s*,\\s*")));
		
		
		if (this.results != null && !this.results.isEmpty()) {
			for (String c : countries) {
				country = ECountries.obtainCountries(c);
				names.add(bundle.getLabelCountryBundle(country.trim()));
			}
		
		}
		
		return String.join(", ", names);
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
		
		return String.join("\n", keys);
	}

	public String sectionsNames(String language) {
		RemoveBundle bundle = RemoveBundle.init(language);
		List<String> names = new ArrayList<String>();
		
		if (this.getConfiguration() != null && this.getConfiguration().getSearch_type() != null) {
			for (String section : this.getConfiguration().getSearch_type().split(",")) {
				names.add(bundle.getLabelBundle("scanner.section." + section.trim()));
			}
		}
		
		return String.join(", ", names);
	}

	/**
	 * A veces SERP trae resultados repetidos, este metodo es un parch para 
	 */
	@Deprecated
	public void removeRepeatedTracksForLoad() {
		if (this.results != null) {
			this.results.forEach(result -> {
				if (result.getSnippetsImages() != null) {
					result.getSnippetsImages().forEach(snip -> {
						if (snip.getTracking() != null && !snip.getTracking().isEmpty()) {
							snip.setTracking(Arrays.asList(snip.getTracking().get(0)));
						}
					});
				}
				if (result.getSnippetsNews() != null) {
					result.getSnippetsNews().forEach(snip -> {
						if (snip.getTracking() != null && !snip.getTracking().isEmpty()) {
							snip.setTracking(Arrays.asList(snip.getTracking().get(0)));
						}
					});
				}
				if (result.getSnippetsWebs() != null) {
					result.getSnippetsWebs().forEach(snip -> {
						if (snip.getTracking() != null && !snip.getTracking().isEmpty()) {
							snip.setTracking(Arrays.asList(snip.getTracking().get(0)));
						}
					});
				}
			});
		}
	}
	
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
		row.setEvolution("");
		row.setSectionCode(result.getSearch_type());
		//row.setDateScan(RemoveDateUtils.formatDateTimeDash(lastTrack.getDate_scan()));
		row.setDateScan(RemoveDateUtils.formatDateStringDash(lastTrack.getDate_scan()).trim());
		row.setTargetReached(targetReached);
		
		if(scanner.getRestrictions().getDetail().getTarget_page() != null) {
		row.setTarget_page(scanner.getRestrictions().getDetail().getTarget_page());
		}
		
		row.setMonitor_page(scanner.getConfiguration().getPages());
		row.setPosition_in_page(lastTrack.getPosition_in_page());
		
		row.setFeeling(snippet.getFeeling());

		row.setScanType(scanner.getType());
		row.addTrackingWords(snippet.getTrackingWords());
		
		list.add(row);
	}
	
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
		row.setEvolution("");
		row.setSectionCode(result.getSearch_type());
		//row.setDateScan(RemoveDateUtils.formatDateTimeDash(lastTrack.getDate_scan()));
		row.setDateScan(RemoveDateUtils.formatDateStringDash(lastTrack.getDate_scan()).trim());
		row.setTargetReached(targetReached);
		
		if(scanner.getRestrictions().getDetail().getTarget_page() != null) {
		row.setTarget_page(scanner.getRestrictions().getDetail().getTarget_page());
		}
		
		row.setMonitor_page(scanner.getConfiguration().getPages());
		row.setPosition_in_page(lastTrack.getPosition_in_page());
		
		row.setFeeling(snippet.getFeeling());
		
		row.setScanType(scanner.getType());
		row.addTrackingWords(snippet.getTrackingWords());
		
		list.add(row);
	}
	
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
		row.setEvolution("");
		row.setSectionCode(result.getSearch_type());
		//row.setDateScan(RemoveDateUtils.formatDateTimeDash(lastTrack.getDate_scan()));
		row.setDateScan(RemoveDateUtils.formatDateStringDash(lastTrack.getDate_scan()).trim());
		row.setTargetReached(targetReached);
		
		if(scanner.getRestrictions().getDetail().getTarget_page() != null) {
		row.setTarget_page(scanner.getRestrictions().getDetail().getTarget_page());
		}
		
		row.setMonitor_page(scanner.getConfiguration().getPages());
		row.setPosition_in_page(lastTrack.getPosition_in_page());
		
		row.setFeeling(snippet.getFeeling());
		
		row.setScanType(scanner.getType());
		row.addTrackingWords(snippet.getTrackingWords());
		
		list.add(row);
	}
	
	private Scanner loadScannerInfo(String uuidScanner) throws RemoveApplicationException {
        Scanner scanner = ScannerDao.init().getBasicByUuid(uuidScanner);

        if(scanner != null) {
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
        return scanner;
    }
}


