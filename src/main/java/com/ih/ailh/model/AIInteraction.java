package com.ih.ailh.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_interactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    private String aiModel;

    @Enumerated(EnumType.STRING)
    private InteractionType interactionType;

    @Column(columnDefinition = "TEXT")
    private String prompt;

    private Integer tokensUsed;

    @Enumerated(EnumType.STRING)
    private SatisfactionLevel resultSatisfaction = SatisfactionLevel.NOT_RATED;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum InteractionType {
        MATERIAL_GENERATION, QUIZ_GENERATION, PROGRESS_ANALYSIS, VISUALIZATION
    }

    public enum SatisfactionLevel {
        NOT_RATED, POOR, AVERAGE, GOOD, EXCELLENT
    }
}