/**
 * 
 */
package net.aurore.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zeusu
 *
 */
public class ConfigParserManager {

	private static final String EXTENSION_SEPARATOR = "\\.";
	
	private final Map<String,ConfigParser> parsers;
	
	public ConfigParserManager(ConfigParser... parsers) {
		this.parsers = new HashMap<>();
		for(ConfigParser p : parsers) {
			this.parsers.put(p.extension(),p);
		}
	}

	public void parse(File f, Class<? extends AbstractConfig> c)
			throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		if (f.exists() && !f.isDirectory()) {
			String[] splitted = f.getName().split(EXTENSION_SEPARATOR);
			String extension = splitted[splitted.length - 1];
			if (parsers.containsKey(extension)) {
				ConfigManager.register(parsers.get(extension).parse(f, c));
			} else {
				throw new UnsupportedOperationException("Parser for \"" + extension + "\" does not exists");
			}
		} else {
			throw new FileNotFoundException("File " + f.getName() + " doesn't exist or is a directory");
		}
	}

}
	
