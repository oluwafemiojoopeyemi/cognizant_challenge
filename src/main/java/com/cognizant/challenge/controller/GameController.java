package com.cognizant.challenge.controller;

import com.cognizant.challenge.dto.GameRequest;
import com.cognizant.challenge.dto.GameResponse;
import com.cognizant.challenge.dto.ScoreResponse;
import com.cognizant.challenge.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoint to submit and query orders.
 */
@RestController("orders")
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping("/submit")
    @PostMapping
    public ResponseEntity<GameResponse> submit(@RequestBody GameRequest gameRequest) {

         GameResponse gameResponse = gameService.submitGameRequest(gameRequest);
        return ResponseEntity.ok(gameResponse);
    }

    @RequestMapping("/highscores")
    public ResponseEntity<ScoreResponse> getHighScores() {

        ScoreResponse scoreResponse = gameService.findHighScores();
        return ResponseEntity.ok(scoreResponse);
    }


}
