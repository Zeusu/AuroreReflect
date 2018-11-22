/**
 * 
 */
package net.aurore.config;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * @author Zeusu
 *
 */
public @interface ConfigProperty {
	
	public static final String DEFAULT_NAME = "";

	public String name() default DEFAULT_NAME;
	
}
