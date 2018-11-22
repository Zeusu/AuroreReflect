/**
 * 
 */
package net.aurore.config;

/**
 * Abstract vision of a config
 * @author Zeusu
 *
 */
public abstract class AbstractConfig {
	
	protected final String key;
	
	public AbstractConfig(String key) {
		this.key = key;
	}
	
	/**
	 * @return the key for the config Manager
	 */
	public String getKey() {
		return key;
	}

}
