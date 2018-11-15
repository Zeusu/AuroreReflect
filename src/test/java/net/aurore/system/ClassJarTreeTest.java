package net.aurore.system;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.junit.jupiter.api.Test;

import net.aurore.util.FinalUtil;

class ClassJarTreeTest {

	@Test
	void test() {
		URL url = null;
		for(URL u : Classpath.INSTANCE) {
			if(u.toString().matches(FinalUtil.JAR_REGEX))
				url = u;
		}
		System.out.println("- " + url);
		try {
			ClassTree t = new ClassJarTree(url);
			System.out.println(t.toString());
			Set<Class<?>> classes = t.getClasses("org.hamcrest.core");
			assertTrue(classes.size() > 1);
			System.out.println(classes.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	void testExpand() {
		File f = new File("AuroreThreads.jar");
		try {
			URL url = f.toURI().toURL();
			System.out.println(url);
			ClassTree t = new ClassJarExpandingTree(url);
			System.out.println(t.toString());
			System.out.println(t.getClasses("net.aurore.thread"));
			System.out.println(t.getClass("net.aurore.thread.Task"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
