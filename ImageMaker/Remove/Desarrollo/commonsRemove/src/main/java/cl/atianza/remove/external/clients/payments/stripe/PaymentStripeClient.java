package cl.atianza.remove.external.clients.payments.stripe;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.stripe.Stripe;
import com.stripe.exception.ApiConnectionException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.RateLimitException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceCollection;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.model.Price;
import com.stripe.model.PriceCollection;
import com.stripe.model.Product;
import com.stripe.model.ProductCollection;
import com.stripe.model.Subscription;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.PriceUpdateParams;
import com.stripe.param.PriceCreateParams.Builder;
import com.stripe.param.ProductCreateParams;

import cl.atianza.remove.daos.admin.PlanExternalKeyDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPaymentKeyDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EPaymentMethods;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.external.clients.payments.ExternalClientPayment;
import cl.atianza.remove.external.clients.payments.ExternalPaymentMethod;
import cl.atianza.remove.external.clients.payments.ExternalPaymentReference;
import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.admin.PlanExternalKey;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPaymentKey;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveResponse;
@Deprecated
public class PaymentStripeClient {
	private static final int MAX_RECORDS = 100;
	
	private Logger log = LogManager.getLogger(PaymentStripeClient.class);
	private final String API_KEY = "sk_test_51Jk5CTCoDErZfP4UgooCXyOm7RkxC2lYBFR146e0ouChVqyRNLsyqOpDj1uUi6Rao4MyM1p5oFUiT5HsmGtt6xRG003r2AVOOu";
	//private final String API_KEY = "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";
	//private final String API_KEY = "sk_test_51JUGRzE5sKB1kzXkupAAtULzcZgGPxarxmjM28SNHSid4ngPKJA9smtchVgEroEML1MoTQ7AYluwD0ivfsgneQ7x004XEkkUk8";
	
	public PaymentStripeClient() {
		super();
		Stripe.apiKey = API_KEY;
	}

	public static PaymentStripeClient init() {
		return new PaymentStripeClient();
	}
	
	/**
	 * 
	 * @param plan
	 * @return
	 */
	public RemoveResponse refreshProduct(Plan plan) {
		try {
			Product checkProduct = findProduct(plan);
			
			if (checkProduct == null) {
				createProduct(plan);
			} else {
				updateProduct(checkProduct, plan);
			}
		} catch (Exception | RemoveApplicationException e) {
			return errorHandler(e);
		}
		
		return RemoveResponse.instanceDefaultOk();
	}
	
