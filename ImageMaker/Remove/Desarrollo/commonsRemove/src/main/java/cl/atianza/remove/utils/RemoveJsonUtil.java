package cl.atianza.remove.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;

/**
 * Clase utilitaria con los métodos para convertir una instancia Java en un JSON String y viceversa.
 * @author Jose Gutierrez
 *
 */
public class RemoveJsonUtil {
	private static Logger log;
	
	/**
	 * Convierte una instancia de Java a una cadena de caracteres (JSON)
	 * @param data
	 * @return
	 * @throws RemoveApplicationException
	 */
	public static String dataToJson(Object data) throws RemoveApplicationException {
        try {
            ObjectMapper mapper = createMapper();
            
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            
            return sw.toString();
        } catch (IOException e) {
        	getLog().error(EMessageBundleKeys.ERROR_PARSING_JSON.getTag()+" "+data, e);
        	RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PARAMS);
        }
        
        return null;
    }
	
	public static Map<String, Object> dataToMap(Object data) throws RemoveApplicationException {
        return jsonToMap(dataToJson(data));
    }
	
	/**
	 * Convierte una cadena de caracteres (JSON) en una instancia de Java 
	 * @param json
	 * @param valueType
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToData(String json, Class<?> valueType) throws RemoveApplicationException {
		ObjectMapper objectMapper = createMapper();
		
		try {
		    return (T) objectMapper.readValue(json, valueType);
		} catch (IOException e) {
			getLog().error(EMessageBundleKeys.ERROR_PARSING_JSON.getTag()+" "+json, e);
		    RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		}
		
		return null;
	}
	public static Object jsonToData(String json) throws RemoveApplicationException {
		ObjectMapper objectMapper = createMapper();
		
		try {
		    return objectMapper.readValue(json, Object.class);
		} catch (IOException e) {
			getLog().error(EMessageBundleKeys.ERROR_PARSING_JSON.getTag()+" "+json, e);
		    RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		}
		
		return null;
	}
	
	/**
	 * Convierte un arreglo JSON en una instancia util.List de java del tipo especificado.
	 * 
	 * @param <T>
	 * @param jsonList
	 * @param type
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<Object> parseJsonListToData(String jsonList, Class<T> type) 
    		throws JsonGenerationException, JsonMappingException, IOException{
    	ObjectMapper mapper = createMapper();
    	
    	return mapper.readValue(jsonList, List.class);
    }
	
	/**
	 * Construye el ObjectMapper que realizará la conversión.
	 * @return
	 */
	private static ObjectMapper createMapper() {
		ObjectMapper mapper = new ObjectMapper();
		
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(RemoveDateUtils.FORMAT_FOR_JSON_PARSE);
        
		return mapper;
	}
	
	/**
	 * Convierte un String JSON en un Map/File instancia de Java, util para las lecturas de archivo por servicio.
	 * @param json
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, List<Map<String, String>>> jsonToFileMap(String json) throws RemoveApplicationException {
		return (Map<String, List<Map<String, String>>>) jsonToData(json, Map.class);
	}
	/**
	 * Convierte un String JSON en un List/File instancia de Java, util para las lecturas de archivo por servicio.
	 * @param json
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<LinkedHashMap<String,Object>> jsonToFileList(String json) throws RemoveApplicationException {
		return (ArrayList<LinkedHashMap<String,Object>>) jsonToData(json, ArrayList.class);
	}
	
	/**
	 * Convierte un String JSON en un util.Map instancia de Java generico.
	 * @param json
	 * @return
	 * @throws RemoveApplicationException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(String json) throws RemoveApplicationException {
		return (Map<String, Object>) jsonToData(json, Map.class);
	}
	
	/**
	 * Obtiene el log para registrar las trazas.
	 * @return
	 */
	private static Logger getLog() {
		if(log == null) {
			log = LogManager.getLogger(RemoveJsonUtil.class);
		}
		
		return log;
	}

	public static Object clone(Object object) throws RemoveApplicationException {
		return jsonToData(dataToJson(object), object.getClass());
	}
	
	public static <T> Object cast(Object object, Class<T> type) throws RemoveApplicationException {
		return jsonToData(dataToJson(object), type);
	}
}
