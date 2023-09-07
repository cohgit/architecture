package cl.atianza.remove.helpers.reports;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EReportTypes;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.ReportParams;
import cl.atianza.remove.utils.RemoveBundle;
import cl.atianza.remove.utils.RemoveResponseUtil;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public abstract class AbstractReportGeneratorMontlhy {
	protected RemoveBundle bundle;
	protected String uuid = UUID.randomUUID().toString();
	protected Logger log;
	private String jasperTemplate;
	private ReportParams parameters;
	private Map<String, Object> jrParameters = new HashMap<String, Object>();
	private boolean async;
	
	public AbstractReportGeneratorMontlhy(Logger log, String jasperTemplate, ReportParams parameters) {
		super();
		this.log = log;
		this.jasperTemplate = jasperTemplate;
		this.parameters = parameters;
	}
	public AbstractReportGeneratorMontlhy(Logger log) {
		super();
		this.log = log;
	}

	/**
	 * Starting method, defines if the report will be generated sync or async.
	 * @return
	 */
	public String send() {
		getLog().info(uuid + ": Sending process... Async: " + async);
		try {
			organizeParams();
			
			if (validateParams()) {
				if (async) {
					new Thread(() -> this.process()).start();
				} else {
					this.process();
				}
			} else {
				log.error("Invalid Parameters...");
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
			}
		} catch (RemoveApplicationException e) {
			this.log.error("Error building report: ", e);
			return RemoveResponseUtil.buildError(e);
		} catch (Throwable e) {
			this.log.error("Error building report: ", e);
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INTERNAL_SERVER);
		}
		
		return RemoveResponseUtil.buildDefaultOk();
	}
	
	/**
	 * Main method, trigger all report generation configured in the child class
	 * @return
	 */
	protected Object process() {
		getLog().info(uuid + ": Processing report... ");
		this.fillDataParameters();
		
    	return this.build();
	}
	
	/**
	 * Compile and generate pdf report file from a .jasper file.
	 * @return
	 */
	protected String generatePdfFile() {
		try {
			getLog().info(uuid + ": Generating Pdf Report by template: " + this.getJasperTemplate());
			String jReportPath = "jreports/" + this.getJasperTemplate() + ".jasper";
			getLog().info(uuid + ": jReportPath: " + jReportPath);
			
			InputStream inputStream = this.getClass()
			  .getClassLoader()
			  .getResourceAsStream(jReportPath);
			
			getLog().info(uuid + ": InputStream Found: " + inputStream);
			  
	        //String targetFile = this.getJasperTemplate() + "_" + UUID.randomUUID().toString() +".pdf";
			String targetFile = EReportTypes.SCANNER_DASHBOARD.getCode() + "_" + UUID.randomUUID().toString() +".pdf";
	        
	        JasperPrint print = JasperFillManager.fillReport(inputStream, this.getJrParameters(), new JREmptyDataSource());
	        log.info(uuid + ":Report finished...");
	        JasperExportManager.exportReportToPdfFile(print, targetFile);
	        log.info(uuid + ":Report exported...");
	        
	        // Example of pdf security in case of needed.
//	        JRPdfExporter exporter = new JRPdfExporter();
//	    	
//	    	exporter.setExporterInput(new SimpleExporterInput(print));
//	    	exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));
//	    	SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
//	    	configuration.setEncrypted(true);
//	    	configuration.set128BitKey(true);
//	    	configuration.setUserPassword("jasper");
//	    	configuration.setOwnerPassword("reports");
//	    	configuration.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);
//	    	exporter.setConfiguration(configuration);
//	    	exporter.exportReport();
	        
	        return targetFile;
		} catch (Exception e) {
			log.error(uuid + ":Error building report: ", e);
	    }
		
		return null;
	}
		
	/**
	 * Delete pdf file after process
	 * @param pathFile
	 */
	protected void cleanPdfFile(String pathFile) {
		try  {         
			getLog().info(uuid + ": Cleaning File Process: (" + this.getParameters().isCleanFile() + ") - " + pathFile);
			if (pathFile != null && this.getParameters().isCleanFile()) {
				File f= new File(pathFile);           //file to be delete  
				if(f != null && f.delete()) {  
					log.info(f.getName() + " deleted");   //getting and printing the file name  
				} else {
					log.warn(uuid + " File not deleted: '" + pathFile + "' (Can't delete or not found)");
				}
			}
			getLog().info(uuid + ": Cleaning File Finished...");
		} catch(Exception e) {  
			log.error("Error deleting file: " + pathFile, e);
		}  
	}
	
	/**
	 * Generate report, process it and then delete it.
	 * @return
	 */
	protected Object build() {
		log.info(uuid + ": Starting Build Process: " + this.getParameters());
		try {
			String filePath = generatePdfFile();
			
			if (filePath != null) {
				if (this.parameters.isToEmail()) {
					this.sendNotificationsMontlhy(filePath);	
				}
				if (this.parameters.isToNotification()) {
					// TODO: implement send to notifications.
				}
				
				cleanPdfFile(filePath);
			} else {
				log.error(uuid + ": Error generating report: " + filePath);
			}
		} catch (Exception e) {
			getLog().error("Error generating report: ", e);
		}
		return null;
	}
	
	/**
	 * Override this method with report parameter validation before the fill data
	 * @return true if parameters are valid, otherwise false
	 */
	protected abstract boolean validateParams() throws RemoveApplicationException;
	
	/**
	 * 
	 * @return
	 */
	protected abstract void organizeParams();
	/**
	 * Override this method with logic and fill JasperReport parameters in jrParameter attributes
	 */
	protected abstract void fillDataParameters();
	/**
	 * Override this method with logic of report notifications (email or other)
	 * @param fileReportPath
	 */
	protected abstract void sendNotificationsMontlhy(String fileReportPath);
	
	
	protected Logger getLog() {
		return log;
	}
	public ReportParams getParameters() {
		return parameters;
	}
	public void setParameters(ReportParams parameters) {
		this.parameters = parameters;
	}
	public boolean isAsync() {
		return async;
	}
	public void setAsync(boolean async) {
		this.async = async;
	}
	public String getJasperTemplate() {
		return jasperTemplate;
	}
	public void setJasperTemplate(String jasperTemplate) {
		this.jasperTemplate = jasperTemplate;
	}
	protected Map<String, Object> getJrParameters() {
		if (jrParameters == null) {
			jrParameters = new HashMap<String, Object>();
		}
		
		return jrParameters;
	}
	protected void setJrParameters(Map<String, Object> jrParameters) {
		log.info(uuid + ": Adding to JR Parameters: " + jrParameters  + " | size: " + (jrParameters != null ? jrParameters.size() : "null"));
		this.jrParameters = jrParameters;
	}
	protected void addJrParameter(String key, Object value) {
		log.info(uuid + ": Adding to JR Parameter: " + key + " = " + value);
		this.getJrParameters().put(key, value);
	}
	@SuppressWarnings("unchecked")
	protected void addJrParameterCollection(String key, List list) {
		try {
			Collections.sort(list);
		} catch (Throwable e) {log.warn("List is not Sortable: " + key + " : " + list, e.getMessage());}
		
		log.info(uuid + ": Adding to JR DataSource: " + key + " = " + list + " | size: " + (list != null ? list.size() : "null"));
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(list);
		this.addJrParameter(key, itemsJRBean);
	}
}
