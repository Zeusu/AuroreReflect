package net.aurore.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.aurore.util.FinalUtil;

public class ClassJarTree implements ClassTree{
	
	private static final String JAR_PATH_SERPARTOR = "/";

	protected final URL source;
	protected final String group;
	protected final String name;
	protected final Set<String> childs;
	protected final Map<String,ClassJarTree> nexts;
	
	
	
	public ClassJarTree(URL url) throws FileNotFoundException {
		this(url,"","");
		JarFile jFile = null;
		try {
			jFile = new JarFile(new File(url.toURI()));
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
		childs = new HashSet<String>();
		nexts = new HashMap<String,ClassJarTree>();
	}
	
	private void addEntry(JarEntry jEntry) {
		if(!jEntry.isDirectory() && !jEntry.getName().matches(FinalUtil.CLASS_REGEX)) return;
		String[] splittedPath = jEntry.getName().split(JAR_PATH_SERPARTOR);
		ClassJarTree curr = this;
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
		ClassJarTree t = this;
		if(path != null) {
			int i = 0;
			while(t != null && i < splittedPath.length) {
				t = t.nexts.get(splittedPath[i]);
				i++;
			}
		}	
		if(t != null) {
			for(String s : t.childs) {
				try {
					result.add(Class.forName(path + "." + s.substring(0, s.length() - CLASS_KEYWORD_LENGTH)));
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	@Override
	public Class<?> getClass(String path) {
		String[] splittedPath = path.split(SEPARATOR);
		String className = splittedPath[splittedPath.length - 1];
		ClassJarTree t = this;
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
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return toString("");

	}
	
	public String toString(String tabs) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(tabs + "= " + group + name + FinalUtil.LINE_SEPARATOR);
		tabs += FinalUtil.TAB;
		for(String s : childs) {
			buffer.append(tabs + "-> " + s + FinalUtil.LINE_SEPARATOR);
		}
		for(Map.Entry<String, ClassJarTree> e : nexts.entrySet()) {
			buffer.append(e.getValue().toString(tabs));
		}
		return buffer.toString();
	}

	/* (non-Javadoc)
	 * @see net.aurore.system.ClassTree#getClass(java.lang.String)
	 */

	
	
}
