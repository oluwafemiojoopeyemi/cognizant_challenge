package com.cognizant.challenge.repository;

import com.cognizant.challenge.model.GameRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<GameRecord, Long> {

   List<GameRecord> findAllOrOrderByScoreDesc();

}
