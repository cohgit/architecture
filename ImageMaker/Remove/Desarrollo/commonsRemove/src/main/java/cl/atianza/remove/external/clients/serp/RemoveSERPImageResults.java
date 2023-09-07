package cl.atianza.remove.external.clients.serp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class for images results in API SERP response.
 * @author pilin
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveSERPImageResults {
	private Integer position;
	private String title;
	private String link;
	private String domain;
	private Integer width;
	private Integer height;
	private String image;
	private String description;
	private String brand;
	private Object source;
	private Integer page;
	
	public RemoveSERPImageResults() {
		super();
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
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
	
	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "RemoveSERPImageResults [position=" + position + ", title=" + title + ", link=" + link + ", domain="
				+ domain + ", width=" + width + ", height=" + height + ", image=" + image + ", description="
				+ description + ", brand=" + brand + "]";
	}
}