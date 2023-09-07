package cl.atianza.remove.api.commons;

import static spark.Spark.get;
import static spark.Spark.put;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static spark.Spark.path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.admin.UserAlertMessageDao;
import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.client.ClientAlertMessageDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.client.ClientPlanMigrationDao;
import cl.atianza.remove.daos.commons.ConfigurationDao;
import cl.atianza.remove.daos.commons.CountryDao;
import cl.atianza.remove.daos.commons.ForbiddenWordDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.TrackingWordDao;
import cl.atianza.remove.enums.EDeindexationStatus;
import cl.atianza.remove.enums.EDevices;
import cl.atianza.remove.enums.EFeelings;
import cl.atianza.remove.enums.EImageColors;
import cl.atianza.remove.enums.EImageSize;
import cl.atianza.remove.enums.EImageType;
import cl.atianza.remove.enums.EImageUsageRights;
import cl.atianza.remove.enums.EImpulseInteractionCategory;
import cl.atianza.remove.enums.EImpulseInteractionConcept;
import cl.atianza.remove.enums.EImpulseStatus;
import cl.atianza.remove.enums.EImpulseType;
import cl.atianza.remove.enums.ELanguages;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.ENewsTypes;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.ESearchTypes;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.external.clients.google.GoogleSuggestClient;
import cl.atianza.remove.external.clients.payments.stripe.PaymentStripeClient;
import cl.atianza.remove.models.views.NotificationUpdates;
import cl.atianza.remove.models.views.PlanChangeSuscriptionInfo;
import cl.atianza.remove.models.admin.User;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.models.client.ClientPlanMigration;
import cl.atianza.remove.models.views.CheckoutPlansView;
import cl.atianza.remove.models.views.ImpulseConceptModel;
import cl.atianza.remove.utils.AbstractRestController;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.validators.ClientAccessValidator;
import cl.atianza.remove.validators.UserAccessValidator;
import spark.Request;
import spark.Response;

/**
 * Common services used by the system. Generally lists required by forms in the frontend.
 * @author Jose Gutierrez
 *
 */
public class CommonController {
	private static Logger log = LogManager.getLogger(CommonController.class);
	
