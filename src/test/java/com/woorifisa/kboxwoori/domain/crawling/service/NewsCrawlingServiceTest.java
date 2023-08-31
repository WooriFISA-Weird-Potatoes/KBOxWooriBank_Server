package com.woorifisa.kboxwoori.domain.crawling.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NewsCrawlingServiceTest {
    @Autowired
    private NewsCrawlingService service;
    @Test
    void 크롤링뉴스저장() {
        service.crawlKboNews();
    }

    @Test
    void 크롤링뉴스전체리스트() {
        service.newsFindAll();
    }

    @Test
    void 크롤링뉴스검색() {
       service.searchNewsByKeyword("한화");
    }
}