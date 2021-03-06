package com.kevinmiller.gradingsupport.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kevinmiller.gradingsupport.fxgui.controls.section.Section;
import com.kevinmiller.gradingsupport.fxgui.controls.section.SuperSection;
import com.kevinmiller.gradingsupport.fxgui.controls.segment.Segment;
import com.kevinmiller.gradingsupport.fxgui.controls.segment.SegmentContent;
import com.kevinmiller.gradingsupport.fxgui.controls.segment.finaloverview.FinalOverview;
import com.kevinmiller.gradingsupport.fxgui.controls.subpoint.SubPoint;
import com.kevinmiller.gradingsupport.fxgui.controls.subpoint.SubPointEntry;
import com.kevinmiller.gradingsupport.stagecontroller.UserScreen;
import com.kevinmiller.gradingsupport.utility.PropertiesHelper;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

import javafx.stage.FileChooser;

public final class JSONReader {

	private static Optional<ArrayList<Section>> sections;
	private static ArrayList<Section> loadedSections = new ArrayList<Section>();

	private static final String configLocation = "/config/appconfig.json";
	private final static Logger logger = Logger.getLogger(ScreenHelper.class.getName());

	private static final FileChooser fileChooser = new FileChooser();

	public static void loadConfigurationAndInitializeApplication(String configuration) throws JSONException {
		ScreenHelper.configureLogger(logger);
		try {
			JSONUtil.loadTerminology();
			if (configuration == null) {
				// blank application from appconfig.json
				initializeGUIContent(readDefaultConfiguration());
			} else {
				// configuration from file
				try {
					initializeGUIContent(new JSONObject(configuration));
				} catch (JSONException e) {
					UserScreen.updateFooterMessage(true,
							"Invalid file! Only use .json files generated by this application");
				}
			}
			return;
		} catch (FileNotFoundException e) {
			UserScreen.updateFooterMessage(true, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		} catch (IOException e) {
			UserScreen.updateFooterMessage(true, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		} catch (Exception e) {
			UserScreen.updateFooterMessage(true, e.getMessage());
			logger.log(Level.SEVERE, e.getClass().getSimpleName(), e);
			e.printStackTrace();
		}
		logger.log(Level.SEVERE, "Could not load configuration, stopping Application");
		System.exit(1);
	}

	public static void reset() {
		loadedSections = new ArrayList<Section>();
	}

	public static JSONObject getDefaultConfiguration() {
		return readDefaultConfiguration();
	}

	private static JSONObject readDefaultConfiguration() {
		try {
			return new JSONObject(IOUtils.toString(JSONReader.class.getResourceAsStream(configLocation), "UTF-8"));
		} catch (FileNotFoundException e) {
			UserScreen.updateFooterMessage(true, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		} catch (IOException e) {
			UserScreen.updateFooterMessage(true, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		} catch (Exception e) {
			UserScreen.updateFooterMessage(true, e.getMessage());
			logger.log(Level.SEVERE, e.getClass().getSimpleName(), e);
			e.printStackTrace();
		}
		return new JSONObject();
	}

	public static void initializeGUIContentFromFile() {
		File file = fileChooser.showOpenDialog(UserScreen.getStage());
		loadedSections = new ArrayList<Section>();
		if (file != null) {
			try {
				String configuration = FileUtils.readFileToString(file, "UTF-8");
				loadConfigurationAndInitializeApplication(configuration);
				UserScreen.reload(new JSONObject(configuration), "File loaded successfully");
			} catch (IOException e) {
				UserScreen.updateFooterMessage(true, e.getMessage());
				e.printStackTrace();
			} catch (JSONException e) {
				UserScreen.updateFooterMessage(true,
						"Invalid file! Only use .json files generated by this application");
				e.printStackTrace();
			}
		}
	}

	public static void generateCSVFromMultipleFiles() {
		List<File> files = fileChooser.showOpenMultipleDialog(UserScreen.getStage());
		if (files != null) {
			String currentFileName = "";
			boolean warning = false;
			try {
				String fileName = "grading_result.csv";
				FileWriter file = new FileWriter(fileName);
				file.append(PropertiesHelper.loadProperty("csvheadline"));
				file.append("\n");
				for (File f : files) {
					currentFileName = f.getName();
					JSONObject currentStudent = new JSONObject(FileUtils.readFileToString(f, "UTF-8"));
					// if something was forgotten, warn user
					if (currentStudent.getString(JSONUtil.studentFirstnameTerm).equals("not entered")
							|| currentStudent.getString(JSONUtil.studentLastnameTerm).equals("not entered")
							|| currentStudent.getString(JSONUtil.studentidTerm).equals("not entered")) {
						warning = true;
					}
					file.append(currentStudent.getString(JSONUtil.studentFirstnameTerm));
					file.append(';');
					file.append(currentStudent.getString(JSONUtil.studentLastnameTerm));
					file.append(';');
					file.append(currentStudent.getString(JSONUtil.studentidTerm));
					file.append(';');
					file.append(FinalOverview.getFeedback());
					file.append("\n");
				}
				file.flush();
				file.close();
				UserScreen.updateFooterMessage(false,
						warning ? "Warning, detected missing student info in 1 or more files. File Created: "
								: "Success! File created: " + fileName);
			} catch (IOException io) {
				UserScreen.updateFooterMessage(true, io.getMessage() + " in " + currentFileName);
				io.printStackTrace();
			} catch (JSONException e) {
				UserScreen.updateFooterMessage(true, e.getMessage() + " in " + currentFileName);
				e.printStackTrace();
			}
		}
	}

	static void initializeGUIContent(JSONObject configuration) {
		ScreenHelper.configureLogger(logger);
		JSONArray jSections = configuration.getJSONArray(JSONUtil.sectionTerm);
		for (int i = 0; i < jSections.length(); ++i) {
			try {
				loadedSections.add(constructSection(jSections.getJSONObject(i)));
			} catch (JSONException e) {
				UserScreen.updateFooterMessage(true, e.getMessage());
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
		String comment = jSegment.has(JSONUtil.commentTerm) ? jSegment.getString(JSONUtil.commentTerm) : "";

		return new Segment(name, new SegmentContent(subPoints, hint, formula, comment), identifier);
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