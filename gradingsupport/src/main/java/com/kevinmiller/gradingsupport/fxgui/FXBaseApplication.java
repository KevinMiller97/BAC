package com.kevinmiller.gradingsupport.fxgui;

import java.util.ArrayList;

import org.json.JSONException;

import com.kevinmiller.gradingsupport.fxgui.controls.Section;
import com.kevinmiller.gradingsupport.json.JSONInterpreter;
import com.kevinmiller.gradingsupport.json.JSONReader;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class FXBaseApplication extends TabPane {

	public FXBaseApplication() {
		ScreenHelper.loadFXML(this, this);
		try {
			JSONReader.loadConfigurationAndInitializeApplication();
			while (!JSONInterpreter.getSections().isPresent()) {
			}
			ArrayList<Section> sections = JSONInterpreter.getSections().get();
			for (Section s : sections) {
				getTabs().add(new Tab(s.getTitle(), s));
			}
		} catch (JSONException e) {

		}
	}
}
