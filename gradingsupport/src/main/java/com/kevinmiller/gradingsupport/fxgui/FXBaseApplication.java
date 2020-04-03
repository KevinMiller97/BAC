package com.kevinmiller.gradingsupport.fxgui;

import java.util.ArrayList;

import org.json.JSONException;

import com.kevinmiller.gradingsupport.fxgui.controls.Footer;
import com.kevinmiller.gradingsupport.fxgui.controls.Section;
import com.kevinmiller.gradingsupport.json.JSONReader;
import com.kevinmiller.gradingsupport.json.JSONWriter;
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

	private Footer footer;
	private ArrayList<Section> sections;

	public FXBaseApplication() {
		ScreenHelper.loadFXML(this, this);
		try {
			JSONReader.loadConfigurationAndInitializeApplication();
			while (!JSONReader.getSections().isPresent()) {
				// wait
			}
			sections = JSONReader.getSections().get();
			for (Section s : sections) {
				sectionPane.getTabs().add(new Tab(s.getTitle(), s));
			}
			footer = new Footer("Kevin Miller", "01638797");
			footer.setOnFinishButtonPressed(() -> {
				double points = 0;
				for (Section s : sections) {
					System.out.println(s.getTitle() + " " + s.getPoints());
					points += s.getPoints();
				}
				System.out.println(points);
				JSONWriter.generateSaveFile(sections);
			});

			footerWrapper.getChildren().add(footer);// TODO

		} catch (JSONException e) {

		}
	}
}
