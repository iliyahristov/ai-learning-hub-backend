package com.ih.ailh.dto;

import com.ih.ailh.model.Course.DifficultyLevel;
import com.ih.ailh.model.Material.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDTO {
    private Long id;
    private Long courseId;
    private String courseTitle;
    private String title;
    private String content;
    private ContentType contentType;
    private DifficultyLevel difficultyLevel;
    private Integer estimatedTimeMinutes;
    private String aiModelUsed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String progressStatus;
    private Double score;
}