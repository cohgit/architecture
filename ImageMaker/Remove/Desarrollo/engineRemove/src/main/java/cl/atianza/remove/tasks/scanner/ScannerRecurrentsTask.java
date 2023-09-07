package cl.atianza.remove.tasks.scanner;

import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.ScannerHelper;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Execute a scanner
 * @author pilin
 *
 */
public class ScannerRecurrentsTask extends TimerTask {
	private Logger log = LogManager.getLogger(ScannerRecurrentsTask.class);
	private String scanner_uuid;
	
	public ScannerRecurrentsTask(String scanner_uuid) {
		super();
		this.scanner_uuid = scanner_uuid;
	}

	@Override
	public void run() {
		this.executeTask();
	}

	public void executeTask() {
		log.info("Running Scanner Task (scanner: " + this.scanner_uuid + ") at: "+RemoveDateUtils.nowLocalDateTime());
		
		try {
			ScannerHelper.externalCallForRecurrentTasks(this.scanner_uuid);
		} catch (RemoveApplicationException | Exception e) {
			log.error("Error starting scanner task: ", e);
		}
		log.info("Scanner Task (scanner: " + this.scanner_uuid + ") is Ready to start at: "+RemoveDateUtils.nowLocalDateTime());
	}
}
