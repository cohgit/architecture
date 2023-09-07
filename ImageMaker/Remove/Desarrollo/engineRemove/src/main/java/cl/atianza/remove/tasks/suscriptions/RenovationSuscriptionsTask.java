package cl.atianza.remove.tasks.suscriptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.client.ClientPlanMigrationDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EPaymentMethods;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.external.clients.payments.stripe.EStripeSubscriptionStatus;
import cl.atianza.remove.external.clients.payments.stripe.PaymentStripeClient;
import cl.atianza.remove.helpers.ClientNotificationHelper;
import cl.atianza.remove.helpers.SuscriptionHelper;
import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.models.client.ClientPlanMigration;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Check every scanner to be executed and schedule it.
 * @author pilin
 *
 */
public class RenovationSuscriptionsTask extends TimerTask {
	private Logger log = LogManager.getLogger(RenovationSuscriptionsTask.class);
	private SuscriptionHelper suscriptionHelper = SuscriptionHelper.init();
	
	public RenovationSuscriptionsTask() {
		super();
	}

	@Override
	public void run() {
		log.info("###################################### Starting Renovation Suscription Task Manager at:" + RemoveDateUtils.nowLocalDateTime());
		
		try {
			List<ClientPlan> list = ClientPlanDao.init().listActives();
			PlanDao planDao = PlanDao.init();
			
			if (list != null && !list.isEmpty()) {
				log.info("Total Subscriptions founds to evaluate: " + list.size());
				
				list.forEach(clientPlan -> {
					log.info("###################################### Evaluating suscription: " + clientPlan);
					try {
						if (clientPlan.isActive()) {
							ClientPlanMigration migration = ClientPlanMigrationDao.init().getProgramedByClient(clientPlan.getId_client());
							
							if (migration == null) {
								if (clientPlan.getExternal_subscription_platform().equals(EPaymentMethods.STRIPE.getCode())) {
									verifyStripeSuscription(clientPlan, planDao);
								} else if (clientPlan.getExternal_subscription_platform().equals(EPaymentMethods.TRANSFER.getCode())) {
									verifyTransferSuscription(clientPlan, planDao);
								} else {
									log.info("Invalid suscription type: " + clientPlan);
								}
							} else {
								log.info("Skip suscription verification, has a migration programmed: " + clientPlan);
							}
						} else {
							log.info("Suscription is inactive: " + clientPlan);
						}
					} catch (RemoveApplicationException | Exception e) {
						log.error("Error running Task: "+ clientPlan, e);
					}
					
					log.info("");
				});
			} else {
				log.info("There's no subscriptions to verify...");
			}
		} catch (RemoveApplicationException e) {
			log.error("Error scheduling tasks: ", e);
		}
		
		log.info("###################################### Subscription Task Manager Execution Finished at: " + RemoveDateUtils.nowLocalDateTime());
	}
	
	private void verifyTransferSuscription(ClientPlan clientPlan, PlanDao planDao) throws RemoveApplicationException {
		log.info("Verifying Transfer Suscription: " + clientPlan);
		LocalDateTime now = LocalDateTime.now();
		Plan plan = planDao.get(clientPlan.getId_plan());
		
		if (plan.esCostZero()) {
			log.info("Verifying Free Suscription: " + clientPlan);
			if (clientPlan.getExpiration_date().isBefore(now)) {
				log.info("Free Suscription Expired: " + clientPlan);
				if (plan.isAutomatic_renewal()) {
					suscriptionHelper.renewForFree(clientPlan);
				} else {
					suscriptionHelper.cancel(clientPlan);
				}	
			} else {
				log.info("Suscription in order: " + clientPlan);
			}
		} else {
			suscriptionHelper.verifyRenewForTransferSuscriptions(clientPlan, plan);
		}
	}
	
	private void verifyStripeSuscription(ClientPlan clientPlan, PlanDao planDao) {
		log.info("Verifying subscription with Stripe: " + clientPlan.getExternal_subscription_key());
		try {
			clientPlan.setDetail(planDao.get(clientPlan.getId_plan()));
			if (verifyExpirationDate(clientPlan)) {
				if (clientPlan.getExternal_subscription_platform().equals(EPaymentMethods.STRIPE.getCode())) {
					checkForStripe(clientPlan);
				}
			}
		} catch (RemoveApplicationException e) {
			log.error("Error checking Stripe Subscription: ", e);
		}
	}

