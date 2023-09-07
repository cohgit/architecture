package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class ScannerImpulseVariableDto extends AbstractDto {
	private Long id_scanner_impulse;
	private Long id_country;
	private String traffic_site;
	private String da;
	private String dr;
	private String pa;
	private Integer total_links;
	private Integer total_keywords;
	private String category_content;
	private Long id_author;
	private String author_profile;
	
	public ScannerImpulseVariableDto() {
		super();
	}

	public Long getId_scanner_impulse() {
		return id_scanner_impulse;
	}

	public void setId_scanner_impulse(Long id_scanner_impulse) {
		this.id_scanner_impulse = id_scanner_impulse;
	}

	public Long getId_country() {
		return id_country;
	}

	public void setId_country(Long id_country) {
		this.id_country = id_country;
	}

	public String getTraffic_site() {
		return traffic_site;
	}

	public void setTraffic_site(String traffic_site) {
		this.traffic_site = traffic_site;
	}

	public String getDa() {
		return da;
	}

	public void setDa(String da) {
		this.da = da;
	}

	public String getDr() {
		return dr;
	}

	public void setDr(String dr) {
		this.dr = dr;
	}

	public String getPa() {
		return pa;
	}

	public void setPa(String pa) {
		this.pa = pa;
	}

	public Integer getTotal_links() {
		return total_links;
	}

	public void setTotal_links(Integer total_links) {
		this.total_links = total_links;
	}

	public Integer getTotal_keywords() {
		return total_keywords;
	}

	public void setTotal_keywords(Integer total_keywords) {
		this.total_keywords = total_keywords;
	}

	public String getCategory_content() {
		return category_content;
	}

	public void setCategory_content(String category_content) {
		this.category_content = category_content;
	}

	public Long getId_author() {
		return id_author;
	}

	public void setId_author(Long id_author) {
		this.id_author = id_author;
	}

	public String getAuthor_profile() {
		return author_profile;
	}

	public void setAuthor_profile(String author_profile) {
		this.author_profile = author_profile;
	}

	@Override
	public String toString() {
		return "ScannerImpulseVariableDto [id_scanner_impulse=" + id_scanner_impulse + ", id_country=" + id_country
				+ ", traffic_site=" + traffic_site + ", da=" + da + ", dr=" + dr + ", pa=" + pa + ", total_links="
				+ total_links + ", total_keywords=" + total_keywords + ", category_content=" + category_content
				+ ", id_author=" + id_author + ", author_profile=" + author_profile + "]";
	}
}
