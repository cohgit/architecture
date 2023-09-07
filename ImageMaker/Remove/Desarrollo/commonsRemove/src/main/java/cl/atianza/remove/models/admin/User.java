package cl.atianza.remove.models.admin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cl.atianza.remove.dtos.admin.UserDto;
import cl.atianza.remove.enums.ELanguages;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.models.client.Client;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends UserDto {
	private EProfiles profileDef;
	private List<Client> clients;
	
	public User() {
		super();
	}

	public User(Long id) {
		super(id);
	}

	public EProfiles getProfileDef() {
		if (profileDef == null) {
			profileDef = EProfiles.obtain(this.getProfile());
		}
		return profileDef;
	}

	public void setProfileDef(EProfiles profileDef) {
		this.profileDef = profileDef;
	}

	public List<Client> getClients() {
		if (this.clients == null) {
			this.clients = new ArrayList<Client>();
		}
		
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	@Override
	public String toString() {
		return "User [clients=" + clients + "]";
	}

	public void checkAtts() {
		this.setEmail(this.getEmail().trim());
		if (this.getProfileDef() != null) {
			this.setProfile(this.getProfileDef().getCode());
		}
		if (this.getLanguage() == null) {
			this.setLanguage(ELanguages.SPANISH.getCode());
		}
	}
	
	public String fullName() {
		return this.getName() + " " + this.getLastname();
	}
}
