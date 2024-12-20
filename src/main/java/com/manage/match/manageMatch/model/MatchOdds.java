package com.manage.match.manageMatch.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "MatchOdds")
public class MatchOdds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @Column(name = "specifier", nullable = false)
    private String specifier;

    @Column(name = "odd", nullable = false)
    private Double odd;

    public MatchOdds(Long id, Match match, String specifier, Double odd) {
        this.id = id;
        this.match = match;
        this.specifier = specifier;
        this.odd = odd;
    }
}
