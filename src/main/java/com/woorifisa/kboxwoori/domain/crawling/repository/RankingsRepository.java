package com.woorifisa.kboxwoori.domain.crawling.repository;

import com.woorifisa.kboxwoori.domain.crawling.entity.Rankings;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@EnableRedisRepositories
public interface RankingsRepository extends CrudRepository<Rankings, String>, PagingAndSortingRepository<Rankings, String> {
    @Override
    List<Rankings> findAll(Sort sort);
}
