package cl.atianza.remove.api.admin.clients;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.UserAuditAccessDao;
import cl.atianza.remove.daos.client.ClientAuditAccessDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPaybillDao;
import cl.atianza.remove.daos.client.ClientPaymentKeyDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPaybill;
import cl.atianza.remove.models.client.ClientPaymentKey;
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
public class AdminClientPaymentController extends RestController {
	public AdminClientPaymentController() {
		super(ERestPath.ADMIN_CLIENTS_PAYMENT, LogManager.getLogger(AdminClientPaymentController.class));
	}

	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			User user = UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			ClientPaybill payment = (ClientPaybill) RemoveRequestUtil.getBodyObject(request, ClientPaybill.class);
			ClientPaymentKey key = ClientPaymentKeyDao.init().getById(payment.getId_client_key());
			AccessToClientDataValidator.init(token).validateAccessOperation(key.getId_client());
			
			payment.setId_approved_by(user.getId());
			ClientPaybillDao.init().confirmPayment(payment);
			
			// change read only for client
			Client client = ClientDao.init().getBasicById(key.getId_client());
			
			if (client.isEmail_verified()) {
				ClientDao.init().updateReadOnly(client.getId(), false, "");
			} else {
				ClientDao.init().updateReadOnly(client.getId(), true, EMessageBundleKeys.WARNING_CLIENT_MUST_VERIFY_EMAIL.getTag());
			}
			
			UserAuditAccessDao.init().save(user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_PAYMENT_APPROVED);
			ClientAuditAccessDao.init().save(client.getId(), user.getId(), user.getProfile(), EMessageBundleKeys.MESSAGE_AUDIT_CLIENT_PAYMENT_APPROVED);
			
			return RemoveResponseUtil.buildDefaultOk();
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
