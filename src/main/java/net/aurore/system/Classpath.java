package net.aurore.system;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.aurore.util.FinalUtil;

/**
 * Represent the Classpath as an object. Singleton immutable and unextandable
 * @author Zeusu
 * 
 * */
public final class Classpath implements Iterable<URL>{

	public static final Classpath INSTANCE = new Classpath();
	
	private static final String SYSTEM_PROPERTY_CLASSPATH = "java.class.path";
	
	private final String cp;
	private final Set<URL> pathes;
	
	private Classpath() {
		cp = System.getProperty(SYSTEM_PROPERTY_CLASSPATH);
		pathes = new HashSet<URL>();
		parse();
	}
	
	private void parse(){
		String[] pathes = cp.split(FinalUtil.SYSTEM_PATH_SEPARATOR);
		for(String s: pathes) {
			try {
				this.pathes.add(new URL(FinalUtil.PROTOCOLE_FILE + s));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Iterator<URL> iterator() {
		return new ClasspathIterator(pathes.iterator());
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for(URL u : pathes) {
			buffer.append(u.toString());
			buffer.append(FinalUtil.LINE_SEPARATOR);
		}
		return buffer.toString();
	}
}
