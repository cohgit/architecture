package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class ScannerTrackingWordDto extends AbstractDto {
	private Long id_scanner;
	private String word;
	private String type;
	private String feeling;
	
	public ScannerTrackingWordDto() {
		super();
	}

	public ScannerTrackingWordDto(Long id, Long id_scanner, String word, String type, String feeling) {
		super(id);
		this.id_scanner = id_scanner;
		this.word = word;
		this.type = type;
		this.feeling = feeling;
	}


	public Long getId_scanner() {
		return id_scanner;
	}

	public void setId_scanner(Long id_scanner) {
		this.id_scanner = id_scanner;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFeeling() {
		return feeling;
	}

	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}

	@Override
	public String toString() {
		return "ScannerTrackingWord [id_scanner=" + id_scanner + ", word=" + word + ", type=" + type + ", feeling="
				+ feeling + "]";
	}
}
