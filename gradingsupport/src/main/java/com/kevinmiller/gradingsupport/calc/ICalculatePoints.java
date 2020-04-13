package com.kevinmiller.gradingsupport.calc;

import java.util.List;

/**
 * used for calculating points from a parsed String formula that is matched
 * against against the identifiers of the implementing FX components
 * 
 * @author Kevin Miller
 */
public interface ICalculatePoints {

	public double getPoints();

	public String getTitle();

	public String getIdentifier();

	public String getFormula();

	public List<? extends ICalculatePoints> getSubNodes();

}
