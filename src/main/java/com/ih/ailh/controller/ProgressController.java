package com.ih.ailh.controller;

import com.ih.ailh.dto.ProgressDTO;
import com.ih.ailh.security.UserDetailsImpl;
import com.ih.ailh.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping("/update/{materialId}")
    public ResponseEntity<ProgressDTO> updateProgress(@PathVariable Long materialId,
                                                      @RequestBody ProgressDTO progressDTO,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(progressService.updateOrCreateProgress(userDetails.getId(), materialId, progressDTO));
    }

    @GetMapping
    public ResponseEntity<List<ProgressDTO>> getUserProgress(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(progressService.getUserProgress(userDetails.getId()));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ProgressDTO>> getUserProgressForCourse(@PathVariable Long courseId,
                                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(progressService.getUserProgressForCourse(userDetails.getId(), courseId));
    }

    @GetMapping("/statistics")
    public ResponseEntity<Object> getUserStatistics(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(progressService.getUserStatistics(userDetails.getId()));
    }
}