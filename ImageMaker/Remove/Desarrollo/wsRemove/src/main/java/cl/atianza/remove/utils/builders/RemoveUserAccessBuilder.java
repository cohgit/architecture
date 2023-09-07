package cl.atianza.remove.utils.builders;

import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.views.ClientAccess;
import cl.atianza.remove.models.views.UserAccess;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveTokenAccess;

public class RemoveUserAccessBuilder {
	public static UserAccess buildAccessForUser(User user, String userAgent) throws RemoveApplicationException {
		UserAccess access = UserAccess.init();

		access.addConfig("lang", user.getLanguage());
		
		access.setFirst_time(user.isFirst_time());
		access.setName(user.getName() + " " + user.getLastname());
		access.setPermissions(RemovePermissionBuilder.init().buildByProfile(user.getProfile()));
		access.setToken(RemoveTokenAccess.initAdmin(user.getUuid(), 
				RemoveDateUtils.formatDateDash(RemoveDateUtils.nowLocalDateTime()), userAgent, user.getProfile()).getToken());
		access.setUuid(user.getUuid());
		
		return access;
	}
	
	public static ClientAccess buildAccessForClient(Client client, String userAgent) throws RemoveApplicationException {
		ClientAccess access = ClientAccess.init();

		access.addConfig("lang", client.getLanguage());
		access.addConfig("country", client.getCountry().getTag());
		
		access.setPlan(client.getPlanActives());
		access.setProject(client.getProject_name());
		
		access.setFirst_time(client.isFirst_time());
		access.setName(client.getName() + " " + client.getLastname());
		access.setPermissions(RemovePermissionBuilder.init().buildByClient(client));
		access.setToken(RemoveTokenAccess.initClient(client.getUuid(), 
				RemoveDateUtils.formatDateTimeDash(RemoveDateUtils.nowLocalDateTime()), userAgent).getToken());
		access.setReadOnly(client.isReadOnly());
		access.setMessage(client.getMessage());
		access.setUuid(client.getUuid());
		
		return access;
	}
}
