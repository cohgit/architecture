package cl.atianza.remove.utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.AbstractDao;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.CacheDB;
import cl.atianza.remove.properties.RemoveCommonsProperties;

public class RemoveDao extends AbstractDao {
	private Map<Long, CacheDB> cache;
	protected int cacheExpireHours = 1;
	
	public RemoveDao(Logger _log, String table_name) throws RemoveApplicationException {
		super(_log, table_name);
	}
	
	protected static String removeTable(String tableName) {
		return schemaTable(RemoveCommonsProperties.init().getProperties().getConnection().getSchemaName(), tableName);
	}

	protected String concatFieldsForUpdate(String...fields) {
		String subquery = "";
		
		if(fields != null) {
			for(String field : fields) {
				subquery += field + " = :" + field + ", ";
			}
		}
		
		return subquery.isEmpty() ? subquery : subquery.substring(0, subquery.length()-2);
	}

	private Map<Long, CacheDB> getCache() {
		if (cache == null) {
			this.cache = new HashMap<Long, CacheDB>();
		}
		
		return cache;
	}

	protected void putInCache(Long key, Object data) {
		if (data != null) {
			try {
				this.getCache().put(key, new CacheDB(LocalDateTime.now(), RemoveJsonUtil.dataToJson(data)));
			} catch (RemoveApplicationException e) {
				getLog().error("Error saving cache info: ", e);
			}
		}
	}
	
	protected <T> T getFromCache(Long key, Class<?> valueType) {
		try {
			CacheDB _cache = this.getCache().get(key);
		
			if (_cache != null && _cache.getCreated().plusHours(this.cacheExpireHours).isBefore(LocalDateTime.now())) {
				return RemoveJsonUtil.jsonToData(_cache.getData(), valueType);	
			}
		} catch (RemoveApplicationException e) {
			getLog().error("Error getting cache info: ", e);
		}
		
		return null;
	}
}
