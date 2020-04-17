package com.kevinmiller.gradingsupport.fxgui.controls.section;

import java.util.ArrayList;

import com.kevinmiller.gradingsupport.calc.ICalculatePoints;
import com.kevinmiller.gradingsupport.fxgui.controls.segment.Segment;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

/**
 * a SuperSection is fundamentally the same as a Section, except it also
 * contains a reference of all other Sections in this application, allowing a
 * SuperSection to coordinate the final point calculation, while being a section
 * itself
 * 
 * @author Kevin Miller
 */
public class SuperSection extends Section implements ICalculatePoints {

	private ArrayList<Section> sections;

	public SuperSection(String title, ArrayList<Segment> segments, String pointsFormula, String identifier) {
		ScreenHelper.loadFXML(this, this, "Section.fxml");
		this.title = title;
		this.segments = segments;
		this.pointsFormula = pointsFormula;
		this.identifier = identifier;
		getTabs().addAll(segments);
		addListeners();
	}

	public void setSections(ArrayList<Section> sections) {
		this.sections = sections;
	}

	public ArrayList<Section> getSections() {
		return sections;
	}

}
