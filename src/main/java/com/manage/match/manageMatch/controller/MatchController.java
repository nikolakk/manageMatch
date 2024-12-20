package com.manage.match.manageMatch.controller;

import com.manage.match.manageMatch.exceptions.MatchNotFoundException;
import com.manage.match.manageMatch.model.Match;
import com.manage.match.manageMatch.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private static final Logger logger = LoggerFactory.getLogger(MatchController.class);
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // GET all matches
    @GetMapping("/all")
    public ResponseEntity<List<MatchEntity>> getAllMatches() {
        logger.info("Received request to fetch all matches");
        List<MatchEntity> matches = matchService.getAllMatches();
        return ResponseEntity.ok(matches);
    }

    // GET match by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<MatchEntity> getMatchById(@PathVariable Long id) {
        logger.info("Received request to fetch match with id: {}", id);
        try {
            MatchEntity matchEntity = matchService.findById(id);
            return ResponseEntity.ok(matchEntity);
        } catch (MatchNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // POST create a new match
    @PostMapping ("/create")
    public ResponseEntity<MatchEntity> createMatch(@RequestBody Match match) {
        logger.info("Received request to create a new match: {}", match);
        MatchEntity createdMatch = matchService.save(match);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMatch);
    }

    // PUT update an existing match
    @PutMapping("/update/{id}")
    public ResponseEntity<MatchEntity> updateMatch(@PathVariable Long id, @RequestBody Match updatedMatch) {
        logger.info("Received request to update match with id: {}", id);
        try {
            MatchEntity updated = matchService.update(id, updatedMatch);
            return ResponseEntity.ok(updated);
        } catch (MatchNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE a match by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        logger.info("Received request to delete match with id: {}", id);
        try {
            matchService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (MatchNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
