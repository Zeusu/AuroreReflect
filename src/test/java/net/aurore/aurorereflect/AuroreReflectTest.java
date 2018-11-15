package net.aurore.aurorereflect;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

@TestClass
public class AuroreReflectTest {

	@Test
	public void testReflect() {
		AuroreReflect r = new AuroreReflectImpl(CacheStrategy.PERSISTANT, ReflectStrategy.CLASSPATH);
		r.setPackagePath("net.aurore.aurorereflect");
		assertEquals(1,r.getTypeAnnotatedWith(TestClass.class).size());
	}

	@Test
	public void testReflectJar() {
	}	





}
