package com.woorifisa.kboxwoori.domain.quiz.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", nullable = false)
    private char answer;

    @Column(name = "choice1", nullable = false)
    private String choice1;

    @Column(name = "choice2", nullable = false)
    private String choice2;

    @Column(name = "choice3", nullable = false)
    private String choice3;

    @Column(name = "choice4", nullable = false)
    private String choice4;

    @Builder
    public Quiz(LocalDate createdAt, String question, char answer, String choice1, String choice2, String choice3, String choice4) {
        this.createdAt = createdAt;
        this.question = question;
        this.answer = answer;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
    }

    public Quiz() {

    }
}