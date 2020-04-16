package com.kevinmiller.gradingsupport.fxgui.controls.subpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kevinmiller.gradingsupport.calc.ICalculatePoints;
import com.kevinmiller.gradingsupport.fxgui.controls.IWorkedOn;
import com.kevinmiller.gradingsupport.utility.PropertiesHelper;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class SubPoint extends VBox implements ICalculatePoints, IWorkedOn {

	@FXML
	private VBox root;

	@FXML
	private Label label;

	@FXML
	private MenuButton dropdownMenu;
	private SubPointEntry selectedEntry = null;
	private final String topic;
	private final ArrayList<SubPointEntry> entries;
	private final boolean bonusPoints;
	private final String identifier;
	private BooleanProperty workedOn = new SimpleBooleanProperty(false);

	private String colorEntryWorkedOn;
	private String colorEntryNotWorkedOn;
	private final String focusStyle = "-fx-focus-color: BLACK; -fx-faint-focus-color: BLACK;";

	public SubPoint(String topic, ArrayList<SubPointEntry> entries, boolean bonusPoints, String identifier) {
		ScreenHelper.loadFXML(this, this);
		this.bonusPoints = bonusPoints;
		this.entries = entries;
		this.topic = topic;
		this.identifier = identifier;
		label.setText(topic);

		try {
			colorEntryWorkedOn = PropertiesHelper.loadProperty("colorEntryWorkedOn");
			colorEntryNotWorkedOn = PropertiesHelper.loadProperty("colorEntryNotWorkedOn");
		} catch (IOException e) {
			// if not configured
			colorEntryWorkedOn = "WHITE";
			colorEntryNotWorkedOn = "GREY";
		}

		dropdownMenu.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// if focused
				if (newValue)
					dropdownMenu.setStyle(focusStyle);
				else if (workedOn.get() && !newValue)
					dropdownMenu.setStyle("-fx-background-color: " + colorEntryWorkedOn + ";");
				else
					dropdownMenu.setStyle("-fx-background-color: " + colorEntryNotWorkedOn + ";");
			}
		});

		for (SubPointEntry entry : entries) {
			MenuItem option = new MenuItem(entry.getTitle());
			option.setOnAction(v -> {
				selectedEntry = entry;
				dropdownMenu.setText(entry.getTitle());
				workedOn.set(true);
			});
			if (entry.wasSelectedBeforeSession()) {
				selectedEntry = entry;
			}
			dropdownMenu.getItems().add(option);
		}
		if (selectedEntry == null) {
			selectedEntry = entries.get(0);
			dropdownMenu.setStyle("-fx-background-color: " + colorEntryNotWorkedOn + ";");
		}
		dropdownMenu.setText(selectedEntry.getTitle());

	}

	public BooleanProperty getWorkedOnProperty() {
		return workedOn;
	}

	@Override
	public void addListeners() {
		// empty implementation, no more relevant subnodes
	}

	@Override
	public boolean validateWorkedOnPropertiesOfChildNodes() {
		// empty implementation, not relevant
		return true;
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
