package cl.atianza.remove.models.views;

import java.util.HashMap;
import java.util.Map;

import cl.atianza.remove.enums.EWebParam;

/**
 * Model view for frontend tables.
 * 
 * @author Jose Gutierrez
 */
public class AbstractListView {
	public static final String[] SORT = {"ASC","DESC"};
	
	// Atributos de la tabla
	private int totalRecords;
	private int totalPages;
	private int pageNumber;
	private int recordsByPage;
	
	// Atributos de Ordenado
	private String orderField;
	private String orderType;
	
	// Atributos de búsqueda
	private String likeSearch;
	
	private Map<String, String> filters;
	
	public AbstractListView() {
		super();
	}
	public AbstractListView(int totalRegistros, int totalPaginas, int numeroPagina, int registrosPorPagina, String orderField,
			String orderType, String likeSearch) {
		super();
		this.totalRecords = totalRegistros;
		this.totalPages = totalPaginas;
		this.pageNumber = numeroPagina;
		this.recordsByPage = registrosPorPagina;
		this.orderField = orderField;
		this.orderType = orderType;
		this.likeSearch = likeSearch;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getRecordsByPage() {
		return recordsByPage;
	}
	public void setRecordsByPage(int recordsByPage) {
		this.recordsByPage = recordsByPage;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getLikeSearch() {
		return likeSearch;
	}
	public void setLikeSearch(String likeSearch) {
		this.likeSearch = likeSearch;
	}
	public Map<String, String> getFilters() {
		return filters;
	}
	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}
	
	public void putParam(String key, String param) {
		if (this.filters == null) {
			this.filters = new HashMap<String, String>();
		}
		
		filters.put(key, param);
	}
	public void putParam(EWebParam webParam, String param) {
		this.putParam(webParam.getTag(), param);
	}
	@Override
	public String toString() {
		return "AbstractListView [totalRecords=" + totalRecords + ", totalPages=" + totalPages + ", pageNumber="
				+ pageNumber + ", recordsByPage=" + recordsByPage + ", orderField=" + orderField + ", orderType="
				+ orderType + ", likeSearch=" + likeSearch + ", filters=" + filters + "]";
	}
}