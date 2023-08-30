package com.woorifisa.kboxwoori.domain.admin.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@ToString
@Table(name = "user_statistics")
public class UserStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_users", nullable = false)
    private Integer totalUsers;

    @Column(name = "prediction_participants", nullable = false)
    private Integer predictionParticipants;

    @Column(name = "quiz_participants", nullable = false)
    private Integer quizParticipants;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDate createdAt;


    @Builder
    public UserStatistics(Integer totalUsers, Integer predictionParticipants, Integer quizParticipant, LocalDate createdAt) {
        this.totalUsers = totalUsers;
        this.predictionParticipants = predictionParticipants;
        this.quizParticipants = quizParticipant;
        this.createdAt = createdAt;
    }

    public UserStatistics() {

    }
}
