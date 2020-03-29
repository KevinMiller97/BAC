package com.kevinmiller.gradingsupport.json;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.kevinmiller.gradingsupport.utility.ScreenHelper;

public final class JSONReader {

	static final String configLocation = "/config/appconfig.json";
	final static Logger logger = Logger.getLogger(ScreenHelper.class.getName());

	public static void loadConfigurationAndInitializeApplication() throws JSONException {
		ScreenHelper.configureLogger(logger);
		try {
			String configRead = IOUtils.toString(JSONReader.class.getResourceAsStream(configLocation), "UTF-8");
			JSONInterpreter.initializeGUIContent(new JSONObject(configRead));
			return;
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getClass().getSimpleName(), e);
		}
		logger.log(Level.SEVERE, "Could not load configuration, stopping Application");
		System.exit(1);
	}
}