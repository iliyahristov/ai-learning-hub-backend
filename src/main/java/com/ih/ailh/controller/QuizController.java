package com.ih.ailh.controller;

import com.ih.ailh.dto.QuizDTO;
import com.ih.ailh.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @GetMapping("/material/{materialId}")
    public ResponseEntity<QuizDTO> getQuizByMaterialId(@PathVariable Long materialId) {
        return ResponseEntity.ok(quizService.getQuizByMaterialId(materialId));
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quizDTO) {
        return ResponseEntity.ok(quizService.createQuiz(quizDTO));
    }

    @PostMapping("/evaluate/{quizId}")
    public ResponseEntity<BigDecimal> evaluateQuiz(@PathVariable Long quizId,
                                                   @RequestBody Map<Long, Long> userAnswers) {
        return ResponseEntity.ok(quizService.calculateScore(quizId, userAnswers));
    }
}