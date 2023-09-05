package com.woorifisa.kboxwoori.domain.notification.service;

import com.woorifisa.kboxwoori.domain.notification.entity.Notification;
import com.woorifisa.kboxwoori.domain.notification.entity.Type;
import com.woorifisa.kboxwoori.domain.notification.exception.NotificationNotFoundException;
import com.woorifisa.kboxwoori.domain.notification.repository.NotificationRepository;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.exception.AccountNotFoundException;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public List<Notification> getUserNotifications(String userId) {
        return notificationRepository.findTop30ByUser_UserIdOrderByCreatedAtDesc(userId).orElseThrow(() -> NotificationNotFoundException.EXCEPTION);
    }


}
