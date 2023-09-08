package com.woorifisa.kboxwoori.domain.event.controller;

import com.woorifisa.kboxwoori.domain.event.dto.EventResponseDto;
import com.woorifisa.kboxwoori.domain.event.exception.WooriLinkRequiredException;
import com.woorifisa.kboxwoori.domain.event.service.EventService;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.woorifisa.kboxwoori.global.util.AuthenticationUtil.getCurrentUserId;
import static com.woorifisa.kboxwoori.global.util.AuthenticationUtil.isWooriLinked;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseDto<EventResponseDto> getCurrentEvent() {
        return ResponseDto.success(eventService.findCurrentEvent());
    }

    @PostMapping("/{eventId}")
    public ResponseDto joinEvent(@PathVariable Long eventId) {
        //TODO: eventId가 다를 때 처리
        if (!isWooriLinked()) {
            throw WooriLinkRequiredException.EXCEPTION;
        }

        eventService.joinEvent(getCurrentUserId());
        return ResponseDto.success();
    }

    @GetMapping("/{eventId}")
    public ResponseDto<EventResponseDto> getEvent(@PathVariable Long eventId) {
        return ResponseDto.success(eventService.getEvent(eventId));
    }

    @PostMapping("/{eventId}/addr")
    public ResponseDto saveAddress(@PathVariable Long eventId,
                                   @RequestBody String addr) {
        eventService.saveAddress(eventId, getCurrentUserId(), addr);
        return ResponseDto.success();
    }
}
