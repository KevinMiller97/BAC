package com.kevinmiller.gradingsupport.fxgui;

import java.util.ArrayList;

import org.json.JSONException;

import com.kevinmiller.gradingsupport.calc.CalculationParser;
import com.kevinmiller.gradingsupport.calc.ICalculatePoints;
import com.kevinmiller.gradingsupport.fxgui.controls.Footer;
import com.kevinmiller.gradingsupport.fxgui.controls.Section;
import com.kevinmiller.gradingsupport.fxgui.controls.StartSection;
import com.kevinmiller.gradingsupport.json.JSONReader;
import com.kevinmiller.gradingsupport.json.JSONWriter;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

public class FXBaseApplication extends StackPane implements ICalculatePoints {

	@FXML
	private StackPane footerWrapper;

	@FXML
	private TabPane sectionPane;

	private Footer footer;
	private ArrayList<Section> sections;
	private StartSection startSection;
	private String pointsFormula = ""; // TODO

	public FXBaseApplication() {
		ScreenHelper.loadFXML(this, this);
		try {
			JSONReader.loadConfigurationAndInitializeApplication();
			startSection = new StartSection();
			sectionPane.getTabs().add(new Tab("Start", startSection));

			while (!JSONReader.getSections().isPresent()) {
				// wait
			}
			sections = JSONReader.getSections().get();
			for (Section s : sections) {
				sectionPane.getTabs().add(new Tab(s.getTitle(), s));
			}

			footer = new Footer(startSection.getStudentNameProperty(), startSection.getStudentIdProperty());
			footer.setOnFinishButtonPressed(() -> {
				System.out.println(CalculationParser.calculatePoints(pointsFormula, sections));
				JSONWriter.generateSaveFile(sections, startSection.getStudentName(), startSection.getStudentId());
			});

			footerWrapper.getChildren().add(footer);// TODO

		} catch (JSONException e) {

		}
	}

	// TODO
	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public double getPoints() {
		return 0;
	}
}
