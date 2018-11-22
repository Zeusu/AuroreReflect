/**
 * 
 */
package net.aurore.aurorereflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import net.aurore.util.FinalUtil;

/**
 * Utils for working on classes
 * @author Zeusu
 *
 */
public final class ReflectionUtils {

	/**
	 * Return the setter for the giving field (if present)
	 * @param f the field , c the class
	 * @return Setter
	 * */
	public static final Method getSetter(Field f, Class<?> c) throws NoSuchMethodException, SecurityException {
		String fieldName = f.getName();
		String setterName = fieldName.substring(0, 1).toUpperCase();
		if(fieldName.length() > 1)
			setterName += fieldName.substring(1);
		return c.getDeclaredMethod(FinalUtil.SETTER + setterName, f.getType());		
	}
	
	/**
	 * Return the getter for the giving field (if present)
	 * @param f the field , c the class
	 * @return Getter
	 * */
	public static final Method getGetter(Field f, Class<?> c) throws NoSuchMethodException, SecurityException {
		String fieldName = f.getName();
		String setterName = fieldName.substring(0, 1).toUpperCase();
		if(fieldName.length() > 1)
			setterName += fieldName.substring(1);
		return c.getDeclaredMethod(FinalUtil.GETTER + setterName, f.getType());		
	}
	
	/**
	 * Return a set of field with annotated with a in c.
	 * @param c a
	 * @return Set<Class<?>>
	 * */
	public static final Set<Field> getFieldsAnnotatedWith(Class<?> c, Class<? extends Annotation> a) {
		Set<Field> result = new HashSet<Field>();
		for(Field f : c.getDeclaredFields()) {
			if(f.isAnnotationPresent(a)) result.add(f);
		}
		return result;
	}

	/**
	 * Return a set of field with annotated with a in c.
	 * @param c a
	 * @return Set<Class<?>>
	 * */
	public static final Set<Field> getPublicFieldsAnnotatedWith(Class<?> c, Class<? extends Annotation> a) {
		Set<Field> result = new HashSet<Field>();
		for(Field f : c.getFields()) {
			if(f.isAnnotationPresent(a)) result.add(f);
		}
		return result;
	}

	/**
	 * Return a methods of field with annotated with a in c.
	 * @param c a
	 * @return Set<Class<?>>
	 * */
	public static final Set<Method> getMethodsAnnotatedWidth(Class<?> c, Class<? extends Annotation> a) {
		Set<Method> result = new HashSet<Method>();
		for(Method m : c.getMethods()) {
			if(m.isAnnotationPresent(a)) result.add(m);
		}
		return result;
	}
	
	
}
