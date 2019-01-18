package net.aurore.system;

import java.io.File;
import java.net.URI;

public class Directory extends File{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7927306330946071812L;

	public Directory(String pathname) {
		super(pathname);
		if(!this.isDirectory()) throw new NotADirectoryException(this.getAbsolutePath());
	}
	
	public Directory(URI uri) {
		super(uri);
		if(!this.isDirectory()) throw new NotADirectoryException(this.getAbsolutePath());
	}
	
	
}
