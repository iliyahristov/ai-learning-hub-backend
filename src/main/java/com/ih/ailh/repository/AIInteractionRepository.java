package com.ih.ailh.repository;

import com.ih.ailh.model.AIInteraction;
import com.ih.ailh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIInteractionRepository extends JpaRepository<AIInteraction, Long> {
    List<AIInteraction> findByUserOrderByCreatedAtDesc(User user);

    @Query("SELECT ai.aiModel, ai.interactionType, COUNT(ai), AVG(ai.tokensUsed) " +
            "FROM AIInteraction ai " +
            "GROUP BY ai.aiModel, ai.interactionType")
    List<Object[]> getModelUsageStats();

    @Query("SELECT ai.aiModel, " +
            "AVG(CASE WHEN ai.resultSatisfaction = 'EXCELLENT' THEN 5 " +
            "         WHEN ai.resultSatisfaction = 'GOOD' THEN 4 " +
            "         WHEN ai.resultSatisfaction = 'AVERAGE' THEN 3 " +
            "         WHEN ai.resultSatisfaction = 'POOR' THEN 2 " +
            "         ELSE 0 END) as satisfaction_score, " +
            "COUNT(ai) " +
            "FROM AIInteraction ai " +
            "GROUP BY ai.aiModel")
    List<Object[]> getUserSatisfactionStats();
}