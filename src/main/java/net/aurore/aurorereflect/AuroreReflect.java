package net.aurore.aurorereflect;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Set;

import net.aurore.system.Directory;

/**
 * An AuroreReflect object is an adptater which will allow you do easy reflection. To instantiate use AuroreReflectImpl.
 * @author Zeusu
 * */
public interface AuroreReflect {
	
	/**
	 * Return a set of classes which match the specified path.
	 * @param path
	 * @return Set<Class<?>>
	 * */
	public Set<Class<?>> getClasses(String path);
	
	/**
	 * Return a set of classes with annotated with a.
	 * @param a  
	 * @return Set<Class<?>>
	 * */
	public Set<Class<?>> getTypeAnnotatedWith(Class<? extends Annotation> a);
	
	
	/**
	 * Return a set of classes with annotated with a within the package.
	 * @param a  
	 * @return Set<Class<?>>
	 * */
	public Set<Class<?>> getTypeAnnotatedWith(String packagePath, Class<? extends Annotation> a);
	
	/**
	 * Return a set of classes with annotated with a and extending/implementing c.
	 * @param a c
	 * @return Set<Class<?>>
	 * */
	public Set<Class<?>> getTypeAnnotatedWithAndAssignableFrom(Class<? extends Annotation> a, Class<?> c);
	
	
	
	/**
	 * Return a set of classes with annotated with a and extending/implementing c within the package.
	 * @param a c
	 * @return Set<Class<?>>
	 * */
	public Set<Class<?>> getTypeAnnotatedWithAndAssignableFrom(String packagePath, Class<? extends Annotation> a, Class<?> c);
	
	
	/**
	 * Set the package path. This method must be call before getClassesAnnotatedWith(Class<? extends Annotation> a).
	 * @param packagePath
	 * */
	public void setPackagePath(String packagePath);

	/**
	 * Add the file to the known classes. Must be a jar.
	 * @param file
	 * */
	public void expand(File file) throws AuroreReflectAlreadyRegisteredException, FileNotFoundException;
	
	/**
	 * Add the file, using his url, to the known classes. Must be a jar.
	 * @param url
	 * */
	public void expand(URL url) throws AuroreReflectAlreadyRegisteredException,  FileNotFoundException;
	
	/**
	 * Add tall the jar in the directory to the known classes
	 * @param directory
	 * @throws AuroreReflectAlreadyRegisteredException 
	 * @throws FileNotFoundException 
	 * */
	public void expand(Directory directory) throws FileNotFoundException, AuroreReflectAlreadyRegisteredException;
	
	
}
