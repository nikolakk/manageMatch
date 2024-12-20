package com.manage.match.manageMatch.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(name = "match_date", nullable = false)
    private LocalDate matchDate;

    @Column(name = "match_time", nullable = false)
    private LocalTime matchTime;

    @JoinColumn(name = "team_a", nullable = false)
    private String teamA;

    @JoinColumn(name = "team_a", nullable = false)
    private String teamB;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SportEnum sport;

    public Match(Long id, String description, LocalDate matchDate, LocalTime matchTime, String teamA, String teamB, SportEnum sport) {
        this.id = id;
        this.description = description;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.teamA = teamA;
        this.teamB = teamB;
        this.sport = sport;
    }
}
