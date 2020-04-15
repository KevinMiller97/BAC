package com.kevinmiller.gradingsupport.fxgui.controls;

import java.util.List;

import com.kevinmiller.gradingsupport.calc.ICalculatePoints;

import javafx.scene.control.Tab;

/**
 * @author Kevin Miller
 */
public class Segment extends Tab implements ICalculatePoints {

	private final String title;
	private final SegmentContent content;
	private final String identifier;

	public Segment(String title, SegmentContent content, String identifier) {
		super(title, content);
		this.title = title;
		this.content = content;
		this.identifier = identifier;
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

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public String getFormula() {
		return content.getFormula();
	}

	@Override
	public List<? extends ICalculatePoints> getSubNodes() {
		return content.getSubPoints();
	}

	public String getComment() {
		return content.getComment();
	}

}