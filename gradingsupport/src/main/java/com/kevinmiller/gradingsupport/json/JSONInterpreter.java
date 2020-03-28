package com.kevinmiller.gradingsupport.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kevinmiller.gradingsupport.fxgui.controls.Section;
import com.kevinmiller.gradingsupport.fxgui.controls.Segment;

public class JSONInterpreter {

	public static ArrayList<Section> extractSections(JSONObject configuration) {
		JSONArray sections = configuration.getJSONArray("sections"); // TODO prop file
		for (int i = 0; i < sections.length(); ++i) {
			extractSegment(sections.getJSONObject(i));
		}
		return null;
	}

	private static ArrayList<Segment> extractSegment(JSONObject section) throws JSONException {
		JSONArray segments = section.getJSONArray("segments"); // TODO prop file
		for (int i = 0; i < segments.length(); ++i) {
			// extract individual bulletpoints
			System.out.println(segments.getJSONObject(i).get("name"));
		}
		return null;
	}

}
