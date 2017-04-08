package com.hippolee.app.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesManager {

	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(PropertiesManager.class);

	private static final String APP_PROPERTY_PATH = "config/app.properties";

	private static final PropertiesManager instance = new PropertiesManager();

	private Properties properties;

	private PropertiesManager() {
		loadAppProperties();
	}

	public static PropertiesManager getInstance() {
		return instance;
	}

	private Properties loadAppProperties() {
		properties = new Properties();
		try {
			File file = new File(APP_PROPERTY_PATH);
			if (!file.exists()) {
				File folder = file.getParentFile();
				if (!folder.exists()) {
					folder.mkdirs();
				}
				file.createNewFile();
			}

			properties.load(new FileInputStream(file));
		} catch (IOException e) {
			logger.error("load app properties error!", e);
		}
		return properties;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return this.properties.getProperty(key, defaultValue);
	}

	public void setProperty(String key, String value) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(APP_PROPERTY_PATH, false);
			this.properties.setProperty(key, value);
			this.properties.store(os, "Updata " + key + ":" + value);
		} catch (Exception e) {
			logger.error("store app properties error!", e);
		}
	}

	public void removeProperty(String key) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(APP_PROPERTY_PATH, true);
			this.properties.remove(key);
			this.properties.store(os, "Remove " + key);
		} catch (Exception e) {
			logger.error("store app properties error!", e);
		}
	}

}
