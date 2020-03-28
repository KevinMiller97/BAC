package com.kevinmiller.gradingsupport.fxgui.controls;

import java.util.ArrayList;

import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class SubPoint extends VBox {

	@FXML
	private Label topic;

	@FXML
	private MenuButton dropdownMenu;
	private SubPointEntry selectedEntry = new SubPointEntry("not selected", 0);

	public SubPoint(String topic, ArrayList<SubPointEntry> entries) {
		ScreenHelper.loadFXML(this, this);
		this.topic.setText(topic);

		for (SubPointEntry entry : entries) {
			MenuItem option = new MenuItem(entry.getTitle());
			option.setOnAction(v -> {
				selectedEntry = entry;
				dropdownMenu.setText(entry.getTitle());
			});
			dropdownMenu.getItems().add(option);
		}
	}

	public SubPointEntry getSelectedSubPoint() {
		return selectedEntry;
	}
}
