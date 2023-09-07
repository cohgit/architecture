package cl.atianza.remove.models.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.atianza.remove.dtos.commons.DeindexationDto;
import cl.atianza.remove.enums.EDeindexationStatus;
@Deprecated
public class Deindexation extends DeindexationDto {
	private Integer maxUrlsAvailables;
	private EDeindexationStatus statusDef;
	private List<DeindexationUrl> urls;
	private List<DeindexationHistoric> historic;
	private List<DeindexationFileEvidence> evidenceFiles;
	private List<DynamicFormConditionResult> results;
	
	private DynamicForm dynamicForm;
	private List<DeindexationConclusion> conclusions;
	
	public Deindexation() {
		super();
		this.urls = new ArrayList<DeindexationUrl>();
		this.historic = new ArrayList<DeindexationHistoric>();
		this.evidenceFiles = new ArrayList<DeindexationFileEvidence>();
	}
	
	public Integer getMaxUrlsAvailables() {
		return maxUrlsAvailables;
	}
	public void setMaxUrlsAvailables(Integer maxUrlsAvailables) {
		this.maxUrlsAvailables = maxUrlsAvailables;
	}
	public EDeindexationStatus getStatusDef() {
		if (statusDef == null && this.getStatus() != null) {
			this.statusDef = EDeindexationStatus.obtain(this.getStatus());
		}
		
		return statusDef;
	}
	public void setStatusDef(EDeindexationStatus statusDef) {
		this.statusDef = statusDef;
	}

	public DynamicForm getDynamicForm() {
		return dynamicForm;
	}
	public void setDynamicForm(DynamicForm dynamicForm) {
		this.dynamicForm = dynamicForm;
	}
	public List<DeindexationHistoric> getHistoric() {
		return historic;
	}
	public void setHistoric(List<DeindexationHistoric> historic) {
		this.historic = historic;
	}
	public List<DeindexationFileEvidence> getEvidenceFiles() {
		return evidenceFiles;
	}
	public void setEvidenceFiles(List<DeindexationFileEvidence> evidenceFiles) {
		this.evidenceFiles = evidenceFiles;
	}
	
	public List<DeindexationUrl> getUrls() {
		return urls;
	}
	public void setUrls(List<DeindexationUrl> urls) {
		this.urls = urls;
	}
	public List<DeindexationConclusion> getConclusions() {
		return conclusions;
	}
	public void setConclusions(List<DeindexationConclusion> conclusions) {
		this.conclusions = conclusions;
	}
	public List<DynamicFormConditionResult> getResults() {
		return results;
	}
	public void setResults(List<DynamicFormConditionResult> results) {
		this.results = results;
	}
	@Override
	public String toString() {
		return "Deindexation [historic=" + historic + ", evidenceFiles=" + evidenceFiles + "]";
	}
	
	public void fixLabelsAndDescriptions(String language) {
		if (this.getDynamicForm() != null) {
			this.getDynamicForm().fixLabelsAndDescriptions(language);
		}
	}
	public void splitResultsInTypes(String language) {
		String breakLine = " <br> ";
		this.conclusions = new ArrayList<DeindexationConclusion>();
		
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		
		this.getResults().forEach(result -> {
			if (data.get(result.getType()) == null) {
				data.put(result.getType(), new HashMap<String, String>());
			}
			
			result.getLabels().forEach(label -> {
				if (data.get(result.getType()).get(label.getLanguage()) == null) {
					data.get(result.getType()).put(label.getLanguage(), label.getLabel());
				} else {
					String text = data.get(result.getType()).get(label.getLanguage());
//					if (text.endsWith(".")) {
//						text += breakLine + label.getLabel();
//					} else if (text.endsWith(" ")) {
//						text += label.getLabel();
//					} else {
//						text += " " + label.getLabel();	
//					}
					
					text += breakLine + label.getLabel();
					
					data.get(result.getType()).put(label.getLanguage(), text);
				}
			});
		});
		
		if (!data.isEmpty()) {
			data.keySet().forEach(key -> {
				if (language != null) {
					this.conclusions.add(new DeindexationConclusion(key, data.get(key), data.get(key).get(language)));
				} else {
					this.conclusions.add(new DeindexationConclusion(key, data.get(key), null));
				}
			});
		}
	}
	public int countNewsUrls() {
		int total = 0;
		
		if (this.getUrls() != null) {
			for (DeindexationUrl url : this.getUrls()) {
				if (url.esNuevo()) total++;
			}
		}
		
		return total;
	}
}

class DeindexationConclusion {
	private String type;
	private Map<String, String> descriptions;
	private String text;
	
	public DeindexationConclusion() {
		super();
	}
	
	public DeindexationConclusion(String type, Map<String, String> descriptions, String text) {
		super();
		this.type = type;
		this.descriptions = descriptions;
		this.text = text;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, String> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(Map<String, String> descriptions) {
		this.descriptions = descriptions;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "DeindexationConclusion [type=" + type + ", descriptions=" + descriptions + ", text=" + text + "]";
	}
	
	
}