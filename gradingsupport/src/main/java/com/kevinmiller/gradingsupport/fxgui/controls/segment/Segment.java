package com.kevinmiller.gradingsupport.fxgui.controls.segment;

import java.util.List;

import com.kevinmiller.gradingsupport.calc.ICalculatePoints;
import com.kevinmiller.gradingsupport.fxgui.controls.IWorkedOn;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Tab;

public class Segment extends Tab implements ICalculatePoints, IWorkedOn {

	private final String title;
	private final SegmentContent content;
	private final String identifier;

	public Segment(String title, SegmentContent content, String identifier) {
		super(title, content);
		this.title = title;
		this.content = content;
		this.identifier = identifier;
	}

	public void focusFirstSubPoint() {
		content.getSubPoints().get(0).focus();
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

	@Override
	public void addListeners() {
		// empty implementation, handled in segmentContent
	}

	@Override
	public boolean validateWorkedOnPropertiesOfChildNodes() {
		return content.validateWorkedOnPropertiesOfChildNodes();
	}

	@Override
	public BooleanProperty getWorkedOnProperty() {
		return content.getWorkedOnProperty();
	}

}