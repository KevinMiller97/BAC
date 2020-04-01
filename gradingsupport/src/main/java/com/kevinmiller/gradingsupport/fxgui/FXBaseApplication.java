package com.kevinmiller.gradingsupport.fxgui;

import java.util.ArrayList;

import org.json.JSONException;

import com.kevinmiller.gradingsupport.fxgui.controls.Section;
import com.kevinmiller.gradingsupport.json.JSONInterpreter;
import com.kevinmiller.gradingsupport.json.JSONReader;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

public class FXBaseApplication extends StackPane {

	@FXML
	private StackPane footerWrapper;

	@FXML
	private TabPane sectionPane;

	public FXBaseApplication() {
		ScreenHelper.loadFXML(this, this);
		try {
			JSONReader.loadConfigurationAndInitializeApplication();
			while (!JSONInterpreter.getSections().isPresent()) {
				// wait
			}
			ArrayList<Section> sections = JSONInterpreter.getSections().get();
			for (Section s : sections) {
				sectionPane.getTabs().add(new Tab(s.getTitle(), s));
			}

			footerWrapper.getChildren().add(new Footer("Kevin Miller", "01638797"));// TODO

		} catch (JSONException e) {

		}
	}
}
