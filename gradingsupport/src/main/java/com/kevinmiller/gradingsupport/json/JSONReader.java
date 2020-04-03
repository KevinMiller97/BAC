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
import com.kevinmiller.gradingsupport.utility.PropertiesHelper;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

public final class JSONReader {

	private static Optional<ArrayList<Section>> sections;
	private static ArrayList<Section> loadedSections = new ArrayList<Section>();

	static final String configLocation = "/config/appconfig.json";
	final static Logger logger = Logger.getLogger(ScreenHelper.class.getName());

	public static void loadConfigurationAndInitializeApplication() throws JSONException {
		ScreenHelper.configureLogger(logger);
		try {
			String configRead = IOUtils.toString(JSONReader.class.getResourceAsStream(configLocation), "UTF-8");
			initializeGUIContent(new JSONObject(configRead));
			return;
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getClass().getSimpleName(), e);
		}
		logger.log(Level.SEVERE, "Could not load configuration, stopping Application");
		System.exit(1);
	}

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

	static void initializeGUIContent(JSONObject configuration) {
		ScreenHelper.configureLogger(logger);
		try {
			loadTerminology();
		} catch (IOException e) {
			e.printStackTrace(); // TODO communicate to user
			return;
		}

		JSONArray jSections = configuration.getJSONArray(sectionTerm);
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
		JSONArray jSegments = jSection.getJSONArray(segmentTerm);
		for (int i = 0; i < jSegments.length(); ++i) {
			segments.add(constructSegment(jSegments.getJSONObject(i)));
		}
		Section s = new Section(jSection.getString(nameTerm), segments);
		logger.log(Level.INFO, "Loaded " + s.toString());
		return s;
	}

	private static Segment constructSegment(JSONObject jSegment) throws JSONException {
		ArrayList<SubPoint> subPoints = new ArrayList<SubPoint>();
		JSONArray jSubPoints = jSegment.getJSONArray(subpointTerm);
		for (int i = 0; i < jSubPoints.length(); ++i) {
			subPoints.add(constructSubPoint(jSubPoints.getJSONObject(i)));
		}
		return new Segment(jSegment.getString(nameTerm),
				new SegmentContent(subPoints, jSegment.has(hintTerm) ? jSegment.getString(hintTerm) : null));
	}

	private static SubPoint constructSubPoint(JSONObject jSubPoint) throws JSONException {
		ArrayList<SubPointEntry> subPointEntries = new ArrayList<SubPointEntry>();
		JSONArray jSubPointEntries = jSubPoint.getJSONArray(subpointentryTerm);
		// points calculated
		if (jSubPoint.getBoolean(pointsrankedTerm)) {
			double maxRank = jSubPoint.getDouble(maxRankTerm);
			double maxPoints = jSubPoint.getDouble(maxPointsTerm);
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
		return new SubPoint(jSubPoint.getString(nameTerm), subPointEntries, jSubPoint.has(bonusPointsTerm));
	}

	private static SubPointEntry constructSubPointEntry(JSONObject jSubPointEntry) throws JSONException {
		if (jSubPointEntry.has(selectedTerm))
			return new SubPointEntry(jSubPointEntry.getString(nameTerm), jSubPointEntry.getDouble(pointsTerm),
					jSubPointEntry.getBoolean(selectedTerm));
		return new SubPointEntry(jSubPointEntry.getString(nameTerm), jSubPointEntry.getDouble(pointsTerm));
	}

	private static SubPointEntry constructSubPointEntryRanked(JSONObject jSubPointEntry, double maxRank,
			double maxPoints) throws JSONException {
		if (jSubPointEntry.has(selectedTerm))
			return new SubPointEntry(jSubPointEntry.getString(nameTerm), jSubPointEntry.getInt(rankTerm), maxRank,
					maxPoints, jSubPointEntry.getBoolean(selectedTerm));
		return new SubPointEntry(jSubPointEntry.getString(nameTerm), jSubPointEntry.getInt(rankTerm), maxRank,
				maxPoints);
	}

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