package com.ih.ailh.repository;

import com.ih.ailh.model.Course;
import com.ih.ailh.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByCourseOrderByCreatedAt(Course course);

    @Query("SELECT m FROM Material m JOIN m.progressSet p WHERE p.user.id = :userId ORDER BY m.createdAt DESC")
    List<Material> findLatestForUser(@Param("userId") Long userId);

    @Query("SELECT m.aiModelUsed, COUNT(m) FROM Material m " +
            "WHERE m.contentType = 'AI_GENERATED' AND m.aiModelUsed IS NOT NULL " +
            "GROUP BY m.aiModelUsed")
    List<Object[]> getAIGeneratedStats();
}