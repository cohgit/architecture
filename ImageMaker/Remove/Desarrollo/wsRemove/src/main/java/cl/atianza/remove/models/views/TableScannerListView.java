package cl.atianza.remove.models.views;

import java.util.List;

import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.models.commons.Scanner;

public class TableScannerListView extends TableListView {
	private ClientPlan restrictions;

	public TableScannerListView() {
		super();
	}

	public TableScannerListView(int totalRegistros, int totalPaginas, int numeroPagina, int registrosPorPagina,
			String orderField, String orderType, String likeSearch) {
		super(totalRegistros, totalPaginas, numeroPagina, registrosPorPagina, orderField, orderType, likeSearch);
	}
	
	public static TableScannerListView init(List<Scanner> records, ClientPlan restrictions) {
		TableScannerListView table = new TableScannerListView();
		table.setRecords(records);
		table.setRestrictions(restrictions);
		return table;
	}

	public ClientPlan getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(ClientPlan restrictions) {
		this.restrictions = restrictions;
	}

	@Override
	public String toString() {
		return "TableScannerListView [restrictions=" + restrictions + "]";
	}
}
