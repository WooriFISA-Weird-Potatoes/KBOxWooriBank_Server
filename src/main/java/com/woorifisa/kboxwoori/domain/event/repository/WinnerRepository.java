package com.woorifisa.kboxwoori.domain.event.repository;

import com.woorifisa.kboxwoori.domain.event.entity.Winner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WinnerRepository extends JpaRepository<Winner, Long> {
    Optional<Winner> findByEvent_IdAndUser_UserId(Long eventId, String userId);
}
