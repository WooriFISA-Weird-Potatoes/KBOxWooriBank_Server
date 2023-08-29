package com.woorifisa.kboxwoori.domain.crawling.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScheduleCrawlingServiceTest {
    @Autowired
    private ScheduleCrawlingService service;
    @Test
    void 일정크롤링저장() {
        service.crawlKboSchedule();
    }

    @Test
    void 일정크롤링모두가져오기() {
        service.scheduleFindAll();
    }
}