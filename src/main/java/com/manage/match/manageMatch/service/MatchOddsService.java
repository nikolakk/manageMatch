package com.manage.match.manageMatch.service;


import com.manage.match.manageMatch.controller.MatchOddsEntity;
import com.manage.match.manageMatch.converters.MatchOddsConverter;
import com.manage.match.manageMatch.exceptions.MatchNotFoundException;
import com.manage.match.manageMatch.model.Match;
import com.manage.match.manageMatch.model.MatchOdds;
import com.manage.match.manageMatch.repository.MatchOddsRepository;
import com.manage.match.manageMatch.repository.MatchRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchOddsService {

    private static final Logger logger = LoggerFactory.getLogger(MatchOddsService.class);
    private final MatchOddsRepository matchOddsRepository;
    private final MatchRepository matchRepository;

    @Autowired
    public MatchOddsService(MatchOddsRepository matchOddsRepository, MatchRepository matchRepository) {
        this.matchOddsRepository = matchOddsRepository;
        this.matchRepository = matchRepository;
    }

    public List<MatchOdds> getAllMatchOdds() {
        logger.info("Fetching all match odds");
        return matchOddsRepository.findAll();
    }

    public MatchOdds findById(Long id) {
        logger.info("Fetching match odds with id: {}", id);
        return matchOddsRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException("MatchOdds with id " + id + " not found"));
    }

    @Transactional
    public MatchOddsEntity save(MatchOddsEntity matchOddsEntity) {
        Match match = matchRepository.findById(matchOddsEntity.getMatchId())
                .orElseThrow(() -> new MatchNotFoundException("Meeting room not found."));
        MatchOdds matchOdds = MatchOddsConverter.covertToMatchOdds(matchOddsEntity, match);
        logger.info("Saving match odds: {}", matchOdds);
        MatchOdds saved = matchOddsRepository.save(matchOdds);
        return MatchOddsConverter.covertToMatchOddsEntity(saved);
    }

    @Transactional
    public MatchOddsEntity update(Long id, MatchOddsEntity matchOddsEntity) {
        logger.info("Updating match odds with id: {}", id);

        MatchOdds existingMatchOdds = matchOddsRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException("MatchOdds with id " + id + " not found"));

        existingMatchOdds.setSpecifier(matchOddsEntity.getSpecifier());
        existingMatchOdds.setOdd(matchOddsEntity.getOdd());

        MatchOdds saved = matchOddsRepository.save(existingMatchOdds);
        return MatchOddsConverter.covertToMatchOddsEntity(saved);
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Deleting match odds with id: {}", id);
        if (!matchOddsRepository.existsById(id)) {
            throw new MatchNotFoundException("MatchOdds with id " + id + " does not exist");
        }
        matchOddsRepository.deleteById(id);
    }
}
