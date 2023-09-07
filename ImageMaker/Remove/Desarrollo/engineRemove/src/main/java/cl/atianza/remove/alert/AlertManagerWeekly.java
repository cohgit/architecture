package cl.atianza.remove.alert;

import java.util.TimerTask;

import cl.atianza.remove.utils.AlertUtilsWeekly;

public class AlertManagerWeekly extends TimerTask{
	
	
	public AlertManagerWeekly() {
		super();
	}
	
	@Override
	public void run() {
		AlertUtilsWeekly alert = new AlertUtilsWeekly();
		alert.alertInitialize();
	
	}
}
