package cl.atianza.remove.utils;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.enums.EFeelings;
import cl.atianza.remove.enums.EImpulseStatus;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.client.ClientPlan;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerImpulse;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.models.commons.ScannerResultImageSnippet;
import cl.atianza.remove.models.commons.ScannerResultNewsSnippet;
import cl.atianza.remove.models.commons.ScannerResultRaw;
import cl.atianza.remove.models.commons.ScannerResultWebSnippet;
import cl.atianza.remove.models.commons.ScannerTrackingWord;

/**
 * 
 * @author Quiliano Gutierrez
 *
 */
public class RemoveFormula {
	private static Logger log = LogManager.getLogger(RemoveFormula.class);
	
	private static Map<Integer, Float> referencePositionScore20 = Stream.of(
		new AbstractMap.SimpleEntry<>(1, 20f),
		new AbstractMap.SimpleEntry<>(2, 18f),
		new AbstractMap.SimpleEntry<>(3, 16f),
		new AbstractMap.SimpleEntry<>(4, 14f),
		new AbstractMap.SimpleEntry<>(5, 10f),
		new AbstractMap.SimpleEntry<>(6, 8f),
		new AbstractMap.SimpleEntry<>(7, 6f),
		new AbstractMap.SimpleEntry<>(8, 3f),
		new AbstractMap.SimpleEntry<>(9, 3f),
		new AbstractMap.SimpleEntry<>(10, 1f),
		new AbstractMap.SimpleEntry<>(11, 0.2f),
		new AbstractMap.SimpleEntry<>(12, 0.2f),
		new AbstractMap.SimpleEntry<>(13, 0.1f),
		new AbstractMap.SimpleEntry<>(14, 0.1f),
		new AbstractMap.SimpleEntry<>(15, 0.1f),
		new AbstractMap.SimpleEntry<>(16, 0.1f),
		new AbstractMap.SimpleEntry<>(17, 0.05f),
		new AbstractMap.SimpleEntry<>(18, 0.05f),
		new AbstractMap.SimpleEntry<>(19, 0.05f),
		new AbstractMap.SimpleEntry<>(20, 0.05f)
	).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	
	private static Map<Integer, Float> referencePositionScore19 = Stream.of(
			new AbstractMap.SimpleEntry<>(1, 20f),
			new AbstractMap.SimpleEntry<>(2, 18f),
			new AbstractMap.SimpleEntry<>(3, 16f),
			new AbstractMap.SimpleEntry<>(4, 14f),
			new AbstractMap.SimpleEntry<>(5, 10f),
			new AbstractMap.SimpleEntry<>(6, 8f),
			new AbstractMap.SimpleEntry<>(7, 6f),
			new AbstractMap.SimpleEntry<>(8, 4f),
			new AbstractMap.SimpleEntry<>(9, 3f),
			new AbstractMap.SimpleEntry<>(10, 0.2f),
			new AbstractMap.SimpleEntry<>(11, 0.2f),
			new AbstractMap.SimpleEntry<>(12, 0.1f),
			new AbstractMap.SimpleEntry<>(13, 0.1f),
			new AbstractMap.SimpleEntry<>(14, 0.1f),
			new AbstractMap.SimpleEntry<>(15, 0.1f),
			new AbstractMap.SimpleEntry<>(16, 0.05f),
			new AbstractMap.SimpleEntry<>(17, 0.05f),
			new AbstractMap.SimpleEntry<>(18, 0.05f),
			new AbstractMap.SimpleEntry<>(19, 0.05f)
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	
	private static Map<Integer, Float> referencePositionScore18 = Stream.of(
			new AbstractMap.SimpleEntry<>(1, 20f),
			new AbstractMap.SimpleEntry<>(2, 18f),
			new AbstractMap.SimpleEntry<>(3, 16f),
			new AbstractMap.SimpleEntry<>(4, 14f),
			new AbstractMap.SimpleEntry<>(5, 12f),
			new AbstractMap.SimpleEntry<>(6, 9f),
			new AbstractMap.SimpleEntry<>(7, 6f),
			new AbstractMap.SimpleEntry<>(8, 4f),
			new AbstractMap.SimpleEntry<>(9, 0.2f),
			new AbstractMap.SimpleEntry<>(10, 0.2f),
			new AbstractMap.SimpleEntry<>(11, 0.1f),
			new AbstractMap.SimpleEntry<>(12, 0.1f),
			new AbstractMap.SimpleEntry<>(13, 0.1f),
			new AbstractMap.SimpleEntry<>(14, 0.1f),
			new AbstractMap.SimpleEntry<>(15, 0.05f),
			new AbstractMap.SimpleEntry<>(16, 0.05f),
			new AbstractMap.SimpleEntry<>(17, 0.05f),
			new AbstractMap.SimpleEntry<>(18, 0.05f)
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	
	private static Map<Integer, Float> referencePositionScore17 = Stream.of(
			new AbstractMap.SimpleEntry<>(1, 20f),
			new AbstractMap.SimpleEntry<>(2, 18f),
			new AbstractMap.SimpleEntry<>(3, 16f),
			new AbstractMap.SimpleEntry<>(4, 14f),
			new AbstractMap.SimpleEntry<>(5, 12f),
			new AbstractMap.SimpleEntry<>(6, 10f),
			new AbstractMap.SimpleEntry<>(7, 9f),
			new AbstractMap.SimpleEntry<>(8, 0.2f),
			new AbstractMap.SimpleEntry<>(9, 0.2f),
			new AbstractMap.SimpleEntry<>(10, 0.1f),
			new AbstractMap.SimpleEntry<>(11, 0.1f),
			new AbstractMap.SimpleEntry<>(12, 0.1f),
			new AbstractMap.SimpleEntry<>(13, 0.1f),
			new AbstractMap.SimpleEntry<>(14, 0.05f),
			new AbstractMap.SimpleEntry<>(15, 0.05f),
			new AbstractMap.SimpleEntry<>(16, 0.05f),
			new AbstractMap.SimpleEntry<>(17, 0.05f)
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	
	private static Map<Integer, Float> referencePositionScore16 = Stream.of(
			new AbstractMap.SimpleEntry<>(1, 22f),
			new AbstractMap.SimpleEntry<>(2, 20f),
			new AbstractMap.SimpleEntry<>(3, 18f),
			new AbstractMap.SimpleEntry<>(4, 16f),
			new AbstractMap.SimpleEntry<>(5, 13f),
			new AbstractMap.SimpleEntry<>(6, 10f),
			new AbstractMap.SimpleEntry<>(7, 0.2f),
			new AbstractMap.SimpleEntry<>(8, 0.2f),
			new AbstractMap.SimpleEntry<>(9, 0.1f),
			new AbstractMap.SimpleEntry<>(10, 0.1f),
			new AbstractMap.SimpleEntry<>(11, 0.1f),
			new AbstractMap.SimpleEntry<>(12, 0.1f),
			new AbstractMap.SimpleEntry<>(13, 0.05f),
			new AbstractMap.SimpleEntry<>(14, 0.05f),
			new AbstractMap.SimpleEntry<>(15, 0.05f),
			new AbstractMap.SimpleEntry<>(16, 0.05f)
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	
	private static Map<Integer, Float> referenceImagePositionScore = Stream.of(
			new AbstractMap.SimpleEntry<>(1, 10f),
			new AbstractMap.SimpleEntry<>(2, 10f),
			new AbstractMap.SimpleEntry<>(3, 9f),
			new AbstractMap.SimpleEntry<>(4, 9f),
			new AbstractMap.SimpleEntry<>(5, 8f),
			new AbstractMap.SimpleEntry<>(6, 8f),
			new AbstractMap.SimpleEntry<>(7, 7f),
			new AbstractMap.SimpleEntry<>(8, 7f),
			new AbstractMap.SimpleEntry<>(9, 5f),
			new AbstractMap.SimpleEntry<>(10, 5f),
			new AbstractMap.SimpleEntry<>(11, 4f),
			new AbstractMap.SimpleEntry<>(12, 4f),
			new AbstractMap.SimpleEntry<>(13, 3f),
			new AbstractMap.SimpleEntry<>(14, 3f),
			new AbstractMap.SimpleEntry<>(15, 1.5f),
			new AbstractMap.SimpleEntry<>(16, 1.5f),
			new AbstractMap.SimpleEntry<>(17, 1.5f),
			new AbstractMap.SimpleEntry<>(18, 1.5f),
			new AbstractMap.SimpleEntry<>(19, 0.5f),
			new AbstractMap.SimpleEntry<>(20, 0.5f),
			new AbstractMap.SimpleEntry<>(21, 0.1f),
			new AbstractMap.SimpleEntry<>(22, 0.1f),
			new AbstractMap.SimpleEntry<>(23, 0.1f),
			new AbstractMap.SimpleEntry<>(24, 0.1f),
			new AbstractMap.SimpleEntry<>(25, 0.05f),
			new AbstractMap.SimpleEntry<>(26, 0.05f),
			new AbstractMap.SimpleEntry<>(27, 0.05f),
			new AbstractMap.SimpleEntry<>(28, 0.05f),
			new AbstractMap.SimpleEntry<>(29, 0.05f),
			new AbstractMap.SimpleEntry<>(30, 0.05f),
			new AbstractMap.SimpleEntry<>(31, 0.05f),
			new AbstractMap.SimpleEntry<>(32, 0.05f),
			new AbstractMap.SimpleEntry<>(33, 0.02f),
			new AbstractMap.SimpleEntry<>(34, 0.02f),
			new AbstractMap.SimpleEntry<>(35, 0.02f),
			new AbstractMap.SimpleEntry<>(36, 0.02f),
			new AbstractMap.SimpleEntry<>(37, 0.02f),
			new AbstractMap.SimpleEntry<>(38, 0.01f),
			new AbstractMap.SimpleEntry<>(39, 0.01f),
			new AbstractMap.SimpleEntry<>(40, 0.01f),
			new AbstractMap.SimpleEntry<>(41, 0.01f),
			new AbstractMap.SimpleEntry<>(42, 0.01f),
			new AbstractMap.SimpleEntry<>(43, 0.01f),
			new AbstractMap.SimpleEntry<>(44, 0.01f),
			new AbstractMap.SimpleEntry<>(45, 0.01f),
			new AbstractMap.SimpleEntry<>(46, 0.01f),
			new AbstractMap.SimpleEntry<>(47, 0.01f)
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	
	/**
	 * 
	 * @param result
	 */
	@Deprecated
	private static void calulateRatings(ScannerResult result) {
		log.info("Calculating Ratings For: " + result.getUuid());
		float reputationScore = 0f;
		float visualizationScore = 0f;
		
		Map<Integer, Float> referencePositionScoreNewsAssigned = selectScoreTable(result.lastNewsSearch().size());
		
		for (ScannerResultNewsSnippet snippet : result.lastNewsSearch()) {
			if (snippet.lastTracking().getPosition().intValue() <= 20) {
				reputationScore += EFeelings.find(snippet.getFeeling()).getScore().intValue() * referencePositionScoreNewsAssigned.get(snippet.lastTracking().getPosition());
				visualizationScore += !EFeelings.NOT_APPLIED.getTag().equals(snippet.getFeeling()) ? referencePositionScoreNewsAssigned.get(snippet.lastTracking().getPosition()) : 0;
			}
		};
		
		log.info("Total Web Snippet: " + result.getSnippetsWebs().size());
		log.info("Total LAST Web Snippet: " + result.lastWebSearch().size());
		Map<Integer, Float> referencePositionScoreWebAssigned = selectScoreTable(result.lastWebSearch().size());
		log.info("Using score: " + referencePositionScoreWebAssigned.size());
		
		for (ScannerResultWebSnippet snippet : result.lastWebSearch()) {
			if (snippet.lastTracking().getPosition().intValue() <= 20) {
				reputationScore += EFeelings.find(snippet.getFeeling()).getScore().intValue() * referencePositionScoreWebAssigned.get(snippet.lastTracking().getPosition());
				visualizationScore += !EFeelings.NOT_APPLIED.getTag().equals(snippet.getFeeling()) ? referencePositionScoreWebAssigned.get(snippet.lastTracking().getPosition()) : 0;
			}
		};
		
		for (ScannerResultImageSnippet snippet : result.lastImagesSearch()) {
			if (snippet.lastTracking().getPosition().intValue() <= 47) {
				reputationScore += EFeelings.find(snippet.getFeeling()).getScore().intValue() * referenceImagePositionScore.get(snippet.lastTracking().getPosition());
				visualizationScore += !EFeelings.NOT_APPLIED.getTag().equals(snippet.getFeeling()) ? referenceImagePositionScore.get(snippet.lastTracking().getPosition()) : 0;
			}
		};
		
		log.info("Total Reputation Score For: " + result.getUuid() + " = " + reputationScore);
		log.info("Total Visualization Score For: " + result.getUuid() + " = " + visualizationScore);
		
		reputationScore = standarizeReputation(reputationScore, visualizationScore);
		log.info("Ranged Reputation Score For: " + result.getUuid() + " = " + reputationScore);
		
		result.setRating_reputation(reputationScore);
		result.setRating_visualization(visualizationScore);
		
		for (ScannerResultRaw raw : result.getRaws()) {
			raw.setRating_reputation(reputationScore);
			raw.setRating_visualization(visualizationScore);	
		};
	}
	
	private static Map<Integer, Float> selectScoreTable(int totalResults) {
		if (totalResults == 16) {
			return referencePositionScore16;
		} else if (totalResults == 17) {
			return referencePositionScore17;
		} else if (totalResults == 18) {
			return referencePositionScore18;
		} else if (totalResults == 19) {
			return referencePositionScore19;
		}
		
		return referencePositionScore20;
	}

	/* DEPRECADO
	public static void calulateRawNewsRatings(ScannerResultRaw raw, List<ScannerResultNewsSnippet> list) {
		float reputationScore = 0f;
		float visualizationScore = 0f;
		
		if (list != null && !list.isEmpty()) {
			for (ScannerResultNewsSnippet snippet : list) {
				if (snippet.lastTracking().getPosition().intValue() <= 20) {
					reputationScore += EFeelings.find(snippet.getFeeling()).getScore().intValue() * referencePositionScore20.get(snippet.lastTracking().getPosition());
					visualizationScore += !EFeelings.NOT_APPLIED.getTag().equals(snippet.getFeeling()) ? referencePositionScore20.get(snippet.lastTracking().getPosition()) : 0;
				}
			};
		}
		
		reputationScore = standarizeReputation(reputationScore, visualizationScore);
		
		raw.setRating_reputation(reputationScore);
		raw.setRating_visualization(visualizationScore);	
	}
	
	public static void calulateRawWebsRatings(ScannerResultRaw raw, List<ScannerResultWebSnippet> list) {
		float reputationScore = 0f;
		float visualizationScore = 0f;
		
		if (list != null && !list.isEmpty()) {
			for (ScannerResultWebSnippet snippet : list) {
				if (snippet.lastTracking().getPosition().intValue() <= 20) {
					reputationScore += EFeelings.find(snippet.getFeeling()).getScore().intValue() * referencePositionScore20.get(snippet.lastTracking().getPosition());
					visualizationScore += !EFeelings.NOT_APPLIED.getTag().equals(snippet.getFeeling()) ? referencePositionScore20.get(snippet.lastTracking().getPosition()) : 0;
				}
			};
		}
		
		reputationScore = standarizeReputation(reputationScore, visualizationScore);
		
		raw.setRating_reputation(reputationScore);
		raw.setRating_visualization(visualizationScore);	
	}
	
	public static void calulateRawImagesRatings(ScannerResultRaw raw, List<ScannerResultImageSnippet> list) {
		float reputationScore = 0f;
		float visualizationScore = 0f;
		
		if (list != null && !list.isEmpty()) {
			for (ScannerResultImageSnippet snippet : list) {
				if (snippet.lastTracking().getPosition().intValue() <= 47) {
					reputationScore += EFeelings.find(snippet.getFeeling()).getScore().intValue() * referenceImagePositionScore.get(snippet.lastTracking().getPosition());
					visualizationScore += !EFeelings.NOT_APPLIED.getTag().equals(snippet.getFeeling()) ? referenceImagePositionScore.get(snippet.lastTracking().getPosition()) : 0;
				}
			};
		}
		
		reputationScore = standarizeReputation(reputationScore, visualizationScore);
		
		raw.setRating_reputation(reputationScore);
		raw.setRating_visualization(visualizationScore);	
	}
	*/
	@Deprecated
	private static Float standarizeReputation(float reputationScore, float visualizationScore) {
		// return visualizationScore > 0 ? (reputationScore / 3) + (100/3) : 0f;
		return visualizationScore > 0 ? 
				(reputationScore >= 0 
					? reputationScore / (EFeelings.GOOD.getScore().intValue()) 
							: reputationScore / (-1 * EFeelings.BAD.getScore().intValue())) 
				: 0f;
	}

	private static void calculateProgress(ScannerResult result, List<ScannerTrackingWord> urls, ClientPlan plan, List<ScannerImpulse> impulses) {
		try {
			if (plan.getDetail().getTarget_page() != null) {
				if (urls != null && !urls.isEmpty()) {															// Its a remove url program
					int totalUrls = urls.size();
					int pageToClean = plan.getDetail().getTarget_page().intValue();
					int totalCleaned = 0;
					
					log.info("totalUrls: " + totalUrls);
					log.info("pageToClean: " + pageToClean);
					
					for (ScannerTrackingWord url : urls) {
//						log.info("LOOKING FOR URL IN POSITION: " + url.getWord());
						Integer page = findInResult(url.getWord(), result);
						
//						log.info("URL FOUND IN PAGE " + page + ": " + url.getWord());
						if (page == null || page.intValue() > pageToClean) {
							totalCleaned++;
						}
					};
					
					result.setProgress( totalCleaned != 0 ? ((float) totalCleaned / (float) totalUrls) * 100f : 0f);
					log.info("totalCleaned: " + totalCleaned);
					log.info("result.getProgress: " + result.getProgress());
				}  else {
					log.error("Urls list list is empty...");
					result.setProgress(0f);
				}
				
				log.info("Formula Impulses: " + impulses != null ? impulses.size() : impulses);
				if (impulses != null && !impulses.isEmpty() && plan.getDetail().getTarget_page() != null) {// Its an impulse url program
//						&& plan.getDetail().getDuration_months() != null) {										
					int pageToPositionate = plan.getDetail().getTarget_page().intValue();						// TODO: esto viene del plan? o siempre es 1?
					log.info("Total pageToPositionate: " + pageToPositionate);
//					int totalCapByPlan = plan.getDetail().getMax_url_to_impulse().intValue() * plan.getDetail().getDuration_months().intValue();
//					int totalPositionated = 0;
					log.info("Total Impulses: " + impulses.size());
					for (ScannerImpulse impulse : impulses) {
						if (impulse.getStatus().equalsIgnoreCase(EImpulseStatus.PUBLISHED.getTag())) {
							if (impulse.isTarget_reached()) {
//								log.info("Ignore impulse (Target Reached): " + impulse.getReal_publish_link());
//								totalPositionated++;
							} else {
								log.info("LOOKING FOR IMPULSE IN POSITION: " + impulse.getReal_publish_link());
								Integer page = findInResult(impulse.getReal_publish_link(), result);	
								
								log.info("IMPULSE FOUND IN PAGE " + page + ": " + impulse.getReal_publish_link());
								if (page != null && page.intValue() <= pageToPositionate) {
									try {
										ScannerImpulseDao.init().markTargetReached(impulse.getId());
									} catch (RemoveApplicationException e) {
										log.error("Error updating impulse as target reached: ", e);
									}
//									totalPositionated++;
								}
							}
						}
					}
					
					// Fecha 10/06/2022: Se decidió que el objetivo no se calculará en base a los impulsos
//					result.setProgress( totalPositionated != 0 ? ((float) totalPositionated / (float) totalCapByPlan) * 100f : 0f);	
//					log.info("totalPositionated: " + totalPositionated);
//					log.info("result.getProgress: " + result.getProgress());
				}
			}
		} catch (Throwable ex) {
			log.error("Error Calculating progress for result: " + result.getUuid(), plan, impulses, urls, ex);
			log.error("Error Calculating progress: " + result.getUuid(), plan, impulses, urls, ex);
			result.setProgress(0f);
		}
	}

	private static Integer findInResult(String url, ScannerResult result) {
		if (result != null) {
			if (result.getSnippetsWebs() != null && !result.getSnippetsWebs().isEmpty()) {
				log.info("LOOKING IN WEBS:" + result.getSnippetsWebs().size());
				for (ScannerResultWebSnippet snippet: result.getSnippetsWebs()) {
//					log.info("URL: " + snippet.getLink());
					if (snippet.getLink().equals(url)) {
						return snippet.getTracking() != null ? snippet.getTracking().get(snippet.getTracking().size() - 1).getPage() : null;
					}
				}
			}
			if (result.getSnippetsNews() != null && !result.getSnippetsNews().isEmpty()) {
				log.info("LOOKING IN NEWS:" + result.getSnippetsNews().size());
				for (ScannerResultNewsSnippet snippet: result.getSnippetsNews()) {
//					log.info("URL: " + snippet.getLink());
					if (snippet.getLink().equals(url)) {
						return snippet.getTracking() != null ? snippet.getTracking().get(snippet.getTracking().size() - 1).getPage() : null;
					}
				}
			}
			if (result.getSnippetsImages() != null && !result.getSnippetsImages().isEmpty()) {
				log.info("LOOKING IN IMAGES:" + result.getSnippetsImages().size());
				for (ScannerResultImageSnippet snippet: result.getSnippetsImages()) {
//					log.info("URL: " + snippet.getLink());
					if (snippet.getLink().equals(url)) {
						return snippet.getTracking() != null ? snippet.getTracking().get(snippet.getTracking().size() - 1).getPage() : null;
					}
				}
			}
		}
		
		return null;
	}

	/**
	 * 
	 * @param scanner
	 * @param result
	 * @throws RemoveApplicationException 
	 */
	public static void processResult(Scanner scanner, ScannerResult result) throws RemoveApplicationException {
		if (scanner.esTransform()) {
			ClientPlan plan = ClientPlanDao.init().getById(scanner.getId_client_plan());
			
			if (plan != null) {
				scanner.setImpulses(ScannerImpulseDao.init().list(scanner.getId()));
				RemoveFormula.calculateProgress(result, scanner.getJustTrackingURLs(), plan, scanner.getImpulses());	
			} else {
				log.warn("Plan not found, Can't calculate progress: ", scanner.getUuid());
			}
		}
		
		// RemoveFormula.calulateRatings(result); Se cambiará la forma de evaluar visibilidad y rating
	}
	
	public static long calculateMrr() throws RemoveApplicationException {
		long result = 0l;
		
		try {
			List<ClientPlan> list = ClientPlanDao.init().listActives();
			
			if (list != null) {
				for (ClientPlan clientPlan : list) {
					Plan plan = PlanDao.init().get(clientPlan.getId_plan());
					
					result += (plan.getCost().longValue() / plan.getDuration_months().longValue());
				}
			}
			
		} catch (Exception ex) {
			log.error("Error calculating Mrr:", ex);
		}
		
		return result;
	}
	
	/**
	 * 
	 * Reference (https://www.inboundcycle.com/blog-de-inbound-marketing/customer-lifetime-value-cltv-como-calcularlo)
	 * @return
	 * @throws RemoveApplicationException 
	 */
	public static long calculateCLV() throws RemoveApplicationException {
		int CUSTOMER_RETENTION_RATE = 75; // % customer retention rate for example pruposes
		int CUSTOMER_CHURN_RATE = 100 - CUSTOMER_RETENTION_RATE; // % customer cancelation rate for example pruposes
		
		long averageCostsPurchases = 0; // Promedio de pagos realizados por suscripciones al mes (Este o el pasado?)
		
		List<ClientPlan> list = ClientPlanDao.init().listActives();
		long averageActivesSuscriptions = list.size(); // Promedio de suscripciones activas al mes (Este o el pasado?)
		for (ClientPlan clientPlans : list) {
			Plan plan = PlanDao.init().get(clientPlans.getId_plan());
			averageCostsPurchases += (plan.getCost().longValue() / plan.getDuration_months().longValue());
		}
		
		long CLTV1 = (averageCostsPurchases / averageActivesSuscriptions) * 12; // Promedio al año
		long percentageProfitYear = 100; // Definir el margen de contribucion
		
		long CLTV2 = CLTV1 * percentageProfitYear / 100;
		long CLTV3 = CLTV2 * CUSTOMER_RETENTION_RATE / (1 - CUSTOMER_CHURN_RATE - CUSTOMER_RETENTION_RATE);
		
		
		return (CLTV1 + CLTV2 + CLTV3) / 3;
	}
}
