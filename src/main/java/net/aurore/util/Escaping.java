package net.aurore.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public final class Escaping {

	private static final String REGEX_SPACE = " ";
	private static final String URI_SPACE_ESCAPE = "%20";
	
	public static final URI escape(URL url) throws URISyntaxException {
		
		return new URI(url.toString().replaceAll(REGEX_SPACE, URI_SPACE_ESCAPE));
	}
	
	
	
	
}
