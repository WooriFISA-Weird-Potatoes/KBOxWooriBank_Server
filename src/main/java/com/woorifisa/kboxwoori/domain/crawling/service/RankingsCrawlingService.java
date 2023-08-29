package com.woorifisa.kboxwoori.domain.crawling.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woorifisa.kboxwoori.domain.crawling.dto.RankingsDto;
import com.woorifisa.kboxwoori.domain.crawling.entity.Rankings;
import com.woorifisa.kboxwoori.domain.crawling.entity.TeamLogo;
import com.woorifisa.kboxwoori.domain.crawling.repository.RankingsRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RankingsCrawlingService {

    private final RedisTemplate<String, String> redisTemplate;
    private final RankingsRepository rankingsRepository;
    private final ObjectMapper objectMapper;

    private HashOperations<String, String, Rankings> hashOps;

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHH"));

    public void crawlKboRanking() {
        try {
            Document document = Jsoup.connect("https://www.koreabaseball.com/TeamRank/TeamRank.aspx").get();
            Element table = document.select("table.tData").first();
            Elements rows = table.select("tr");

            int index = 1;

            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements cells = row.select("td");

                RankingsDto kboRankings = RankingsDto.builder()
                                                     .id(formattedTime + "-" + index)
                                                     .rank(cells.get(0).text())
                                                     .teamName(cells.get(1).text())
                                                     .teamLogo(TeamLogo.fromString(cells.get(1).text()).getTeamLogoUrl())
                                                     .gamesPlayed(cells.get(2).text())
                                                     .wins(cells.get(3).text())
                                                     .losses(cells.get(4).text())
                                                     .draws(cells.get(5).text())
                                                     .winningPercentage(cells.get(6).text())
                                                     .gameBehind(cells.get(7).text())
                                                     .build();
                // DTO에서 Entity로 변환
                Rankings result = rankingsRepository.save(kboRankings.toEntity());
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public String rankingsFindAll() {
        List<Map<String, Rankings>> allRankingsResults = new ArrayList<>();
        Set<String> keys = redisTemplate.keys("crawling:rankings:*"); // 모든 관련 키를 찾음
        System.out.println("keys = " + keys);

        for (String key : keys) {
            Map<String, Rankings> rankingsMap = hashOps.entries(key);
            allRankingsResults.add(rankingsMap);
        }
        String jsonResults = "";
        try {
            jsonResults = objectMapper.writeValueAsString(allRankingsResults);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("jsonResults = " + jsonResults);
        return jsonResults;
    }
}

