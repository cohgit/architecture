package cl.atianza.remove.enums;

import java.util.function.Function;

import cl.atianza.remove.helpers.reports.ReportScannerGenerator;
import cl.atianza.remove.helpers.reports.ReportScannerGeneratorMonthly;
import cl.atianza.remove.models.ReportParams;
import cl.atianza.remove.utils.RemoveResponseUtil;

public enum EReportTypesMonthly {
	SCANNER_DASHBOARD("escaner_dashboard", 
			"scanner_dashboard",
			(parameters) -> {
				try {
					return ReportScannerGeneratorMonthly.init("scanner_dashboard", parameters).send();
				} catch (Exception e) {
					return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INTERNAL_SERVER);
				}
			});
	
	private String code;
	private String pathTemplate;
	private boolean forAdmin = true;
	private boolean forClient = true;
	private Function<ReportParams, String> buildFunction;

	
	private EReportTypesMonthly(String code, String pathTemplate, Function<ReportParams, String> buildFunction) {
		this.code = code;
		this.pathTemplate = pathTemplate;
		this.buildFunction = buildFunction;
	}

	private EReportTypesMonthly(String code, boolean forAdmin, boolean forClient, Function<ReportParams, String> generate) {
		this.code = code;
		this.forAdmin = forAdmin;
		this.forClient = forClient;
		this.buildFunction = generate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPathTemplate() {
		return pathTemplate;
	}

	public void setPathTemplate(String pathTemplate) {
		this.pathTemplate = pathTemplate;
	}

	public boolean isForAdmin() {
		return forAdmin;
	}

	public void setForAdmin(boolean forAdmin) {
		this.forAdmin = forAdmin;
	}

	public boolean isForClient() {
		return forClient;
	}

	public void setForClient(boolean forClient) {
		this.forClient = forClient;
	}

	public Function<ReportParams, String> getBuildFunction() {
		return buildFunction;
	}

	public void setBuildFunction(Function<ReportParams, String> buildFunction) {
		this.buildFunction = buildFunction;
	}

	public static EReportTypesMonthly find(String code) {
		for (EReportTypesMonthly type: EReportTypesMonthly.values()) {
			if (type.getCode().equalsIgnoreCase(code)) {
				return type;
			}
		}
		
		return null;
	}
}
