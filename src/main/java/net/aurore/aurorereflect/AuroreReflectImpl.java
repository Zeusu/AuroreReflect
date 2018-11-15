package net.aurore.aurorereflect;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import net.aurore.system.ClassTree;
import net.aurore.system.Classpath;

public class AuroreReflectImpl implements AuroreReflect {
	
	private static final String NO_PATH = "No path, you must expand or set strategy to CLASSPATH or RUNTIME";

	private Cache cache;
	private Set<URL> path;
	private String packagePath = "";
		
	/**
	 * Full specified constructor
	 * */
	public AuroreReflectImpl(CacheStrategy cStrategy, ReflectStrategy rStrategy) {
		super();
		path = new HashSet<URL>();
		if(rStrategy.equals(ReflectStrategy.CLASSPATH)) {
			for(URL url : Classpath.INSTANCE) {
				path.add(url);
			}
		}else if(rStrategy.equals(ReflectStrategy.RUNTIME)) {
			path.add(ClassLoader.getSystemClassLoader().getResource("./"));
		}
		if(cStrategy.isCached()) {
			cache = Cache.INSTANCE;
		}else {
			cache = new Cache();
		}
	}
	
	/**
	 * Conveniant constructor
	 * */
	public AuroreReflectImpl() {
		this(CacheStrategy.PERSISTANT, ReflectStrategy.CLASSPATH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.aurore.aurorereflect.AuroreReflect#getClass(java.lang.String)
	 */
	@Override
	public Set<Class<?>> getClasses(String path) {
		Set<Class<?>> result = new HashSet<Class<?>>();
		if (this.path.size() == 0) {
			throw new UnsupportedOperationException(NO_PATH);
		} else {
			for (ClassTree t : cache.getContext(this.path)) {
				if (t != null) {
					result.add(t.getClass(path));
				}
			}
		}
		return result;
	}

	@Override
	public Set<Class<?>> getTypeAnnotatedWith(Class<? extends Annotation> a){
		Set<Class<?>> result = new HashSet<Class<?>>();
		if(path.size() == 0) {
			throw new UnsupportedOperationException(NO_PATH);
		}else {
			for(ClassTree t : cache.getContext(path)) {
				if(t != null) {
					for(Class<?> c : t.getClasses(packagePath)) {
						if(c.isAnnotationPresent(a)) {
							result.add(c);
						}
					}
				}
			}
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see net.aurore.aurorereflect.AuroreReflect#getTypeAnnotatedWithAndAssignableFrom(java.lang.Class, java.lang.Class)
	 */
	@Override
	public Set<Class<?>> getTypeAnnotatedWithAndAssignableFrom(Class<? extends Annotation> a, Class<?> c) {
		Set<Class<?>> result = new HashSet<Class<?>>();
		if(path.size() == 0) {
			throw new UnsupportedOperationException();
		}else {
			for(ClassTree t : cache.getContext(path)) {
				if(t != null) {
					for(Class<?> cls : t.getClasses(packagePath)) {
						if(cls.isAnnotationPresent(a) && c.isAssignableFrom(cls)) {
							result.add(cls);
						}
					}
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see net.aurore.aurorereflect.AuroreReflect#getFieldsAnnotatedWith(java.lang.Class, java.lang.Class)
	 */
	@Override
	public Set<Field> getFieldsAnnotatedWith(Class<?> c, Class<? extends Annotation> a) {
		Set<Field> result = new HashSet<Field>();
		for(Field f : c.getDeclaredFields()) {
			if(f.isAnnotationPresent(a)) result.add(f);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see net.aurore.aurorereflect.AuroreReflect#getPublicFieldsAnnotatedWiith(java.lang.Class, java.lang.Class)
	 */
	@Override
	public Set<Field> getPublicFieldsAnnotatedWith(Class<?> c, Class<? extends Annotation> a) {
		Set<Field> result = new HashSet<Field>();
		for(Field f : c.getFields()) {
			if(f.isAnnotationPresent(a)) result.add(f);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see net.aurore.aurorereflect.AuroreReflect#getMethodsAnnotatedWidth(java.lang.Class, java.lang.Class)
	 */
	@Override
	public Set<Method> getMethodsAnnotatedWidth(Class<?> c, Class<? extends Annotation> a) {
		Set<Method> result = new HashSet<Method>();
		for(Method m : c.getMethods()) {
			if(m.isAnnotationPresent(a)) result.add(m);
		}
		return result;
	}
	
	
	
	
	
	
	@Override
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	
	}
	
	
	

	@Override
	public void expand(File file) throws AuroreReflectAlreadyRegisteredException, FileNotFoundException{
		if(!file.exists()) throw new FileNotFoundException();
		try {
			URL u = file.toURI().toURL();
			cache.expand(u);
			path.add(u);
		} catch (MalformedURLException e) {} 
			catch (AuroreReflectAlreadyRegisteredException e) {
			throw e;
		}
	}

	@Override
	public void expand(URL url) throws AuroreReflectAlreadyRegisteredException, FileNotFoundException {
		cache.expand(url);
		path.add(url);
	}


	
	
	public String getPackagePath() {
		return packagePath;
	}



}
