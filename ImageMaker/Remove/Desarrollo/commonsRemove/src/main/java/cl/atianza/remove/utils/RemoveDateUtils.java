package cl.atianza.remove.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Facilita operaciones para el manejo de Fechas en el Sistema
 * @author Jose Gutierrez
 *
 */
public class RemoveDateUtils {
	public static final String STRING_FORMAT_ID = "yyyyMMddHHmmss";
	
	public static final String STRING_FORMAT_DD_MM_YYYY_DASH = "dd-MM-yyyy";
	public static final String STRING_FORMAT_DD_MM_YYYY_HH_MM_SS_DASH = "dd-MM-yyyy HH:mm:ss";
	
	private static final String STRING_FORMAT_DD_MM_YYYY_SLASH = "dd/MM/yyyy";
	private static final String STRING_FORMAT_DD_MM_YYYY_HH_MM_SS_SLASH = "dd/MM/yyyy HH:mm:ss";
	
	private static final String STRING_FORMAT_HH_MM_SS = "HH:mm:ss";
	private static final String STRING_FORMAT_HH_MM = "HH:mm";
	
	public static final String STRING_FORMAT_MM_YYYY_SLASH = "MM/yyyy";
	
	private static final String STRING_FORMAT_POSTGRES = "yyyy-MM-dd";
	
	public static final String STRING_FORMAT_FOR_JSON_PARSE_DATE_TIME = "dd/MM/yyyy HH:mm:ss.SSS";
	public static final String STRING_FORMAT_FOR_JSON_PARSE_DATE = "dd/MM/yyyy";
	
	public static final String STRING_FORMAT_FOR_SERP = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String STRING_FORMAT_STANDARD_SLASH = "yyyy/MM/dd'T'HH:mm:ss.SSS'Z'";
	public static final String STRING_FORMAT_STANDARD_DASH = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	public static final String STRING_LOCALDATE_FORMAT_FOR_FRONTEND = "yyyy/MM/dd";
	public static final String STRING_LOCALDATETIME_FORMAT_FOR_FRONTEND = "yyyy/MM/dd HH:mm:ss.SSS";
	
	private static final DateTimeFormatter FORMATER_DD_MM_YYYY_SLASH = DateTimeFormatter.ofPattern(STRING_FORMAT_DD_MM_YYYY_SLASH);
	private static final DateTimeFormatter FORMATER_DD_MM_YYYY_DASH = DateTimeFormatter.ofPattern(STRING_FORMAT_DD_MM_YYYY_DASH);
	private static final DateTimeFormatter FORMATER_DD_MM_YYYY_HH_MM_SS_SLASH = DateTimeFormatter.ofPattern(STRING_FORMAT_DD_MM_YYYY_HH_MM_SS_SLASH);
	private static final DateTimeFormatter FORMATER_DD_MM_YYYY_HH_MM_SS_DASH = DateTimeFormatter.ofPattern(STRING_FORMAT_DD_MM_YYYY_HH_MM_SS_DASH);
	
	private static final DateTimeFormatter FORMATER_HH_MM_SS = DateTimeFormatter.ofPattern(STRING_FORMAT_HH_MM_SS);
	private static final DateTimeFormatter FORMATER_HH_MM = DateTimeFormatter.ofPattern(STRING_FORMAT_HH_MM);
	
	private static final DateTimeFormatter FORMATER_POSTGRES = DateTimeFormatter.ofPattern(STRING_FORMAT_POSTGRES);
	public static final DateTimeFormatter FORMATER_LOCALDATE_FRONTEND = DateTimeFormatter.ofPattern(STRING_LOCALDATE_FORMAT_FOR_FRONTEND);
	public static final DateTimeFormatter FORMATER_LOCALDATETIME_FRONTEND = DateTimeFormatter.ofPattern(STRING_LOCALDATETIME_FORMAT_FOR_FRONTEND);
	public static final SimpleDateFormat FORMAT_FOR_JSON_PARSE = new SimpleDateFormat(STRING_FORMAT_FOR_JSON_PARSE_DATE_TIME);
	
	public static final DateTimeFormatter FORMAT_ID = DateTimeFormatter.ofPattern(STRING_FORMAT_ID);
	
