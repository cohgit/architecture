package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractNameDto;

public class ForbiddenWordDto extends AbstractNameDto {
	private String word;
	private String languages;

	public ForbiddenWordDto() {
		super();
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	@Override
	public String toString() {
		return "ForbiddenWordDto [word=" + word + ", languages=" + languages + "]";
	}
}
