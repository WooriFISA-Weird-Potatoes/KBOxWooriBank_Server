package com.woorifisa.kboxwoori.domain.quiz.repository;

import com.woorifisa.kboxwoori.domain.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCreatedAt(LocalDate createdAt);
}
