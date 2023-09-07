package cl.atianza.remove.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cl.atianza.remove.enums.EFeelings;
import cl.atianza.remove.models.commons.ScannerResultImageSnippet;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippet;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;
import cl.atianza.remove.models.commons.ScannerTrackingWord;

/**
 * Helper that check web snippets for tracking words.
 * @author pilin
 *
 */
public class HtmlInspectorHelper {
	private static Logger log = LogManager.getLogger(HtmlInspectorHelper.class);
			
	public static void inspect(ScannerResultWebSnippet snippet, List<ScannerTrackingWord> trackingWords) {
		if (trackingWords != null && !trackingWords.isEmpty()) {
			try {
				WordTracker tracker = inspect(trackingWords, snippet.getDomain(), snippet.getTitle(), snippet.getSnippet(), snippet.getLink());
				
				snippet.setFeeling(tracker.getFeeling() != null ? tracker.getFeeling().getTag() : EFeelings.NOT_APPLIED.getTag());
				snippet.setTrackingWords(tracker.getWords());
			} catch (IOException e) {
				log.error("Error inspecting: " + snippet.getLink(), e);
				snippet.setFeeling(EFeelings.NOT_APPLIED.getTag());
			}
		}
	}
	
	public static void inspect(ScannerResultNewsSnippet snippet, List<ScannerTrackingWord> trackingWords) {
//		log.info("HtmlInspectorHelper.inspect News: " + trackingWords);
		if (trackingWords != null && !trackingWords.isEmpty()) {
			try {
				WordTracker tracker = inspect(trackingWords, snippet.getDomain(), snippet.getTitle(), snippet.getSnippet(), snippet.getLink());
				
				snippet.setFeeling(tracker.getFeeling() != null ? tracker.getFeeling().getTag() : null);
				snippet.setTrackingWords(tracker.getWords());
			} catch (IOException e) {
				log.error("Error inspecting: " + snippet.getLink(), e);
				snippet.setFeeling(null);
			}
		}
	}

	public static void inspect(ScannerResultImageSnippet snippet, List<ScannerTrackingWord> trackingWords) {
//		log.info("HtmlInspectorHelper.inspect Images" + trackingWords);
		if (trackingWords != null && !trackingWords.isEmpty()) {
			try {
				WordTracker tracker = inspect(trackingWords, snippet.getDomain(), snippet.getTitle(), snippet.getDescription(), snippet.getLink());
				
				snippet.setFeeling(tracker.getFeeling() != null ? tracker.getFeeling().getTag() : null);
				snippet.setTrackingWords(tracker.getWords());
			} catch (IOException e) {
				log.error("Error inspecting: " + snippet.getLink(), e);
				snippet.setFeeling(null);
			}
		}
	}
	
	private static WordTracker inspect(List<ScannerTrackingWord> trackingWords, String domain, String title, String snippet, String link) throws IOException {
		WordTracker tracker = new WordTracker();
		
		for (ScannerTrackingWord tw : trackingWords) {
//			log.info("Evaluating Tracking word/url : (" + tw.getWord() +") " + " - " + tw.getType() + " | " + link);
			if (tw.getType().equals("URL")) {
				// if (domain.toUpperCase().equals(tw.getWord().toUpperCase())) { // Se cambia la verificaci�n de URL contra Link en lugar de Contra Domain...
				// 
				String urlToCompare = tw.getWord().trim().toUpperCase();
				if (urlToCompare.startsWith("HTTP://") || urlToCompare.startsWith("HTTPS://")) {
					if (link.trim().equalsIgnoreCase(urlToCompare)) {
						log.info("Tracking url found: (" + tw.getWord() +")");
						tracker.getWords().add(tw);
						tracker.setFeeling(calculateFeeling(tw, tracker.getFeeling()));
					}
				} else {
					if (link.trim().equalsIgnoreCase("HTTP://" + urlToCompare) || link.toUpperCase().equals("HTTPS://" + urlToCompare)) {
						log.info("Tracking url found: (" + tw.getWord() +")");
						tracker.getWords().add(tw);
						tracker.setFeeling(calculateFeeling(tw, tracker.getFeeling()));
					}	
				}
			} else if (tw.getType().equals("WORD")) {
				if (searchTrackingWord(tw, title, snippet)) {
					tracker.getWords().add(tw);
					tracker.setFeeling(calculateFeeling(tw, tracker.getFeeling()));
				} else {
					/*try {
						Document doc = Jsoup.connect(link).get(); // Commented for now (Need to handle timeouts, too much time inspecting)
						if (searchTrackingWord(tw, doc.text())) { // OR USE html()
							tracker.getWords().add(tw);
							tracker.setFeeling(calculateFeeling(tw, tracker.getFeeling()));
						}
					} catch (Throwable e) {
						log.error("Error inspecting URL tracking word: (" + link +")", e);
					}*/
				}
			}
		};
		
		return tracker;
	}
	
	private static boolean searchTrackingWord(ScannerTrackingWord tw, String... strings) {
		if (tw != null && strings != null) {
			for (String s : strings) {
				if (s != null && s.trim().toUpperCase().contains(tw.getWord().trim().toUpperCase())) {
					log.info("Tracking word found: (" + tw.getWord() +") in (" + s + ")");
					return true;
				}
			}
		}
		return false;
	}

	private static EFeelings calculateFeeling(ScannerTrackingWord trackingWordFound, EFeelings actualFeeling) {
		switch (actualFeeling) {
			case BAD:
				return EFeelings.BAD;
			case GOOD:
				return EFeelings.BAD.getTag().equals(trackingWordFound.getFeeling()) ? EFeelings.BAD : actualFeeling;
			case NEUTRAL:
				return EFeelings.BAD.getTag().equals(trackingWordFound.getFeeling()) || EFeelings.GOOD.getTag().equals(trackingWordFound.getFeeling()) ? 
						EFeelings.find(trackingWordFound.getFeeling()) : actualFeeling;
			default:
				return EFeelings.find(trackingWordFound.getFeeling());
		}
	}
}

class WordTracker {
	private EFeelings feeling = EFeelings.NOT_APPLIED;
	private List<ScannerTrackingWord> words = new ArrayList<ScannerTrackingWord>();
	
	public WordTracker() {
		super();
	}
	public EFeelings getFeeling() {
		return feeling;
	}
	public void setFeeling(EFeelings feeling) {
		this.feeling = feeling;
	}
	public List<ScannerTrackingWord> getWords() {
		return words;
	}
	public void setWords(List<ScannerTrackingWord> words) {
		this.words = words;
	}
	@Override
	public String toString() {
		return "WordTracker [feeling=" + feeling + ", words=" + words + "]";
	}
}