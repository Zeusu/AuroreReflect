/**
 * 
 */
package net.aurore.observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation of Observer/Observale
 * @author Zeusu
 * 
 */
public abstract class Observable<E> {

	private List<Observer<E>> observers = new ArrayList<>();
	
	/**
	 * Discribe how the push will act
	 * */
	public abstract void pushTo(Observer<E> obs, E e);
	
	public void update(E e) {
		for(Observer<E> obs : observers) {
			pushTo(obs, e);
		}
	}
	
	public boolean attach(Observer<E> obs) {
		if(!observers.contains(obs)) {
			observers.add(obs);
			return true;
		}
		return false;
	}
	
	public boolean detach(Observer<E> obs) {
		return observers.remove(obs);
	}
	
}
