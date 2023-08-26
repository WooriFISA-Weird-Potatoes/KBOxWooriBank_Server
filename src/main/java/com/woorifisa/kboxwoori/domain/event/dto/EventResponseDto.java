package com.woorifisa.kboxwoori.domain.event.dto;

import com.woorifisa.kboxwoori.domain.event.entity.Event;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EventResponseDto {
    private Long id;
    private String prize;
    private Integer winnerLimit;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isEnded;

    public EventResponseDto(Event event) {
        this.id = event.getId();
        this.prize = event.getPrize();
        this.winnerLimit = event.getWinnerLimit();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.isEnded = event.getIsEnded();
    }
}
