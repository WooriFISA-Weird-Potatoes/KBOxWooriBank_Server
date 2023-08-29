package com.woorifisa.kboxwoori.domain.crawling.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@NoArgsConstructor
@Getter
@RedisHash("crawling:news")
public class News {
    @Id
    private String id;
    private String articleLink;
    private String imgLink;
    private String headline;
    private String contentPreview;
    private String date;
    @Builder
    public News(String id, String articleLink, String imgLink, String headline, String contentPreview, String date){
        this.id = id;
        this.articleLink = articleLink;
        this.imgLink = imgLink;
        this.headline = headline;
        this.contentPreview = contentPreview;
        this.date = date;
    }

//    public CrawledNewsDTO toResponseDTO() {
//        news;
//        news = News.builder()
//                .id(id)
//                .articleLink(articleLink)
//                .imgLink(imgLink)
//                .headline(headline)
//                .contentPreview(contentPreview)
//                .date(date)
//                .build();
//        return news;
//    }
}
