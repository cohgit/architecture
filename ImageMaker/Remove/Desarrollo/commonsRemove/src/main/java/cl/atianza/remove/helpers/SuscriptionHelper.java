package cl.atianza.remove.helpers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.admin.PlanClientSuggestionDao;
import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientEmailVerificationRequestDao;
import cl.atianza.remove.daos.client.ClientPasswordDao;
import cl.atianza.remove.daos.client.ClientPaybillDao;
import cl.atianza.remove.daos.client.ClientPaymentKeyDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.client.ClientPlanMigrationDao;
import cl.atianza.remove.daos.client.ClientSuscriptionAttemptDao;
import cl.atianza.remove.daos.commons.CountryDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.enums.ECountries;
import cl.atianza.remove.enums.ELanguages;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EPaymentMethods;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.enums.EScannerStatus;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.external.clients.payments.ExternalPaymentMethod;
import cl.atianza.remove.external.clients.payments.ExternalPaymentReference;
import cl.atianza.remove.external.clients.payments.stripe.PaymentStripeClient;
import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPassword;
import cl.atianza.remove.models.client.ClientPaybill;
import cl.atianza.remove.models.client.ClientPaymentKey;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.models.client.ClientPlanMigration;
import cl.atianza.remove.models.client.ClientSuscriptionAttempt;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.views.ClientSuscription;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveResponse;
import cl.atianza.remove.utils.RemoveResponseUtil;

public class SuscriptionHelper {
	protected Logger log = LogManager.getLogger(SuscriptionHelper.class);
	
	public static SuscriptionHelper init() {
		return new SuscriptionHelper();
	}
	
	/**
	 * 
	 * @param suscription
	 * @return
	 * @throws RemoveApplicationException
	 */
	public String saveSuscribeAttempt(ClientSuscription suscription) throws RemoveApplicationException {
		ClientSuscriptionAttempt attempt = new ClientSuscriptionAttempt();
		
		attempt.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		attempt.setEmail(suscription.getClient().getEmail());
		attempt.setId_plan(suscription.getPlan().getId());
		attempt.setLastname(suscription.getClient().getLastname());
		attempt.setName(suscription.getClient().getName());
		
		ClientSuscriptionAttemptDao.init().save(attempt);
		
		return RemoveResponseUtil.buildDefaultOk();
	}
	
