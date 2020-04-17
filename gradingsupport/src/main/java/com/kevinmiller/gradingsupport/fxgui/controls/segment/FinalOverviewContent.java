package com.kevinmiller.gradingsupport.fxgui.controls.segment;

import java.io.IOException;
import java.util.ArrayList;

import com.kevinmiller.gradingsupport.utility.PropertiesHelper;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class FinalOverviewContent extends ScrollPane {

	@FXML
	private VBox content;

	@FXML
	private BorderPane borderPane;

	private ArrayList<FinalOverviewEntry> entries = new ArrayList<FinalOverviewEntry>();

	public FinalOverviewContent(ArrayList<Node> overviewContent) {
		ScreenHelper.loadFXML(this, this);
		content.getChildren().addAll(overviewContent);

		try {
			borderPane.setPrefWidth(Integer.parseInt(PropertiesHelper.loadProperty("fx.x")) - 40);
			BorderPane.setMargin(content, new Insets(0, 0, 30, 0));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<FinalOverviewEntry> getEntries() {
		return entries;
	}

}
