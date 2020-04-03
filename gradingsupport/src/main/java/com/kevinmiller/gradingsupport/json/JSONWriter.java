package com.kevinmiller.gradingsupport.json;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kevinmiller.gradingsupport.fxgui.controls.Section;
import com.kevinmiller.gradingsupport.fxgui.controls.Segment;
import com.kevinmiller.gradingsupport.fxgui.controls.SubPoint;
import com.kevinmiller.gradingsupport.fxgui.controls.SubPointEntry;
import com.kevinmiller.gradingsupport.utility.PropertiesHelper;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

public final class JSONWriter {

	final static Logger logger = Logger.getLogger(ScreenHelper.class.getName());

	private static String sectionTerm;
	private static String segmentTerm;
	private static String subpointTerm;
	private static String subpointentryTerm;
	private static String hintTerm;
	private static String nameTerm;
	private static String pointsTerm;
	private static String rankTerm;
	private static String pointsrankedTerm;
	private static String maxPointsTerm;
	private static String maxRankTerm;
	private static String selectedTerm;
	private static String bonusPointsTerm;

	public static void generateSaveFile(ArrayList<Section> sections) {
		try {
			loadTerminology();
		} catch (IOException e) {
			e.printStackTrace(); // TODO communicate to user
			return;
		}
		JSONObject result = new JSONObject();
		JSONArray jSections = new JSONArray();
		for (Section s : sections) {
			jSections.put(constructSection(s));
		}
		result.put(sectionTerm, jSections);

		try {
			logger.log(Level.INFO, "writing JSON: " + result.toString());
			FileWriter file = new FileWriter("output.json");
			file.write(result.toString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JSONObject constructSection(Section section) {
		JSONObject jSection = new JSONObject();
		jSection.put(nameTerm, section.getTitle());
		JSONArray jSegments = new JSONArray();
		for (Segment s : section.getSegments()) {
			jSegments.put(constructSegment(s));
		}
		jSection.put(segmentTerm, jSegments);
		return jSection;

	}

	public static JSONObject constructSegment(Segment segment) {
		JSONObject jSegment = new JSONObject();
		jSegment.put(nameTerm, segment.getTitle());

		JSONArray jSubPoints = new JSONArray();
		for (SubPoint sp : segment.getSegmentContent().getSubPoints()) {
			jSubPoints.put(constructSubPoint(sp));
		}
		jSegment.put(subpointTerm, jSubPoints);
		if (segment.getSegmentContent().getHint() != null)
			jSegment.put(hintTerm, segment.getSegmentContent().getHint());
		return jSegment;
	}

	public static JSONObject constructSubPoint(SubPoint subPoint) {
		JSONObject jSub = new JSONObject();
		jSub.put(nameTerm, subPoint.getTopic());
		if (subPoint.isBonusPoints()) {
			jSub.put(bonusPointsTerm, true);
		}
		// stored within the individual subPoints for easier evaluation
		boolean pointsRanked = subPoint.getEntries().get(0).isPointsRanked();
		jSub.put(pointsrankedTerm, pointsRanked);
		if (pointsRanked) {
			jSub.put(maxRankTerm, subPoint.getEntries().get(0).getMaxRank());
			jSub.put(maxPointsTerm, subPoint.getEntries().get(0).getMaxPoints());
		}

		JSONArray jSubPointEntries = new JSONArray();
		for (SubPointEntry spe : subPoint.getEntries()) {
			jSubPointEntries.put(constructSubPointEntry(spe, subPoint.getSelectedEntry().equals(spe)));
		}
		jSub.put(subpointentryTerm, jSubPointEntries);
		return jSub;
	}

	public static JSONObject constructSubPointEntry(SubPointEntry entry, boolean selected) {
		JSONObject jEntry = new JSONObject();
		jEntry.put(nameTerm, entry.getTitle());
		if (entry.isPointsRanked()) {
			jEntry.put(rankTerm, entry.getRank());
		} else {
			jEntry.put(pointsTerm, entry.getPoints());
		}
		if (selected) {
			jEntry.put(selectedTerm, true);
		}
		return jEntry;
	}

	// TODO fuse into 1 function with JSONReader
	private static void loadTerminology() throws IOException {
		sectionTerm = PropertiesHelper.loadProperty("sections");
		segmentTerm = PropertiesHelper.loadProperty("segments");
		subpointTerm = PropertiesHelper.loadProperty("subpoints");
		subpointentryTerm = PropertiesHelper.loadProperty("subpointentries");
		hintTerm = PropertiesHelper.loadProperty("hint");
		nameTerm = PropertiesHelper.loadProperty("name");
		pointsTerm = PropertiesHelper.loadProperty("points");
		rankTerm = PropertiesHelper.loadProperty("rank");
		pointsrankedTerm = PropertiesHelper.loadProperty("pointsranked");
		maxPointsTerm = PropertiesHelper.loadProperty("maxpoints");
		maxRankTerm = PropertiesHelper.loadProperty("maxrank");
		selectedTerm = PropertiesHelper.loadProperty("selected");
		bonusPointsTerm = PropertiesHelper.loadProperty("bonuspoints");
	}

}
