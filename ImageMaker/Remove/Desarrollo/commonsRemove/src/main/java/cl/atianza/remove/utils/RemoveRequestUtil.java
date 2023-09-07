package cl.atianza.remove.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.io.CharStreams;

import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.views.UploaderFile;
import cl.atianza.remove.properties.RemoveCommonsProperties;
import spark.Request;

/**
 * Clase utilitaria para operar el objeto Request de los servicios de entrada.
 * 
 * @author Jose Gutierrez
 */
public class RemoveRequestUtil {
	private static Logger log = LogManager.getLogger(RemoveRequestUtil.class);
	/**
	 * Obtiene un parámetro entero de los QueryParams, en caso de no existir arroja una excepción.
	 * @param request
	 * @param param
	 * @return
	 * @throws RemoveApplicationException
	 */
	public static Integer getParamInt(Request request, EWebParam param) throws RemoveApplicationException {
		return getParamInt(request, param.getTag());
	}
	public static Integer getParamInt(Request request, String key) throws RemoveApplicationException {
		try {
			return Integer.parseInt(getParamString(request, key));
		} catch (Exception ex) {
			RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		}
		
		return null;
	}
	public static Long getParamLong(Request request, EWebParam param) throws RemoveApplicationException {
		return getParamLong(request, param.getTag());
	}
	public static Long getParamLong(Request request, String key) throws RemoveApplicationException {
		try {
			return Long.valueOf(getParamString(request, key));
		} catch (Exception ex) {
			RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		}
		
		return null;
	}
	/**
	 * Obtiene un parámetro entero de los QueryParams, en caso de no existir retorna null.
	 * @param request
	 * @param key
	 * @return
	 */
	public static Integer getOptionalParamInt(Request request, String key) {
		String value = getOptionalParamString(request, key);
		
		if(value != null) {
			return Integer.parseInt(value);			
		}
		
		return null;
	}
	public static Integer getOptionalParamInt(Request request, EWebParam param) {
		return getOptionalParamInt(request, param.getTag());
	}

	/**
	 * Obtiene un parámetro String de los QueryParams, en caso de no existir arroja una excepción.
	 * @param request
	 * @param key
	 * @return
	 * @throws RemoveApplicationException
	 */
	public static String getParamString(Request request, String key) throws RemoveApplicationException {
		String value = request.queryParams(key);
		
		if(value == null || value.equalsIgnoreCase("null") || value.trim().isEmpty()) {
			log.error(EMessageBundleKeys.ERROR_INVALID_PARAMS + ": Not found '"+key+"'");
			RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		}
		
		return value;
	}
	public static String getParamString(Request request, EWebParam param) throws RemoveApplicationException {
		return getParamString(request, param.getTag());
	}
	/**
	 * Obtiene un parámetro String de los QueryParams, en caso de no existir retorna null.
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getOptionalParamString(Request request, String key) {
		String value = request.queryParams(key);
		
		if(value == null || value.equalsIgnoreCase("null") || value.trim().isEmpty()) {
			value = null;
		}
		
		return value;
	}
	public static String getOptionalParamString(Request request, EWebParam param) {
		return getOptionalParamString(request, param.getTag());
	}
	/**
	 * Obtiene un parámetro Boolean de los QueryParams, en caso de no existir arroja una excepción.
	 * @param request
	 * @param key
	 * @return
	 * @throws RemoveApplicationException
	 */
	public static Boolean getParamBoolean(Request request, String key) throws RemoveApplicationException {
		try {
			return Boolean.parseBoolean(getParamString(request, key));
		} catch (Exception ex) {
			RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		}
		
		return null;
	}
	public static Boolean getParamBoolean(Request request, EWebParam param) throws RemoveApplicationException {
		return getParamBoolean(request, param.getTag());
	}
	/**
	 * Obtiene un parámetro Boolean de los QueryParams, en caso de no existir retorna null.
	 * @param request
	 * @param key
	 * @return
	 */
	public static Boolean getOptionalParamBolean(Request request, String key) {
		String value = getOptionalParamString(request, key);
		
		if(value != null) {
			return Boolean.parseBoolean(value);			
		}
		
		return null;
	}
	public static Boolean getOptionalParamBolean(Request request, EWebParam param) {
		return getOptionalParamBolean(request, param.getTag());
	}
	
	public static LocalDate getParamLocalDate(Request request, String key) throws RemoveApplicationException {
		return RemoveDateUtils.parseLocalDateFrontend(getParamString(request, key));
	}
	public static LocalDate getParamLocalDate(Request request, EWebParam param) throws RemoveApplicationException {
		return getParamLocalDate(request, param.getTag());
	}
	
	public static LocalDate getOptionalParamLocalDate(Request request, String key) throws RemoveApplicationException {
		String stringDate = getOptionalParamString(request, key);
		return stringDate != null ? RemoveDateUtils.parseLocalDateFrontend(stringDate) : null;
	}
	public static LocalDate getOptionalParamLocalDate(Request request, EWebParam param) throws RemoveApplicationException {
		return getOptionalParamLocalDate(request, param.getTag());
	}
	
