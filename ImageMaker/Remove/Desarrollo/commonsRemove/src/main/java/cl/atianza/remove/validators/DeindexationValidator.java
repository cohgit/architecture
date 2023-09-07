package cl.atianza.remove.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.commons.DeindexationDao;
import cl.atianza.remove.daos.commons.DeindexationUrlDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Deindexation;
import cl.atianza.remove.utils.RemoveTokenAccess;
@Deprecated
public class DeindexationValidator {
	private Logger log = LogManager.getLogger(DeindexationValidator.class);
	private Client client;
	private Deindexation deindexation;
	
	public DeindexationValidator(Client client, Deindexation deindexation) throws RemoveApplicationException {
		super();
		this.client = client;
		
		if (client!= null) log.info("Validating access to client: " + client.getUuid());
		ClientAccessValidator.init(client).validateClientAccess();
	}
	
	public static DeindexationValidator init(Client client) throws RemoveApplicationException {
		return new DeindexationValidator(client, null);
	}
	public static DeindexationValidator init(RemoveTokenAccess token) throws RemoveApplicationException {
		return new DeindexationValidator(ClientDao.init().getByUuid(token.getUuidAccount()), null);
	}
	public static DeindexationValidator init(String uuid) throws RemoveApplicationException {
		return new DeindexationValidator(ClientDao.init().getByUuid(uuid), null);
	}
	public static DeindexationValidator init(Long idClient) throws RemoveApplicationException {
		return new DeindexationValidator(ClientDao.init().getById(idClient), null);
	}

	public void validateClientDeindexation(Deindexation deindexation) throws RemoveApplicationException {
		this.deindexation = deindexation;
		
		if (this.client.getId().longValue() != deindexation.getId_owner().longValue()) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_DEINDEXATION_DONT_MATCH);
		}
	}
	public void validateClientDeindexation(Long idDeindexation) throws RemoveApplicationException {
		Deindexation deindex = DeindexationDao.init().getBasicById(idDeindexation);
		deindex.setUrls(DeindexationUrlDao.init().listByDeindexation(deindex.getId()));
		validateClientDeindexation(deindex);
	}
	
	public void checkDeindexationPlanLimitations(Deindexation deindexation) throws RemoveApplicationException {
		deindexation.setId_client_plan(this.client.getPlanActives().get(0).getId());
		this.deindexation = deindexation;
		int news = this.deindexation.countNewsUrls();
		int olds = DeindexationUrlDao.init().countByPlan(deindexation.getId_client_plan());
		int availables = this.client.getPlanActives().get(0).getDetail().getMax_url_to_deindexate().intValue() - olds;
		
		if (news > availables) RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_DEINDEXATION_EXCEEDS_CREDITS);
	}
}