	/**
	 * 
	 * @param suscription
	 * @return
	 * @throws RemoveApplicationException
	 */
	public String suscribe(ClientSuscription suscription) throws RemoveApplicationException {
		String validation = validateSuscription(suscription);
		if (validation != null) {
			return validation;
		}
		
		if (suscription.getPaymentMethod().equals(EPaymentMethods.STRIPE.getCode())) {
			return saveSuscriptionAsStripe(suscription);
		} else if (suscription.getPaymentMethod().equals(EPaymentMethods.TRANSFER.getCode())) {
			return saveSuscription(suscription, EPaymentMethods.TRANSFER.getCode());
		}
		
		return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS); 
	}
	
	private String validateSuscription(ClientSuscription suscription) throws RemoveApplicationException {
		ClientDao clientDao = ClientDao.init();
		
		Client old = clientDao.getByLogin(suscription.getClient().getEmail());
		if (old != null) {
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_CLIENT_DUPLICATED);
		}
		
		old = clientDao.getByPhone(suscription.getClient().getPhone());
		if (old != null) {
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_CLIENT_DUPLICATED);
		}
		
		return null;
	}

	/**
	 * 
	 * @param clientPlan
	 * @param paybill
	 * @param isConfirmedPayment
	 * @throws RemoveApplicationException
	 */
	public void renew(ClientPlan clientPlan, ClientPaybill paybill, boolean isConfirmedPayment) throws RemoveApplicationException {
		LocalDateTime now = RemoveDateUtils.nowLocalDateTime();
		Plan plan = PlanDao.init().get(clientPlan.getId_plan());
		Long oldId = clientPlan.getId();
		List<Scanner> scanners = ScannerDao.init().listActivesByClientPlanId(oldId);
		
		ClientPlanDao.init().cancel(clientPlan);
		
		paybill.setConfirmed(isConfirmedPayment);
		paybill = ClientPaybillDao.init().save(paybill);
		
		clientPlan.setId_client_paybills(paybill.getId());
		clientPlan.setCredits_used(0l);
		clientPlan.setDeindex_used(0l);
		clientPlan.setSuscribe_date(now);
		clientPlan.setCancellation_date(null);
		clientPlan.setActive(true);
		
		if (plan.getDuration_months() != null) {
			clientPlan.setExpiration_date(clientPlan.getSuscribe_date().plusMonths(plan.getDuration_months().intValue()));	
		} else {
			clientPlan.setExpiration_date(null);
		}
		
		ClientPlanDao.init().save(clientPlan);
		
		List<Long> idsToMigrate = new ArrayList<Long>();
		if (scanners != null && !scanners.isEmpty()) {
			scanners.forEach(scanner -> {
				//if ((scanner.esRecurrent() || scanner.esTransform())) {
					idsToMigrate.add(scanner.getId());					
				//}
			});
		}
		
		ScannerDao.init().updateClientPlanId(clientPlan.getId(), oldId, idsToMigrate);
	}
	
	/**
	 * 
	 * @param clientPlan
	 * @param plan
	 * @throws RemoveApplicationException
	 */
	public void verifyRenewForTransferSuscriptions(ClientPlan clientPlan, Plan plan) throws RemoveApplicationException {
		log.info("Verifying renovation for Transfer Suscription: " + clientPlan);
		LocalDateTime now = LocalDateTime.now();
		ClientPlan lastSuscription = ClientPlanDao.init().getLastSuscriptionByClient(clientPlan.getId_client());
		ClientPlanDao clientPlanDao = ClientPlanDao.init();
		
		if (clientPlan.getExpiration_date() != null) {
			if (clientPlan.getExpiration_date().isBefore(now)) { // La suscripcion expiro, renovar a suscripcion provisional
				log.info("Suscription Expired: " + clientPlan);
				if (plan.isAutomatic_renewal()) {
					log.info("Renewing Suscription: " + clientPlan);
					if (lastSuscription.getId().longValue() == clientPlan.getId().longValue()) { // No se ha creado suscripcion provisional, se crea
						lastSuscription = createProvisionalSuscription(clientPlan, clientPlan.getExpiration_date());
						lastSuscription.setPaybill(ClientPaybillDao.init().getById(lastSuscription.getId_client_paybills()));
					} 
					
					// Existe suscripcion provisional, se migra todo el plan
					migrateScannersAndServices(clientPlan, lastSuscription);
					
					clientPlanDao.cancel(clientPlan);
					clientPlanDao.activate(lastSuscription);
					
					if (!lastSuscription.getPaybill().isConfirmed()) {						 // El pago esta confirmado, se realiza la migracion
						setClientReadOnlyForDuePayment(clientPlan);
					}
				} else {
					cancel(clientPlan);
				}
			}
			
			if (clientPlan.getExpiration_date().minusDays(10).isBefore(now) && lastSuscription.getId().longValue() == clientPlan.getId().longValue() && plan.isAutomatic_renewal()) { // No se ha creado la suscripcion provisional
				createProvisionalSuscription(clientPlan, clientPlan.getExpiration_date());
			} else {
				log.info("Suscription in order: " + clientPlan);
			}
		} else {
			log.info("Suscription doesn't have expiration date: " + clientPlan);
		}
	}

	/**
	 * 
	 * @param oldSuscription
	 * @param newSuscription
	 * @throws RemoveApplicationException
	 */
	private void migrateScannersAndServices(ClientPlan oldSuscription, ClientPlan newSuscription) throws RemoveApplicationException {
		migrateScannersAndServices(ScannerDao.init().listActivesByClientPlanId(oldSuscription.getId()), oldSuscription, newSuscription); 
	}
	/**
	 * 
	 * @param scanners
	 * @param oldSuscription
	 * @param newSuscription
	 * @throws RemoveApplicationException
	 */
	private void migrateScannersAndServices(List<Scanner> scanners, ClientPlan oldSuscription, ClientPlan newSuscription) throws RemoveApplicationException {
		log.info("Migrating scanners and services: " + oldSuscription + " | to: " + newSuscription);
		List<Long> idsToMigrate = new ArrayList<Long>();
		if (scanners != null && !scanners.isEmpty()) {
			scanners.forEach(scanner -> {
				//if ((scanner.esRecurrent() || scanner.esTransform())) {
					idsToMigrate.add(scanner.getId());
				//}
			});
		}
		
		ScannerDao.init().updateClientPlanId(newSuscription.getId(), oldSuscription.getId(), idsToMigrate);
	}

	/**
	 * 
	 * @param clientPlan
	 * @param initTime
	 * @return
	 * @throws RemoveApplicationException
	 */
	public ClientPlan createProvisionalSuscription(ClientPlan clientPlan, LocalDateTime initTime) throws RemoveApplicationException {
		log.info("Creating provisional suscription for transfer suscription: " + clientPlan);
		Plan plan = PlanDao.init().get(clientPlan.getId_plan());
		
		ClientPaymentKey paymentKey = ClientPaymentKeyDao.init().getByClientAndPaymentMethod(clientPlan.getId_client(), EPaymentMethods.TRANSFER);
		
		ClientPaybill paybill = new ClientPaybill();
		paybill.setId_client_key(paymentKey.getId());
		paybill.setId_plan(clientPlan.getId_plan());
		paybill.setPayment_date(LocalDateTime.now());
		paybill.setAmount(clientPlan.getDetail().getCost());
		
		paybill.setConfirmed(false);
		paybill = ClientPaybillDao.init().save(paybill);
		
		clientPlan.setId_client_paybills(paybill.getId());
		clientPlan.setCredits_used(0l);
		clientPlan.setDeindex_used(0l);
		clientPlan.setSuscribe_date(initTime);
		clientPlan.setCancellation_date(null);
		clientPlan.setActive(false);
		
		if (plan.getDuration_months() != null) {
			clientPlan.setExpiration_date(clientPlan.getSuscribe_date().plusMonths(plan.getDuration_months().intValue()));	
		} else {
			clientPlan.setExpiration_date(null);
		}
		
		return ClientPlanDao.init().save(clientPlan);
	}
	
	/**
	 * 
	 * @param clientPlan
	 * @throws RemoveApplicationException
	 */
	public void cancel(ClientPlan clientPlan) throws RemoveApplicationException {
		log.info("Canceling Suscription: ", clientPlan);
		ClientPlanDao.init().cancel(clientPlan);
	}
	
	/**
	 * 
	 * @param clientPlan
	 * @throws RemoveApplicationException
	 */
	private void setClientReadOnlyForDuePayment(ClientPlan clientPlan) throws RemoveApplicationException {
		log.info("Setting client ReadOnly for due payment: " + clientPlan);
		ClientDao.init().updateReadOnly(clientPlan.getId_client(), true, EMessageBundleKeys.WARNING_PAYMENT_PAST_DUE.getTag());
		ScannerDao.init().changeStatusByOwner(clientPlan.getId_client(), EScannerStatus.EXECUTING, EScannerStatus.SUSPENDED);
		ScannerDao.init().changeStatusByOwner(clientPlan.getId_client(), EScannerStatus.ACTIVE, EScannerStatus.SUSPENDED);
	}
	
	/**
	 * 
	 * @param suscription
	 * @return
	 * @throws RemoveApplicationException
	 */
	private String saveSuscriptionAsStripe(ClientSuscription suscription) throws RemoveApplicationException {
		LocalDateTime now = RemoveDateUtils.nowLocalDateTime();
		Plan plan = PlanDao.init().get(suscription.getPlan().getId());
		
		RemoveResponse resp = PaymentStripeClient.init().validatePayment(suscription.getPaymentTrackingCode());
		
		if (resp.isSuccess() || resp.isWarning()) {
			ExternalPaymentReference reference = (ExternalPaymentReference) resp.getData();
			ClientPaybill recordedPaybill = ClientPaybillDao.init().findByTrackingCode(reference.getClientPaybill().getTracking_code());
			
			if (recordedPaybill != null) {
				log.error("Invalid payment: is duplicated... " + suscription);
				log.error("Invalid payment: Reference of fail: " + recordedPaybill);
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_PAYMENT_CONFIRMATION);
			}
			
			if (!reference.getClientPaybill().isConfirmed()) {
				log.error("Invalid payment: payment isn't 'paid'... " + suscription);
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_PAYMENT_CONFIRMATION);
			}
			
			Client client = saveClient(suscription);
			
			reference.getClientPaymentKey().setId_client(client.getId());
			reference.setClientPaymentKey(ClientPaymentKeyDao.init().save(reference.getClientPaymentKey()));
			
			reference.getClientPaybill().setId_client_key(reference.getClientPaymentKey().getId());
			reference.getClientPaybill().setId_plan(suscription.getPlan().getId());
			reference.setClientPaybill(ClientPaybillDao.init().save(reference.getClientPaybill()));
			
			savePassword(suscription, client, now);
			saveClientPlan(reference.getClientPaybill(), plan, client, now, reference.getSubscriptionTrackingCode(), EPaymentMethods.STRIPE.getCode());

			if (suscription.getUuidSuscription() != null) {
				PlanClientSuggestionDao.init().updateAttempted(suscription.getUuidSuscription());
				PlanClientSuggestionDao.init().updateRegistred(suscription.getUuidSuscription());	
			}
			
			return RemoveResponseUtil.buildDefaultOk();
		} else {
			log.error("Error calling stripe services for payment confirmation:", suscription);
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_PAYMENT_CONFIRMATION);
		}
	}
	
	/**
	 * 
	 * @param suscription
	 * @param paymentPlatform
	 * @return
	 * @throws RemoveApplicationException
	 */
	private String saveSuscription(ClientSuscription suscription, String paymentPlatform) throws RemoveApplicationException {
		LocalDateTime now = RemoveDateUtils.nowLocalDateTime();
		Plan plan = PlanDao.init().get(suscription.getPlan().getId());
		
		Client client = saveClient(suscription);
		savePassword(suscription, client, now);
		ClientPaymentKey paymentKey = savePaymentKey(client, suscription.getPaymentMethod());
		ClientPaybill paybill = savePaybill(paymentKey, plan);
		saveClientPlan(paybill, plan, client, now, null, paymentPlatform);
		
		if (suscription.getUuidSuscription() != null) {
			PlanClientSuggestionDao.init().updateAttempted(suscription.getUuidSuscription());
			PlanClientSuggestionDao.init().updateRegistred(suscription.getUuidSuscription());	
		}
		
		sendNewSuscriptionNotifications(suscription);
		
		return RemoveResponseUtil.buildDefaultOk();
	}

	private void sendNewSuscriptionNotifications(ClientSuscription suscription) {
		try {
			List<User> admins = UserDao.init().listByProfile(EProfiles.ADMIN);
			
			if (admins != null && !admins.isEmpty()) {
				admins.forEach(admin -> {
					try {
						UserNotificationHelper.init(admin).createNewSuscriptionNotification(suscription);
					} catch (RemoveApplicationException e) {
						log.error("Couldn't send new suscription notification: " + admin + " | " + suscription, e);
					}
				});
			} else {
				log.warn("There are no admins to send new suscription notification:", suscription);
			}
		} catch (RemoveApplicationException e) {
			log.error("Error sending admins notifications:", e);
		}
	}

	/**
	 * 
	 * @param paybill
	 * @param plan
	 * @param client
	 * @param now
	 * @param trackingCode
	 * @param platform
	 * @throws RemoveApplicationException
	 */
	private void saveClientPlan(ClientPaybill paybill, Plan plan, Client client, LocalDateTime now, String trackingCode, String platform) throws RemoveApplicationException {
		ClientPlan clientPlan = new ClientPlan();
		clientPlan.setId_client_paybills(paybill.getId());
		clientPlan.setActive(true);
		clientPlan.setCredits_used(0l);
		clientPlan.setDetail(plan);
		clientPlan.setId_client(client.getId());
		clientPlan.setId_plan(plan.getId());
		clientPlan.setSuscribe_date(now);
		
		if (plan.getDuration_months() != null) {
			clientPlan.setExpiration_date(clientPlan.getSuscribe_date().plusMonths(plan.getDuration_months().intValue()));	
		}
		
		clientPlan.setExternal_subscription_key(trackingCode);
		clientPlan.setExternal_subscription_platform(platform);
		ClientPlanDao.init().save(clientPlan);
		
		ClientEmailVerificationRequestDao.init().createRequest(client);
	}

	/**
	 * 
	 * @param paymentKey
	 * @param plan
	 * @return
	 * @throws RemoveApplicationException
	 */
	private ClientPaybill savePaybill(ClientPaymentKey paymentKey, Plan plan) throws RemoveApplicationException {
		ClientPaybill paybill = new ClientPaybill();
		paybill.setId_client_key(paymentKey.getId());
		paybill.setId_plan(plan.getId());
		paybill.setPayment_date(LocalDateTime.now());
		paybill.setConfirmed(plan.esCostZero());	
		paybill.setAmount(plan.getCost());
		return ClientPaybillDao.init().save(paybill);
	}
	/**
	 * 
	 * @param client
	 * @param paymentMethod
	 * @return
	 * @throws RemoveApplicationException
	 */
	private ClientPaymentKey savePaymentKey(Client client, String paymentMethod) throws RemoveApplicationException {
		ClientPaymentKey paymentKey = new ClientPaymentKey();
		paymentKey.setId_client(client.getId());
		paymentKey.setActive(true);
		paymentKey.setKey(UUID.randomUUID().toString());
		paymentKey.setPlatform(paymentMethod);
		return ClientPaymentKeyDao.init().save(paymentKey);
	}

	/**
	 * 
	 * @param suscription
	 * @param client
	 * @param now
	 * @throws RemoveApplicationException
	 */
	private void savePassword(ClientSuscription suscription, Client client, LocalDateTime now) throws RemoveApplicationException {
		ClientPassword password = new ClientPassword();
		password.setClient(client);
		password.setActive(true);
		password.setCreation_date(now);
		password.setPassword(suscription.getClient().getPass());
		password.setId_owner(client.getId());
		ClientPasswordDao.init().save(password);
		
	}

	/**
	 * 
	 * @param suscription
	 * @return
	 * @throws RemoveApplicationException
	 */
	private Client saveClient(ClientSuscription suscription) throws RemoveApplicationException {
		Client client = suscription.buildClientObject();
		client.setId_country(CountryDao.init().get(ConfigHelper.getDefaultCountry(ECountries.SPAIN.getCode())).getId());
		client.setLanguage(ConfigHelper.getDefaultLanguage(ELanguages.SPANISH.getCode()));
		client.setProject_name("");
		
		return ClientDao.init().save(client);
	}

	/**
	 * 
	 * @param clientPlan
	 * @throws RemoveApplicationException
	 */
	public void renewForFree(ClientPlan clientPlan) throws RemoveApplicationException {
		log.info("Renewing Free Suscription: " + clientPlan);
		ClientPaymentKey paymentKey = ClientPaymentKeyDao.init().getByClientAndPaymentMethod(clientPlan.getId_client(), EPaymentMethods.TRANSFER);
		
		ClientPaybill paybill = new ClientPaybill();
		paybill.setId_client_key(paymentKey.getId());
		paybill.setId_plan(clientPlan.getId_plan());
		paybill.setPayment_date(LocalDateTime.now());
		paybill.setAmount(clientPlan.getDetail().getCost());
		
		renew(clientPlan, paybill, true);
	}

	/**
	 * 
	 * @param client
	 * @param newPlan
	 * @param scannersToMigrate
	 * @param programmedToNextPayment
	 * @throws RemoveApplicationException
	 */
	public void changeSuscriptionByTransfer(Client client, Plan newPlan, List<Long> scannersToMigrate, boolean programmedToNextPayment) throws RemoveApplicationException {
		ClientPlanMigration migration = saveMigration(client, newPlan, scannersToMigrate, programmedToNextPayment, EPaymentMethods.TRANSFER.getCode(), null);
		
		if (!programmedToNextPayment) {
			ClientPaymentKey paymentKey = savePaymentKey(client, EPaymentMethods.TRANSFER.getCode());
			ClientPaybill paybill = savePaybill(paymentKey, newPlan);
			
			migratePlan(migration, null, paybill);
		}
	} 

	/**
	 * 
	 * @param migration
	 * @throws RemoveApplicationException
	 */
	private void migratePlan(ClientPlanMigration migration, String trackingCode, ClientPaybill paybill) throws RemoveApplicationException {
		Plan plan = PlanDao.init().get(migration.getId_client_plan_to());
		ClientPlan old = ClientPlanDao.init().getActiveOrLastClientId(migration.getId_client());
		
		ClientPlan newClientPlan = new ClientPlan();
		newClientPlan.setId_client_paybills(paybill.getId());
		newClientPlan.setActive(true);
		newClientPlan.setCredits_used(0l);
		newClientPlan.setDetail(plan);
		newClientPlan.setId_client(migration.getId_client());
		newClientPlan.setId_plan(migration.getId_client_plan_to());
		newClientPlan.setSuscribe_date(RemoveDateUtils.nowLocalDateTime());
		
		if (plan.getDuration_months() != null) {
			newClientPlan.setExpiration_date(newClientPlan.getSuscribe_date().plusMonths(plan.getDuration_months().intValue()));
		}
		
		newClientPlan.setExternal_subscription_key(trackingCode);	
		newClientPlan.setExternal_subscription_platform(migration.getPayment_platform());
				
		newClientPlan = ClientPlanDao.init().savePlanChange(old.getId(), newClientPlan);
		
		if (migration.getPayment_platform().equals(EPaymentMethods.TRANSFER.getCode()) && plan.getCost().floatValue() != 0f) {
			ClientDao.init().updateReadOnly(migration.getId_client(), true, EMessageBundleKeys.WARNING_PAYMENT_PAST_DUE.getTag());
		}
		
		if (migration.getScanners_to_migrate() != null && !migration.getScanners_to_migrate().isEmpty()) {
			ScannerDao.init().updateClientPlanId(newClientPlan.getId(), old.getId(), migration.getScanners_to_migrate());
		}
		
		ScannerDao.init().deactivateByClientPlan(old.getId());
		
		ClientPlanMigrationDao.init().updateExecutedTrue(migration);
	}
	
	private ClientPlanMigration saveMigration(Client client, Plan newPlan, List<Long> scannersToMigrate, boolean programmedToNextPayment, String paymentPlatform, String paymentKey) throws RemoveApplicationException {
		ClientPlan clientPlanActive = ClientPlanDao.init().getActiveOrLastClientId(client.getId());
		
		ClientPlanMigration migration = new ClientPlanMigration();
		
		migration.setCreation_date(LocalDateTime.now());
		migration.setExecuted(false);
		migration.setId_client(client.getId());
		migration.setId_client_plan_from(clientPlanActive.getId_plan());
		migration.setId_client_plan_to(newPlan.getId());
		migration.setProgrammed(programmedToNextPayment);
		
		if (programmedToNextPayment) {
			migration.setProgrammed_date(clientPlanActive.getExpiration_date().minusDays(2));	
		}
		
		migration.setScanners_to_migrate(parseListScannersIds(scannersToMigrate));
		migration.setPayment_platform(paymentPlatform);
		migration.setPayment_method_key(paymentKey);
		
		return ClientPlanMigrationDao.init().save(migration);
	}

	/**
	 * 
	 * @param scannersToMigrate
	 * @return
	 */
	private String parseListScannersIds(List<Long> scannersToMigrate) {
		String ids = "";
		
		if (scannersToMigrate != null) {
			ids = scannersToMigrate.stream().map(Object::toString).collect(Collectors.joining(","));
		}
		
		return ids;
	}

	/**
	 * 
	 * @param client
	 * @param newPlan
	 * @param scannersToMigrate
	 * @param programmedToNextPayment
	 * @param externalPaymentMethod
	 * @param clientPlanToChangeId
	 * @throws RemoveApplicationException
	 */
	public void changeSuscriptionByStripe(Client client, Plan newPlan, List<Long> scannersToMigrate,
			boolean programmedToNextPayment, ExternalPaymentMethod externalPaymentMethod, Long clientPlanToChangeId) throws RemoveApplicationException {
		ClientPlanMigration migration = saveMigration(client, newPlan, scannersToMigrate, programmedToNextPayment, EPaymentMethods.STRIPE.getCode(), externalPaymentMethod.getKey());
		
		if (!programmedToNextPayment) {
			ExternalPaymentReference reference = processStripePayment(client, newPlan, clientPlanToChangeId, externalPaymentMethod.getKey());

			migratePlan(migration, reference.getSubscriptionTrackingCode(), reference.getClientPaybill());
		}
	}

	private ExternalPaymentReference processStripePayment(Client client, Plan newPlan, Long clientPlanToChangeId, String externalPaymentMethodKey) throws RemoveApplicationException {
		ClientPaymentKey paymentKey = ClientPaymentKeyDao.init().getByClientAndPaymentMethod(client.getId(), EPaymentMethods.STRIPE);

		if (paymentKey == null) {
			log.error("Client Stripe Payment Key Not Found:" + client.getId());
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_CLIENT_PAYMENT_KEY_NOT_FOUND);
		}
		
		Plan plan = PlanDao.init().get(newPlan.getId());
		
		ClientPlan clientPlan = ClientPlanDao.init().getById(clientPlanToChangeId);
		
		RemoveResponse stripeResponse = PaymentStripeClient.init().changeSuscription(client, clientPlan, newPlan, externalPaymentMethodKey);
		
		if (stripeResponse.isError()) {
			log.error("Could't change subscription for client: " + client.getUuid() + " to plan: " + plan.getId());
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_EXTERNAL_SERVICE);
		}
		
		ExternalPaymentReference reference = (ExternalPaymentReference) stripeResponse.getData();
		
		reference.getClientPaybill().setId_client_key(reference.getClientPaymentKey().getId());
		reference.getClientPaybill().setId_plan(plan.getId());
		reference.setClientPaybill(ClientPaybillDao.init().save(reference.getClientPaybill()));
		
		return reference;
	}

	public void changePlan(ClientPlanMigration migration) throws RemoveApplicationException {
		Client client = ClientDao.init().getBasicById(migration.getId_client());
		Plan newPlan = PlanDao.init().get(migration.getId_client_plan_to());
		
		if (migration.getPayment_platform().equals(EPaymentMethods.TRANSFER.getCode())) {
			ClientPaymentKey paymentKey = savePaymentKey(client, EPaymentMethods.TRANSFER.getCode());
			ClientPaybill paybill = savePaybill(paymentKey, newPlan);
			
			migratePlan(migration, null, paybill);
		} else if (migration.getPayment_platform().equals(EPaymentMethods.STRIPE.getCode())) {
			ClientPlan clientPlan = ClientPlanDao.init().getActiveOrLastClientId(client.getId());
			ExternalPaymentReference reference = processStripePayment(client, newPlan, clientPlan.getId(), migration.getPayment_method_key());
			
			migratePlan(migration, reference.getSubscriptionTrackingCode(), reference.getClientPaybill());
		}
	}
}
