package com.kevinmiller.gradingsupport.json;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kevinmiller.gradingsupport.fxgui.controls.Section;
import com.kevinmiller.gradingsupport.fxgui.controls.Segment;
import com.kevinmiller.gradingsupport.fxgui.controls.SegmentContent;
import com.kevinmiller.gradingsupport.fxgui.controls.SubPoint;
import com.kevinmiller.gradingsupport.fxgui.controls.SubPointEntry;
import com.kevinmiller.gradingsupport.fxgui.controls.SuperSection;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

public final class JSONReader {

	private static Optional<ArrayList<Section>> sections;
	private static ArrayList<Section> loadedSections = new ArrayList<Section>();

	static final String configLocation = "/config/appconfig.json";
	final static Logger logger = Logger.getLogger(ScreenHelper.class.getName());

	public static void loadConfigurationAndInitializeApplication() throws JSONException {
		ScreenHelper.configureLogger(logger);
		try {
			JSONUtil.loadTerminology();
			String configRead = IOUtils.toString(JSONReader.class.getResourceAsStream(configLocation), "UTF-8");
			initializeGUIContent(new JSONObject(configRead));
			return;
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getClass().getSimpleName(), e);
			e.printStackTrace();
		}
		logger.log(Level.SEVERE, "Could not load configuration, stopping Application");
		System.exit(1);
	}

	static void initializeGUIContent(JSONObject configuration) {
		ScreenHelper.configureLogger(logger);
		JSONArray jSections = configuration.getJSONArray(JSONUtil.sectionTerm);
		for (int i = 0; i < jSections.length(); ++i) {
			try {
				loadedSections.add(constructSection(jSections.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		for (Section s : loadedSections) {
			if (s instanceof SuperSection)
				((SuperSection) s).setSections(loadedSections);
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
		JSONArray jSegments = jSection.getJSONArray(JSONUtil.segmentTerm);
		for (int i = 0; i < jSegments.length(); ++i) {
			segments.add(constructSegment(jSegments.getJSONObject(i)));
		}
		String name = jSection.getString(JSONUtil.nameTerm);
		String formula = jSection.has(JSONUtil.formulaTerm) ? jSection.getString(JSONUtil.formulaTerm) : "";
		String identifier = jSection.has(JSONUtil.identifierTerm) ? jSection.getString(JSONUtil.identifierTerm) : "";

		Section s;
		if (jSection.has(JSONUtil.superSectionTerm)) {
			s = new SuperSection(name, segments, formula, identifier);
		} else {
			s = new Section(name, segments, formula, identifier);
		}
		logger.log(Level.INFO, "Loaded " + s.toString());
		return s;
	}

	private static Segment constructSegment(JSONObject jSegment) throws JSONException {
		ArrayList<SubPoint> subPoints = new ArrayList<SubPoint>();
		JSONArray jSubPoints = jSegment.getJSONArray(JSONUtil.subpointTerm);
		for (int i = 0; i < jSubPoints.length(); ++i) {
			subPoints.add(constructSubPoint(jSubPoints.getJSONObject(i)));
		}
		String name = jSegment.getString(JSONUtil.nameTerm);
		String hint = jSegment.has(JSONUtil.hintTerm) ? jSegment.getString(JSONUtil.hintTerm) : null;
		String formula = jSegment.has(JSONUtil.formulaTerm) ? jSegment.getString(JSONUtil.formulaTerm) : "";
		String identifier = jSegment.has(JSONUtil.identifierTerm) ? jSegment.getString(JSONUtil.identifierTerm) : "";

		return new Segment(name, new SegmentContent(subPoints, hint, formula), identifier);
	}

	private static SubPoint constructSubPoint(JSONObject jSubPoint) throws JSONException {
		ArrayList<SubPointEntry> subPointEntries = new ArrayList<SubPointEntry>();
		JSONArray jSubPointEntries = jSubPoint.getJSONArray(JSONUtil.subpointentryTerm);
		String identifier = jSubPoint.has(JSONUtil.identifierTerm) ? jSubPoint.getString(JSONUtil.identifierTerm) : "";
		// points calculated
		if (jSubPoint.getBoolean(JSONUtil.pointsrankedTerm)) {
			double maxRank = jSubPoint.getDouble(JSONUtil.maxRankTerm);
			double maxPoints = jSubPoint.getDouble(JSONUtil.maxPointsTerm);
			for (int i = 0; i < jSubPointEntries.length(); ++i) {
				subPointEntries
						.add(constructSubPointEntryRanked(jSubPointEntries.getJSONObject(i), maxRank, maxPoints));
			}
			// points read from json
		} else {
			for (int i = 0; i < jSubPointEntries.length(); ++i) {
				subPointEntries.add(constructSubPointEntry(jSubPointEntries.getJSONObject(i)));
			}
		}
		return new SubPoint(jSubPoint.getString(JSONUtil.nameTerm), subPointEntries,
				jSubPoint.has(JSONUtil.bonusPointsTerm), identifier);
	}

	private static SubPointEntry constructSubPointEntry(JSONObject jSubPointEntry) throws JSONException {
		String name = jSubPointEntry.getString(JSONUtil.nameTerm);
		double points = jSubPointEntry.getDouble(JSONUtil.pointsTerm);
		if (jSubPointEntry.has(JSONUtil.selectedTerm))
			return new SubPointEntry(name, points, jSubPointEntry.getBoolean(JSONUtil.selectedTerm));
		return new SubPointEntry(name, points);
	}

	private static SubPointEntry constructSubPointEntryRanked(JSONObject jSubPointEntry, double maxRank,
			double maxPoints) throws JSONException {
		String name = jSubPointEntry.getString(JSONUtil.nameTerm);
		int rank = jSubPointEntry.getInt(JSONUtil.rankTerm);
		if (jSubPointEntry.has(JSONUtil.selectedTerm))
			return new SubPointEntry(name, rank, maxRank, maxPoints, jSubPointEntry.getBoolean(JSONUtil.selectedTerm));
		return new SubPointEntry(name, rank, maxRank, maxPoints);
	}
}