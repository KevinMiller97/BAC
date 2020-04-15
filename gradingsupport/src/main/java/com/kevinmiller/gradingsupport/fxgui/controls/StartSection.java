package com.kevinmiller.gradingsupport.fxgui.controls;

import java.util.logging.Logger;

import com.kevinmiller.gradingsupport.json.JSONReader;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class StartSection extends TabPane {

	@FXML
	private Label nameLabel;

	@FXML
	private Label idLabel;

	@FXML
	private TextField nameField;

	@FXML
	private TextField idField;

	@FXML
	private Button enterButton;

	@FXML
	private Button fileChooserButton;

	private StringProperty studentName = new SimpleStringProperty("not entered");
	private StringProperty studentId = new SimpleStringProperty("not entered");

	final static Logger logger = Logger.getLogger(StartSection.class.getName());

	public StartSection() {
		ScreenHelper.loadFXML(this, this);

		enterButton.setOnAction(value -> {
			studentName.set(nameField.getText());
			nameLabel.setText(nameField.getText());
			try {
				// simple check for numbers only, needs to be 8 digits of numbers only
				Integer.parseInt(idField.getText());
				if (String.valueOf(idField.getText()).length() == 9) {
					throw new Exception();
				}
				// preserve original input
				studentId.set(idField.getText());
				idLabel.setText(idField.getText());
			} catch (Exception e) {
				idLabel.setText("Invalid!");
			}
			enterButton.setText("Change");
		});
		enterButton.setDefaultButton(true);

		fileChooserButton.setOnAction(value -> {
			JSONReader.initializeGUIContentFromFile();
		});
	}

	public StringProperty getStudentNameProperty() {
		return studentName;
	}

	public StringProperty getStudentIdProperty() {
		return studentId;
	}

	public String getStudentName() {
		return studentName.get();
	}

	public String getStudentId() {
		return studentId.get();
	}
}
