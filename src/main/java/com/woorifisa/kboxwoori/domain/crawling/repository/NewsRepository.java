package com.woorifisa.kboxwoori.domain.crawling.repository;

import com.woorifisa.kboxwoori.domain.crawling.entity.News;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@EnableRedisRepositories
public interface NewsRepository extends CrudRepository<News, String>, PagingAndSortingRepository<News, String> {
    @Override
    List<News> findAll(Sort sort);
}
