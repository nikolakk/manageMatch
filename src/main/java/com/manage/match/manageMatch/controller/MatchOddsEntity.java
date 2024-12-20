package com.manage.match.manageMatch.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchOddsEntity {

    private Long matchId;

    private String specifier;

    private Double odd;

    public MatchOddsEntity(Long matchId, String specifier, Double odd) {
        this.matchId = matchId;
        this.specifier = specifier;
        this.odd = odd;
    }
}
