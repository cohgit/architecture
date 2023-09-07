package cl.atianza.remove.models.commons;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cl.atianza.remove.dtos.commons.ScannerResultDto;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.utils.RemoveJsonUtil;

public class ScannerResult extends ScannerResultDto {
	private Long id_parent_keyword;
	private ScannerKeyword keyword;
	private Country country;
	
	private List<ScannerResultRaw> raws;
	private List<ScannerResultWebSnippet> snippetsWebs;
	private List<ScannerResultNewsSnippet> snippetsNews;
	private List<ScannerResultImageSnippet> snippetsImages;
	
	public ScannerResult() {
		super();
	}

	public ScannerKeyword getKeyword() {
		return keyword;
	}

	public void setKeyword(ScannerKeyword keyword) {
		this.keyword = keyword;
	}

	public List<ScannerResultRaw> getRaws() {
		if (raws == null) {
			this.raws = new ArrayList<ScannerResultRaw>();
		}
		
		return raws;
	}

	public void setRaws(List<ScannerResultRaw> raws) {
		this.raws = raws;
	}
	
	public List<ScannerResultWebSnippet> getSnippetsWebs() {
		if (snippetsWebs == null) {
			this.snippetsWebs = new ArrayList<ScannerResultWebSnippet>();
		}
		return snippetsWebs;
	}

	public void setSnippetsWebs(List<ScannerResultWebSnippet> snippetsWebs) {
		this.snippetsWebs = snippetsWebs;
	}

	public List<ScannerResultImageSnippet> getSnippetsImages() {
		if (snippetsImages == null) {
			this.snippetsImages = new ArrayList<ScannerResultImageSnippet>();
		}
		return snippetsImages;
	}

	public void setSnippetsImages(List<ScannerResultImageSnippet> snippetsImages) {
		this.snippetsImages = snippetsImages;
	}

	public List<ScannerResultNewsSnippet> getSnippetsNews() {
		if (snippetsNews == null) {
			snippetsNews = new ArrayList<ScannerResultNewsSnippet>();
		}
		return snippetsNews;
	}

	public void setSnippetsNews(List<ScannerResultNewsSnippet> snippetsNews) {
		this.snippetsNews = snippetsNews;
	}

	public Long getId_parent_keyword() {
		return id_parent_keyword;
	}

	public void setId_parent_keyword(Long id_parent_keyword) {
		this.id_parent_keyword = id_parent_keyword;
	}

	public boolean same(ScannerResult newResult) {
		return this.getId_country().longValue() == newResult.getId_country().longValue() 
				&& this.getId_keyword().longValue() == newResult.getId_keyword().longValue()
				&& this.getDevice().equals(newResult.getDevice())
				&& this.getLanguage().equals(newResult.getLanguage())
				&& this.getSearch_type().equals(newResult.getSearch_type());
	}

	public void merge(ScannerResult newResult) {
		this.setSnippetsImages(newResult.getSnippetsImages());
		this.setSnippetsNews(newResult.getSnippetsNews());
		this.setSnippetsWebs(newResult.getSnippetsWebs());
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "ScannerResult [id_parent_keyword=" + id_parent_keyword + ", keyword=" + keyword + "]";
	}

	public void cleanRaws() {
		this.raws = new ArrayList<ScannerResultRaw>();
	}

	public Long findUuidRaw(String uuidRaw) {
		for (ScannerResultRaw raw : this.getRaws()) {
			if (raw.getUuid().equals(uuidRaw)) {
				return raw.getId();
			}
		};
		
		return null;
	}
	
