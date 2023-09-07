package cl.atianza.remove.api.admin.clients;

import java.util.List;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPaybillDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.AccessToClientDataValidator;
import cl.atianza.remove.validators.UserProfileAccessValidator;
import spark.Request;
import spark.Response;

/**
 * Client account REST services for management.
 * @author pilin
 *
 */
public class AdminClientController extends RestController {
	public AdminClientController() {
		super(ERestPath.ADMIN_CLIENTS, LogManager.getLogger(AdminClientController.class));
	}

	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			Long clientId = RemoveRequestUtil.getParamLong(request, EWebParam.ID);
			AccessToClientDataValidator.init(token).validateAccessOperation(clientId);
			
			Client client = ClientDao.init().getById(clientId);
			
			if (client != null) {
				ClientPaybillDao.init().listConfirmedByClient(client);
			}
			/*try {
				client.getPayments().add(new ListExternalClientPayment(EPaymentMethods.STRIPE, PaymentStripeClient.init().listPaymentsForCustomer(client)));	
			} catch (RemoveApplicationException ex) {
				getLog().error("Error loading payments from Stripe: ", ex);
				client.setMessage("error.loading.payments.stripe");
			}*/
			
			return RemoveResponseUtil.buildOk(client);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException { // TODO: Esto se usa?
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			Client client = (Client) RemoveRequestUtil.getBodyObject(request, Client.class);
			client = ClientDao.init().save(client);
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_SAVED);
			ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_SAVED);
			
			return RemoveResponseUtil.buildOk(client);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object list(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LIST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccessList(this);
			List<Client> clients = ClientDao.init().listByUser(user.getId());
			if (clients.isEmpty()) {
				clients = ClientDao.init().listAll();
			}

			return RemoveResponseUtil.buildOk(clients);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			Client client = (Client) RemoveRequestUtil.getBodyObject(request, Client.class);
			AccessToClientDataValidator.init(token).validateAccessOperation(client.getId());
			client = ClientDao.init().update(client);
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_UPDATED);
			ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_UPDATED);
			
			return RemoveResponseUtil.buildOk(client);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object delete(Request request, Response response) throws RemoveApplicationException {
		getLog().info("DELETE: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			Long idClient = RemoveRequestUtil.getParamLong(request, EWebParam.ID);
			AccessToClientDataValidator.init(token).validateAccessOperation(idClient);
			
			Client client = ClientDao.init().switchActive(idClient);
			
			if (client.isActive()) {
				UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_SWITCH_ACTIVE);
				ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_SWITCH_ACTIVE);
			} else {
				UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_SWITCH_DEACTIVE);
				ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_SWITCH_DEACTIVE);
			}
			
			return RemoveResponseUtil.buildOk(client);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
