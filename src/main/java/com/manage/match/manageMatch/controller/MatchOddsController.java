package com.manage.match.manageMatch.controller;

import com.manage.match.manageMatch.converters.MatchOddsConverter;
import com.manage.match.manageMatch.exceptions.MatchNotFoundException;
import com.manage.match.manageMatch.model.MatchOdds;
import com.manage.match.manageMatch.service.MatchOddsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/match-odds")
public class MatchOddsController {

    private static final Logger logger = LoggerFactory.getLogger(MatchOddsController.class);

    private final MatchOddsService matchOddsService;

    public MatchOddsController(MatchOddsService matchOddsService) {
        this.matchOddsService = matchOddsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MatchOddsEntity>> getAllMatchOdds() {
        logger.info("Received request to fetch all match odds");
        List<MatchOdds> matchOddsList = matchOddsService.getAllMatchOdds();
        List<MatchOddsEntity> matchOddsEntities = matchOddsList.stream().map(MatchOddsConverter::covertToMatchOddsEntity).collect(Collectors.toList());
        return ResponseEntity.ok(matchOddsEntities);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MatchOddsEntity> getMatchOddsById(@PathVariable Long id) {
        logger.info("Received request to fetch match odds with id: {}", id);
        try {
            MatchOdds matchOdds = matchOddsService.findById(id);
            return ResponseEntity.ok(MatchOddsConverter.covertToMatchOddsEntity(matchOdds));
        } catch (MatchNotFoundException e) {
            logger.error("MatchOdds with id {} not found", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<MatchOddsEntity> createMatchOdds(@RequestBody MatchOddsEntity matchOdds) {
        logger.info("Received request to create match odds: {}", matchOdds);
        MatchOddsEntity savedMatchOdds = matchOddsService.save(matchOdds);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMatchOdds);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MatchOddsEntity> updateMatchOdds(@PathVariable Long id, @RequestBody MatchOddsEntity matchOdds) {
        logger.info("Received request to update match odds with id: {}", id);
        try {
            MatchOddsEntity updatedMatchOdds = matchOddsService.update(id, matchOdds);
            return ResponseEntity.ok(updatedMatchOdds);
        } catch (MatchNotFoundException e) {
            logger.error("MatchOdds with id {} not found for update", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMatchOdds(@PathVariable Long id) {
        logger.info("Received request to delete match odds with id: {}", id);
        try {
            matchOddsService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (MatchNotFoundException e) {
            logger.error("MatchOdds with id {} not found for deletion", id, e);
            return ResponseEntity.notFound().build();
        }
    }
}


