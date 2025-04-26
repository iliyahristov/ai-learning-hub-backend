package com.ih.ailh.controller;

import com.ih.ailh.dto.GenerateMaterialRequestDTO;
import com.ih.ailh.dto.GenerateQuizRequestDTO;
import com.ih.ailh.dto.MaterialDTO;
import com.ih.ailh.dto.QuizDTO;
import com.ih.ailh.security.UserDetailsImpl;
import com.ih.ailh.service.AIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @PostMapping("/generate-material")
    public ResponseEntity<MaterialDTO> generateMaterial(@Valid @RequestBody GenerateMaterialRequestDTO request,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(aiService.generateMaterial(request, userDetails.getId()));
    }

    @PostMapping("/generate-quiz")
    public ResponseEntity<QuizDTO> generateQuiz(@Valid @RequestBody GenerateQuizRequestDTO request,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(aiService.generateQuiz(request, userDetails.getId()));
    }

    @PostMapping("/generate-diagram")
    public ResponseEntity<String> generateDiagram(@RequestBody String content,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(aiService.generateDiagram(content, userDetails.getId()));
    }

    @GetMapping("/analyze-progress")
    public ResponseEntity<String> analyzeProgress(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(aiService.analyzeProgress(userDetails.getId()));
    }
}