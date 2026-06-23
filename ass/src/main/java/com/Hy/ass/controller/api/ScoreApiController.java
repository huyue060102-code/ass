package com.Hy.ass.controller.api;

import com.Hy.ass.entity.Score;
import com.Hy.ass.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        Score score = scoreService.getScoreById(id);
        if (score == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "未找到该成绩记录");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result); // 返回 404
        }
        return ResponseEntity.ok(score); // 返回 200
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody Score score) {
        Map<String, Object> result = new HashMap<>();

        // 1. 基础非空校验：校验学生姓名与课程名称不能为空
        if (score.getStudentName() == null || score.getStudentName().trim().isEmpty() ||
                score.getCourseName() == null || score.getCourseName().trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "保存失败：学生姓名或课程名称不能为空！");
            return ResponseEntity.badRequest().body(result); // 返回 HTTP 400
        }

        // 2. 基础分数校验：分数不能为负数
        if (score.getScore() == null || score.getScore() < 0) {
            result.put("success", false);
            result.put("message", "保存失败：成绩分数不能小于 0 分！");
            return ResponseEntity.badRequest().body(result); // 返回 HTTP 400
        }

        // 3. 🔥 动态边界值校验：语数外上限 150，其他科目上限 100
        String course = score.getCourseName().trim();
        boolean isMainSubject = course.equals("语文") || course.equals("数学") || course.equals("英语")
                || course.equalsIgnoreCase("chinese") || course.equalsIgnoreCase("math") || course.equalsIgnoreCase("english");

        if (isMainSubject) {
            // 语数外分支：如果超过 150 分则拦截
            if (score.getScore() > 150) {
                result.put("success", false);
                result.put("message", "保存失败：" + course + "成绩最高不能超过 150 分！");
                return ResponseEntity.badRequest().body(result); // 返回 HTTP 400
            }
        } else {
            // 其他科目分支：如果超过 100 分则拦截
            if (score.getScore() > 100) {
                result.put("success", false);
                result.put("message", "保存失败：" + course + "成绩最高不能超过 100 分！");
                return ResponseEntity.badRequest().body(result); // 返回 HTTP 400
            }
        }

        // =============================================================
        // 🌟 只要是通过了上面重重安检的“正常成绩”，才会无感地来到下面这行真实保存代码
        // =============================================================
        scoreService.saveScore(score);
        result.put("success", true);
        result.put("message", "保存成功");
        return ResponseEntity.ok(result); // 返回 HTTP 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();

        // 4. 修复 BUG-003: 在删除之前，先验证该 ID 是否真实存在
        Score existingScore = scoreService.getScoreById(id);
        if (existingScore == null) {
            result.put("success", false);
            result.put("message", "删除失败：提示不存在该成绩记录！");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result); // 返回 HTTP 404
        }

        // 确认记录存在后，再执行真实删除动作
        scoreService.deleteScore(id);
        result.put("success", true);
        result.put("message", "删除成功");
        return ResponseEntity.ok(result); // 返回 HTTP 200
    }
}