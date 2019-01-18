package net.aurore.aurorereflect;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import net.aurore.system.ClassTree;
import net.aurore.system.Classpath;
import net.aurore.system.Directory;
import net.aurore.util.FinalUtil;

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
					Set<Class<?>> potentials = t.getClasses(path);
					if(potentials != null && potentials.size() > 0)
						result.addAll(potentials);
				}
			}
		}
		return result;
	}

	@Override
	public Set<Class<?>> getTypeAnnotatedWith(Class<? extends Annotation> a){
		return getTypeAnnotatedWith(packagePath,a);
	}
	
	/* (non-Javadoc)
	 * @see net.aurore.aurorereflect.AuroreReflect#getTypeAnnotatedWith(java.lang.String, java.lang.Class)
	 */
	@Override
	public Set<Class<?>> getTypeAnnotatedWith(String packagePath, Class<? extends Annotation> a) {
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
		return getTypeAnnotatedWithAndAssignableFrom(packagePath, a, c);
	}
	
	
	/* (non-Javadoc)
	 * @see net.aurore.aurorereflect.AuroreReflect#getTypeAnnotatedWithAndAssignableFrom(java.lang.String, java.lang.Class, java.lang.Class)
	 */
	@Override
	public Set<Class<?>> getTypeAnnotatedWithAndAssignableFrom(String packagePath, Class<? extends Annotation> a,
			Class<?> c) {
		Set<Class<?>> result = new HashSet<Class<?>>();
		if(path.size() == 0) {
			throw new UnsupportedOperationException(NO_PATH);
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

	@Override
	public void expand(Directory directory) throws FileNotFoundException, AuroreReflectAlreadyRegisteredException {
		for(File f : directory.listFiles()) {
			if(f.getName().matches(FinalUtil.JAR_REGEX) && !f.isDirectory()) {
				expand(f);
			}
		}
	}

	
	public String getPackagePath() {
		return packagePath;
	}




}
