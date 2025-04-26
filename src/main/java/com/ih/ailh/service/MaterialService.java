package com.ih.ailh.service;

import com.ih.ailh.dto.MaterialDTO;
import com.ih.ailh.model.Course;
import com.ih.ailh.model.Material;
import com.ih.ailh.repository.CourseRepository;
import com.ih.ailh.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public MaterialDTO createMaterial(MaterialDTO materialDTO) {
        Course course = courseRepository.findById(materialDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Material material = modelMapper.map(materialDTO, Material.class);
        material.setCourse(course);

        Material savedMaterial = materialRepository.save(material);
        return convertToDTO(savedMaterial);
    }

    public MaterialDTO getMaterialById(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));
        return convertToDTO(material);
    }

    public List<MaterialDTO> getMaterialsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return materialRepository.findByCourseOrderByCreatedAt(course).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MaterialDTO> getLatestMaterialsForUser(Long userId) {
        return materialRepository.findLatestForUser(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MaterialDTO updateMaterial(Long id, MaterialDTO materialDTO) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        modelMapper.map(materialDTO, material);

        Material updatedMaterial = materialRepository.save(material);
        return convertToDTO(updatedMaterial);
    }

    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }

    private MaterialDTO convertToDTO(Material material) {
        MaterialDTO dto = modelMapper.map(material, MaterialDTO.class);
        dto.setCourseId(material.getCourse().getId());
        dto.setCourseTitle(material.getCourse().getTitle());
        return dto;
    }
}