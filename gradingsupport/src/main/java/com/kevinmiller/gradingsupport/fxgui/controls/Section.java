package com.kevinmiller.gradingsupport.fxgui.controls;

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

	public Section() {
		ScreenHelper.loadFXML(this, this);
	}

}
