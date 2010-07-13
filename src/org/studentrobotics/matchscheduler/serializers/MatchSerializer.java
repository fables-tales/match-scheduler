package org.studentrobotics.matchscheduler.serializers;

import java.util.List;

import org.studentrobotics.matchscheduler.Match;
import org.studentrobotics.matchscheduler.Team;

public interface MatchSerializer {
	public void serialize(List<Match> matches, List<Team> teams);
}
