package com.ih.ailh.service;

import com.ih.ailh.dto.CourseDTO;
import com.ih.ailh.model.Course;
import com.ih.ailh.model.User;
import com.ih.ailh.repository.CourseRepository;
import com.ih.ailh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<CourseDTO> getAllPublicCourses() {
        return courseRepository.findByIsPublicTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return convertToDTO(course);
    }

    public List<CourseDTO> getCoursesBySubject(String subject) {
        return courseRepository.findBySubjectAndPublic(subject).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getCoursesByDifficulty(Course.DifficultyLevel level) {
        return courseRepository.findByDifficultyLevel(level).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getUserCourses(Long userId) {
        return courseRepository.findUserCourses(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO createCourse(CourseDTO courseDTO, Long creatorId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = modelMapper.map(courseDTO, Course.class);
        course.setCreatedBy(creator);

        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        modelMapper.map(courseDTO, course);

        Course updatedCourse = courseRepository.save(course);
        return convertToDTO(updatedCourse);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = modelMapper.map(course, CourseDTO.class);
        if (course.getCreatedBy() != null) {
            dto.setCreatedById(course.getCreatedBy().getId());
            dto.setCreatedByName(course.getCreatedBy().getName());
        }
        dto.setMaterialCount(course.getMaterials().size());
        return dto;
    }
}