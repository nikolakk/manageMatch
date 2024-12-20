package com.manage.match.manageMatch.converters;

import com.manage.match.manageMatch.controller.MatchEntity;
import com.manage.match.manageMatch.model.Match;
import com.manage.match.manageMatch.model.SportEnum;

public class MatchConverter {
    public static Match covertToMatch(MatchEntity matchEntity) {

        Match match = new Match();
        if (match != null) {
           match.setMatchTime(matchEntity.getMatchTime());
           match.setMatchDate(matchEntity.getMatchDate());
           match.setDescription(matchEntity.getDescription());
           match.setSport(SportEnum.valueOf(matchEntity.getSport()));
           match.setTeamA(matchEntity.getTeamA());
           match.setTeamB(matchEntity.getTeamB());
        }
        return match;
    }


    public static MatchEntity covertToMatchEntity(Match match) {

        MatchEntity matchEntity = new MatchEntity();
        if (matchEntity != null) {
            matchEntity.setMatchTime(match.getMatchTime());
            matchEntity.setMatchDate(match.getMatchDate());
            matchEntity.setDescription(match.getDescription());
            matchEntity.setSport(match.getSport().name());
            matchEntity.setTeamA(match.getTeamA());
            matchEntity.setTeamB(match.getTeamB());
        }
        return matchEntity;
    }
}
