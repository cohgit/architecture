package cl.atianza.remove.datamanager;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.http.HttpHost;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.support.master.AcknowledgedResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.core.MainResponse;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//
//import cl.atianza.remove.enums.EMessageBundleKeys;
//import cl.atianza.remove.exceptions.RemoveApplicationException;
//import cl.atianza.remove.exceptions.RemoveExceptionHandler;

public class ElasticSearchConnector {
//	private Logger log = LogManager.getLogger(TlmeElasticSearchConnector.class);
//	
//	private String hostname;
//	private int port;
//	private String scheme = "http";
//	
//	private RestHighLevelClient client;
//	private ESQuery esQuery = ESQuery.init();
//	
//	public ESQuery getEsQuery() {
//		return esQuery;
//	}
//	public void setEsQuery(ESQuery esQuery) {
//		this.esQuery = esQuery;
//	}
//	
//	public TlmeElasticSearchConnector(String hostname, int port, String scheme) {
//		super();
//		this.hostname = hostname;
//		this.port = port;
//		this.scheme = scheme;
//	}
//	public TlmeElasticSearchConnector(String hostname, int port, String scheme, LocalDate date) {
//		super();
//		this.hostname = hostname;
//		this.port = port;
//		this.scheme = scheme;
//	}
//
//	public static TlmeElasticSearchConnector init (String hostname, int port, String scheme) {
//		return new TlmeElasticSearchConnector(hostname, port, scheme);
//	}
//	public static TlmeElasticSearchConnector init (String hostname, int port, String scheme, LocalDate date) {
//		return new TlmeElasticSearchConnector(hostname, port, scheme, date);
//	}
//	
//	private void connect() {
//		this.client = new RestHighLevelClient(RestClient.builder(new HttpHost(this.hostname, this.port, this.scheme)));
//	}
//	public void close() {
//		if (this.client != null) {
//			try {
//				this.client.close();
//				log.info("ESConnection closed...");
//			} catch (IOException e) {
//				log.error("Error disconnecting client:", e);
//			}
//		}
//	}
//	
//	private RestHighLevelClient getClient() {
//		if (this.client == null) {
//			this.connect();
//		}
//		
//		return client;
//	}
//
//	public void callClientInfo() {
//		try {
//			MainResponse response = this.getClient().info(RequestOptions.DEFAULT);
//			
//			log.info("Información del cluster: ");
//			log.info("Nombre del cluster: "+ response.getClusterName());
//			log.info("Identificador del cluster: "+ response.getClusterUuid());
//			log.info("Nombre de los nodos del cluster: "+ response.getNodeName());
//			log.info("Versión de elasticsearch del cluster: "+ response.getVersion());
//		} catch (IOException e) {
//			log.error("Error getting cluster info:", e);
//		} finally {
//			this.close();
//		}
//	}
//
//	public List<String> getByIndice(String indice) throws TlmeApplicationException {
//		List<String> documents = new ArrayList<String>();
//		
//		try {
//			SearchRequest searchRequest = new SearchRequest(indice);
//			searchRequest.source(buildSearchSource());
//			
//			SearchResponse searchResponse = this.getClient().search(searchRequest, RequestOptions.DEFAULT);
//
//			if (searchResponse.getHits().getHits().length > 0) {
//				for (SearchHit hit: searchResponse.getHits().getHits()){
//					documents.add(hit.getSourceAsString());
//				}
//			}
//		} catch (Exception e) {
//			log.error("Error getting data: ", e);
//			TlmeExceptionHandler.throwAppException(TlmeMessageBundleKeys.ERROR_DB_ES);
//		} finally {
//			this.close();
//		}
//		
//		return documents;
//	}
//
//	public List<Map<String,Object>> getByIndiceAsMap(String indice) throws TlmeApplicationException {
//		List<Map<String,Object>> documents = new ArrayList<Map<String,Object>>();
//		
//		try {
//			SearchRequest searchRequest = new SearchRequest(indice);
//			searchRequest.source(buildSearchSource());
//			
//			SearchResponse searchResponse = this.getClient().search(searchRequest, RequestOptions.DEFAULT);
//
//			if (searchResponse.getHits().getHits().length > 0) {
//				for (SearchHit hit: searchResponse.getHits().getHits()){
//					documents.add(TlmeJsonUtil.jsonToMap(hit.getSourceAsString()));
//				}
//			}
//		} catch (Exception e) {
//			log.error("Error getting data: ", e);
//			TlmeExceptionHandler.throwAppException(TlmeMessageBundleKeys.ERROR_DB_ES);
//		} finally {
//			this.close();
//		}
//		
//		return documents;
//	}
//	
//	public void deleteIndice(String index) {
//		try {
//			DeleteIndexRequest request = new DeleteIndexRequest(index); 
//			AcknowledgedResponse deleteIndexResponse = this.getClient().indices().delete(request, RequestOptions.DEFAULT);
//			
//			log.info("deleteIndexResponse: " + deleteIndexResponse);		
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			this.close();
//		}
//	}
//	public void upsert(String indice, String type, String id, Map<String, Object> document) {
//		BulkRequest request = new BulkRequest();
//		request.add(new IndexRequest(indice, type, id).source(document));
//		
//		BulkResponse bulkResponse;
//		try {
//			bulkResponse = this.getClient().bulk(request, RequestOptions.DEFAULT);
//			
//			log.info("Bulk con errores: {" + bulkResponse.hasFailures() + "}");
//		} catch (IOException e) {
//			log.error("Error upserting: ", e);
//		} finally {
//			this.close();	
//		}
//	}
//	
//	private SearchSourceBuilder buildSearchSource() {
//		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		searchSourceBuilder.size(esQuery.getSize());
//		searchSourceBuilder.timeout(new TimeValue(esQuery.getTimeoutInSecs(), TimeUnit.SECONDS));
//		
//		if (esQuery.isBool()) {
//			buildSearchBoolQuery(searchSourceBuilder);
//		}
//		
//		System.out.println("********** AGG: "+searchSourceBuilder.aggregations());
//		System.out.println("********** QUERY: "+searchSourceBuilder.query());
//		
//		return searchSourceBuilder;
//	}
//	
//	private void buildSearchBoolQuery(SearchSourceBuilder searchSourceBuilder) {
//		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//
//		this.esQuery.getBoolCondition().getConditions().forEach(cond -> {
//			log.info("cond casing:" + cond);
//			switch (cond.getType()) {
//		        case MUST:
//		        	addMustConditions(boolQuery, cond.getComparisons());
//		            break;
//		        case FILTER:
//		            break;
//		        case EXISTS:
//		        	addExistsConditions(boolQuery, cond.getComparisons());
//		            break;
//		        case MUST_NOT:
//		        	addMustNotConditions(boolQuery, cond.getComparisons());
//		            break;
//		        case SHOULD:
//		        	addShouldConditions(boolQuery, cond.getComparisons());
//		            break;
////		        case SHOULD_NOT:
////		        	addShouldNotConditions(boolQuery, cond.getComparisons());
////		            break;
//		        case MINIUM_SHOULD_MATCH:
//		        	break;
//		        case BOOST:
//		        	break;
//		        default:
//		        	log.error("Condition Type Unsopported: " + cond);
//		            break;
//		    }
//		});
////			    .must(QueryBuilders.existsQuery("usuarioIntegracao"))
////			    .must(QueryBuilders.termsQuery("tabela", "Arquivo", "Mensagem"))
////			    .must(QueryBuilders.termQuery("statusTexto", "Erro"))
////			    .must(QueryBuilders.rangeQuery("dataEntrada").from("now-1d/d").timeZone("-03:00"));
//	    searchSourceBuilder.query(boolQuery);
//	}
//	
//	private void addExistsConditions(BoolQueryBuilder boolQuery, List<ESComparison> comparisons) {
//		if (comparisons != null && !comparisons.isEmpty()) {
//			comparisons.forEach(comp -> {
//				boolQuery.must(QueryBuilders.existsQuery(comp.getKey()));
//			});
//		}
//	}
//	private void addMustConditions(BoolQueryBuilder boolQuery, List<ESComparison> comparisons) {
//		if (comparisons != null && !comparisons.isEmpty()) {
//			comparisons.forEach(comp -> {
//				switch (comp.getType()) {
//		        case TERM:
//		        	boolQuery.must(QueryBuilders.termQuery(comp.getKey(), comp.getValue()));
//		            break;
//		        case RANGE:
//		        	if (comp.getTimezone() != null) {
//		        		boolQuery.must(QueryBuilders.rangeQuery(comp.getKey()).from(comp.getValue()+"-"+comp.getValueAux() ).timeZone(comp.getTimezone()));
//		        	} else {
//		        		boolQuery.must(QueryBuilders.rangeQuery(comp.getKey()).from(comp.getValue()+"-"+comp.getValueAux()));
//		        	}
//		        	
//		            break;
//		        default:
//		        	log.error("Comparison Type Unsopported: " + comp);
//		            break;
//		    }
//			});
//		}
//	}
//	private void addMustNotConditions(BoolQueryBuilder boolQuery, List<ESComparison> comparisons) {
//		if (comparisons != null && !comparisons.isEmpty()) {
//			comparisons.forEach(comp -> {
//				switch (comp.getType()) {
//		        case TERM:
//		        	boolQuery.mustNot(QueryBuilders.termQuery(comp.getKey(), comp.getValue()));
//		            break;
//		        case RANGE:
//		        	if (comp.getTimezone() != null) {
//		        		boolQuery.mustNot(QueryBuilders.rangeQuery(comp.getKey()).from(comp.getValue()+"-"+comp.getValueAux() ).timeZone(comp.getTimezone()));
//		        	} else {
//		        		boolQuery.mustNot(QueryBuilders.rangeQuery(comp.getKey()).from(comp.getValue()+"-"+comp.getValueAux()));
//		        	}
//		        	
//		            break;
//		        default:
//		        	log.error("Comparison Type Unsopported: " + comp);
//		            break;
//		    }
//			});
//		}
//	}
////	private void addShouldNotConditions(BoolQueryBuilder boolQuery, List<ESComparison> comparisons) {
////		if (comparisons != null && !comparisons.isEmpty()) {
////			comparisons.forEach(comp -> {
////				switch (comp.getType()) {
////		        case TERM:
////		        	boolQuery.should(QueryBuilders.termQuery(comp.getKey(), comp.getValue()));
////		            break;
////		        case RANGE:
////		        	if (comp.getTimezone() != null) {
////		        		boolQuery.should(QueryBuilders.rangeQuery(comp.getKey()).from(comp.getValue()+"-"+comp.getValueAux() ).timeZone(comp.getTimezone()));
////		        	} else {
////		        		boolQuery.should(QueryBuilders.rangeQuery(comp.getKey()).from(comp.getValue()+"-"+comp.getValueAux()));
////		        	}
////		        	
////		            break;
////		        default:
////		        	log.error("Comparison Type Unsopported: " + comp);
////		            break;
////		    }
////			});
////		}
////	}
//	private void addShouldConditions(BoolQueryBuilder boolQuery, List<ESComparison> comparisons) {
//		if (comparisons != null && !comparisons.isEmpty()) {
//			comparisons.forEach(comp -> {
//				switch (comp.getType()) {
//		        case TERM:
//		        	boolQuery.should(QueryBuilders.termQuery(comp.getKey(), comp.getValue()));
//		            break;
//		        case RANGE:
//		        	if (comp.getTimezone() != null) {
//		        		boolQuery.should(QueryBuilders.rangeQuery(comp.getKey()).from(comp.getValue()+"-"+comp.getValueAux() ).timeZone(comp.getTimezone()));
//		        	} else {
//		        		boolQuery.should(QueryBuilders.rangeQuery(comp.getKey()).from(comp.getValue()+"-"+comp.getValueAux()));
//		        	}
//		        	
//		            break;
//		        default:
//		        	log.error("Comparison Type Unsopported: " + comp);
//		            break;
//		    }
//			});
//		}
//	}
//	
//	
//	
//	public void callGetTest() {
//		try {
//			SearchRequest searchRequest = new SearchRequest("context-event-date-2020-11");
////			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
////			searchSourceBuilder.query(QueryBuilders.matchAllQuery());
////			searchRequest.source(searchSourceBuilder);
//	
//			SearchResponse searchResponse = this.getClient().search(searchRequest, RequestOptions.DEFAULT);
//			
//			System.out.println("Hits length: " + searchResponse.getHits().getHits().length);
//	
//			for (SearchHit hit: searchResponse.getHits().getHits()){
//				System.out.println("Documento con id {" + hit.getId() + "}: " + hit.getSourceAsString());
//			}
//			
//			this.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void callCreateIndexTest() {
//	try {
//		// CreateIndexRequest request = new CreateIndexRequest("temas");
//		CreateIndexRequest request = new CreateIndexRequest("*");
//		// request.mapping("tema", "message", "type=text");
//		CreateIndexResponse createIndexResponse = this.getClient().indices().create(request, RequestOptions.DEFAULT);
//		 
//		boolean acknowledged = createIndexResponse.isAcknowledged();
//		System.out.println("Indice creado: {" + acknowledged + "}");
//		
//		this.close();
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//}
	

//	public void callGetIndices() throws IOException {
//	try {
////		ClusterHealthRequest request = new ClusterHealthRequest();
////		ClusterHealthResponse response = client.cluster().health(request, RequestOptions.DEFAULT);
////		Set<String> indices = response.getIndices().keySet();
//		
//		GetIndexRequest request = new GetIndexRequest().indices("*");
//		GetIndexResponse response = this.getClient().indices().get(request, RequestOptions.DEFAULT);
//		String[] indices = response.getIndices();
//		
//		if (indices != null) {
//			for (String ind : indices) {getByIndice}
//		}
//		
//		this.close();
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//}
//	public void callQueryExample() {
//		String index = "context-event-date-2020-11";
//		System.out.println("Indice: " + index);
//		
//		try {
//			SearchRequest searchRequest = new SearchRequest(index);
//			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//			
//			// searchSourceBuilder.query(QueryBuilders.termQuery("eventType", "form"));
//			searchSourceBuilder.query(QueryBuilders.termQuery("itemId", "67e37994-f99d-4a47-953f-987e0da2d4ce"));
//			searchRequest.source(searchSourceBuilder);
//
//			SearchResponse searchResponse = this.getClient().search(searchRequest, RequestOptions.DEFAULT);
//			
//			System.out.println("Hits length: " + searchResponse.getHits().getHits().length);
//
//			for (SearchHit hit: searchResponse.getHits().getHits()){
//				// System.out.println("Documento con id {" + hit.getId() + "}:  " + hit.getSourceAsString());
//				System.out.println(hit.getSourceAsString());
//				
//			}
//			
//			this.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
}
