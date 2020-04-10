package com.kevinmiller.gradingsupport.fxgui.controls;

import com.kevinmiller.gradingsupport.calc.ICalculatePoints;

import javafx.scene.control.Tab;

/**
 * @author Kevin Miller
 */
public class Segment extends Tab implements ICalculatePoints {

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