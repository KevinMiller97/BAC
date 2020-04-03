package com.kevinmiller.gradingsupport.fxgui.controls;

import javafx.scene.control.Tab;

/**
 * @author Kevin Miller
 */
public class Segment extends Tab {

	private final String title;
	private final SegmentContent content;

	public Segment(String title, SegmentContent content) {
		super(title, content);
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public double getPoints() {
		return content.getPoints();
	}

	public SegmentContent getSegmentContent() {
		return content;
	}
}
