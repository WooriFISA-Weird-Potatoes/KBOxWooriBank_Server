package com.woorifisa.kboxwoori.domain.crawling.controller;

import com.woorifisa.kboxwoori.domain.crawling.entity.News;
import com.woorifisa.kboxwoori.domain.crawling.entity.Rankings;
import com.woorifisa.kboxwoori.domain.crawling.entity.Schedule;
import com.woorifisa.kboxwoori.domain.crawling.entity.TodaySchedule;
import com.woorifisa.kboxwoori.domain.crawling.service.NewsCrawlingService;
import com.woorifisa.kboxwoori.domain.crawling.service.RankingsCrawlingService;
import com.woorifisa.kboxwoori.domain.crawling.service.ScheduleCrawlingService;
import com.woorifisa.kboxwoori.domain.crawling.service.TodayScheduleCrawlingService;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CrawlingController {

    private final NewsCrawlingService newsCrawlingService;
    private final RankingsCrawlingService kboRankingWebCrawlingService;
    private final ScheduleCrawlingService scheduleCrawlingService;
    private final TodayScheduleCrawlingService todayScheduleCrawlingService;

    @GetMapping(path = "/api/news/{keyword}")
    public ResponseDto<List<News>> newsSearchedList(@PathVariable(name = "keyword") String keyword) {
        return ResponseDto.success(newsCrawlingService.searchNewsByKeyword(keyword));
    }

    @GetMapping("/api/news")
    public ResponseDto<List<News>> newsAllList() {
        return ResponseDto.success(newsCrawlingService.newsFindAll());
    }

    @GetMapping("/api/main/rankings")
    public ResponseDto<List<Rankings>> rankingsAllList() {
        return ResponseDto.success(kboRankingWebCrawlingService.rankingsFindAll());
    }

    @GetMapping("/api/schedules")
    public ResponseDto<List<Schedule>> scheduleAllList() {
        return ResponseDto.success(scheduleCrawlingService.scheduleFindAll());
    }

    @GetMapping("/api/main/schedules")
    public ResponseDto<List<TodaySchedule>> todayScheduleAllList() {
        return ResponseDto.success(todayScheduleCrawlingService.todayScheduleFindAll());
    }
}
