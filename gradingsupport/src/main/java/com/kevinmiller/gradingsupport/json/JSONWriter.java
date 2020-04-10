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
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

public final class JSONWriter {

	final static Logger logger = Logger.getLogger(ScreenHelper.class.getName());

	public static void generateSaveFile(ArrayList<Section> sections, String studentName, String studentId) {
		JSONObject result = new JSONObject();
		JSONArray jSections = new JSONArray();
		for (Section s : sections) {
			jSections.put(constructSection(s));
		}
		result.put(JSONUtil.sectionTerm, jSections);

		try {
			logger.log(Level.INFO, "writing JSON: " + result.toString());
			FileWriter file = new FileWriter(JSONUtil.generateFileName(studentName, studentId) + ".json");
			file.write(result.toString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static JSONObject constructSection(Section section) {
		JSONObject jSection = new JSONObject();
		jSection.put(JSONUtil.nameTerm, section.getTitle());
		JSONArray jSegments = new JSONArray();
		for (Segment s : section.getSegments()) {
			jSegments.put(constructSegment(s));
		}
		jSection.put(JSONUtil.segmentTerm, jSegments);
		return jSection;

	}

	private static JSONObject constructSegment(Segment segment) {
		JSONObject jSegment = new JSONObject();
		jSegment.put(JSONUtil.nameTerm, segment.getTitle());

		JSONArray jSubPoints = new JSONArray();
		for (SubPoint sp : segment.getSegmentContent().getSubPoints()) {
			jSubPoints.put(constructSubPoint(sp));
		}
		jSegment.put(JSONUtil.subpointTerm, jSubPoints);
		if (segment.getSegmentContent().getHint() != null)
			jSegment.put(JSONUtil.hintTerm, segment.getSegmentContent().getHint());
		return jSegment;
	}

	private static JSONObject constructSubPoint(SubPoint subPoint) {
		JSONObject jSub = new JSONObject();
		jSub.put(JSONUtil.nameTerm, subPoint.getTitle());
		if (subPoint.isBonusPoints()) {
			jSub.put(JSONUtil.bonusPointsTerm, true);
		}
		// stored within the individual subPoints for easier evaluation
		boolean pointsRanked = subPoint.getEntries().get(0).isPointsRanked();
		jSub.put(JSONUtil.pointsrankedTerm, pointsRanked);
		if (pointsRanked) {
			jSub.put(JSONUtil.maxRankTerm, subPoint.getEntries().get(0).getMaxRank());
			jSub.put(JSONUtil.maxPointsTerm, subPoint.getEntries().get(0).getMaxPoints());
		}

		JSONArray jSubPointEntries = new JSONArray();
		for (SubPointEntry spe : subPoint.getEntries()) {
			jSubPointEntries.put(constructSubPointEntry(spe, subPoint.getSelectedEntry().equals(spe)));
		}
		jSub.put(JSONUtil.subpointentryTerm, jSubPointEntries);
		return jSub;
	}

	private static JSONObject constructSubPointEntry(SubPointEntry entry, boolean selected) {
		JSONObject jEntry = new JSONObject();
		jEntry.put(JSONUtil.nameTerm, entry.getTitle());
		if (entry.isPointsRanked()) {
			jEntry.put(JSONUtil.rankTerm, entry.getRank());
		} else {
			jEntry.put(JSONUtil.pointsTerm, entry.getPoints());
		}
		if (selected) {
			jEntry.put(JSONUtil.selectedTerm, true);
		}
		return jEntry;
	}
}
