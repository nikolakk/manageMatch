package com.manage.match.manageMatch.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@NoArgsConstructor
public class MatchEntity {

    private String description;

    private LocalDate matchDate;

    private LocalTime matchTime;

    private String teamA;

    private String teamB;

    private String sport;

    public MatchEntity(String description, LocalDate matchDate, LocalTime matchTime, String teamA, String teamB, String sport) {
        this.description = description;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.teamA = teamA;
        this.teamB = teamB;
        this.sport = sport;
    }
}
