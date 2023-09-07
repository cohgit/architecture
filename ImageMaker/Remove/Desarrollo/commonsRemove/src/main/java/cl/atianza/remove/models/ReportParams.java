package cl.atianza.remove.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.utils.RemoveDateUtils;

public class ReportParams {
	protected Logger log = LogManager.getLogger(ReportParams.class);
	
	private String code;
	private String email;
	private boolean toEmail;
	private boolean toNotification;
	private boolean cleanFile = true;
	
	private Map<String, Object> parameters;
	
	public ReportParams() {
		super();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isToEmail() {
		return toEmail;
	}
	public void setToEmail(boolean toEmail) {
		this.toEmail = toEmail;
	}
	public boolean isToNotification() {
		return toNotification;
	}
	public void setToNotification(boolean toNotification) {
		this.toNotification = toNotification;
	}
	
	public boolean isCleanFile() {
		return cleanFile;
	}
	public void setCleanFile(boolean cleanFile) {
		this.cleanFile = cleanFile;
	}
	public Map<String, Object> getParameters() {
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}
		
		return parameters;
	}
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	public void addParameter(String key, Object value) {
		this.getParameters().put(key, value);
	}
	
	public String extractParamAsString(String key) {
		Object value = this.extractParam(key);
		return value != null ? String.valueOf(value) : "";
	}
	public String[] extractParamAsStringArray(String key) {
		Object value = this.extractParam(key);
		if (value != null) {
			@SuppressWarnings("unchecked")
			ArrayList<String> list = (ArrayList<String>) value;
			return list.toArray(new String[list.size()]);
		}
		
		return new String[0];
	}
	public String extractParamStringArrayAsString(String key) {
		return extractParamStringArrayAsString(key, ", ");
	}
	public String extractParamStringArrayAsString(String key, String delimiter) {
		String[] values = extractParamAsStringArray(key);
		
		return String.join(delimiter, values);
	}
	
	public Integer extractParamAsInt(String key) {
		String value = this.extractParamAsString(key);
		
		try {
			return value.trim().equals("") ? null : Integer.valueOf(value);	
		} catch (Exception e) {
			return null;
		}
	}
	public int[] extractParamAsIntArray(String key) {
		Object value = this.extractParam(key);
		if (value != null) {
			@SuppressWarnings("unchecked")
			ArrayList<Integer> list = (ArrayList<Integer>) value;
			return list.stream().mapToInt(Integer::intValue).toArray();
		}
		
		return new int[0];
	}
	public String extractParamIntArrayAsString(String key) {
		int[] values = extractParamAsIntArray(key);
		String[] strArray = Arrays.stream(values)
                .mapToObj(String::valueOf)
                .toArray(String[]::new);
		
		return String.join(", ", strArray);
	}
	
	public LocalDate extractParamAsDate(String key) {
		String value = this.extractParamAsString(key);
		
		try {
			LocalDate date = RemoveDateUtils.parseLocalDate(value);
			if (date != null) return date;
			
			LocalDateTime dateTime = RemoveDateUtils.parseLocalDateTime(value);
			if (dateTime != null) return dateTime.toLocalDate();
			
			dateTime = RemoveDateUtils.parseStandard(value);
			
			return dateTime != null ? dateTime.toLocalDate() : null;	
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean extractParamAsBoolean(String key) {
		String value = this.extractParamAsString(key);
		
		return value.trim().equalsIgnoreCase("true");	
	}
	
	private Object extractParam(String key) {
		return this.getParameters().get(key);
	}
	
	public boolean validateParams(String... keys) {
		boolean isValid = true;
		
		if (keys != null) {
			for (String key: keys) {
				if (extractParam(key) == null) {
					log.error("Required Param Not Found::: " + key);
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	public void overrideParam(String oldKey, String newKey) {
		if (this.parameters.get(oldKey) != null) {
			log.info("Overriding Param: " + oldKey + ": " + this.parameters.get(oldKey) + " --> " + newKey);
			this.parameters.put(newKey, this.parameters.get(oldKey));
			this.parameters.remove(oldKey);	
		}
	}
	
	@Override
	public String toString() {
		return "AbstractReportParams [code=" + code + ", email=" + email + ", toEmail=" + toEmail + ", toNotification="
				+ toNotification + "]";
	}
	public boolean isNull(String key) {
		return this.getParameters().get(key) == null;
	}
	
}
