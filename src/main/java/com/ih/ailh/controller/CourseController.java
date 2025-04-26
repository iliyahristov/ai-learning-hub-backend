package com.ih.ailh.controller;

import com.ih.ailh.dto.CourseDTO;
import com.ih.ailh.model.Course;
import com.ih.ailh.security.UserDetailsImpl;
import com.ih.ailh.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllPublicCourses() {
        return ResponseEntity.ok(courseService.getAllPublicCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<CourseDTO>> getCoursesBySubject(@PathVariable String subject) {
        return ResponseEntity.ok(courseService.getCoursesBySubject(subject));
    }

    @GetMapping("/difficulty/{level}")
    public ResponseEntity<List<CourseDTO>> getCoursesByDifficulty(@PathVariable Course.DifficultyLevel level) {
        return ResponseEntity.ok(courseService.getCoursesByDifficulty(level));
    }

    @GetMapping("/user")
    public ResponseEntity<List<CourseDTO>> getUserCourses(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(courseService.getUserCourses(userDetails.getId()));
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(courseService.createCourse(courseDTO, userDetails.getId()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
}