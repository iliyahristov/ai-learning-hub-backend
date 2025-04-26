package com.ih.ailh.repository;

import com.ih.ailh.model.Course;
import com.ih.ailh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByIsPublicTrue();

    @Query("SELECT c FROM Course c WHERE c.subject = :subject AND c.isPublic = true")
    List<Course> findBySubjectAndPublic(@Param("subject") String subject);

    @Query("SELECT c FROM Course c WHERE c.difficultyLevel = :level AND c.isPublic = true")
    List<Course> findByDifficultyLevel(@Param("level") Course.DifficultyLevel level);

    List<Course> findByCreatedBy(User creator);

    @Query("SELECT c FROM Course c " +
            "JOIN c.materials m " +
            "JOIN m.progressSet p " +
            "WHERE p.user.id = :userId " +
            "ORDER BY p.lastInteraction DESC")
    List<Course> findUserCourses(@Param("userId") Long userId);
}