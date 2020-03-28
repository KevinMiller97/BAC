package com.kevinmiller.gradingsupport.fxgui.controls;

import javafx.scene.control.Tab;

/**
 * 
 * @author Kevin Miller
 */
public class Segment extends Tab {

	private final SegmentContent content;

	public Segment(String title, SegmentContent content) {
		super(title);
		this.content = content;
	}

}