	private boolean verifyExpirationDate(ClientPlan clientPlan) throws RemoveApplicationException {
		log.info("Checking expiration date for: " + clientPlan);
		if (clientPlan.getDetail().getDuration_months() != null) {
			if (clientPlan.getExpiration_date() == null) {
				log.info("Expiration Date is null (Updating): " + clientPlan);
				clientPlan.setExpiration_date(clientPlan.getSuscribe_date().plusMonths(clientPlan.getDetail().getDuration_months().intValue()));
				ClientPlanDao.init().updateExpirationDate(clientPlan);
			}
			
			if (clientPlan.getExpiration_date().isBefore(LocalDateTime.now())) {
				if (clientPlan.getDetail().isAutomatic_renewal()) {
					log.info("Plan Expired (Automatic Renewal): " + clientPlan);
					refreshExpirationDate(clientPlan);
					ClientPlanDao.init().updateExpirationDate(clientPlan);
				} else {
					log.info("Plan Expired (Cancelling): " + clientPlan);
					if (clientPlan.getExternal_subscription_platform().equals(EPaymentMethods.STRIPE.getCode())) {
						PaymentStripeClient.init().cancelSuscription(clientPlan);
					}
					
					ClientPlanDao.init().cancel(clientPlan);
					return false;
				}
			}
		} else {
			log.error("Plan doesn't have duration months... " + clientPlan.getDetail());
		}
		
		return true;
	}

	private void refreshExpirationDate(ClientPlan clientPlan) {
		if (clientPlan.getExpiration_date().isBefore(LocalDateTime.now())) {
			clientPlan.setExpiration_date(clientPlan.getExpiration_date().plusMonths(clientPlan.getDetail().getDuration_months().intValue()));
			refreshExpirationDate(clientPlan);
		}
	}

	/**
	 * 
	 * @param clientPlan
	 * @throws RemoveApplicationException
	 */
	private void checkForStripe(ClientPlan clientPlan) throws RemoveApplicationException {
		EStripeSubscriptionStatus status = PaymentStripeClient.init().getSuscriptionStatus(clientPlan.getExternal_subscription_key());
		
		
		if (status == null) {
			ClientPlanDao.init().cancel(clientPlan);
		} else {
			switch (status) {
			case ACTIVE: 
				Client client = ClientDao.init().getById(clientPlan.getId_client());
				
				if (client.isReadOnly() && client.getMessage() != null && client.getMessage().equals(EMessageBundleKeys.WARNING_PAYMENT_PAST_DUE.getTag())) {
					ClientDao.init().updateReadOnly(clientPlan.getId_client(), false, "");
				}
				break;
			case PAST_DUE:
				processDuePaymentCondition(clientPlan);
				break;
			case CANCELED:
			case UNPAID:
				ClientPlanDao.init().cancel(clientPlan);
				break;
			default:
				break;
			}
		}
	}

	private void processDuePaymentCondition(ClientPlan clientPlan) throws RemoveApplicationException {
		Client client = ClientDao.init().getById(clientPlan.getId_client());
		
		if (!client.isReadOnly()) {
			ClientDao.init().updateReadOnly(clientPlan.getId_client(), true, EMessageBundleKeys.WARNING_PAYMENT_PAST_DUE.getTag());			
		}
		
		if (clientPlan.getExpiration_date().toLocalDate().isEqual(LocalDate.now())) {
			ClientNotificationHelper.init(client).createDuePaymentNotificationLevel1();
		} else if (clientPlan.getExpiration_date().toLocalDate().plusDays(5).isEqual(LocalDate.now())) {
			ClientNotificationHelper.init(client).createDuePaymentNotificationLevel2();
		} else if (clientPlan.getExpiration_date().toLocalDate().plusDays(10).isBefore(LocalDate.now())){
			ClientNotificationHelper.init(client).createDuePaymentNotificationLevel3();
			
			if (clientPlan.getExternal_subscription_platform().equals(EPaymentMethods.STRIPE.getCode())) {
				PaymentStripeClient.init().cancelSuscription(clientPlan);
			}
			
			ClientPlanDao.init().cancel(clientPlan);
		}
	}
}
