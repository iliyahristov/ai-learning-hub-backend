package com.ih.ailh.dto;

import com.ih.ailh.model.Course.DifficultyLevel;
import com.ih.ailh.model.Question.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private Long quizId;
    private String questionText;
    private QuestionType questionType;
    private DifficultyLevel difficultyLevel;
    private boolean aiGenerated;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AnswerDTO> answers;
}