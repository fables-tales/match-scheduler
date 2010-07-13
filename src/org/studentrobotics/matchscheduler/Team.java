package org.studentrobotics.matchscheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Team implements Comparable<Team> {

	public static List<Team> generateTeamList(List<Match> matches) {
		SortedSet<Team> teams = new TreeSet<Team>();
		for (Match m : matches) {
			for (Team t : m.getTeams()) {
				teams.add(t);
			}

		}

		return Collections.unmodifiableList(new ArrayList<Team>(teams));
	}

	private List<Match> mMatches = new ArrayList<Match>();
	private int mNumber;
	private Map<Team, List<Match>> mOpponents = new HashMap<Team, List<Match>>();

	public Team(int teamNumber) {
		mNumber = teamNumber;
		assert this.equals(this);
	}

	public Team(Team t) {
		mNumber = t.mNumber;
		assert this.equals(this);
	}

	public void addMatch(Match match) {
		if (mMatches.contains(match)) {
			throw new IllegalArgumentException("this team is already in match: "
					+ match.getNumber());
		} else {
			mMatches.add(match);
		}
	}

	public void addOpposition(Team t, Match m) {
		if (!mOpponents.containsKey(t)) {
			mOpponents.put(t, new ArrayList<Match>());
		}

		List<Match> opposeMatches = mOpponents.get(t);
		opposeMatches.add(m);

	}

	@Override
	public int compareTo(Team o) {
		if (mNumber < o.mNumber) {
			return -1;
		} else if (mNumber == o.mNumber) {
			return 0;
		} else {
			return 1;
		}

	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Team && ((Team) obj).mNumber == mNumber;
	}

	public List<Match> getMatches() {
		return Collections.unmodifiableList(mMatches);
	}

	public int getNumber() {
		return mNumber;
	}

	public int getNumberOfMatches() {
		return mMatches.size();
	}

	public int getNumberOfOpponents() {
		return mOpponents.keySet().size();
	}

	/**
	 * notify the team is not in the match
	 * 
	 * @param m
	 */
	public void notifyNotInMatch(Match m) {
		List<Team> toRemoveOpponents = new ArrayList<Team>();

		for (Team t : mOpponents.keySet()) {
			List<Match> opposeMatches = mOpponents.get(t);
			opposeMatches.remove(m);

			if (opposeMatches.size() == 0) {
				toRemoveOpponents.add(t);
			}

		}

		for (Team t : toRemoveOpponents) {
			mOpponents.remove(t);
		}

		mMatches.remove(m);
	}

	public List<Team> getOpposition() {
		// return a copy of the set of opponents, sorted
		List<Team> sortOpponents = new ArrayList<Team>(mOpponents.keySet());
		Collections.sort(sortOpponents);

		// DEBUG check the collection is the same size as the number of
		// opponents we have
		assert sortOpponents.size() == this.getNumberOfOpponents();

		return Collections.unmodifiableList(sortOpponents);
	}

}
