package com.kevinmiller.gradingsupport.fxgui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kevinmiller.gradingsupport.json.JSONInterpreter;
import com.kevinmiller.gradingsupport.json.JSONReader;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.scene.control.TabPane;

public class FXBaseApplication extends TabPane {

	private JSONObject configuration;
	private JSONArray sections;

	public FXBaseApplication() {
		ScreenHelper.loadFXML(this, this);
		try {
			configuration = JSONReader.loadConfiguration();
			initialize();
		} catch (JSONException e) {

		}
	}

	private void initialize() {
		JSONInterpreter.extractSections(configuration);
	}
}
