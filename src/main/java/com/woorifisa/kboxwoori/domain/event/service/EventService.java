package com.woorifisa.kboxwoori.domain.event.service;

import com.woorifisa.kboxwoori.domain.event.dto.EventResponseDto;
import com.woorifisa.kboxwoori.domain.event.entity.Event;
import com.woorifisa.kboxwoori.domain.event.exception.OngoingEventNotFoundException;
import com.woorifisa.kboxwoori.domain.event.repository.EventRedisRepository;
import com.woorifisa.kboxwoori.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventRedisRepository eventRedisRepository;

    private static final String KEY = "event:";

    private Event event;
    private Boolean reachedLimit;

    //테스트용
    @PostConstruct
    public void init() {
        Event event = Event.builder()
                .prize("아이패드")
                .winnerLimit(10)
                .startDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                .endDate(LocalDateTime.of(LocalDate.now(), LocalTime.MAX))
                .isEnded(false)
                .build();

        eventRepository.save(event);
    }

    public EventResponseDto findCurrentEvent() {
        Event event = eventRepository.findByStartDateEquals().orElseThrow(() -> OngoingEventNotFoundException.EXCEPTION);
        if (!event.equals(this.event)) {
            this.event = event;
            reachedLimit = event.getIsEnded();
        }
        return new EventResponseDto(event);
    }

}
