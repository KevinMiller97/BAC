package com.kevinmiller.gradingsupport.fxgui.controls;

import java.util.ArrayList;

import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.scene.control.TabPane;

/**
 * A {@code Section} condenses various
 * {@link com.kevinmiller.gradingsupport.fxgui.controls.Segment#Segment
 * Segments} into a single unit, displayed as a tab in the
 * {@link com.kevinmiller.gradingsupport.fxgui.FXBaseApplication#FXBaseApplication
 * Base application}.
 * 
 * @author Kevin Miller
 */
public class Section extends TabPane {

	private String title;

	public Section(String title, ArrayList<Segment> segments) {
		ScreenHelper.loadFXML(this, this);
		this.title = title;
		getTabs().addAll(segments);
	}

	public String getTitle() {
		return title;
	}

}
