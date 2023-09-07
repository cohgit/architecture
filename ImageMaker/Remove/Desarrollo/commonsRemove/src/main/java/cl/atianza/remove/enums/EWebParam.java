package cl.atianza.remove.enums;

/**
 * Enum that list web service query parameters.
 * 
 * @author JoseGutierrez
 */
public enum EWebParam {
	TOKEN("token"),
	
	UUID("uuid"),
	UUID_CLIENT("uuid_client"),
	UUID_SCANNER("uuid_scanner"),
	EMAIL("email"),
	
	ID("id"),
	ID_CLIENT_PLAN("id_client_plan"),
	ID_SCANNER("id_scanner"),
	ID_IMPULSE("id_impulse"),
	SCANNER_TYPE("scanner_type"),
	ESTIMATED_DATE("estimated_date"),
	RESULT_TYPE("result_type"),
	TYPE("type"),
	FILE("file"),
	DATA("data"),
	OPERATION("operation"),
	
	FILTER_MONTH_YEAR("filterMonthYear"),
	
	TRANSACTION("transaction"),
	
	TYPE_REQUEST("typeRequest"),
	URL("url"),
	
	Q("q"),
	HL("hl"),
	GL("gl"),
	
	PATH("path"),
	
	TOTAL("total");
	
	private String tag;
	
	private EWebParam(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;			
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
