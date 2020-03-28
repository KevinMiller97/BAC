package com.kevinmiller.gradingsupport.fxgui.controls;

import java.util.ArrayList;

import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SegmentContent extends AnchorPane {

	@FXML
	private VBox content;

	public SegmentContent(ArrayList<SubPoint> subPoints) {
		ScreenHelper.loadFXML(this, this);
		content.getChildren().addAll(subPoints);
	}
}
