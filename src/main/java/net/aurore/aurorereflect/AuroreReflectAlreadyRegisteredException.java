/**
 * 
 */
package net.aurore.aurorereflect;

import java.net.URL;

/**
 * @author Pierre
 *
 */
public class AuroreReflectAlreadyRegisteredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7555048179537472319L;

	
	private URL url;
	
	/**
	 * 
	 */
	public AuroreReflectAlreadyRegisteredException(URL url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + ": URL \"" + url.toString() + "\" is already registered.";
	}
}
