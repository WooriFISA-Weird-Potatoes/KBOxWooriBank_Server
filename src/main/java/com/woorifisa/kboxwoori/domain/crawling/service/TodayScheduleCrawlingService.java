package com.woorifisa.kboxwoori.domain.crawling.service;

import com.woorifisa.kboxwoori.domain.crawling.entity.TeamLogo;
import com.woorifisa.kboxwoori.domain.crawling.entity.TodaySchedule;
import com.woorifisa.kboxwoori.domain.crawling.exception.CrawlingDataNotFoundException;
import com.woorifisa.kboxwoori.domain.crawling.exception.CrawlingDataSaveException;
import com.woorifisa.kboxwoori.domain.crawling.exception.CrawlingStoredDataNotFoundException;
import com.woorifisa.kboxwoori.domain.crawling.repository.TodayScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodayScheduleCrawlingService {

    private final RedisTemplate<String, String> redisTemplate;
    private final TodayScheduleRepository todayScheduleRepository;

    public List<TodaySchedule> todayScheduleFindAll() {
        List<TodaySchedule> todaySchedules = null;
        try {
            todaySchedules = todayScheduleRepository.findByDate(LocalDate.now());
            todaySchedules = todaySchedules.stream().sorted(Comparator.comparing(TodaySchedule::getId))
                                           .collect(Collectors.toList());
            log.info("메인페이지 오늘경기일정 API 호출됨 !: " + todaySchedules);
        } catch (Exception e) {
            throw CrawlingStoredDataNotFoundException.EXCEPTION;
        }
        return todaySchedules;
    }
    
}
