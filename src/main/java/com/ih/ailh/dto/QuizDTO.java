package com.ih.ailh.dto;

import com.ih.ailh.model.Course.DifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO {
    private Long id;
    private Long materialId;
    private String title;
    private String description;
    private boolean aiGenerated;
    private DifficultyLevel difficultyLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<QuestionDTO> questions;
}