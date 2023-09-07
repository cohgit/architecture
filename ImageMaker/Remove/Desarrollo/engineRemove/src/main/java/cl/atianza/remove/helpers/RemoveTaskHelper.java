package cl.atianza.remove.helpers;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.alert.AlertManagerWeekly;
import cl.atianza.remove.tasks.report.ReportManagerFifteen;
import cl.atianza.remove.tasks.report.ReportManagerOne;
import cl.atianza.remove.tasks.scanner.ScannerTaskManager;
import cl.atianza.remove.tasks.suscriptions.ChangeSuscriptionsTask;
import cl.atianza.remove.tasks.suscriptions.RenovationSuscriptionsTask;
import cl.atianza.remove.utils.AlertsUtils;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * 
 * @author Jose Gutierrez
 *
 */
public class RemoveTaskHelper {
	private static Logger log = LogManager.getLogger(RemoveTaskHelper.class);
	
	/**
	 * 
	 */
	public static void initialize() {
		log.info("Initializing Scanner Task Helper. " + RemoveDateUtils.nowLocalDateTime());
		
		try {
			Timer timerScanner = new Timer("RemoveScannerTimer");
			Timer timerSubs = new Timer("RemoveSuscriptionsTimer");
			Timer timerRenew = new Timer("RemoveRenovationTimer");
			Timer timerReportOne = new Timer("RemoveReportOne");
			Timer timerReportFifteen = new Timer("RemoveReportFifteen");
			Timer timerAlertWeekly = new Timer("RemoveAlertWeekly");
			
			timerScanner.schedule(new ScannerTaskManager(), getInitialTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
			timerSubs.schedule(new RenovationSuscriptionsTask(), getInitialTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
			timerRenew.schedule(new ChangeSuscriptionsTask(), getInitialTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
			timerReportOne.schedule(new ReportManagerOne(), getInitialTimeReportOne());
			timerReportFifteen.schedule(new ReportManagerFifteen(), getInitialTimeReportFifteen());			
			timerAlertWeekly.schedule(new AlertManagerWeekly(), getInitialTimeAlertWeekly());
			
		    log.info("Scanner Task Manager scheduled successfully... " + RemoveDateUtils.nowLocalDateTime());
		} catch (Exception ex) {
			log.error("Error starting task manager job: ", ex);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private static Date getInitialTime() {
		LocalDateTime start = RemoveDateUtils.nowLocalDateTime().plusDays(1).withHour(0).withMinute(5).withSecond(0);
		//LocalDateTime start = RemoveDateUtils.nowLocalDateTime().plusDays(0).withHour(23).withMinute(22).withSecond(0);
//		LocalDateTime start = RemoveDateUtils.nowLocalDateTime().plusMinutes(1);
		
		log.info("Engine programed to: " + start);
		
		return RemoveDateUtils.parseLocalDateTimeToUtilsDate(start);
	}
	
	/**
	 * 
	 * @return
	 */
	private static Date getInitialTimeReportOne() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss.zzz");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.zzz");
		Calendar calendar = Calendar.getInstance(); 
		String hoy = sdf.format(calendar.getTime());
		
		calendar.set(Calendar.DAY_OF_MONTH,1);
		String primerDiaMesActual = sdf.format(calendar.getTime());
		//Se le agrega 1 mes 
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
		String primerDiaMesSiguiente = sdf.format(calendar.getTime());
		
		LocalDateTime startOne = LocalDateTime.parse(primerDiaMesSiguiente, formatter);
		
		LocalDateTime start = startOne.minusDays(1).withHour(8).withMinute(0).withSecond(0);
		
		log.info("Engine programed report one to: " + start);
		Date date = RemoveDateUtils.parseLocalDateTimeToUtilsDate(start);
		return date;
	}
	
	private static Date getInitialTimeReportFifteen() {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss.zzz");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.zzz");
		Calendar calendar = Calendar.getInstance(); 
		String hoy = sdf.format(calendar.getTime());
		LocalDateTime hoyFormateado = LocalDateTime.parse(hoy, formatter);
		
		calendar.set(Calendar.DAY_OF_MONTH,1);
		String primerDiaMesActual = sdf.format(calendar.getTime());
		
		//calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
		
		LocalDateTime startOne = LocalDateTime.parse(primerDiaMesActual, formatter);
		
		LocalDateTime start = startOne.plusDays(13).withHour(8).withMinute(0).withSecond(0);
		
		if(start.isAfter(hoyFormateado)) {
			date = RemoveDateUtils.parseLocalDateTimeToUtilsDate(start);
		}else {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
			primerDiaMesActual = sdf.format(calendar.getTime());
			startOne = LocalDateTime.parse(primerDiaMesActual, formatter);
			
			start = startOne.plusDays(13).withHour(8).withMinute(0).withSecond(0);
			date = RemoveDateUtils.parseLocalDateTimeToUtilsDate(start);
		}
		
		log.info("Engine programed report fifteen to: " + start);
		
		
		return date;
	}
	
	private static Date getInitialTimeAlertWeekly() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss.zzz");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.zzz");
		Calendar calendar = Calendar.getInstance(); 

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String lunes = sdf.format(calendar.getTime());
		
		LocalDateTime startOne = LocalDateTime.parse(lunes, formatter);
		
		LocalDateTime start = startOne.plusDays(7).withHour(8).withMinute(30).withSecond(0);
		
		log.info("Engine programed alert weekly to: " + start);
		
		return RemoveDateUtils.parseLocalDateTimeToUtilsDate(start);
	}
}
