package cl.atianza.remove.models.views;

import java.util.ArrayList;
import java.util.List;

import cl.atianza.remove.models.admin.UserAlertMessage;
import cl.atianza.remove.models.client.ClientAlertMessage;

public class NotificationUpdates {
	private String field;
	private boolean value;
	private List<ClientAlertMessage> clientsAlerts;
	private List<UserAlertMessage> userAlerts;
	
	public NotificationUpdates() {
		super();
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public List<ClientAlertMessage> getClientsAlerts() {
		if (clientsAlerts == null) {
			this.clientsAlerts = new ArrayList<ClientAlertMessage>();
		}
		
		return clientsAlerts;
	}
	public void setClientsAlerts(List<ClientAlertMessage> clientsAlerts) {
		this.clientsAlerts = clientsAlerts;
	}
	public List<UserAlertMessage> getUserAlerts() {
		if (userAlerts == null) {
			this.userAlerts = new ArrayList<UserAlertMessage>();
		}
		return userAlerts;
	}
	public void setUserAlerts(List<UserAlertMessage> userAlerts) {
		this.userAlerts = userAlerts;
	}
	
	@Override
	public String toString() {
		return "NotificationUpdates [field=" + field + ", clientsAlerts=" + clientsAlerts + ", userAlerts=" + userAlerts
				+ "]";
	}
}
