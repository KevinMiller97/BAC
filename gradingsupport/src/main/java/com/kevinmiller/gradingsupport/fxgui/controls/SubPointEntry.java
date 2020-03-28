package com.kevinmiller.gradingsupport.fxgui.controls;

public class SubPointEntry {

	private String title;
	private double points;

	/**
	 * allows for easy equal distribution of points
	 * 
	 * @param rank      descending, maxRank is best, 0 results in 0 points
	 * @param maxPoints max points available, will be divided by rank
	 */
	public SubPointEntry(String title, double rank, double maxRank, double maxPoints) {
		this.title = title;
		this.points = rank == 0 ? 0 : (maxPoints / maxRank) * rank;
	}

	public SubPointEntry(String title, double points) {
		this.title = title;
		this.points = points;
	}

	public String getTitle() {
		return title;
	}

	public double getPoints() {
		return points;
	}

}
