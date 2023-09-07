package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class ScannerConfigurationDto extends AbstractDto {
	private Long id_scanner;
	private String device;
	private String language;
	private Integer pages;
	private String search_type;
	private String cities;
	// Image Configuration
	private String images_color;
	private String images_size;
	private String images_type;
	private String images_use_rights;
	// News Configuration
	private String news_type;
	
	public ScannerConfigurationDto() {
		super();
	}

	public Long getId_scanner() {
		return id_scanner;
	}

	public void setId_scanner(Long id_scanner) {
		this.id_scanner = id_scanner;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public String getSearch_type() {
		return search_type;
	}

	public void setSearch_type(String search_type) {
		this.search_type = search_type;
	}

	public String getCities() {
		return cities;
	}

	public void setCities(String cities) {
		this.cities = cities;
	}

	public String getImages_color() {
		return images_color;
	}

	public void setImages_color(String images_color) {
		this.images_color = images_color;
	}

	public String getImages_size() {
		return images_size;
	}

	public void setImages_size(String images_size) {
		this.images_size = images_size;
	}

	public String getImages_type() {
		return images_type;
	}

	public void setImages_type(String images_type) {
		this.images_type = images_type;
	}

	public String getImages_use_rights() {
		return images_use_rights;
	}

	public void setImages_use_rights(String images_use_rights) {
		this.images_use_rights = images_use_rights;
	}

	public String getNews_type() {
		return news_type;
	}

	public void setNews_type(String news_type) {
		this.news_type = news_type;
	}

	@Override
	public String toString() {
		return "ScannerConfigurationDto [id_scanner=" + id_scanner + ", device=" + device + ", language=" + language
				+ ", pages=" + pages + ", search_type=" + search_type + ", cities=" + cities + ", images_color="
				+ images_color + ", images_size=" + images_size + ", images_type=" + images_type
				+ ", images_use_rights=" + images_use_rights + ", news_type=" + news_type + "]";
	}
}
