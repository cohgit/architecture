package cl.atianza.remove.models.commons;

import java.util.List;

import cl.atianza.remove.dtos.commons.ScannerImpulseContentDto;

public class ScannerImpulseContent extends ScannerImpulseContentDto {
	private List<ScannerImpulseObservation> observations;
	private String author_name;

	public ScannerImpulseContent() {
		super();
	}

	public List<ScannerImpulseObservation> getObservations() {
		return observations;
	}

	public void setObservations(List<ScannerImpulseObservation> observations) {
		this.observations = observations;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String owner_name) {
		this.author_name = owner_name;
	}

	@Override
	public String toString() {
		return "ScannerImpulseContent [" + super.toString() + ", observations=" + observations + "]";
	}
}
