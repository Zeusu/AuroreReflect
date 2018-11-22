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
public @interface ConfigClass {

	public String key();
		
	public PropertyFormat format() default PropertyFormat.FIELD_NAME;
	
}
