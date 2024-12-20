package com.manage.match.manageMatch.repository;

import com.manage.match.manageMatch.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