	public static void buildPaths() {
		path(ERestPath.COMMON.getPath(), () -> {
			// Scanner Parameters
			get(ERestPath.COMMON_SEARCH_TYPES.getNonBasePath(), (Request request, Response response) -> {
	        	try {
					return RemoveResponseUtil.buildOk(ESearchTypes.values());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_DEVICES.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(EDevices.values());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_COUNTRIES.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(CountryDao.init().list());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_LANGUAGES.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(ELanguages.values());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_TRACKING_WORDS.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(TrackingWordDao.init().listActives());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_FORBIDDEN_WORDS.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(ForbiddenWordDao.init().list());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			
			get(ERestPath.COMMON_FEELINGS.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(EFeelings.values());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			
			// Scanner News Parameters
			get(ERestPath.COMMON_NEWS_TYPES.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(ENewsTypes.values());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			
			// Scanner Images Parameters
			get(ERestPath.COMMON_IMAGES_COLOURS.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(EImageColors.values());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_IMAGES_TYPES.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(EImageType.values());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_IMAGES_SIZES.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(EImageSize.values());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_IMAGES_USAGE_RIGHTS.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(EImageUsageRights.values());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_CLIENTS.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(ClientDao.init().listActives());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_CLIENTS_EMAIL.getNonBasePath(), (Request request, Response response) -> {
				try {
					String email = RemoveRequestUtil.getParamString(request, EWebParam.EMAIL);
					Client client = ClientDao.init().getByEmail(email);
					
					if (client != null && !client.esNuevo()) {
						return RemoveResponseUtil.buildOk(true);
					} else {
						return RemoveResponseUtil.buildOk(false);
					}
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_CLIENT_PLAN.getNonBasePath(), (Request request, Response response) -> {
				try {
					RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
					ClientPlan clientPlan = ClientPlanDao.init().getActiveOrLastClientUuid(token.getUuidAccount());
					PlanChangeSuscriptionInfo info = new PlanChangeSuscriptionInfo();
					info.setActualPlan(PlanDao.init().get(clientPlan.getId_plan()));
					ClientPlanMigration migration = ClientPlanMigrationDao.init().getProgramedByClient(clientPlan.getId_client());
					
					if (migration != null) {
						info.setProgrammedToNextPayment(true);
					} else {
						info.setPaymentsMethodsAvailables(PaymentStripeClient.init().findPaymentMethodsEnabledByClient(token.getUuidAccount()));
						info.setPlansAvailables(PlanDao.init().listAvailablesForClient(clientPlan));
						info.setPlansCustomized(PlanDao.init().listCustomizedForClient(clientPlan));
						info.addScanners(ScannerDao.init().listByClientPlanIdForSuscriptionChange(clientPlan.getId()));	
					}
					
					return RemoveResponseUtil.buildOk(info);
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_PLANS.getNonBasePath(), (Request request, Response response) -> {
				try {
					CheckoutPlansView checkout = new CheckoutPlansView(PlanDao.init().listActiveNotCostumized());
					
					checkout.getParams().put("TRANSFER_MIN_VALUE", ConfigurationDao.init().getTransferMinValue());
					
					return RemoveResponseUtil.buildOk(checkout);
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_PROFILES.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(EProfiles.valuesWithoutClient());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			
			get(ERestPath.COMMON_IMPULSE_TYPES.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(EImpulseType.values());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_IMPULSE_STATUS.getNonBasePath(), (Request request, Response response) -> {
				try {
					return RemoveResponseUtil.buildOk(EImpulseStatus.values());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			get(ERestPath.COMMON_IMPULSE_CONCEPTS.getNonBasePath(), (Request request, Response response) -> {
				try {
					List<ImpulseConceptModel> list = new ArrayList<ImpulseConceptModel>();
					list.add(new ImpulseConceptModel(EImpulseInteractionConcept.TRAFFIC, 
							Arrays.asList(EImpulseInteractionCategory.SOCIAL, EImpulseInteractionCategory.GOOGLE, 
									EImpulseInteractionCategory.DIRECT, EImpulseInteractionCategory.MEDIA, 
									EImpulseInteractionCategory.OTHER, EImpulseInteractionCategory.BLOG)));
					list.add(new ImpulseConceptModel(EImpulseInteractionConcept.LINK, 
							Arrays.asList(EImpulseInteractionCategory.SOCIAL, EImpulseInteractionCategory.OTHER, 
									EImpulseInteractionCategory.BLOG)));
					list.add(new ImpulseConceptModel(EImpulseInteractionConcept.CTR, 
							Arrays.asList(EImpulseInteractionCategory.GOOGLE, EImpulseInteractionCategory.LINK)));
					
					return RemoveResponseUtil.buildOk(list);
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			
			// Other Commons Services
			get(ERestPath.COMMON_NOTIFICATIONS.getNonBasePath(), (Request request, Response response) -> {
				try {
					RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
					Integer total = RemoveRequestUtil.getOptionalParamInt(request, EWebParam.TOTAL);
					
					if (token.getProfile().equals(EProfiles.CLIENT.getCode())) {
						return RemoveResponseUtil.buildOk(ClientAlertMessageDao.init().listForOwner(ClientDao.init().getByUuid(token.getUuidAccount()).getId(), total));
					} else if (EProfiles.obtain(token.getProfile()) != null) {
						return RemoveResponseUtil.buildOk(UserAlertMessageDao.init().listForOwner(UserDao.init().getByUuid(token.getUuidAccount()).getId(), total));
					}
				} catch (RemoveApplicationException e) {
					return AbstractRestController.catchException(log, e);
				}
				
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
	        });
			
			// Other Commons Services
			put(ERestPath.COMMON_NOTIFICATIONS.getNonBasePath(), (Request request, Response response) -> {
				try {
					RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
					NotificationUpdates updates = (NotificationUpdates) RemoveRequestUtil.getBodyObject(request, NotificationUpdates.class);
					
					if (token.getProfile().equals(EProfiles.CLIENT.getCode())) {
						ClientAccessValidator.init(token).validateNotifications(updates.getClientsAlerts());
						updates.getClientsAlerts().forEach(alert -> {
							try {
								if (updates.getField().equals("readed")) {
									ClientAlertMessageDao.init().switchMessageReaded(alert, updates.isValue());
								} else if (updates.getField().equals("hidden")) {
									ClientAlertMessageDao.init().switchMessageHidden(alert, updates.isValue());
								}
							} catch (RemoveApplicationException e) {
								log.error("Error switching alert :" + alert, e);
							}
						});
					} else if (EProfiles.obtain(token.getProfile()) != null) {
						UserAccessValidator.init(token).validateNotifications(updates.getUserAlerts());
						updates.getUserAlerts().forEach(alert -> {
							try {
								if (updates.getField().equals("readed")) {
									UserAlertMessageDao.init().switchMessageReaded(alert, updates.isValue());
								} else if (updates.getField().equals("hidden")) {
									UserAlertMessageDao.init().switchMessageHidden(alert, updates.isValue());
								}
							} catch (RemoveApplicationException e) {
								log.error("Error switching alert :" + alert, e);
							}
						});
					}
				} catch (RemoveApplicationException e) {
					return AbstractRestController.catchException(log, e);
				}
				
				return RemoveResponseUtil.buildDefaultOk();
	        });
			
			get(ERestPath.COMMON_GOOGLE_SUGGEST.getNonBasePath(), (Request request, Response response) -> {
				try {
					String query = RemoveRequestUtil.getParamString(request, EWebParam.Q);
					String country = RemoveRequestUtil.getParamString(request, EWebParam.GL);
					String language = RemoveRequestUtil.getParamString(request, EWebParam.HL);
					
					return RemoveResponseUtil.buildOk(GoogleSuggestClient.init().findSuggests(query, language, country));
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			
			get(ERestPath.COMMON_DEINDEXATION_STATUS.getNonBasePath(), (Request request, Response response) -> {
	        	try {
					return RemoveResponseUtil.buildOk(EDeindexationStatus.values());
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
			
			get(ERestPath.COMMON_EMAIL.getNonBasePath(), (Request request, Response response) -> {
				try {
					RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
					
					if (token.getProfile().equals(EProfiles.CLIENT.getCode())) {
						ClientAccessValidator.init(token).validateClientAccess();
						Client client = ClientDao.init().getBasicByUuid(token.getUuidAccount());
						
						if (client != null) {
							return RemoveResponseUtil.buildOk(client.getEmail());
						}
					} else if (EProfiles.obtain(token.getProfile()) != null) {
						UserAccessValidator.init(token).validateUserAccess();
						User user = UserDao.init().getBasicByUuid(token.getUuidAccount());
						
						if (user != null) {
							return RemoveResponseUtil.buildOk(user.getEmail());
						}
					}
					
					return RemoveResponseUtil.buildDefaultOk();
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });
						
			get(ERestPath.COMMON_PING.getNonBasePath(), (Request request, Response response) -> {
				return RemoveResponseUtil.buildDefaultOk();
	        });
	    });
	}
}
