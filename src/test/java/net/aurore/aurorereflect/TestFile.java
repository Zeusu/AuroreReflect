package net.aurore.aurorereflect;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.Test;

import net.aurore.system.Classpath;
import net.aurore.util.Escaping;

class TestFile {

	@Test
	void test() {
	
		for(URL url : Classpath.INSTANCE){
			try {
				System.out.println(Escaping.escape(url));
				System.out.println(new File(Escaping.escape(url)).exists());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	
	
	}

}
