package com.ih.ailh.dto;

import com.ih.ailh.model.Course.DifficultyLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenerateMaterialRequestDTO {
    private Long courseId;

    @NotBlank
    private String topic;

    private DifficultyLevel difficultyLevel;

    private String aiPreferences;
}