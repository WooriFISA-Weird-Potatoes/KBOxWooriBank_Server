package com.woorifisa.kboxwoori.domain.crawling.service;

import com.woorifisa.kboxwoori.domain.crawling.entity.TeamLogo;
import com.woorifisa.kboxwoori.domain.crawling.entity.TodaySchedule;
import com.woorifisa.kboxwoori.domain.crawling.exception.CrawlingDataSaveException;
import com.woorifisa.kboxwoori.domain.crawling.exception.CrawlingStoredDataNotFoundException;
import com.woorifisa.kboxwoori.domain.crawling.repository.TodayScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodayScheduleCrawlingService {

    private final RedisTemplate<String, String> redisTemplate;
    private final TodayScheduleRepository todayScheduleRepository;

    private final String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    public void crawlKboTodaySchedule() {
        try {
            Document document = Jsoup.connect("https://sports.news.naver.com/kbaseball/schedule/index").get();
            Element todayGames = document.select("ul.sch_vs").first();
            Elements rows = todayGames.select("li");

            int todayGamesIndex = 1;

            for (Element row : rows) {
                String gameTime = row.select("em.state").text();

                String team1SPName = "";
                String team1SPLink = "";
                String team1SPState = "";
                String team1Score = "";
                String team1Name = "";
                String team2SPName = "";
                String team2SPLink = "";
                String team2SPState = "";
                String team2Score = "";
                String team2Name = "";
                try {
                    for (Element vsDiv : row.select(".vs_lft, .vs_rgt")) {
                        String teamName = vsDiv.select("strong").first().text();
                        String teamScore = vsDiv.select("strong").last().text();
                        String startingPlayer = vsDiv.select("span.game_info > span").first().text();
                        Element playerLinkTag = vsDiv.select("a").first();
                        String playerLink = playerLinkTag.attr("href");
                        String playerName = playerLinkTag.text();

                        if (vsDiv.className().contains("vs_lft")) {
                            team1Name = teamName;
                            team1Score = teamScore;
                            team1SPState = startingPlayer;
                            team1SPLink = playerLink;
                            team1SPName = playerName;
                        } else {
                            team2Name = teamName;
                            team2Score = teamScore;
                            team2SPState = startingPlayer;
                            team2SPLink = playerLink;
                            team2SPName = playerName;
                        }
                    }
                } catch (Exception e) {
                    throw CrawlingDataSaveException.EXCEPTION;
                }
                if (team1Name.isEmpty() && team2Name.isEmpty()) {
                    continue;
                }
                TodaySchedule saveTodaySchedule = todayScheduleRepository.save(TodaySchedule.builder()
                                                                                        .id(formattedTime + "-" + todayGamesIndex)
                                                                                        .date(LocalDate.now())
                                                                                        .gameTime(gameTime)
                                                                                        .team1Name(team1Name)
                                                                                        .team1Logo(TeamLogo.fromString(team1Name).getTeamLogoUrl())
                                                                                        .team1Score(team1Score)
                                                                                        .team1SPState(team1SPState)
                                                                                        .team1SPLink(team1SPLink)
                                                                                        .team1SPName(team1SPName)
                                                                                        .team2Name(team2Name)
                                                                                        .team2Logo(TeamLogo.fromString(team2Name).getTeamLogoUrl())
                                                                                        .team2Score(team2Score)
                                                                                        .team2SPState(team2SPState)
                                                                                        .team2SPLink(team2SPLink)
                                                                                        .team2SPName(team2SPName)
                                                                                        .build());
                log.info("오늘 경기 일정 저장 !: " + saveTodaySchedule);
                todayGamesIndex++;
            }
        } catch (Exception e) {
            throw CrawlingDataSaveException.EXCEPTION;
        }
    }

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

    public void saveTodayMatchCount() {
        try {
            List<TodaySchedule> todaySchedules = todayScheduleRepository.findByDate(LocalDate.now());
            todaySchedules = todaySchedules.stream().sorted(Comparator.comparing(TodaySchedule::getId))
                                           .collect(Collectors.toList());

            // "gameTime": "취소"인 객체의 수를 세기
            long cancelledCount = todaySchedules.stream().filter(schedule -> "취소".equals(schedule.getGameTime())).count();
            int matchCountResult = todaySchedules.size() - (int) cancelledCount;

            redisTemplate.opsForHash().put("prediction:info:" + LocalDate.now(), "match-count", String.valueOf(matchCountResult));
        } catch (Exception e) {
            throw CrawlingStoredDataNotFoundException.EXCEPTION;
        }
    }

    public void saveEarliestMatch() {
        List<TodaySchedule> earliestMatch = todayScheduleRepository.findByDate(LocalDate.now())
                                                                   .stream()
                                                                   .sorted(Comparator.comparing(TodaySchedule::getId))
                                                                   .collect(Collectors.toList());
        log.info("오늘 일정 중 가장 빠른 경기 시간 저장 !: " + earliestMatch);
        if (earliestMatch.isEmpty()) {
            throw CrawlingStoredDataNotFoundException.EXCEPTION;
        }
        redisTemplate.opsForHash().put("prediction:info:" + LocalDate.now(), "end-time", earliestMatch.get(0).getGameTime());
    }
}
