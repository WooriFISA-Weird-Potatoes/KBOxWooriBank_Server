package com.woorifisa.kboxwoori.domain.crawling.repository;

import com.woorifisa.kboxwoori.domain.crawling.entity.Rankings;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface RankingsRepository extends CrudRepository<Rankings, String> {
}
