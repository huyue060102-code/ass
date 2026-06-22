package com.Hy.ass.controller.api;

import com.Hy.ass.entity.Score;
import com.Hy.ass.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/scores")
public class ScoreApiController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping
    public List<Score> list() {
        return scoreService.getAllScores();
    }

    @GetMapping("/{id}")
    public Score getById(@PathVariable Integer id) {
        return scoreService.getScoreById(id);
    }

    @PostMapping
    public Map<String, Object> save(@RequestBody Score score) {
        scoreService.saveScore(score);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "保存成功");
        return result;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Integer id) {
        scoreService.deleteScore(id);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "删除成功");
        return result;
    }
}