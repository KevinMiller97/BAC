package com.kevinmiller.gradingsupport.fxgui.controls.section;

import java.util.ArrayList;

import com.kevinmiller.gradingsupport.calc.CalculationParser;
import com.kevinmiller.gradingsupport.calc.ICalculatePoints;
import com.kevinmiller.gradingsupport.fxgui.controls.IWorkedOn;
import com.kevinmiller.gradingsupport.fxgui.controls.segment.Segment;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TabPane;

/**
 * A {@code Section} condenses various
 * {@link com.kevinmiller.gradingsupport.fxgui.controls.segment.Segment#Segment
 * Segments} into a single unit, displayed as a tab in the
 * {@link com.kevinmiller.gradingsupport.fxgui.FXBaseApplication#FXBaseApplication
 * Base application}.
 * 
 * @author Kevin Miller
 */
public class Section extends TabPane implements ICalculatePoints, IWorkedOn {

	protected String title;
	protected ArrayList<Segment> segments;
	protected String pointsFormula = "";
	protected String identifier;
	private BooleanProperty fullyWorkedOn = new SimpleBooleanProperty(false);

	public Section() {
	}

	public Section(String title, ArrayList<Segment> segments, String pointsFormula, String identifier) {
		ScreenHelper.loadFXML(this, this);
		this.title = title;
		this.segments = segments;
		this.pointsFormula = pointsFormula;
		this.identifier = identifier;
		getTabs().addAll(segments);
		addListeners();
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName() + " " + title + ": ");
		if (segments.size() > 0) {
			sb.append(segments.get(0).getClass().getSimpleName() + (segments.size() > 1 ? "s: " : ":"));
			for (int i = 0; i < segments.size(); ++i) {
				sb.append(segments.get(i).getTitle() + (i < segments.size() - 1 ? ", " : ""));
			}
		}
		return sb.toString();
	}

	public double getPoints() {
		return CalculationParser.calculatePoints(pointsFormula, segments);
	}

	public ArrayList<Segment> getSubNodes() {
		return segments;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public String getFormula() {
		return pointsFormula;
	}

	@Override
	public void addListeners() {
		for (Segment s : segments) {
			s.getWorkedOnProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					selectNext();
					// all segments fully worked on
					if (validateWorkedOnPropertiesOfChildNodes()) {
						fullyWorkedOn.set(true);
					}
				}
			});
		}
	}

	private void selectNext() {
		getSelectionModel().selectNext();
		((Segment) getSelectionModel().getSelectedItem()).focusFirstSubPoint();
	}

	@Override
	public boolean validateWorkedOnPropertiesOfChildNodes() {
		for (Segment s : segments)
			if (!s.getWorkedOnProperty().getValue())
				return false;
		return true;
	}

	@Override
	public BooleanProperty getWorkedOnProperty() {
		return fullyWorkedOn;
	}

}
