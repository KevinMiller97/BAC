package com.kevinmiller.gradingsupport.fxgui.controls;

public class SubPointEntry {

	private String title;
	private double points;
	private double rank;
	private double maxRank;
	private double maxPoints;
	private boolean pointsRanked = false;
	private boolean selectedBeforeSession = false;

	/**
	 * allows for easy equal distribution of points
	 * 
	 * @param rank      descending, maxRank is best, 0 results in 0 points
	 * @param maxPoints max points available, will be divided by rank
	 */
	public SubPointEntry(String title, double rank, double maxRank, double maxPoints) {
		this(title, rank, maxRank, maxPoints, false);
	}

	public SubPointEntry(String title, double rank, double maxRank, double maxPoints, boolean selectedBeforeSession) {
		this.title = title;
		this.pointsRanked = true;
		this.maxRank = maxRank;
		this.maxPoints = maxPoints;
		this.selectedBeforeSession = selectedBeforeSession;
	}

	public SubPointEntry(String title, double points) {
		this(title, points, false);
	}

	public SubPointEntry(String title, double points, boolean selectedBeforeSession) {
		this.title = title;
		this.points = points;
		this.selectedBeforeSession = selectedBeforeSession;
	}

	public String getTitle() {
		return title;
	}

	public double getPoints() {
		return pointsRanked ? (rank == 0 ? 0 : (maxPoints / maxRank) * rank) : points;
	}

	public boolean wasSelectedBeforeSession() {
		return selectedBeforeSession;
	}

	public double getRank() {
		return rank;
	}

	public double getMaxRank() {
		return maxRank;
	}

	public double getMaxPoints() {
		return maxPoints;
	}

	public boolean isPointsRanked() {
		return pointsRanked;
	}

	public boolean isSelectedBeforeSession() {
		return selectedBeforeSession;
	}

}
