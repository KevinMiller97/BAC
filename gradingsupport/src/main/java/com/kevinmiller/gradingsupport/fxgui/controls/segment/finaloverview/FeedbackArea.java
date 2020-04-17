package com.kevinmiller.gradingsupport.fxgui.controls.segment.finaloverview;

import com.kevinmiller.gradingsupport.stagecontroller.UserScreen;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class FeedbackArea extends VBox {

	@FXML
	private TextArea feedbackField;

	@FXML
	private Button submitButton;

	public FeedbackArea() {
		ScreenHelper.loadFXML(this, this);
		// prevents the user from having to re-enter it
		feedbackField.setText(FinalOverview.getFeedback());
		submitButton.setOnAction(value -> {
			FinalOverview.setFeedback(feedbackField.getText());
			UserScreen.updateFooterMessage(false, "Feedback submitted successfully!");
		});
	}

}
