/**
 * 
 */
package net.aurore.aurorereflect;

import net.aurore.system.ClassTree;

/**
 * Allow you to manipulate the global cache
 * @author Zeusu
 *
 */
public interface CacheView {

	/**
	 * Clear the cache, remove all trees and classes
	 * */
	public void clear();
	
	/**
	 * Reload the trees, and so the files
	 * */
	public void refresh();
	
	
	/**
	 * return trees in the cache
	 * */
	public ClassTree[] getTrees();
	
}
