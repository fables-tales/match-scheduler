package org.studentrobotics.matchscheduler.scheduling;

public class MatchConstraints {
	private boolean mAllowByes = false;
	private int mMaxByeSize = 0;
	private int mMinByeSize = 0;
	private int mNumberOfMatches = 0;
	private int mNumberOfTeams = 0;
	private int mTeamsPerMatch = 0;

	public void disableByes() {
		mAllowByes = false;
	}

	public void enableByes() {
		mAllowByes = true;
	}

	public boolean getAllowByes() {
		return mAllowByes;
	}

	public int getMaxByeSize() {
		return mMaxByeSize;
	}

	public int getMinByeSize() {
		return mMinByeSize;
	}

	public int getNumberOfMatches() {
		return mNumberOfMatches;
	}

	public int getNumberOfTeams() {
		return mNumberOfTeams;
	}

	public int getTeamsPerMatch() {
		return mTeamsPerMatch;
	}

	public void setMaxByeSize(int maxByeSize) {
		if (maxByeSize >= this.mTeamsPerMatch) {
			throw new IllegalArgumentException(
					"the maximum bye size must be smaller than the number of teams per match");
		}
		this.mMaxByeSize = maxByeSize;
	}

	public void setMinByeSize(int minByeSize) {
		if (minByeSize < 2) {
			throw new IllegalArgumentException("the minimum bye size must be at least 2");
		} else {
			mMinByeSize = minByeSize;
		}

	}

	public void setNumberOfMatches(int numberOfMatches) {
		if (numberOfMatches >= 1) {
			this.mNumberOfMatches = numberOfMatches;
		} else {
			throw new IllegalArgumentException("number of matches must be one or more");
		}

	}

	public void setNumberOfTeams(int numberOfTeams) {
		if (numberOfTeams < 2) {
			throw new IllegalArgumentException("number of teams must be 2 or greater");
		} else {
			this.mNumberOfTeams = numberOfTeams;
		}

	}

	public void setTeamsPerMatch(int teamsPerMatch) {
		if (teamsPerMatch < 2) {
			throw new IllegalArgumentException("teams per match must be 2 or higher");
		} else {
			this.mTeamsPerMatch = teamsPerMatch;
		}

	}

}
