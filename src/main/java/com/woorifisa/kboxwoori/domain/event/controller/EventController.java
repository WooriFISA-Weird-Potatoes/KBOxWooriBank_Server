package com.woorifisa.kboxwoori.domain.event.controller;

import com.woorifisa.kboxwoori.domain.event.dto.EventResponseDto;
import com.woorifisa.kboxwoori.domain.event.service.EventService;
import com.woorifisa.kboxwoori.domain.user.dto.UserSessionDTO;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public EventResponseDto findEvent() {
        return eventService.findCurrentEvent();
    }

    @PostMapping("/{eventId}")
    public Boolean joinEvent(@PathVariable Long eventId,
                             @AuthenticationPrincipal PrincipalDetails principalDetails,
                             @SessionAttribute(required = false)UserSessionDTO user) {
        //TODO: eventId가 다를 때 처리
        if (user == null) {
            return false;
        }

        if (!principalDetails.isWooriLinked()) {
            return false;
        }

        return eventService.joinEvent(principalDetails.getUsername());
    }
}
