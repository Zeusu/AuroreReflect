/**
 * 
 */
package net.aurore.observable;

/**
 * @author Zeusu
 *
 */
public interface Observer<T> {

	public void onUpdate(T event);
	
}
