package com.kevinmiller.gradingsupport.fxgui.controls;

import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class Footer extends HBox {

	@FXML
	private Label studentNameLabel;

	@FXML
	private Label studentIdLabel;

	@FXML
	private TextField studentNameField;

	@FXML
	private TextField studentIdField;

	@FXML
	private Button finishButton;

	@FXML
	private Button enterButton;

	public Footer() {
		ScreenHelper.loadFXML(this, this);
		studentNameLabel.setText("Name");
		studentIdLabel.setText("Matriculation nr.");
		enterButton.setOnAction(value -> {
			studentNameLabel.setText(studentNameField.getText());
			try {
				// simple check for numbers only
				Integer.parseInt(studentIdField.getText());
				// preserve original input
				studentIdLabel.setText(studentIdField.getText());
			} catch (Exception e) {
				studentIdLabel.setText("Invalid!");
			}
			getChildren().remove(studentNameField);
			getChildren().remove(studentIdField);
			enterButton.setText("Change");
		});
	}

	public String generateFileName() {
		return getStudentName().trim().replaceAll("\\s+", "_") + "_" + getStudentId().trim();
	}

	public String getStudentName() {
		return studentNameLabel.getText();
	}

	public String getStudentId() {
		return studentIdLabel.getText();
	}

	public void setOnFinishButtonPressed(Runnable buttonPress) {
		finishButton.setOnAction(value -> buttonPress.run());
	}

}
