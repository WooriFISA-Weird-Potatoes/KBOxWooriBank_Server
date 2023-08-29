package com.woorifisa.kboxwoori.domain.point.repository;

import com.woorifisa.kboxwoori.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    @Query("SELECT ph FROM Point ph WHERE ph.user.userId = :userId ORDER BY ph.id DESC")
    List<Point> findPointByUserId(String userId);
    Optional<Point> deletePointByUserId(Long id);

    

}
