package com.ih.ailh.service;

import com.ih.ailh.dto.ProgressDTO;
import com.ih.ailh.model.Material;
import com.ih.ailh.model.Progress;
import com.ih.ailh.model.User;
import com.ih.ailh.repository.MaterialRepository;
import com.ih.ailh.repository.ProgressRepository;
import com.ih.ailh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgressService {
    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final MaterialRepository materialRepository;
    private final ModelMapper modelMapper;

    public ProgressDTO updateOrCreateProgress(Long userId, Long materialId, ProgressDTO progressDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        Optional<Progress> existingProgress = progressRepository.findByUserAndMaterial(user, material);
        Progress progress;

        if (existingProgress.isPresent()) {
            progress = existingProgress.get();
        } else {
            progress = new Progress();
            progress.setUser(user);
            progress.setMaterial(material);
        }

        if (progressDTO.getStatus() != null) {
            progress.setStatus(progressDTO.getStatus());
        }
        if (progressDTO.getScore() != null) {
            progress.setScore(progressDTO.getScore());
        }
        if (progressDTO.getCompletionTime() != null) {
            progress.setCompletionTime(progressDTO.getCompletionTime());
        }
        if (progressDTO.getFeedback() != null) {
            progress.setFeedback(progressDTO.getFeedback());
        }
        if (progressDTO.getAiRecommendations() != null) {
            progress.setAiRecommendations(progressDTO.getAiRecommendations());
        }

        progress.setLastInteraction(LocalDateTime.now());

        Progress savedProgress = progressRepository.save(progress);
        return convertToDTO(savedProgress);
    }

    public List<ProgressDTO> getUserProgress(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return progressRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProgressDTO> getUserProgressForCourse(Long userId, Long courseId) {
        return progressRepository.findUserProgressForCourse(userId, courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Object getUserStatistics(Long userId) {
        Object[] stats = (Object[]) progressRepository.getUserStatistics(userId)[0];
        return stats;
    }

    private ProgressDTO convertToDTO(Progress progress) {
        ProgressDTO dto = modelMapper.map(progress, ProgressDTO.class);
        dto.setUserId(progress.getUser().getId());
        dto.setMaterialId(progress.getMaterial().getId());
        dto.setMaterialTitle(progress.getMaterial().getTitle());
        return dto;
    }
}