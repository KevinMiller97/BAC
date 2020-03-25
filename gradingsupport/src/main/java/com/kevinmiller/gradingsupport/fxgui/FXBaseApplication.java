package com.kevinmiller.gradingsupport.fxgui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kevinmiller.gradingsupport.json.JSONReader;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.scene.control.TabPane;

public class FXBaseApplication extends TabPane {

	private JSONObject configuration;

	public FXBaseApplication() {
		ScreenHelper.loadFXML(this, this);
		try {
			configuration = JSONReader.loadConfiguration();
			loadParts();
		} catch (JSONException e) {

		}
	}

	private void initialize() {

	}

	private void loadParts() { // TODO define names for grouping, segments in separate prop file
		JSONArray parts = configuration.getJSONArray("parts");
	}

}
