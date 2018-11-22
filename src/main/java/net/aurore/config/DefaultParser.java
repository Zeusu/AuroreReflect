/**
 * 
 */
package net.aurore.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author Zeusu
 *
 */
public enum DefaultParser implements ConfigParser {
	
	PROPERTIES_PARSER{
		
		@Override
		public String extension() {
			return "properties";
		}
		
		@Override
		public AbstractConfig parse(File f, Class<? extends AbstractConfig> cls)
				throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException,
				IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
			ConfigClass cc = cls.getAnnotation(ConfigClass.class);
			AbstractConfig config = cls.getConstructor(String.class).newInstance(cc.key());
			Properties p = new Properties();
			p.load(new FileInputStream(f));
			for (Field field : cls.getDeclaredFields()) {
				if(field.isAnnotationPresent(ConfigProperty.class)) {
					Method setter = ConfigParser.getSetter(field, cls);
					setter.invoke(config, p.getProperty(ConfigParser.getPropertyName(field, cc.format())));
				}
			}
			return config;
		}

	};
}
