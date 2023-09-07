package cl.atianza.remove.external.clients.serp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.commons.ConfigurationDao;
import cl.atianza.remove.enums.ELanguages;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.external.clients.AbstractHttpClient;
import cl.atianza.remove.utils.HttpClientModel;
import cl.atianza.remove.utils.RemoveJsonUtil;
import cl.atianza.remove.utils.RemoveResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Engine class that build request for SERP API service.
 * @author pilin
 *
 */
public class RemoveSERPEngine extends AbstractHttpClient {
	public RemoveSERPEngine() {
		super(LogManager.getLogger(RemoveSERPEngine.class));
	}
	
	public static RemoveSERPEngine init() {
		return new RemoveSERPEngine();
	}
	

	
	public RemoveResponse search(RemoveSERPRequest request) throws RemoveApplicationException {
		log.info("Searching... " + request);
		
		ConfigurationDao configDao = ConfigurationDao.init();
		log.info("API_KEY..."+ configDao.getScaleSerpKey());
		
		int retries = 0; 		// Will retry 10 times 
		boolean done = false;
		RemoveResponse response; 
		
		//do {
			log.info("Trying... " + retries + " - " + request);
			HttpClientModel client = HttpClientModel.init();
			
			client.setUrl(configDao.getScaleSerpApi());
			
			client.setMethod("GET");
			client.putPathParam("api_key", configDao.getScaleSerpKey());
			try {
				fillParameters(client, request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			response = executeService(client, true);
			
			log.info("RemoveResponseGETDATA: "+ response.getData() + "STATUS CODE: "+ response.getCode()); 
			
			//RemoveSERPWebResponse serpResponse = (RemoveSERPWebResponse) RemoveJsonUtil.jsonToData(RemoveJsonUtil.dataToJson(response.getData()), RemoveSERPWebResponse.class);	
			
			//if (serpResponse.checkRepeatedResults()) {						
				//retries++;
			//} else {
				//log.info("Search Done... " + retries + " - " + request);
				//done = true;
			//}
		//} while (retries < 10 && !done);
		 
		return response;
	}
	
	public RemoveResponse accountData() throws RemoveApplicationException {
		ConfigurationDao configDao = ConfigurationDao.init();
		HttpClientModel client = HttpClientModel.init();
		
		client.setUrl("https://api.scaleserp.com/account");
		
		client.setMethod("GET");
		client.putPathParam("api_key", configDao.getScaleSerpKey());
		
		return executeService(client);
	}
	
	@Deprecated
	public RemoveResponse search(String urlPath) throws RemoveApplicationException {
		log.info("Searching... " + urlPath);
		HttpClientModel client = HttpClientModel.init();
		
		client.setUrl(urlPath);
		client.setMethod("GET");
		
		return executeService(client, true);
	}
	
	private void fillParameters(HttpClientModel client, RemoveSERPRequest request)  {
		// Web Params
		try {
			pushPathParam(client, "q",URLEncoder.encode(request.getQ().replace(" ", "+"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pushPathParam(client, "search_type", request.getSearch_type());
		pushPathParam(client, "device", request.getDevice());
//		pushPathParam(client, "page", request.getPage());
		pushPathParam(client, "google_domain", request.getGoogle_domain());
		
		
		if (request.getLocation() != null) {
			pushPathParam(client, "location", request.getLocation());
		} else {
			pushPathParam(client, "gl", request.getGl());
			if(!request.getHl().equals(ELanguages.NOLANGUAGE.getCode()))
				pushPathParam(client, "hl", request.getHl());
			
			else
				pushPathParam(client, "", "");
		}
		pushPathParam(client, "page", request.getPage());
		pushPathParam(client, "num", request.getNum());
		//pushPathParam(client, "max_page", request.getMax_page());
		
		// News Params
		pushPathParam(client, "news_type", request.getNews_type());
		
		// Image Params
		pushPathParam(client, "images_page", request.getImages_page());
		pushPathParam(client, "images_size", request.getImages_size());
		pushPathParam(client, "images_color", request.getImages_color());
		pushPathParam(client, "images_type", request.getImages_type());
		pushPathParam(client, "images_usage", request.getImages_usage());
	}

	private void pushPathParam(HttpClientModel client, String param, String value) {
		if (value != null) {
			client.putPathParam(param, value);
		}
	}
}
