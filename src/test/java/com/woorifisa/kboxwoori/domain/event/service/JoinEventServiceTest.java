package com.woorifisa.kboxwoori.domain.event.service;

import com.woorifisa.kboxwoori.domain.event.entity.Event;
import com.woorifisa.kboxwoori.domain.event.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JoinEventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    private static final Long eventId = 1L;

    @BeforeEach
    void setUp() {
        eventService.delete(eventId);
        Event event = Event.builder()
                .prize("아이패드")
                .winnerLimit(10)
                .startDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                .endDate(LocalDateTime.of(LocalDate.now(), LocalTime.MAX))
                .isEnded(false)
                .build();
        eventRepository.save(event);
    }

    @Test
    void 선착순_이벤트() throws InterruptedException {
        eventService.findCurrentEvent();

        int people = 100000;
        int limit = 10;
        CountDownLatch countDownLatch = new CountDownLatch(people);

        List<Thread> threads = Stream
                .generate(() -> new Thread(new JoinEvent(countDownLatch)))
                .limit(people)
                .collect(Collectors.toList());
        threads.forEach(Thread::start);
        countDownLatch.await();

        Long success = eventService.getSize();
        assertEquals(limit, success);
    }

    private class JoinEvent implements Runnable {
        private CountDownLatch countDownLatch;

        public JoinEvent(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            String userId = Thread.currentThread().getName();
            eventService.joinEvent(userId);
            countDownLatch.countDown();
        }
    }
}