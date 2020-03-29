package com.kevinmiller.gradingsupport.fxgui.controls;

import java.util.ArrayList;

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

	public SegmentContent(ArrayList<SubPoint> subPoints) {
		this(subPoints, null);
	}

	public SegmentContent(ArrayList<SubPoint> subPoints, String hint) {
		ScreenHelper.loadFXML(this, this);
		this.subPoints = subPoints;
		if (hint != null)
			hintLabel.setText(hint);
		else
			hintLabel.setVisible(false);
		content.getChildren().addAll(subPoints);
	}

	public double getPoints() {
		double points = 0;
		for (SubPoint s : subPoints) {
			points += s.getPoints();
		}
		return points;
	}
}
