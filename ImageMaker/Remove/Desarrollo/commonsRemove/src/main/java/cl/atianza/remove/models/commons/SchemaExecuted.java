package cl.atianza.remove.models.commons;

import cl.atianza.remove.dtos.commons.SchemaExecutedDto;

public class SchemaExecuted extends SchemaExecutedDto {
	private String query;

	public SchemaExecuted() {
		super();
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
