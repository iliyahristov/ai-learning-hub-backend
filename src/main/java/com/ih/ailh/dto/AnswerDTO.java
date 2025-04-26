package com.ih.ailh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
    private Long id;
    private Long questionId;
    private String answerText;
    private boolean correct;
    private String explanation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}