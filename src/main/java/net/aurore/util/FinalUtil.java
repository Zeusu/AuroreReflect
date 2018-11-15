package net.aurore.util;

public final class FinalUtil {
	
	
	private static final String WINDOWS = "win(.*)";
	
	public static final String PATH_SEPARATOR = System.getProperty("file.separator");
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	public static final String SYSTEM_PATH_SEPARATOR = System.getProperty("path.separator");
	
	public static final String TAB = "\t";
	
	public static final String CLASS_REGEX = "(.)*\\.class";
	
	public static final String JAR_REGEX = "(.)*\\.jar";
	
	private static final String PROTOCOLE_FILE_WINDOWS = "file:/";
	
	private static final String PROTOCOLE_FILE_UNIX = "file:";
	
	public static final String PROTOCOLE_FILE;
	
	static {
		if(System.getProperty("os.name").toLowerCase().matches(WINDOWS)) {
			PROTOCOLE_FILE = PROTOCOLE_FILE_WINDOWS;
		}else {
			PROTOCOLE_FILE = PROTOCOLE_FILE_UNIX;
		}
	}
	
}
