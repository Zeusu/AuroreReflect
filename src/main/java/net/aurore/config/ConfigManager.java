/**
 * 
 */
package net.aurore.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Use to retrieve config, can be cast in the method with "T get(configId)"
 * @author Zeusu
 *
 */
public final class ConfigManager {

	private static final ConfigManager MANAGER = new ConfigManager();
	
	private final Map<String,AbstractConfig> map;
	
	private ConfigManager() {
		map = new HashMap<>();
	}
	
	public static void register(AbstractConfig config) {
		if(contrains(config.getKey()) || config.getKey() == null)
			throw new IllegalArgumentException("Config already registered or null key");
		MANAGER.map.put(config.getKey(), config);
	}
	
	public static void remove(String configId) {
		MANAGER.map.remove(configId);
	}
	
	public static AbstractConfig getAbstract(String configId) {
		return MANAGER.map.get(configId);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(String configId) {
		return (T) MANAGER.map.get(configId);
	}
	
	public static boolean contrains(String configId) {
		return MANAGER.map.containsKey(configId);
	}
	
}
