package com.woorifisa.kboxwoori.domain.crawling.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RankingsCrawlingServiceTest {

    @Autowired
    private RankingsCrawlingService service;

    @Test
    void 순위크롤링모두가져오기() {
        service.rankingsFindAll();
    }
}