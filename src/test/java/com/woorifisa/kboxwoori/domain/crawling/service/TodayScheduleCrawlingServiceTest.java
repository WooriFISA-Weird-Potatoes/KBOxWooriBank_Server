package com.woorifisa.kboxwoori.domain.crawling.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TodayScheduleCrawlingServiceTest {
    @Autowired
    private TodayScheduleCrawlingService service;

    @Test
    @BeforeAll
    void 오늘일정크롤링저장() {
        service.crawlKboTodaySchedule();
    }
    @Test
    @Order(3)
    void 오늘일정크롤링모두가져오기() {
        service.todayScheduleFindAll();
    }
    @Test
    @Order(1)
    void 오늘일정크롤링경기수저장() {
        service.saveTodayMatchCount();
    }
    @Test
    @Order(2)
    void 오늘일정크롤링가장빠른시간() {
        service.saveEarliestMatch();
    }
}