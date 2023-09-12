package com.woorifisa.kboxwoori.domain.crawling.service;

import com.woorifisa.kboxwoori.domain.crawling.entity.Schedule;
import com.woorifisa.kboxwoori.domain.crawling.entity.TeamLogo;
import com.woorifisa.kboxwoori.domain.crawling.exception.CrawlingDataSaveException;
import com.woorifisa.kboxwoori.domain.crawling.exception.CrawlingStoredDataNotFoundException;
import com.woorifisa.kboxwoori.domain.crawling.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleCrawlingService {

    private final ScheduleRepository scheduleRepository;

    public List<Schedule> scheduleFindAll() {
        List<Schedule> scheduleResults = null;
        try {
            List<Schedule> schedules = scheduleRepository.findAll(Sort.by("id"));
            scheduleResults = new ArrayList<>();
            for (Schedule schedule : schedules) {
                if (schedule.getId().contains(LocalDate.now().toString())){
                    scheduleResults.add(schedule);
                }
            }
            log.info("전체 경기일정 API 호출 !: " + scheduleResults);
        } catch (Exception e) {
            throw CrawlingStoredDataNotFoundException.EXCEPTION;
        }
        return scheduleResults;
    }
}