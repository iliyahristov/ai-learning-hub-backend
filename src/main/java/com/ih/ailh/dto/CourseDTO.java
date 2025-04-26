package com.ih.ailh.dto;

import com.ih.ailh.model.Course.DifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String title;
    private String subject;
    private String description;
    private DifficultyLevel difficultyLevel;
    private boolean isPublic;
    private Long createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int materialCount;
    private int completedMaterialCount;
}