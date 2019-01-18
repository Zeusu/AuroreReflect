/**
 * 
 */
package net.aurore.system;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.aurore.util.FinalUtil;

/**
 * @author Zeusu
 *
 */
public abstract class AbstractClassTree implements ClassTree{

	protected String group;
	protected String name;
	protected List<String> childs;
	protected Map<String,AbstractClassTree> nexts;
	
	
	protected void getClasses(Set<Class<?>> result, String path) throws ClassNotFoundException {
		for(String s : childs) {
			String className = path  + "." + s.substring(0, s.length() - CLASS_KEYWORD_LENGTH);
			result.add(Class.forName(className));
		}
		for(AbstractClassTree t : nexts.values()) {
			t.getClasses(result, path + "." + t.name);
		}
	}
	
	protected void getExpandingClasses(Set<Class<?>> result, String path, ClassLoader cl) throws ClassNotFoundException {
		for(String s : childs) {
			String className = path  + "." + s.substring(0, s.length() - CLASS_KEYWORD_LENGTH);
			result.add(cl.loadClass(className));
		}
		for(AbstractClassTree t : nexts.values()) {
			t.getClasses(result, path + "." + t.name);
		}
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
		for(Map.Entry<String, AbstractClassTree> e : nexts.entrySet()) {
			buffer.append(e.getValue().toString(tabs));
		}
		return buffer.toString();
	}

	
}
