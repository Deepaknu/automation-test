package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadConfigFile {
	private static Properties properties;

	public static synchronized Properties getInstance() {
		if (properties == null) {
			loadFromPropertiesFile();
		}
		return properties;
	}

	private static void loadFromPropertiesFile() {
		properties = new Properties();
		try {
			properties.load(new FileInputStream("GlobalSettings.properties"));
		} catch (FileNotFoundException var2) {
			System.out.println("FileNotFoundException while loading the Global Settings file");
		} catch (IOException var3) {
			System.out.println("IOException while loading the Global Settings file.");
		}
	}

	public static String getValue(String propertyName) {
		try {
			return properties.getProperty(propertyName).trim();
		} catch (Exception e) {
			return "";
		}

	}

}
