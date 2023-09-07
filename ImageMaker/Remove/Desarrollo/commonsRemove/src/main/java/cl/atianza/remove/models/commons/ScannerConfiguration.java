package cl.atianza.remove.models.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cl.atianza.remove.dtos.commons.ScannerConfigurationDto;
import joptsimple.internal.Strings;

public class ScannerConfiguration extends ScannerConfigurationDto {
	private List<Country> countries;
	private List<String> lstSearchTypes;

	public ScannerConfiguration() {
		super();
	}

	public List<Country> getCountries() {
		if (countries == null) {
			countries = new ArrayList<Country>();
		}
		
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public List<String> getLstSearchTypes() {
		if (lstSearchTypes == null) {
			this.lstSearchTypes = new ArrayList<String>();
		}
		return lstSearchTypes;
	}

	public void setLstSearchTypes(List<String> lstSearchTypes) {
		this.lstSearchTypes = lstSearchTypes;
	}

	@Override
	public String toString() {
		return "ScannerConfiguration [countries=" + countries + "]";
	}

	public void splitSearchTypes() {
		if (this.getSearch_type() != null) {
			this.setLstSearchTypes(Arrays.asList(this.getSearch_type().split(",")));
		}
	}

	public void joinSearchTypes() {
		if (this.getLstSearchTypes() != null) {
			this.setSearch_type(Strings.join(this.getLstSearchTypes(), ","));
		}
	}
}
