package com.woorifisa.kboxwoori.domain.crawling.service;

import com.woorifisa.kboxwoori.domain.crawling.entity.Rankings;
import com.woorifisa.kboxwoori.domain.crawling.entity.TeamLogo;
import com.woorifisa.kboxwoori.domain.crawling.exception.CrawlingDataSaveException;
import com.woorifisa.kboxwoori.domain.crawling.exception.CrawlingStoredDataNotFoundException;
import com.woorifisa.kboxwoori.domain.crawling.repository.RankingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RankingsCrawlingService {

    private final RankingsRepository rankingsRepository;

    String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    public void crawlKboRanking() {
        try {
            Document document = Jsoup.connect("https://www.koreabaseball.com/TeamRank/TeamRank.aspx").get();
            Element table = document.select("table.tData").first();
            if (table == null) {
                throw CrawlingStoredDataNotFoundException.EXCEPTION;
            }

            Elements rows = table.select("tr");

            int index = 0;

            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements cells = row.select("td");

                Rankings saveRankings = rankingsRepository.save(Rankings.builder()
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
                                                .build());
                log.info("구단 순위 저장 !: " + saveRankings);
                index++;
            }
        } catch (Exception e) {
            throw CrawlingDataSaveException.EXCEPTION;
        }
    }

    public List<Rankings> rankingsFindAll() {
        List<Rankings> rankingsFindAll = rankingsRepository.findAll(Sort.by("id")).stream()
                                                    .filter(rankings -> rankings.getId().contains(formattedTime))
                                                    .collect(Collectors.toList());
        if (rankingsFindAll.isEmpty()) {
            throw CrawlingStoredDataNotFoundException.EXCEPTION;
        }
        log.info("구단 순위 API 호출 !: " + rankingsFindAll);
        return rankingsFindAll;
    }
}

