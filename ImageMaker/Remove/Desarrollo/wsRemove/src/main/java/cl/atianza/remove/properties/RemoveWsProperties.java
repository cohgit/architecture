package cl.atianza.remove.properties;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * 
 * @author pilin
 *
 */
public class RemoveWsProperties {
	private final String DEFAULT_PATH = "remove_ws.yaml";
	private final String SYSTEM_PROPERTY_PATH = "remove.system.property.path";
	
	public static RemoveWsProperties init() {
		return new RemoveWsProperties();
	}
	
	public RemoveWsProps getProperties() {
		String path = System.getProperty(SYSTEM_PROPERTY_PATH, DEFAULT_PATH);
		
		try {
			Yaml yaml = new Yaml(new Constructor(RemoveWsProps.class));
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