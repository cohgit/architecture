package cl.atianza.remove.helpers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.client.ClientAlertMessageDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.commons.ConfigurationDao;
import cl.atianza.remove.daos.commons.ScannerResultDao;
import cl.atianza.remove.enums.EClientAlertMessageTypes;
import cl.atianza.remove.enums.ELanguages;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.enums.ESearchTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.client.ClientAlertMessage;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerAlert;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.models.views.NotificationInOutSnippetsRecurrent;
import cl.atianza.remove.utils.AlertSectionUtils;
import cl.atianza.remove.utils.RemoveBundle;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveJsonUtil;

/**
 * Class used for client notification process (Notification service and Email
 * Notification Services).
 * 
 * @author Quiliano Gutierrez
 *
 */
public class ClientNotificationHelper {
	protected RemoveBundle bundle;
	private Logger log = LogManager.getLogger(ClientNotificationHelper.class);
	private Client client;
	private ConfigurationDao configDao;

	public ClientNotificationHelper(Client client) throws RemoveApplicationException {
		super();
		this.client = client;
		this.configDao = ConfigurationDao.init();
	}

	public static ClientNotificationHelper init(Client client) throws RemoveApplicationException {
		return new ClientNotificationHelper(client);
	}

	/**
	 * Create a Email Verification solitude notification.
	 * 
	 * @param uuidChange
	 * @param email
	 */
	public void createVerifyEmailSolitude(String uuidChange, String email) {
		try {
			client.setEmail(email);
			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{VERIFICATION_EMAIL_URL}}", configDao.getFrontEndDomainBase()
					+ configDao.getEmailParamClientVerificationEmailUrlPage() + uuidChange);
			createNotification(new ClientAlertMessage(client.getId(), new HashMap<String, Object>()),
					EClientAlertMessageTypes.VERIFY_MAIL_SOLITUDE, tokens);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createVerifyEmailNotification': ", ex);
		}
	}

	/**
	 * Create a Email Verified notification.
	 */
	public void createVerifiedEmailSuccess() {
		try {
			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{CLIENT_LOGIN_URL}}",
					configDao.getFrontEndDomainBase() + configDao.getEmailParamClientLoginUrlPage());
			createNotification(new ClientAlertMessage(client.getId(), new HashMap<String, Object>()),
					EClientAlertMessageTypes.VERIFIED_MAIL_SUCCESFULL, tokens);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createVerifyEmailNotification': ", ex);
		}
	}

	/**
	 * Create a Email Content Alert.
	 */
	public void createContentAlertClient(String scan_name, String description, String clientName,
			AlertSectionUtils section, Client client, String query_date, String rastreo, String busqueda, ScannerAlert alert, String type) {
		try {
			this.bundle = RemoveBundle.init(ELanguages.SPANISH.getCode());
			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{SCANNER_NAME}", scan_name);
			tokens.put("{PAGE_CONFIGURATIONS}", description);
			tokens.put("{CLIENT_NAME}", clientName);
			tokens.put("{query_date}", query_date);
			tokens.put("{TEMAS_RASTREO}", rastreo);
			tokens.put("{TEMAS_BUSQUEDA}", busqueda);
			

			String tableNew = "";
			String tableNeg = "";
			String tableUrl = "";
			String tableTack = "";
			String tableKw = "";

			if(alert.isNew_content_detected()) {
				if(section.getNew_content() != null && !section.getNew_content().isEmpty()) {
					tokens.put("{TitleNew}", "Contenido Nuevo");
					tokens.put("{NEW_TOTAL}", Integer.toString(section.getNew_content().size()));
					for (int i = 0; i < section.getNew_content().size(); i++) {
						tableNew += "<tr>";
						tableNew += "<td><p style='font-size: 12px; color: #8b8b8b;'>"
								+ section.getNew_content().get(i).getKeyword() + " - "
								+bundle.getLabelCountryBundle(section.getNew_content().get(i).getCountry()) + " - "
								+bundle.getLabelBundle("scanner.section." + section.getNew_content().get(i).getSection()) + " - "+section.getNew_content().get(i).getPage()+"/"+section.getNew_content().get(i).getPosition_in_page()+" <br/>"

								+ "<span style='font-size: 12px; color: #000000;'><strong>"
								+ section.getNew_content().get(i).getTitle() + "<strong></span><br/>"

								+ "<a style='font-size: 12px' href="
								+ section.getNew_content().get(i).getLink() + " target='_blank'>"
								
								+ section.getNew_content().get(i).getLink() + "</a></p>";

						tableNew += "<td>";
						tableNew += "</tr>";
					}
				 tokens.put("{new_content}", tableNew);
				}else {
					tokens.put("{TitleNew}", "Contenido Nuevo");
					tokens.put("{NEW_TOTAL}", "No se encontraron coincidencias");
					tokens.put("{new_content}", "										");
				}				
			}else {
				tokens.put("{TitleNew}", "										");
				tokens.put("{NEW_TOTAL}", "										");
				tokens.put("{new_content}", "										");
			}
			
			if(alert.isNegative_content_detected()) {
				if(section.getNegative_content() != null && !section.getNegative_content().isEmpty()) {
					tokens.put("{TitleNegative}", "Contenido Negativo");
					tokens.put("{NEG_TOTAL}", Integer.toString(section.getNegative_content().size()));
					for (int i = 0; i < section.getNegative_content().size(); i++) {
						tableNeg += "<tr>";
						tableNeg += "<td><p style='font-size: 12px; color: #8b8b8b;'>"
								+ section.getNegative_content().get(i).getKeyword() + " - "
								+ bundle.getLabelCountryBundle(section.getNegative_content().get(i).getCountry()) + " - "
								+ bundle.getLabelBundle("scanner.section." + section.getNegative_content().get(i).getSection()) + " - "+section.getNegative_content().get(i).getPage()+"/"+section.getNegative_content().get(i).getPosition_in_page()+" <br/>"

								+ "<span style='font-size: 12px; color: #000000;'><strong>"
								+ section.getNegative_content().get(i).getTitle() + "<strong></span><br/>"

								+ "<a style='font-size: 12px' href="
								+ section.getNegative_content().get(i).getLink() + " target='_blank'>"
								
								+ section.getNegative_content().get(i).getLink() + "</a></p>";

						tableNeg += "<td>";
						tableNeg += "</tr>";
					}
					tokens.put("{negative_content}", tableNeg);
				}else {
					tokens.put("{TitleNegative}", "Contenido Negativo");
					tokens.put("{NEG_TOTAL}", "No se encontraron coincidencias");
					tokens.put("{negative_content}", tableNeg);
				}				

			}else {
				tokens.put("{TitleNegative}", "										");
				tokens.put("{NEG_TOTAL}", "										");
				tokens.put("{negative_content}", "										");
			}


			if(alert.isDisplaced_url()) {
				if(section.getDisplaced_url() != null && !section.getDisplaced_url().isEmpty()) {
					tokens.put("{TitleUrl}", type.equalsIgnoreCase(EScannerTypes.MONITOR.getCode()) ? "Enlaces (URL) a monitorear": "Enlaces (URL) a anular");
					tokens.put("{URL_TOTAL}", Integer.toString(section.getDisplaced_url().size()));
					for (int i = 0; i < section.getDisplaced_url().size(); i++) {
						tableUrl += "<tr>";
						tableUrl += "<td><p style='font-size: 12px; color: #8b8b8b;'>"
								+ section.getDisplaced_url().get(i).getKeyword() + " - "
								+ bundle.getLabelCountryBundle(section.getDisplaced_url().get(i).getCountry()) + " - "
								+ bundle.getLabelBundle("scanner.section." + section.getDisplaced_url().get(i).getSection()) + " - "+section.getDisplaced_url().get(i).getPage()+"/"+section.getDisplaced_url().get(i).getPosition_in_page()+" <br/>"

								+ "<span style='font-size: 12px; color: #000000;'><strong>"
								+ section.getDisplaced_url().get(i).getTitle() + "<strong></span><br/>"

								+ "<a style='font-size: 12px' href="
								+ section.getDisplaced_url().get(i).getLink() + " target='_blank'>"
								
								+ section.getDisplaced_url().get(i).getLink() + "</a></p>";

						tableUrl += "<td>";
						tableUrl += "</tr>";

					}
					tokens.put("{url}", tableUrl);
				}else {
					tokens.put("{TitleUrl}", type.equalsIgnoreCase(EScannerTypes.MONITOR.getCode()) ? "Enlaces (URL) a monitorear": "Enlaces (URL) a anular");
					tokens.put("{URL_TOTAL}", "No se encontraron coincidencias");
					tokens.put("{url}", tableUrl);
				}				

			}else {
				tokens.put("{TitleUrl}", "										");
				tokens.put("{URL_TOTAL}", "										");
				tokens.put("{url}", "										");
			}			
			
			
			if(alert.isMatching_tracking_word()) {
				if(section.getMatch_track() != null && !section.getMatch_track().isEmpty()) {
					tokens.put("{TitleTrack}", "Temas de Rastreo: ");
					tokens.put("{TRACK_TOTAL}", Integer.toString(section.getMatch_track().size()));
					for (int i = 0; i < section.getMatch_track().size(); i++) {
						tableTack += "<tr>";
						tableTack += "<td><p style='font-size: 12px; color: #8b8b8b;'>"
								+ section.getMatch_track().get(i).getKeyword() + " - "
								+ bundle.getLabelCountryBundle(section.getMatch_track().get(i).getCountry()) + " - "
								+ bundle.getLabelBundle("scanner.section." + section.getMatch_track().get(i).getSection()) + " - "+section.getMatch_track().get(i).getPage()+"/"+section.getMatch_track().get(i).getPosition_in_page()+
								" - "+section.getMatch_track().get(i).getTracking_word()+" <br/>"
								
								
								+ "<span style='font-size: 12px; color: #000000;'><strong>"
								+ section.getMatch_track().get(i).getTitle() + "<strong></span><br/>"

								+ "<a style='font-size: 12px' href="
								+ section.getMatch_track().get(i).getLink() + " target='_blank'>"
								
								+ section.getMatch_track().get(i).getLink() + "</a></p>";

						tableTack += "<td>";
						tableTack += "</tr>";

					}
					tokens.put("{track}", tableTack);
				}else {
					tokens.put("{TitleTrack}", "Temas de Rastreo: ");
					tokens.put("{TRACK_TOTAL}", "No se encontraron coincidencias");
					tokens.put("{track}", tableTack);
				}				

			}else {
				tokens.put("{TitleTrack}", "										");
				tokens.put("{TRACK_TOTAL}", "										");
				tokens.put("{track}", "										");
			}			
			
			
			if(alert.isMatching_keyword()) {
				if(section.getMatch_key() != null && !section.getMatch_key().isEmpty()) {
					tokens.put("{TitleKey}", "Terminos de busqueda: ");
					tokens.put("{KW_TOTAL}", Integer.toString(section.getMatch_key().size()));
					for (int i = 0; i < section.getMatch_key().size(); i++) {
						tableKw += "<tr>";
						tableKw += "<td><p style='font-size: 12px; color: #8b8b8b;'>"
								+ section.getMatch_key().get(i).getKeyword() + " - "
								+ bundle.getLabelCountryBundle(section.getMatch_key().get(i).getCountry()) + " - "
								+ bundle.getLabelBundle("scanner.section." + section.getMatch_key().get(i).getSection()) + " - "+section.getMatch_key().get(i).getPage()+"/"+section.getMatch_key().get(i).getPosition_in_page()+" <br/>"

								+ "<span style='font-size: 12px; color: #000000;'><strong>"
								+ section.getMatch_key().get(i).getTitle() + "<strong></span><br/>"

								+ "<a style='font-size: 12px' href="
								+ section.getMatch_key().get(i).getLink() + " target='_blank'>"
								
								+ section.getMatch_key().get(i).getLink() + "</a></p>";

						tableKw += "<td>";
						tableKw += "</tr>";

					}
					tokens.put("{kw}", tableKw);
				}else {
					tokens.put("{TitleKey}", "Terminos de busqueda: ");
					tokens.put("{KW_TOTAL}", "No se encontraron coincidencias");
					tokens.put("{kw}", tableKw);
				}				

			}else {
				tokens.put("{TitleKey}", "										");
				tokens.put("{KW_TOTAL}", "										");
				tokens.put("{kw}", "										");
			}

			createNotificationAlert(EClientAlertMessageTypes.ALERT_REPORT, tokens, client);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createContentAlertClient': ", ex);
		}
	}

	/**
	 * Create a recovery password solitude notification.
	 * 
	 * @param uuidChange
	 */
	public void createRecoveryPasswordSolitude(String uuidChange) {
		try {
			Map<String, String> tokens = new HashMap<String, String>();
			//tokens.put("{{CLIENT_NAME}}", client.getName() +" "+client.getLastname());
			tokens.put("{{RECOVERY_PASSWORD_URL}}", configDao.getFrontEndDomainBase()
					+ configDao.getEmailParamClientRecoveryPasswordUrlPage() + uuidChange);
			createNotification(new ClientAlertMessage(client.getId(), new HashMap<String, Object>()),
					EClientAlertMessageTypes.RECOVERY_PASSWORD_SOLITUDE, tokens);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createRecoveryPasswordNotification': ", ex);
		}
	}

	/**
	 * Create a Recovery Password successful notification.
	 */
	public void createRecoveryPasswordSuccessNotification() {
		try {
			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{CLIENT_LOGIN_URL}}",
					configDao.getFrontEndDomainBase() + configDao.getEmailParamClientLoginUrlPage());
			createNotification(new ClientAlertMessage(client.getId(), new HashMap<String, Object>()),
					EClientAlertMessageTypes.RECOVERIED_PASSWORD_SUCCESSFULL, tokens);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createRecoveryPasswordNotification': ", ex);
		}
	}

	/**
	 * Create a One Shot scanner initiation notification.
	 * 
	 * @param uuidScanner
	 * @param name
	 */
	public void createOneShotStartingScannerNotification(String uuidScanner, String name) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("uuidScanner", uuidScanner);
			parameters.put("name", name);

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.ONE_SHOT_STARTING_SCANNER);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createOneShotStartingScannerNotification': ", ex);
		}
	}

	/**
	 * Create a One Shot scanner finished notification.
	 * 
	 * @param uuidScanner
	 * @param name
	 */
	public void createOneShotFinishedScannerNotification(String uuidScanner, String name) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("uuidScanner", uuidScanner);
			parameters.put("name", name);

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.ONE_SHOT_FINISHED_SCANNER);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createOneShotFinishedScannerNotification': ", ex);
		}
	}

	/**
	 * Create a One Shot scanners credits running out notification.
	 * 
	 * @param clientPlan
	 */
	public void createOneShotLimitCreditsRunningOutNotification(ClientPlan clientPlan) {
		try {
			if (clientPlan.getDetail().getLimit_credits() != null) {
				float used = clientPlan.getCredits_used().floatValue();
				float total = clientPlan.getDetail().getLimit_credits().floatValue();

				if ((used / total) * 100 > 80) {
					boolean alreadySent = false;
					List<ClientAlertMessage> list = ClientAlertMessageDao.init().listNotificationByClientType(
							client.getId(), EClientAlertMessageTypes.ONE_SHOT_LIMIT_CREDITS_RUNNING_OUT.getCode());

					if (list != null) {
						for (ClientAlertMessage message : list) {
							@SuppressWarnings("unchecked")
							Map<String, Object> parameters = (Map<String, Object>) message.getParameters();

							if (parameters.get("planClientId") != null) {
								long planClientId = parameters.get("planClientId") instanceof Integer
										? ((Integer) parameters.get("planClientId")).longValue()
										: ((Long) parameters.get("planClientId")).longValue();
								if (planClientId == client.getId().longValue()) {
									alreadySent = true;
									break;
								}
							}
						}
					}

					if (!alreadySent) {
						Map<String, Object> parameters = new HashMap<String, Object>();

						parameters.put("planId", clientPlan.getDetail().getId());
						parameters.put("planClientId", client.getId());
						parameters.put("planName", clientPlan.getDetail().getName());

						Map<String, String> tokens = new HashMap<String, String>();
						tokens.put("{{CLIENT_LOGIN_URL}}",
								configDao.getFrontEndDomainBase() + configDao.getEmailParamClientLoginUrlPage());

						createNotification(new ClientAlertMessage(client.getId(), parameters),
								EClientAlertMessageTypes.ONE_SHOT_LIMIT_CREDITS_RUNNING_OUT, tokens);
					} else {
						log.info("Message already sent... Ignore");
					}
				}
			}
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createOneShotFinishedScannerNotification': ", ex);
		}
	}

	public void createOneShotMonthlyReportNotification(String uuidReport) {
		try {
			// TODO: CARGAR TODA LA INFORMACION DEL MES uuidReport (CREATE FROM
			// COMPLEX_TEMPLATE)
			Map<String, Object> parameters = new HashMap<String, Object>();

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.ONE_SHOT_REPORT_MONTHLY);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createOneShotFinishedScannerNotification': ", ex);
		}
	}

	/**
	 * 
	 * @param scanner
	 * @param lastScanTime
	 */
	public void createNewAndLeavingContentNotification(Scanner scanner, LocalDateTime lastScanTime) {
		try {
			NotificationInOutSnippetsRecurrent info = new NotificationInOutSnippetsRecurrent();
			info.setUuidScanner(scanner.getUuid());
			info.setName(scanner.getName());

			List<ScannerResult> lstResults = ScannerResultDao.init().listLastTwoScans(scanner.getId(), lastScanTime);

			if (lstResults != null) {
				for (ScannerResult result : lstResults) {
					if (result.getSearch_type().equals(ESearchTypes.WEB.getCode())) {
						result.getSnippetsWebs().forEach(snip -> {
							if (snip.getTracking().size() == 1) {
								if (snip.lastTracking().getDate_scan().isEqual(lastScanTime)) {
									info.addInSnippet(result, snip);
								} else {
									info.addOutSnippet(result, snip);
								}
							}
						});
					} else if (result.getSearch_type().equals(ESearchTypes.NEWS.getCode())) {
						result.getSnippetsNews().forEach(snip -> {
							if (snip.getTracking().size() == 1) {
								if (snip.lastTracking().getDate_scan().isEqual(lastScanTime)) {
									info.addInSnippet(result, snip);
								} else {
									info.addOutSnippet(result, snip);
								}
							}
						});
					}
				}
			}

			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{CLIENT_LOGIN_URL}}",
					configDao.getFrontEndDomainBase() + configDao.getEmailParamClientLoginUrlPage());
			tokens.put("{{SCANNER_NAME}}", scanner.getName());
			tokens.put("{{TOTAL_IN}}", String.valueOf(info.getLstInSnippets().size()));
			tokens.put("{{TOTAL_OUT}}", String.valueOf(info.getLstOutSnippets().size()));

			if (scanner.getType().equals(EScannerTypes.MONITOR.getCode())) {
				createRecurrentNewAndLeavingContentNotification(info, tokens);
			} else if (scanner.getType().equals(EScannerTypes.TRANSFORM.getCode())) {
				createTransformNewAndLeavingContentNotification(info, tokens);
			}
		} catch (RemoveApplicationException ex) {
			log.error("Error creating 'createNewAndLeavingContentNotification': ", ex);
		}
	}

	public void createRecurrentStartingScannerNotification(String uuidScanner, String name) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("uuidScanner", uuidScanner);
			parameters.put("name", name);

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.RECURRENT_STARTING_SCANNER);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createRecurrentStartingScannerNotification': ", ex);
		}
	}

	private void createRecurrentNewAndLeavingContentNotification(NotificationInOutSnippetsRecurrent info,
			Map<String, String> tokens) throws RemoveApplicationException {
		if (info != null) {
			info.filterForRecurrent();

			if (info.hasSnippets()) {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("info", info);

				createNotification(new ClientAlertMessage(client.getId(), parameters),
						EClientAlertMessageTypes.RECURRENT_NEW_AND_LEAVING_CONTENT, tokens);
			}
		}
	}

	public void createRecurrentConfigurationUpdatedNotification(String uuidScanner, String name) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("uuidScanner", uuidScanner);
			parameters.put("name", name);

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.RECURRENT_CONFIGURATION_UPDATED);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createRecurrentConfigurationUpdatedNotification': ", ex);
		}
	}

	public void createRecurrentMonthlyReportNotification(String uuidReport) {
		try {
			// TODO: CARGAR TODA LA INFORMACION DEL MES uuidReport // TODO: (CREATE FROM
			// COMPLEX_TEMPLATE)
			Map<String, Object> parameters = new HashMap<String, Object>();

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.RECURRENT_REPORT_MONTHLY);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createRecurrentMonthlyReportNotification': ", ex);
		}
	}

	public void createTransformStartingScannerNotification(String uuidScanner, String name) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("uuidScanner", uuidScanner);
			parameters.put("name", name);

			createNotification(new ClientAlertMessage(this.client.getId(), parameters),
					EClientAlertMessageTypes.TRANSFORM_STARTING_SCANNER);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createTransformStartingScannerNotification': ", ex);
		}
	}

	private void createTransformNewAndLeavingContentNotification(NotificationInOutSnippetsRecurrent info,
			Map<String, String> tokens) throws RemoveApplicationException {
		if (info != null) {
			info.filterForTranform();

			if (info.hasSnippets()) {
				Map<String, Object> parameters = new HashMap<String, Object>();

				parameters.put("info", info);

				createNotification(new ClientAlertMessage(client.getId(), parameters),
						EClientAlertMessageTypes.TRANSFORM_NEW_AND_LEAVING_CONTENT, tokens);
			}
		}
	}

	public void createTransformConfigurationUpdatedNotification(String uuidScanner, String name) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("uuidScanner", uuidScanner);
			parameters.put("name", name);

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.TRANSFORM_CONFIGURATION_UPDATED);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createTransformConfigurationUpdatedNotification': ", ex);
		}
	}

	public void createTransformMonthlyReportNotification(String uuidReport) {
		try {
			// TODO: CARGAR TODA LA INFORMACION DEL MES uuidReport // TODO: (CREATE FROM
			// COMPLEX_TEMPLATE)
			Map<String, Object> parameters = new HashMap<String, Object>();

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.TRANSFORM_REPORT_MONTHLY);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createTransformMonthlyReportNotification': ", ex);
		}
	}

	public void createTransformImpulseWaitingApproveNotification(String uuidScanner, ScannerImpulse impulse) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("uuidScanner", uuidScanner);
			parameters.put("estimated_publish", impulse.getEstimated_publish());
			parameters.put("keyword", impulse.getKeyword());
			parameters.put("reference_link", impulse.getReference_link());

			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{CLIENT_LOGIN_URL}}",
					configDao.getFrontEndDomainBase() + "/#/client/module/impulse/" + uuidScanner); // TODO: Mover path
																									// a configDao
			tokens.put("{{IMPULSE_TITTLE}}", impulse.getContent().getTitle());

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.TRANSFORM_IMPULSE_WAITING_APPROVE, tokens);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createTransformImpulseWaitingApproveNotification': ", ex);
		}
	}

	public void createTransformImpulseRejectedNotification(String uuidScanner, ScannerImpulse impulse) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("uuidScanner", uuidScanner);
			parameters.put("estimated_publish", impulse.getEstimated_publish());
			parameters.put("keyword", impulse.getKeyword());
			parameters.put("reference_link", impulse.getReference_link());

			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{CLIENT_LOGIN_URL}}",
					configDao.getFrontEndDomainBase() + "/#/client/module/impulse/" + uuidScanner); // TODO: Mover path
																									// a configDao
			tokens.put("{{IMPULSE_TITTLE}}", impulse.getContent().getTitle());

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.TRANSFORM_IMPULSE_REJECTED, tokens);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createTransformImpulseRejectedNotification': ", ex);
		}
	}

	public void createTransformImpulseApprovedNotification(String uuidScanner, ScannerImpulse impulse) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("uuidScanner", uuidScanner);
			parameters.put("estimated_publish", impulse.getEstimated_publish());
			parameters.put("keyword", impulse.getKeyword());
			parameters.put("reference_link", impulse.getReference_link());

			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{CLIENT_LOGIN_URL}}",
					configDao.getFrontEndDomainBase() + "/#/client/module/impulse/" + uuidScanner); // TODO: Mover path
																									// a configDao
			tokens.put("{{IMPULSE_TITTLE}}", impulse.getContent().getTitle());

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.TRANSFORM_IMPULSE_APPROVED, tokens);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createTransformImpulseApprovedNotification': ", ex);
		}
	}

	public void createTransformImpulsePublishedNotification(String uuidScanner, ScannerImpulse impulse) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("uuidScanner", uuidScanner);
			parameters.put("estimated_publish", impulse.getEstimated_publish());
			parameters.put("keyword", impulse.getKeyword());
			parameters.put("reference_link", impulse.getReference_link());
			parameters.put("publish_date", impulse.getReal_publish_date());
			parameters.put("publish_link", impulse.getReal_publish_link());

			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{CLIENT_LOGIN_URL}}",
					configDao.getFrontEndDomainBase() + "/#/client/module/impulse/" + uuidScanner); // TODO: Mover path
																									// a configDao
			tokens.put("{{IMPULSE_URL}}", impulse.getReal_publish_link());
			tokens.put("{{IMPULSE_TITTLE}}", impulse.getContent().getTitle());

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.TRANSFORM_IMPULSE_PUBLISHED, tokens);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createTransformImpulsePublishedNotification': ", ex);
		}
	}

	public void createPlanRenewedNotification() {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{CLIENT_LOGIN_URL}}",
					configDao.getFrontEndDomainBase() + configDao.getEmailParamClientLoginUrlPage());

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.RENEWAL_PLAN, tokens);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createPlanRenewedNotification': ", ex);
		}
	}

	public void createPlanToExpireNotification() {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{CLIENT_LOGIN_URL}}",
					configDao.getFrontEndDomainBase() + configDao.getEmailParamClientLoginUrlPage());

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.EXPIRES_PLAN, tokens);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createPlanToExpireNotification': ", ex);
		}
	}

	public void createDuePaymentNotificationLevel1() {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{CLIENT_LOGIN_URL}}",
					configDao.getFrontEndDomainBase() + configDao.getEmailParamClientLoginUrlPage());

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.PAYMENT_DUE_NOTIFICATION_LVL_1, tokens);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createDuePaymentNotificationLevel1': ", ex);
		}
	}

	public void createDuePaymentNotificationLevel2() {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{CLIENT_LOGIN_URL}}",
					configDao.getFrontEndDomainBase() + configDao.getEmailParamClientLoginUrlPage());

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.PAYMENT_DUE_NOTIFICATION_LVL_2, tokens);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createDuePaymentNotificationLevel2': ", ex);
		}
	}

	public void createDuePaymentNotificationLevel3() {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();

			Map<String, String> tokens = new HashMap<String, String>();
			tokens.put("{{CLIENT_LOGIN_URL}}",
					configDao.getFrontEndDomainBase() + configDao.getEmailParamClientLoginUrlPage());

			createNotification(new ClientAlertMessage(client.getId(), parameters),
					EClientAlertMessageTypes.PAYMENT_DUE_NOTIFICATION_LVL_3, tokens);
		} catch (RemoveApplicationException | Exception ex) {
			log.error("Error creating 'createDuePaymentNotificationLevel3': ", ex);
		}
	}

	private ClientAlertMessage createNotification(ClientAlertMessage message, EClientAlertMessageTypes type)
			throws RemoveApplicationException {
		return createNotification(message, type, null);
	}

	private ClientAlertMessage createNotification(ClientAlertMessage message, EClientAlertMessageTypes type,
			Map<String, String> emailsTokens) throws RemoveApplicationException {
		message.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		message.setDescription(type.getTag());
		message.setTitle(type.getTag());
		message.setEmail_sent(false);
		message.setMust_send_email(type.isMustSendEmail());
		message.setParams(RemoveJsonUtil.dataToJson(message.getParameters()));
		message.setReaded(false);
		message.setType(type.getCode());
		message.setTypeObj(type);
		message.setUrgent(type.isMustSendEmail());

		message = ClientAlertMessageDao.init().save(message);

		if (type.isMustSendEmail()) {
			EmailHelper.init().sendClientEmail(type, client, emailsTokens);
		}

		return message;
	}

	private void createNotificationAlert(EClientAlertMessageTypes type, Map<String, String> emailsTokens, Client client)
			throws RemoveApplicationException {

		EmailHelper.init().sendClientEmail(type, client, emailsTokens);

	}

	public static void main(String[] args) {
		try {
			Client client = ClientDao.init().getById(1l);
			ClientNotificationHelper helper = init(client);

			// helper.createVerifyEmailSolitude(UUID.randomUUID().toString(),
			// client.getEmail());
			// helper.createVerifiedEmailSuccess();

			// helper.createRecoveryPasswordSuccessNotification();
			helper.createRecoveryPasswordSolitude(UUID.randomUUID().toString());
			//
			// helper.createOneShotLimitCreditsRunningOutNotification();
			//
			//
			// helper.createRecurrentStartingScannerNotification(ScannerDao.init().getBasicByOwner(client.getId(),
			// EScannerTypes.MONITOR.getCode()).getUuid());
			//
			// helper.createTransformStartingScannerNotification(ScannerDao.init().getBasicByOwner(client.getId(),
			// EScannerTypes.TRANSFORM.getCode()).getUuid());
			//
			// helper.createTransformImpulseWaitingApproveNotification(ScannerDao.init().getBasicByOwner(client.getId(),
			// EScannerTypes.TRANSFORM.getCode()).getUuid(),
			// ScannerImpulseDao.init().get(22l));
			// helper.createTransformImpulseApprovedNotification(ScannerDao.init().getBasicByOwner(client.getId(),
			// EScannerTypes.TRANSFORM.getCode()).getUuid(),
			// ScannerImpulseDao.init().get(22l));
			// helper.createTransformImpulsePublishedNotification(ScannerDao.init().getBasicByOwner(client.getId(),
			// EScannerTypes.TRANSFORM.getCode()).getUuid(),
			// ScannerImpulseDao.init().get(22l));
			// helper.createTransformImpulseRejectedNotification(ScannerDao.init().getBasicByOwner(client.getId(),
			// EScannerTypes.TRANSFORM.getCode()).getUuid(),
			// ScannerImpulseDao.init().get(22l));
			//
			// helper.createPlanRenewedNotification();
			// helper.createPlanToExpireNotification();
		} catch (RemoveApplicationException e) {
			e.printStackTrace();
		}
	}
}
