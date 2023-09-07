package cl.atianza.remove.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.owasp.esapi.ESAPI;

import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;

/**
 * Modelo de Cliente HTTP para conectarse a servicios externos.
 * @author Jose Gutierrez
 *
 */
public class HttpClientModel {
	private Logger log = LogManager.getLogger(HttpClientModel.class);
	private HttpObject http = new HttpObject();
	private static final Charset CHARSET = StandardCharsets.UTF_8;
	
	public HttpClientModel() {
		super();
	}
	public static HttpClientModel init() {
		return new HttpClientModel();
	}
	public void setUrl(String url) {
		this.http.setUrl(url);
	}
	public void setMethod(String method) {
		this.http.setMethod(method);
	}
	public void setParams(Map<String, String> requestParams) {
		this.http.setRequestParams(requestParams);
	}
	public void setHeaders(Map<String, String> headers) {
		this.http.setHeaders(headers);
	}
	
	public void putParam(String key, String value) {
		this.http.putParam(key, value);
	}
	public void putPathParam(String key, String value) {
		this.http.putPathParam(key, value);
	}
	public void putHeader(String key, String value) {
		this.http.putHeader(key, value);
	}
	
	public void setBody(String body) {
		this.http.setBody(body);
	}
	
	/**
	 * Invoca el llamado al servicio externo con los par�metros configurados.
	 * @return
	 * @throws RemoveApplicationException
	 */
	public String callService() throws RemoveApplicationException {
		return this.callService(false);
	}
	/**
	 * Invoca el llamado al servicio externo con los par�metros configurados.
	 * @return
	 * @throws RemoveApplicationException
	 */
	public String callService(boolean cleanPathParams) throws RemoveApplicationException {
		HttpURLConnection con;
		try {
			prepareUrlParams(cleanPathParams);
			con = buildConnection();
			
			if(this.http.isGet() || this.http.isPatch()) {
				return callGet(con);
			} else if(this.http.isPost()) {
				return callPost(con);
			} else {
				log.error("Method unsopported: " + this.http.getUrl());
				RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_EXTERNAL_SERVICE);
			}
		} catch (Exception e) {
			log.error(EMessageBundleKeys.ERROR_EXTERNAL_SERVICE.getTag() + " - " +  this.http.getUrl(), e);
			RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_EXTERNAL_SERVICE);
		}
		
		return null;
	}
	/**
	 * Prepara todos los par�metros url necesarios para el llamado al servicio
	 */
	private void prepareUrlParams(boolean cleanPathParams) {
		if(this.http.hasParams()) {
			for(String param : this.http.getRequestParams().keySet()) {
				String value = this.http.getRequestParams().get(param) != null ? this.http.getRequestParams().get(param) : "";
				
				if(!value.isEmpty()) {
					if(param.contains("&")) {
						value = "&" + value;
					}
				}
				
				this.http.setUrl(this.http.getUrl().replace(param, value));
			}
		}
		if (this.http.hasPathParams()) {
			this.http.addToUrl("?");
			
			for(String param : this.http.getRequestPathParams().keySet()) {
				if (cleanPathParams) {
					this.http.addToUrl(param+"="+cleanParams(this.http.getRequestPathParams().get(param))+"&");	
				} else {
					this.http.addToUrl(param+"="+this.http.getRequestPathParams().get(param)+"&");
				}
			}
		}
	}
	
	/**
	 * Parche para corregir glitch. Al desplegar la app en un contenedor Docker Alpine
	 * ScaleSerp responde resultados erroneos al encontrar caracteres 'extranos' en la query de busqueda
	 * (parametro 'q')
	 */
	@Deprecated
	public String cleanParams(String parameter) {
		if (parameter != null) {
			return parameter
					.replace("á", "%C3%A1").replace("Á", "%C3%81")	// a con tilde y A con tilde
					.replace("é", "%C3%A9").replace("É", "%C3%89")	// e con tilde y E con tilde
					.replace("í", "%C3%AD").replace("Í", "%C3%8D")	// i con tilde y I con tilde
					.replace("ó", "%C3%B3").replace("Ó", "%C3%93")	// o con tilde y O con tilde
					.replace("ú", "%C3%BA").replace("Ú", "%C3%9A")	// u con tilde y U con tilde
					.replace("ü", "%C3%BC").replace("Ü", "%C3%9C")	// u con dieresis y U con dieresis
					.replace("ñ", "%C3%B1").replace("Ñ", "%C3%91")	// n (enie) y N (ENIE)
					.replace("&", "%26");							// &
		}
		
		return parameter;
	}
	
	/**
	 * Invoca al servicio en caso de ser un REST-GET
	 * @param connection
	 * @return
	 * @throws RemoveApplicationException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private String callGet(HttpURLConnection connection) throws RemoveApplicationException, UnsupportedEncodingException, IOException {
		// Send request
//		if(this.http.hasParams()) {
//			con.setDoOutput(true);
//			DataOutputStream out = new DataOutputStream(con.getOutputStream());
//			out.writeBytes(getParamsString(this.http.getRequestParams()));
//			out.flush();
//			out.close();
//		}
		
		return readResponse(connection);
	}
	/**
	 * Invoca al servicio en caso de ser un REST-POST
	 * @param connection
	 * @return
	 * @throws RemoveApplicationException
	 * @throws IOException
	 */
	private String callPost(HttpURLConnection connection) throws RemoveApplicationException, IOException {
		log.info("Calling POST:"+connection);

		connection.setDoOutput(true);
		//Send request
	    DataOutputStream wr = new DataOutputStream (
	    		connection.getOutputStream());
	    wr.writeBytes(this.http.getBody());
	    wr.close();
	    
	    return readResponse(connection);
	}

	/**
	 * Interpreta la respuesta obtenida del servicio retornandola en una cadena de caracteres.
	 * @param connection
	 * @return
	 * @throws RemoveApplicationException
	 * @throws InterruptedException 
	 */
	private String readResponse(HttpURLConnection connection) throws RemoveApplicationException {
		StringBuilder content = new StringBuilder();

		try {
			
			log.info("Calling service: "+this.http.getUrl() + " - ConnectionURL: " + connection.getURL().getPath() + " - Query: " + connection.getURL().getQuery());
			log.info("Prueba de Contenido SCALE 1----------------------------"+content.toString());
			try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream(), CHARSET))) {
			    String line;
			    
			    while ((line = input.readLine()) != null) {
			        // Append each line of the response and separate them
			        content.append(line);
			        content.append(System.lineSeparator());
			    }
			    
			} finally {
			    connection.disconnect();
			}
		} catch (IOException e) {
			log.error(EMessageBundleKeys.ERROR_EXTERNAL_SERVICE.getTag() + this.http.getUrl(), e);
			try {
				log.error("Response Code:" + connection.getResponseCode());
				log.error("Response Error:" + new InputStreamReader(connection.getErrorStream()).toString());
				log.info("Buscando informacion para [ " + ESAPI.encoder().encodeForHTML(new InputStreamReader(connection.getErrorStream()).toString()) + " ]");
				//logger.info("Buscando informacion para [ " + ESAPI.encoder().encodeForHTML(request.getUuidTransaction()) + " ]");
			} catch (Exception e1) {}
			
			RemoveExceptionHandler.throwSeveralAppException(EMessageBundleKeys.ERROR_EXTERNAL_SERVICE);
		}
		log.info("Prueba de Contenido SCALE 2 ---------------------------- [[ " + ESAPI.encoder().encodeForHTML(content.toString()) + " ]]");
		return content.toString();
	}
	
	/**
	 * Construye la conexi�n con el servicio externo
	 * @return
	 * @throws IOException
	 */
	private HttpURLConnection buildConnection() throws IOException {
		URL url = new URL(this.http.getUrl());
		
		//HttpURLConnection con = (HttpURLConnection) url.openConnection();
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection(); 
		con.setRequestMethod(this.http.getMethod());
		
		if(this.http.hasHeaders()) {
			for(String key : this.http.getHeaders().keySet()) {
				log.info("Adding header:"+key+"-"+this.http.getHeaders().get(key));
				con.setRequestProperty(key, this.http.getHeaders().get(key));
			}
		}
		
		con.setConnectTimeout(this.http.getTimeout());
		con.setReadTimeout(this.http.getTimeout());
		con.setRequestProperty("Accept-Charset", "UTF-8");
	    con.setUseCaches(false);
	    
	    // printConnection(con);
	    
		return con;
	}
	private void printConnection(HttpURLConnection con) {
		log.info("Request Properties... ");
	    for (String k: con.getRequestProperties().keySet()) {
	    	log.info("Request Properties: " + k + " = " + con.getRequestProperty(k));	
	    }
	    log.info("Header Fields... ");
	    for (String k: con.getHeaderFields().keySet()) {
	    	log.info("Header: " + k + " = " + con.getHeaderField(k));	
	    }
	    
	    log.info("con.getContentType(): ", con.getContentType());
	    log.info("con.getContentEncoding(): ", con.getContentEncoding());
	}
	@Override
	public String toString() {
		return "HttpClient [http=" + http + "]";
	}
	
