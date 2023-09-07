package cl.atianza.remove.models.views;

import java.util.ArrayList;
import java.util.List;

import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippet;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;

public class NotificationInOutSnippetsRecurrent {
	private String uuidScanner;
	private String name;
	private List<InfoSnippet> lstInSnippets = new ArrayList<InfoSnippet>();
	private List<InfoSnippet> lstOutSnippets = new ArrayList<InfoSnippet>();
	
	public NotificationInOutSnippetsRecurrent() {
		super();
	}
	
	public String getUuidScanner() {
		return uuidScanner;
	}

	public void setUuidScanner(String uuidScanner) {
		this.uuidScanner = uuidScanner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<InfoSnippet> getLstInSnippets() {
		return lstInSnippets;
	}
	public void setLstInSnippets(List<InfoSnippet> lstInSnippets) {
		this.lstInSnippets = lstInSnippets;
	}
	public List<InfoSnippet> getLstOutSnippets() {
		return lstOutSnippets;
	}
	public void setLstOutSnippets(List<InfoSnippet> lstOutSnippets) {
		this.lstOutSnippets = lstOutSnippets;
	}
	
	public void addInSnippet(ScannerResult result, ScannerResultWebSnippet snippet) {
		this.addIn(InfoSnippet.init(result, snippet));
	}
	public void addInSnippet(ScannerResult result, ScannerResultNewsSnippet snippet) {
		this.addIn(InfoSnippet.init(result, snippet));
	}
	public void addOutSnippet(ScannerResult result, ScannerResultWebSnippet snippet) {
		this.addOut(InfoSnippet.init(result, snippet));
	}
	public void addOutSnippet(ScannerResult result, ScannerResultNewsSnippet snippet) {
		this.addOut(InfoSnippet.init(result, snippet));
	}
	
	private void addIn(InfoSnippet info) {
		this.getLstInSnippets().add(info);
	}
	private void addOut(InfoSnippet info) {
		this.getLstOutSnippets().add(info);
	}
	
	@Override
	public String toString() {
		return "NotificationInOutSnippetsRecurrent [lstInSnippets=" + lstInSnippets + ", lstOutSnippets="
				+ lstOutSnippets + "]";
	}

	public boolean hasSnippets() {
		return !this.getLstInSnippets().isEmpty() || !this.getLstOutSnippets().isEmpty();
	}

	public void filterForRecurrent() {
		List<InfoSnippet> newInlist = new ArrayList<InfoSnippet>();
		this.getLstInSnippets().forEach(inSnippet -> {
			if (!inSnippet.getTrackingWords().isEmpty()) {
				newInlist.add(inSnippet);
			}
		});
		this.setLstInSnippets(newInlist);
		
		List<InfoSnippet> newOutlist = new ArrayList<InfoSnippet>();
		this.getLstOutSnippets().forEach(outSnippet -> {
			if (!outSnippet.getTrackingWords().isEmpty()) {
				newOutlist.add(outSnippet);
			}
		});
		this.setLstOutSnippets(newOutlist);
	}

	public void filterForTranform() {
		this.filterForRecurrent();
	}
}

