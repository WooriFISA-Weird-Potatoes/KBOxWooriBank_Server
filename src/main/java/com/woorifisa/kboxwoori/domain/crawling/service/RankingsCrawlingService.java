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

import java.time.LocalDate;
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

    public List<Rankings> rankingsFindAll() {
        List<Rankings> rankingsFindAll = rankingsRepository.findAll(Sort.by("id")).stream()
                                                    .filter(rankings -> rankings != null && rankings.getId().contains(LocalDate.now().toString()))
                                                    .collect(Collectors.toList());
        if (rankingsFindAll.isEmpty()) {
            throw CrawlingStoredDataNotFoundException.EXCEPTION;
        }
        log.info("구단 순위 API 호출 !: " + rankingsFindAll);
        return rankingsFindAll;
    }
}

