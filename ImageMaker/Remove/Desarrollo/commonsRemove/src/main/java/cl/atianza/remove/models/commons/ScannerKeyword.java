package cl.atianza.remove.models.commons;

import java.util.List;

import cl.atianza.remove.dtos.commons.ScannerKeywordDto;

public class ScannerKeyword extends ScannerKeywordDto {
	private List<ScannerKeyword> listSuggested;
	private Country countryObj;
	private boolean checked = false;

	public ScannerKeyword() {
		super();
	}

	public ScannerKeyword(Long id, Long id_scanner, Long id_suggested_from, String word, boolean suggested) {
		super(id, id_scanner, id_suggested_from, word, suggested);
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Country getCountryObj() {
		return countryObj;
	}

	public void setCountryObj(Country countryObj) {
		this.countryObj = countryObj;
	}

	public List<ScannerKeyword> getListSuggested() {
		return listSuggested;
	}

	public void setListSuggested(List<ScannerKeyword> listSuggested) {
		this.listSuggested = listSuggested;
	}
}
