package org.studentrobotics.matchscheduler.main;

import java.util.ArrayList;
import java.util.List;

import org.studentrobotics.matchscheduler.Match;
import org.studentrobotics.matchscheduler.Team;
import org.studentrobotics.matchscheduler.byeresolver.MaxSizeByeResolutionStrategy;
import org.studentrobotics.matchscheduler.scheduling.MatchConstraints;
import org.studentrobotics.matchscheduler.scheduling.MatchSchedulerImpl;
import org.studentrobotics.matchscheduler.scheduling.annealing.MatchAnnealer;
import org.studentrobotics.matchscheduler.serializers.MatchSerializer;
import org.studentrobotics.matchscheduler.serializers.PrintMatchesSerializer;
import org.studentrobotics.matchscheduler.serializers.TeamSerializer;

public class Main {
	private static List<MatchSerializer> serializers = new ArrayList<MatchSerializer>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// add some serializers
		serializers.add(new PrintMatchesSerializer());
		serializers.add(new TeamSerializer());

		// setup match constraints
		MatchConstraints mc = new MatchConstraints();
		mc.setNumberOfTeams(13);
		mc.setNumberOfMatches(23);
		mc.setTeamsPerMatch(4);
		mc.enableByes();
		mc.setMinByeSize(2);
		mc.setMaxByeSize(3);

		MatchSchedulerImpl ms = new MatchSchedulerImpl(new MaxSizeByeResolutionStrategy());
		List<Match> matches = ms.schedule(mc);

		List<Match> finalMatches = MatchAnnealer.anneal(matches, 30);

		for (MatchSerializer serializer : serializers) {
			serializer.serialize(finalMatches, Team.generateTeamList(finalMatches));
		}

	}

}
