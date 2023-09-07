package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class ScannerKeywordDto extends AbstractDto {
	private Long id_scanner;
	private Long id_suggested_from;
	private Long id_country_suggested;
	private String word;
	private boolean suggested;
	
	public ScannerKeywordDto() {
		super();
	}

	public ScannerKeywordDto(Long id, Long id_scanner, Long id_suggested_from, String word, boolean suggested) {
		super(id);
		this.id_scanner = id_scanner;
		this.id_suggested_from = id_suggested_from;
		this.word = word;
		this.suggested = suggested;
	}

	public Long getId_scanner() {
		return id_scanner;
	}

	public void setId_scanner(Long id_scanner) {
		this.id_scanner = id_scanner;
	}

	public Long getId_suggested_from() {
		return id_suggested_from;
	}

	public void setId_suggested_from(Long id_suggested_from) {
		this.id_suggested_from = id_suggested_from;
	}

	public Long getId_country_suggested() {
		return id_country_suggested;
	}

	public void setId_country_suggested(Long id_country_suggested) {
		this.id_country_suggested = id_country_suggested;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public boolean isSuggested() {
		return suggested;
	}

	public void setSuggested(boolean suggested) {
		this.suggested = suggested;
	}

	@Override
	public String toString() {
		return "ScannerKeyword [id_scanner=" + id_scanner + ", id_suggested_from=" + id_suggested_from + ", word="
				+ word + ", suggested=" + suggested + "]";
	}
}
