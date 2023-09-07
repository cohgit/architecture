package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;
import java.util.UUID;

import cl.atianza.remove.dtos.AbstractDto;
import cl.atianza.remove.utils.RemoveDateUtils;

public class ScannerResultRawDto extends AbstractDto {
	private Long id_scanner_result;
	private String uuid = UUID.randomUUID().toString();
	private Float rating_visualization;
	private Float rating_reputation;
	private LocalDateTime date_scan = RemoveDateUtils.nowLocalDateTime();
	private Long scan_number = 1l;
	private String type;
	private String raw;
	
	public ScannerResultRawDto() {
		super();
	}

	public ScannerResultRawDto(Long id_scanner_result, String raw) {
		super();
		this.id_scanner_result = id_scanner_result;
		this.raw = raw;
	}
	public ScannerResultRawDto(Long id, Long id_scanner_result, String raw) {
		super(id);
		this.id_scanner_result = id_scanner_result;
		this.raw = raw;
	}

	public ScannerResultRawDto(Long id_scanner_result, LocalDateTime date_scan, String raw) {
		super();
		this.id_scanner_result = id_scanner_result;
		this.date_scan = date_scan;
		this.raw = raw;
	}

	public ScannerResultRawDto(Long id_scanner_result, LocalDateTime date_scan, Long scan_number, String type,
			String raw) {
		super();
		this.id_scanner_result = id_scanner_result;
		this.date_scan = date_scan;
		this.scan_number = scan_number;
		this.type = type;
		this.raw = raw;
	}

	public Long getId_scanner_result() {
		return id_scanner_result;
	}

	public void setId_scanner_result(Long id_scanner_result) {
		this.id_scanner_result = id_scanner_result;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
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

	public LocalDateTime getDate_scan() {
		return date_scan;
	}

	public void setDate_scan(LocalDateTime date_scan) {
		this.date_scan = date_scan;
	}

	public Long getScan_number() {
		return scan_number;
	}

	public void setScan_number(Long scan_number) {
		this.scan_number = scan_number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ScannerResultRawDto [id_scanner_result=" + id_scanner_result + ", raw=" + raw + "]";
	}
}
