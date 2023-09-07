package cl.atianza.remove.models.commons;

import java.util.ArrayList;
import java.util.List;

import cl.atianza.remove.dtos.commons.ScannerImpulseVariableDto;

public class ScannerImpulseVariable extends ScannerImpulseVariableDto {
	private Country country;
	private List<ScannerImpulseInteraction> interactions;

	public ScannerImpulseVariable() {
		super();
		this.interactions = new ArrayList<ScannerImpulseInteraction>();
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<ScannerImpulseInteraction> getInteractions() {
		return interactions;
	}

	public void setInteractions(List<ScannerImpulseInteraction> interactions) {
		this.interactions = interactions;
	}
}
