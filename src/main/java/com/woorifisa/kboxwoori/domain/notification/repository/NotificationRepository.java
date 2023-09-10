package com.woorifisa.kboxwoori.domain.notification.repository;

import com.woorifisa.kboxwoori.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<List<Notification>> findTop30ByUser_UserIdOrderByCreatedAtDesc(String userId);

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.isChecked = true WHERE n.id = ?1")
    void setIsCheckedTrue(Long id);
}