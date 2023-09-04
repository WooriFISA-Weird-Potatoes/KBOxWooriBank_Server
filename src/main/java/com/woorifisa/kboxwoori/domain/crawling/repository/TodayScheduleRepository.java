package com.woorifisa.kboxwoori.domain.crawling.repository;

import com.woorifisa.kboxwoori.domain.crawling.entity.TodaySchedule;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

@EnableRedisRepositories
public interface TodayScheduleRepository extends CrudRepository<TodaySchedule, String>, PagingAndSortingRepository<TodaySchedule, String> {
    @Override
    List<TodaySchedule> findAll(Sort id);

    @Override
    List<TodaySchedule> findAllById(Iterable<String> strings);

    List<TodaySchedule> findByDate(LocalDate date);

    List<TodaySchedule> findByGameTime(String gameTime);
}
