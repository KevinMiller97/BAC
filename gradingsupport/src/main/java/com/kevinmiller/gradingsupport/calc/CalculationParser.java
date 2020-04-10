package com.kevinmiller.gradingsupport.calc;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

public class CalculationParser {

	final static Logger logger = Logger.getLogger(ScreenHelper.class.getName());

	public static double calculatePoints(String formula, List<? extends ICalculatePoints> nodes) {
		if (formula.equals("")) {
			logger.log(Level.FINE, "no formula detected, returning sum");
			return calculatePoints(nodes);
		}

		logger.log(Level.FINE, "calculating points using formula: " + formula);
		String f = formula;
		Pattern p = Pattern.compile("\\{(.*?)\\}");
		Matcher m = p.matcher(f);
		while (m.find()) {
			f = f.replace(m.group(1), getNodePoints(m.group(1), nodes));
			f = f.replaceAll("\\{", "");
			f = f.replaceAll("\\}", "");
		}
		try {
			logger.log(Level.FINE, "calculation executed: " + f);
			return new DoubleEvaluator().evaluate(f);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	private static double calculatePoints(List<? extends ICalculatePoints> nodes) {
		double points = 0;
		for (ICalculatePoints s : nodes) {
			points += s.getPoints();
		}
		return points;
	}

	private static String getNodePoints(String nodeTitle, List<? extends ICalculatePoints> nodes) {
		String title = nodeTitle;
		title = nodeTitle.replaceAll("\\{", "");
		title = nodeTitle.replaceAll("\\}", "");

		for (ICalculatePoints n : nodes) {
			if (title.equals(n.getTitle()))
				return Double.toString(n.getPoints());
		}
		throw new RuntimeException("No match for: " + title);
	}

}
