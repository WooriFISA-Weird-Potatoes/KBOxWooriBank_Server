package com.woorifisa.kboxwoori.domain.crawling.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woorifisa.kboxwoori.domain.crawling.dto.NewsDto;
import com.woorifisa.kboxwoori.domain.crawling.entity.News;
import com.woorifisa.kboxwoori.domain.crawling.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NewsCrawlingService {

    private final RedisTemplate<String, String> redisTemplate;
    private final NewsRepository newsRepository;
    private final ObjectMapper objectMapper;
    private HashOperations<String, String, News> hashOps;
    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    private String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    public void crawlKboNews() {
        try {
            Document document = Jsoup.connect("https://www.koreabaseball.com/News/BreakingNews/List.aspx").get();
            Element boardPhoto = document.select("ul.boardPhoto").first();
            Elements listItems = boardPhoto.select("li");

            int index = 1;

            for (Element listItem : listItems) {
                // 크롤링 데이터
                String articleLink = listItem.select("span.photo a").attr("href");
                String imgLink = listItem.select("span.photo img").attr("src");
                String headline = listItem.select("div.txt strong a").text();
                String contentPreview = listItem.select("div.txt p").first().ownText();
                String date = listItem.select("div.txt p span.date").text();

                // 크롤링된 데이터 DTO에 ID값 설정해서 저장
                NewsDto kboNews = new NewsDto();
                kboNews.setId(formattedTime + "-" + index);
                kboNews.setArticleLink("https://www.koreabaseball.com/News/BreakingNews/" + articleLink);
                kboNews.setImgLink("https:" + imgLink);
                kboNews.setHeadline(headline);
                kboNews.setContentPreview(contentPreview);
                kboNews.setDate(date);

                // DTO에서 Entity로 변환
                News result = newsRepository.save(kboNews.toEntity());
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String newsFindAll() {
        List<Map<String, News>> allNewsResults = new ArrayList<>();
        Set<String> keys = redisTemplate.keys("crawling:news:" + formattedTime + "*");

        for (String key : keys) {
            Map<String, News> newsMap = hashOps.entries(key);
            allNewsResults.add(newsMap);
        }

        // Java 객체를 JSON 문자열로 변환
        String jsonResults = "";
        try {
            jsonResults = objectMapper.writeValueAsString(allNewsResults);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("jsonResults = " + jsonResults);
        return jsonResults;
    }

    public String searchNewsByKeyword(String keyword) {
        List<Map<String, News>> searchNewsResults = new ArrayList<>();
        Set<String> keys = redisTemplate.keys("crawling:news:*" + formattedTime + "*"); // 모든 관련 키를 찾음

        for (String key : keys) {
            Map<String, News> newsMap = hashOps.entries(key);
            try {
                if (String.valueOf(newsMap.get("headline")).contains(keyword)) {
                    searchNewsResults.add(newsMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String jsonResults = "";
        try {
            jsonResults = objectMapper.writeValueAsString(searchNewsResults);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("jsonResults = " + jsonResults);
        return jsonResults;
    }
}
