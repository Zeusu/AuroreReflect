package net.aurore.system;

import java.net.URL;
import java.util.Iterator;


public final class ClasspathIterator implements Iterator<URL> {

	private static final String REMOVE = "Can't remove a value from classpath";

	private Iterator<URL> it;

	protected ClasspathIterator(Iterator<URL> it) {
		this.it = it; 
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public URL next() {
		return it.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(REMOVE);
	}



}
