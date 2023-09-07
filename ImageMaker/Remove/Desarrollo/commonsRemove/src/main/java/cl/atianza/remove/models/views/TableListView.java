package cl.atianza.remove.models.views;

import java.util.List;

import cl.atianza.remove.dtos.AbstractDto;

/**
 * Model view for frontend tables.
 * 
 * @author Jose Gutierrez
 */
public class TableListView extends AbstractListView {
	private List<? extends AbstractDto> records;
	
	public TableListView() {
		super();
	}
	public TableListView(List<? extends AbstractDto> records) {
		super();
		this.records = records;
	}
	public TableListView(int totalRegistros, int totalPaginas, int numeroPagina, int registrosPorPagina,
			String orderField, String orderType, String likeSearch) {
		super(totalRegistros, totalPaginas, numeroPagina, registrosPorPagina, orderField, orderType, likeSearch);
	}

	public static TableListView init(int totalRegistros, int totalPaginas, int numeroPagina, int registrosPorPagina, String orderField,
			String orderType, String likeSearch, List<? extends AbstractDto> registros) {
		TableListView _newObject = new TableListView(totalRegistros, totalPaginas, numeroPagina, registrosPorPagina, orderField, orderType, likeSearch);
		_newObject.setRecords(registros);
		return _newObject;
	}
	
	public static TableListView init(List<? extends AbstractDto> records) {
		return new TableListView(records);
	}

	public List<? extends AbstractDto> getRecords() {
		return records;
	}
	public void setRecords(List<? extends AbstractDto> records) {
		this.records = records;
	}
	@Override
	public String toString() {
		return "TableListView [" + super.toString() + ", records=" + records + "]";
	}
	public static TableListView init(AbstractListView view, List<? extends AbstractDto> records) {
		TableListView tablaView = new TableListView();
		
		tablaView.setFilters(view.getFilters());
		tablaView.setLikeSearch(view.getLikeSearch());
		tablaView.setPageNumber(view.getPageNumber());
		tablaView.setOrderField(view.getOrderField());
		tablaView.setOrderType(view.getOrderType());
		tablaView.setRecordsByPage(view.getRecordsByPage());
		tablaView.setTotalPages(view.getTotalPages());
		tablaView.setTotalRecords(view.getTotalRecords());
		
		tablaView.setRecords(records);
		return tablaView;
	}
}
