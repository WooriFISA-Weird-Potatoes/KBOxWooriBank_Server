package com.woorifisa.kboxwoori.domain.prediction.entity;

import com.woorifisa.kboxwoori.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PredictionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private Boolean isCorrect;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Builder
    public PredictionHistory(User user, Boolean isCorrect, LocalDate createdAt) {
        this.user = user;
        this.isCorrect = isCorrect;
        this.createdAt = createdAt;
    }

}
