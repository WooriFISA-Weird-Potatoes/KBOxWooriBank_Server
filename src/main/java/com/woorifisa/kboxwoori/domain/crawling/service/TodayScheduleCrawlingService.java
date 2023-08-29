package com.woorifisa.kboxwoori.domain.crawling.service;

import com.woorifisa.kboxwoori.domain.crawling.entity.TeamLogo;
import com.woorifisa.kboxwoori.domain.crawling.entity.TodaySchedule;
import com.woorifisa.kboxwoori.domain.crawling.repository.TodayScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodayScheduleCrawlingService {

    private final RedisTemplate<String, String> redisTemplate;
    private final TodayScheduleRepository todayScheduleRepository;
    private HashOperations<String, String, TodaySchedule> hashOps;

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }
    private String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

    public void crawlKboTodaySchedule() {
        try {
            Document document = Jsoup.connect("https://sports.news.naver.com/kbaseball/schedule/index").get();
            Element todayGames = document.select("ul.sch_vs").first();
            Elements rows = todayGames.select("li");


            int todayGamesIndex = 1;
            for (Element row : rows) {
                // 공통 정보
                String gameTime = row.select("em.state").text();
                Element vsBtn = row.select("div.vs_btn").first();

                String comparisonLink = null;
                String comparisonText = null;
                String tvBroadcastLink = null;
                String tvBroadcastText = null;
                String matchCancled = null;
                if (vsBtn.childrenSize() == 1) {
                    Element vsBtnNone = row.select("div.vs_btn.none").first();
                    matchCancled = vsBtnNone.select("p").text();
                } else {
                    comparisonLink = "https://m.sports.naver.com/" + vsBtn.select("a.btn_ltr").attr("href") ;
                    comparisonText = vsBtn.select("a.btn_ltr").text();
                    tvBroadcastLink = vsBtn.select("span.btn_tlv").attr("href");
                    tvBroadcastText = vsBtn.select("span.btn_tlv").text();
                }

                // team1과 team2 정보를 담을 변수들
                String team1Name = "", team1Score = "", team1SPState = "", team1SPLink = "", team1SPName = "";
                String team2Name = "", team2Score = "", team2SPState = "", team2SPLink = "", team2SPName = "";

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

                // Entity 저장
                TodaySchedule todaySchedule = todayScheduleRepository.save(TodaySchedule.builder()
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
                                                                                        .comparisonLink(comparisonLink)
                                                                                        .comparisonText(comparisonText)
                                                                                        .tvBroadcastLink(tvBroadcastLink)
                                                                                        .tvBroadcastText(tvBroadcastText)
                                                                                        .remarks(matchCancled)
                                                                                        .build());
                System.out.println("todaySchedule = " + todaySchedule);
                todayGamesIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<TodaySchedule> todayScheduleFindAll() {
        List<TodaySchedule> todaySchedules = todayScheduleRepository.findAll(Sort.by("id"));
        log.info("메인페이지 오늘경기일정 API 호출됨 !: " + todaySchedules);
        return todaySchedules;
    }

    public void saveTodayMatchCount() {
        int findTodayMatchCount = todayScheduleRepository.findByDate(LocalDate.now()).size();
        log.info("오늘경기일정 레디스에 저장 !: " + findTodayMatchCount);
        redisTemplate.opsForHash().put("prediction:" + LocalDate.now(), "match-count", String.valueOf(findTodayMatchCount));
    }

    public void saveEarliestMatch() {
        List<TodaySchedule> earliestMatch = todayScheduleRepository.findByDate(LocalDate.now());
        earliestMatch.stream().min(Comparator.comparing(TodaySchedule::getGameTime))
                     .ifPresent(earliestMatchSave -> redisTemplate.opsForHash().put("prediction:" + LocalDate.now(), "end-time", earliestMatchSave.getGameTime()));
        log.info("오늘경기일정 중 가장 빠른 경기시간 레디스에 저장 !: " + earliestMatch);
    }
}
