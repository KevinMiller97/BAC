package com.kevinmiller.gradingsupport.fxgui.controls.segment.finaloverview;

import javafx.scene.control.Tab;

public class FinalOverview extends Tab {

	/**
	 * a flag to determine whether something along the grading process was changed
	 * even though the FinalOverview was already created
	 */
	private static boolean edited = false;
	private static String feedback = "";

	private final String title;
	private final FinalOverviewContent content;

	public FinalOverview(String title, FinalOverviewContent content) {
		super(title, content);
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public FinalOverviewContent getSegmentContent() {
		return content;
	}

	public static boolean wasEdited() {
		return edited;
	}

	public static void setEdited(boolean edited) {
		FinalOverview.edited = edited;
	}

	public static String getFeedback() {
		return feedback;
	}

	public static void setFeedback(String feedback) {
		FinalOverview.feedback = feedback;
	}
}
