package org.smartwork.json.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigLoader {

	private static final Logger log = Logger.getLogger(ConfigLoader.class.getName());

	private static Properties config = new Properties();

	static {
		// loading resource using getResourceAsStream() method
		BufferedInputStream input = (BufferedInputStream) ConfigLoader.class.getClassLoader()
				.getResourceAsStream("resources.properties");
		if (input == null) {
			log.severe("Unable to locate properties file");
		} else {
			try {
				config.load(input);
			} catch (IOException ex) {
				log.severe(ex.getMessage());
			} finally {
				try {
					input.close();
				} catch (IOException ex) {
					log.severe(ex.getMessage());
				}
			}
		}

	}

	public static String getProperty(String name) {
		if (name != null) {
			return config.getProperty(name);
		} else
			return null;
	}

	public static Integer getPropertyAsInt(String name) {
		if (name != null) {
			return Integer.parseInt(config.getProperty(name));
		} else
			return null;
	}

	public static void main(String[] args) {
		log.info(getProperty("json.response.by.file"));
		log.info(getProperty("json.response.by.file.2"));
		log.info(getProperty(null));
		log.info(getProperty("json.url.timeout"));
	}
}
