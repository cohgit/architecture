package cl.atianza.remove.helpers.reports.beans;

import java.util.HashMap;
import java.util.Map;

public class KeywordFeelingCounter {
	Map<String, FeelingCounter> map = new HashMap<String, FeelingCounter>();

	public KeywordFeelingCounter() {
		super();
	}

	public Map<String, FeelingCounter> getMap() {
		return map;
	}

	public void setMap(Map<String, FeelingCounter> map) {
		this.map = map;
	}
	
	public void inc(String key, String feeling) {
		if (map.get(key) == null) {
			map.put(key, new FeelingCounter());
		}
		
		map.get(key).incByFeeling(feeling);
	}

	@Override
	public String toString() {
		return "KeywordFeelingCounter [map=" + map + "]";
	}
}
