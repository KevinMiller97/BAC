package com.kevinmiller.gradingsupport.calc;

/**
 * used for calculating points from a parsed String formula that is matched
 * against against the titles of the implementing FX components
 * 
 * @author Kevin Miller
 */
public interface ICalculatePoints {

	public double getPoints();

	public String getTitle();

}
