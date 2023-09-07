package cl.atianza.remove.helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.views.UploaderFile;
import cl.atianza.remove.properties.RemoveCommonsProperties;
import cl.atianza.remove.utils.RemoveDateUtils;

public class FileManager {
	private static Logger log = LogManager.getLogger(FileManager.class);
	
	public static FileManager init() {
		return new FileManager();
	}
	
	public UploaderFile uploadFile(UploaderFile infoFile, Part file) throws RemoveApplicationException {
		infoFile.setLink(storeFile(file, infoFile.getName()));
		
		return infoFile;
	}
	
	private String storeFile(Part file, String fileName) throws RemoveApplicationException {
		String finalName = RemoveDateUtils.nowIdFormat() + "_" + fileName;
		
		File targetFile = new File(RemoveCommonsProperties.init().getProperties().getTemp_files_path() + finalName);
		
		try (InputStream is = file.getInputStream()) {
			java.nio.file.Files.copy(is, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	    } catch (IOException e) {
	    	log.error("Error storing file: ", e);
	    	RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_STORING_FILE);
		}
		
		return finalName;
	}

	public byte[] obtainingFileBytes(String filePath) throws RemoveApplicationException {
		try {
			File file = obtainingFile(filePath);
			return FileUtils.readFileToByteArray(file);
		} catch (IOException | RemoveApplicationException e) {
			log.error("Error obtaining file: ", e);
			RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_FILE_NOT_FOUND);
		}
		
		return null;
	}
	
	private static File obtainingFile(String filePath) throws RemoveApplicationException {
		return new File(RemoveCommonsProperties.init().getProperties().getTemp_files_path()+filePath);
	}
}
