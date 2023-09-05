package com.woorifisa.kboxwoori.domain.event.service;

import com.woorifisa.kboxwoori.domain.event.dto.EventResponseDto;
import com.woorifisa.kboxwoori.domain.event.entity.Event;
import com.woorifisa.kboxwoori.domain.event.entity.Winner;
import com.woorifisa.kboxwoori.domain.event.exception.EventIsEndedException;
import com.woorifisa.kboxwoori.domain.event.exception.InvalidEventParticipationTimeException;
import com.woorifisa.kboxwoori.domain.event.exception.OngoingEventNotFoundException;
import com.woorifisa.kboxwoori.domain.event.exception.WinningRecordNotFoundException;
import com.woorifisa.kboxwoori.domain.event.repository.EventRedisRepository;
import com.woorifisa.kboxwoori.domain.event.repository.EventRepository;
import com.woorifisa.kboxwoori.domain.event.repository.WinnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventRedisRepository eventRedisRepository;
    private final WinnerRepository winnerRepository;

    private static final String KEY = "event:winner:";

    private Event event;
    private Boolean reachedLimit;

    public EventResponseDto findCurrentEvent() {
        Event event = eventRepository.findByStartDateEquals().orElseThrow(() -> OngoingEventNotFoundException.EXCEPTION);
        if (!event.equals(this.event)) {
            this.event = event;
            reachedLimit = event.getIsEnded();
        }
        return new EventResponseDto(event);
    }

    public void joinEvent(String userId) {
        if (LocalDateTime.now().isBefore(event.getStartDate()) || LocalDateTime.now().isAfter(event.getEndDate())) {
            throw InvalidEventParticipationTimeException.EXCEPTION;
        }

        if (reachedLimit) {
            throw EventIsEndedException.EXCEPTION;
        }
        saveWinner(userId);
    }

    private void saveWinner(String userId) {
        Boolean result = eventRedisRepository.saveWinner(KEY + event.getId(), userId, event.getWinnerLimit());
        if (!result && !reachedLimit) {
            reachedLimit = true;
            updateIsEnded();
        }
        if (!result) {
            throw EventIsEndedException.EXCEPTION;
        }
    }

    @Transactional
    public void updateIsEnded() {
        Event event = eventRepository.findById(this.event.getId()).orElseThrow(() -> OngoingEventNotFoundException.EXCEPTION);
        event.updateIsEnded();
        this.event = event;
    }

    public Long getSize() {
        return eventRedisRepository.getSize(KEY + event.getId());
    }

    public EventResponseDto getEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> OngoingEventNotFoundException.EXCEPTION);
        return new EventResponseDto(event);
    }

    @Transactional
    public void saveAddress(Long eventId, String userId, String addr) {
        Winner winner = winnerRepository.findByEvent_IdAndUser_UserId(eventId, userId).orElseThrow(() -> WinningRecordNotFoundException.EXCEPTION);
        winner.updateAddr(addr);
    }

    //테스트용
    public void delete(Long eventId) {
        eventRedisRepository.delete(KEY + eventId);
    }
}
