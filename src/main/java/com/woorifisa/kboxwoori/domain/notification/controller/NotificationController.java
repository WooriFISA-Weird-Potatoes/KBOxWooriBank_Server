package com.woorifisa.kboxwoori.domain.notification.controller;

import com.woorifisa.kboxwoori.domain.notification.entity.Notification;
import com.woorifisa.kboxwoori.domain.notification.service.NotificationService;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.woorifisa.kboxwoori.global.util.AuthenticationUtil.getCurrentUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseDto<List<Notification>> getUserNotifications() {
        return ResponseDto.success(notificationService.getUserNotifications(getCurrentUserId()));
    }

    @PostMapping("/read/{id}")
    public ResponseDto<String> markNotificationAsRead(@PathVariable Long id) {
        notificationService.notificationMarkAsRead(id);
        return ResponseDto.success("");
    }

}
