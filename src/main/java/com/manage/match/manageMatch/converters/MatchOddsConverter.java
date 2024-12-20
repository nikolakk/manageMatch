package com.manage.match.manageMatch.converters;

import com.manage.match.manageMatch.controller.MatchOddsEntity;
import com.manage.match.manageMatch.model.Match;
import com.manage.match.manageMatch.model.MatchOdds;

public class MatchOddsConverter {
    public static MatchOdds covertToMatchOdds(MatchOddsEntity matchOddsEntity, Match match) {

        MatchOdds matchOdds = new MatchOdds();
        if (matchOddsEntity != null) {
            matchOdds.setMatch(match);
            matchOdds.setOdd(matchOddsEntity.getOdd());
            matchOdds.setSpecifier(matchOddsEntity.getSpecifier());
        }
        return matchOdds;
    }

    public static MatchOddsEntity covertToMatchOddsEntity(MatchOdds matchOdds) {

        MatchOddsEntity matchOddsEntity = new MatchOddsEntity();
        if (matchOdds != null) {
            matchOddsEntity.setMatchId(matchOdds.getMatch().getId());
            matchOddsEntity.setOdd(matchOdds.getOdd());
            matchOddsEntity.setSpecifier(matchOdds.getSpecifier());
        }
        return matchOddsEntity;
    }
}
