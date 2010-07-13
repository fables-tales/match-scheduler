package org.studentrobotics.matchscheduler.serializers;

import java.util.List;

import org.studentrobotics.matchscheduler.Match;
import org.studentrobotics.matchscheduler.Team;

public class PrintMatchesSerializer implements MatchSerializer {

	@Override
	public void serialize(List<Match> matches, List<Team> teams) {
		for (Match m : matches) {
			if (m.getNumber() + 1 >= 10)
				System.out.print("Match " + (m.getNumber() + 1) + ": ");
			else
				System.out.print("Match " + (m.getNumber() + 1) + " : ");

			for (Team t : m.getTeams()) {
				if (t.getNumber() + 1 >= 10)
					System.out.print((t.getNumber() + 1) + " ");
				else
					System.out.print((t.getNumber() + 1) + "  ");
			}

			System.out.print("\n");
		}

	}
}
