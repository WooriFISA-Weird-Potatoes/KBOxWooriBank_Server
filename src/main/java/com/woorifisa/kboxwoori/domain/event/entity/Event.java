package com.woorifisa.kboxwoori.domain.event.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(nullable = false)
    private String prize;

    @Column(nullable = false)
    private Integer winnerLimit;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Boolean isEnded;

    @Builder
    public Event(String prize, Integer winnerLimit, LocalDateTime startDate, LocalDateTime endDate, Boolean isEnded) {
        this.prize = prize;
        this.winnerLimit = winnerLimit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isEnded = isEnded;
    }

    public void updateIsEnded() {
        this.isEnded = true;
    }
}
