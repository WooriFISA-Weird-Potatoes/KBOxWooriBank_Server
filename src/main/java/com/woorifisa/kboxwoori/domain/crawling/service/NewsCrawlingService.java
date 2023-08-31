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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsCrawlingService {

    private final NewsRepository newsRepository;

    private final String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    public void crawlKboNews() {
        try {
            Document document = Jsoup.connect("https://www.koreabaseball.com/News/BreakingNews/List.aspx").get();
            Element boardPhoto = document.select("ul.boardPhoto").first();
            Elements listItems = boardPhoto.select("li");

            for (int index = 0; index < listItems.size(); index++) {
                String articleLink = null;
                String imgLink = null;
                String headline = null;
                String contentPreview = null;
                String date = null;
                try {
                    Element listItem = listItems.get(index);
                    articleLink = listItem.select("span.photo a").attr("href");
                    imgLink = listItem.select("span.photo img").attr("src");
                    headline = listItem.select("div.txt strong a").text();
                    contentPreview = listItem.select("div.txt p").first().ownText();
                    date = listItem.select("div.txt p span.date").text();
                } catch (Exception e) {
                    throw CrawlingDataNotFoundException.EXCEPTION;
                }

                News saveNews = newsRepository.save(News.builder()
                                        .id(formattedTime + "-" + String.format("%03d", index + 1))
                                        .articleLink("https://www.koreabaseball.com/News/BreakingNews/" + articleLink)
                                        .imgLink("https:" +imgLink)
                                        .headline(headline)
                                        .contentPreview(contentPreview)
                                        .date(date)
                                        .build());
                log.info("뉴스 저장 !: " + saveNews);
            }
        } catch (Exception e) {
            throw CrawlingDataSaveException.EXCEPTION;
        }
    }

    public List<News> newsFindAll() {
        List<News> newsFindAllResults = null;
        try {
            List<News> newsFindAll = newsRepository.findAll(Sort.by("id"));
            newsFindAllResults = new ArrayList<>();
            for (News news : newsFindAll) {
                if (news.getId().contains(formattedTime)) {
                    newsFindAllResults.add(news);
                }
            }
            log.info("뉴스 API 호출 !: " + newsFindAllResults);
        } catch (Exception e) {
            throw CrawlingStoredDataNotFoundException.EXCEPTION;
        }
        return newsFindAllResults;
    }

    public List<News> searchNewsByKeyword(String keyword) {
        List<News> newsFindAllResults = null;
        try {
            List<News> newsFindAll = newsRepository.findAll(Sort.by("id"));
            newsFindAllResults = new ArrayList<>();
            for (News news : newsFindAll) {
                if (news.getId().contains(formattedTime) &&
                        ((news.getHeadline().contains(keyword.toUpperCase())) ||
                                (news.getHeadline().contains(keyword.toLowerCase())))) {
                        newsFindAllResults.add(news);
                }
            }
            log.info("뉴스 검색 API 호출 !: " + newsFindAllResults);
        } catch (Exception e) {
            throw CrawlingStoredDataNotFoundException.EXCEPTION;
        }
        return newsFindAllResults;
    }
}
