package cl.atianza.remove.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class RemoveUtils {
	public static final String STRING = "String";
	public static final String STRING_SIMPLE = "StringSimple";
	public static final String DATE = "Date";
	public static final String NUMBER = "Number";
	public static final String SELECT = "Select";
	
	/**
	 * Compare 2 strings ignoring uppercase, trims and accents
	 * @param string1
	 * @param string2
	 * @return
	 */
	public static boolean equalsStrings(String string1, String string2) {
		return StringUtils.stripAccents(string1).trim().equalsIgnoreCase(StringUtils.stripAccents(string2).trim());
	}
	
	public static String removeAccents(String string) {
		return StringUtils.stripAccents(string);
	}

	public static String clearPhone(String _phone) {
		if (_phone != null) {
			return _phone.trim().replace("+", "").replace("-", "").replace(" ", "");
		}
		
		return null;
	}

	public static boolean isEmptyOrNull(String string) {
		return string == null || string.trim().isEmpty();
	}

	public static boolean validateEmail(String email) {
		return EmailValidator.getInstance().isValid(email);
	}
	
	public static long generateRandomNumber(long min, long max) {
		return (long) ((Math.random() * (max - min)) + min);
	}
}
