package cl.atianza.remove.dtos.commons;

import java.time.LocalDate;
import cl.atianza.remove.dtos.AbstractDto;
@Deprecated
public class DeindexationUrlDto extends AbstractDto {
	private Long id_deindexation;
	private String url;
	private Boolean ask_google;
	private Boolean ask_media;
	
	private LocalDate publish_date;
	private Boolean sent_to_google;
	private LocalDate sent_to_google_date;
	private Boolean sent_to_media;
	private LocalDate sent_to_media_date;
	private Boolean google_approved;
	private Boolean media_approved;
	
	public DeindexationUrlDto() {
		super();
	}

	public Long getId_deindexation() {
		return id_deindexation;
	}

	public void setId_deindexation(Long id_deindexation) {
		this.id_deindexation = id_deindexation;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean isAsk_google() {
		return ask_google;
	}

	public void setAsk_google(Boolean ask_google) {
		this.ask_google = ask_google;
	}

	public Boolean isAsk_media() {
		return ask_media;
	}

	public void setAsk_media(Boolean ask_media) {
		this.ask_media = ask_media;
	}

	public LocalDate getPublish_date() {
		return publish_date;
	}

	public void setPublish_date(LocalDate publish_date) {
		this.publish_date = publish_date;
	}

	public Boolean isSent_to_google() {
		return sent_to_google;
	}

	public void setSent_to_google(Boolean sent_to_google) {
		this.sent_to_google = sent_to_google;
	}

	public LocalDate getSent_to_google_date() {
		return sent_to_google_date;
	}

	public void setSent_to_google_date(LocalDate sent_to_google_date) {
		this.sent_to_google_date = sent_to_google_date;
	}

	public Boolean isSent_to_media() {
		return sent_to_media;
	}

	public void setSent_to_media(Boolean sent_to_media) {
		this.sent_to_media = sent_to_media;
	}

	public LocalDate getSent_to_media_date() {
		return sent_to_media_date;
	}

	public void setSent_to_media_date(LocalDate sent_to_media_date) {
		this.sent_to_media_date = sent_to_media_date;
	}

	public Boolean isGoogle_approved() {
		return google_approved;
	}

	public void setGoogle_approved(Boolean google_approved) {
		this.google_approved = google_approved;
	}

	public Boolean isMedia_approved() {
		return media_approved;
	}

	public void setMedia_approved(Boolean media_approved) {
		this.media_approved = media_approved;
	}

	@Override
	public String toString() {
		return "DeindexationUrlDto [id_deindexation=" + id_deindexation + ", url=" + url + ", publish_date="
				+ publish_date + ", sent_to_google=" + sent_to_google + ", sent_to_google_date=" + sent_to_google_date
				+ ", sent_to_media=" + sent_to_media + ", sent_to_media_date=" + sent_to_media_date + "]";
	}
}
