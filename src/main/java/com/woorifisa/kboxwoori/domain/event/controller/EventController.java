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

}
