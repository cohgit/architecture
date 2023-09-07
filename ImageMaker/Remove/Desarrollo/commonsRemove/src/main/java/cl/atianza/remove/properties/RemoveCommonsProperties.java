package cl.atianza.remove.properties;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * 
 * @author pilin
 *
 */
public class RemoveCommonsProperties {
	private final String DEFAULT_PATH = "remove_commons.yaml";
	private final String SYSTEM_PROPERTY_PATH = "remove.system.property.path";
	
	public static RemoveCommonsProperties init() {
		return new RemoveCommonsProperties();
	}
	
	public RemoveCommonsProps getProperties() {
		String path = System.getenv(SYSTEM_PROPERTY_PATH) != null ? System.getenv(SYSTEM_PROPERTY_PATH) : DEFAULT_PATH;
		
		try {
			Yaml yaml = new Yaml(new Constructor(RemoveCommonsProps.class));
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