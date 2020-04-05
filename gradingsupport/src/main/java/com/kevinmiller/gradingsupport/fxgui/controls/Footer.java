package com.kevinmiller.gradingsupport.fxgui.controls;

import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Footer extends HBox {

	@FXML
	private Label nameLabel;

	@FXML
	private Label idLabel;

	@FXML
	private Button finishButton;

	public Footer(StringProperty studentName, StringProperty studentId) {
		ScreenHelper.loadFXML(this, this);
		studentName.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				setNameLabel(newValue);
			}
		});
		studentId.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				setIdLabel(newValue);
			}
		});
	}

	public void setNameLabel(String name) {
		nameLabel.setText(name);
	}

	public void setIdLabel(String id) {
		idLabel.setText(id);
	}

	public void setOnFinishButtonPressed(Runnable buttonPress) {
		finishButton.setOnAction(value -> buttonPress.run());
	}

}