	/**
	 * Extrae del objeto request la instancia para cargar un listado paginado para una tabla en la vista.
	 * @param request
	 * @return
	 */
//	public static AbstractListView getViewParams(Request request) throws RemoveApplicationException {
//		AbstractListView view = new AbstractListView();
//		for (String key : request.queryParams()){
//			if (key.equals(EWebParam.NUMERO_PAGINA.getTag())) {
//				view.setNumeroPagina(getParamInt(request, key));
//			} else if (key.equals(EWebParam.CANTIDAD.getTag())) {
//				view.setRegistrosPorPagina(getParamInt(request, key));
//			} else if (key.equals(EWebParam.ORDER.getTag())) {
//				view.setOrderField(getOptionalParamString(request,key));
//			} else if (key.equals(EWebParam.ORDER_TYPE.getTag())) {
//				view.setOrderType(getOptionalParamString(request, key));
//			} else if (key.equals(EWebParam.SEACH_LIKE.getTag())) {
//				view.setLikeSearch(getOptionalParamString(request, key));
//			} else {
//				view.putParam(key, getOptionalParamString(request, key));
//			}
//		};
//		
//		return view;
//	}
	
	public static Map<String, String> extractQueryParams(Request request) {
		Map<String, String> map = new HashMap<String, String>();
		
		for (String key : request.queryParams()) {
			map.put(key, request.queryParams(key));
		}
		
		return map;
	}
	
	/**
	 * Extrae el token de seguridad del header del request.
	 * @param request
	 * @return
	 */
    public static String getToken(Request request) {
        return request.headers(EWebParam.TOKEN.getTag());
    }
    public static String getHeader(Request request, EWebParam param) {
        return request.headers(param.getTag());
    }
    public static RemoveTokenAccess extractToken(Request request) throws RemoveApplicationException {
    	RemoveTokenAccess access = RemoveTokenAccess.init(getToken(request));
        return access;
    }
    
    public static Object getBodyObject(Request request, Class<?> valueType) throws RemoveApplicationException {
    	return RemoveJsonUtil.jsonToData(request.body(), valueType);
    }
    public static Map<String, Object> getBodyMap(Request request) throws RemoveApplicationException {
    	return RemoveJsonUtil.jsonToData(request.body(), Map.class);
    }
    public static List<Object> getBodyList(Request request, Class<?> valueType) throws RemoveApplicationException, JsonGenerationException, JsonMappingException, IOException {
    	return RemoveJsonUtil.parseJsonListToData(request.body(), valueType);
    }
    public static Map<String, List<Map<String, String>>> getBodyFileMap(Request request) throws RemoveApplicationException {
    	return RemoveJsonUtil.jsonToFileMap(request.body());
    }
    public static ArrayList<LinkedHashMap<String, Object>> getBodyFileList(Request request) throws RemoveApplicationException {
    	return RemoveJsonUtil.jsonToFileList(request.body());
    }

	// Metodos utilizados a futuro (Configuraciones de usuario y session)
	public static String getQueryLocale(Request request) {
        return request.queryParams("locale");
    }

    public static String getParamIsbn(Request request) {
        return request.params("isbn");
    }

    public static String getQueryUsername(Request request) {
        return request.queryParams("username");
    }

    public static String getQueryPassword(Request request) {
        return request.queryParams("password");
    }

    public static String getQueryLoginRedirect(Request request) {
        return request.queryParams("loginRedirect");
    }

    public static String getSessionLocale(Request request) {
        return request.session().attribute("locale");
    }

    public static String getSessionCurrentUser(Request request) {
        return request.session().attribute("currentUser");
    }

    public static boolean removeSessionAttrLoggedOut(Request request) {
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        return loggedOut != null;
    }

    public static String removeSessionAttrLoginRedirect(Request request) {
        String loginRedirect = request.session().attribute("loginRedirect");
        request.session().removeAttribute("loginRedirect");
        return loginRedirect;
    }

    public static boolean clientAcceptsHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }

    public static boolean clientAcceptsJson(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("application/json");
    }
	public static Part extractFileMultipart(Request request) throws RemoveApplicationException {
		try {
			request.attribute("org.eclipse.jetty.multipartConfig", 
					new MultipartConfigElement("D:\\temp-atianza\\temp\\"));
			return request.raw().getPart(EWebParam.FILE.getTag());
		} catch (IOException | ServletException e) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		}
		
		return null;
	}
	public static UploaderFile extractDataMultipart(Request request, Class<UploaderFile> valueType) throws RemoveApplicationException {
		try {
			request.attribute("org.eclipse.jetty.multipartConfig", 
					new MultipartConfigElement(RemoveCommonsProperties.init().getProperties().getTemp_files_path()));
			Part dataPart = request.raw().getPart(EWebParam.DATA.getTag());
			try (InputStream is = dataPart.getInputStream()) {
				try (Reader reader = new InputStreamReader(is)) {
					return RemoveJsonUtil.jsonToData(CharStreams.toString(reader), valueType);
			    }
			}
		} catch (IOException | ServletException e) {
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_INVALID_PARAMS);
		}
		
		return null;
	}
}
