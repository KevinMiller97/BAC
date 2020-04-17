package com.kevinmiller.gradingsupport.fxgui;

import java.util.ArrayList;

import org.json.JSONException;

import com.kevinmiller.gradingsupport.calc.CalculationParser;
import com.kevinmiller.gradingsupport.fxgui.controls.IWorkedOn;
import com.kevinmiller.gradingsupport.fxgui.controls.footer.Footer;
import com.kevinmiller.gradingsupport.fxgui.controls.section.Section;
import com.kevinmiller.gradingsupport.fxgui.controls.section.StartSection;
import com.kevinmiller.gradingsupport.fxgui.controls.section.SuperSection;
import com.kevinmiller.gradingsupport.fxgui.controls.segment.FeedbackArea;
import com.kevinmiller.gradingsupport.fxgui.controls.segment.FinalOverview;
import com.kevinmiller.gradingsupport.fxgui.controls.segment.FinalOverviewContent;
import com.kevinmiller.gradingsupport.fxgui.controls.segment.FinalOverviewEntry;
import com.kevinmiller.gradingsupport.fxgui.controls.segment.Segment;
import com.kevinmiller.gradingsupport.fxgui.controls.subpoint.SubPoint;
import com.kevinmiller.gradingsupport.json.JSONReader;
import com.kevinmiller.gradingsupport.json.JSONWriter;
import com.kevinmiller.gradingsupport.stagecontroller.UserScreen;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Separator;
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
				addFinalOverview();
			});
			footerWrapper.getChildren().add(footer);
		} catch (JSONException e) {
			// TODO
		}
	}

	private void addFinalOverview() {
		FinalOverview.setEdited(false);
		ArrayList<Node> overviewContent = new ArrayList<Node>();
		for (Section s : sections) {
			for (Segment seg : s.getSubNodes()) {
				overviewContent.add(new FinalOverviewEntry(seg.getTitle()));
				for (SubPoint sp : seg.getSegmentContent().getSubPoints()) {
					overviewContent.add(new FinalOverviewEntry(sp.getTitle(), sp.getSelectedEntry().getTitle()));
				}
				overviewContent.add(new Separator());
			}
		}
		overviewContent.add(new FinalOverviewEntry("Feedback"));
		overviewContent.add(new FeedbackArea());

		FinalOverview overview = new FinalOverview("Overview", new FinalOverviewContent(overviewContent));
		sectionPane.getTabs().add(overview);
		sectionPane.getSelectionModel().select(overview);

		footer.setOnFinishButtonPressed(() -> {
			if (FinalOverview.wasEdited()) {
				// generate new final overview because something was changed in the grading part
				sectionPane.getTabs().remove(overview);
				addFinalOverview();
				FinalOverview.setEdited(false);
			} else {
				for (Section s : sections) {
					if (s instanceof SuperSection) {
						System.out.println(CalculationParser.calculatePointsGlobalIdentifier(s.getFormula(),
								s.getSubNodes(), ((SuperSection) s).getSections()));
						JSONWriter.generateSaveFile(sections, startSection.getStudentFirstName(),
								startSection.getStudentLastName(), startSection.getStudentId());
						// new blank application
						UserScreen.reload(JSONReader.getDefaultConfiguration());
					}
				}
			}
		});
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
					selectNext();
				}
			});
		}
	}

	private void selectNext() {
		sectionPane.getSelectionModel().selectNext();
		((Section) sectionPane.getSelectionModel().getSelectedItem().getContent()).getSubNodes().get(0)
				.focusFirstSubPoint();
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
