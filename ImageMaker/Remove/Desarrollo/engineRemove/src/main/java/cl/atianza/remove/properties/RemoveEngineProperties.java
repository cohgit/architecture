package cl.atianza.remove.properties;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * 
 * @author pilin
 *
 */
public class RemoveEngineProperties {
	private final String DEFAULT_PATH = "remove_engine.yaml";
	private final String SYSTEM_PROPERTY_PATH = "remove.system.property.path";
	
	public static RemoveEngineProperties init() {
		return new RemoveEngineProperties();
	}
	
	public RemoveEngineProps getProperties() {
		String path = System.getProperty(SYSTEM_PROPERTY_PATH, DEFAULT_PATH);
		
		try {
			Yaml yaml = new Yaml(new Constructor(RemoveEngineProps.class));
			InputStream inputStream = this.getClass()
					  .getClassLoader()
					  .getResourceAsStream(path);
			return yaml.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}