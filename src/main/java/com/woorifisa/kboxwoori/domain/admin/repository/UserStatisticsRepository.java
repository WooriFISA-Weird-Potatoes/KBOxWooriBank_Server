package com.woorifisa.kboxwoori.domain.admin.repository;

import com.woorifisa.kboxwoori.domain.admin.entity.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserStatisticsRepository extends JpaRepository <UserStatistics,Long>{

    List<UserStatistics> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);

}
