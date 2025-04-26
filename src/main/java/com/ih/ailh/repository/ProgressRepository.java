package com.ih.ailh.repository;

import com.ih.ailh.model.Material;
import com.ih.ailh.model.Progress;
import com.ih.ailh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Optional<Progress> findByUserAndMaterial(User user, Material material);

    List<Progress> findByUser(User user);

    @Query("SELECT p FROM Progress p WHERE p.user.id = :userId AND p.material.course.id = :courseId")
    List<Progress> findUserProgressForCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);

    @Query("SELECT COUNT(p), AVG(p.score), SUM(p.completionTime) FROM Progress p WHERE p.user.id = :userId")
    Object[] getUserStatistics(@Param("userId") Long userId);
}