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
	static String formulaTerm;
	static String superSectionTerm;
	static String identifierTerm;
	static String commentTerm;
	public static String studentFirstnameTerm;
	public static String studentLastnameTerm;
	public static String studentidTerm;
	public static String feedbackTerm;

	public static String generateFileName(String studentFirstName, String studentLastName, String studentId) {
		return studentFirstName.trim().replaceAll("\\s+", "_") + "_" + studentLastName.trim().replaceAll("\\s+", "_")
				+ "_" + studentId.trim();
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
		formulaTerm = PropertiesHelper.loadProperty("pointsformula");
		superSectionTerm = PropertiesHelper.loadProperty("supersection");
		identifierTerm = PropertiesHelper.loadProperty("identifier");
		studentFirstnameTerm = PropertiesHelper.loadProperty("firstname");
		studentLastnameTerm = PropertiesHelper.loadProperty("lastname");
		studentidTerm = PropertiesHelper.loadProperty("studentid");
		commentTerm = PropertiesHelper.loadProperty("comment");
		feedbackTerm = PropertiesHelper.loadProperty("feedback");
	}

}
