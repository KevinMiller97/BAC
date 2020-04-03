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
	private final String topic;
	private final ArrayList<SubPointEntry> entries;
	private final boolean bonusPoints;

	public SubPoint(String topic, ArrayList<SubPointEntry> entries, boolean bonusPoints) {
		ScreenHelper.loadFXML(this, this);
		this.bonusPoints = bonusPoints;
		this.entries = entries;
		this.topic = topic;
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

	public double getPoints() {
		return selectedEntry.getPoints();
	}

	public SubPointEntry getSelectedEntry() {
		return selectedEntry;
	}

	public String getTopic() {
		return topic;
	}

	public boolean isBonusPoints() {
		return bonusPoints;
	}

	public ArrayList<SubPointEntry> getEntries() {
		return entries;
	}

}
