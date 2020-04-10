package com.kevinmiller.gradingsupport.fxgui.controls;

import java.util.ArrayList;

import com.kevinmiller.gradingsupport.calc.CalculationParser;
import com.kevinmiller.gradingsupport.calc.ICalculatePoints;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.scene.control.TabPane;

/**
 * A {@code Section} condenses various
 * {@link com.kevinmiller.gradingsupport.fxgui.controls.Segment#Segment
 * Segments} into a single unit, displayed as a tab in the
 * {@link com.kevinmiller.gradingsupport.fxgui.FXBaseApplication#FXBaseApplication
 * Base application}.
 * 
 * @author Kevin Miller
 */
public class Section extends TabPane implements ICalculatePoints {

	private String title;
	private ArrayList<Segment> segments;
	private String pointsFormula = "";

	public Section(String title, ArrayList<Segment> segments, String pointsFormula) {
		ScreenHelper.loadFXML(this, this);
		this.title = title;
		this.segments = segments;
		this.pointsFormula = pointsFormula;
		getTabs().addAll(segments);
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

	public ArrayList<Segment> getSegments() {
		return segments;
	}

}
