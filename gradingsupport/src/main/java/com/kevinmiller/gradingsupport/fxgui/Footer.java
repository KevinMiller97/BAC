package com.kevinmiller.gradingsupport.fxgui;

import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Footer extends HBox {

	@FXML
	private Label studentNameLabel;

	@FXML
	private Label studentIdLabel;

	@FXML
	private Button finishButton;

	public Footer(String studentName, String studentId) {
		ScreenHelper.loadFXML(this, this);
		studentNameLabel.setText(studentName);
		studentIdLabel.setText(studentId);
	}

}
