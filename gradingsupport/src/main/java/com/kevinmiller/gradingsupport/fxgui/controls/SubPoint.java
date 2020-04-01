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
	private Label label;

	@FXML
	private MenuButton dropdownMenu;
	private SubPointEntry selectedEntry = null;

	public SubPoint(String topic, ArrayList<SubPointEntry> entries) {
		ScreenHelper.loadFXML(this, this);
		label.setText(topic);

		for (SubPointEntry entry : entries) {
			MenuItem option = new MenuItem(entry.getTitle());
			option.setOnAction(v -> {
				selectedEntry = entry;
				dropdownMenu.setText(entry.getTitle());
			});
			if (entry.wasSelectedBeforeSession()) {
				selectedEntry = entry;
			}
			dropdownMenu.getItems().add(option);
		}
		if (selectedEntry == null)
			selectedEntry = entries.get(0);
		dropdownMenu.setText(selectedEntry.getTitle());
	}

	// TODO
//	public boolean bonusPoints() {
//		
//	}

	public double getPoints() {
		return selectedEntry.getPoints();
	}
}
