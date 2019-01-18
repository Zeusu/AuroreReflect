/**
 * 
 */
package net.aurore.system;

import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pierre
 *
 */
public class ClassJarExpandingTree extends ClassJarTree{

	private URLClassLoader classLoader = null;
	
	public ClassJarExpandingTree(URL url) throws FileNotFoundException {
		super(url);
		if(classLoader == null) {
			classLoader = new URLClassLoader(new URL[] {url});
		}
	}
	
	
	@Override
	public Set<Class<?>> getClasses(String path) {
		Set<Class<?>> result = new HashSet<Class<?>>();
		String[] splittedPath = path.split(SEPARATOR);
		AbstractClassTree t = this;
		if(path != null) {
			int i = 0;
			while(t != null && i < splittedPath.length) {
				t = t.nexts.get(splittedPath[i]);
				i++;
			}
		}	
		if(t != null) {
			try {
				t.getExpandingClasses(result, path,classLoader);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	public Class<?> getClass(String path) {
		String[] splittedPath = path.split(SEPARATOR);
		String className = splittedPath[splittedPath.length - 1];
		AbstractClassTree t = this;
		if(path != null) {
			int i = 0;
			while(t != null && i < splittedPath.length - 1) {
				t = t.nexts.get(splittedPath[i]);
				i++;
			}
		}	
		if(t != null && t.childs.contains(className + ".class")) {
			try {
				return classLoader.loadClass(path);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
