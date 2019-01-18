package net.aurore.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

class TestConfigManager {

	@Test
	void test() {
		ConfigParserManager parserManager = new ConfigParserManager(DefaultParser.PROPERTIES_PARSER);
		File f = new File("test.properties");
		try {
			parserManager.parse(f, TestConfig.class);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | IOException e) {
			e.printStackTrace();
		}

		System.out.println(ConfigManager.<TestConfig>get("key"));
	}

}
