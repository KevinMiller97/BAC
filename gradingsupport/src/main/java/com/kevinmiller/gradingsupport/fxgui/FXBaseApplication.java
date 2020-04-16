package com.kevinmiller.gradingsupport.fxgui;

import java.util.ArrayList;

import org.json.JSONException;

import com.kevinmiller.gradingsupport.calc.CalculationParser;
import com.kevinmiller.gradingsupport.fxgui.controls.IWorkedOn;
import com.kevinmiller.gradingsupport.fxgui.controls.footer.Footer;
import com.kevinmiller.gradingsupport.fxgui.controls.section.Section;
import com.kevinmiller.gradingsupport.fxgui.controls.section.StartSection;
import com.kevinmiller.gradingsupport.fxgui.controls.section.SuperSection;
import com.kevinmiller.gradingsupport.json.JSONReader;
import com.kevinmiller.gradingsupport.json.JSONWriter;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

public class FXBaseApplication extends StackPane implements IWorkedOn {

	@FXML
	private StackPane footerWrapper;

	@FXML
	private TabPane sectionPane;

	private Footer footer;
	private ArrayList<Section> sections;
	private StartSection startSection;

	public FXBaseApplication(boolean fromFile) {
		ScreenHelper.loadFXML(this, this);
		try {
			if (!fromFile)
				JSONReader.loadConfigurationAndInitializeApplication(null);

			startSection = new StartSection();
			sectionPane.getTabs().clear();
			sectionPane.getTabs().add(new Tab("Start", startSection));

			while (!JSONReader.getSections().isPresent()) {
				// wait
			}
			sections = JSONReader.getSections().get();
			for (Section s : sections) {
				sectionPane.getTabs().add(new Tab(s.getTitle(), s));
			}

			addListeners();

			footer = new Footer(startSection.getStudentNameProperty(), startSection.getStudentIdProperty());
			footer.setOnFinishButtonPressed(() -> {
				for (Section s : sections) {
					if (s instanceof SuperSection) {
						System.out.println(CalculationParser.calculatePointsGlobalIdentifier(s.getFormula(),
								s.getSubNodes(), ((SuperSection) s).getSections()));
						JSONWriter.generateSaveFile(sections, startSection.getStudentName(),
								startSection.getStudentId());
					}
				}
			});
			footerWrapper.getChildren().add(footer);
		} catch (JSONException e) {
			// TODO
		}
	}

	public StartSection getStartSection() {
		return startSection;
	}

	@Override
	public void addListeners() {
		for (Section s : sections) {
			s.getWorkedOnProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					System.err.println("here");
					sectionPane.getSelectionModel().selectNext();
				}
			});
		}
	}

	@Override
	public boolean validateWorkedOnPropertiesOfChildNodes() {
		return false;
	}

	@Override
	public BooleanProperty getWorkedOnProperty() {
		// has no more parents
		return null;
	}

}
