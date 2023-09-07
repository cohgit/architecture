package cl.atianza.remove.models.views;

import java.util.ArrayList;
import java.util.List;

import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerImpulse;

public class ImpulsesTableView {
	private Scanner scanner;
	private List<ScannerImpulse> impulses = new ArrayList<ScannerImpulse>();
	
	public ImpulsesTableView() {
		super();
	}
	public ImpulsesTableView(Scanner scanner, List<ScannerImpulse> impulses) {
		super();
		this.scanner = scanner;
		this.impulses = impulses;
	}

	public Scanner getScanner() {
		return scanner;
	}
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	public List<ScannerImpulse> getImpulses() {
		return impulses;
	}
	public void setImpulses(List<ScannerImpulse> impulses) {
		this.impulses = impulses;
	}
	@Override
	public String toString() {
		return "ImpulsesTableView [scanner=" + scanner + ", impulses=" + impulses + "]";
	}
}
