package com.vzt.framework.hpqc.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import com.vzt.framework.hpqc.logger.VzTLogger;

public class PropertyUtils {

	private final Properties myConfigProperties = new Properties();

	private PropertyUtils(final String aPropFile) {

		// String workingDir = System.getProperty("user.dir");
		InputStream systemResource = ClassLoader
				.getSystemResourceAsStream(aPropFile);
		// String file = systemResource.getFile();
		// InputStream in;
		try {
			// in = new FileInputStream(workingDir + aPropFile);
			// in = new FileInputStream(file);
			myConfigProperties.load(systemResource);
		} catch (FileNotFoundException fex) {
			VzTLogger.e("FileNotFoundException {}", fex.getMessage());
		} catch (IOException ioe) {
			VzTLogger.e("IOException {}", ioe.getMessage());
		}
	}

	public static PropertyUtils getInstance(final String propFile) {
		return new PropertyUtils(propFile);
	}

	public String getProperty(final String key) {
		return myConfigProperties.getProperty(key);
	}

	public Set<String> getAllPropertyNames() {
		return myConfigProperties.stringPropertyNames();
	}

	public boolean containsKey(final String key) {
		return myConfigProperties.containsKey(key);
	}

}
