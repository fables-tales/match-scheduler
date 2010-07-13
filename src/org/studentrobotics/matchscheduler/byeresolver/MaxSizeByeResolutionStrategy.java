package org.studentrobotics.matchscheduler.byeresolver;

import java.util.ArrayList;
import java.util.List;

import org.studentrobotics.matchscheduler.Match;
import org.studentrobotics.matchscheduler.Team;
import org.studentrobotics.matchscheduler.scheduling.MatchConstraints;

/**
 * will attempt to solve to maximise the size of a bye match borrowing from an
 * earlier match if necessary
 */
public class MaxSizeByeResolutionStrategy implements ByeResolutionStrategy {

	@Override
	public void resolve(List<Match> matches, List<Team> teams, MatchConstraints mc) {
		// debugging assert that all the matches are full
		/*for (Match m : matches) {
			assert m.getNumberOfTeams() == mc.getTeamsPerMatch();
		}
		
		for (Team t : teams) {
			System.out.println(t.getNumberOfMatches() + " " + t.getNumber());
		}*/

		int maxMatchesPerTeam = (mc.getNumberOfMatches() * mc.getTeamsPerMatch())
				/ mc.getNumberOfTeams();

		for (int i = matches.size() - 1; i >= 0; i--) {
			Match m = matches.get(i);
			cullTeams(maxMatchesPerTeam, m, mc);
		}

	}

	private void cullTeams(int maxMatchesPerTeam, Match m, MatchConstraints mc) {
		int matchSize = m.getNumberOfTeams();
		
		List<Team> toRemove = new ArrayList<Team>();

		for (Team t : m.getTeams()) {
			if (t.getNumberOfMatches() > maxMatchesPerTeam) {
				toRemove.add(t);
				matchSize--;

				if (matchSize <= mc.getMaxByeSize()) {
					break;
				}

			}

		}

		for (Team t : toRemove) {
			m.removeTeam(t);
		}
		
	}
	
}
