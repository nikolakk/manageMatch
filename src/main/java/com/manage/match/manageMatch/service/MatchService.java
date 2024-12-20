package com.manage.match.manageMatch.service;

import com.manage.match.manageMatch.controller.MatchEntity;
import com.manage.match.manageMatch.converters.MatchConverter;
import com.manage.match.manageMatch.exceptions.MatchNotFoundException;
import com.manage.match.manageMatch.model.Match;
import com.manage.match.manageMatch.repository.MatchRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);
    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<MatchEntity> getAllMatches() {
        logger.info("Fetching all matches");
        List<Match> matches = matchRepository.findAll();
        return matches.stream().map(MatchConverter::covertToMatchEntity).collect(Collectors.toList());

    }

    public MatchEntity findById(Long id) {
        logger.info("Fetching match with id: {}", id);
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException("Match with id " + id + " not found"));
        return MatchConverter.covertToMatchEntity(match);
    }

    @Transactional
    public MatchEntity save(Match match) {
        logger.info("Saving match: {}", match);
        validateMatch(match);
        Match savedMatch = matchRepository.save(match);
        return MatchConverter.covertToMatchEntity(savedMatch);
    }

    @Transactional
    public MatchEntity update(Long id, Match updatedMatch) {
        logger.info("Updating match with id: {}", id);

        Match existingMatch = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException("Match with id " + id + " not found"));

        // Update fields
        existingMatch.setDescription(updatedMatch.getDescription());
        existingMatch.setMatchDate(updatedMatch.getMatchDate());
        existingMatch.setMatchTime(updatedMatch.getMatchTime());
        existingMatch.setTeamA(updatedMatch.getTeamA());
        existingMatch.setTeamB(updatedMatch.getTeamB());
        existingMatch.setSport(updatedMatch.getSport());

        validateMatch(existingMatch);

        Match savedMatch = matchRepository.save(existingMatch);
        return MatchConverter.covertToMatchEntity(savedMatch);
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Deleting match with id: {}", id);
        if (!matchRepository.existsById(id)) {
            throw new MatchNotFoundException("Match with id " + id + " does not exist");
        }
        matchRepository.deleteById(id);
    }

    private void validateMatch(Match match) {
        logger.debug("Validating match: {}", match);
        if (match.getTeamA() == null || match.getTeamA().isEmpty()) {
            throw new IllegalArgumentException("Team A cannot be null or empty");
        }
        if (match.getTeamB() == null || match.getTeamB().isEmpty()) {
            throw new IllegalArgumentException("Team B cannot be null or empty");
        }
        if (match.getTeamA().equals(match.getTeamB())) {
            throw new IllegalArgumentException("Team A and Team B cannot be the same");
        }
        if (match.getMatchDate() == null) {
            throw new IllegalArgumentException("Match date cannot be null");
        }
    }
}
