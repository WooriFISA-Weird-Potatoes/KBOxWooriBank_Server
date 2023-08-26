package com.woorifisa.kboxwoori.domain.event.service;

import com.woorifisa.kboxwoori.domain.event.entity.Event;
import com.woorifisa.kboxwoori.domain.event.exception.InvalidEventParticipationTimeException;
import com.woorifisa.kboxwoori.domain.event.exception.OngoingEventNotFoundException;
import com.woorifisa.kboxwoori.domain.event.repository.EventRepository;
import com.woorifisa.kboxwoori.domain.user.entity.Club;
import com.woorifisa.kboxwoori.domain.user.entity.Gender;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void 오늘의_이벤트_가져오기() {
        //진행중인 이벤트
        Event event = Event.builder()
                .prize("아이패드")
                .winnerLimit(10)
                .startDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                .endDate(LocalDateTime.of(LocalDate.now(), LocalTime.MAX))
                .isEnded(false)
                .build();

        Event savedEvent = eventRepository.save(event);

        //시작 전 이벤트
        Event event2 = Event.builder()
                .prize("아이패드")
                .winnerLimit(10)
                .startDate(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIN))
                .endDate(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MAX))
                .isEnded(false)
                .build();

        eventRepository.save(event2);

        assertEquals(savedEvent.getId(), eventService.findCurrentEvent().getId());
    }

    @Test
    @Transactional
    void 오늘의_이벤트가_없으면_실패() {
        //진행중인 이벤트
        Event event = Event.builder()
                .prize("아이패드")
                .winnerLimit(10)
                .startDate(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIN))
                .endDate(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MAX))
                .isEnded(false)
                .build();

        eventRepository.save(event);

        assertThrows(OngoingEventNotFoundException.class, () -> eventService.findCurrentEvent());
    }

    @Test
    @Transactional
    void 이벤트_시간_외에_참여하면_실패() {
        User user = User.builder()
                .admin(false)
                .userId("test")
                .password("test")
                .name("test")
                .gender(Gender.F)
                .birth(LocalDate.now())
                .phone("000-0000-0000")
                .addr("seoul")
                .club(Club.DOOSAN)
                .point(0)
                .svcAgmt(true)
                .infoAgmt(true)
                .wooriLinked(false)
                .build();

        userRepository.save(user);

        //진행중인 이벤트
        Event event = Event.builder()
                .prize("아이패드")
                .winnerLimit(10)
                .startDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(1)))
                .endDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(2)))
                .isEnded(false)
                .build();

        eventRepository.save(event);

        eventService.findCurrentEvent();
        assertThrows(InvalidEventParticipationTimeException.class, () -> eventService.joinEvent("test"));
    }
}