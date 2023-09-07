package cl.atianza.remove.datamanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.commons.SchemaDao;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.commons.SchemaExecuted;
import cl.atianza.remove.properties.RemoveDBConnProps;
import cl.atianza.remove.properties.RemoveCommonsProperties;

/**
 * Class dedicated for read database script creation and execution.
 * @author pilin
 *
 */
public class SchemaBuilder {
	private Logger log = LogManager.getLogger(SchemaBuilder.class);
	
	private final RemoveDBConnProps props = RemoveCommonsProperties.init().getProperties().getConnection();
	
	private final String CHARSET_NAME = "UTF8";
	private final String SQL_EXTENTION = ".sql";
	
	public static SchemaBuilder init() {
		return new SchemaBuilder();
	}
 	
	/**
	 * 
	 * @return
	 */
	public String buildSchemaQueryDB() {
		return buildSchemaQuery(props.getInit_script_path(), props.getSchemaName(), props.getScript_token());
	}
	/**
	 * 
	 * @param scriptPath
	 * @param schema_code
	 * @param replaceToken
	 * @return
	 */
	private String buildSchemaQuery(String scriptPath, String schema_code, String replaceToken) {
		try {
			InputStream inputStream = this.getClass()
					  .getClassLoader()
					  .getResourceAsStream(scriptPath);
			InputStreamReader isReader = new InputStreamReader(inputStream, CHARSET_NAME);
			BufferedReader reader = new BufferedReader(isReader);
			StringBuffer sb = new StringBuffer();
			String str;
		
			while((str = reader.readLine())!= null){
				if(!str.trim().startsWith("--")) sb.append(str);
			}
			
			return sb.toString().replace(replaceToken, schema_code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 
	 * @return
	 * @throws RemoveApplicationException
	 */
	public List<SchemaExecuted> buildPendingSchemaUpdatesGU() throws RemoveApplicationException {
		List<SchemaExecuted> pendingList = new ArrayList<SchemaExecuted>();
		
		try {
			Files.newDirectoryStream(Paths.get(ClassLoader.getSystemResource(props.getUpdate_folder()).toURI()), 
					path -> path.toString().endsWith(SQL_EXTENTION))
		        .forEach(path -> {
		        	boolean stop = false;
		        	
		        	try {
		        		if (!stop) {
		        			SchemaExecuted executed = SchemaDao.init().getSuccessByNameAndSchema(path.getFileName().toString(), props.getSchemaName());
		        			
		        			if (executed == null) {
		        				pendingList.add(buildSchemaFromScript(path, props.getSchemaName(), props.getScript_token(), 
		        						props.getUpdate_folder()));
		        			}
		        		}
					} catch (RemoveApplicationException e) {
						log.error("Schematool stopped: Can't check file: " + path);
						stop = true;
					}
	        });
		} catch (IOException | URISyntaxException e) {
			log.error("Error running schema tool: ", e);
			RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_INTERNAL_SERVER);
		}
		
		return pendingList;
	}
	/**
	 * 
	 * @param path
	 * @param schema_code
	 * @param replaceToken
	 * @param relativePathFolder
	 * @return
	 */
	private SchemaExecuted buildSchemaFromScript(Path path, String schema_code, String replaceToken, String relativePathFolder) {
		SchemaExecuted script = new SchemaExecuted();
		
		script.setName(path.getFileName().toString());
		script.setSchema_executed(schema_code);
		script.setQuery(buildSchemaQuery(relativeUpdateGUPath(relativePathFolder, path.getFileName().toString()), schema_code, replaceToken));
		
		return script;
	}
	/**
	 * 
	 * @param relativePathFolder
	 * @param fileName
	 * @return
	 */
	private String relativeUpdateGUPath(String relativePathFolder, String fileName) {
		return relativePathFolder + "/" + fileName;
	}
}
