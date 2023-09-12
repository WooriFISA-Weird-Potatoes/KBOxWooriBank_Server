package com.woorifisa.kboxwoori.domain.crawling.service;

import com.woorifisa.kboxwoori.domain.crawling.entity.News;
import com.woorifisa.kboxwoori.domain.crawling.exception.CrawlingDataNotFoundException;
import com.woorifisa.kboxwoori.domain.crawling.exception.CrawlingDataSaveException;
import com.woorifisa.kboxwoori.domain.crawling.exception.CrawlingStoredDataNotFoundException;
import com.woorifisa.kboxwoori.domain.crawling.repository.NewsRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsCrawlingService {

    private final NewsRepository newsRepository;

    public List<News> newsFindAll() {
        List<News> newsFindAll = newsRepository.findAll(Sort.by("id"))
                                               .stream().filter(news -> news.getId().contains(LocalDate.now().toString()))
                                               .collect(Collectors.toList());
        if (newsFindAll.isEmpty()) {
            throw CrawlingStoredDataNotFoundException.EXCEPTION;
        }
        return newsFindAll;
    }

    public List<News> searchNewsByKeyword(String keyword) {
        List<News> newsFindAll = newsRepository.findAll(Sort.by("id"))
                                               .stream().filter(news -> news.getId().contains(LocalDate.now().toString()))
                                               .collect(Collectors.toList());
        if (newsFindAll.isEmpty()) {
            throw CrawlingStoredDataNotFoundException.EXCEPTION;
        }

        return newsFindAll.stream()
                          .filter(news -> news.getHeadline().contains(keyword.toUpperCase()) ||
                                  news.getHeadline().contains(keyword.toLowerCase()))
                          .collect(Collectors.toList());
    }
}
