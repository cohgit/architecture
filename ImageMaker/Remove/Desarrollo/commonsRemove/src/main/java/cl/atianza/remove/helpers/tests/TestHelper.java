package cl.atianza.remove.helpers.tests;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cl.atianza.remove.datamanager.ConnectionDB;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class TestHelper {	
	public static void main(String[] args) {
		try {
//			new TestDeindexationFormCreator().createVersionOne();
//			new TestDummyForm().createDummyVersion();
//
//			RemoveMail mail = RemoveMail.initForContact("contact_admin.html", ELanguages.SPANISH.getCode());
//			
//			mail.setTo(ConfigurationDao.init().getContactEmail());
//			mail.setLanguage(ELanguages.SPANISH.getCode());
//			mail.addToken("{{TITLE_FORM}}", "Hola Bonifacio 2");
//			mail.addToken("{{CONTACT_FORM}}", "");
//			
//			RemoveEmailClient.init(ConfigurationDao.init().getEmailParams()).send(mail);
			new TestHelper().test();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void test() throws RemoveApplicationException {
		try {
	        URL resource = TestHelper.class.getResource("/jreports/HyperlinkReport.jasper");
	        File file = Paths.get(resource.toURI()).toFile();
	        String sourceFileName = file.getAbsolutePath();
	        String targetFile = "D:\\temp-atianza\\sample_" + UUID.randomUUID().toString() +".pdf";

	        Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("ReportTitle", "HOLA!");
	        
	        JasperPrint print = JasperFillManager.fillReport(sourceFileName, parameters);
	        System.out.println("Reporte finalizado...");
	        JasperExportManager.exportReportToPdfFile(print, targetFile);
	        System.out.println("Reporte exportado...");
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
	        
	        
		} catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
