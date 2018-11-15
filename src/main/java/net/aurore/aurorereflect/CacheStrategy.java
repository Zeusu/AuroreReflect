package net.aurore.aurorereflect;

/**
 * INSTANCE : The cache will be own by the AuroreReflect
 * PERSISTANT : The cache will survivre the AuroreReflect and is the same for all instances with this tag
 * */
public enum CacheStrategy {
	
	INSTANCE(false),
	PERSISTANT(true);
	
	private final boolean cache;
	
	private CacheStrategy(boolean b) {
		cache = b;
	}
	
	public boolean isCached() {
		return cache;
	}
	
}
