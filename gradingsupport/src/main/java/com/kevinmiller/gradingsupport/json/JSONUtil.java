package com.kevinmiller.gradingsupport.json;

import java.io.IOException;

import com.kevinmiller.gradingsupport.utility.PropertiesHelper;

public class JSONUtil {

	static String sectionTerm;
	static String segmentTerm;
	static String subpointTerm;
	static String subpointentryTerm;
	static String hintTerm;
	static String nameTerm;
	static String pointsTerm;
	static String rankTerm;
	static String pointsrankedTerm;
	static String maxPointsTerm;
	static String maxRankTerm;
	static String selectedTerm;
	static String bonusPointsTerm;

	public static String generateFileName(String studentName, String studentId) {
		return studentName.trim().replaceAll("\\s+", "_") + "_" + studentId.trim();
	}

	static void loadTerminology() throws IOException {
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
