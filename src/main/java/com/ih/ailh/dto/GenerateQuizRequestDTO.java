package com.ih.ailh.dto;

import com.ih.ailh.model.Course.DifficultyLevel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GenerateQuizRequestDTO {
    @NotNull
    private Long materialId;

    private int numberOfQuestions = 5;

    private DifficultyLevel difficultyLevel;
}