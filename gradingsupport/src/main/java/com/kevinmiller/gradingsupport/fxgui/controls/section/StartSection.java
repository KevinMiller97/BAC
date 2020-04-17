package com.kevinmiller.gradingsupport.fxgui.controls.section;

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
	private Label firstNameLabel;

	@FXML
	private Label lastNameLabel;

	@FXML
	private Label idLabel;

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private TextField idField;

	@FXML
	private Button enterButton;

	@FXML
	private Button fileChooserButton;

	@FXML
	private Button generateCSVButton;

	private StringProperty studentName = new SimpleStringProperty("not entered");
	private StringProperty studentId = new SimpleStringProperty("not entered");

	private String studentFirstName = "not entered";
	private String studentLastName = "not entered";

	final static Logger logger = Logger.getLogger(StartSection.class.getName());

	public StartSection() {
		ScreenHelper.loadFXML(this, this);

		enterButton.setOnAction(value -> {
			studentName.set(firstNameField.getText() + " " + lastNameField.getText());

			studentFirstName = firstNameField.getText();
			firstNameLabel.setText(firstNameField.getText());

			studentLastName = lastNameField.getText();
			lastNameLabel.setText(lastNameField.getText());
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

		generateCSVButton.setOnAction(value -> {
			JSONReader.generateCSVFromMultipleFiles();
		});
	}

	public StringProperty getStudentNameProperty() {
		return studentName;
	}

	public StringProperty getStudentIdProperty() {
		return studentId;
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}

	public String getStudentLastName() {
		return studentLastName;
	}

	public String getStudentId() {
		return studentId.get();
	}
}
