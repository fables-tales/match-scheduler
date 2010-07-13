package org.studentrobotics.matchscheduler.serializers;

import java.util.List;

import org.studentrobotics.matchscheduler.Match;
import org.studentrobotics.matchscheduler.Team;

public class TeamSerializer implements MatchSerializer {

	@Override
	public void serialize(List<Match> matches, List<Team> teams) {
		for (Team t : teams) {
			System.out.print("Team " + (t.getNumber() + 1) + " opposes: ");

			for (Team to : t.getOpposition()) {
				System.out.print((to.getNumber() + 1) + " ");
			}

			System.out.print(" A total of " + t.getOpposition().size() + " teams");
			System.out.print("\n");
		}

	}

}
