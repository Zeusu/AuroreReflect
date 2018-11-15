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

	public void clear();
	
	public void refresh();
	
	public ClassTree[] getTrees();
	
}
