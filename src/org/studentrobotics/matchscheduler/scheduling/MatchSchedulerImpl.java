package org.studentrobotics.matchscheduler.scheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.studentrobotics.matchscheduler.Match;
import org.studentrobotics.matchscheduler.Team;
import org.studentrobotics.matchscheduler.byeresolver.ByeResolutionStrategy;

public class MatchSchedulerImpl implements MatchScheduler {
	private static final Random RNG = new Random();
	private ByeResolutionStrategy mByeResoultionStrategy;
	private List<Match> mMatches = new ArrayList<Match>();
	private List<Team> mTeams;

	public MatchSchedulerImpl(ByeResolutionStrategy s) {
		this.mByeResoultionStrategy = s;
	}

	@Override
	public List<Match> schedule(MatchConstraints matchConstraints) {
		generateTeams(matchConstraints);
		setupMatches(matchConstraints);

		if (matchConstraints.getAllowByes()) {
			mByeResoultionStrategy.resolve(mMatches, mTeams, matchConstraints);
		}

		return mMatches;
	}

	private void generateTeams(MatchConstraints matchConstraints) {
		mTeams = new ArrayList<Team>(matchConstraints.getNumberOfTeams());

		for (int i = 0; i < matchConstraints.getNumberOfTeams(); i++) {
			mTeams.add(new Team(i));
		}

	}

	private void setupMatches(MatchConstraints mc) {
		List<Team> workingTeams = new ArrayList<Team>(mTeams);

		for (int i = 0; i < mc.getNumberOfMatches(); i++) {
			Match m = new Match(i);

			for (int j = 0; j < mc.getTeamsPerMatch(); j++) {
				updateMatch(workingTeams, m);

				if (workingTeams.size() == 0) {
					workingTeams = new ArrayList<Team>(mTeams);
				}

			}

			mMatches.add(m);
		}

	}

	private void updateMatch(List<Team> workingTeams, Match m) {
		Team t;
		int choice;

		do {
			choice = RNG.nextInt(workingTeams.size());
			t = workingTeams.get(choice);
		} while (m.hasTeam(t));

		m.addTeam(t);
		workingTeams.remove(choice);
	}

}
