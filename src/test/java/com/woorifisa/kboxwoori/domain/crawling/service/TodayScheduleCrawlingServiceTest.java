package com.woorifisa.kboxwoori.domain.crawling.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodayScheduleCrawlingServiceTest {
    @Autowired
    private TodayScheduleCrawlingService service;

    @Test
    void 오늘일정크롤링저장() {
        service.crawlKboTodaySchedule();
    }
    @Test
    void 오늘일정크롤링모두가져오기() {
        service.todayScheduleFindAll();
    }
    @Test
    void 오늘일정크롤링경기수저장() {
        service.saveTodayMatchCount();
    }
    @Test
    void 오늘일정크롤링가장빠른시간() {
        service.saveEarliestMatch();
    }
}