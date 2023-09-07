package cl.atianza.remove.models.views;

import java.util.List;

import cl.atianza.remove.models.commons.ScannerResultImageSnippet;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippet;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;

public class SnippetViewList {
	private List<ScannerResultWebSnippet> web;
	private List<ScannerResultImageSnippet> images;
	private List<ScannerResultNewsSnippet> news;
	
	public SnippetViewList() {
		super();
	}

	public List<ScannerResultWebSnippet> getWeb() {
		return web;
	}

	public void setWeb(List<ScannerResultWebSnippet> web) {
		this.web = web;
	}

	public List<ScannerResultImageSnippet> getImages() {
		return images;
	}

	public void setImages(List<ScannerResultImageSnippet> images) {
		this.images = images;
	}

	public List<ScannerResultNewsSnippet> getNews() {
		return news;
	}

	public void setNews(List<ScannerResultNewsSnippet> news) {
		this.news = news;
	}
	
	public Long findScannerResult() {
		if (this.web != null && !this.web.isEmpty()) {
			return this.web.get(0).getId_scanner_result();
		}
		if (this.images != null && !this.images.isEmpty()) {
			return this.images.get(0).getId_scanner_result();
		}
		if (this.news != null && !this.news.isEmpty()) {
			return this.news.get(0).getId_scanner_result();
		}
		return null;
	}

	@Override
	public String toString() {
		return "SnippetViewList [web=" + web + ", images=" + images + ", news=" + news + "]";
	}
}
