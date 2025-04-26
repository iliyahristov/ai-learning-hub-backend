package com.ih.ailh.dto;

import com.ih.ailh.model.AIInteraction.InteractionType;
import com.ih.ailh.model.AIInteraction.SatisfactionLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIInteractionDTO {
    private Long id;
    private Long userId;
    private String aiModel;
    private InteractionType interactionType;
    private String prompt;
    private Integer tokensUsed;
    private SatisfactionLevel resultSatisfaction;
    private LocalDateTime createdAt;
}