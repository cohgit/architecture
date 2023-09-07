package cl.atianza.remove.models.commons;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.commons.ScannerResultRawDto;

public class ScannerResultRaw extends ScannerResultRawDto {
	public ScannerResultRaw() {
		super();
	}

	public ScannerResultRaw(Long id, Long id_scanner_result, String raw) {
		super(id, id_scanner_result, raw);
	}

	public ScannerResultRaw(Long id_scanner_result, String raw) {
		super(id_scanner_result, raw);
	}

	public ScannerResultRaw(Long id_scanner_result, LocalDateTime date_scan, String raw) {
		super(id_scanner_result, date_scan, raw);
	}

	public ScannerResultRaw(Long id_scanner_result, LocalDateTime date_scan, Long scan_number, String type, String raw) {
		super(id_scanner_result, date_scan, scan_number, type, raw);
	}
}
