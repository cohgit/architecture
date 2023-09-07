package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;
import java.util.UUID;

import cl.atianza.remove.dtos.AbstractDto;

public class ScannerResultDto extends AbstractDto {
	private String uuid = UUID.randomUUID().toString();
	private Long id_scanner;
	private Long id_country;
	private Long id_keyword;
	private Float rating_visualization;
	private Float rating_reputation;
	private Integer total_last_scan;
	private Float progress;
	private String language;
	private String device;
	private String search_type;
	private String city;
	private LocalDateTime query_date;
	
	public ScannerResultDto() {
		super();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getId_scanner() {
		return id_scanner;
	}

	public void setId_scanner(Long id_scanner) {
		this.id_scanner = id_scanner;
	}

	public Long getId_country() {
		return id_country;
	}

	public void setId_country(Long id_country) {
		this.id_country = id_country;
	}

	public Long getId_keyword() {
		return id_keyword;
	}

	public void setId_keyword(Long id_keyword) {
		this.id_keyword = id_keyword;
	}

	public Float getRating_visualization() {
		return rating_visualization;
	}

	public void setRating_visualization(Float rating_visualization) {
		this.rating_visualization = rating_visualization;
	}

	public Float getRating_reputation() {
		return rating_reputation;
	}

	public void setRating_reputation(Float rating_reputation) {
		this.rating_reputation = rating_reputation;
	}

	public Integer getTotal_last_scan() {
		return total_last_scan;
	}

	public void setTotal_last_scan(Integer total_last_scan) {
		this.total_last_scan = total_last_scan;
	}

	public Float getProgress() {
		return progress;
	}

	public void setProgress(Float progress) {
		this.progress = progress;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getSearch_type() {
		return search_type;
	}

	public void setSearch_type(String search_type) {
		this.search_type = search_type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public LocalDateTime getQuery_date() {
		return query_date;
	}

	public void setQuery_date(LocalDateTime query_date) {
		this.query_date = query_date;
	}

	@Override
	public String toString() {
		return "ScannerResultDto [uuid=" + uuid + ", id_scanner=" + id_scanner + ", id_country=" + id_country
				+ ", id_keyword=" + id_keyword + ", language=" + language + ", device=" + device + ", search_type="
				+ search_type + ", city=" + city + ", query_date=" + query_date + "]";
	}
}
