package com.kevinmiller.gradingsupport.json;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class JSONReader {

	static final String configLocation = "/config/appconfig.json";

	// TODO add analysis and filter to the read configuration, might return controls
	// later
	public static JSONObject loadConfiguration() { // ArrayList<Segment>
		try {
			String configRead = IOUtils.toString(JSONReader.class.getResourceAsStream(configLocation), "UTF-8");
			System.out.println(configRead);
			JSONObject configuration = new JSONObject(configRead);
			return configuration;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(e.getClass().getSimpleName());
		}
		return null;
	}
}