package com.kevinmiller.gradingsupport.fxgui.controls.segment;

import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FinalOverviewEntry extends VBox {

	@FXML
	private Label topicLabel;

	@FXML
	private Label selectedEntryLabel;

	public FinalOverviewEntry(String topic, String selectedEntry) {
		ScreenHelper.loadFXML(this, this);
		topicLabel.setText(topic);
		selectedEntryLabel.setText(selectedEntry);
	}

	public FinalOverviewEntry(String headline) {
		ScreenHelper.loadFXML(this, this);
		topicLabel.setText(headline);
		topicLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
		getChildren().remove(selectedEntryLabel);
		setPrefHeight(15);
	}

}