	public List<ScannerResultWebSnippet> lastWebSearch() {
		return webSearchByTime(findLastSearchTime());
	}
	public List<ScannerResultWebSnippet> webSearchByTime(LocalDateTime searchTime) {
		List<ScannerResultWebSnippet> last = new ArrayList<ScannerResultWebSnippet>();
		
		this.getSnippetsWebs().forEach(web -> {
			web.getTracking().forEach(track -> {
				if (track.getDate_scan().isEqual(searchTime)) {
					try {
						ScannerResultWebSnippet clone = (ScannerResultWebSnippet) RemoveJsonUtil.clone(web);
						clone.setTracking(new ArrayList<ScannerResultWebSnippetTrack>());
						clone.getTracking().add(track);
						last.add(clone);
					} catch (RemoveApplicationException e) {
						e.printStackTrace();
					}
				}
			});
		});
		
		return last; 
	}
	
	public List<ScannerResultNewsSnippet> lastNewsSearch() {
		return newsSearchByTime(findLastSearchTime());
	}
	public List<ScannerResultNewsSnippet> newsSearchByTime(LocalDateTime searchTime) {
		List<ScannerResultNewsSnippet> last = new ArrayList<ScannerResultNewsSnippet>();
		
		this.getSnippetsNews().forEach(news -> {
			news.getTracking().forEach(track -> {
				if (track.getDate_scan().isEqual(searchTime)) {
					try {
						ScannerResultNewsSnippet clone = (ScannerResultNewsSnippet) RemoveJsonUtil.clone(news);
						clone.setTracking(new ArrayList<ScannerResultNewsSnippetTrack>());
						clone.getTracking().add(track);
						last.add(clone);
					} catch (RemoveApplicationException e) {
						e.printStackTrace();
					}
				}
			});
		});
		
		return last; 
	}
	
	public List<ScannerResultImageSnippet> lastImagesSearch() {
		return imagesSearchByTime(findLastSearchTime());
	}
	public List<ScannerResultImageSnippet> imagesSearchByTime(LocalDateTime searchTime) {
		List<ScannerResultImageSnippet> last = new ArrayList<ScannerResultImageSnippet>();
		
		this.getSnippetsImages().forEach(image -> {
			image.getTracking().forEach(track -> {
				if (track.getDate_scan().isEqual(searchTime)) {
					try {
						ScannerResultImageSnippet clone = (ScannerResultImageSnippet) RemoveJsonUtil.clone(image);
						clone.setTracking(new ArrayList<ScannerResultImageSnippetTrack>());
						clone.getTracking().add(track);
						last.add(clone);
					} catch (RemoveApplicationException e) {
						e.printStackTrace();
					}
				}
			});
		});
		
		return last; 
	}

	public LocalDateTime findLastSearchTime() {
		LocalDateTime lastSearchTime = null;
		
		for (ScannerResultRaw raw : this.getRaws()) {
			if (lastSearchTime == null) {
				lastSearchTime = raw.getDate_scan();
			} else if (lastSearchTime.isBefore(raw.getDate_scan())) {
				lastSearchTime = raw.getDate_scan();
			}
		};
		
		return lastSearchTime != null ? lastSearchTime : LocalDateTime.now();
	}

	public void filterSnippetsByPage(int... numberPages) {
		if (numberPages != null) {
			if (this.snippetsImages != null && !this.snippetsImages.isEmpty()) {
				this.snippetsImages = this.snippetsImages.stream().filter(
						(snippet) -> {
							snippet.filterTrackingByPage(numberPages);
							return !snippet.getTracking().isEmpty();
						}
				).collect(Collectors.toList());
			}
			if (this.snippetsNews != null && !this.snippetsNews.isEmpty()) {
				this.snippetsNews = this.snippetsNews.stream().filter(
						(snippet) -> {
							snippet.filterTrackingByPage(numberPages);
							return !snippet.getTracking().isEmpty();
						}
				).collect(Collectors.toList());
			}
			if (this.snippetsWebs != null && !this.snippetsWebs.isEmpty()) {
				this.snippetsWebs = this.snippetsWebs.stream().filter(
						(snippet) -> {
							snippet.filterTrackingByPage(numberPages);
							return !snippet.getTracking().isEmpty();
						}
				).collect(Collectors.toList());
			}
		}
	}

