package cl.atianza.remove.models.views;

import java.util.List;

import cl.atianza.remove.models.commons.Deindexation;

@Deprecated
public class ListDeindaxations {
	private Integer urlsAvailables;
	private List<Deindexation> list;

	public ListDeindaxations() {
		super();
	}

	public Integer getUrlsAvailables() {
		return urlsAvailables;
	}

	public void setUrlsAvailables(Integer urlsAvailables) {
		this.urlsAvailables = urlsAvailables;
	}

	public List<Deindexation> getList() {
		return list;
	}

	public void setList(List<Deindexation> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "ListDeindaxations [list=" + list + "]";
	}
	
	
}
