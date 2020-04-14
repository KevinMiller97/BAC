package com.kevinmiller.gradingsupport.fxgui.controls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kevinmiller.gradingsupport.calc.ICalculatePoints;
import com.kevinmiller.gradingsupport.utility.PropertiesHelper;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class SubPoint extends VBox implements ICalculatePoints {

	@FXML
	private Label label;

	@FXML
	private MenuButton dropdownMenu;
	private SubPointEntry selectedEntry = null;
	private final String topic;
	private final ArrayList<SubPointEntry> entries;
	private final boolean bonusPoints;
	private final String identifier;

	private String entryWorkedOn;
	private String entryNotWorkedOn;

	public SubPoint(String topic, ArrayList<SubPointEntry> entries, boolean bonusPoints, String identifier) {
		ScreenHelper.loadFXML(this, this);
		this.bonusPoints = bonusPoints;
		this.entries = entries;
		this.topic = topic;
		this.identifier = identifier;
		label.setText(topic);
		try {
			entryWorkedOn = PropertiesHelper.loadProperty("colorEntryWorkedOn");
			entryNotWorkedOn = PropertiesHelper.loadProperty("colorEntryNotWorkedOn");
		} catch (IOException e) {
			// if not configured
			entryWorkedOn = "WHITE";
			entryNotWorkedOn = "GREY";
		}

		for (SubPointEntry entry : entries) {
			MenuItem option = new MenuItem(entry.getTitle());
			option.setOnAction(v -> {
				selectedEntry = entry;
				dropdownMenu.setText(entry.getTitle());
				dropdownMenu.setStyle("-fx-background-color: " + entryWorkedOn + ";");
			});
			if (entry.wasSelectedBeforeSession()) {
				selectedEntry = entry;
			}
			dropdownMenu.getItems().add(option);
		}
		if (selectedEntry == null) {
			selectedEntry = entries.get(0);
			dropdownMenu.setStyle("-fx-background-color: " + entryNotWorkedOn + ";");
		}
		dropdownMenu.setText(selectedEntry.getTitle());
	}

	public double getPoints() {
		return selectedEntry.getPoints();
	}

	public SubPointEntry getSelectedEntry() {
		return selectedEntry;
	}

	public String getTitle() {
		return topic;
	}

	public boolean isBonusPoints() {
		return bonusPoints;
	}

	public ArrayList<SubPointEntry> getEntries() {
		return entries;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public String getFormula() {
		return "";
	}

	@Override
	public List<? extends ICalculatePoints> getSubNodes() {
		return null;
	}

}
