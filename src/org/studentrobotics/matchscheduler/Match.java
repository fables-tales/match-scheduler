package org.studentrobotics.matchscheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Match {
	private int mMatchNumber;

	/**
	 * defines the capacity which means that any number of teams is valid in
	 * this match, specifically it should not be a positive number, or if so it
	 * should be so obscenely high we never envision that many teams being in a
	 * match
	 */
	private static final int UNDEFINED_CAPACITY = -1;
	private int mWorkingCapacity = UNDEFINED_CAPACITY;

	/**
	 * sorted set is used here so that when we compare teams pairwise makes
	 * sense
	 */
	private SortedSet<Team> mTeams = new TreeSet<Team>();

	public Match(int matchNumber) {
		this.mMatchNumber = matchNumber;
		updateTeamCache();
	}

	/**
	 * creates a new match based on the passed parameter it removes all the
	 * previous teams but records the previous matches capacity
	 * 
	 * @param m
	 */
	public Match(Match m, List<Team> workingTeams) {
		mWorkingCapacity = m.mTeams.size();
		mMatchNumber = m.mMatchNumber;

		for (Team t : workingTeams) {
			if (m.getTeams().contains(t)) {
				this.addTeam(t);
			}

		}

	}

	public void addTeam(Team t) {

		if (mWorkingCapacity == UNDEFINED_CAPACITY || mWorkingCapacity >= mTeams.size() + 1) {
			for (Team tExisting : this.mTeams) {
				tExisting.addOpposition(t, this);
				t.addOpposition(tExisting, this);
			}

			this.mTeams.add(t);
			t.addMatch(this);
			updateTeamCache();
		} else {
			throw new IllegalArgumentException("this match is already full");
		}

	}

	public int getNumber() {
		return mMatchNumber;
	}

	public int getNumberOfTeams() {
		return mTeams.size();
	}

	private List<Team> mTeamCache;

	public List<Team> getTeams() {
		return Collections.unmodifiableList(mTeamCache);
	}

	public boolean hasTeam(Team team) {
		return mTeams.contains(team);
	}

	public void removeTeam(Team t) {
		if (mTeams.contains(t)) {
			t.notifyNotInMatch(this);
			mTeams.remove(t);
		} else {
			throw new IllegalArgumentException("passed team not in this match");
		}

		updateTeamCache();
	}

	private void updateTeamCache() {
		mTeamCache = new ArrayList<Team>(mTeams.size());
		mTeamCache.clear();
		mTeamCache.addAll(mTeams);
	}

}
