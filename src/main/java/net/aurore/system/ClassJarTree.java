package net.aurore.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.aurore.util.Escaping;
import net.aurore.util.FinalUtil;

public class ClassJarTree extends AbstractClassTree{
	
	private static final String JAR_PATH_SERPARTOR = "/";

	protected final URL source;
	
	public ClassJarTree(URL url) throws FileNotFoundException {
		this(url,"","");
		JarFile jFile = null;
		try {
			jFile = new JarFile(new File(Escaping.escape(url)));
			Enumeration<JarEntry> entries = jFile.entries();
			while(entries.hasMoreElements()) {
				addEntry(entries.nextElement());
			}
		} catch (IOException | URISyntaxException e) {
			throw new FileNotFoundException();
		}finally {
			try {jFile.close();}catch(Exception e) {}
		}
	}
	
	private ClassJarTree(URL url, String group, String name){
		source = url;
		this.group = group;
		this.name = name;
		childs = new ArrayList<String>();
		nexts = new HashMap<String,AbstractClassTree>();
	}
	
	private void addEntry(JarEntry jEntry) {
		if(!jEntry.isDirectory() && !jEntry.getName().matches(FinalUtil.CLASS_REGEX)) return;
		String[] splittedPath = jEntry.getName().split(JAR_PATH_SERPARTOR);
		AbstractClassTree curr = this;
		for(int i = 0; i < splittedPath.length; i++) {
			if(splittedPath[i].matches(FinalUtil.CLASS_REGEX)) {
				curr.childs.add(splittedPath[i]);
			}else{
				if(!curr.nexts.containsKey(splittedPath[i])) {
					curr.nexts.put(splittedPath[i], new ClassJarTree(source,curr.group + curr.name + JAR_PATH_SERPARTOR, splittedPath[i]));
				}
				curr = curr.nexts.get(splittedPath[i]);
			}
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
				t.getClasses(result, path);
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
				return Class.forName(path);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

}
