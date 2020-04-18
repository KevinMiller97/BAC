package com.kevinmiller.gradingsupport.calc;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.kevinmiller.gradingsupport.stagecontroller.UserScreen;
import com.kevinmiller.gradingsupport.utility.ScreenHelper;

public class CalculationParser {

	final static Logger logger = Logger.getLogger(ScreenHelper.class.getName());

	/**
	 * only call from instanceof {@code SuperSection}; allows for formulas that use
	 * indices outside its own subNodes. Typically used to calculate the final
	 * result by calculating with multiple sections for example.
	 * 
	 * @param formula  : from the config file, can identifiy nodes from other
	 *                 sections
	 * @param ownNodes : subnodes of the {@code SuperSection}, can be {@code null}
	 * @param sections : a reference to a list of sections (can be every section in
	 *                 the application), of which you want to calculate a result
	 *                 from
	 * @return : the result of the calculation from the formula
	 */
	public static double calculatePointsGlobalIdentifier(String formula, List<? extends ICalculatePoints> ownNodes,
			List<? extends ICalculatePoints> sections) {
		if (formula.equals("") && sections == null) {
			logger.log(Level.FINE, "no formula detected, returning sum");
			return calculatePointsDefault(ownNodes);
		} else if (formula.equals("") && sections != null) {
			logger.log(Level.INFO, "calculatePoints called with sections but without specified formula, returning sum");
			return calculatePointsDefault(sections);
		}

		logger.log(Level.FINE, "calculating points using formula: " + formula);
		String f = formula;
		Pattern p = Pattern.compile("\\{(.*?)\\}");
		Matcher m = p.matcher(f);
		while (m.find()) {
			f = f.replace(m.group(1), getNodePoints(m.group(1), ownNodes, sections));
			f = f.replaceAll("\\{", "");
			f = f.replaceAll("\\}", "");
		}
		try {
			logger.log(Level.FINE, "calculation executed: " + f);
			return new DoubleEvaluator().evaluate(f);
		} catch (Exception e) {
			UserScreen.updateFooterMessage(true, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	public static double calculatePoints(String formula, List<? extends ICalculatePoints> nodes) {
		return calculatePointsGlobalIdentifier(formula, nodes, null);
	}

	private static double calculatePointsDefault(List<? extends ICalculatePoints> nodes) {
		double points = 0;
		for (ICalculatePoints s : nodes) {
			points += s.getPoints();
		}
		return points;
	}

	private static String getNodePoints(String nodeIdentifier, List<? extends ICalculatePoints> ownNodes,
			List<? extends ICalculatePoints> sections) {
		String title = nodeIdentifier;
		title = nodeIdentifier.replaceAll("\\{", "");
		title = nodeIdentifier.replaceAll("\\}", "");

		// nodes of other sections
		if (sections != null) {
			for (ICalculatePoints section : sections) {
				if (title.equals(section.getIdentifier()))
					return Double.toString(section.getPoints());
				if (section.getSubNodes() != null) {
					for (ICalculatePoints segment : section.getSubNodes()) {
						if (title.equals(segment.getIdentifier()))
							return Double.toString(segment.getPoints());
						if (segment.getSubNodes() != null) {
							for (ICalculatePoints subPoint : segment.getSubNodes()) {
								if (title.equals(subPoint.getIdentifier()))
									return Double.toString(subPoint.getPoints());
							}
						}
					}
				}
			}
		}
		if (ownNodes != null) {
			for (ICalculatePoints n : ownNodes) {
				if (n != null && title.equals(n.getIdentifier()))
					return Double.toString(n.getPoints());
			}
		}
		throw new RuntimeException("No match for: " + title);
	}

}
