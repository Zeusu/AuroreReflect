package net.aurore.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.aurore.util.FinalUtil;

public class ClassFolderTree implements ClassTree{

	private final String group;
	private final String name;
	private final List<String> childs;
	private final Map<String,ClassFolderTree> nexts;


	public ClassFolderTree(URL path) throws FileNotFoundException, URISyntaxException{
		this(new File(path.toURI()));
	}

	private ClassFolderTree(File f) throws FileNotFoundException, URISyntaxException{
		String path = f.getAbsolutePath();
		if(f.exists()) {
			int i;
			boolean found = false;
			for(i = path.length() - 1; i >= 0 && !found; i--) {
				if(path.charAt(i) == FinalUtil.PATH_SEPARATOR.charAt(0)) {
					found = true;
				}
			}
			name = path.substring(i+2,path.length());
			group = path.substring(0, path.length() - name.length()); 
			childs = new ArrayList<String>();
			nexts = new HashMap<String,ClassFolderTree>();
			for(String s : f.list()) {
				File child = new File(path + FinalUtil.PATH_SEPARATOR + s);
				if(child.isDirectory()) {
					nexts.put(s, new ClassFolderTree(child));
				}else {
					childs.add(s);
				}
			}
		}else {
			throw new FileNotFoundException();
		}
	}

	@Override
	public Set<Class<?>> getClasses(String path) {
		Set<Class<?>> result = new HashSet<Class<?>>();
		String[] splittedPath = path.split(SEPARATOR);
		ClassFolderTree t = this; 
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
		ClassFolderTree t = this;
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
		for(Map.Entry<String, ClassFolderTree> e : nexts.entrySet()) {
			buffer.append(e.getValue().toString(tabs));
		}
		return buffer.toString();
	}


	public static void main(String[] args) {
		try {
			ClassFolderTree t = new ClassFolderTree(ClassLoader.getSystemClassLoader().getResource("./"));
			System.out.println(t.toString());
			Set<Class<?>> result = t.getClasses("net.aurore.util");
			for(Class<?> c: result) {
				System.out.println(c.getName());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}


}
