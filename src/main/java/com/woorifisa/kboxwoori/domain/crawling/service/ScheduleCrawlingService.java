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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleCrawlingService {

    private final ScheduleRepository scheduleRepository;

    private final String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    public void crawlKboSchedule() {
        try {
            Document document = Jsoup.connect("https://sports.news.naver.com/kbaseball/schedule/index").get();
            Elements games = document.select("div.sch_tb, div.sch_tb2");

            int gamesIndex = 1;
            for (Element game : games) {
                // 크롤링 데이터
                String date = game.select(".td_date strong").text();
                String day = game.select(".td_date").text().replace(date, "").trim();
                date = date + day;
                Elements rows = game.select("tr");

                for (Element row : rows) {
                    String time = row.select(".td_hour").text();
                    String teamInfo = row.select(".team_lft").text() + " " + row.select(".td_score").text() + " " + row.select(".team_rgt").text();
                    String team1 = row.select(".team_lft").text();
                    String team2 = row.select(".team_rgt").text();
                    String score = row.select(".td_score").text();
                    Elements stadiums = row.select(".td_stadium");
                    String stadium;
                    String remarks = "";
                    if (!stadiums.isEmpty()) {
                        stadium = stadiums.last().text();
                    } else {
                        stadium = "";
                    }
                    if (!row.select(".suspended").isEmpty()) {
                        time = "경기취소";
                    }
                    if (time.equals("-")){
                        remarks = "프로야구 경기가 없습니다.";
                    }

                    Schedule saveSchedule = scheduleRepository.save(Schedule.builder()
                                                                      .id(formattedTime + "-" + String.format("%03d", gamesIndex))
                                                                      .date(date)
                                                                      .time(time)
                                                                      .teamInfo(teamInfo)
                                                                      .team1(team1)
                                                                      .team2(team2)
                                                                      .score(score)
                                                                      .stadium(stadium)
                                                                      .remarks(remarks)
                                                                      .team1Logo(TeamLogo.fromString(team1).getTeamLogoUrl())
                                                                      .team2Logo(TeamLogo.fromString(team2).getTeamLogoUrl())
                                                                      .build());
                    log.info("전체 경기 일정 저장 !:" + saveSchedule);
                    gamesIndex++;
                }
            }
        } catch (Exception e) {
            throw CrawlingDataSaveException.EXCEPTION;
        }
    }

    public List<Schedule> scheduleFindAll() {
        List<Schedule> scheduleResults = null;
        try {
            List<Schedule> schedules = scheduleRepository.findAll(Sort.by("id"));
            scheduleResults = new ArrayList<>();
            for (Schedule schedule : schedules) {
                if (schedule.getId().contains(formattedTime)){
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