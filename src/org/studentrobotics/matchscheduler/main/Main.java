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
        
        if(args.length!=6){
            System.out.println("Wrong number of arguments");
            System.exit(1);
        }
        
        else {
        
            // add some serializers
            serializers.add(new PrintMatchesSerializer());
            serializers.add(new TeamSerializer());

            int num_teams = Integer.parseInt(args[0]);
            int num_matches = Integer.parseInt(args[1]);
            int match_size = Integer.parseInt(args[2]);
            String byes = args[3];
            int min_byes = Integer.parseInt(args[4]);
            int max_byes = Integer.parseInt(args[5]);

            // setup match constraints
            MatchConstraints mc = new MatchConstraints();

            mc.setNumberOfTeams(num_teams);
            mc.setNumberOfMatches(num_matches);
            mc.setTeamsPerMatch(match_size);
            if(byes=="true"){
                mc.enableByes();
                mc.setMinByeSize(min_byes);
                mc.setMaxByeSize(max_byes);
            }

            /*mc.setNumberOfTeams(13);
            mc.setNumberOfMatches(23);
            mc.setTeamsPerMatch(4);
            mc.enableByes();
            mc.setMinByeSize(2);
            mc.setMaxByeSize(3);
            */

            MatchSchedulerImpl ms = new MatchSchedulerImpl(new MaxSizeByeResolutionStrategy());
            List<Match> matches = ms.schedule(mc);
    
            List<Match> finalMatches = MatchAnnealer.anneal(matches, 30);
        
            for (MatchSerializer serializer : serializers) {
                    serializer.serialize(finalMatches, Team.generateTeamList(finalMatches));
            }

        }

    }

}
