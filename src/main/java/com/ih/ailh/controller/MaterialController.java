package com.ih.ailh.controller;

import com.ih.ailh.dto.MaterialDTO;
import com.ih.ailh.security.UserDetailsImpl;
import com.ih.ailh.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTO> getMaterialById(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.getMaterialById(id));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<MaterialDTO>> getMaterialsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(materialService.getMaterialsByCourseId(courseId));
    }

    @GetMapping("/user/latest")
    public ResponseEntity<List<MaterialDTO>> getLatestMaterialsForUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(materialService.getLatestMaterialsForUser(userDetails.getId()));
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<MaterialDTO> createMaterial(@RequestBody MaterialDTO materialDTO) {
        return ResponseEntity.ok(materialService.createMaterial(materialDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<MaterialDTO> updateMaterial(@PathVariable Long id, @RequestBody MaterialDTO materialDTO) {
        return ResponseEntity.ok(materialService.updateMaterial(id, materialDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.ok().build();
    }
}