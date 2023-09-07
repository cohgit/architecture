package cl.atianza.remove.tasks.report;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.admin.UserDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.commons.CountryDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerImpulseDao;
import cl.atianza.remove.daos.commons.ScannerKeywordsDao;
import cl.atianza.remove.daos.commons.ScannerResultDao;
import cl.atianza.remove.daos.commons.ScannerResultViewDao;
import cl.atianza.remove.daos.commons.ScannerTrackingWordsDao;
import cl.atianza.remove.enums.ELanguages;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.enums.EReportTypes;
import cl.atianza.remove.enums.EReportTypesMonthly;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ScannerHelper;
import cl.atianza.remove.models.ReportParams;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Country;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerKeyword;
import cl.atianza.remove.models.commons.ScannerResultView;
import cl.atianza.remove.tasks.scanner.ScannerRecurrentsTask;
import cl.atianza.remove.tasks.scanner.ScannerTaskManager;
import cl.atianza.remove.utils.RemoveBundle;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveJsonUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;

public class ReportManagerOne extends TimerTask {
	private Logger log = LogManager.getLogger(ReportManagerOne.class);  
	protected RemoveBundle bundle;
	
	public ReportManagerOne() {
		super();
	}


	@Override
	public void run() {
		log.info("Executing new Img ");
		log.info("Starting Report manager one at:" + RemoveDateUtils.nowLocalDateTime());
		List<Client> lstclient = null;
		
		try {
			
			lstclient = ClientDao.init().listActivesOne();
		
			if(lstclient != null && !lstclient.isEmpty()) {
				lstclient.forEach(client -> {
					List<Scanner> listScan = null;
					try {
						listScan = ScannerDao.init().listByOwner(client.getId());
						
						if(listScan != null && !listScan.isEmpty()) {
							listScan.forEach(scan -> {
								try {
									this.bundle = RemoveBundle.init(ELanguages.SPANISH.getCode());
									Scanner scanner = new Scanner();
									scanner = loadScannerInfo(scan.getUuid());
									ReportParams params = new ReportParams();
									params.setCleanFile(true);
									params.setCode("escaner_dashboard");
									params.setEmail(client.getEmail());
									params.setToEmail(true);
									params.setToNotification(false);
									
									//Parametros para reporte
									params.addParameter("email", client.getEmail());
									params.addParameter("uuid_scanner", scanner.getUuid());								
									params.addParameter("init_date", RemoveDateUtils.formatDateDash(scanner.getResults().get(0).getQuery_date().toLocalDate().minusMonths(1)));
									params.addParameter("end_date",RemoveDateUtils.formatDateDash(scanner.getResults().get(0).getQuery_date().toLocalDate()));
									
									List<Integer> page = new ArrayList<Integer>();									
									for(int i=0;i<scanner.getConfiguration().getPages();i++) {
										page.add(i+1);
									}							
									params.addParameter("pages", page);
									
									List<ScannerKeyword> kw = ScannerKeywordsDao.init().list(scanner.getId());
									List<String> key = new ArrayList<String>();
									for(int i=0;i<kw.size();i++) {
										key.add(kw.get(i).getWord());
									}	
									params.addParameter("keywords", key);
									
									String[] searchType = scanner.getConfiguration().getSearch_type().split(",");
									
									List<String> search_type = new ArrayList<String>();
									for(int i=0;i<searchType.length;i++) {
										search_type.add(searchType[i]);
									}
									
									params.addParameter("section", search_type);
									
									List<String> search_type_section = new ArrayList<String>();
									for(int i=0;i<searchType.length;i++) {
										search_type_section.add(bundle.getLabelBundle("scanner.section."+searchType[i]));
									}
									params.addParameter("sectionName",search_type_section);
									
									List<Long> idsCountries = new ArrayList<Long>();
									scanner.getResults().forEach(result -> {
										idsCountries.add(result.getCountry().getId());
									});
									
									List<Country> countries = CountryDao.init().list(idsCountries);
									List<String> country = new ArrayList<String>();
									for(int i=0;i<countries.size();i++) {
										country.add(countries.get(i).getName());
									}
									
									params.addParameter("countries", country.stream().distinct().collect(Collectors.toList()));

									
									EReportTypesMonthly reportType = EReportTypesMonthly.find(params.getCode());
									
									if (reportType == null || !reportType.isForAdmin()) {
										log.error("Error Executing tasks: ",EMessageBundleKeys.ERROR_INVALID_PARAMS);
									}
									
									params.addParameter("KEY_PARAM_FILTER_USER_UUID", "a7c12444-1518-45dc-ad56-81b2bd91a55f");
									params.addParameter("KEY_PARAM_FILTER_USER_PROFILE", EProfiles.ADMIN.getCode());
									
									reportType.getBuildFunction().apply(params);

								} catch (RemoveApplicationException e) {

								}								
							});
						}
						
					}catch (RemoveApplicationException | Exception e) {
						log.error("Error Executing tasks: ", e);
					}
				});
			}
		}catch (RemoveApplicationException | Exception e) {
			log.error("Error Executing tasks: ", e);
		}
	}
	
	private Scanner loadScannerInfo(String uuidScanner) throws RemoveApplicationException {
        Scanner scanner = ScannerDao.init().getBasicByUuid(uuidScanner);

        ScannerResultView view = ScannerResultViewDao.init().getByUuid(uuidScanner);

        if (view != null && view.getContent() != null) {
            scanner = (Scanner) RemoveJsonUtil.jsonToData(view.getContent(), Scanner.class);
            scanner.setImpulses(ScannerImpulseDao.init().listPublishedByScanner(scanner.getId()));
        } else { // Deprecado temporal mientras se migran todos los scanners al view
            scanner = ScannerDao.init().getJustConfigByUuid(uuidScanner, scanner.getType());

            if (scanner != null) {
                scanner.setResults(ScannerResultDao.init().list(scanner.getId()));
                scanner.setImpulses(ScannerImpulseDao.init().listPublishedByScanner(scanner.getId()));
            }
        }

        if (scanner != null) {
            scanner.clearImagesResults();
        }

        if (scanner.getTrackingWords().isEmpty()) {
            scanner.setTrackingWords(ScannerTrackingWordsDao.init().list(scanner.getId()));
            scanner.splitTrackingWords();
        }

        scanner.setRestrictions(ClientPlanDao.init().getById(scanner.getId_client_plan()));

        return scanner;
    }
}