	/**
	 * Parsea una fecha en String formateada a una instancia LocalDate
	 * @param stringDate
	 * @return
	 */
	public static LocalDate parseLocalDate(String stringDate){
		try {
			return LocalDate.parse(stringDate, FORMATER_DD_MM_YYYY_DASH);
		} catch (Exception ex) {
			try {
				return LocalDate.parse(stringDate, FORMATER_DD_MM_YYYY_SLASH);
			} catch (Exception e) {/*Dont Match Format*/}
		}
		
		return null;
	}
	
	/**
	 * Parsea una fecha en String formateada a una instancia LocalDateTime
	 * @param stringDateTime
	 * @return
	 */
	public static LocalDateTime parseLocalDateTime(String stringDateTime){
		try {
			return LocalDateTime.parse(stringDateTime, FORMATER_DD_MM_YYYY_HH_MM_SS_DASH);
		} catch (Exception ex) {
			try {
				return LocalDateTime.parse(stringDateTime, FORMATER_DD_MM_YYYY_HH_MM_SS_SLASH);
			} catch (Exception e) {/* Parse Fallido */}
		}
		
		return null;
	}
	
	public static LocalDate parseLocalDateFrontend(String stringDate){
		try {
			return LocalDate.parse(stringDate, FORMATER_LOCALDATE_FRONTEND);
		} catch (Exception ex) {/* Parse Fallido */}
		
		return null;
	}
	public static LocalDateTime parseLocalDateTimeFrontend(String stringDate){
		try {
			return LocalDateTime.parse(stringDate, FORMATER_LOCALDATETIME_FRONTEND);
		} catch (Exception ex) {/* Parse Fallido */}
		
		return null;
	}
	
	public static String formatDateDash(LocalDateTime fecha) {
		if(fecha != null) {
			return FORMATER_DD_MM_YYYY_DASH.format(fecha);
		}
		
		return "";
	}
	public static String formatDateDash(LocalDate fecha) {
		if(fecha != null) {
			return FORMATER_DD_MM_YYYY_DASH.format(fecha);
		}
		
		return "";
	}
	public static String formatDateTimeDash(LocalDateTime fecha) {
		if(fecha != null) {
			return FORMATER_DD_MM_YYYY_HH_MM_SS_DASH.format(fecha);
		}
		
		return "";
	}
	
	/**
	 * Castea una instancia de sql.Timestamp a una instancia LocalDateTime
	 * @param fecha
	 * @return
	 */
	
	public static String formatPostgres(LocalDate fecha) {
		if(fecha != null) {
			return FORMATER_POSTGRES.format(fecha);
		}
		
		return "";
	}
	
	/**
	 * Obtiene la fecha del sistema en una instancia LocalDateTime
	 * @return
	 */
	public static LocalDateTime nowLocalDateTime() {
		return LocalDateTime.now();
	}
	
	public static String formatDateStringDash(LocalDateTime fecha) {
		if(fecha != null) {
			return FORMATER_DD_MM_YYYY_DASH.format(fecha);
		}
		
		return "";
	}
	
	public static String nowLocalDateString() {
		return FORMATER_DD_MM_YYYY_DASH.format(nowLocalDate());
	}
	public static String nowLocalDateTimeString() {
		return FORMATER_DD_MM_YYYY_HH_MM_SS_SLASH.format(nowLocalDateTime());
	}
	public static LocalDate nowLocalDate() {
		return LocalDate.now();
	}
	public static LocalTime nowLocalTime() {
		return LocalTime.now();
	}

	public static Long diffDaysFromToday(LocalDate date) {
		return diffDays(nowLocalDate(), date);
	}
	public static Long diffDays(LocalDate firstDate, LocalDate secondDate) {
		if (firstDate != null && secondDate != null) {
			return ChronoUnit.DAYS.between(firstDate, secondDate);
		}
		
		return null;
	}
	
	public static LocalDate weekStartingDay(LocalDate date) {
		if (date == null) {
			return null;
		}
		
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		if (dayOfWeek.compareTo(DayOfWeek.MONDAY) == 0) {
			return date;
		}
		
		return date.minusDays(dayOfWeek.getValue()-1);
	}
	public static LocalDate weekEndingDay(LocalDate date) {
		if (date == null) {
			return null;
		}
		
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		if (dayOfWeek.compareTo(DayOfWeek.SUNDAY) == 0) {
			return date;
		}
		return date.plusDays(7 - dayOfWeek.getValue());
	}
	
