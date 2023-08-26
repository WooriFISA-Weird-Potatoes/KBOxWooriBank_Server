package com.woorifisa.kboxwoori.domain.event.repository;

import com.woorifisa.kboxwoori.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * " +
            "FROM event e " +
            "WHERE DATE(e.start_date) = DATE(NOW()) AND e.is_ended = False " +
            "LIMIT 1", nativeQuery = true)
    Optional<Event> findByStartDateEquals();
}
