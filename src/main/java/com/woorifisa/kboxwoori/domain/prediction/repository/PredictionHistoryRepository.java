package com.woorifisa.kboxwoori.domain.prediction.repository;

import com.woorifisa.kboxwoori.domain.prediction.entity.PredictionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PredictionHistoryRepository extends JpaRepository<PredictionHistory, Long> {
    @Query(value = "SELECT p FROM PredictionHistory p WHERE p.user.userId = :userId")
    Optional<List<PredictionHistory>> findByUserId(@Param("userId") String userId);

    @Query(value = "SELECT p FROM PredictionHistory p WHERE p.user.userId = :userId AND p.isCorrect = true")
    Optional<List<PredictionHistory>> findByUserIdAndIsCorrect(@Param("userId") String userId);
}