	public void filterSnippetsByTrackingWords(String[] filterTrackingWords) {//FIXME:
		if (filterTrackingWords != null) {
			if (this.snippetsImages != null && !this.snippetsImages.isEmpty()) {
				this.snippetsImages = this.snippetsImages.stream().filter(snippet -> snippet.containsTrackingWord(filterTrackingWords)).collect(Collectors.toList());
			}
			if (this.snippetsNews != null && !this.snippetsNews.isEmpty()) {
				this.snippetsNews = this.snippetsNews.stream().filter(snippet -> snippet.containsTrackingWord(filterTrackingWords)).collect(Collectors.toList());
			}
			if (this.snippetsWebs != null && !this.snippetsWebs.isEmpty()) {
				this.snippetsWebs = this.snippetsWebs.stream().filter(snippet -> snippet.containsTrackingWord(filterTrackingWords)).collect(Collectors.toList());
			}
		}
	}

	public void filterSnippetsByFeeling(String[] feelings) {//FIXME:
		if (feelings != null) {
			if (this.snippetsImages != null && !this.snippetsImages.isEmpty()) {
				this.snippetsImages = this.snippetsImages.stream().filter(snippet -> snippet.containsFeelings(feelings)).collect(Collectors.toList());
			}
			if (this.snippetsNews != null && !this.snippetsNews.isEmpty()) {
				this.snippetsNews = this.snippetsNews.stream().filter(snippet -> snippet.containsFeelings(feelings)).collect(Collectors.toList());
			}
			if (this.snippetsWebs != null && !this.snippetsWebs.isEmpty()) {
				this.snippetsWebs = this.snippetsWebs.stream().filter(snippet -> snippet.containsFeelings(feelings)).collect(Collectors.toList());
			}
		}
	}

	public void filterSnippetsByUrl(String[] urls) { //FIXME:
		if (urls != null) {
			if (this.snippetsImages != null && !this.snippetsImages.isEmpty()) {
				this.snippetsImages = this.snippetsImages.stream().filter(snippet -> snippet.containsLinks(urls)).collect(Collectors.toList());
			}
			if (this.snippetsNews != null && !this.snippetsNews.isEmpty()) {
				this.snippetsNews = this.snippetsNews.stream().filter(snippet -> snippet.containsLinks(urls)).collect(Collectors.toList());
			}
			if (this.snippetsWebs != null && !this.snippetsWebs.isEmpty()) {
				this.snippetsWebs = this.snippetsWebs.stream().filter(snippet -> snippet.containsLinks(urls)).collect(Collectors.toList());
			}
		}
	}

	public void filterSnippetsByDate(LocalDate startDate, LocalDate endDate) {
		if (startDate != null && endDate != null) {
			if (this.snippetsImages != null && !this.snippetsImages.isEmpty()) {
				this.snippetsImages = this.snippetsImages.stream().filter(
						(snippet) -> {
							snippet.filterTrackingByRangeDate(startDate, endDate);
							return !snippet.getTracking().isEmpty();
						}
				).collect(Collectors.toList());
			}
			if (this.snippetsNews != null && !this.snippetsNews.isEmpty()) {
				this.snippetsNews = this.snippetsNews.stream().filter(
						(snippet) -> {
							snippet.filterTrackingByRangeDate(startDate, endDate);
							return !snippet.getTracking().isEmpty();
						}
				).collect(Collectors.toList());
			}
			if (this.snippetsWebs != null && !this.snippetsWebs.isEmpty()) {
				this.snippetsWebs = this.snippetsWebs.stream().filter(
						(snippet) -> {
							snippet.filterTrackingByRangeDate(startDate, endDate);
							return !snippet.getTracking().isEmpty();
						}
				).collect(Collectors.toList());
			}
		}
	}
}
