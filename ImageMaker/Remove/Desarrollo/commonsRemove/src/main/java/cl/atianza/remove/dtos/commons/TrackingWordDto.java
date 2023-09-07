package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractNameDto;

public class TrackingWordDto extends AbstractNameDto {
	private String word;
	private String feeling;
	private Boolean active;

	public TrackingWordDto() {
		super();
	}
	public TrackingWordDto(String word, String feeling, Boolean active) {
		super();
		this.word = word;
		this.feeling = feeling;
		this.active = active;
	}


	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getFeeling() {
		return feeling;
	}

	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "TrackingWordDto [word=" + word + ", feeling=" + feeling + ", active=" + active + "]";
	}
}
