package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractNameDto;

public class CountryDto extends AbstractNameDto {
	private String tag;
	private String domain;
	private Boolean active;

	public CountryDto() {
		super();
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "CountryDto [tag=" + tag + ", domain=" + domain + ", active=" + active + "]";
	}
}
