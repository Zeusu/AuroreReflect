/**
 * 
 */
package net.aurore.config;

import java.io.File;
import java.io.FileNotFoundException;

import net.aurore.system.Directory;

/**
 * @author Pierre
 *
 */
public interface ConfigLoader {

	/**
	 * @param file
	 * @throws FileNotFoundException
	 */
	public void load(File file) throws FileNotFoundException;

	/**
	 * @param directory
	 * @throws FileNotFoundException
	 */
	public void load(Directory directory) throws FileNotFoundException;
	
}
