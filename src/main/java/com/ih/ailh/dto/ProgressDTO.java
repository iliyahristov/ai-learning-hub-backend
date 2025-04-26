package com.ih.ailh.dto;

import com.ih.ailh.model.Progress.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressDTO {
    private Long id;
    private Long userId;
    private Long materialId;
    private String materialTitle;
    private Status status;
    private BigDecimal score;
    private Integer completionTime;
    private String feedback;
    private String aiRecommendations;
    private LocalDateTime lastInteraction;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}