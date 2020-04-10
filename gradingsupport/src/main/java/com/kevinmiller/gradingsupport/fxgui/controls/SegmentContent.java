package com.kevinmiller.gradingsupport.fxgui.controls;

import java.util.ArrayList;

import com.kevinmiller.gradingsupport.calc.CalculationParser;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SegmentContent extends AnchorPane {

	@FXML
	private VBox content;

	@FXML
	private Label hintLabel;

	private final ArrayList<SubPoint> subPoints;
	private String hint;
	private String pointsFormula = "";

	public SegmentContent(ArrayList<SubPoint> subPoints) {
		this(subPoints, null, "");
	}

	public SegmentContent(ArrayList<SubPoint> subPoints, String hint, String pointsFormula) {
		ScreenHelper.loadFXML(this, this);
		this.subPoints = subPoints;
		this.hint = hint;
		this.pointsFormula = pointsFormula;
		if (hint != null)
			hintLabel.setText(hint);
		else
			hintLabel.setVisible(false);
		content.getChildren().addAll(subPoints);

	}

	public double getPoints() {
		return CalculationParser.calculatePoints(pointsFormula, subPoints);
	}

	public ArrayList<SubPoint> getSubPoints() {
		return subPoints;
	}

	public String getFormula() {
		return pointsFormula;
	}

	public String getHint() {
		return hint;
	}

}
