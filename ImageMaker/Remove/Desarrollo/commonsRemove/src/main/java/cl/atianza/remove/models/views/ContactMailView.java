package cl.atianza.remove.models.views;

import java.util.ArrayList;
import java.util.List;

public class ContactMailView {
	private String title;
	private List<ContactAttsMailView> atts;
	
	public ContactMailView() {
		super();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ContactAttsMailView> getAtts() {
		if (atts == null) {
			this.atts = new ArrayList<ContactAttsMailView>();
		}
		
		return atts;
	}
	public void setAtts(List<ContactAttsMailView> atts) {
		this.atts = atts;
	}
	@Override
	public String toString() {
		return "ContactMailView [title=" + title + ", atts=" + atts + "]";
	}
}