//	private String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
//		StringBuilder result = new StringBuilder();
//		
//		for (Map.Entry<String, String> entry : params.entrySet()) {
//			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
//			result.append("=");
//			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
//			result.append("&");
//		}
//
//		String resultString = result.toString();
//		return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
//	}
	
	
}

/**
 * Clase interna como modelo de configuraci�n del servicio externo
 * @author Jose Gutierrez
 */
class HttpObject {
	public static final String GET_METHOD = "GET";
	public static final String POST_METHOD = "POST";
	public static final String PATCH_METHOD = "PATCH";
	public static final String JSON_CONTENT_TYPE = "application/json";
	
	private String method = GET_METHOD;
	private String url;
	private Map<String, String> requestParams = new HashMap<String, String>();
	private Map<String, String> requestPathParams = new HashMap<String, String>();
	private Map<String, String> headers = new HashMap<String, String>();
	private String body;
	private String contentType = JSON_CONTENT_TYPE;
	
	private int timeout = 180000;
	
	public HttpObject() {
		super();
	}

	public void addToUrl(String string) {
		if (this.getUrl() != null) {
			this.setUrl(this.getUrl() + string);
		}
	}

	public HttpObject(String method, String url, Map<String, String> requestParams, Map<String, String> headers) {
		super();
		this.method = method;
		this.url = url;
		this.requestParams = requestParams;
		this.headers = headers;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String urlBase) {
		this.url = urlBase;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, String> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(Map<String, String> requestParams) {
		this.requestParams = requestParams;
	}

	public Map<String, String> getRequestPathParams() {
		return requestPathParams;
	}

	public void setRequestPathParams(Map<String, String> requestPathParams) {
		this.requestPathParams = requestPathParams;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void putHeader(String key, String value) {
		this.getHeaders().put(key, value);
	}
	public void putParam(String key, String value) {
		this.getRequestParams().put(key, value);
	}
	public void putPathParam(String key, String value) {
		this.getRequestPathParams().put(key, value);
	}
	
	public boolean isGet() {
		return this.getMethod().contentEquals(GET_METHOD);
	}
	public boolean isPatch() {
		return this.getMethod().contentEquals(PATCH_METHOD);
	}
	public boolean isPost() {
		return this.getMethod().contentEquals(POST_METHOD);
	}
	
	public boolean hasParams() {
		return this.getRequestParams() != null && !this.getRequestParams().isEmpty();
	}
	public boolean hasPathParams() {
		return this.getRequestPathParams() != null && !this.getRequestPathParams().isEmpty();
	}
	public boolean hasHeaders() {
		return this.getHeaders() != null && !this.getHeaders().isEmpty();
	}
	
	@Override
	public String toString() {
		return "HttpObject [method=" + method + ", urlBase=" + url + ", requestParams=" + requestParams
				+ ", headers=" + headers + "]";
	}
}