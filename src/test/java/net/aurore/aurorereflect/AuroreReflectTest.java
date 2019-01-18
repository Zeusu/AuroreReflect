package net.aurore.aurorereflect;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

@TestClass
public class AuroreReflectTest {

	@Test
	public void testReflect() {
		AuroreReflect r = new AuroreReflectImpl(CacheStrategy.PERSISTANT, ReflectStrategy.CLASSPATH);
		//r.setPackagePath("net.aurore.aurorereflect");
		assertEquals(1,r.getTypeAnnotatedWith("net.aurore.aurorereflect", TestClass.class).size());
		Set<Class<?>> classes = r.getClasses("net.aurore");
		System.out.println(classes.size());
		System.out.println(classes);

	}

	@Test
	public void testReflectJar() {
	}	





}