	/**
	 * 
	 * @param plan
	 * @return
	 */
	private Product findProduct(Plan plan) {
		try {
			if (plan.getStripeKey() != null && plan.getStripeKey().getKey() != null) {
				return Product.retrieve(plan.getStripeKey().getKey());	
			} else {
				return findByName(plan.getName());
			}
		} catch (StripeException e) {
			errorHandler(e);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	private Product findByName(String name) {
		Map<String, Object> params = new HashMap<>();
		params.put("limit", MAX_RECORDS);

		try {
			ProductCollection products = Product.list(params);
			
			for (Product product : products.getData()) {
				if (product.getName().equals(name))
					return product;
			};
		} catch (StripeException e) {
			errorHandler(e);
		}
		
		return null;
	}

	/**
	 * 
	 * @param plan
	 * @return
	 * @throws StripeException
	 * @throws RemoveApplicationException
	 */
	public Product createProduct(Plan plan) throws StripeException, RemoveApplicationException {
		ProductCreateParams productParams = ProductCreateParams.builder()
			    .setName(plan.getName())
			    .setDescription(plan.getDescription())
			    .setActive(plan.isActive())
			    .build();
		
		Product product = Product.create(productParams);
		log.info("Stripe Product Created: "+product.getId());
		
		PlanExternalKey externalKey = new PlanExternalKey();
		externalKey.setId_plan(plan.getId());
		externalKey.setKey(product.getId());
		externalKey.setPlatform(EPaymentMethods.STRIPE.getCode());
		
		PlanExternalKeyDao.init().save(externalKey);
		
		Price price = createPrice(product, plan);
		
		upsertRemoveKey(plan, product, price);
		
		return product;
	}
	/**
	 * 
	 * @param plan
	 * @param product
	 * @param price
	 * @throws RemoveApplicationException
	 */
	private void upsertRemoveKey(Plan plan, Product product, Price price) throws RemoveApplicationException {
		PlanExternalKey key = new PlanExternalKey();
		
		key.setActive(true);
		key.setId_plan(plan.getId());
		key.setKey(product.getId());
		key.setKey_payment(price.getId());
		key.setPlatform(EPaymentMethods.STRIPE.getCode());
		
		PlanExternalKey oldKey = PlanExternalKeyDao.init().getByPlan(plan.getId(), EPaymentMethods.STRIPE);
		
		if (oldKey == null) {
			PlanExternalKeyDao.init().save(key);	
		} else {
			PlanExternalKeyDao.init().update(key);
		}
	}

	/**
	 * 
	 * @param product
	 * @param plan
	 * @return
	 * @throws StripeException
	 */
	private Price createPrice(Product product, Plan plan) throws StripeException {
		Builder priceBuilder =
				  PriceCreateParams.builder()
				    .setProduct(product.getId())
				    .setUnitAmountDecimal(new BigDecimal(Float.toString(plan.getCost()*100)))
				    .setCurrency("eur");
		
		if (plan.isAutomatic_renewal()) {
			priceBuilder.setRecurring(
				      PriceCreateParams.Recurring.builder()
				        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
				        .setIntervalCount(plan.getDuration_months().longValue())
				        .build());
		}
		
		return	Price.create(priceBuilder.build());
	}
	/**
	 * 
	 * @param product
	 * @param plan
	 * @param replacePrice
	 * @throws StripeException
	 * @throws RemoveApplicationException
	 */
	private void updateProduct(Product product, Plan plan) throws StripeException, RemoveApplicationException {
		Map<String, Object> paramsPrduct = new HashMap<>();
		paramsPrduct.put("active", plan.isActive());
		paramsPrduct.put("description", plan.getDescription());
		paramsPrduct.put("name", plan.getName());
		product.update(paramsPrduct);
		
		verifyAndReplacePrice(product, plan);
	}

	private void verifyAndReplacePrice(Product product, Plan plan) throws RemoveApplicationException, StripeException {
		Map<String, Object> paramsFindPrice = new HashMap<>();
		paramsFindPrice.put("active", true);
		paramsFindPrice.put("product", product.getId());

		PriceCollection prices = Price.list(paramsFindPrice);
		
		if (!prices.getData().isEmpty()) {
			Price price = prices.getData().get(0);

			if (price.getUnitAmountDecimal().floatValue() == (plan.getCost().floatValue() * 100)) {
				upsertRemoveKey(plan, product, price);
			} else {
				prices.getData().forEach(_price -> {
					if (_price.getActive().booleanValue()) {
						log.info("Deactivating Price: "+ price.getId());
						PriceUpdateParams priceParams = PriceUpdateParams.builder().setActive(false).build();
						try {
							_price.update(priceParams);
						} catch (StripeException e) {
							log.error("Error updating price:" + price.getId(), e);
						}	
					}
				});
				
				Price newPrice = createPrice(product, plan);
				upsertRemoveKey(plan, product, newPrice);
			}
		} else {
			Price newPrice = createPrice(product, plan);
			upsertRemoveKey(plan, product, newPrice);
		}
	}

	/**
	 * 
	 * @param sessionId
	 * @return
	 */
	public RemoveResponse validatePayment(String sessionId) {
		try {
			ExternalPaymentReference reference = new ExternalPaymentReference(EPaymentMethods.STRIPE.getCode());
			
			com.stripe.model.checkout.Session session = com.stripe.model.checkout.Session.retrieve(sessionId);
			reference.getClientPaymentKey().setKey(session.getCustomer());
			
			reference.getClientPaybill().setTracking_code(sessionId);
			reference.getClientPaybill().setAmount(Float.valueOf(session.getAmountTotal() / 100));
			reference.getClientPaybill().setConfirmed(session.getPaymentStatus().equalsIgnoreCase("paid"));
			reference.getClientPaybill().setPayment_date(RemoveDateUtils.nowLocalDateTime());
			reference.setCustomerEmail(session.getCustomerEmail());
			reference.setSubscriptionTrackingCode(session.getSubscription());
			
			return RemoveResponse.instanceOk(reference);
		} catch (StripeException e) {
			return errorHandler(e);
		}
	}
	
	public RemoveResponse changeSuscription(Client client, ClientPlan clientPlan, Plan plan, String paymentMethodKey) throws RemoveApplicationException {
		ExternalPaymentReference reference = new ExternalPaymentReference(EPaymentMethods.STRIPE.getCode());
		
		try {
			PlanExternalKey planKey = PlanExternalKeyDao.init().getByPlan(plan.getId(), EPaymentMethods.STRIPE);
			ClientPaymentKey clientKey = ClientPaymentKeyDao.init().getByClientAndPaymentMethod(client.getId(), EPaymentMethods.STRIPE);
			
			// Cancel Suscription
			try {
				Subscription.retrieve(clientPlan.getExternal_subscription_key()).cancel();
			} catch (StripeException e) {
				log.error("Error canceling subscription:" +  clientPlan.getExternal_subscription_key(), e);
			}
			
			List<Object> items = new ArrayList<>();
			Map<String, Object> itemPrice = new HashMap<>();
			Map<String, Object> params = new HashMap<>();
			
			itemPrice.put("price", planKey.getKey_payment());
			items.add(itemPrice);
			
			params.put("customer", clientKey.getKey());
			params.put("items", items);
			params.put("default_payment_method", paymentMethodKey);
			
			if (!plan.isAutomatic_renewal()) {
				params.put("cancel_at", RemoveDateUtils.localDateToSeconds(LocalDate.now().plusMonths(plan.getDuration_months())));
			}

			// Create a New Suscription
			Subscription newSubscription = Subscription.create(params);
			
			reference.getClientPaymentKey().setKey(clientKey.getKey());
			
			reference.getClientPaybill().setTracking_code(newSubscription.getItems().getData().get(0).getId());
			reference.getClientPaybill().setAmount(Float.valueOf(plan.getCost().floatValue() / 100));
			reference.getClientPaybill().setConfirmed(newSubscription.getStatus().equalsIgnoreCase("active"));
			reference.getClientPaybill().setPayment_date(RemoveDateUtils.nowLocalDateTime());
			reference.setCustomerEmail(client.getEmail());
			reference.setSubscriptionTrackingCode(newSubscription.getId());
			
			reference.setClientPaymentKey(clientKey);
		} catch (StripeException e) {
			return errorHandler(e);
		}
		
		return RemoveResponse.instanceOk(reference);
	}
	public List<ExternalPaymentMethod> findPaymentMethodsEnabledByClient(String uuidClient) throws RemoveApplicationException {
		Client client = ClientDao.init().getBasicByUuid(uuidClient);
		ClientPaymentKey key = ClientPaymentKeyDao.init().getByClientAndPaymentMethod(client.getId(), EPaymentMethods.STRIPE);
		try {
			if (key != null) {
				return findPaymentMethodsEnabledByCustomer(key.getKey());	
			}
		} catch (StripeException e) {
			errorHandler(e);
		}
		return new ArrayList<ExternalPaymentMethod>();
	}
	private List<ExternalPaymentMethod> findPaymentMethodsEnabledByCustomer(String key) throws StripeException {
		List<ExternalPaymentMethod> listPayments = new ArrayList<ExternalPaymentMethod>();
		
		Map<String, Object> params = new HashMap<>();
		params.put("customer", key);
		params.put("type", "card");

		try {
			PaymentMethodCollection paymentMethods = PaymentMethod.list(params);
			
			paymentMethods.getData().forEach(pm -> {
				if (pm.getCard() != null) {
					listPayments.add(new ExternalPaymentMethod("card", pm.getId(), pm.getCard().getBrand(), pm.getCard().getExpMonth(), pm.getCard().getExpYear(), pm.getCard().getLast4(), EPaymentMethods.STRIPE.getCode()));	
				}
			});	
		} catch (Exception ex) {
			log.error("Error finding payment methods in Stripe: ", ex);
		}
		
		
		return listPayments;
	}

	public List<ExternalClientPayment> listPaymentsForCustomer(Long idClient) throws RemoveApplicationException {
		return listPaymentsForCustomer(ClientDao.init().getById(idClient));
	}
	public List<ExternalClientPayment> listPaymentsForCustomer(String uuidClient) throws RemoveApplicationException {
		return listPaymentsForCustomer(ClientDao.init().getByUuid(uuidClient));
	}
	public List<ExternalClientPayment> listPaymentsForCustomer(Client client) throws RemoveApplicationException {
		List<ExternalClientPayment> list = new ArrayList<ExternalClientPayment>();
		ClientPaymentKey key = ClientPaymentKeyDao.init().getByClientAndPaymentMethod(client.getId(), EPaymentMethods.STRIPE);
		
		if (key != null) {
			Map<String, Object> paramsInvoices = new HashMap<>();
			paramsInvoices.put("limit", MAX_RECORDS);
			paramsInvoices.put("customer", key.getKey());
		
			InvoiceCollection invoices;
			try {
				invoices = Invoice.list(paramsInvoices);
				
				invoices.getData().forEach(invoice -> {
					ExternalClientPayment pay = new ExternalClientPayment();
					
					pay.setAmount_due(invoice.getAmountDue()/100);
					pay.setAmount_paid(invoice.getAmountPaid()/100);
					pay.setAmount_remaining(invoice.getAmountRemaining()/100);
					pay.setBilling_reason(invoice.getBillingReason());
					pay.setCountry(invoice.getAccountCountry());
					pay.setCurrency(invoice.getCurrency());
					pay.setSubtotal(invoice.getSubtotal()/100);
					pay.setTotal(invoice.getTotal()/100);
					pay.setCreated(RemoveDateUtils.parse(invoice.getCreated()));
					
					list.add(pay);
				});
			} catch (StripeException e) {
				errorHandler(e);
			}
		}
		
		return list;
	}
	
	public EStripeSubscriptionStatus getSuscriptionStatus(String external_subscription_key) {
		try {
			Subscription subs = Subscription.retrieve(external_subscription_key);
			
			if (subs != null) {
				return EStripeSubscriptionStatus.find(subs.getStatus());
			}
		} catch (StripeException e) {
			errorHandler(e);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param ex
	 * @return
	 */
	private RemoveResponse errorHandler(Throwable ex) {
		try {
			throw ex;
		} catch (CardException e) {
		  // Since it's a decline, CardException will be caught
		  log.error("Status is: " + e.getCode());
		  log.error("Message is: " + e.getMessage());
		  log.error("CardException ocurred: ", ex);
		} catch (RateLimitException e) {
		  // Too many requests made to the API too quickly
			log.error("RateLimitException ocurred: ", ex);
		} catch (InvalidRequestException e) {
		  // Invalid parameters were supplied to Stripe's API
			log.error("InvalidRequestException ocurred: ", ex);
		} catch (AuthenticationException e) {
		  // Authentication with Stripe's API failed
		  // (maybe you changed API keys recently)
			log.error("AuthenticationException ocurred: ", ex);
		} catch (ApiConnectionException e) {
		  // Network communication with Stripe failed
			log.error("ApiConnectionException ocurred: ", ex);
		} catch (StripeException e) {
		  // Display a very generic error to the user, and maybe send
		  // yourself an email
			log.error("StripeException ocurred: ", ex);
		} catch (Throwable e) {
			log.error("Unexpected error ocurred: ", ex);
		  // Something else happened, completely unrelated to Stripe
		}
		
		return RemoveResponse.instanceError(EMessageBundleKeys.ERROR_EXTERNAL_SERVICE);
	}
	
	/**
	 * 
	 */
	public void deleteAllProducts() {
		Map<String, Object> params = new HashMap<>();
		params.put("limit", MAX_RECORDS);

		try {
			ProductCollection products = Product.list(params);
			
			products.getData().forEach(product -> {
				try {
					product.delete();
				} catch (StripeException e) {
					errorHandler(e);
				}
			});
		} catch (StripeException e) {
			errorHandler(e);
		}
	}
	public String getPaymentPortal(String customerKey, String urlReturn) {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("customer", customerKey);
			params.put("return_url", urlReturn);

			return com.stripe.model.billingportal.Session.create(params).getUrl();
		} catch (StripeException e) {
			errorHandler(e);
		}
		
		return null;
	}
	
	public Long totalizeChargesByMonth(LocalDate referenceDate) {
		long total = 0l;
		
		for (Charge charge : listChargesByRangeDate(referenceDate.withDayOfMonth(1), referenceDate.withDayOfMonth(1).plusMonths(1))) {
			if (charge.getStatus().equals("succeeded")) {
				total += (charge.getAmount().longValue() / 100);	
			}
		};
		
		return total;
	}
	public Long totalizeChargesLifeTime() {
		long total = 0l;
		
		for (Charge charge : listChargesByRangeDate(null, null)) {
			if (charge.getStatus().equals("succeeded")) {
				total += (charge.getAmount().longValue() / 100);	
			}
		};
		
		return total;
	}
	
	private List<Charge> listChargesByRangeDate(LocalDate initDate, LocalDate endDate) {
		List<Charge> list = new ArrayList<Charge>();
		listChargesByRangeDateRecurrent(list, initDate, endDate, null);
		return list;
	}
	
	private List<Charge> listChargesByRangeDateRecurrent(List<Charge> list, LocalDate initDate, LocalDate endDate, String idStartingAfter) {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("limit", MAX_RECORDS);
			
			if (initDate != null && endDate != null) {
				Map<String, Object> paramsDate = new HashMap<>();
				paramsDate.put("gte", RemoveDateUtils.localDateToSeconds(initDate));
				paramsDate.put("lte", RemoveDateUtils.localDateToSeconds(endDate));
				
				params.put("created", paramsDate);	
			}
			
			if (idStartingAfter != null) {
				params.put("starting_after", idStartingAfter);	
			}

			ChargeCollection charges = Charge.list(params);
			list.addAll(charges.getData());
			
			if (charges.getData().size() == MAX_RECORDS) {
				listChargesByRangeDateRecurrent(list, initDate, endDate, charges.getData().get(MAX_RECORDS - 1).getId());
			}
		} catch (StripeException e) {
			log.error("Error loading recurrent charges: ", e);
		}
		
		return list;
	}
	
	public void testMethods() {
		
	}
	
	public static void main(String[] args) {
		init().testMethods();
	}

	public void cancelSuscription(ClientPlan clientPlan) {
		try {
			Subscription.retrieve(clientPlan.getExternal_subscription_key()).cancel();
		} catch (StripeException e) {
			errorHandler(e);
		}
	}
}
