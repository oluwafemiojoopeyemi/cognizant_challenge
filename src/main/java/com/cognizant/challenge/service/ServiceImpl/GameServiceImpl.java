package com.cognizant.challenge.service.ServiceImpl;

import com.cognizant.challenge.dto.GameRequest;
import com.cognizant.challenge.dto.GameResponse;
import com.cognizant.challenge.dto.Score;
import com.cognizant.challenge.dto.ScoreResponse;
import com.cognizant.challenge.model.GameRecord;
import com.cognizant.challenge.repository.GameRepository;
import com.cognizant.challenge.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {


    @Autowired
    private GameRepository gameRepository;


    @Value("jdoodle.clientId")
    private String clientId;

    @Value("jdoodle.clientSecret")
    private String clientSecret;


    @Override
    public GameResponse submitGameRequest(GameRequest gameRequest) {
        GameRecord gameRecord  = new GameRecord();
        gameRecord.setName(gameRequest.getName());
        gameRecord.setDescription(gameRequest.getDescription());
        gameRecord.setSolution(gameRequest.getSolution());
        gameRecord.setTask(gameRequest.getTask());
getScore(gameRequest.getSolution());
gameRepository.save(gameRecord);
return null;

    }

    @Override
    public ScoreResponse findHighScores() {
        List<GameRecord> gameRecords = gameRepository.findAllOrOrderByScoreDesc();
        List<Score> scores = new ArrayList<>();
        gameRecords.stream().forEach(e->{
            Score score = new Score();
            score.setName(e.getName());
            score.setScore(e.getScore());
            score.setTask(e.getTask());
            scores.add(score);

        });
        ScoreResponse scoreResponse = new ScoreResponse();
        scoreResponse.setScores(scores);
        return scoreResponse;
    }

    private void getScore(String script) {

        String language = "java";
        String versionIndex = "0";

        try {
            URL url = new URL("https://api.jdoodle.com/v1/execute");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            String input = "{\"clientId\": \"" + clientId + "\",\"clientSecret\":\"" + clientSecret + "\",\"script\":\"" + script +
                    "\",\"language\":\"" + language + "\",\"versionIndex\":\"" + versionIndex + "\"} ";

            System.out.println(input);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Please check your inputs : HTTP error code : "+ connection.getResponseCode());
            }

            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String output;
            System.out.println("Output from JDoodle .... \n");
            while ((output = bufferedReader.readLine()) != null) {
                System.out.println(output);
            }

            connection.disconnect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
