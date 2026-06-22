package com.Hy.ass.service;

import com.Hy.ass.entity.Score;
import com.Hy.ass.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    public Score getScoreById(Integer id) {
        return scoreRepository.findById(id).orElse(null);
    }

    public void saveScore(Score score) {
        scoreRepository.save(score);
    }

    public void deleteScore(Integer id) {
        scoreRepository.deleteById(id);
    }
}