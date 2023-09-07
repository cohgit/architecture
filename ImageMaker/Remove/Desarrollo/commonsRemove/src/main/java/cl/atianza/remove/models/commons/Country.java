package cl.atianza.remove.models.commons;

import cl.atianza.remove.dtos.commons.CountryDto;

public class Country extends CountryDto {
	public String getFullDomain() {
		return this.getDomain() != null ? "google." + this.getDomain() : null;
	}
	public void setFullDomain(String fullDomain) {}
}
