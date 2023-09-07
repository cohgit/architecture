package cl.atianza.remove.tasks.scanner;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.daos.commons.ScannerResultDao;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ScannerHelper;
import cl.atianza.remove.models.commons.Scanner;
import cl.atianza.remove.models.commons.ScannerResult;
import cl.atianza.remove.utils.AlertsUtils;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Check every scanner to be executed and schedule it.
 * @author pilin
 *
 */
public class ScannerTaskManager extends TimerTask {
	private Logger log = LogManager.getLogger(ScannerTaskManager.class);
	private boolean sequencial = true;
	AlertsUtils alert = new AlertsUtils();
	
	
	public ScannerTaskManager() {
		super();
	}

	@Override
	public void run() {
		log.info("Executing new Img ");
		log.info("Starting Scanner Task Manager at:" + RemoveDateUtils.nowLocalDateTime() + " - is sequencial: "+ this.sequencial);
		List<Scanner> lstScanners = null;
		try {
			lstScanners = ScannerDao.init().listRecurrents();
		
			if (lstScanners != null && !lstScanners.isEmpty()) {
				log.info("Total Scanner founds to execute: " + lstScanners.size());
				
				if (sequencial) {
					lstScanners.forEach(scan -> {
						try {
							Thread.sleep(5*60*1000);		// Sleep 5 minutes between scanners
							log.info("Initializing Subsecuent Scanner: " + scan.getUuid());
							
							ScannerHelper.externalCallForRecurrentTasks(scan.getUuid());
							log.info("Finishing Subsecuent Scanner: " + scan.getUuid());
						} catch (RemoveApplicationException | Exception e) {
							log.error("Error starting scanner task: " + scan.getUuid(), e);
						}
					});
				} else {
					Timer timer = new Timer("ScannerTaskManagerTimer");
					
					lstScanners.forEach(scan -> {
						log.info("Scheduling Scanner: " + scan.getUuid());
				    	timer.schedule(new ScannerRecurrentsTask(scan.getUuid()), getInitialTime(scan.getUuid()));	
					});
				}
			} else {
				log.info("There's no scanner to run...");
			}
		} catch (RemoveApplicationException | Exception e) {
			log.error("Error Executing tasks: ", e);
		}
		log.info("Scanner Task Manager Finished all schedules at: " + RemoveDateUtils.nowLocalDateTime());
		log.info("Finished new Img ");
		
		try {
			log.info("Finalizando ejecucion de Scanner y descanso de 10 min");
			Thread.sleep(10*60*1000);
			log.info("Iniciando alertas");
			alert.alertInitialize();
			log.info("Finalizando alertas");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	private Date getInitialTime(String uuid) {
		LocalDateTime start = RemoveDateUtils.nowLocalDateTime().plusMinutes(1);
		
		log.info("Scanner Task (" + uuid + ") Scheduled at: " + start);
		return RemoveDateUtils.parseLocalDateTimeToUtilsDate(start);
	}
}
