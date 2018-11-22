/**
 * 
 */
package net.aurore.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.aurore.util.FinalUtil;

/**
 * @author Zeusu
 *
 */
public interface ConfigParser {

	public String extension();
	
	public AbstractConfig parse(File f, Class<? extends AbstractConfig> cls)throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException,
		IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException ;




	public static Method getSetter(Field f, Class<?> c) throws NoSuchMethodException, SecurityException {
		String fieldName = f.getName();
		String setterName = fieldName.substring(0, 1).toUpperCase();
		if(fieldName.length() > 1)
			setterName += fieldName.substring(1);
		return c.getDeclaredMethod(FinalUtil.SETTER + setterName, f.getType());		
	}
	
	public static String getPropertyName(Field f, PropertyFormat pf) {
		String name = f.getAnnotation(ConfigProperty.class).name();
		if(name.equals(ConfigProperty.DEFAULT_NAME)) name = f.getName();
		if(pf.equals(PropertyFormat.LOWER_CASE)) {
			name = name.toLowerCase();
		}else if(pf.equals(PropertyFormat.UPPER_CASE)) {
			name = name.toUpperCase();
		}
		return name;
	}


}
