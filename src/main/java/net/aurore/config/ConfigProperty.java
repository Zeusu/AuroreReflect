/**
 * 
 */
package net.aurore.config;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(ElementType.FIELD)
/**
 * @author Zeusu
 *
 */
public @interface ConfigProperty {
	
	public static final String DEFAULT_NAME = "";

	public String name() default DEFAULT_NAME;
	
}
