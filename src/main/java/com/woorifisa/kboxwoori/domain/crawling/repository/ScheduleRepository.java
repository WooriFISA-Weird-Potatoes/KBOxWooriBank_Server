package com.woorifisa.kboxwoori.domain.crawling.repository;

import com.woorifisa.kboxwoori.domain.crawling.entity.Schedule;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@EnableRedisRepositories
public interface ScheduleRepository extends CrudRepository<Schedule, String>, PagingAndSortingRepository<Schedule, String> {
    @Override
    List<Schedule> findAll(Sort sort);

    @Override
    List<Schedule> findAllById(Iterable<String> strings);
}
