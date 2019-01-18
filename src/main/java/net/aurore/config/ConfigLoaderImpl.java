/**
 * 
 */
package net.aurore.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

import net.aurore.aurorereflect.AuroreReflectImpl;
import net.aurore.aurorereflect.CacheStrategy;
import net.aurore.aurorereflect.ReflectStrategy;
import net.aurore.system.Directory;

/**
 * @author Pierre
 *
 */
public class ConfigLoaderImpl implements ConfigLoader {
	
	private final ConfigParserManager parserManager;
	private final Set<Class<?>> classes;
	
	public ConfigLoaderImpl(ConfigParser... parsers) {
		parserManager = new ConfigParserManager(parsers);
		classes = new AuroreReflectImpl(CacheStrategy.INSTANCE, ReflectStrategy.CLASSPATH)
					.getTypeAnnotatedWithAndAssignableFrom(ConfigClass.class, AbstractConfig.class);
	}
	

	/* (non-Javadoc)
	 * @see net.aurore.config.ConfigLoader#load(java.lang.String)
	 */
	@Override
	public void load(File file) throws FileNotFoundException {
	
	
	
	}
	
	@Override
	public void load(Directory directory) throws FileNotFoundException{
		
	}
}
