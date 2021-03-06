package net.aurore.aurorereflect;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.aurore.observable.Observable;
import net.aurore.observable.Observer;
import net.aurore.system.ClassFolderExpandingTree;
import net.aurore.system.ClassFolderTree;
import net.aurore.system.ClassJarExpandingTree;
import net.aurore.system.ClassJarTree;
import net.aurore.system.ClassTree;
import net.aurore.util.Escaping;
import net.aurore.util.FinalUtil;

/**
 * @author Zeusu
 *  Cache extends Observable, on add, will push the added URL to Observers 
 * */
public class Cache extends Observable<URL>{

	
	private static SecurityStrategy strategy = SecurityStrategy.CONTROLLABLE;
	
	protected static final Cache INSTANCE = new Cache();

	private Map<URL,ClassTree> trees = new HashMap<URL,ClassTree>();

	protected  Set<ClassTree> getContext(Set<URL> paths){
		Set<ClassTree> result = new HashSet<ClassTree>();
		for(URL url : paths) {
			result.add(getContext(url));
		}
		return result;
	}

	protected  ClassTree getContext(URL path){
		if(!trees.containsKey(path)) {
			try {
				File f = new File(Escaping.escape(path));
				if(f.isDirectory())
					trees.put(path, new ClassFolderTree(path));
				else if(f.getName().matches(FinalUtil.JAR_REGEX))
					trees.put(path, new ClassJarTree(path));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				//e.printStackTrace();
			}
		}
		return trees.get(path);
	}
	
	
	protected void expand(URL path) throws AuroreReflectAlreadyRegisteredException, FileNotFoundException {
		if(trees.containsKey(path)) throw new AuroreReflectAlreadyRegisteredException(path);
		if(path.toString().matches(FinalUtil.JAR_REGEX))
			trees.put(path, new ClassJarExpandingTree(path));
		else
			trees.put(path, new ClassFolderExpandingTree(path));
		update(path);
	}
	
	
	
	
	
	/**
	 * Set the security of the cache, security mest be on CONTROLLABLE to use
	 * */
	public static void setSecurity(SecurityStrategy strategy) {
		if(strategy.equals(SecurityStrategy.CONTROLLABLE))
			Cache.strategy = strategy;
		else
			throw new SecurityException(error());
	}
	
	/**
	 * Return a view of the cache, depending of the security level
	 * */
	public static CacheView getView() {
		if(strategy.equals(SecurityStrategy.CONTROLLABLE)) {
			return new CacheView() {

				@Override
				public void clear() {
					Cache.INSTANCE.trees.clear();
				}

				@Override
				public void refresh() {
					for(URL path : Cache.INSTANCE.trees.keySet()) {
						
						try {
							File f = new File(Escaping.escape(path));
							if(f.isDirectory())
								INSTANCE.trees.put(path, new ClassFolderTree(path));
							else if(f.getName().matches(FinalUtil.JAR_REGEX))
								INSTANCE.trees.put(path, new ClassJarTree(path));
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
				}

				@Override
				public ClassTree[] getTrees() {
					return Cache.getTrees();
				}

				@Override
				public void attach(Observer<URL> obs) {
					Cache.INSTANCE.attach(obs);
				}
			};
		}else if(strategy.equals(SecurityStrategy.VISIBLE)) {
			return new CacheView() {

				@Override
				public void clear() {
					throw new SecurityException(error());
				}

				@Override
				public void refresh() {
					throw new SecurityException(error());
				}

				@Override
				public ClassTree[] getTrees() {
					return Cache.getTrees();
				}

				@Override
				public void attach(Observer<URL> obs) {
					Cache.INSTANCE.attach(obs);
				}
			};
		}else {
			throw new SecurityException(error());
		}
	}
	

	private static String error() {
		return "Strategy is set to " + strategy.toString() + ".";
	}
	
	private static ClassTree[] getTrees() {
		Set<Entry<URL, ClassTree>> entries = INSTANCE.trees.entrySet();
		ClassTree[] result = new ClassTree[entries.size()];
		int i = 0;
		for(Entry<URL,ClassTree> e : entries) {
			result[i] = e.getValue();
			i++;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see net.aurore.observable.Observable#pushTo(net.aurore.observable.Observer)
	 */
	@Override
	public void pushTo(Observer<URL> obs, URL url) {
		obs.onUpdate(url);
	}

}
