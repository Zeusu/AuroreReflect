package net.aurore.system;

import java.util.Set;

public interface ClassTree {

	public static final String SEPARATOR = "\\.";
	public static final int CLASS_KEYWORD_LENGTH = 6;

	/**
	 * @param path
	 * @return Set<Class<?>> a set of classes in the package
	 * */
	public Set<Class<?>> getClasses(String path);
	
	
	/**
	 * @param path
	 * @return Class<?> the class or null
	 * */
	public Class<?> getClass(String path);

}
