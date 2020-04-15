package com.kevinmiller.gradingsupport.fxgui.controls;

import java.io.IOException;
import java.util.ArrayList;

import com.kevinmiller.gradingsupport.calc.CalculationParser;
import com.kevinmiller.gradingsupport.utility.PropertiesHelper;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SegmentContent extends AnchorPane {

	@FXML
	private AnchorPane root;

	@FXML
	private VBox content;

	@FXML
	private Label commentLabel;

	@FXML
	private Label hintLabel;

	@FXML
	private BorderPane borderPane;

	@FXML
	private VBox hintBox;

	@FXML
	private TextField commentEntryField;

	@FXML
	private Button commentEntryButton;

	@FXML
	private VBox commentBox;

	private final ArrayList<SubPoint> subPoints;
	private String hint;
	private String pointsFormula = "";
	private String comment;

	public SegmentContent(ArrayList<SubPoint> subPoints) {
		this(subPoints, null, "", "");
	}

	public SegmentContent(ArrayList<SubPoint> subPoints, String hint, String pointsFormula, String comment) {
		ScreenHelper.loadFXML(this, this);
		this.subPoints = subPoints;
		this.hint = hint;
		this.comment = comment;
		this.pointsFormula = pointsFormula;
		if (hint != null)
			hintLabel.setText(hint);
		else
			hintBox.setVisible(false);

		commentLabel.setText(comment);
		commentEntryButton.setOnAction(value -> {
			commentLabel.setText(commentEntryField.getText());
			this.comment = commentEntryField.getText();
		});
		root.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				commentEntryButton.fire();
			}
		});

		content.getChildren().addAll(subPoints);
		try {
			borderPane.setPrefWidth(Integer.parseInt(PropertiesHelper.loadProperty("fx.x")) - 40);
			BorderPane.setMargin(content, new Insets(0, 0, 30, 0));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
