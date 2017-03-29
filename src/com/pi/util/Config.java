package com.pi.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Config {
	public static boolean visibility = true;
	static Properties configProperties = new Properties();
	static String configFilePath = "./config.properties";
	private static final Logger logger = Logger.getLogger("piclientLogger");

	private static void loadBundle() {
		try {
			// ./ is the root of the config.properties file
			InputStream input = Config.class.getClassLoader().getResourceAsStream(configFilePath);
			configProperties.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Key access
	public static String getKey(String key) {
		if (configProperties.isEmpty())
			loadBundle();
		return configProperties.getProperty(key);
	}

	public static String getEnvKey(String key) {
		if (configProperties.isEmpty())
			loadBundle();
		String environment = configProperties.getProperty("env");
							//System.getenv("ENV");
		logger.info("<<<<Environment::"+environment);
		if(environment == null) environment = "LOCAL";
		return configProperties.getProperty(environment + "_" + key);
	}

	public static void main(String[] args) {
		System.out.println(System.getenv("ENV"));
	}
}
