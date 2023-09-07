package cl.atianza.remove.external.clients.google;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.enums.ELanguages;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.utils.RemoveJsonUtil;

public class GoogleSuggestClient {
	private static Logger log = LogManager.getLogger(GoogleSuggestClient.class);
	private final String PATH = "http://suggestqueries.google.com/complete/search?client=chrome&max=2"; 
	private final Charset CHARSET = Charset.forName(StandardCharsets.UTF_8.name());
			
	public static GoogleSuggestClient init() {
		return new GoogleSuggestClient();
	}
	
	public List<String> findSuggests(String query, String lang, String country) throws RemoveApplicationException {
		List<String> results = new ArrayList<String>();
		String path = PATH + "&q=" + query;
		
		if(lang != null) {
			if (!lang.equals(ELanguages.NOLANGUAGE.getCode())) {
				path += "&hl=" + lang;
			}else {
				path = path;
			}
		}
		
		if (country != null) {
			path += "&gl=" + country;
		}
		
		log.info("SUGGEST: "+path);
		
		try {
			URL url;
		
			url = new URL(path);
		
			URLConnection conn = (URLConnection) url.openConnection();
			conn.setRequestProperty("User-Agent",
			    "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36");
			conn.connect();
			BufferedReader serverResponse = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARSET));
			
			fillResults(results, serverResponse.readLine());
			
			serverResponse.close();
		} catch (IOException e) {
			log.error("Error calling google suggest service: " + path, e);
			RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_EXTERNAL_SERVICE);
		}
		
		return results;
	}

	@SuppressWarnings("unchecked")
	private void fillResults(List<String> results, String serverResponse) throws RemoveApplicationException {
		ArrayList<Object> list = (ArrayList<Object>) RemoveJsonUtil.jsonToData(serverResponse);
		
		list.forEach(res -> {
			if (res instanceof ArrayList) {
				ArrayList<Object> list2 = (ArrayList<Object>) res;
				if (!list2.isEmpty()) {
					list2.forEach(result2 -> {
						if (result2 instanceof String) {
							String value = (String) result2;
							
							if (value != null && !value.trim().isEmpty()) {
								results.add(value);
							}
						}
					});
				}
			}
		});
	}
}
