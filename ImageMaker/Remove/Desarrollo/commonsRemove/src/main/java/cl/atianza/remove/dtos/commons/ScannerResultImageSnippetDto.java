package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class ScannerResultImageSnippetDto extends AbstractDto {
	private Long id_scanner_result;
	private Long id_scanner_raw;
	private String title;
	private String link;
	private String domain;
	private Integer width;
	private Integer height;
	private String image;
	private String description;
	private String brand;
	private String feeling;
	
	public ScannerResultImageSnippetDto() {
		super();
	}

	public Long getId_scanner_result() {
		return id_scanner_result;
	}

	public void setId_scanner_result(Long id_scanner_result) {
		this.id_scanner_result = id_scanner_result;
	}

	public Long getId_scanner_raw() {
		return id_scanner_raw;
	}

	public void setId_scanner_raw(Long id_scanner_raw) {
		this.id_scanner_raw = id_scanner_raw;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getFeeling() {
		return feeling;
	}

	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}

	@Override
	public String toString() {
		return "ScannerResultImageSnippetDto [id_scanner_result=" + id_scanner_result + ", title=" + title + ", link="
				+ link + ", domain=" + domain + ", width=" + width + ", height=" + height + ", image=" + image
				+ ", description=" + description + ", brand=" + brand + "]";
	}
}
