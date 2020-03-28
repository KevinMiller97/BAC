package com.kevinmiller.gradingsupport.json;

import java.util.ArrayList;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kevinmiller.gradingsupport.fxgui.controls.Section;
import com.kevinmiller.gradingsupport.fxgui.controls.Segment;
import com.kevinmiller.gradingsupport.fxgui.controls.SegmentContent;
import com.kevinmiller.gradingsupport.fxgui.controls.SubPoint;
import com.kevinmiller.gradingsupport.fxgui.controls.SubPointEntry;

public class JSONInterpreter {

	private static Optional<ArrayList<Section>> sections;
	private static ArrayList<Section> loadedSections = new ArrayList<Section>();

	static void initializeGUIContent(JSONObject configuration) {
		JSONArray jSections = configuration.getJSONArray("sections"); // TODO prop file
		for (int i = 0; i < jSections.length(); ++i) {
			try {
				loadedSections.add(constructSection(jSections.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		sections = Optional.of(loadedSections);
	}

	/**
	 * @return the Optional contains all GUI content when fully loaded
	 */
	public static Optional<ArrayList<Section>> getSections() {
		return sections;
	}

	private static Section constructSection(JSONObject jSection) throws JSONException {
		ArrayList<Segment> segments = new ArrayList<Segment>();
		JSONArray jSegments = jSection.getJSONArray("segments"); // TODO prop file
		for (int i = 0; i < jSegments.length(); ++i) {
			segments.add(constructSegment(jSegments.getJSONObject(i)));
		}
		return new Section(jSection.getString("name"), segments);
	}

	private static Segment constructSegment(JSONObject jSegment) throws JSONException {
		ArrayList<SubPoint> subPoints = new ArrayList<SubPoint>();
		JSONArray jSubPoints = jSegment.getJSONArray("subpoints"); // TODO prop file
		for (int i = 0; i < jSubPoints.length(); ++i) {
			subPoints.add(constructSubPoint(jSubPoints.getJSONObject(i)));
		}
		return new Segment(jSegment.getString("name"), new SegmentContent(subPoints));
	}

	private static SubPoint constructSubPoint(JSONObject jSubPoint) throws JSONException {
		ArrayList<SubPointEntry> subPointEntries = new ArrayList<SubPointEntry>();
		JSONArray jSubPointEntries = jSubPoint.getJSONArray("subpointentries"); // TODO prop file
		if (jSubPoint.getBoolean("points_ranked")) {
			double maxRank = jSubPoint.getDouble("max_rank");
			double maxPoints = jSubPoint.getDouble("max_points");
			for (int i = 0; i < jSubPointEntries.length(); ++i) {
				subPointEntries
						.add(constructSubPointEntryRanked(jSubPointEntries.getJSONObject(i), maxRank, maxPoints));
			}
		} else {
			for (int i = 0; i < jSubPointEntries.length(); ++i) {
				subPointEntries.add(constructSubPointEntry(jSubPointEntries.getJSONObject(i)));
			}
		}
		return new SubPoint(jSubPoint.getString("name"), subPointEntries);
	}

	private static SubPointEntry constructSubPointEntry(JSONObject jSubPointEntry) throws JSONException {
		return new SubPointEntry(jSubPointEntry.getString("name"), jSubPointEntry.getDouble("points"));
	}

	private static SubPointEntry constructSubPointEntryRanked(JSONObject jSubPointEntry, double maxRank,
			double maxPoints) throws JSONException {
		return new SubPointEntry(jSubPointEntry.getString("name"), jSubPointEntry.getInt("rank"), maxRank, maxPoints);
	}
}
