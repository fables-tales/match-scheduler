package org.studentrobotics.matchscheduler.byeresolver;

import java.util.List;

import org.studentrobotics.matchscheduler.Match;
import org.studentrobotics.matchscheduler.Team;
import org.studentrobotics.matchscheduler.scheduling.MatchConstraints;

/**
 * strategy for resolving byes, note that the contract for resolution is that it
 * will not generate new matches, but can remove teams from matches, thus all
 * matches should be full when passed in
 * 
 * 
 */
public interface ByeResolutionStrategy {

	public void resolve(List<Match> matches, List<Team> teams, MatchConstraints mc);

}