	public static String transformToPostgresFormat(String value) {
		if (value != null) {
			return formatPostgres(parseLocalDate(value));
		}
		
		return null;
	}
	public static String formatTimeHourMinutes(LocalTime time) {
		if (time != null) {
			return time.format(FORMATER_HH_MM_SS);
		}
		
		return null;
	}
	public static LocalTime parseTimeHourMinutes(String stringTime) {
		if (stringTime != null) {
			try {
				return LocalTime.parse(stringTime, FORMATER_HH_MM_SS);	
			} catch (Exception e) {
				return LocalTime.parse(stringTime, FORMATER_HH_MM);	
			}
		}
		
		return null;
	}
	
	public static LocalDate firstDayMonth() {
		return firstDayMonth(LocalDate.now());
	}
	public static LocalDate firstDayMonth(LocalDate referenceDate) {
	    return referenceDate.withDayOfMonth(1);
	}
	public static LocalDate lastDayMonth() {
		return lastDayMonth(LocalDate.now());
	}
	public static LocalDate lastDayMonth(LocalDate referenceDate) {
	    return referenceDate.withDayOfMonth(referenceDate.lengthOfMonth());
	}
	
	public static LocalDate firstDayWeek() {
		return firstDayWeek(LocalDate.now());
	}
	public static LocalDate firstDayWeek(LocalDate referenceDate) {
		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
		
		return referenceDate.with(fieldUS, 1).atStartOfDay(ZoneId.systemDefault()).toLocalDate();
	}
	public static LocalDate lastDayWeek() {
		return lastDayWeek(LocalDate.now());
	}
	public static LocalDate lastDayWeek(LocalDate referenceDate) {
		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
		
		return referenceDate.with(fieldUS, 7).atStartOfDay(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static String addLocalDateDay(String value, int daysToAdd) {
		return formatDateDash(parseLocalDate(value).plusDays(daysToAdd));
	}
	
	public static LocalDate minusYearsFromNow(Integer age) {
		return nowLocalDate().minusYears(age);
	}
	public static String minusYearsFromNowFormated(Integer age) {
		return formatDateDash(minusYearsFromNow(age));
	}
	public static String minusYearsFromNowPostgresFormated(Integer age) {
		return formatPostgres(minusYearsFromNow(age));
	}
	public static String formatLocalDatePosgresForced(String stringDate) {
		if (stringDate == null) {
			return null;
		}
		
		LocalDate date = null;
		
		date = tryLocalDateParse(stringDate, FORMATER_DD_MM_YYYY_DASH);
		if (date == null) {
			date = tryLocalDateParse(stringDate, FORMATER_DD_MM_YYYY_SLASH);
			
			if (date == null) {
				date = tryLocalDateParse(stringDate, FORMATER_POSTGRES);
			}
		}
		
		return date != null ? formatPostgres(date) : null;
	}
	
	private static LocalDate tryLocalDateParse(String stringDate, DateTimeFormatter formatter) {
		LocalDate date = null;
		
		try {
			date = LocalDate.parse(stringDate, formatter);
		} catch (Exception e) {}
				
		return date;
	}
	public static LocalDateTime setLocalTimeToLocalDateTime(LocalDateTime dateTime, LocalTime time) {
		if (dateTime != null && time != null) {
			return LocalDateTime.of(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), time.getHour(), time.getMinute(), time.getSecond());
		}

		return dateTime;
	}
	
	public static Date parseLocalDateTimeToUtilsDate(LocalDateTime localDateTime) {
		return java.util.Date
			      .from(localDateTime.atZone(ZoneId.systemDefault())
			    	      .toInstant());
	}
	
	/**
	 * Look for the next date of day of week
	 * @return
	 */
	public static LocalDateTime nextDayOfWeek(DayOfWeek dayToFind) {
		return nextDayOfWeek(dayToFind, nowLocalDateTime());
	}
	/**
	 * Look for the next date of day of week
	 * @return
	 */
	public static LocalDateTime nextDayOfWeek(DayOfWeek dayToFind, LocalDateTime reference) {
		return reference.getDayOfWeek().equals(dayToFind) ? reference : nextDayOfWeek(dayToFind, reference.plusDays(1));
	}
	
	/**
	 * Return a list of a specific date of every month between dates interval
	 * 
	 * @param init
	 * @param end
	 * @param dateNumber
	 * @return
	 */
	public static List<LocalDateTime> buildListDates(LocalDateTime init, LocalDateTime end, int dateNumber) {
		List<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		
		if (init != null && end != null && init.isBefore(end) && dateNumber > 0) {
			boolean searching = true;
			
			LocalDateTime reference = init;
			
			if (reference.getDayOfMonth() > dateNumber) {
				reference = reference.plusMonths(1);
			}

			reference = reference.withDayOfMonth(reference.getMonth().maxLength() > dateNumber ? dateNumber : reference.getMonth().maxLength());

			while (searching) {
				if (reference.isBefore(end)) {
					dates.add(reference);
					reference = reference.plusMonths(1);
				} else {
					searching = false;
				}
			}
		}
		
		return dates;
	}
	
	public static List<LocalDateTime> buildListDatesFirstAtMonth(LocalDateTime init, LocalDateTime end) {
		List<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		
		if (init != null && end != null && init.isBefore(end)) {
			boolean searching = true;
			
			LocalDateTime reference = init;
			
			if (reference.getDayOfMonth() > 1) {
				reference = reference.plusMonths(1).withDayOfMonth(1);
			}

			while (searching) {
				if (reference.isBefore(end)) {
					dates.add(reference);
					reference = reference.plusMonths(1);
				} else {
					searching = false;
				}
			}
		}
		
		return dates;
	}
	
	public static List<LocalDateTime> buildListDatesLastAtMonth(LocalDateTime init, LocalDateTime end) {
		List<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		
		if (init != null && end != null && init.isBefore(end)) {
			boolean searching = true;
			
			LocalDateTime reference = init.withDayOfMonth(init.getMonth().maxLength());
			
			while (searching) {
				if (reference.isBefore(end)) {
					dates.add(reference);
					reference = reference.plusMonths(1);
				} else {
					searching = false;
				}
			}
		}
		
		return dates;
	}
	public static LocalDateTime parseFromSerp(String date) {
		try {
			if (date != null) {
				return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(STRING_FORMAT_FOR_SERP));
			}
		} catch (Exception e) {System.out.println("ERROR: Parsing SERP DATE: " + date + " From Format: " + STRING_FORMAT_FOR_SERP + " : " + e.getMessage());}
		
		return null;
	}
	public static LocalDateTime parseStandard(String date) {
		try {
			if (date != null) {
				return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(STRING_FORMAT_STANDARD_SLASH));
			}
		} catch (Exception e) {
			try {
				return LocalDateTime.parse(date, DateTimeFormatter.ISO_INSTANT);	
			} catch (Exception ex) {}
		}
		
		return null;
	}
	public static LocalDate parseMonthYear(String date) {
		try {
			if (date != null) {
				return LocalDate.parse(date, DateTimeFormatter.ofPattern(STRING_FORMAT_MM_YYYY_SLASH));
			}
		} catch (Exception e) {}
		
		return null;
	}

	public static boolean isInRangeImplicit(LocalDateTime date, LocalDateTime from, LocalDateTime to) {
		return date.isEqual(from) || date.isEqual(to) || (date.isAfter(from) && date.isBefore(to));
	}

	public static LocalDateTime parse(Long seconds) {
		if (seconds != null) {
			return new Timestamp(seconds.longValue() * 1000).toLocalDateTime();
		}
		
		return null;
	}
	public static Long localDateTimeToSeconds(LocalDateTime date) {
		if (date != null) {
			return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000;
		}
		
		return null;
	}
	public static Long localDateToSeconds(LocalDate date) {
		return localDateTimeToSeconds(date.atStartOfDay());
	}

	public static LocalDate randomNextDays(int maxNextDays) {
		return LocalDate.now().plusDays(RemoveUtils.generateRandomNumber(1, maxNextDays));
	}

	public static String nowIdFormat() {
		return FORMAT_ID.format(nowLocalDateTime());
	}
}
