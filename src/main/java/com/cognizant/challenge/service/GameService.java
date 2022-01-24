package com.cognizant.challenge.service;

import com.cognizant.challenge.dto.GameRequest;
import com.cognizant.challenge.dto.GameResponse;
import com.cognizant.challenge.dto.ScoreResponse;
import com.cognizant.challenge.model.GameRecord;

public interface GameService {

    public GameResponse submitGameRequest(GameRequest gameRequest);

    public ScoreResponse  findHighScores();


}
