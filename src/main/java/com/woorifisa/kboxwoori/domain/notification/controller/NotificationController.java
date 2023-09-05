package com.woorifisa.kboxwoori.domain.notification.controller;

import com.woorifisa.kboxwoori.domain.notification.entity.Notification;
import com.woorifisa.kboxwoori.domain.notification.service.NotificationService;
import com.woorifisa.kboxwoori.domain.user.exception.NotAuthenticatedAccountException;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseDto<List<Notification>> getUserNotifications(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails == null) {
            throw NotAuthenticatedAccountException.EXCEPTION;
        }

        return ResponseDto.success(notificationService.getUserNotifications(principalDetails.getUsername()));
    }

}
